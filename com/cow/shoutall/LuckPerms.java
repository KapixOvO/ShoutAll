package com.cow.shoutall;

import java.util.List;

import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class LuckPerms
{
    public static net.luckperms.api.LuckPerms api = null;

    public static void init()
    {
        api = LuckPermsProvider.get();
    }

    public static String getPlayerGroup(ProxiedPlayer player, List<String> possibleGroups)
    {
        for (String group : possibleGroups) {
            if (player.hasPermission("group." + group)) {
                return group;
            }
        }
        return null;
    }

    public static String getGroupRank(ProxiedPlayer player)
    {
        try
        {
            User user = api.getUserManager().getUser(player.getUniqueId());
            return user.getCachedData().getMetaData().getPrefix()
                    .replace("&", "§");
        }
        catch (Exception e) {}
        return "§e";
    }
}
