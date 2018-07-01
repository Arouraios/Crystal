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
		log.info("world2: " + getServer().createWorld(new WorldCreator("world2")));
		log.info("world2: " + getServer().createWorld(new WorldCreator("world3")));
		log.info("worlds created");
	}

	@Override
	public void onDisable(){
		resetCustomWorlds();
		log.info("Crystal stopped!");
	}

	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent e) 
	{
		Location loc = new Location(getServer().getWorld("world"), 0.5, 100.5, 0.5);
		e.getPlayer().teleport(loc);
		log.info("Player joined. Was teleported to default location");
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		Player s;
		World w;
		if(sender instanceof Player) {
			s = ((Player) sender);
			w = s.getWorld();
		}
		else {
			s=null;
		}
		
		if(cmd.getName().equalsIgnoreCase("tpalltoplayer")) 
		{
			Location loc = s.getLocation();
			for(Player pl : getServer().getOnlinePlayers()) 
			{
				pl.teleport(loc);
				log.info("Player " + pl + "was teleported to " + s.getName());
			}
			return true;
		}
		
		else if(cmd.getName().equalsIgnoreCase("tpalltoloc")) {
			Location loc = new Location(getServer().getWorld(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));
			for(Player pl : getServer().getOnlinePlayers()) 
			{
				pl.teleport(loc);
				log.info("Player " + pl + "was teleported to " + s.getName());
			}
			return true;
		}

//		else if(cmd.getName().equalsIgnoreCase("reloadworld"))
//		{
//			//TODO: summarize all steps
//			//reload();
//			return true;
//		}
		
		else if(cmd.getName().equalsIgnoreCase("deleteworld")) 
		{
			World wo = getServer().getWorld(args[0]);
			if(wo != null) {
				log.info(wo.getName());
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
						log.warning("Bukkit.unload was false");
					}
				}
				else 
				{
					log.warning("still players on world");
				}
			}
			else {
				log.warning("World " + args[0] + " isn't loaded");
				log.info("trying to delete files anyway...");
				File unloadedWorld = new File("./" + args[0]);
				if(unloadedWorld != null) {
					try {
						FileUtils.deleteDirectory(unloadedWorld);
					} catch (IOException e) {
						log.warning("something went wrong");
						e.printStackTrace();
					}
					log.info("It worked! The directory of the unloaded world has been removed");
				}
				else {
					log.warning("nope, no directory of that name found");
				}
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
			World wo = getServer().createWorld(new WorldCreator(args[0]));
			log.info(wo.getName());
			return true;
		}
		
		else if(cmd.getName().equalsIgnoreCase("copyBackup")) 
		{
			File src = new File("./backups/" + args[0]);
			File dir = new File("./" + args[0]);
			try {
				FileUtils.copyDirectory(src, dir);
				log.info("Folders copied successfully");
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
			log.info("world2 war nicht null, Welten wurden entladen");
		}
		else {
			log.warning("world2 was null");
		}
		if(getServer().getWorld("world3") != null){
			log.info("world3: " + Boolean.toString(Bukkit.unloadWorld(getServer().getWorld("world3").getName(), false)));
			log.info("world3 war nicht null, Welten wurden entladen");
		}
		else {
			log.warning("world3 was null");
		}
		loadBackup("world2");
		loadBackup("world3");
		log.info("Worlds were reset");
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