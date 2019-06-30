package by.matrosov.vkbotserver.repository;

import by.matrosov.vkbotserver.model.HistoryMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository extends JpaRepository<HistoryMessage, Integer> {
}
