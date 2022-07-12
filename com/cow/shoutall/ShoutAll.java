package com.cow.shoutall;

import java.util.HashMap;
import java.util.Map;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;

public final class ShoutAll
        extends Plugin
{
    private static ShoutAll instance = null;
    private Map<ProxiedPlayer, Data> allData = new HashMap();

    public static ShoutAll getInstance()
    {
        return instance;
    }

    public void onLoad()
    {
        instance = this;
    }

    public void onEnable()
    {
        LuckPerms.init();

        ConfigManager.saveDefaultConfig();
        ConfigManager.reloadConfig();

        installCMD();
        getProxy().getPluginManager().registerListener(this, new PlayerListener());

        getLogger().info("§b==============================");
        getLogger().info("§e");
        getLogger().info("§6ShoutAll v" + getDescription().getVersion());
        getLogger().info("§aby CowNow");
        getLogger().info("§e");
        getLogger().info("§b==============================");
    }

    public void onDisable() {}

    private void installCMD()
    {
        getProxy().getPluginManager().registerCommand(this, new ShoutCMD("shoutall", "shoutall.shout", new String[] { "hh" }));
        getProxy().getPluginManager().registerCommand(this, new TpCMD("tpserver", "shoutall.tp", new String[0]));

        getProxy().getPluginManager().registerCommand(this, new Command("shoutreload", "shoutall.reload", new String[0])
        {
            public void execute(CommandSender sender, String[] args)
            {
                ConfigManager.reloadConfig();
                sender.sendMessage(ConfigManager.getReloadSuccess());
            }
        });
    }

    public Map<ProxiedPlayer, Data> getAllData()
    {
        return this.allData;
    }

}
