package org.eldrygo.LTeams.Handlers;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.eldrygo.LTeams.Managers.TeamGroupManager;
import org.eldrygo.LTeams.Utils.ChatUtils;
import org.eldrygo.LTeams.Utils.LoadUtils;
import org.eldrygo.XTeams.API.XTeamsAPI;
import org.eldrygo.XTeams.Models.Team;
import org.jetbrains.annotations.NotNull;

public class LTeamsCommand implements CommandExecutor {
    private final ChatUtils chatUtils;
    private final LoadUtils loadUtils;
    private final TeamGroupManager groupManager;

    public LTeamsCommand(ChatUtils chatUtils, LoadUtils loadUtils, TeamGroupManager groupManager) {
        this.chatUtils = chatUtils;
        this.loadUtils = loadUtils;
        this.groupManager = groupManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(chatUtils.getMessage("error.no_action", null));
            return true;
        }

        String action = args[0].toLowerCase();

        switch (action) {
            case "reload" -> {
                if (!sender.hasPermission("lteams.reload") && !sender.isOp()) {
                    sender.sendMessage(chatUtils.getMessage("error.no_permission", (sender instanceof Player p) ? p : null));
                } else {
                    handleReload(sender);
                }
            }
            case "sync" -> {
                if (!sender.hasPermission("lteams.sync") && !sender.isOp()) {
                    sender.sendMessage(chatUtils.getMessage("error.no_permission", (sender instanceof Player p) ? p : null));
                } else {
                    handleSync(sender);
                }
            }
            default -> sender.sendMessage(chatUtils.getMessage("error.invalid_action", null));
        }
        return true;
    }

    private void handleReload(CommandSender sender) {
        Player target = (sender instanceof Player) ? (Player) sender : null;
        try {
            loadUtils.loadFiles();
        } catch (Exception e) {
            sender.sendMessage(chatUtils.getMessage("command.reload.error", target));
            return;
        }
        sender.sendMessage(chatUtils.getMessage("command.reload.success", target));
    }

    private void handleSync(CommandSender sender) {
        int count = 0;
        for (String teamName : XTeamsAPI.listTeams()) {
            Team team = XTeamsAPI.getTeamByName(teamName);
            for (String memberName : team.getMembers()) {
                Player player = Bukkit.getPlayerExact(memberName);
                if (player != null && player.isOnline()) {
                    groupManager.applyGroup(player, team.getName());
                    count++;
                }
            }
        }

        sender.sendMessage(chatUtils.getMessage("command.sync.success", (Player) sender).replace("%count%", String.valueOf(count)));
    }
}