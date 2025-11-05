package com.example.cobblemonAXExtraslot.util;

import com.example.cobblemonAXExtraslot.config.MainConfig;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * 受配置 debug 开关控制的日志工具
 * 当 config.yml 中 debug: true 时才输出日志
 */
public final class DebugLogger {

    private DebugLogger() {}

    private static boolean isDebugEnabled() {
        return MainConfig.INSTANCE != null && MainConfig.INSTANCE.getBoolean("debug", false);
    }

    public static void info(JavaPlugin plugin, String message) {
        if (isDebugEnabled()) {
            plugin.getLogger().info(message);
        }
    }

    public static void warning(JavaPlugin plugin, String message) {
        if (isDebugEnabled()) {
            plugin.getLogger().warning(message);
        }
    }

    public static void severe(JavaPlugin plugin, String message) {
        if (isDebugEnabled()) {
            plugin.getLogger().severe(message);
        }
    }

    public static void printStackTraceIfDebug(Throwable t) {
        if (isDebugEnabled() && t != null) {
            t.printStackTrace();
        }
    }
}