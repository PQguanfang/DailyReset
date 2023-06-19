package cn.superiormc.dailyreset.tasks;

import cn.superiormc.dailyreset.DailyReset;
import cn.superiormc.dailyreset.configs.Action;
import cn.superiormc.dailyreset.configs.Settings;
import cn.superiormc.dailyreset.utils.Actions;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ResetTask {
    public void StartTask(){
        boolean firstDone = false;
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (GetSQLTime(player).isBefore(GetNowingTime())) {
                if (!firstDone) {
                    Actions.DoIt(Action.GetConsoleActions());
                }
                DailyReset.dataMap.replace(player, DailyReset.dataMap.get(player).plusDays(1L).
                        withHour(Settings.GetResetHour()).
                        withMinute(Settings.GetResetMinute()).
                        withSecond(Settings.GetResetSecond()));
                firstDone = true;
            }
            Actions.DoIt(Action.GetPerPlayerActions(), player);
        }
    }

    // 获取执行该方法时的时间
    // 两个时间，一个是现在时间，另外一个是 SQL 的时间
    // 玩家每天进服都会有一个 SQL 的时间
    // 第二天时现在时间就会晚于 SQL 的时间
    public static ZonedDateTime GetNowingTime() {
        return Instant.now().atZone(ZoneId.of(Settings.GetTimeZone()));
    }

    public static ZonedDateTime GetSQLTime(Player player) {
        return DailyReset.dataMap.get(player);
    }
}
