package org.eldrygo.LTeams.Managers;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.eldrygo.LTeams.LTeams;
import org.eldrygo.LTeams.Utils.ChatUtils;

import java.io.File;

public class ConfigManager {
    private final LTeams plugin;
    private FileConfiguration messagesConfig;

    public ConfigManager(LTeams plugin) { this.plugin = plugin; }

    public void loadConfig() {
        try {
            plugin.saveDefaultConfig();
            plugin.reloadConfig();
            plugin.getLogger().info("✅ The config.yml file successfully loaded.");
        } catch (Exception e) {
            plugin.getLogger().severe("❌ Failed on loading config.yml: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void reloadMessages() {
        try {
            File messagesFile = new File(plugin.getDataFolder(), "messages.yml");
            if (!messagesFile.exists()) {
                plugin.saveResource("messages.yml", false);
                plugin.getLogger().info("✅ The messages.yml file did not exist, it has been created.");
            } else {
                plugin.getLogger().info("✅ The messages.yml file has been loaded successfully.");
            }

            messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);
            plugin.prefix = ChatUtils.formatColor("#8eff39&lL&r&lTeams &cDefault Prefix &8»&r");
        } catch (Exception e) {
            plugin.getLogger().severe("❌ Failed to load messages configuration due to an unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public String getPrefix() { return plugin.prefix; }
    public void setPrefix(String prefix) { plugin.prefix = prefix; }
    public FileConfiguration getMessageConfig() {
        return messagesConfig;
    }
}
