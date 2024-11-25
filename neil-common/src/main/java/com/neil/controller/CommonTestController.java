package com.neil.controller;

import com.neil.utils.Tools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.FileReader;

/**
 * @Decription
 * @Author NEIL
 * @Date 2023/7/25 9:44
 * @Version 1.0
 */
@Slf4j
@Controller
@RequestMapping("/test")
public class CommonTestController {

    @GetMapping(value = "/javac")
    @ResponseBody
    public boolean javac() {
        log.info("java.class.path：" + System.getProperty("java.class.path"));
        log.info("java.ext.dirs：" + System.getProperty("java.ext.dirs"));
        //System.setProperty("java.ext.dirs", System.getProperty("java.class.path") + ";C:\\Users\\NEIL\\.m2\\repository\\org\\apache\\commons\\commons-lang3\\3.8.1\\commons-lang3-3.8.1.jar");
        System.setProperty("java.ext.dirs", System.getProperty("java.ext.dirs") + ":C:\\Users\\NEIL\\.m2\\repository\\org\\apache\\commons\\commons-lang3\\3.8.1");

        String name = "DynamicJava";
        StringBuffer body = new StringBuffer();
        try(FileReader file = new FileReader("C:\\Tools\\IJetBrains\\IntelliJ IDEA 2018.3.5\\Projects\\neil-learning\\neil-common\\src\\main\\resources\\files\\DynamicJava.java")) {
            int len = 0;
            while ((len = file.read()) != -1) {
                body.append((char) len);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        Boolean codeJavac = Tools.codeJavac(name, body.toString());
        return codeJavac.booleanValue();
    }
}
