package me.Plugins.Crystal;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public class Main extends JavaPlugin{
	private Logger log;

	@Override
	public void onEnable(){
		log = getLogger();
		log.info("Crystal enabled!");
		reload();
	}

	@Override
	public void onDisable(){
		log.info("Crystal stopped!");
	}

	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent e) {
		Location loc = new Location(getServer().getWorld("world2"), 0.5, 100.5, 0.5);
		e.getPlayer().teleport(loc);
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		Player p = (Player) sender;
		if(cmd.getName().equalsIgnoreCase("reloadworld"))
		{
			reload();
			return true;
		}
		if(cmd.getName().equalsIgnoreCase("menno"))
		{
			Location loc = new Location(getServer().getWorld("world2"), 1000.5, 70.0, 0.5);
			p.teleport(loc);
			return true;
		}
		return false;
	}

	public void reload() {
		File srcDir = new File(Bukkit.getWorldContainer().getPath() + "/backup");
		if (!srcDir.exists()) {
			Bukkit.getLogger().warning("Backup does not exist!");
			return;
		}
		File destDir = new File(Bukkit.getWorldContainer().getPath()+"/world2");
		try {
			delete();
			FileUtils.copyDirectory(srcDir, destDir);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		Bukkit.getServer().createWorld(new WorldCreator("world2"));
	}

	public void delete() {
		for(Player p : getServer().getOnlinePlayers()) {
			p.kickPlayer("Reloading World");
		}
		try {
			Bukkit.getServer().unloadWorld("world2", true);
			File dir = new File(getServer().getWorldContainer().getPath()+"/world2");
			try {
				FileUtils.deleteDirectory(dir);
			}
			catch (IOException ex) {
				ex.printStackTrace();
			}
		}catch(NullPointerException e) {
			log.warning("world not yet loaded");
		}
	}
}