package org.eldrygo.LTeams.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.eldrygo.API.Events.TeamJoinEvent;
import org.eldrygo.API.Events.TeamLeaveEvent;
import org.eldrygo.API.XTeamsAPI;
import org.eldrygo.LTeams.LTeams;
import org.eldrygo.Models.Team;

public class TeamEventListener implements Listener {

    private final LTeams plugin;

    public TeamEventListener(LTeams plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onTeamJoin(TeamJoinEvent event) {
        Player player = (Player) event.getPlayer();
        Team team = XTeamsAPI.getTeamByName(event.getTeamName());
        plugin.getGroupManager().applyGroup(player, team.getName());
    }

    @EventHandler
    public void onTeamLeave(TeamLeaveEvent event) {
        Player player = (Player) event.getPlayer();
        Team team = XTeamsAPI.getTeamByName(event.getTeamName());
        plugin.getGroupManager().removeGroup(player, team.getName());
    }
}
