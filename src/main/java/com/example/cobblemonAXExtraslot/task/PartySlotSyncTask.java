package com.example.cobblemonAXExtraslot.task;

import com.example.cobblemonAXExtraslot.service.PartySlotSyncService;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * 宝可梦队伍槽位同步任务
 * 每1秒将玩家队伍中的宝可梦同步到对应的额外槽位中
 */
public class PartySlotSyncTask extends BukkitRunnable {
    
    private final PartySlotSyncService syncService;
    
    public PartySlotSyncTask(PartySlotSyncService syncService) {
        this.syncService = syncService;
    }
    
    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            syncService.syncPlayerPartySilently(player);
        }
    }
}