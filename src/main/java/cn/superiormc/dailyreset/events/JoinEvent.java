package cn.superiormc.dailyreset.events;

import cn.superiormc.dailyreset.DailyReset;
import cn.superiormc.dailyreset.configs.Settings;
import cn.superiormc.dailyreset.database.SQLDatabase;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {
    @EventHandler
    public void JoinEvent(PlayerJoinEvent event) {
        Bukkit.getScheduler().runTaskLater(DailyReset.instance, () -> {
            SQLDatabase.QueryData(event.getPlayer());
        }, Settings.GetPeriodTime());
    }
}
