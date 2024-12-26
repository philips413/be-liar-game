package psnl.liar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import psnl.liar.entity.Chat;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Integer> {
}
