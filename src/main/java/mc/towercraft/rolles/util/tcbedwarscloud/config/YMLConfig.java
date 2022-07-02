package mc.towercraft.rolles.util.tcbedwarscloud.config;

import mc.towercraft.rolles.util.tcbedwarscloud.TCBedWarsCloud;
import org.bukkit.configuration.file.FileConfiguration;

public class YMLConfig {

    public String groupServer;
    private FileConfiguration config;

    public YMLConfig(TCBedWarsCloud plugin) {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
            plugin.saveDefaultConfig();
        }
        config = plugin.getConfig();
        groupServer = config.getString("server-group");
    }
}
