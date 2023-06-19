package cn.superiormc.dailyreset.database;

import cc.carm.lib.easysql.EasySQL;
import cc.carm.lib.easysql.api.SQLManager;
import cc.carm.lib.easysql.api.action.query.QueryAction;
import cc.carm.lib.easysql.api.enums.IndexType;
import cc.carm.lib.easysql.hikari.HikariConfig;
import cn.superiormc.dailyreset.DailyReset;
import cn.superiormc.dailyreset.configs.Database;
import cn.superiormc.dailyreset.configs.Settings;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;

public class SQLDatabase {

    public static SQLManager sqlManager;

    public static void InitSQL() {
        Bukkit.getConsoleSender().sendMessage("§x§9§8§F§B§9§8[DailyReset] §fTrying connect to SQL database...");
        String jdbcUrl = Database.GetDatabaseUrl();
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(Database.GetDatabaseClass());
        config.setJdbcUrl(jdbcUrl);
        if ((Database.GetDatabaseUser() != null && Database.GetDatabasePassword() != null)) {
            config.setUsername(Database.GetDatabaseUser());
            config.setPassword(Database.GetDatabasePassword());
        }
        sqlManager = EasySQL.createManager(config);
        try {
            if (!sqlManager.getConnection().isValid(5)) {
                Bukkit.getConsoleSender().sendMessage("§x§9§8§F§B§9§8[DailyReset] §cFailed connect to SQL database!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        CreateTable();
    }

    public static void CloseSQL() {
        if (Objects.nonNull(sqlManager)) {
            EasySQL.shutdownManager(sqlManager);
            sqlManager = null;
        }
    }

    public static void CreateTable() {
        sqlManager.createTable("dailyreset")
                .addColumn("uuid", "VARCHAR(36)")
                .addColumn("year", "INT")
                .addColumn("month", "INT")
                .addColumn("day", "INT")
                .addColumn("hour", "INT")
                .addColumn("minute", "INT")
                .addColumn("second", "INT")
                .setIndex(IndexType.PRIMARY_KEY, null, "uuid")
                .build().execute(null);
    }

    public static void InsertData(Player player) {
        if (DailyReset.dataMap.containsKey(player)) {
            return;
        }
        ZonedDateTime dateTime = Instant.now().atZone(ZoneId.of(Settings.GetTimeZone())).plusDays(1L).
                withHour(Settings.GetResetHour()).
                withMinute(Settings.GetResetMinute()).
                withSecond(Settings.GetResetSecond());
        sqlManager.createInsert("dailyreset")
                .setColumnNames(player.getUniqueId().toString(), "year", "month", "day", "hour", "minute", "second")
                .setParams(player.getUniqueId().toString(), dateTime.getYear(), dateTime.getMonthValue(), dateTime.getDayOfMonth(),
                        dateTime.getHour(), dateTime.getMinute(), dateTime.getSecond())
                .executeAsync();
        QueryData(player);
    }

    public static void QueryData(Player player) {
        QueryAction queryAction = sqlManager.createQuery()
                .inTable("dailyreset")
                .selectColumns("year", "month", "day", "hour", "minute", "second")
                .addCondition("uuid = '" + player.getUniqueId().toString() + "'")
                .build();
        queryAction.executeAsync((result) ->
        {
            if (result.getResultSet().next()) {
                ZonedDateTime dateTime = ZonedDateTime.of(result.getResultSet().getInt("year"),
                        result.getResultSet().getInt("month"),
                        result.getResultSet().getInt("day"),
                        result.getResultSet().getInt("hour"),
                        result.getResultSet().getInt("minute"),
                        result.getResultSet().getInt("second"),
                            0,
                        ZoneId.of(Settings.GetTimeZone()));
                if (DailyReset.dataMap.containsKey(player)) {
                    DailyReset.dataMap.replace(player, dateTime);
                }
                else {
                    DailyReset.dataMap.put(player, dateTime);
                }
                return;
            }
                InsertData(player);
        });
    }

    public static void UpdateData(Player player) {
        ZonedDateTime dateTime = DailyReset.dataMap.get(player);
        sqlManager.createUpdate("dailyreset")
                .addCondition("uuid = '"+ player.getUniqueId().toString() +"'")
                .setColumnValues("year", dateTime.getYear())
                .setColumnValues("month", dateTime.getMonthValue())
                .setColumnValues("day", dateTime.getDayOfYear())
                .setColumnValues("hour", dateTime.getHour())
                .setColumnValues("minute", dateTime.getMinute())
                .setColumnValues("second", dateTime.getSecond())
                .build()
                .executeAsync();
    }

}
