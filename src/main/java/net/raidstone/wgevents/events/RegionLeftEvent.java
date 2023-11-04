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

import java.util.UUID;

/**
 * @author Weby &amp; Anrza (info@raidstone.net)
 * @version 1.0.0
 * @since 2/24/19
 */
@Getter
public class RegionLeftEvent extends Event implements Cancellable {
    @Getter
    private static final HandlerList handlers = new HandlerList();
    private final UUID uuid;
    private final ProtectedRegion region;
    private final String regionName;
    @Setter
    private boolean cancelled = false;


    /**
     * This even is fired whenever a region is left.
     * It may be fired multiple times per tick, if several
     * regions are left at the same time.
     *
     * @param playerUUID The UUID of the player leaving the region.
     * @param region     WorldGuard's ProtectedRegion region.
     */
    public RegionLeftEvent(UUID playerUUID, @NotNull ProtectedRegion region) {
        this.uuid = playerUUID;
        this.region = region;
        this.regionName = region.getId();
    }

    @Nullable
    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }
}
