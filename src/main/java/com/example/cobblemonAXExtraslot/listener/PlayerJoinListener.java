package com.example.cobblemonAXExtraslot.listener;

import com.example.cobblemonAXExtraslot.CobblemonAXExtraslot;
import com.example.cobblemonAXExtraslot.service.PartySlotSyncService;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * 玩家加入事件监听器
 * 当玩家进入服务器时，同步其宝可梦队伍到额外槽位
 */
public class PlayerJoinListener implements Listener {
    
    private final CobblemonAXExtraslot plugin;
    private final PartySlotSyncService syncService;
    
    public PlayerJoinListener(CobblemonAXExtraslot plugin, PartySlotSyncService syncService) {
        this.plugin = plugin;
        this.syncService = syncService;
    }
    
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        
        boolean success = syncService.syncPlayerPartySilently(player);
    }
}