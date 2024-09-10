package dev.misieur.kamoof.command;

import dev.misieur.kamoof.Kamoofplugin;
import dev.misieur.kamoof.db;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import dev.misieur.kamoof.kamoof;

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
                kamoof.unkamoof(p);
                String msg = plugin.getConfig().getString("message.undisguise");
                p.sendMessage(msg);
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
