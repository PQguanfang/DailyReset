package cn.superiormc.dailyreset.utils;

import cn.superiormc.dailyreset.DailyReset;

public class CheckPluginLoad {
    public static boolean DoIt(String pluginName){
        return DailyReset.instance.getServer().getPluginManager().isPluginEnabled(pluginName);
    }
}
