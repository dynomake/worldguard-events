package net.raidstone.wgevents.events;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author Weby &amp; Anrza (info@raidstone.net)
 * @version 1.0.0
 * @since 2/24/19
 */
@Getter
public class RegionsLeftEvent extends Event implements Cancellable {

    @Getter
    private static final HandlerList handlers = new HandlerList();
    private final UUID uuid;
    private final Set<ProtectedRegion> regions;
    private final Set<String> regionsNames;
    @Setter
    private boolean cancelled = false;

    /**
     * This even is fired whenever one or several regions are left.
     *
     * @param playerUUID The UUID of the player leaving the regions.
     * @param regions    Set of WorldGuard's ProtectedRegion regions.
     */
    public RegionsLeftEvent(UUID playerUUID, @Nullable Set<ProtectedRegion> regions) {
        this.uuid = playerUUID;
        this.regionsNames = new HashSet<>();
        this.regions = new HashSet<>();

        if (regions == null)
            return;

        this.regions.addAll(regions);
        for (ProtectedRegion region : regions)
            this.regionsNames.add(region.getId());
    }


    @Nullable
    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }
}
