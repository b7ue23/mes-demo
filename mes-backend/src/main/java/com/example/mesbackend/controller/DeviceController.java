package com.example.mesbackend.controller;

import com.example.mesbackend.entity.Device;
import com.example.mesbackend.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/api")
public class DeviceController {

    @Autowired
    private DeviceRepository deviceRepository;

    @GetMapping("/devices")
    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }
    // 先把 Repository 注入进来
    @Autowired
    private com.example.mesbackend.repository.TelemetryRepository telemetryRepository;

    @Autowired
    private  com.example.mesbackend.repository.AlarmRepository alarmRepository;


    @PostMapping("/alarms")
    public com.example.mesbackend.entity.Alarm createAlarm(@RequestBody com.example.mesbackend.entity.Alarm newAlarm)
    {
        if (newAlarm.getStartTime() == null) {
            newAlarm.setStartTime(java.time.LocalDateTime.now());
        }

        return alarmRepository.save(newAlarm);
    }
    // 新增一个接口 /api/telemetry
    @GetMapping("/telemetry")
    public List<com.example.mesbackend.entity.Telemetry> getAllTelemetry() {
        return telemetryRepository.findAll();
    }

    @GetMapping("/alarms")
    public List<com.example.mesbackend.entity.Alarm> getAllAlarms(){
        return alarmRepository.findAll();
    }
}
