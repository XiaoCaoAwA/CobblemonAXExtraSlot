package com.example.cobblemonAXExtraslot.config;

import org.bukkit.plugin.Plugin;
import priv.seventeen.artist.arcartx.core.config.setting.Database;

import java.io.File;

/**
 * 数据库配置管理类
 * 负责创建和管理数据库配置
 */
public class DatabaseConfig {
    
    /**
     * 创建 SQLite 数据库配置（使用默认文件名）
     * 
     * @param plugin 插件实例
     * @return Database 配置对象
     */
    public static Database createSQLiteConfig(Plugin plugin) {
        return createSQLiteConfig(plugin, "cobblemon_extraslot.db");
    }
    
    /**
     * 创建 SQLite 数据库配置（使用自定义文件名）
     * 
     * @param plugin 插件实例
     * @param filename SQLite 数据库文件名
     * @return Database 配置对象
     */
    public static Database createSQLiteConfig(Plugin plugin, String filename) {

        File dataFolder = plugin.getDataFolder();
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        File dbFile = new File(dataFolder, filename);
        String dbPath = dbFile.getAbsolutePath();
        Database database = new Database();
        
        return database;
    }
    
    /**
     * 从主配置文件创建数据库配置
     * 
     * @param plugin 插件实例
     * @param mainConfig 主配置对象
     * @return Database 配置对象
     */
    public static Database createFromConfig(Plugin plugin, MainConfig mainConfig) {
        String type = mainConfig.getString("database.type", "sqlite").toLowerCase();

        String sqliteFilename = mainConfig.getString("database.sqlite.filename", "cobblemon_extraslot.db");
        return createSQLiteConfig(plugin, sqliteFilename);
    }
}