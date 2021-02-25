package xyz.fcidd.serverlangmanager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerLangManager {

    protected List<String> langList;
    protected HashMap<String, String> currentLangMap;

    protected ServerLangManager() {

    }

    /**
     * 获取当前语言的映射表
     * 
     * @return 当前语言的映射表
     */
    public Map<String, String> getCurrentLangMap() {
        return currentLangMap;
    }

    /**
     * 获取当前语言中输入的键对应的翻译
     * 
     * @param key  键
     * @param args 需要向翻译结果中替换的数据，如"%s"、"%d"等
     * @return 返回翻译后的字符串
     */
    public String getTranslated(String key, Object... args) {
        String translated = currentLangMap.get(key);
        return String.format(translated, args);
    }

    /**
     * 获取当前目录下的所有语言文件的无后缀文件名列表(当前目录下的所有语言ID)
     * 
     * @return 当前目录下的所有语言文件的无后缀文件名列表(当前目录下的所有语言ID)
     */
    public List<String> getLangList() {
        return langList;
    }
}
