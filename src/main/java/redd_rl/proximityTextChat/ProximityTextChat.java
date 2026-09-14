package redd_rl.proximityTextChat;

import org.bstats.bukkit.Metrics;
import org.bstats.charts.SingleLineChart;
import org.bukkit.plugin.java.JavaPlugin;
import redd_rl.proximityTextChat.listeners.AsyncPlayerChatListener;

import java.io.File;
import java.io.IOException;

public final class ProximityTextChat extends JavaPlugin {

    public Config.Settings settings;
    public Config.Messages messages;

    int totalMessagesLost = 0;

    @Override
    public void onEnable() {

        loadConfig();

        // METRICS!
        int pluginId = 34069;
        Metrics metrics = new Metrics(this, pluginId);

        metrics.addCustomChart(new SingleLineChart(
                "total_unheard_messages", () -> {
                    return totalMessagesLost;
        }
        ));

        getServer().getPluginManager().registerEvents(new AsyncPlayerChatListener(this), this);
    }

    public void incrementLostMessages() {
        totalMessagesLost++;
    }

    public void loadConfig() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }

        File configFile = new File(getDataFolder(), "config.toml");

        if (!configFile.exists()) {
            if (getResource("config.toml") != null) {
                saveResource("config.toml", false);
            } else {
                try {
                    configFile.createNewFile();
                } catch (IOException e) {
                    getLogger().severe("Failed to create config.toml!");
                    e.printStackTrace();
                }
            }
        }
        Config config = Config.load(configFile);
        this.settings = config.settings;
        this.messages = config.messages;
    }

    @Override
    public void onDisable() {}
}
