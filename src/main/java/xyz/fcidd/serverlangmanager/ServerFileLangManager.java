package xyz.fcidd.serverlangmanager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
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
        if (!langFile.getName().endsWith(".json") || langFile.isDirectory()) {
            throw new IllegalArgumentException("not a json file！");
        }
        Gson gson = new Gson();
        FileInputStream in = new FileInputStream(langFile);
        Scanner scanner = new Scanner(in, "UTF-8");
        String json = scanner.useDelimiter("\\A").next();
        scanner.close();
        currentLangMap = gson.fromJson(json, new TypeToken<HashMap<String, String>>() {
        }.getType());

        // 从文件中加载语言文件
        langsList = new ArrayList<>();
        File langPath = langFile.getParentFile();
        File[] langFiles = langPath.listFiles();
        for (File file : langFiles) {
            String fileName = file.getName();
            langsList.add(fileName.substring(0, fileName.lastIndexOf(".")));
        }
    }
}
