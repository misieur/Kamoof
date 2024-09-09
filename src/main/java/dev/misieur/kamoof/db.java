package dev.misieur.kamoof;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class db {
    public class Profile {
        String name;
        List<Properties> properties;
    }
    public class Properties{
        String value;
        String signature;
    }
    private static Kamoofplugin plugin;

    public db(Kamoofplugin plugin) {
        this.plugin = plugin;
    }
    public static boolean iskamoof(UUID player) {
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "db.yml"));
        return yml.contains("kamoofplayer."+ player);
    }
    public static void setkamoof(UUID player,UUID kamoofplayer){
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "db.yml"));
        yml.set("kamoofplayer."+player, kamoofplayer.toString());
        try {
            yml.save(new File(plugin.getDataFolder(), "db.yml"));
        } catch (IOException e) {
            plugin.getLogger().severe(e.getMessage());
        }
    }
    public static void removekamoof(UUID player){
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "db.yml"));
        yml.set("kamoofplayer."+player, null);
        try {
            yml.save(new File(plugin.getDataFolder(), "db.yml"));
        } catch (IOException e) {
            plugin.getLogger().severe(e.getMessage());
        }
    }
    public static UUID getkamoof(UUID player){
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "db.yml"));
        return UUID.fromString(yml.getString("kamoofplayer."+ player));
    }
    public static void setProfile(UUID uuid, Profile profile){
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "db.yml"));
        yml.set("profile."+ uuid+".name", profile.name);
        yml.set("profile."+ uuid+".value", profile.properties.get(0).value);
        yml.set("profile."+ uuid+".signature", profile.properties.get(0).signature);
        try {
            yml.save(new File(plugin.getDataFolder(), "db.yml"));
        } catch (IOException e) {
            plugin.getLogger().severe(e.getMessage());
        }
    }
    public static String getName(UUID uuid){
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "db.yml"));
        return yml.getString("profile."+ uuid+".name");
    }
    public static String getValue(UUID uuid){
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "db.yml"));
        return yml.getString("profile."+ uuid+".value");
    }
    public static String getSignature(UUID uuid){
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "db.yml"));
        return yml.getString("profile."+ uuid+".signature");
    }
    public static boolean containsProfile(UUID uuid){
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "db.yml"));
        return yml.contains("profile."+ uuid);
    }

}
