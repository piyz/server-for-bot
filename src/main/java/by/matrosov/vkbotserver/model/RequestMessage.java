package by.matrosov.vkbotserver.model;

import com.fasterxml.jackson.databind.JsonNode;


public class RequestMessage {
    private String type;
    private JsonNode object;
    private int group_id;

    public RequestMessage() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public JsonNode getObject() {
        return object;
    }

    public void setObject(JsonNode object) {
        this.object = object;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    @Override
    public String toString() {
        return "RequestMessage{" +
                "type='" + type + '\'' +
                ", object=" + object +
                ", group_id=" + group_id +
                '}';
    }
}
