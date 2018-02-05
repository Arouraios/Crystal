package me.Plugins.Crystal;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
	private Logger log;
	
	@Override
	public void onEnable(){
		log = getLogger();
		log.info("Crystal enabled!");
		//reloadworld();
	}
	
	@Override
	public void onDisable(){
		log.info("Crystal stopped!");
	}
	
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if(cmd.getName().equalsIgnoreCase("reloadworld"))
		{
			
			reloadworld();
			return true;
		}
		return false;
	}
	
	void reloadworld()
	{
		 String name = getConfig().getString("defworld");
		 File folder = new File("Game Worlds/" + name);
	        try 
	        {
	            new File(name).mkdir();
	            FileUtils.copyDirectory(folder, new File(name));
	            Bukkit.getServer().createWorld(new WorldCreator(name));
	            log.info("Loading world " + name);
	            
	        } 
	        catch (IOException ex) 
	        {
	            log.warning("IO error while loading "+name);
	        }
	}
	       
}