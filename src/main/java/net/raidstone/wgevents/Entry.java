package net.raidstone.wgevents;

import com.sk89q.worldedit.util.Location;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.session.MoveType;
import com.sk89q.worldguard.session.Session;
import com.sk89q.worldguard.session.handler.Handler;
import lombok.NonNull;
import net.raidstone.wgevents.events.*;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

import java.util.Set;


/**
 * @author Weby &amp; Anrza (info@raidstone.net)
 * @version 1.0.0
 * @since 3/3/19
 */
public class Entry extends Handler implements Listener {

    private final PluginManager pluginManager;
    public Entry(@NonNull Session session, @NonNull PluginManager pluginManager) {
        super(session);
        this.pluginManager = pluginManager;
    }

    @Override
    public boolean onCrossBoundary(LocalPlayer player, Location from, Location to, ApplicableRegionSet toSet, Set<ProtectedRegion> entered, Set<ProtectedRegion> left, MoveType moveType) {
        RegionsChangedEvent regionsChangedEvent = new RegionsChangedEvent(player.getUniqueId(), left, entered);
        pluginManager.callEvent(regionsChangedEvent);
        if (regionsChangedEvent.isCancelled())
            return false;

        RegionsEnteredEvent regionsEnteredEvent = new RegionsEnteredEvent(player.getUniqueId(), entered);
        pluginManager.callEvent(regionsEnteredEvent);
        if (regionsEnteredEvent.isCancelled())
            return false;

        RegionsLeftEvent regionsLeftEvent = new RegionsLeftEvent(player.getUniqueId(), left);
        pluginManager.callEvent(regionsLeftEvent);

        if (regionsLeftEvent.isCancelled())
            return false;

        for (ProtectedRegion region : entered) {
            RegionEnteredEvent regionEnteredEvent = new RegionEnteredEvent(player.getUniqueId(), region);
            pluginManager.callEvent(regionEnteredEvent);

            if (regionEnteredEvent.isCancelled())
                return false;
        }


        for (ProtectedRegion region : left) {
            RegionLeftEvent regionLeftEvent = new RegionLeftEvent(player.getUniqueId(), region);
            pluginManager.callEvent(regionLeftEvent);

            if (regionLeftEvent.isCancelled())
                return false;
        }

        return true;
    }
}
