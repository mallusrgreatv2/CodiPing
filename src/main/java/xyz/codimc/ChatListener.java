package xyz.codimc;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.regex.Pattern;

public class ChatListener implements Listener {

    private final CodiPing plugin;
    private final MiniMessage miniMessage = MiniMessage.miniMessage();
    private final PlainTextComponentSerializer plainSerializer = PlainTextComponentSerializer.plainText();
    private static final Pattern PING_PATTERN = Pattern.compile("\\[ping]", Pattern.CASE_INSENSITIVE);

    public ChatListener(CodiPing plugin) {
        this.plugin = plugin;
    }

    // Handle modern Paper AsyncChatEvent
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onChatLowest(AsyncChatEvent event) {
        processMessage(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onChatHighest(AsyncChatEvent event) {
        processMessage(event);
    }

    // Handle legacy AsyncPlayerChatEvent (used by EssentialsX)
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onLegacyChatLowest(AsyncPlayerChatEvent event) {
        processLegacyMessage(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onLegacyChatHighest(AsyncPlayerChatEvent event) {
        processLegacyMessage(event);
    }

    private void processMessage(AsyncChatEvent event) {
        Player player = event.getPlayer();
        Component message = event.message();

        // Check if message contains [ping]
        String plainMessage = plainSerializer.serialize(message);
        if (PING_PATTERN.matcher(plainMessage).find()) {
            int ping = player.getPing();
            String coloredPing = formatPingWithColor(ping);

            plugin.getLogger().info("Replacing [ping] for " + player.getName() + " (AsyncChatEvent) with ping: " + ping + "ms");

            // Replace [ping] with colored ping
            TextReplacementConfig replacement = TextReplacementConfig.builder()
                    .match(PING_PATTERN)
                    .replacement(miniMessage.deserialize(coloredPing))
                    .build();

            Component newMessage = message.replaceText(replacement);
            event.message(newMessage);
        }
    }

    private void processLegacyMessage(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();

        // Check if message contains [ping]
        if (PING_PATTERN.matcher(message).find()) {
            int ping = player.getPing();

            plugin.getLogger().info("Replacing [ping] for " + player.getName() + " (Legacy) with ping: " + ping + "ms");

            // Use MiniMessage format for gradients
            String coloredPing = formatPingWithColor(ping);

            String newMessage = PING_PATTERN.matcher(message).replaceAll(coloredPing);
            event.setMessage(newMessage);
        }
    }

    private String formatPingWithColor(int ping) {
        String startColor;
        String endColor;

        if (ping <= 60) {
            startColor = "#14D400";
            endColor = "#0C7700";
        } else if (ping <= 100) {
            startColor = "#8DD400";
            endColor = "#447E00";
        } else if (ping <= 150) {
            startColor = "#D2D400";
            endColor = "#A7A900";
        } else if (ping <= 200) {
            startColor = "#D4AB00";
            endColor = "#B89500";
        } else {
            startColor = "#D40000";
            endColor = "#7E1400";
        }

        return "<gradient:" + startColor + ":" + endColor + ">" + ping + "ms</gradient>";
    }
}