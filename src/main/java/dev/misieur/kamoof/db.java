package dev.misieur.kamoof;

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
        return plugin.getConfig().contains("db.kamoofplayer."+ player);
    }
    public static void setkamoof(UUID player,UUID kamoofplayer){
        plugin.getConfig().set("db.kamoofplayer."+player, kamoofplayer.toString());
        plugin.saveConfig();
    }
    public static void removekamoof(UUID player){
        plugin.getConfig().set("db.kamoofplayer."+player, null);
        plugin.saveConfig();
    }
    public static UUID getkamoof(UUID player){
        return UUID.fromString(plugin.getConfig().getString("db.kamoofplayer."+player));
    }
    public static void setProfile(UUID uuid, Profile profile){
        plugin.getConfig().set("db.profile."+ uuid+".name", profile.name);
        plugin.getConfig().set("db.profile."+ uuid+".value", profile.properties.get(0).value);
        plugin.getConfig().set("db.profile."+ uuid+".signature", profile.properties.get(0).signature);
        plugin.saveConfig();
    }
    public static String getName(UUID uuid){
        return plugin.getConfig().getString("db.profile."+ uuid+".name");
    }
    public static String getValue(UUID uuid){
        return plugin.getConfig().getString("db.profile."+ uuid+".value");
    }
    public static String getSignature(UUID uuid){
        return plugin.getConfig().getString("db.profile."+ uuid+".signature");
    }
    public static boolean containsProfile(UUID uuid){
        return plugin.getConfig().contains("db.profile."+ uuid);
    }

}
