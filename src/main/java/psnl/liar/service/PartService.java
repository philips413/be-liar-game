package psnl.liar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import psnl.liar.entity.Participants;
import psnl.liar.payload.dto.CreateUserDto;
import psnl.liar.repository.ParticipantsRepository;

@Service
@RequiredArgsConstructor
public class PartService {

    private final ParticipantsRepository participantsRepository;


    public Participants createUser(CreateUserDto user) {
        Participants entity = Participants.creator()
                .name(user.getName())
                .cookie(user.getCookie())
                .build();

        System.out.println(user.toString());
        Participants result = participantsRepository.save(entity);
        return result;
    }
}
