package cn.superiormc.dailyreset;

import cn.superiormc.dailyreset.configs.Settings;
import cn.superiormc.dailyreset.database.SQLDatabase;
import cn.superiormc.dailyreset.events.JoinEvent;
import cn.superiormc.dailyreset.tasks.ResetTask;
import cn.superiormc.dailyreset.tasks.SaveTask;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.ZonedDateTime;
import java.util.Map;

public final class DailyReset extends JavaPlugin {

    public static JavaPlugin instance;

    public static Map<Player, ZonedDateTime> dataMap;

    @Override
    public void onLoad() {
        saveDefaultConfig();
    }

    @Override
    public void onEnable() {
        instance = this;
        Events();
        Tasks();
        SQLDatabase.InitSQL();
        Bukkit.getConsoleSender().sendMessage("§x§9§8§F§B§9§8[DailyReset] §fPlugin is loaded. Author: PQguanfang.");
    }

    @Override
    public void onDisable() {
        SQLDatabase.CloseSQL();
        Bukkit.getConsoleSender().sendMessage("§x§9§8§F§B§9§8[DailyReset] §fPlugin is disabled. Author: PQguanfang.");
    }

    public void Events() {
        Bukkit.getPluginManager().registerEvents(new JoinEvent(), this);
    }

    public void Tasks() {
        (new BukkitRunnable() {

            @Override
            public void run() {
                ResetTask resetTask = new ResetTask();
                resetTask.StartTask();
                if (Settings.GetSaveEnabled()) {
                    SaveTask saveTask = new SaveTask();
                    saveTask.StartTask();
                }
            }

        }).runTaskTimer(DailyReset.instance, 0L, Settings.GetPeriodTime());
    }
}
