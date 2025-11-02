package com.example.cobblemonAXExtraslot.command;

import com.cobblemon.mod.common.pokemon.Pokemon;
import com.example.cobblemonAXExtraslot.util.CobblemonUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

/**
 * 宝可梦队伍查询命令
 * 使用方法: /cae party
 */
public class PartyCommand extends SubCommand {

    @Override
    @NotNull
    public String getName() {
        return "party";
    }

    @Override
    @NotNull
    public String getDescription() {
        return "查询你的宝可梦队伍";
    }

    @Override
    @Nullable
    public String getUsage() {
        return "/cae party";
    }

    @Override
    @Nullable
    public String getPermission() {
        return "CobbleAXExtraSlot.party";
    }

    @Override
    public void onCommand(Player player, String[] args) {
        List<Pokemon> partyPokemon = CobblemonUtil.getPartyPokemon(player);
        
        if (partyPokemon.isEmpty()) {
            player.sendMessage("§e你的队伍中没有宝可梦。");
            return;
        }
        sendPartyInfo(player, partyPokemon);
    }

    @Override
    public void onCommand(ConsoleCommandSender console, String[] args) {
        console.sendMessage("§c此命令只能由玩家执行。");
    }

    /**
     * 发送队伍信息给玩家
     * @param player 玩家
     * @param partyPokemon 队伍中的宝可梦列表
     */
    private void sendPartyInfo(Player player, List<Pokemon> partyPokemon) {
        player.sendMessage("§7你的宝可梦队伍:");
        player.sendMessage("§7队伍大小: §a" + partyPokemon.size() + "§7/6");
        player.sendMessage("");

        for (int i = 0; i < 6; i++) {
            if (i < partyPokemon.size()) {
                Pokemon pokemon = partyPokemon.get(i);
                String pokemonName = CobblemonUtil.getPokemonDisplayName(pokemon);
                if (pokemonName.isEmpty()) {
                    player.sendMessage(String.format("§7%d. §8空", i + 1));
                } else {
                    player.sendMessage(String.format("§a%d. §f%s", i + 1, pokemonName));
                }
            } else {
                player.sendMessage(String.format("§7%d. §8空", i + 1));
            }
        }

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        // party命令不需要额外的参数补全
        return Collections.emptyList();
    }
}