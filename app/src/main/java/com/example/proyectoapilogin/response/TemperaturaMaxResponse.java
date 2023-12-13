package com.example.proyectoapilogin.response;

import com.google.gson.annotations.SerializedName;

public class TemperaturaMaxResponse {

    @SerializedName("process")
    private String process;

    @SerializedName("message")
    private String message;

    @SerializedName("error")
    private String error;

    @SerializedName("room")
    private RoomResponse room;

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public RoomResponse getRoom() {
        return room;
    }

    public void setRoom(RoomResponse room) {
        this.room = room;
    }

    public static class RoomResponse {

        @SerializedName("id")
        private String id;

        @SerializedName("value")
        private String value;

        @SerializedName("feed_id")
        private int feedId;

        @SerializedName("feed_key")
        private String feedKey;

        @SerializedName("created_at")
        private String createdAt;

        @SerializedName("created_epoch")
        private long createdEpoch;

        @SerializedName("expiration")
        private String expiration;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getFeedId() {
            return feedId;
        }

        public void setFeedId(int feedId) {
            this.feedId = feedId;
        }

        public String getFeedKey() {
            return feedKey;
        }

        public void setFeedKey(String feedKey) {
            this.feedKey = feedKey;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public long getCreatedEpoch() {
            return createdEpoch;
        }

        public void setCreatedEpoch(long createdEpoch) {
            this.createdEpoch = createdEpoch;
        }

        public String getExpiration() {
            return expiration;
        }

        public void setExpiration(String expiration) {
            this.expiration = expiration;
        }
    }
}