package com.example.cobblemonAXExtraslot.command;

import org.jetbrains.annotations.Nullable;

/**
 * 命令管理器，用于注册和管理所有子命令
 */
public class MainCommand extends AbstractMainCommand {

    public MainCommand() {
        // 注册所有子命令
        registerSubCommands();
    }

    /**
     * 注册所有子命令
     */
    private void registerSubCommands() {
        // 注册help命令
        registerSubCommand(new HelpCommand());
        
        // 注册party命令
        registerSubCommand(new PartyCommand());
        
        // 注册item命令
        registerSubCommand(new ItemCommand());
        
        // 注册slot命令
        registerSubCommand(new SlotCommand());
    }

    @Override
    protected @Nullable String getUnknownSubCommandMessage() {
        return "§c未知的子命令，请使用 /cae help 查看帮助";
    }

}