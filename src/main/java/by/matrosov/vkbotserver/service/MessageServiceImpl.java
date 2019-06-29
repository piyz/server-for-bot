package by.matrosov.vkbotserver.service;

import by.matrosov.vkbotserver.model.Message;
import by.matrosov.vkbotserver.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public void add(Message message) {
        messageRepository.save(message);
    }

    @Override
    public List<Message> getAll() {
        return messageRepository.findAll();
    }
}
