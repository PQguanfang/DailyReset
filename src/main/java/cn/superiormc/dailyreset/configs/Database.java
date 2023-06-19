package cn.superiormc.dailyreset.configs;

import cn.superiormc.dailyreset.DailyReset;

public class Database {
    public static String GetDatabaseUrl() {
        return DailyReset.instance.getConfig().getString("database.jdbc-url").replace("%plugin_folder%", String.valueOf(DailyReset.instance.getDataFolder()));
    }
    public static String GetDatabaseClass() {
        return DailyReset.instance.getConfig().getString("database.jdbc-class");
    }
    public static String GetDatabaseUser() {
        return DailyReset.instance.getConfig().getString("database.properties.user", null);
    }
    public static String GetDatabasePassword() {
        return DailyReset.instance.getConfig().getString("database.properties.password", null);
    }
}
