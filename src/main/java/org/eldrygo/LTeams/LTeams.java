package org.eldrygo.LTeams;

import org.bukkit.plugin.java.JavaPlugin;
import org.eldrygo.LTeams.Managers.ConfigManager;
import org.eldrygo.LTeams.Managers.TeamGroupManager;
import org.eldrygo.LTeams.Utils.ChatUtils;
import org.eldrygo.LTeams.Utils.LoadUtils;
import org.eldrygo.LTeams.Utils.LogsUtils;

public class LTeams extends JavaPlugin {
    public String version;
    public String prefix;
    private LogsUtils logsUtils;
    private TeamGroupManager teamGroupManager;

    @Override
    public void onEnable() {
        version = getDescription().getVersion();
        ConfigManager configManager = new ConfigManager(this);
        ChatUtils chatUtils = new ChatUtils(configManager, this);
        this.teamGroupManager = new TeamGroupManager(this);
        LoadUtils loadUtils = new LoadUtils(configManager, this, chatUtils, teamGroupManager);
        loadUtils.loadFeatures();
        this.logsUtils = new LogsUtils(this);
        logsUtils.sendStartupMessage();
    }

    @Override
    public void onDisable() {
        logsUtils.sendShutdownMessage();
    }

    public TeamGroupManager getGroupManager() { return teamGroupManager; }
}
