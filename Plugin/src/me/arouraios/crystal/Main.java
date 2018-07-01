package me.arouraios.crystal;

import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
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
		resetCustomWorlds();
	}

	@Override
	public void onDisable(){
		resetCustomWorlds();
		log.info("Crystal stopped!");
	}

	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent e) 
	{
		Location loc = new Location(getServer().getWorld("world2"), 0.5, 100.5, 0.5);
		e.getPlayer().teleport(loc);
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		Player s;
		World w;
		
		if(sender instanceof Player) {
			s = ((Player) sender);
			w = s.getWorld();
		}
		
		if(cmd.getName().equalsIgnoreCase("reloadworld"))
		{
			//TODO: summarize all steps
			//reload();
			return true;
		}
		
//		else if(cmd.getName().equalsIgnoreCase("tpalltoplayer")) 
//		{
//			if(isPlayer) {
//				Location loc = s.getLocation();
//				for(Player pl : getServer().getOnlinePlayers()) 
//				{
//					pl.teleport(loc);
//				}
//			}
//			return true;
//		}
		
		else if(cmd.getName().equalsIgnoreCase("deleteworld")) 
		{
			log.info(args[0]);
			World wo = getServer().getWorld(args[0]);
			log.info(wo.getName());
			if(wo != null) {
				if(wo.getPlayers().size() == 0) 
				{
					if(Bukkit.unloadWorld(wo.getName(), false)) {
						try 
						{
							FileUtils.deleteDirectory(wo.getWorldFolder());
						} 
						catch (IOException e) 
						{
							e.printStackTrace();
						}
					}
					else {
						log.info("Bukkit.unload was false");
					}
				}
				else 
				{
					log.warning("still players on world");
				}
			}
			else {
				log.warning("World " + args[0] + " doesn't exist");
			}
			return true;
		}
		
		else if(cmd.getName().equalsIgnoreCase("getWorlds")) {
			for(World wo : getServer().getWorlds()) {
				log.info(wo.getName());
			}
			return true;
		}
		
		else if(cmd.getName().equalsIgnoreCase("loadworld")) 
		{
			getServer().createWorld(new WorldCreator(args[0]));
			return true;
		}
		
		else if(cmd.getName().equalsIgnoreCase("copyBackup")) 
		{
			File src = new File("./backups/" + args[0]);
			File dir = new File("./" + args[0]);
			Bukkit.unloadWorld(args[0], false);
			try 
			{
				FileUtils.deleteDirectory(dir);
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}

			try {
				FileUtils.copyDirectory(src, dir);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return true;
		}
		return false;
	}
	
	public void resetCustomWorlds() {
		if(getServer().getWorld("world2") != null){
			log.info("world2: " + Boolean.toString(Bukkit.unloadWorld(getServer().getWorld("world2").getName(), false)));
			log.info("world3: " + Boolean.toString(Bukkit.unloadWorld(getServer().getWorld("world3").getName(), false)));
			log.info("world2 war nicht null, Welten wurden entladen");
		}
		else {
			log.warning("world2 was null");
		}
		loadBackup("world2");
		loadBackup("world3");
		log.info("world2: " + getServer().createWorld(new WorldCreator("world2")));
		log.info("world2: " + getServer().createWorld(new WorldCreator("world3")));
		log.info("worlds created");
	}
	
	
	public void loadBackup(String world) {
		File src = new File("./" + world);
		File dir = new File("./backups/" + world);
		try {
			FileUtils.deleteDirectory(dir);
			FileUtils.copyDirectory(src, dir);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		log.info("loadBackup: word " + world + " replaced by backup");
	}
}