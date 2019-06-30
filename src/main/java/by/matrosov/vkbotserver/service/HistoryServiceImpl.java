package by.matrosov.vkbotserver.service;

import by.matrosov.vkbotserver.model.HistoryMessage;
import by.matrosov.vkbotserver.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HistoryServiceImpl implements HistoryService {

    @Autowired
    private HistoryRepository historyRepository;

    @Override
    public void add(HistoryMessage historyMessage) {
        historyRepository.save(historyMessage);
    }
}
