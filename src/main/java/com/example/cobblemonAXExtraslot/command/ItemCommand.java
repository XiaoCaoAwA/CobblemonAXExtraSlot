package com.example.cobblemonAXExtraslot.command;

import com.cobblemon.mod.common.pokemon.Pokemon;
import com.example.cobblemonAXExtraslot.util.CobblemonUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xiaocaoawa.miencraft.plugin.xccore.util.CobblemonUtil.CobblemonItemUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 宝可梦物品相关命令
 * 使用方法: /cae item <子命令>
 */
public class ItemCommand extends SubCommand {

    @Override
    @NotNull
    public String getName() {
        return "item";
    }

    @Override
    @NotNull
    public String getDescription() {
        return "宝可梦物品相关命令";
    }

    @Override
    @Nullable
    public String getUsage() {
        return "/cae item <selfgive> <队伍槽位>";
    }

    @Override
    @Nullable
    public String getPermission() {
        return "CobbleAXExtraSlot.item";
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public void onCommand(Player player, String[] args) {
        if (args.length == 0) {
            player.sendMessage("§c用法: " + getUsage());
            return;
        }

        String subCommand = args[0].toLowerCase();
        
        switch (subCommand) {
            case "selfgive":
                handleSelfGive(player, args);
                break;
            default:
                player.sendMessage("§c未知的子命令: " + subCommand);
                player.sendMessage("§e可用命令: selfgive");
                break;
        }
    }

    @Override
    public void onCommand(ConsoleCommandSender console, String[] args) {
        console.sendMessage("§c此命令只能由玩家执行。");
    }

    /**
     * 处理 selfgive 子命令
     * @param player 玩家
     * @param args 命令参数
     */
    private void handleSelfGive(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage("§c用法: /cae item selfgive <队伍槽位>");
            player.sendMessage("§e队伍槽位范围: 1-6");
            return;
        }

        // 解析槽位参数
        int slot;
        try {
            slot = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            player.sendMessage("§c无效的槽位数字: " + args[1]);
            player.sendMessage("§e队伍槽位范围: 1-6");
            return;
        }

        // 检查槽位范围
        if (slot < 1 || slot > 6) {
            player.sendMessage("§c槽位必须在 1-6 之间，你输入的是: " + slot);
            return;
        }

        // 获取玩家的宝可梦队伍
        List<Pokemon> partyPokemon = CobblemonUtil.getPartyPokemon(player);
        
        if (partyPokemon.isEmpty()) {
            player.sendMessage("§e你的队伍中没有宝可梦。");
            return;
        }

        // 检查指定槽位是否有宝可梦
        if (slot > partyPokemon.size()) {
            player.sendMessage("§c槽位 " + slot + " 没有宝可梦。");
            return;
        }

        // 获取指定槽位的宝可梦
        Pokemon pokemon = partyPokemon.get(slot - 1); // 数组索引从0开始
        if (pokemon == null) {
            player.sendMessage("§c槽位 " + slot + " 没有宝可梦。");
            return;
        }

        // 使用 XCCore API 获取宝可梦照片物品
        ItemStack pokemonItem = CobblemonItemUtil.getPokemonItem(pokemon);
        if (pokemonItem == null) {
            player.sendMessage("§c无法获取宝可梦照片，请稍后再试。");
            return;
        }

        // 给予玩家物品
        if (player.getInventory().firstEmpty() == -1) {
            player.sendMessage("§c你的背包已满，无法给予物品。");
            return;
        }

        player.getInventory().addItem(pokemonItem);
        String pokemonName = CobblemonUtil.getPokemonDisplayName(pokemon);
        player.sendMessage("§a已给予你槽位 " + slot + " 的宝可梦照片: §f" + pokemonName);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            // 第一个参数：子命令补全
            return Arrays.asList("selfgive");
        } else if (args.length == 2 && "selfgive".equalsIgnoreCase(args[0])) {
            // 第二个参数：槽位补全
            return Arrays.asList("1", "2", "3", "4", "5", "6");
        }
        
        return Collections.emptyList();
    }
}