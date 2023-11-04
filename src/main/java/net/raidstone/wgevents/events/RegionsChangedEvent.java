package net.raidstone.wgevents.events;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author Weby &amp; Anrza (info@raidstone.net)
 * @version 1.0.0
 * @since 12/12/2019
 */
@Getter
public class RegionsChangedEvent extends Event implements Cancellable {
    @Getter
    private static final HandlerList handlers = new HandlerList();
    private final UUID uuid;
    private final Set<ProtectedRegion> previousRegions = new HashSet<>();
    private final Set<ProtectedRegion> currentRegions = new HashSet<>();
    private final Set<String> previousRegionsNames = new HashSet<>();
    private final Set<String> currentRegionsNames = new HashSet<>();
    @Setter
    private boolean cancelled = false;

    /**
     * This even is fired whenever a region is entered.
     * It may be fired multiple times per tick, if several
     * regions are entered at the same time.
     *
     * @param playerUUID The UUID of the player entering the region.
     * @param previous   Set of WorldGuard's ProtectedRegion the player was in before this event
     * @param current    Set of WorldGUard's ProtectedRegion the player is currently in
     */
    public RegionsChangedEvent(UUID playerUUID, @NotNull Set<ProtectedRegion> previous, @NotNull Set<ProtectedRegion> current) {
        this.uuid = playerUUID;
        previousRegions.addAll(previous);
        currentRegions.addAll(current);

        for (ProtectedRegion region : current)
            currentRegionsNames.add(region.getId());

        for (ProtectedRegion region : previous)
            previousRegionsNames.add(region.getId());
    }

    @Nullable
    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

}
