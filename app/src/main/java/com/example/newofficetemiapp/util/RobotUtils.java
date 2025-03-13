package com.example.newofficetemiapp.util;

import android.util.Log;

import com.robotemi.sdk.Robot;

/**
 * Temi 로봇과 관련된 유틸리티 메서드들을 제공합니다.
 */
public class RobotUtils {
    private static final String TAG = "RobotUtils";

    private RobotUtils() {
        // 인스턴스화 방지
    }

    /**
     * 현재 Temi 로봇의 ID를 반환합니다.
     *
     * @return Temi 로봇 시리얼 번호
     */
    public static String getTemiId() {
        return Robot.getInstance().getSerialNumber();
    }

    /**
     * 현재 Temi가 TEMI1인지 확인합니다.
     *
     * @return TEMI1인 경우 true, 아니면 false
     */
    public static boolean isTemi1() {
        return Constants.TEMI1.equals(getTemiId());
    }

    /**
     * 현재 Temi가 TEMI2인지 확인합니다.
     *
     * @return TEMI2인 경우 true, 아니면 false
     */
    public static boolean isTemi2() {
        return Constants.TEMI2.equals(getTemiId());
    }

    /**
     * 시리얼 번호 기반으로 Temi 번호를 반환합니다.
     *
     * @param serialNumber Temi 시리얼 번호
     * @return "Temi1", "Temi2" 또는 시리얼 번호를 모르는 경우 "Unknown"
     */
    public static String getTemiName(String serialNumber) {
        if (Constants.TEMI1.equals(serialNumber)) {
            return "Temi1";
        } else if (Constants.TEMI2.equals(serialNumber)) {
            return "Temi2";
        } else {
            return "Unknown";
        }
    }

    /**
     * 현재 Temi가 특정 위치로 이동 가능한지 확인합니다.
     *
     * @param location 이동하려는 위치
     * @return 이동 가능하면 true, 아니면 false
     */
    public static boolean canGoToLocation(String location) {
        if (location == null || location.isEmpty()) {
            return false;
        }

        // TEMI1은 ExecutiveTeam과 EditorialTeam으로만 이동 가능
        if (isTemi1() && (Constants.EXECUTIVE_TEAM.equals(location) ||
                Constants.EDITORIAL_TEAM.equals(location))) {
            return true;
        }

        // TEMI2는 PlanningTeam과 MeetingRoom으로만 이동 가능
        if (isTemi2() && (Constants.PLANNING_TEAM.equals(location) ||
                Constants.MEETING_ROOM.equals(location))) {
            return true;
        }

        // 공통 위치 (홈베이스 등)는 양쪽 Temi 모두 이동 가능
        if ("홈베이스".equals(location) || "prob11".equals(location) ||
                "prob12".equals(location) || "prob21".equals(location) ||
                "prob22".equals(location)) {
            return true;
        }

        Log.w(TAG, "현재 Temi는 " + location + "으로 이동할 수 없습니다.");
        return false;
    }

    /**
     * 현재 배터리 상태를 확인하고 배터리가 낮으면 로그에 기록합니다.
     *
     * @return 배터리가 충분하면 true, 낮으면 false
     */
    public static boolean checkBatteryStatus() {
        com.robotemi.sdk.BatteryData batteryData = Robot.getInstance().getBatteryData();
        int batteryPercentage = batteryData.getBatteryPercentage();

        if (batteryPercentage < 20) {
            Log.w(TAG, "배터리가 낮습니다: " + batteryPercentage + "%");
            return false;
        }

        return true;
    }
}