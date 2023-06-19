package cn.superiormc.dailyreset.configs;

import cn.superiormc.dailyreset.DailyReset;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class Settings {
    public static String GetTimeZone() {
        return DailyReset.instance.getConfig().getString("settings.time-zone");
    }

    public static int GetResetHour() {
        return DailyReset.instance.getConfig().getInt("settings.reset-time.hour");
    }

    public static int GetResetMinute() {
        return DailyReset.instance.getConfig().getInt("settings.reset-time.minute");
    }

    public static int GetResetSecond() {
        return DailyReset.instance.getConfig().getInt("settings.reset-time.second");
    }

    public static long GetPeriodTime() {
        return DailyReset.instance.getConfig().getLong("settings.period-time");
    }

    public static boolean GetSaveEnabled() {
        return DailyReset.instance.getConfig().getBoolean("settings.auto-save");
    }
}
