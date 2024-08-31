package dev.misieur.kamoof;

import dev.misieur.kamoof.command.unkamoof;
import dev.misieur.kamoof.event.click;
import dev.misieur.kamoof.event.death;
import dev.misieur.kamoof.event.join;
import dev.misieur.kamoof.db;
import org.bukkit.plugin.java.JavaPlugin;

public final class Kamoofplugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("Démarrage du plugin");
        getServer().getPluginManager().registerEvents(new death(this),this);
        getServer().getPluginManager().registerEvents(new click(this),this);
        getServer().getPluginManager().registerEvents(new join(this),this);
        getCommand("unkamoof").setExecutor(new unkamoof(this));

        new db(this);
        new kamoof(this);

        saveDefaultConfig();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
