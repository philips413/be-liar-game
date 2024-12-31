package psnl.liar.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import psnl.liar.constant.YesOrNo;
import psnl.liar.entity.Participants;
import psnl.liar.entity.Room;
import psnl.liar.payload.dto.WebSocketResponse;
import psnl.liar.repository.ChatRepository;
import psnl.liar.repository.ParticipantsRepository;
import psnl.liar.repository.RoomRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
@EnableBatchProcessing
public class RoomBatchScheduler {

    private final RoomRepository roomRepository;
    private final ChatRepository chatRepository;
    private final ParticipantsRepository participantsRepository;
    private final SimpMessageSendingOperations template;
    private final static String TOPIC = "/sub/chatroom/";

    @Scheduled(cron = "30 * * * * *")
    public void roomBatch() {
        log.info("========Chat Room Batch Start========");
        List<Room> roomInPeople = roomRepository.findAll();
        roomInPeople.forEach(part -> {

            LocalDateTime updatedAt = part.getUpdatedAt();
            LocalDateTime now = LocalDateTime.now();
            if (updatedAt == null || ChronoUnit.SECONDS.between(updatedAt, now) >= 20) {
                part.exit();
                roomRepository.save(part);
            }
        });

        chatRepository.findAll()
                .stream()
                .forEach(chat -> {

                    List<Participants> list = roomRepository.findByChatIdAndStatus(chat.getChatId(), YesOrNo.YES)
                            .stream()
                            .map(
                                    dto -> participantsRepository
                                            .findById(Integer.valueOf(dto.getPartId()))
                                            .orElseThrow(() -> new IllegalArgumentException("참여자가 존재하지 않습니다."))
                            ).toList();

                    WebSocketResponse result = WebSocketResponse.builder()
                            .users(list)
                            .question(new ArrayList<>())
                            .build();

                    template.convertAndSend(TOPIC+chat.getChatId(), result);
                });


    }

}
