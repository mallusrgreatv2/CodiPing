package xyz.codimc;

import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
import org.bukkit.plugin.java.JavaPlugin;

public final class CodiPing extends JavaPlugin {

    private static CodiPing instance;
    private static final int BSTATS_PLUGIN_ID = 30057;
    private boolean isFolia;

    @Override
    public void onEnable() {
        instance = this;

        // Detect if running on Folia
        try {
            Class.forName("io.papermc.paper.threadedregions.RegionizedServer");
            isFolia = true;
            getLogger().info("Folia detected! Region-based features enabled.");
        } catch (ClassNotFoundException e) {
            isFolia = false;
            getLogger().info("Running on Paper/Spigot. Standard features enabled.");
        }

        // Register PlaceholderAPI expansion
        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PingExpansion(this).register();
            getLogger().info("PlaceholderAPI expansion registered successfully!");
        } else {
            getLogger().warning("PlaceholderAPI not found! This plugin requires PlaceholderAPI to work.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // Initialize bStats metrics
        Metrics metrics = new Metrics(this, BSTATS_PLUGIN_ID);
        metrics.addCustomChart(new SimplePie("server_type", () -> isFolia ? "Folia" : "Paper/Spigot"));

        // Register chat listener for [ping] replacement
        getServer().getPluginManager().registerEvents(new ChatListener(this), this);
        getLogger().info("Chat listener registered! Players can now use [ping] in chat.");

        // Register /ping command
        PingCommand pingCommand = new PingCommand();
        getCommand("ping").setExecutor(pingCommand);
        getCommand("ping").setTabCompleter(pingCommand);
        getLogger().info("/ping command registered!");
    }

    @Override
    public void onDisable() {
        getLogger().info("CodiPing has been disabled.");
    }

    public static CodiPing getInstance() {
        return instance;
    }

    public boolean isFolia() {
        return isFolia;
    }
}
