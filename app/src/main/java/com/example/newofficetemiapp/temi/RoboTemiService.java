package com.example.newofficetemiapp.temi;

import android.util.Log;

import com.robotemi.sdk.Robot;
import com.robotemi.sdk.TtsRequest;

public class RoboTemiService {
    private final Robot robot;

    public RoboTemiService() {
        this.robot = Robot.getInstance();
    }

    public Robot getRobot() {
        return robot;
    }

    public void speak(String text) {
        TtsRequest ttsRequest = TtsRequest.create(text.trim(), false);
        robot.speak(ttsRequest);
    }

    public boolean saveLocation(String location) {
        String loc = location.toLowerCase().trim();
        boolean result = robot.saveLocation(loc);
        if (result) {
            speak(loc + " 장소 저장에 성공하였습니다.");
        } else {
            speak(loc + " 장소 저장에 실패하였습니다.");
        }
        return result;
    }

    public void goTo(String destination) {
        for (String location : robot.getLocations()) {
            if (location.equals(destination.toLowerCase().trim())) {
                robot.goTo(destination.toLowerCase().trim());
                return;
            }
        }
        // 위치를 찾지 못한 경우
        Log.e("RoboTemiService", "위치를 찾을 수 없습니다: " + destination);
    }

    public void stopMovement() {
        robot.stopMovement();
        speak("정지하였습니다.");
    }

    public void followMe() {
        robot.beWithMe();
        speak("테미가 따라갑니다.");
    }

    public void callOwner() {
        robot.startTelepresence(robot.getAdminInfo().getName(), robot.getAdminInfo().getUserId());
    }

    public void tiltAngle(int angle, float velocity) {
        robot.tiltAngle(angle, velocity);
    }

    public void turnBy(int angle, float velocity) {
        robot.turnBy(angle, velocity);
    }

    public void tiltBy(int angle, float velocity) {
        robot.tiltBy(angle, velocity);
    }

    public void getBatteryData() {
        com.robotemi.sdk.BatteryData batteryData = robot.getBatteryData();
        if (batteryData.isCharging()) {
            speak(batteryData.getBatteryPercentage() + " percent battery and charging.");
        } else {
            speak(batteryData.getBatteryPercentage() + " percent battery and not charging.");
        }
    }

    public void skidJoy(float v, float a) {
        robot.skidJoy(v, a);
    }

    public void skidJoy(float velocity, int seconds) {
        long t = System.currentTimeMillis();
        long end = t + 1000L * seconds;
        while (System.currentTimeMillis() < end) {
            robot.skidJoy(velocity, 0F);
        }
    }

    public void setPrivacyMode(boolean enabled) {
        robot.setPrivacyMode(enabled);
    }

    public boolean getPrivacyMode() {
        return robot.getPrivacyMode();
    }

    public void setHardButtonsDisabled(boolean disabled) {
        robot.setHardButtonsDisabled(disabled);
    }

    public boolean isHardButtonsDisabled() {
        return robot.isHardButtonsDisabled();
    }
}
