package by.matrosov.vkbotserver.service;

import by.matrosov.vkbotserver.model.HistoryMessage;
import org.springframework.stereotype.Service;

@Service
public interface HistoryService {
    void add(HistoryMessage historyMessage);
}
