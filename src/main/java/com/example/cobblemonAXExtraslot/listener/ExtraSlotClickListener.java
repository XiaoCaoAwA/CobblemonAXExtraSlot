package com.example.cobblemonAXExtraslot.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import priv.seventeen.artist.arcartx.event.client.ClientExtraSlotClickEvent;

/**
 * 额外槽位点击事件监听器
 * 当左键点击宝可梦队伍槽位时取消事件
 */
public class ExtraSlotClickListener implements Listener {
    
    @EventHandler(priority = EventPriority.NORMAL)
    public void onExtraSlotClick(ClientExtraSlotClickEvent event) {
        String slotID = event.getSlotID();
        int buttonID = event.getButtonID();

        if (buttonID != 0) {
            return;
        }

        if (slotID.equals("cobblemon_party_1") ||
            slotID.equals("cobblemon_party_2") ||
            slotID.equals("cobblemon_party_3") ||
            slotID.equals("cobblemon_party_4") ||
            slotID.equals("cobblemon_party_5") ||
            slotID.equals("cobblemon_party_6")) {

            event.setCancelled(true);
        }
    }
}

