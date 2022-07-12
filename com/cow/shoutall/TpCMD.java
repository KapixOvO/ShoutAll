package com.cow.shoutall;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class TpCMD
        extends Command
{
    public TpCMD(String name, String permission, String... aliases)
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
        if (args.length != 1)
        {
            sender.sendMessage(ConfigManager.getWrongArg());
            return;
        }
        Data data = null;
        for (Data tmpData : ShoutAll.getInstance().getAllData().values()) {
            if (tmpData.token.equals(args[0]))
            {
                data = tmpData;
                break;
            }
        }
        if ((data != null) && (180 - (int)(System.currentTimeMillis() / 1000L - data.time.longValue()) >= 0)) {
            ((ProxiedPlayer)sender).connect(ShoutAll.getInstance().getProxy().getServerInfo(data.server));
        } else {
            sender.sendMessage(new TextComponent(ConfigManager.TokenOutdated()));
        }
    }
}
