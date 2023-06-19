package cn.superiormc.dailyreset.events;

import cn.superiormc.dailyreset.database.SQLDatabase;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitEvent implements Listener {
    @EventHandler
    public void JoinEvent(PlayerQuitEvent event) {
        SQLDatabase.UpdateData(event.getPlayer());
    }
}
