package by.matrosov.vkbotserver.model;

import javax.persistence.*;

@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int message_id;
    private String message_text;
    private int message_owner;

    public Message() {
    }

    public Message(String message_text, int message_owner) {
        this.message_text = message_text;
        this.message_owner = message_owner;
    }

    public int getMessage_id() {
        return message_id;
    }

    public void setMessage_id(int message_id) {
        this.message_id = message_id;
    }

    public String getMessage_text() {
        return message_text;
    }

    public void setMessage_text(String message_text) {
        this.message_text = message_text;
    }

    public int getMessage_owner() {
        return message_owner;
    }

    public void setMessage_owner(int message_owner) {
        this.message_owner = message_owner;
    }
}
