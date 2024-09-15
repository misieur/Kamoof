package dev.misieur.kamoof.event;

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
import org.bukkit.scheduler.BukkitRunnable;

public class join implements Listener {

    private final Kamoofplugin plugin;

    public join(Kamoofplugin plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void OnPlayerJoin(PlayerJoinEvent event){
        Player p = event.getPlayer();
        if (db.iskamoof(p.getUniqueId())&& !plugin.getConfig().getBoolean("undisguise-on-disconnection")){
            TranslatableComponent joinmassege = new TranslatableComponent("multiplayer.player.joined");
            joinmassege.addWith(db.getName(db.getkamoof(p.getUniqueId())));
            joinmassege.setColor(ChatColor.YELLOW);
            event.setJoinMessage(null);
            Bukkit.spigot().broadcast(joinmassege);
            String msg = plugin.getConfig().getString("message.join");
            p.sendMessage(msg);
            new BukkitRunnable() {
                @Override
                public void run() {
                    kamoof.kamoofplayer(p, db.getkamoof(p.getUniqueId()));
                }
            }.runTaskLater(plugin,1L); //Pour éviter le "You logged in from another location"
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
}
