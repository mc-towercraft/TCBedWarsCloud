package mc.towercraft.rolles.util.tcbedwarscloud.listener;

import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.events.gameplay.GameEndEvent;
import com.andrei1058.bedwars.api.events.gameplay.GameStateChangeEvent;
import com.andrei1058.bedwars.api.events.player.PlayerBedBreakEvent;
import com.andrei1058.bedwars.api.events.player.PlayerKillEvent;
import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.driver.service.ServiceInfoSnapshot;
import de.dytanic.cloudnet.ext.bridge.bukkit.BukkitCloudNetHelper;
import de.dytanic.cloudnet.wrapper.Wrapper;
import mc.towercraft.rolles.util.tcbedwarscloud.TCBedWarsCloud;
import me.towercraft.connection.server.ServerConnectApi;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import java.util.List;
import java.util.UUID;

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
            Bukkit.getOnlinePlayers().forEach(player -> ServerConnectApi.getInstance().connectByServer(player, TCBedWarsCloud.config.groupServer));
        }, 100);
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            ServiceInfoSnapshot currentServiceInfoSnapshot = Wrapper.getInstance().getCurrentServiceInfoSnapshot();
            CloudNetDriver.getInstance().getCloudServiceProvider(currentServiceInfoSnapshot.getName()).stop();
        }, 200);
    }

    @EventHandler
    public void onPing(ServerListPingEvent event) {
        event.setMotd(Bukkit.getMotd());
    }

    @EventHandler
    public void onPlayerBedBreak(PlayerBedBreakEvent event) {
        for (String command: TCBedWarsCloud.config.getConfig().getStringList("rewards.bed-destroy-player")) {
            Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), command.replace("{player}", event.getPlayer().getName()));
        }
    }

    @EventHandler
    public void onPlayerKill(PlayerKillEvent event) {
        String command = TCBedWarsCloud.config.getConfig().getStringList("rewards.for-killer").get(0);
        Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), command.replace("{player}", event.getKiller().getName()));
        command = TCBedWarsCloud.config.getConfig().getStringList("rewards.for-deceased").get(0);
        Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), command.replace("{player}", event.getVictim().getName()));
    }

    @EventHandler
    public void onLooseTeam(GameEndEvent event) {
        for (UUID uuid : event.getLosers()) {
            for (String command : TCBedWarsCloud.config.getConfig().getStringList("rewards.for-loose-team")) {
                Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), command.replace("{player}", Bukkit.getPlayer(uuid).getDisplayName()));
            }
        }
        ITeam team = event.getTeamWinner();
        for (Player player : team.getMembers()) {
            for (String command : TCBedWarsCloud.config.getConfig().getStringList("rewards.for-winner-team")) {
                Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), command.replace("{player}", player.getName()));
            }
        }
    }
}
