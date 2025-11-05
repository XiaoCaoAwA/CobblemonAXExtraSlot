package com.example.cobblemonAXExtraslot.command;

import com.cobblemon.mod.common.pokemon.Pokemon;
import com.example.cobblemonAXExtraslot.util.CobblemonUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import com.example.cobblemonAXExtraslot.util.CobblemonLoreBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import priv.seventeen.artist.arcartx.core.entity.data.ArcartXPlayer;
// 生成物品的逻辑已集中到 PokemonItemLoreBuilder

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 额外槽位相关命令
 * 使用方法: /cae slot <子命令>
 */
public class SlotCommand extends SubCommand {
    
    public SlotCommand() {
    }

    @Override
    @NotNull
    public String getName() {
        return "slot";
    }

    @Override
    @NotNull
    public String getDescription() {
        return "额外槽位相关命令";
    }

    @Override
    @Nullable
    public String getUsage() {
        return "/cae slot <setslot> <玩家名> <队伍槽位> <额外槽位名>";
    }

    @Override
    @Nullable
    public String getPermission() {
        return "CobbleAXExtraSlot.slot";
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
            case "setslot":
                handleSetSlot(player, args);
                break;
            default:
                player.sendMessage("§c未知的子命令: " + subCommand);
                player.sendMessage("§e可用命令: setslot");
                break;
        }
    }

    @Override
    public void onCommand(ConsoleCommandSender console, String[] args) {
        if (args.length == 0) {
            console.sendMessage("§c用法: " + getUsage());
            return;
        }

        String subCommand = args[0].toLowerCase();
        switch (subCommand) {
            case "setslot":
                handleSetSlot(console, args);
                break;
            default:
                console.sendMessage("§c未知的子命令: " + subCommand);
                console.sendMessage("§e可用命令: setslot");
                break;
        }
    }

    /**
     * 处理 setslot 子命令
     * @param sender 命令发送者
     * @param args 命令参数
     */
    private void handleSetSlot(CommandSender sender, String[] args) {
        if (args.length < 4) {
            sender.sendMessage("§c用法: /cae slot setslot <玩家名> <队伍槽位> <额外槽位名>");
            return;
        }

        String targetPlayerName = args[1];
        String slotStr = args[2];
        String extraSlotName = args[3];

        Player targetPlayer = Bukkit.getPlayer(targetPlayerName);
        if (targetPlayer == null) {
            sender.sendMessage("§c玩家 " + targetPlayerName + " 不在线或不存在。");
            return;
        }

        int slot;
        try {
            slot = Integer.parseInt(slotStr);
        } catch (NumberFormatException e) {
            sender.sendMessage("§c无效的槽位数字: " + slotStr);
            sender.sendMessage("§e队伍槽位范围: 1-6");
            return;
        }

        if (slot < 1 || slot > 6) {
            sender.sendMessage("§c槽位必须在 1-6 之间，你输入的是: " + slot);
            return;
        }

        List<Pokemon> partyPokemon = CobblemonUtil.getPartyPokemon(targetPlayer);
        
        if (partyPokemon.isEmpty()) {
            sender.sendMessage("§e玩家 " + targetPlayerName + " 的队伍中没有宝可梦。");
            return;
        }

        if (slot > partyPokemon.size()) {
            sender.sendMessage("§c玩家 " + targetPlayerName + " 的槽位 " + slot + " 没有宝可梦。");
            return;
        }

        Pokemon pokemon = partyPokemon.get(slot - 1);
        if (pokemon == null) {
            sender.sendMessage("§c玩家 " + targetPlayerName + " 的槽位 " + slot + " 没有宝可梦。");
            return;
        }

        try {
            ItemStack pokemonItem = CobblemonLoreBuilder.buildPokemonItemWithLore(pokemon, slot);
            if (pokemonItem == null) {
                sender.sendMessage("§c无法获取宝可梦照片，请稍后再试。");
                return;
            }
            ArcartXPlayer arcartXPlayer = new ArcartXPlayer(targetPlayer);
            arcartXPlayer.setSlotItemStackOnlyClient(extraSlotName, pokemonItem);
            
            String pokemonName = CobblemonUtil.getPokemonDisplayName(pokemon);
            sender.sendMessage("§a成功将玩家 §f" + targetPlayerName + " §a的槽位 §f" + slot + " §a中的宝可梦照片 §f" + pokemonName + " §a设置到额外槽位 §f" + extraSlotName);

            
        } catch (Exception e) {
            sender.sendMessage("§c设置额外槽位时发生错误: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            return Arrays.asList("setslot");
        } else if (args.length == 2 && "setslot".equalsIgnoreCase(args[0])) {

            return Bukkit.getOnlinePlayers().stream()
                    .map(Player::getName)
                    .filter(name -> name.toLowerCase().startsWith(args[1].toLowerCase()))
                    .toList();
        } else if (args.length == 3 && "setslot".equalsIgnoreCase(args[0])) {

            return Arrays.asList("1", "2", "3", "4", "5", "6");
        }
        return Collections.emptyList();
    }
}