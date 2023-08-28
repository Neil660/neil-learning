package com.neil.utils;

import lombok.extern.slf4j.Slf4j;

import javax.tools.Diagnostic;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.Closeable;
import java.io.StringWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @Decription
 * @Author NEIL
 * @Date 2023/3/2 16:17
 * @Version 1.0
 */
@Slf4j
public class Tools {
    private static final Random rnd = new Random();

    /**
     * 关闭流不报异常
     * @param closeable
     */
    public static void silentClose(Closeable... closeable) {
        if (null == closeable) {
            return;
        }
        for (Closeable c : closeable) {
            if (null == c) {
                continue;
            }
            try {
                c.close();
            }
            catch (Exception ignored) {
                log.warn(ignored.getMessage(), ignored);
            }
        }
    }

    public static void sleep(long time) {
        try {
            Thread.sleep(time);
        }
        catch (InterruptedException e) {
            log.warn(e.getMessage(), e);
        }
    }

    public static Boolean codeJavac(String componentName, String componentBody) {
        //需要进行编译的代码
        Iterable<? extends JavaFileObject> compilationUnits = new ArrayList<JavaFileObject>() {{
            add(new JavaSourceFromString(componentName, componentBody));
        }};
        //编译的选项，对应于命令行参数
        List<String> options = new ArrayList<>();
        options.add("-d");
        options.add(Paths.get("").toAbsolutePath().toString());
        options.add("-classpath");
        options.add(System.getProperty("java.class.path"));
        //使用系统的编译器
        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager standardJavaFileManager = javaCompiler.getStandardFileManager(null, null, null);
        ScriptFileManager scriptFileManager = new ScriptFileManager(standardJavaFileManager);
        //使用stringWriter来收集错误。
        StringWriter errorStringWriter = new StringWriter();
        //开始进行编译
        boolean ok = javaCompiler.getTask(errorStringWriter, scriptFileManager, diagnostic -> {
            if (diagnostic.getKind() == Diagnostic.Kind.ERROR) {
                errorStringWriter.append(diagnostic.toString());
            }
        }, options, null, compilationUnits).call();
        if (!ok) {
            String errorMessage = errorStringWriter.toString();
            log.error("Compile Error:{}" + errorMessage);
        }

        return ok;
    }
}
