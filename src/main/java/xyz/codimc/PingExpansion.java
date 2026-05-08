package xyz.codimc;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PingExpansion extends PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() {
        return "codiping";
    }

    @Override
    public @NotNull String getAuthor() {
        return "CodiMC";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.1.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String params) {
        if (player == null) {
            return "";
        }

        if (params.equalsIgnoreCase("ping")) {
            int ping = player.getPing();
            return formatPingWithColor(ping);
        }

        return null;
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