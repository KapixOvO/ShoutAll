package com.cow.shoutall;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class ConfigManager
{
    public static Configuration config = null;
    public static final String CONFIG_COLDTIME_PREFIX = "cooldown.";

    public static void saveDefaultConfig()
    {
        if (!ShoutAll.getInstance().getDataFolder().exists()) {
            ShoutAll.getInstance().getDataFolder().mkdir();
        }
        File file = new File(ShoutAll.getInstance().getDataFolder(), "config.yml");
        if (!file.exists()) {
            try
            {
                InputStream in = ShoutAll.getInstance().getResourceAsStream("config.yml");Throwable localThrowable3 = null;
                try
                {
                    Files.copy(in, file.toPath(), new CopyOption[0]);
                }
                catch (Throwable localThrowable1)
                {
                    localThrowable3 = localThrowable1;throw localThrowable1;
                }
                finally
                {
                    if (in != null) {
                        if (localThrowable3 != null) {
                            try
                            {
                                in.close();
                            }
                            catch (Throwable localThrowable2)
                            {
                                localThrowable3.addSuppressed(localThrowable2);
                            }
                        } else {
                            in.close();
                        }
                    }
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public static void reloadConfig()
    {
        try
        {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(ShoutAll.getInstance().getDataFolder(), "config.yml"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void saveConfig()
    {
        try
        {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, new File(ShoutAll.getInstance().getDataFolder(), "config.yml"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static int getPermissionColdTime(PlayerPermission permission)
    {
        if (permission == null) {
            return 180;
        }
        if (permission == PlayerPermission.SUPER) {
            return 0;
        }
        return config.getInt("cooldown." + permission.name().toLowerCase());
    }

    public static String getMsg_A(int sec)
    {
        return
                config.getString("Message.Cooldown").replace("&", "§").replace("<sec>", sec + "");
    }

    public static String getMsg_B(PlayerPermission permission, int msec)
    {
        return

                config.getString("Message.CooldownBypass").replace("&", "§").replace("<permission>", _getPermissionName(permission)).replace("<msec>", msec + "");
    }

    public static String getServerName(String server)
    {
        return config.contains("server." + server) ? config
                .getString("server." + server) : null;
    }

    public static String _getPermissionName(PlayerPermission permission)
    {
        switch (permission)
        {
            case SUPER:
                return "SUPER";
            case MVP_PLUS:
                return "MVP+";
            case MVP:
                return "MVP";
            case VIP_PLUS:
                return "VIP+";
            case VIP:
                return "VIP";
        }
        return "普通玩家";
    }

    // ShoutFormat-1
    public static String getShout1A(){
        return config.getString("ShoutFormat-1.ShoutMessage").replace("&", "§");
    }
    public static String getShout1B(){
        return config.getString("ShoutFormat-1.ClickMessage").replace("&", "§");
    }
    public static String getShout1C(){
        return config.getString("ShoutFormat-1.HoverMessage").replace("&", "§");
    }
    public static String getShout1D(){
        return config.getString("ShoutFormat-1.Footer").replace("&", "§");
    }

    // ShoutFormat-2
    public static String getShout2A(){
        return config.getString("ShoutFormat-2.ShoutPrefix").replace("&", "§");
    }
    public static String getShout2B(){
        return config.getString("ShoutFormat-2.ClickMessage").replace("&", "§");
    }
    public static String getShout2C(){
        return config.getString("ShoutFormat-2.HoverMessage").replace("&", "§");
    }


    public static String getConsole(){
        return config.getString("Message.PlayerOnly").replace("&", "§");
    }
    public static String getReloadSuccess(){
        return config.getString("Message.ReloadSuccess").replace("&", "§");
    }
    public static String getWrongArg(){
        return config.getString("Message.WrongArg").replace("&", "§");
    }
    public static String getInvalid(){
        return config.getString("Message.InvalidServer").replace("&", "§");
    }
    public static String getFailed(){
        return config.getString("Message.ShoutFailed").replace("&", "§");
    }
    public static String TokenOutdated(){
        return config.getString("Message.TokenOutdated").replace("&", "§");
    }
}
