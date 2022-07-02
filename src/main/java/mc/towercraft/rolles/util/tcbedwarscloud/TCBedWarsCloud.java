package mc.towercraft.rolles.util.tcbedwarscloud;

import com.andrei1058.bedwars.api.BedWars;
import mc.towercraft.rolles.util.tcbedwarscloud.config.YMLConfig;
import mc.towercraft.rolles.util.tcbedwarscloud.listener.BedWarsListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class TCBedWarsCloud extends JavaPlugin {

    public static YMLConfig config;

    @Override
    public void onEnable() {
        BedWars bedWarsAPI = Bukkit.getServicesManager().getRegistration(BedWars .class).getProvider();
        config = new YMLConfig(this);
        Bukkit.getPluginManager().registerEvents(new BedWarsListener(this), this);
    }

    @Override
    public void onDisable() {
    }
}
