package com.example.mesbackend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "telemetry")
public class Telemetry {
    @Id
    private Integer id;
    private Integer deviceId;
    private Integer spindleSpeed;
    private Integer runStatus;
    private String alarmCode;
    private LocalDateTime collectTime;

    // Getters and Setters (右键 -> Generate -> Getter and Setter)
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getDeviceId() { return deviceId; }
    public void setDeviceId(Integer deviceId) { this.deviceId = deviceId; }
    public Integer getSpindleSpeed() { return spindleSpeed; }
    public void setSpindleSpeed(Integer spindleSpeed) { this.spindleSpeed = spindleSpeed; }
    public Integer getRunStatus() { return runStatus; }
    public void setRunStatus(Integer runStatus) { this.runStatus = runStatus; }
    public String getAlarmCode() { return alarmCode; }
    public void setAlarmCode(String alarmCode) { this.alarmCode = alarmCode; }
    public LocalDateTime getCollectTime() { return collectTime; }
    public void setCollectTime(LocalDateTime collectTime) { this.collectTime = collectTime; }
}