package dev.misieur.kamoof;

import com.google.gson.Gson;
import dev.iiahmed.disguise.Disguise;
import dev.iiahmed.disguise.DisguiseManager;
import dev.iiahmed.disguise.DisguiseProvider;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;
import java.util.UUID;

public class kamoof {
    private static Kamoofplugin plugin;
    private static DisguiseProvider provider = DisguiseManager.getProvider();
    public kamoof(Kamoofplugin plugin) {
        this.plugin = plugin;
        DisguiseManager.initialize(plugin, false);
        provider.checkOnlineNames(false);
    }
    public static void kamoofplayer(Player player,UUID kamoof){
        if (db.containsProfile(kamoof)){
            Disguise disguise = Disguise.builder()
                    .setName(db.getName(kamoof))
                    .setSkin(db.getValue(kamoof),db.getSignature(kamoof))
                    .build();
            provider.disguise(player,disguise);
            db.setkamoof(player.getUniqueId(),kamoof);
        }
        else{
            new BukkitRunnable() {
                @Override
                public void run() {
                    Gson gson = new Gson();
                    try{
                        URL url = new URL("https://sessionserver.mojang.com/session/minecraft/profile/"+kamoof+"?unsigned=false");
                        Scanner sc = new Scanner(url.openStream());
                        StringBuffer sb = new StringBuffer();
                        while(sc.hasNext()) {
                            sb.append(sc.next());
                        }
                        String result = sb.toString();
                        //plugin.getLogger().info(result);
                        db.Profile profile = gson.fromJson (result, db.Profile.class);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                db.setProfile(kamoof,profile);
                                Disguise disguise = Disguise.builder()
                                        .setName(profile.name)
                                        .setSkin(profile.properties.get(0).value,profile.properties.get(0).signature)
                                        .build();
                                provider.disguise(player,disguise);
                                db.setkamoof(player.getUniqueId(),kamoof);
                            }
                        }.runTask(plugin);
                    } catch (IOException e) {
                        plugin.getLogger().severe(e.getMessage());
                    }
                }
            }.runTaskAsynchronously(plugin);
        }
    }
    public static void unkamoof(Player player) {
        provider.undisguise(player);
        db.removekamoof(player.getUniqueId());
        player.setDisplayName(player.getName());
        player.setPlayerListName(player.getName());
    }
    public static void unkamoofwithoutremovefromdb(Player player) {
        provider.undisguise(player);
        player.setDisplayName(player.getName());
        player.setPlayerListName(player.getName());
    }
}
