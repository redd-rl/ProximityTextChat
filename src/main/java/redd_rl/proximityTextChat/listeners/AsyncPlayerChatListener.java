package redd_rl.proximityTextChat.listeners;

import io.papermc.paper.chat.ChatRenderer;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import redd_rl.proximityTextChat.ProximityTextChat;

public class AsyncPlayerChatListener implements Listener, ChatRenderer {

    private final ProximityTextChat plugin;

    public AsyncPlayerChatListener(final ProximityTextChat plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull Component render(
            @NotNull Player source,
            @NotNull Component sourceDisplayName,
            @NotNull Component message,
            @NotNull Audience viewer
    ) {
        String plainMsg = PlainTextComponentSerializer.plainText().serialize(message);

        String formatted = this.plugin.messages.globalFormat
                .replace("{player}", source.getName())
                .replace("{message}", plainMsg);

        return LegacyComponentSerializer.legacyAmpersand().deserialize(formatted);
    }

    @EventHandler
    public void onChat(final AsyncChatEvent e) {
        Player player = e.getPlayer();
        String plainMessage = PlainTextComponentSerializer.plainText().serialize(e.message());
        boolean bypass = false;

        if (plainMessage.startsWith(this.plugin.settings.globalPrefix) && player.hasPermission("proximitytextchat.global")) {
            bypass = true;
            String trimmedMessage = plainMessage.substring(this.plugin.settings.globalPrefix.length());
            e.message(Component.text(trimmedMessage));

            e.renderer(this);
        }

        if (!bypass) {
            e.viewers().removeIf(audience -> {
                if (audience instanceof Player recipient) {
                    if (!recipient.getWorld().equals(player.getWorld())) {
                        return true;
                    }
                    return recipient.getLocation().distanceSquared(player.getLocation()) > Math.pow(this.plugin.settings.maximumReceivingDistance, 2);
                }
                return false;
            });
        }

        boolean hasOtherRecipients = e.viewers().stream()
                .anyMatch(audience -> audience instanceof Player recipient && !recipient.getUniqueId().equals(player.getUniqueId()));

        if (!bypass && !hasOtherRecipients) {
            if (this.plugin.messages.noReader != null && !this.plugin.messages.noReader.isEmpty()) {
                player.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(this.plugin.messages.noReader));
                this.plugin.incrementLostMessages();
            }
            if (this.plugin.settings.cancelMessage) {
                e.setCancelled(true);
            }
        }

        for (Player onlinePlayer : this.plugin.getServer().getOnlinePlayers()) {
            if (!e.viewers().contains(onlinePlayer) && onlinePlayer.hasPermission("proximitytextchat.spy")) {
                String formattedSpy = this.plugin.messages.spyFormat
                        .replace("{player}", player.getName())
                        .replace("{message}", PlainTextComponentSerializer.plainText().serialize(e.message()));
                onlinePlayer.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(formattedSpy));
            }
        }
    }
}