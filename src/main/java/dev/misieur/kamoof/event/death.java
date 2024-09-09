package dev.misieur.kamoof.event;

import dev.misieur.kamoof.Kamoofplugin;
import dev.misieur.kamoof.db;
import dev.misieur.kamoof.kamoof;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class death implements Listener {

    private final Kamoofplugin plugin;
    public death(Kamoofplugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player p = event.getEntity();
        if (db.iskamoof(p.getUniqueId())) {
            kamoof.unkamoof(p);
            p.sendMessage("§c❌ Vous n'êtes maitenant plus déguisé");
        }
        Location loc = p.getLocation();
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        skullMeta.setOwningPlayer(p);
        skull.setItemMeta(skullMeta);
        loc.getWorld().dropItem(loc,skull);
    }
}
