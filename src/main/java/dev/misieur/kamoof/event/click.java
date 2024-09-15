package dev.misieur.kamoof.event;

import dev.misieur.kamoof.Kamoofplugin;
import dev.misieur.kamoof.db;
import dev.misieur.kamoof.kamoof;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class click implements Listener {

    private final Kamoofplugin plugin;

    public click(Kamoofplugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        ItemStack item = p.getItemInHand();
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_AIR){
            if (!p.isSneaking() && item.getType() == Material.PLAYER_HEAD){
                if (db.iskamoof(p.getUniqueId())){
                    String msg = plugin.getConfig().getString("message.already-disguised");
                    p.sendMessage(msg);
                }
                else{
                    event.setCancelled(true);
                    SkullMeta skullMeta = (SkullMeta) item.getItemMeta();
                    OfflinePlayer Owner = skullMeta.getOwningPlayer();
                    kamoof.kamoofplayer(p,Owner.getUniqueId());
                    String msg = plugin.getConfig().getString("message.disguise");
                    p.sendMessage(msg);
                    if (plugin.getConfig().getBoolean("right-click-remove-head")) {
                        if (item.getAmount() == 1) {
                            p.setItemInHand(null);
                        } else {
                            item.setAmount(item.getAmount() - 1);
                        }
                    }
                }
            }
        }
    }
}
