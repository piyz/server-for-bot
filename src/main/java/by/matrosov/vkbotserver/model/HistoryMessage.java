package by.matrosov.vkbotserver.model;

import javax.persistence.*;

@Entity
@Table(name = "history")
public class HistoryMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int owner_id;
    private long count;
    private String timestamp;

    public HistoryMessage() {
    }

    public HistoryMessage(int owner_id, long count, String timestamp) {
        this.owner_id = owner_id;
        this.count = count;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(int owner_id) {
        this.owner_id = owner_id;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
