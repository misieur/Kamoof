package dev.misieur.kamoof;

import dev.misieur.kamoof.command.unkamoof;
import dev.misieur.kamoof.event.click;
import dev.misieur.kamoof.event.death;
import dev.misieur.kamoof.event.join;
import org.bukkit.plugin.java.JavaPlugin;

public final class Kamoofplugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("Démarrage du plugin");
        getServer().getPluginManager().registerEvents(new death(this),this);
        getServer().getPluginManager().registerEvents(new click(this),this);
        getServer().getPluginManager().registerEvents(new join(this),this);
        getCommand("unkamoof").setExecutor(new unkamoof(this));

        Metrics metrics = new Metrics(this, 	23253);

        new UpdateChecker(this, 119296).getVersion(version -> {
            if (this.getDescription().getVersion().equals(version)) {
                getLogger().info("Le plugin est à jour !");
            } else {
                getLogger().info("Il y a une nouvelle version (la "+version+") vous pouvez la télécharger ici -> https://spigotmc.org/resources/119296/");
            }
        });

        new db(this);
        new kamoof(this);

        saveResource("db.yml",false);

        saveDefaultConfig();

    }

    @Override
    public void onDisable() {

    }
}
