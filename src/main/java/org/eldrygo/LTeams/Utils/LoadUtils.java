package org.eldrygo.LTeams.Utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.eldrygo.LTeams.Handlers.LTeamsCommand;
import org.eldrygo.LTeams.Handlers.LTeamsTabCompleter;
import org.eldrygo.LTeams.LTeams;
import org.eldrygo.LTeams.Listeners.TeamEventListener;
import org.eldrygo.LTeams.Managers.ConfigManager;
import org.eldrygo.LTeams.Managers.TeamGroupManager;
import org.eldrygo.XTeams.API.XTeamsAPI;
import org.eldrygo.XTeams.Models.Team;

public class LoadUtils {

    private final ConfigManager configManager;
    private final LTeams plugin;
    private final ChatUtils chatUtils;
    private final TeamGroupManager teamGroupManager;

    public LoadUtils(ConfigManager configManager, LTeams plugin, ChatUtils chatUtils, TeamGroupManager teamGroupManager) {
        this.configManager = configManager;
        this.plugin = plugin;
        this.chatUtils = chatUtils;
        this.teamGroupManager = teamGroupManager;
    }

    public void loadFeatures() {
        loadFiles();
        loadCommand();
        loadListeners();
        syncGroups();
    }

    private void loadListeners() {
        plugin.getServer().getPluginManager().registerEvents(new TeamEventListener(plugin), plugin);
    }

    public void loadFiles() {
        configManager.loadConfig();
        configManager.reloadMessages();
        configManager.setPrefix(configManager.getMessageConfig().getString("prefix"));
    }

    private void loadCommand() {
        if (plugin.getCommand("lteams") != null) {
            plugin.getLogger().info("✅ Plugin command /lteams successfully registered.");
            plugin.getCommand("lteams").setExecutor(new LTeamsCommand(chatUtils,this, teamGroupManager));
            plugin.getCommand("lteams").setTabCompleter(new LTeamsTabCompleter());
        } else {
            plugin.getLogger().severe("❌ Error: /lteams command is no registered in plugin.yml");
        }
    }

    private void syncGroups() {
        for (String teamName : XTeamsAPI.listTeams()) {
            Team team = XTeamsAPI.getTeamByName(teamName);
            for (String memberName : team.getMembers()) {
                Player player = Bukkit.getPlayerExact(memberName);
                if (player != null && player.isOnline()) {
                    teamGroupManager.applyGroup(player, team.getName());
                }
            }
        }
    }
}
