package com.example.cobblemonAXExtraslot.config;

import com.example.cobblemonAXExtraslot.CobblemonAXExtraslot;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author XiaoCaoAwA
 * @version 1.0
 */
@Getter
public class MainConfig extends BaseConfig {

    public static MainConfig INSTANCE;

    static {

    }

    public static void initialize(CobblemonAXExtraslot plugin) {
        if (INSTANCE == null) {
            INSTANCE = new MainConfig(plugin, "config.yml");
        }
    }

    private MainConfig(JavaPlugin plugin, String fileName) {
        super(plugin, fileName);
        saveDefault();
    }

    @Override
    public void load() {
        super.load();

    }

    /**
     * 是否开启调试日志输出
     */
    public boolean isDebug() {
        return getBoolean("debug", false);
    }

    /**
     * 额外槽位同步时间（秒）
     */
    public int getSlotSyncTimeSeconds() {
        return getInt("SlotSyncTime", 1);
    }

}
