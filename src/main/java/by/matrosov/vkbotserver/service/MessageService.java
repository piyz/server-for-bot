package by.matrosov.vkbotserver.service;

import by.matrosov.vkbotserver.model.Message;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MessageService {
    void add(Message message);
    void deleteAll();
    List<Message> getAll();
}
