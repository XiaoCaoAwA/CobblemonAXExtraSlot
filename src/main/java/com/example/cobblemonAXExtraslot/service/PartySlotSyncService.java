package com.example.cobblemonAXExtraslot.service;

import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.example.cobblemonAXExtraslot.CobblemonAXExtraslot;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import com.example.cobblemonAXExtraslot.util.CobblemonLoreBuilder;
import priv.seventeen.artist.arcartx.core.entity.data.ArcartXPlayer;
import com.example.cobblemonAXExtraslot.util.DebugLogger;
import xiaocaoawa.miencraft.plugin.xccore.util.CobblemonUtil.CobblemonPartyUtil;

/**
 * 宝可梦队伍槽位同步服务
 * 提供统一的宝可梦队伍同步逻辑，避免代码重复
 */
public class PartySlotSyncService {
    
    private final CobblemonAXExtraslot plugin;
    
    public PartySlotSyncService(CobblemonAXExtraslot plugin) {
        this.plugin = plugin;
    }
    
    /**
     * 同步玩家的宝可梦队伍到额外槽位（静默模式，无详细日志）
     * @param player 玩家
     * @return 是否同步成功
     */
    public boolean syncPlayerPartySilently(Player player) {
        PlayerPartyStore partyStore = CobblemonPartyUtil.getPlayerPartyStore(player);
        if (partyStore == null) {
            return false;
        }
        
        try {
            ArcartXPlayer arcartXPlayer = new ArcartXPlayer(player);
            
            for (int slotIndex = 0; slotIndex < 6; slotIndex++) {
                Pokemon pokemon = partyStore.get(slotIndex);
                String slotName = "cobblemon_party_" + (slotIndex + 1);
                
                if (pokemon != null) {
                    ItemStack pokemonItem = CobblemonLoreBuilder.buildPokemonItemWithLore(pokemon, slotIndex + 1);
                    if (pokemonItem != null) {
                        arcartXPlayer.setSlotItemStackOnlyClient(slotName, pokemonItem);
                    } else {
                        DebugLogger.warning(plugin, "无法生成队伍槽位物品: 玩家=" + player.getName() + " 槽位=" + (slotIndex + 1));
                    }
                } else {
                    arcartXPlayer.removeSlotItemStackOnlyClient(slotName, false);
                }
            }
            return true;
        } catch (Exception e) {
            DebugLogger.warning(plugin,
                "同步玩家 " + player.getName() + " 的队伍槽位时发生错误: " + e.getMessage()
            );
            DebugLogger.printStackTraceIfDebug(e);
            return false;
        }
    }
    
    /**
     * 清空玩家的所有宝可梦队伍槽位
     * @param player 玩家
     */
    public void clearPlayerPartySlots(Player player) {
        try {
            ArcartXPlayer arcartXPlayer = new ArcartXPlayer(player);
            
            for (int slotIndex = 1; slotIndex <= 6; slotIndex++) {
                String slotName = "cobblemon_party_" + slotIndex;
                arcartXPlayer.removeSlotItemStackOnlyClient(slotName, false);
            }
        } catch (Exception e) {
            DebugLogger.warning(plugin,
                "清空玩家 " + player.getName() + " 的队伍槽位时发生错误: " + e.getMessage()
            );
            DebugLogger.printStackTraceIfDebug(e);
        }
}
}