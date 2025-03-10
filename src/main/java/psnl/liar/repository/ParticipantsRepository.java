package psnl.liar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import psnl.liar.entity.Participants;

import java.util.List;

@Repository
public interface ParticipantsRepository extends JpaRepository<Participants, Integer> {

}
