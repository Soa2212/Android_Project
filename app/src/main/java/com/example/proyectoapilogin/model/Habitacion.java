package com.example.proyectoapilogin.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Habitacion {
    @SerializedName("id")
    private int id;

    @SerializedName("nombre")
    private String nombre;
    @SerializedName("sensor_magnetico")
    private int sensorMagnetico;

    @SerializedName("movimiento")
    private int movimiento;

    @SerializedName("temperatura")
    private double temperatura;

    @SerializedName("humedad")
    private double humedad;

    @SerializedName("luz")
    private int luz;

    @SerializedName("voltaje")
    private double voltaje;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("updated_at")
    private String updatedAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public int getSensorMagnetico() {
        return sensorMagnetico;
    }

    public void setSensorMagnetico(int sensorMagnetico) {
        this.sensorMagnetico = sensorMagnetico;
    }

    public int getMovimiento() {
        return movimiento;
    }

    public void setMovimiento(int movimiento) {
        this.movimiento = movimiento;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public double getHumedad() {
        return humedad;
    }

    public void setHumedad(double humedad) {
        this.humedad = humedad;
    }

    public int getLuz() {
        return luz;
    }

    public void setLuz(int luz) {
        this.luz = luz;
    }

    public double getVoltaje() {
        return voltaje;
    }

    public void setVoltaje(double voltaje) {
        this.voltaje = voltaje;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Habitacion(int id, String nombre, int sensorMagnetico, int movimiento, double temperatura, double humedad, int luz, double voltaje, String createdAt, String updatedAt) {
        this.id = id;
        this.nombre = nombre;
        this.sensorMagnetico = sensorMagnetico;
        this.movimiento = movimiento;
        this.temperatura = temperatura;
        this.humedad = humedad;
        this.luz = luz;
        this.voltaje = voltaje;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
