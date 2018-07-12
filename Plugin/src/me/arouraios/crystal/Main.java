package me.arouraios.crystal;

import java.io.Console;
import java.io.File;
import java.io.IOException;
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
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public class Main extends JavaPlugin{
	Logger log;
	WorldManager WM;
	File forLaterUse;
	FileConfiguration conf;
	Server s;
	
	@Override
	public void onEnable(){
		conf = getConfig();
		log = getLogger();
		s = getServer();
		
		WM = new WorldManager(log, s);
		loadforLaterUse();
		
		reloadConfig();
		conf.options().copyDefaults(true);
		saveConfig();

		WM.resetCustomWorlds();
		
		log.info("worlds created");
		log.info("Crystal enabled!");
	}

	@Override
	public void onDisable(){
		saveConfig();
//		WM.resetCustomWorlds();
		log.info("Crystal stopped!");
	}

	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent e) 
	{
		Location loc = new Location(getServer().getWorld("world"), 0.5, 100.5, 0.5);
//		e.getPlayer().teleport(loc);
		log.info("Player joined. Was teleported to default location");
	}

	public void loadforLaterUse() {
		forLaterUse = FileUtils.getFile("./forLaterUse.yml");
		if(!forLaterUse.exists()) { 
		    try {
		    	log.info("trying to create new forLaterUse.yml");
				forLaterUse.createNewFile();
			} catch (IOException e) {
				log.warning("couldn't create new forLaterUse.yml");
				e.printStackTrace();
			}
		}else {log.info("forLaterUse loaded");}
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

		else if(cmd.getName().equalsIgnoreCase("reloadworld"))
		{
			WM.reloadWorld(args[0]);
			return true;
		}
		
		else if(cmd.getName().equalsIgnoreCase("deleteworld")) 
		{
			WM.deleteWorld(args[0]);
			return true;
		}
		
		else if(cmd.getName().equalsIgnoreCase("unloadworld")) 
		{
			WM.unloadWorld(args[0]);
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
			WM.loadWorld(args[0]);
			return true;
		}
		
		else if(cmd.getName().equalsIgnoreCase("copyBackup")) 
		{
			WM.copyBackup(args[0]);
			return true;
		}
		return false;
	}
	
}