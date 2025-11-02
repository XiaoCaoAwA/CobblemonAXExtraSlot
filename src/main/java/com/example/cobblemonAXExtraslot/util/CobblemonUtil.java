package com.example.cobblemonAXExtraslot.util;

import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.pokemon.Species;
import org.bukkit.entity.Player;
import xiaocaoawa.miencraft.plugin.xccore.util.CobblemonUtil.CobblemonNameUtil;
import xiaocaoawa.miencraft.plugin.xccore.util.CobblemonUtil.CobblemonPartyUtil;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Cobblemon宝可梦队伍工具类
 * 用于查询和管理玩家的宝可梦队伍
 * 基于XCCore API实现
 */
public class CobblemonUtil {

    private CobblemonUtil() {
    }

    /**
     * 设置初始化状态
     */
    public static void setInitialized() {
        CobblemonPartyUtil.setInitialized();
    }

    /**
     * 获取玩家的宝可梦队伍存储
     * @param player Bukkit玩家对象
     * @return 玩家队伍存储，如果未初始化或玩家无效则返回null
     */
    public static PlayerPartyStore getPlayerPartyStore(Player player) {
        return CobblemonPartyUtil.getPlayerPartyStore(player);
    }

    /**
     * 获取玩家队伍中的所有宝可梦
     * @param player Bukkit玩家对象
     * @return 宝可梦列表，如果获取失败则返回空列表
     */
    public static List<Pokemon> getPartyPokemon(Player player) {
        return CobblemonPartyUtil.getAllPokemon(player);
    }

    /**
     * 获取队伍中宝可梦的数量
     * @param player Bukkit玩家对象
     * @return 宝可梦数量
     */
    public static int getPartySize(Player player) {
        return getPartyPokemon(player).size();
    }

    /**
     * 检查队伍是否为空
     * @param player Bukkit玩家对象
     * @return 队伍是否为空
     */
    public static boolean isPartyEmpty(Player player) {
        return getPartySize(player) == 0;
    }

    /**
     * 根据条件过滤队伍中的宝可梦
     * @param player Bukkit玩家对象
     * @param predicate 过滤条件
     * @return 符合条件的宝可梦列表
     */
    public static List<Pokemon> filterPartyPokemon(Player player, Predicate<Pokemon> predicate) {
        List<Pokemon> allPokemon = getPartyPokemon(player);
        List<Pokemon> filteredPokemon = new ArrayList<>();
        
        for (Pokemon pokemon : allPokemon) {
            if (predicate.test(pokemon)) {
                filteredPokemon.add(pokemon);
            }
        }
        
        return filteredPokemon;
    }

    /**
     * 获取宝可梦的显示名称
     * @param pokemon 宝可梦对象
     * @return 宝可梦名称
     */
    public static String getPokemonDisplayName(Pokemon pokemon) {
        if (pokemon == null) {
            return "";
        }
        
        Species species = pokemon.getSpecies();
        if (species != null) {
            return CobblemonNameUtil.getPokemonName(species);
        }
        
        return "";
    }
}