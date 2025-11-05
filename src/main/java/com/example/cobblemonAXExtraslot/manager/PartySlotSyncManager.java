package com.example.cobblemonAXExtraslot.manager;

import com.example.cobblemonAXExtraslot.CobblemonAXExtraslot;
import com.example.cobblemonAXExtraslot.service.PartySlotSyncService;
import com.example.cobblemonAXExtraslot.task.PartySlotSyncTask;
import com.example.cobblemonAXExtraslot.util.DebugLogger;
import com.example.cobblemonAXExtraslot.config.MainConfig;
import lombok.Getter;
import org.bukkit.scheduler.BukkitTask;

/**
 * 宝可梦队伍槽位同步管理器
 * 负责管理队伍槽位同步任务的启动、停止和状态监控
 */
public class PartySlotSyncManager {
    
    private final CobblemonAXExtraslot plugin;

    @Getter
    private final PartySlotSyncService syncService;

    private BukkitTask syncTask;

    private boolean isRunning = false;
    
    /**
     * 构造函数
     * @param plugin 插件实例
     */
    public PartySlotSyncManager(CobblemonAXExtraslot plugin) {
        this.plugin = plugin;
        this.syncService = new PartySlotSyncService(plugin);
    }

    /**
     * 启动宝可梦队伍槽位同步任务
     * @return 是否启动成功
     */
    public boolean start() {
        if (isRunning) {
            DebugLogger.warning(plugin, "队伍同步任务已经在运行中");
            return false;
        }
        
        try {
            PartySlotSyncTask task = new PartySlotSyncTask(syncService);
            long periodTicks = Math.max(1, MainConfig.INSTANCE.getSlotSyncTimeSeconds()) * 20L;
            syncTask = task.runTaskTimer(plugin, periodTicks, periodTicks);
            isRunning = true;

            return true;
            
        } catch (Exception e) {
            DebugLogger.severe(plugin, "启动队伍同步任务失败: " + e.getMessage());
            DebugLogger.printStackTraceIfDebug(e);
            return false;
        }
    }
    
    /**
     * 停止宝可梦队伍槽位同步任务
     * @return 是否停止成功
     */
    public boolean stop() {
        if (!isRunning) {
            DebugLogger.info(plugin, "队伍同步任务未在运行");
            return true;
        }
        
        try {
            if (syncTask != null && !syncTask.isCancelled()) {
                syncTask.cancel();
                syncTask = null;
            }
            isRunning = false;
            
            DebugLogger.info(plugin, "宝可梦队伍槽位同步任务已停止");
            return true;
            
        } catch (Exception e) {
            DebugLogger.warning(plugin, "停止队伍同步任务时发生错误: " + e.getMessage());
            DebugLogger.printStackTraceIfDebug(e);
            return false;
        }
    }
    
    /**
     * 重启同步任务
     * @return 是否重启成功
     */
    public boolean restart() {
        DebugLogger.info(plugin, "正在重启队伍同步任务...");
        stop();
        return start();
    }
    
    /**
     * 检查任务是否正在运行
     * @return 任务运行状态
     */
    public boolean isRunning() {
        return isRunning && syncTask != null && !syncTask.isCancelled();
    }
    
    /**
     * 获取任务状态信息
     * @return 状态信息字符串
     */
    public String getStatusInfo() {
        if (isRunning()) {
            return "§a运行中 §7(任务ID: " + syncTask.getTaskId() + ")";
        } else {
            return "§c已停止";
        }
    }
    
    /**
     * 强制停止任务（用于插件关闭时的清理）
     */
    public void forceStop() {
        try {
            if (syncTask != null) {
                syncTask.cancel();
                syncTask = null;
            }
            isRunning = false;
        } catch (Exception e) {
            DebugLogger.warning(plugin, "强制停止队伍同步任务时发生错误: " + e.getMessage());
            DebugLogger.printStackTraceIfDebug(e);
        }
    }

}