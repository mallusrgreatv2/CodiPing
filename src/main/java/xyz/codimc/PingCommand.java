package xyz.codimc;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PingCommand implements CommandExecutor, TabCompleter {

    private static final String VERSION = "1.0.3";
    private static final String AUTHOR  = "CodiMC";

    private final MiniMessage mm = MiniMessage.miniMessage();

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command,
                             @NotNull String label,
                             @NotNull String[] args) {

        // /ping  — show own ping (must be a player)
        if (args.length == 0) {
            if (!(sender instanceof Player player)) {
                sender.sendMessage("Console cannot have a ping. Use /ping <player> instead.");
                return true;
            }
            sendOwnPing(player);
            return true;
        }

        switch (args[0].toLowerCase()) {

            // /ping version (OP only)
            case "version" -> {
                if (!sender.isOp()) {
                    sender.sendMessage(mm.deserialize("<red>You don't have permission to use this command.</red>"));
                    return true;
                }
                sender.sendMessage(mm.deserialize(
                        "<gray>CodiPing <white>v" + VERSION + "</white> by <aqua>" + AUTHOR + "</aqua></gray>"
                ));
            }

            // /ping list (OP only)
            case "list" -> {
                if (!sender.isOp()) {
                    sender.sendMessage(mm.deserialize("<red>You don't have permission to use this command.</red>"));
                    return true;
                }
                Collection<? extends Player> online = Bukkit.getOnlinePlayers();
                if (online.isEmpty()) {
                    sender.sendMessage(mm.deserialize("<gray>No players are currently online.</gray>"));
                    return true;
                }

                sender.sendMessage(mm.deserialize(
                        "<dark_gray>┌─</dark_gray> <white><bold>Online Player Pings</bold></white>"
                ));

                for (Player p : online) {
                    int ping = p.getPing();
                    String coloredPing = formatPingWithColor(ping);
                    sender.sendMessage(mm.deserialize(
                            "<dark_gray>│</dark_gray> <white>" + p.getName() + "</white> <dark_gray>—</dark_gray> " + coloredPing
                    ));
                }

                sender.sendMessage(mm.deserialize(
                        "<dark_gray>└─</dark_gray> <gray>" + online.size() + " player(s) online</gray>"
                ));
            }

            // /ping <player> (OP only)
            default -> {
                if (!sender.isOp()) {
                    sender.sendMessage(mm.deserialize("<red>You don't have permission to use this command.</red>"));
                    return true;
                }
                Player target = Bukkit.getPlayerExact(args[0]);
                if (target == null) {
                    sender.sendMessage(mm.deserialize(
                            "<red>Player <white>" + args[0] + "</white> is not online.</red>"
                    ));
                    return true;
                }

                int ping = target.getPing();
                String coloredPing = formatPingWithColor(ping);
                sender.sendMessage(mm.deserialize(
                        "<gray>" + target.getName() + "'s ping: </gray>" + coloredPing
                ));
            }
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender,
                                      @NotNull Command command,
                                      @NotNull String label,
                                      @NotNull String[] args) {
        List<String> suggestions = new ArrayList<>();

        if (args.length == 1) {
            String input = args[0].toLowerCase();

            // Static subcommands (version and list are OP only)
            for (String sub : List.of("version", "list")) {
                if (sub.startsWith(input) && sender.isOp()) suggestions.add(sub);
            }

            // Online player names (OP only)
            if (sender.isOp()) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (p.getName().toLowerCase().startsWith(input)) {
                        suggestions.add(p.getName());
                    }
                }
            }
        }

        return suggestions;
    }

    private void sendOwnPing(Player player) {
        int ping = player.getPing();
        String coloredPing = formatPingWithColor(ping);
        player.sendMessage(mm.deserialize("<gray>Your ping: </gray>" + coloredPing));
    }

    private String formatPingWithColor(int ping) {
        String startColor;
        String endColor;

        if (ping <= 60) {
            startColor = "#14D400";
            endColor   = "#0C7700";
        } else if (ping <= 100) {
            startColor = "#8DD400";
            endColor   = "#447E00";
        } else if (ping <= 150) {
            startColor = "#D2D400";
            endColor   = "#A7A900";
        } else if (ping <= 200) {
            startColor = "#D4AB00";
            endColor   = "#B89500";
        } else {
            startColor = "#D40000";
            endColor   = "#7E1400";
        }

        return "<gradient:" + startColor + ":" + endColor + ">" + ping + "ms</gradient>";
    }
}