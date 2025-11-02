package com.example.cobblemonAXExtraslot.command;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

/**
 * 帮助命令
 * 使用方法: /cae help
 */
public class HelpCommand extends SubCommand {

    @Override
    @NotNull
    public String getName() {
        return "help";
    }

    @Override
    @NotNull
    public String getDescription() {
        return "显示帮助信息";
    }

    @Override
    @Nullable
    public String getUsage() {
        return "/cae help";
    }

    @Override
    @Nullable
    public String getPermission() {
        return null; // 帮助命令不需要权限
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public void onCommand(Player player, String[] args) {
        sendHelpMessage(player);
    }

    @Override
    public void onCommand(ConsoleCommandSender console, String[] args) {
        sendHelpMessage(console);
    }

    /**
     * 发送帮助信息
     * @param sender 命令发送者
     */
    private void sendHelpMessage(CommandSender sender) {
        sender.sendMessage("§7CobbleAXExtraSlot:");
        sender.sendMessage("§f/cae help §7- 显示此帮助信息");
        sender.sendMessage("§f/cae party §7- 查询你的宝可梦队伍");
        sender.sendMessage("§f/cae item selfgive <槽位> §7- 给予指定槽位宝可梦的照片");
        sender.sendMessage("§f/cae slot setslot <玩家> <槽位> <额外槽位名> §7- 设置玩家的额外槽位");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return Collections.emptyList();
    }
}