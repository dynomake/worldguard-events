package net.raidstone.wgevents;

import com.sk89q.worldguard.session.Session;
import com.sk89q.worldguard.session.handler.Handler;
import org.bukkit.Bukkit;

public class EntryFactory extends Handler.Factory<Entry> {
    @Override
    public Entry create(Session session) {
        return new Entry(session, Bukkit.getPluginManager());
    }
}