package me.arouraios.crystal;

import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Server;
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
public class WorldManager{
	World[] worlds = new World[3];
	Logger log;
	Server s;
	
	public WorldManager(Logger pLog, Server pS) {
		log = pLog;
		s = pS;
	}
	
	public void setWorlds() {
		int i = 0;
		worlds = new World[s.getWorlds().size()];
		for(World w: s.getWorlds()) {
			worlds[i]=w;
			i++;
		}
	}
	
	public void reloadWorld(String world) {
		unloadWorld(world);
		log.info(world + " unloaded"); 
		deleteWorld(world);
		log.info(world + " deleted");
		copyBackup(world);
		log.info(world + " copied");
		loadWorld(world);
		log.info(world + " loaded");
	}
	
	public void copyBackup(String world) {
		File src = new File("./backups/" + world);
		File dir = new File("./" + world);
		try {
			FileUtils.copyDirectory(src, dir);
			log.info("Folders copied successfully");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadWorld(String world){
		World wo = s.createWorld(new WorldCreator(world));
		log.info(wo.getName());
	}
	
	public void unloadWorld(String world) {
		World wo = s.getWorld(world);
		if(wo != null) {
			log.info(wo.getName() + " is loaded. Testing for players.");
			if(wo.getPlayers().size() == 0) 
			{
				log.info("No players online. Procceding to unload");
				log.info(wo.getName() + "unloaded: " + Boolean.toString(Bukkit.unloadWorld(s.getWorld(world).getName(), false)));
			}
			else 
			{
				log.warning("There are still players on world " + wo.getName());
			}
		}
		else
		{
			log.warning("World " + world + " doesn't exist");
		}
	}
	
	public void deleteWorld(String world) {
		if(s.getWorld(world) == null) {
			try 
			{
				FileUtils.deleteDirectory(new File("./" + world));
				log.info("Folder deleted");
			} 
			catch (IOException e) 
			{
				log.warning("Couldn't delete folder");
				e.printStackTrace();
			}
		}
		else {
			log.warning("World " + world + " is still loaded!");
		}
	}
	
	public void resetCustomWorlds() {
		for(World wo: s.getWorlds()) {
			if(wo.getName()!= "world" && wo.getName() != "world_the_end" && wo.getName() != "world_nether") {
				String name = wo.getName();
				unloadWorld(name);
				deleteWorld(name);
				loadBackup(name);
			}
		}
	}
	
	public void loadBackup(String world) {
		File dir = new File("./" + world);
		File src = new File("./backups/" + world);
		try {
			FileUtils.copyDirectory(src, dir);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		log.info("loadBackup: word " + world + " replaced by backup");
	}
}
