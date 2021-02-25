package xyz.fcidd.serverlangmanager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ServerFileLangManager extends ServerLangManager {
    /**
     * 新建语言管理器
     * 
     * @param langFile 语言文件
     * @throws FileNotFoundException    找不到语言文件时抛出
     * @throws IllegalArgumentException 语言文件不是json格式时抛出
     */
    ServerFileLangManager(File langFile) throws FileNotFoundException, IllegalArgumentException {
        if (!langFile.getName().endsWith(".json") || !langFile.isFile()) {
            throw new IllegalArgumentException("not a json file！");
        }
        Gson gson = new Gson();
        FileInputStream in = new FileInputStream(langFile);
        Scanner scanner = new Scanner(in, StandardCharsets.UTF_8);
        String json = scanner.useDelimiter("\\A").next();
        scanner.close();
        currentLangMap = gson.fromJson(json, new TypeToken<HashMap<String, String>>() {
        }.getType());

        // 从文件中加载语言文件
        langList = getLangListFromPath(langFile.getParentFile());
    }
    
    /**
     * 获取json文件不带后缀的文件名列表
     * 
     * @param path 要查找的路径
     * @return json文件不带后缀的文件名列表
     * @throws IllegalArgumentException 当参数不是一个文件夹路径时抛出
     */
    public static List<String> getLangListFromPath(File path) throws IllegalArgumentException {
        if (!path.isDirectory()) {
            throw new IllegalArgumentException("not a folder!");
        }
        List<String> langList = new ArrayList<>();
        File[] langFiles = path.listFiles();
        for (File file : langFiles) {
            String fileName = file.getName();
            if (fileName.endsWith(".json")) {
                langList.add(fileName.substring(0, fileName.lastIndexOf(".")));
            }
        }
        return langList;
    }
}
