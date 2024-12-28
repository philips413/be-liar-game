package psnl.liar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import psnl.liar.entity.Theme;

@Repository
public interface ThemeRepository extends JpaRepository<Theme, Long> {
}
