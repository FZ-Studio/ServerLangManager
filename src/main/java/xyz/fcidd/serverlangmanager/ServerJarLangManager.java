package xyz.fcidd.serverlangmanager;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Scanner;
import java.util.jar.JarFile;
import java.util.jar.JarEntry;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ServerJarLangManager extends ServerLangManager {
    /**
     * 新建语言管理器
     * 
     * @param langFolderInJar 储存lang文件的目录的路径
     * @param langID          语言id，与文件名相同，无".json"后缀
     * @throws IOException              出现I/O错误时抛出
     * @throws IllegalArgumentException 语言文件不是json格式时抛出
     */
    ServerJarLangManager(String langFileInJar) throws IOException, IllegalArgumentException {
        if (!langFileInJar.startsWith("/")) {
            langFileInJar = "/" + langFileInJar;
        }
        if (!langFileInJar.endsWith(".json")) {
            throw new IllegalArgumentException("not a json file!");
        }
        Gson gson = new Gson();
        InputStream in = this.getClass().getResourceAsStream(langFileInJar);
        Scanner scanner = new Scanner(in, StandardCharsets.UTF_8);
        String json = scanner.useDelimiter("\\A").next();
        scanner.close();
        currentLangMap = gson.fromJson(json, new TypeToken<HashMap<String, String>>() {
        }.getType());
        if (currentLangMap.isEmpty()) {
            throw new IllegalArgumentException("invalid lang file!");
        }

        //从jar中加载语言文件
        langList = new ArrayList<>();
        String langPath = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        System.out.println(langPath);
        if (langPath.startsWith("/")) {
            langPath = langPath.substring(1, langPath.length());
        }
        langPath = URLDecoder.decode(langPath, "UTF-8");
        try (JarFile langFiles = new JarFile(langPath)) {
            Enumeration<JarEntry> enume = langFiles.entries();
            while (enume.hasMoreElements()) {
                String langFileString = enume.nextElement().getName();
                if (langFileString.startsWith(langFileInJar.substring(1, langFileInJar.lastIndexOf("/")))
                        && langFileString.endsWith(".json")) {
                    // 获取语言id
                    langList.add(langFileString.substring(langFileString.lastIndexOf("/") + 1,
                            langFileString.lastIndexOf(".")));
                }
            }
        }
    }
}
