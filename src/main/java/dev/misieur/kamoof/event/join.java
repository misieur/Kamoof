package dev.misieur.kamoof.event;

import com.google.gson.Gson;
import dev.iiahmed.disguise.Disguise;
import dev.misieur.kamoof.Kamoofplugin;
import dev.misieur.kamoof.UpdateChecker;
import dev.misieur.kamoof.db;
import dev.misieur.kamoof.kamoof;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TranslatableComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class join implements Listener {

    private final Kamoofplugin plugin;

    public join(Kamoofplugin plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void OnPlayerJoin(PlayerJoinEvent event){
        Player p = event.getPlayer();
        if (db.iskamoof(p.getUniqueId())&& !plugin.getConfig().getBoolean("undisguise-on-disconnection")){
            String msg = plugin.getConfig().getString("message.join");
            p.sendMessage(msg);
            boolean potioneffect = false;
            if (!p.hasPotionEffect(PotionEffectType.BLINDNESS)){
                p.addPotionEffect(PotionEffectType.BLINDNESS.createEffect(2,1));

            }
            for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                if (player != p) {
                    player.hidePlayer(plugin,p);
                    potioneffect = true;
                }
            }
            event.setJoinMessage(null);
            kamoof.kamoofplayer(p, db.getkamoof(p.getUniqueId()));
            if (db.containsProfile(db.getkamoof(p.getUniqueId()))){
                TranslatableComponent joinmassege = new TranslatableComponent("multiplayer.player.joined");
                joinmassege.addWith(db.getName(db.getkamoof(p.getUniqueId())));
                joinmassege.setColor(ChatColor.YELLOW);
                Bukkit.spigot().broadcast(joinmassege);
                for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                    if (player != p) {
                        player.showPlayer(plugin,p);
                    }
                }
                if (potioneffect){
                    p.removePotionEffect(PotionEffectType.BLINDNESS);
                }
            }
            else {
                boolean finalPotioneffect = potioneffect;
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        Gson gson = new Gson();
                        try{
                            URL url = new URL("https://sessionserver.mojang.com/session/minecraft/profile/"+db.getkamoof(p.getUniqueId())+"?unsigned=false");
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
                                    TranslatableComponent joinmassege = new TranslatableComponent("multiplayer.player.joined");
                                    joinmassege.addWith(db.getName(db.getkamoof(p.getUniqueId())));
                                    joinmassege.setColor(ChatColor.YELLOW);
                                    Bukkit.spigot().broadcast(joinmassege);
                                    for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                                        if (player != p) {
                                            player.showPlayer(plugin,p);
                                        }
                                    }
                                    if (finalPotioneffect){
                                        p.removePotionEffect(PotionEffectType.BLINDNESS);
                                    }
                                }
                            }.runTask(plugin);
                        } catch (IOException e) {
                            plugin.getLogger().severe(e.getMessage());
                        }
                    }
                }.runTaskAsynchronously(plugin);
            }
        }
        else{
            TranslatableComponent joinmassege = new TranslatableComponent("multiplayer.player.joined");
            joinmassege.addWith(p.getName());
            joinmassege.setColor(ChatColor.YELLOW);
            event.setJoinMessage(null);
            Bukkit.spigot().broadcast(joinmassege);
        }
        if (p.isOp() && plugin.getConfig().getBoolean("update-checker")){
            new UpdateChecker(plugin, 119296).getVersion(version -> {
                if (!plugin.getDescription().getVersion().equals(version)) {
                    p.sendMessage("Il y a une nouvelle version (la "+version+") vous pouvez la télécharger ici -> https://spigotmc.org/resources/119296/");
                }
            });
        }
    }
    @EventHandler
    public void OnPlayerQuite(PlayerQuitEvent event){
        Player p = event.getPlayer();
        if (db.iskamoof(p.getUniqueId())){
            kamoof.unkamoofwithoutremovefromdb(p);
        }
    }
}
