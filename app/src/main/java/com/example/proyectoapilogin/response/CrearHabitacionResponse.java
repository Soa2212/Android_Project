package com.example.proyectoapilogin.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CrearHabitacionResponse {

    @SerializedName("process")
    private String process;

    @SerializedName("message")
    private String message;

    @SerializedName("menssage")
    private Menssage menssage;

    @SerializedName("room")
    private Room room;

    @SerializedName("status")
    private String status;

    public String getProcess() {
        return process;
    }

    public String getMessage() {
        return message;
    }

    public Menssage getMenssage() {
        return menssage;
    }

    public Room getRoom() {
        return room;
    }

    public String getStatus() {
        return status;
    }

    public static class Room {
        @SerializedName("name")
        private String name;

        @SerializedName("user_id")
        private int userId;

        @SerializedName("updated_at")
        private String updatedAt;

        @SerializedName("created_at")
        private String createdAt;

        @SerializedName("id")
        private int id;

        public String getName() {
            return name;
        }

        public int getUserId() {
            return userId;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public int getId() {
            return id;
        }
    }

    public static class Menssage {
        @SerializedName("name")
        private List<String> name;

        public List<String> getName() {
            return name;
        }
    }
}