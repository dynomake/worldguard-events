package net.raidstone.wgevents.impl;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import lombok.NonNull;
import lombok.val;
import net.raidstone.wgevents.EntryFactory;
import net.raidstone.wgevents.WorldGuardEvents;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * @author Weby &amp; Anrza (info@raidstone.net)
 * @since 2/24/19
 */
public class WorldGuardEventsImpl implements WorldGuardEvents {
    private static RegionContainer container;

    @Nonnull
    @Override
    public Set<ProtectedRegion> getRegions(UUID playerUUID) {
        Player player = Bukkit.getPlayer(playerUUID);

        if (player == null || !player.isOnline())
            return Collections.emptySet();

        RegionQuery query = container.createQuery();
        ApplicableRegionSet set = query.getApplicableRegions(BukkitAdapter.adapt(player.getLocation()));
        return set.getRegions();
    }

    @Nonnull
    @Override
    public Set<ProtectedRegion> getRegions(Location loc) {
        RegionQuery query = container.createQuery();
        ApplicableRegionSet set = query.getApplicableRegions(BukkitAdapter.adapt(loc));
        return set.getRegions();
    }

    @Nonnull
    @Override
    public Set<String> getRegionsNames(UUID playerUUID) {
        return getRegions(playerUUID).stream().map(ProtectedRegion::getId).collect(Collectors.toSet());
    }

    @Override
    public boolean isPlayerInAllRegions(UUID playerUUID, Set<String> regionNames) {
        Set<String> regions = getRegionsNames(playerUUID);
        if (regionNames.isEmpty()) throw new IllegalArgumentException("You need to check for at least one region !");

        return regions.containsAll(regionNames.stream().map(String::toLowerCase).collect(Collectors.toSet()));
    }

    @Override
    public boolean isPlayerInAnyRegion(UUID playerUUID, Set<String> regionNames) {
        Set<String> regions = getRegionsNames(playerUUID);
        if (regionNames.isEmpty())
            throw new IllegalArgumentException("You need to check for at least one region !");

        for (String region : regionNames)
            if (regions.contains(region.toLowerCase()))
                return true;

        return false;
    }

    @Override
    public boolean isPlayerInAnyRegion(UUID playerUUID, String... regionName) {
        return isPlayerInAnyRegion(playerUUID, new HashSet<>(Arrays.asList(regionName)));
    }

    @Override
    public boolean isPlayerInAllRegions(UUID playerUUID, String... regionName) {
        return isPlayerInAllRegions(playerUUID, new HashSet<>(Arrays.asList(regionName)));
    }

    public void init(@NonNull Plugin plugin) {
        Logger logger = Bukkit.getLogger();
        PluginManager pluginManager = Bukkit.getPluginManager();
        Plugin worldGuard = Bukkit.getPluginManager().getPlugin("WorldGuard");
        val factory = new EntryFactory();

        if (worldGuard == null) {
            logger.severe("[WorldGuardEvents] WorldGuard wasn't found. Disabling WorldGuardEvents.");
            pluginManager.disablePlugin(plugin);
            return;
        }

        String version = WorldGuard.getVersion();

        if (version.isEmpty()) {
            logger.severe("[WorldGuardEvents] WorldGuard's version not detected. Are you sure it's installed properly ?");
            logger.severe("[WorldGuardEvents] Disabling WorldGuardEvents.");

            pluginManager.disablePlugin(plugin);
            return;
        }

        if (!version.startsWith("7.")) {
            logger.warning("[WorldGuardEvents] Detected WorldGuard version \"" + version + "\".");
            logger.warning("[WorldGuardEvents] This plugin is meant to work with WorldGuard version \"7.0.0\" or higher,");
            logger.warning("[WorldGuardEvents] and may not work properly with any other major revision.");
            logger.warning("[WorldGuardEvents] Please update WorldGuard if your version is below \"7.0.0\" or wait for");
            logger.warning("[WorldGuardEvents] an update of WorldGuardEvents to support WorldGuard " + version + ".");
        }

        if (!WorldGuard.getInstance().getPlatform().getSessionManager().registerHandler(factory, null)) {
            logger.severe("[WorldGuardEvents] Could not register the entry handler !");
            logger.severe("[WorldGuardEvents] Please report this error. The plugin will now be disabled.");

            pluginManager.disablePlugin(plugin);
            return;
        }

        container = WorldGuard.getInstance().getPlatform().getRegionContainer();
    }
}
