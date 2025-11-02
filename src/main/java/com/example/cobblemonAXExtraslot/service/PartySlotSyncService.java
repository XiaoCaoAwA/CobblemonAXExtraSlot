package com.example.cobblemonAXExtraslot.service;

import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.example.cobblemonAXExtraslot.CobblemonAXExtraslot;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import priv.seventeen.artist.arcartx.database.slot.SlotDatabase;
import xiaocaoawa.miencraft.plugin.xccore.util.CobblemonUtil.CobblemonPartyUtil;
import xiaocaoawa.miencraft.plugin.xccore.util.CobblemonUtil.CobblemonItemUtil;

/**
 * 宝可梦队伍槽位同步服务
 * 提供统一的宝可梦队伍同步逻辑
 */
public class PartySlotSyncService {
    
    private final SlotDatabase slotDatabase;
    private final CobblemonAXExtraslot plugin;
    
    public PartySlotSyncService(SlotDatabase slotDatabase, CobblemonAXExtraslot plugin) {
        this.slotDatabase = slotDatabase;
        this.plugin = plugin;
    }
    
    /**
     * 同步玩家的宝可梦队伍到额外槽位（静默模式，无详细日志）
     * @param player 玩家
     * @return 是否同步成功
     */
    public boolean syncPlayerPartySilently(Player player) {
        if (slotDatabase == null) {
            return false;
        }
        
        PlayerPartyStore partyStore = CobblemonPartyUtil.getPlayerPartyStore(player);
        if (partyStore == null) {
            return false;
        }
        
        try {
            for (int slotIndex = 0; slotIndex < 6; slotIndex++) {
                Pokemon pokemon = partyStore.get(slotIndex);
                String slotName = "cobblemon_party_" + (slotIndex + 1);
                
                if (pokemon != null) {
                    ItemStack pokemonItem = CobblemonItemUtil.getPokemonItem(pokemon);
                    slotDatabase.setSlotData(player.getUniqueId(), slotName, pokemonItem);
                } else {
                    slotDatabase.setSlotData(player.getUniqueId(), slotName, null);
                }
            }
            return true;
        } catch (Exception e) {
            plugin.getLogger().warning(
                "同步玩家 " + player.getName() + " 的队伍槽位时发生错误: " + e.getMessage()
            );
            return false;
        }
    }
    
    /**
     * 清空玩家的所有宝可梦队伍槽位
     * @param player 玩家
     */
    public void clearPlayerPartySlots(Player player) {
        if (slotDatabase == null) {
            return;
        }
        
        try {
            for (int slotIndex = 1; slotIndex <= 6; slotIndex++) {
                String slotName = "cobblemon_party_" + slotIndex;
                slotDatabase.setSlotData(player.getUniqueId(), slotName, null);
            }
        } catch (Exception e) {
            plugin.getLogger().warning(
                "清空玩家 " + player.getName() + " 的队伍槽位时发生错误: " + e.getMessage()
            );
        }
    }
}