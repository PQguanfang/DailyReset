package cn.superiormc.dailyreset.configs;

import cn.superiormc.dailyreset.utils.ColorParser;

public class Messages {

    public static String GetActionMessages(String textName){
        textName = ColorParser.parse(textName);
        return textName;
    }
}
