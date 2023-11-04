package net.raidstone.wgevents;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import lombok.NonNull;
import lombok.val;
import net.raidstone.wgevents.impl.WorldGuardEventsImpl;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;
import java.util.*;

public interface WorldGuardEvents {
    static WorldGuardEvents create(@NonNull Plugin owner) {
        val events = new WorldGuardEventsImpl();
        events.init(owner);

        return events;
    }

    /**
     * Gets the regions a player is currently in.
     *
     * @param playerUUID UUID of the player in question.
     * @return Set of WorldGuard protected regions that the player is currently in.
     */
    @Nonnull
    Set<ProtectedRegion> getRegions(@NonNull UUID playerUUID);

    /**
     * Gets the regions in location.
     *
     * @param loc Location to get regions.
     * @return Set of WorldGuard protected regions that is currently in this location.
     */
    @Nonnull
    Set<ProtectedRegion> getRegions(@NonNull Location location);

    /**
     * Gets the regions names a player is currently in.
     *
     * @param playerUUID UUID of the player in question.
     * @return Set of Strings with the names of the regions the player is currently in.
     */
    @Nonnull
    Set<String> getRegionsNames(@NonNull UUID playerUUID);

    /**
     * Checks whether a player is in one or several regions
     *
     * @param playerUUID  UUID of the player in question.
     * @param regionNames Set of regions to check.
     * @return True if the player is in (all) the named region(s).
     */
    boolean isPlayerInAllRegions(@NonNull UUID playerUUID, @NonNull Set<String> regionNames);
    /**
     * Checks whether a player is in one or several regions
     *
     * @param playerUUID  UUID of the player in question.
     * @param regionNames Set of regions to check.
     * @return True if the player is in (any of) the named region(s).
     */
    boolean isPlayerInAnyRegion(@NonNull UUID playerUUID, @NonNull Set<String> regionNames);

    /**
     * Checks whether a player is in one or several regions
     *
     * @param playerUUID UUID of the player in question.
     * @param regionName List of regions to check.
     * @return True if the player is in (any of) the named region(s).
     */
    boolean isPlayerInAnyRegion(@NonNull UUID playerUUID, @NonNull String... regionName);

    /**
     * Checks whether a player is in one or several regions
     *
     * @param playerUUID UUID of the player in question.
     * @param regionName List of regions to check.
     * @return True if the player is in (any of) the named region(s).
     */
    boolean isPlayerInAllRegions(@NonNull UUID playerUUID, @NonNull String... regionName);
}
