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
        // 延迟初始化，等待插件实例可用
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

}
