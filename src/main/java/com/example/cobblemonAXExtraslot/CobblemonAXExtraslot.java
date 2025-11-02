package com.example.cobblemonAXExtraslot;

import com.example.cobblemonAXExtraslot.command.MainCommand;
import com.example.cobblemonAXExtraslot.config.DatabaseConfig;
import com.example.cobblemonAXExtraslot.config.MainConfig;
import com.example.cobblemonAXExtraslot.listener.PlayerJoinListener;
import com.example.cobblemonAXExtraslot.manager.PartySlotSyncManager;
import xiaocaoawa.miencraft.plugin.xccore.util.CobblemonUtil.CobblemonPartyUtil;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import priv.seventeen.artist.arcartx.core.config.setting.Database;
import priv.seventeen.artist.arcartx.database.DatabaseManager;
import priv.seventeen.artist.arcartx.database.slot.SlotDatabase;

public final class CobblemonAXExtraslot extends JavaPlugin {

    @Getter
    private static CobblemonAXExtraslot instance;

    private MainCommand mainCommand;
    private DatabaseManager databaseManager;
    @Getter
    private SlotDatabase slotDatabase;
    @Getter
    private PartySlotSyncManager syncManager;
    private PlayerJoinListener playerJoinListener;

    @Override
    public void onEnable() {

        instance = this;

        MainConfig.initialize(this);
        MainConfig.INSTANCE.load();

        initializeDatabase();

        initializeCobblemonUtils();

        registerCommands();

        initializeSyncManager();

        registerEventListeners();

        startPartySlotSyncTask();
        
        getLogger().info("CobblemonAXExtraslot 插件启动完成！");
    }

    @Override
    public void onDisable() {

        stopPartySlotSyncTask();

        if (databaseManager != null) {
            databaseManager.close();
            getLogger().info("数据库连接已关闭");
        }
    }

    /**
     * 初始化数据库
     */
    private void initializeDatabase() {
        try {
            Database sqlConfig = DatabaseConfig.createFromConfig(this, MainConfig.INSTANCE);
            
            if (sqlConfig == null) {
                getLogger().severe("数据库配置创建失败");
                return;
            }

            databaseManager = new DatabaseManager(sqlConfig);
            slotDatabase = databaseManager.getSlotDatabase();
            
            if (slotDatabase == null) {
                getLogger().severe("SlotDatabase 初始化失败");
                return;
            }
            
            getLogger().info("SQLite 数据库初始化成功！");
            
        } catch (Exception e) {
            getLogger().severe("数据库初始化失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 初始化Cobblemon相关工具
     */
    private void initializeCobblemonUtils() {
        try {
            CobblemonPartyUtil.setInitialized();
            getLogger().info("Cobblemon工具初始化成功");
        } catch (Exception e) {
            getLogger().warning("Cobblemon工具初始化失败: " + e.getMessage());
        }
    }

    /**
     * 注册命令
     */
    private void registerCommands() {
        try {
            mainCommand = new MainCommand(slotDatabase);
            getCommand("cae").setExecutor(mainCommand);
            getCommand("cae").setTabCompleter(mainCommand);
            
            getLogger().info("命令注册成功");
        } catch (Exception e) {
            getLogger().severe("命令注册失败: " + e.getMessage());
        }
    }
    
    /**
     * 注册事件监听器
     */
    private void registerEventListeners() {
        try {
            playerJoinListener = new PlayerJoinListener(this, syncManager.getSyncService());
            getServer().getPluginManager().registerEvents(playerJoinListener, this);
            
            getLogger().info("事件监听器注册成功");
        } catch (Exception e) {
            getLogger().severe("事件监听器注册失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 初始化同步管理器
     */
    private void initializeSyncManager() {
        try {
            syncManager = new PartySlotSyncManager(this, slotDatabase);
            getLogger().info("队伍同步管理器初始化成功");
        } catch (Exception e) {
            getLogger().severe("队伍同步管理器初始化失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 启动宝可梦队伍槽位同步任务
     */
    private void startPartySlotSyncTask() {
        if (syncManager != null) {
            syncManager.start();
        } else {
            getLogger().warning("同步管理器未初始化，无法启动队伍同步任务");
        }
    }
    
    /**
     * 停止宝可梦队伍槽位同步任务
     */
    private void stopPartySlotSyncTask() {
        if (syncManager != null) {
            syncManager.forceStop();
        }
    }
}
