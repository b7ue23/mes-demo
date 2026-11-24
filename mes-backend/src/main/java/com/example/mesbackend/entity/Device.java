package com.example.mesbackend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "device")
public class Device {
    @Id
    private Integer id;
    private String deviceName;
    private String deviceCode;
    private Integer status;

    // Getters and Setters (如果没自动生成，手动把下面代码复制进去)
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getDeviceName() { return deviceName; }
    public void setDeviceName(String deviceName) { this.deviceName = deviceName; }
    public String getDeviceCode() { return deviceCode; }
    public void setDeviceCode(String deviceCode) { this.deviceCode = deviceCode; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
}