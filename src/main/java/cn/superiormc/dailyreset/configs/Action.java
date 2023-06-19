package cn.superiormc.dailyreset.configs;

import cn.superiormc.dailyreset.DailyReset;

import java.util.List;

public class Action {
    public static List<String> GetPerPlayerActions() {
        return DailyReset.instance.getConfig().getStringList("per-player-actions");
    }

    public static List<String> GetConsoleActions() {
        return DailyReset.instance.getConfig().getStringList("console-actions");
    }
}
