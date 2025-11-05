package com.example.cobblemonAXExtraslot.util;

import com.cobblemon.mod.common.pokemon.Pokemon;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xiaocaoawa.miencraft.plugin.xccore.util.CobblemonUtil.CobblemonItemUtil;
import xiaocaoawa.miencraft.plugin.xccore.util.CobblemonUtil.CobblemonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 构建带显示名与详细 Lore 的宝可梦物品
 * 统一管理物品展示文本，避免在各处重复拼接
 */
public final class CobblemonLoreBuilder {

    private CobblemonLoreBuilder() {}

    /**
     * 根据宝可梦生成带有显示名与详细 lore 的照片物品
     * @param pokemon 宝可梦
     * @param displaySlotNumber 显示用的槽位编号（1-6）
     * @return 生成后的物品，若无法获取基础照片则返回 null
     */
    public static ItemStack buildPokemonItemWithLore(Pokemon pokemon, int displaySlotNumber) {
        ItemStack item = CobblemonItemUtil.getPokemonItem(pokemon);
        if (item == null) return null;

        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§a" + displaySlotNumber + "号位: " + CobblemonUtil.getSpeciesName(pokemon));
            List<String> lore = new ArrayList<>();

            lore.add("§a ▶ §b等级: §f" + CobblemonUtil.getLevelToString(pokemon));

            lore.add("§a ▶ §b个体信息§7[§e进度: §f" + CobblemonUtil.getIvSumToString(pokemon) + "%§7]");
            lore.add("§f  ▪ §a血量:§f " + CobblemonUtil.getIvHPToString(pokemon) + " §a速度:§f " + CobblemonUtil.getIvSpeedToString(pokemon));
            lore.add("§f  ▪ §a攻击:§f " + CobblemonUtil.getIvAttackToString(pokemon) + " §a防御:§f " + CobblemonUtil.getIvDefenceToString(pokemon));
            lore.add("§f  ▪ §a特攻:§f " + CobblemonUtil.getIvSpecialAttackToString(pokemon) + " §a特防:§f " + CobblemonUtil.getIvSpecialDefenceToString(pokemon));

            lore.add("§a ▶ §b努力值信息§7[§e进度: §f" + CobblemonUtil.getEvSumToString(pokemon) + "%§7]");
            lore.add("§f  ▪ §a血量:§f " + CobblemonUtil.getEvHPToString(pokemon) + " §a速度:§f " + CobblemonUtil.getEvSpeedToString(pokemon));
            lore.add("§f  ▪ §a攻击:§f " + CobblemonUtil.getEvAttackToString(pokemon) + " §a防御:§f " + CobblemonUtil.getEvDefenceToString(pokemon));
            lore.add("§f  ▪ §a特攻:§f " + CobblemonUtil.getEvSpecialAttackToString(pokemon) + " §a特防:§f " + CobblemonUtil.getEvSpecialDefenceToString(pokemon));

            lore.add("§a ▶ §b基础信息:");
            lore.add("§f  ▪ §a昵称: §f" + CobblemonUtil.getSpeciesName(pokemon));

            String type1 = CobblemonUtil.getPrimaryTypeName(pokemon);
            String type2 = CobblemonUtil.getSecondaryTypeName(pokemon);
            String typeInfo = type2.isEmpty() ? type1 : type1 + " " + type2;
            lore.add("§f  ▪ §a属性: §f" + typeInfo);

            lore.add("§f  ▪ §a性别: §f" + CobblemonUtil.getGenderName(pokemon));
            lore.add("§f  ▪ §a性格: §f" + CobblemonUtil.getNatureName(pokemon) + "§7[" + CobblemonUtil.getNatureType(pokemon) + "§7]");
            lore.add("§f  ▪ §a性格薄荷: §f" + CobblemonUtil.getMintedNatureName(pokemon) + "§7[" + CobblemonUtil.getMintedNatureType(pokemon) + "§7]");
            lore.add("§f  ▪ §a特性: §f" + CobblemonUtil.getAbilityName(pokemon));
            lore.add("§f  ▪ §a闪光: §f" + CobblemonUtil.isShinyToString(pokemon));

            lore.add("§a ▶ §b技能信息:");
            lore.add("§f  ▪ §a一技能: §f" + CobblemonUtil.getMoveName(pokemon, 0));
            lore.add("§f  ▪ §a二技能: §f" + CobblemonUtil.getMoveName(pokemon, 1));
            lore.add("§f  ▪ §a三技能: §f" + CobblemonUtil.getMoveName(pokemon, 2));
            lore.add("§f  ▪ §a四技能: §f" + CobblemonUtil.getMoveName(pokemon, 3));

            meta.setLore(lore);
            item.setItemMeta(meta);
        }

        return item;
    }
}