package mc.towercraft.rolles.util.tcbedwarscloud.listener;

import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.events.gameplay.GameEndEvent;
import com.andrei1058.bedwars.api.events.gameplay.GameStateChangeEvent;
import de.dytanic.cloudnet.driver.service.ServiceInfoSnapshot;
import de.dytanic.cloudnet.ext.bridge.bukkit.BukkitCloudNetHelper;
import de.dytanic.cloudnet.wrapper.Wrapper;
import mc.towercraft.rolles.util.tcbedwarscloud.TCBedWarsCloud;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BedWarsListener implements Listener {

    private final TCBedWarsCloud plugin;

    public BedWarsListener(TCBedWarsCloud plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onGameState(GameStateChangeEvent event) {
        if (event.getNewState() == GameState.playing) BukkitCloudNetHelper.changeToIngame(true);
    }

    @EventHandler
    public void onGameEnd(GameEndEvent event) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            ServiceInfoSnapshot currentServiceInfoSnapshot = Wrapper.getInstance().getCurrentServiceInfoSnapshot();
        }, 100);
    }
}
