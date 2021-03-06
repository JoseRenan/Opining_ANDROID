package br.com.opining.domain;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;

public class Room {

    private String roomId;
    private String authorId;
    private String content;
    private HashMap<String, Object> timestamp;

    public Room() {
    }

    public Room(String authorId, String content) {
        this.authorId = authorId;
        this.content = content;
        timestamp = new HashMap<>();
        timestamp.put("timestamp", ServerValue.TIMESTAMP);
    }

    public Room(String roomId, String authorId, String content, HashMap<String, Object> timestamp) {
        this.roomId = roomId;
        this.authorId = authorId;
        this.content = content;
        this.timestamp = timestamp;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getContent() {
        return content;
    }

    @Exclude
    public String getRoomId() {
        return roomId;
    }

    @Exclude
    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public HashMap<String, Object> getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(HashMap<String, Object> timestamp) {
        this.timestamp = timestamp;
    }

    @Exclude
    public long getTimestampLong(){
        return (long) timestamp.get("timestamp");
    }

    public void setContent(String content) {
        this.content = content;
    }
}
