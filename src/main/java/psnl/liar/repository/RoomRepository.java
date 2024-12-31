package psnl.liar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import psnl.liar.constant.YesOrNo;
import psnl.liar.entity.Room;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {

    Room findByChatIdAndPartId(String chatId, String partId);

    List<Room> findByChatIdAndStatus(String chatId, YesOrNo status);
}
