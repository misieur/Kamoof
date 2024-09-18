package dev.misieur.kamoof.command;

import dev.misieur.kamoof.Kamoofplugin;
import dev.misieur.kamoof.db;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.TranslatableComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import dev.misieur.kamoof.kamoof;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class unkamoof implements CommandExecutor {

    private final Kamoofplugin plugin;
    public unkamoof(Kamoofplugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            Player p = (Player) sender;
            if (db.iskamoof(p.getUniqueId())) {
                if (plugin.getConfig().getBoolean("unkamoof-give-back-the-head")){
                    ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
                    SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
                    skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(db.getkamoof(p.getUniqueId())));
                    skull.setItemMeta(skullMeta);
                    p.getInventory().addItem(skull.clone());
                    kamoof.unkamoof(p);
                    String msg = plugin.getConfig().getString("message.undisguise");
                    p.sendMessage(msg);
                }
                else {
                    kamoof.unkamoof(p);
                    String msg = plugin.getConfig().getString("message.undisguise");
                    p.sendMessage(msg);
                }
            }
            else{
                String msg = plugin.getConfig().getString("message.not-disguised");
                p.sendMessage(msg);
            }
            return true;
        }
        else{
            return false;
        }
    }
}
