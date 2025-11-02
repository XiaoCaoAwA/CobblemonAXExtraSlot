package com.example.cobblemonAXExtraslot.manager;

import com.example.cobblemonAXExtraslot.CobblemonAXExtraslot;
import com.example.cobblemonAXExtraslot.service.PartySlotSyncService;
import com.example.cobblemonAXExtraslot.task.PartySlotSyncTask;
import org.bukkit.scheduler.BukkitTask;
import priv.seventeen.artist.arcartx.database.slot.SlotDatabase;

/**
 * 宝可梦队伍槽位同步管理器
 * 负责管理队伍槽位同步任务的启动、停止和状态监控
 */
public class PartySlotSyncManager {
    
    private final CobblemonAXExtraslot plugin;
    private final PartySlotSyncService syncService;
    private BukkitTask syncTask;
    private boolean isRunning = false;
    
    /**
     * 构造函数
     * @param plugin 插件实例
     * @param slotDatabase 槽位数据库
     */
    public PartySlotSyncManager(CobblemonAXExtraslot plugin, SlotDatabase slotDatabase) {
        this.plugin = plugin;
        this.syncService = new PartySlotSyncService(slotDatabase, plugin);
    }
    
    /**
     * 获取同步服务实例
     * @return PartySlotSyncService 实例
     */
    public PartySlotSyncService getSyncService() {
        return syncService;
    }
    
    /**
     * 启动宝可梦队伍槽位同步任务
     * @return 是否启动成功
     */
    public boolean start() {
        if (isRunning) {
            plugin.getLogger().warning("队伍同步任务已经在运行中");
            return false;
        }
        
        try {
            PartySlotSyncTask task = new PartySlotSyncTask(syncService);
            syncTask = task.runTaskTimer(plugin, 20L, 20L);
            isRunning = true;

            return true;
            
        } catch (Exception e) {
            plugin.getLogger().severe("启动队伍同步任务失败: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 停止宝可梦队伍槽位同步任务
     * @return 是否停止成功
     */
    public boolean stop() {
        if (!isRunning) {
            plugin.getLogger().info("队伍同步任务未在运行");
            return true;
        }
        
        try {
            if (syncTask != null && !syncTask.isCancelled()) {
                syncTask.cancel();
                syncTask = null;
            }
            isRunning = false;
            
            plugin.getLogger().info("宝可梦队伍槽位同步任务已停止");
            return true;
            
        } catch (Exception e) {
            plugin.getLogger().warning("停止队伍同步任务时发生错误: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 重启同步任务
     * @return 是否重启成功
     */
    public boolean restart() {
        plugin.getLogger().info("正在重启队伍同步任务...");
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
            plugin.getLogger().warning("强制停止队伍同步任务时发生错误: " + e.getMessage());
        }
    }
}