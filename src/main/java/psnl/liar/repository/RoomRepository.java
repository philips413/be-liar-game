package psnl.liar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import psnl.liar.constant.YesOrNo;
import psnl.liar.entity.Room;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {

    List<Room> findAllByChatId(String id);

    Room findByChatIdAndPartId(String chatId, String partId);

    List<Room> findByChatId(String chatId);

    List<Room> findByChatIdAndStatus(String chatId, YesOrNo status);
}
