package org.eldrygo.LTeams.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.eldrygo.LTeams.LTeams;
import org.eldrygo.XTeams.API.Events.TeamJoinEvent;
import org.eldrygo.XTeams.API.Events.TeamLeaveEvent;
import org.eldrygo.XTeams.API.XTeamsAPI;
import org.eldrygo.XTeams.Models.Team;

public class TeamEventListener implements Listener {

    private final LTeams plugin;

    public TeamEventListener(LTeams plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onTeamJoin(TeamJoinEvent event) {
        Player player = (Player) event.getPlayer();
        Team team = XTeamsAPI.getTeam(event.getTeamName());
        plugin.getGroupManager().applyGroup(player, team.getName());
    }

    @EventHandler
    public void onTeamLeave(TeamLeaveEvent event) {
        Player player = (Player) event.getPlayer();
        Team team = XTeamsAPI.getTeam(event.getTeamName());
        plugin.getGroupManager().removeGroup(player, team.getName());
    }
}
