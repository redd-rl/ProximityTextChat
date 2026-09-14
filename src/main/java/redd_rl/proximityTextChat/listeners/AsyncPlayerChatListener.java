package redd_rl.proximityTextChat.listeners;

import java.util.Iterator;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import redd_rl.proximityTextChat.ProximityTextChat;

public class AsyncPlayerChatListener implements Listener {

    private final ProximityTextChat plugin;

    public AsyncPlayerChatListener(final ProximityTextChat plugin) {
        this.plugin = plugin;
    }
}