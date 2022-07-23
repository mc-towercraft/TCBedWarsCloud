package mc.towercraft.rolles.util.tcbedwarscloud;

import com.andrei1058.bedwars.api.BedWars;
import mc.towercraft.rolles.util.tcbedwarscloud.config.Config;
import mc.towercraft.rolles.util.tcbedwarscloud.listener.BedWarsListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class TCBedWarsCloud extends JavaPlugin {

    public static Config config;

    @Override
    public void onEnable() {
        BedWars bedWarsAPI = Bukkit.getServicesManager().getRegistration(BedWars .class).getProvider();
        config = new Config(this);
        Bukkit.getPluginManager().registerEvents(new BedWarsListener(this), this);
    }

    @Override
    public void onDisable() {
    }
}
