package com.cow.shoutall;

import java.util.UUID;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ShoutCMD
        extends Command
{
    private static final String PERMISSION_PREFIX = "shoutall.bypass.";

    public ShoutCMD(String name, String permission, String... aliases)
    {
        super(name, permission, aliases);
    }

    public void execute(CommandSender sender, String[] args)
    {
        if (!(sender instanceof ProxiedPlayer))
        {
            sender.sendMessage(ConfigManager.getConsole());
            return;
        }
        ProxiedPlayer player = (ProxiedPlayer)sender;
        Data data = (Data)ShoutAll.getInstance().getAllData().get(player);
        long before = data.time.longValue();
        long now = System.currentTimeMillis() / 1000L;
        int time = ConfigManager.getPermissionColdTime(_getPlayerPermission(player)) - (int)(now - before);
        if (time > 0)
        {
            player.sendMessage(ConfigManager.getMsg_A(time));
            return;
        }
        int neededTime = ConfigManager.getPermissionColdTime(PlayerPermission.NONE) - (int)(now - before);
        if ((neededTime + time > 0) && (_getPlayerPermission(player) != PlayerPermission.NONE)) {
            player.sendMessage(ConfigManager.getMsg_B(_getPlayerPermission(player), neededTime + time));
        }
        if (args.length == 0)
        {
            String server = player.getServer().getInfo().getName();
            if (ConfigManager.getServerName(server) != null){
                String token = UUID.randomUUID().toString().replace("-", "");
                TextComponent tc = new TextComponent();
                TextComponent tc1 = new TextComponent(ConfigManager.getShout1A().replace("<prefix>", LuckPerms.getGroupRank(player)).replace("<player>", player.getName()).replace("<server>", ConfigManager.getServerName(server)).replace("<bungee>", player.getServer().getInfo().getName()));
                TextComponent tcClicked = new TextComponent(ConfigManager.getShout1B());
                tcClicked.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpserver " + token));
                tcClicked.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ConfigManager.getShout1C()).create()));
                TextComponent tc2 = new TextComponent(ConfigManager.getShout1D());
                tc.addExtra(tc1);
                tc.addExtra(tcClicked);
                tc.addExtra(tc2);
                for (ProxiedPlayer player1 : ShoutAll.getInstance().getProxy().getPlayers()) {
                    player1.sendMessage(tc);
                }
                data.token = token;
                data.sender = sender.getName();
                data.server = ((ProxiedPlayer)sender).getServer().getInfo().getName();
                data.time = Long.valueOf(System.currentTimeMillis() / 1000L);
            }
            else
            {
                System.out.println(ConfigManager.getInvalid().replace("<player>", player.getName()).replace("<bungee>", player.getServer().getInfo().getName()));
                sender.sendMessage(ConfigManager.getFailed());
            }

        }
        else
        {
            String server = player.getServer().getInfo().getName();
            if (ConfigManager.getServerName(server) != null){
                StringBuilder sb = new StringBuilder(ConfigManager.getShout2A());
                sb.append(LuckPerms.getGroupRank(player));
                sb.append(player.getName());
                sb.append("§f: ");
                for (String arg : args)
                {
                    arg = arg.replace('&', '§').replace("§§", "&");
                    sb.append(arg);
                    sb.append(" ");
                }
                sb.append(ConfigManager.getShout2B());
                String token = UUID.randomUUID().toString().replace("-", "");
                TextComponent tc = new TextComponent(sb.toString());
                tc.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpserver " + token));
                tc.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ConfigManager.getShout2C().replace("<server>", ConfigManager.getServerName(server)).replace("<bungee>", player.getServer().getInfo().getName())).create()));
                for (ProxiedPlayer player1 : ShoutAll.getInstance().getProxy().getPlayers()) {
                    player1.sendMessage(tc);
                }
                data.token = token;
                data.sender = sender.getName();
                data.server = ((ProxiedPlayer)sender).getServer().getInfo().getName();
                data.time = Long.valueOf(System.currentTimeMillis() / 1000L);
            }
            else
            {
                System.out.println(ConfigManager.getInvalid().replace("<player>", player.getName()).replace("<bungee>", player.getServer().getInfo().getName()));
                sender.sendMessage(ConfigManager.getFailed());
            }

        }
    }

    private PlayerPermission _getPlayerPermission(ProxiedPlayer player)
    {
        if (_fuck1(player, "super")) {
            return PlayerPermission.SUPER;
        }
        if (_fuck1(player, "mvp_plus")) {
            return PlayerPermission.MVP_PLUS;
        }
        if (_fuck1(player, "mvp")) {
            return PlayerPermission.MVP;
        }
        if (_fuck1(player, "vip_plus")) {
            return PlayerPermission.VIP_PLUS;
        }
        if (_fuck1(player, "vip")) {
            return PlayerPermission.VIP;
        }
        return PlayerPermission.NONE;
    }

    private boolean _fuck1(ProxiedPlayer player, String str)
    {
        return player.hasPermission("shoutall.bypass." + str);
    }
}
