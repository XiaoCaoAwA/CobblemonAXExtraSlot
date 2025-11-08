package com.example.cobblemonAXExtraslot;

import com.example.cobblemonAXExtraslot.command.MainCommand;
import com.example.cobblemonAXExtraslot.config.MainConfig;
import com.example.cobblemonAXExtraslot.listener.ExtraSlotClickListener;
import com.example.cobblemonAXExtraslot.listener.PlayerJoinListener;
import com.example.cobblemonAXExtraslot.manager.PartySlotSyncManager;
import xiaocaoawa.miencraft.plugin.xccore.util.CobblemonUtil.CobblemonPartyUtil;
import com.example.cobblemonAXExtraslot.util.DebugLogger;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class CobblemonAXExtraslot extends JavaPlugin {

    @Getter
    private static CobblemonAXExtraslot instance;

    private MainCommand mainCommand;
    @Getter
    private PartySlotSyncManager syncManager;
    private PlayerJoinListener playerJoinListener;
    private ExtraSlotClickListener extraSlotClickListener;

    @Override
    public void onEnable() {

        instance = this;

        MainConfig.initialize(this);
        MainConfig.INSTANCE.load();

        initializeCobblemonUtils();

        registerCommands();

        initializeSyncManager();

        registerEventListeners();

        startPartySlotSyncTask();
        
        DebugLogger.info(this, "CobblemonAXExtraslot 插件启动完成！");
    }

    @Override
    public void onDisable() {
        // 停止同步任务
        stopPartySlotSyncTask();

        DebugLogger.info(this, "CobblemonAXExtraslot 插件已关闭！");
    }

    /**
     * 初始化Cobblemon相关工具
     */
    private void initializeCobblemonUtils() {
        try {
            CobblemonPartyUtil.setInitialized();
            DebugLogger.info(this, "Cobblemon工具初始化成功");
        } catch (Exception e) {
            DebugLogger.warning(this, "Cobblemon工具初始化失败: " + e.getMessage());
            DebugLogger.printStackTraceIfDebug(e);
        }
    }

    /**
     * 注册命令
     */
    private void registerCommands() {
        try {
            mainCommand = new MainCommand();
            getCommand("cae").setExecutor(mainCommand);
            getCommand("cae").setTabCompleter(mainCommand);
            
            DebugLogger.info(this, "命令注册成功");
        } catch (Exception e) {
            DebugLogger.severe(this, "命令注册失败: " + e.getMessage());
            DebugLogger.printStackTraceIfDebug(e);
        }
    }
    
    /**
     * 注册事件监听器
     */
    private void registerEventListeners() {
        try {
            playerJoinListener = new PlayerJoinListener(this, syncManager.getSyncService());
            getServer().getPluginManager().registerEvents(playerJoinListener, this);
            
            extraSlotClickListener = new ExtraSlotClickListener();
            getServer().getPluginManager().registerEvents(extraSlotClickListener, this);
            
            DebugLogger.info(this, "事件监听器注册成功");
        } catch (Exception e) {
            DebugLogger.severe(this, "事件监听器注册失败: " + e.getMessage());
            DebugLogger.printStackTraceIfDebug(e);
        }
    }

    /**
     * 初始化同步管理器
     */
    private void initializeSyncManager() {
        try {
            syncManager = new PartySlotSyncManager(this);
            DebugLogger.info(this, "队伍同步管理器初始化成功");
        } catch (Exception e) {
            DebugLogger.severe(this, "队伍同步管理器初始化失败: " + e.getMessage());
            DebugLogger.printStackTraceIfDebug(e);
        }
    }

    /**
     * 启动宝可梦队伍槽位同步任务
     */
    private void startPartySlotSyncTask() {
        if (syncManager != null) {
            syncManager.start();
        } else {
            DebugLogger.warning(this, "同步管理器未初始化，无法启动队伍同步任务");
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
