package me.Plugins.Crystal;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
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
	}

	@Override
	public void onDisable(){
		getServer().unloadWorld("world2", false);
		File world2 = new File("./world2");
		try {
			FileUtils.deleteDirectory(world2);
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		try{
			Player s = (Player) sender;
			World w = s.getWorld();
		}
		catch(ClassCastException e) {
			log.warning("Sender is Console");
		}
		if(cmd.getName().equalsIgnoreCase("reloadworld"))
		{
			//reload();
			return true;
		}
		else if(cmd.getName().equalsIgnoreCase("tpallto")) 
		{
			World wo = getServer().getWorld(args[0]);
			Location loc = new Location(wo, 1000.5, 100, 0.5);
			for(Player pl : getServer().getOnlinePlayers()) 
			{
				pl.teleport(loc);
			}

			return true;
		}
		else if(cmd.getName().equalsIgnoreCase("deleteworld")) 
		{
			World wo = getServer().getWorld(args[0]);
			if(wo != null) {
				if(wo.getPlayers().size() == 0) 
				{
					getServer().unloadWorld(wo, false);
					try 
					{
						FileUtils.deleteDirectory(wo.getWorldFolder());
					} 
					catch (IOException e) 
					{
						e.printStackTrace();
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
		else if(cmd.getName().equalsIgnoreCase("loadworld")) 
		{
			getServer().createWorld(new WorldCreator("./"+args[0]));
			return true;
		}
		else if(cmd.getName().equalsIgnoreCase("copyBackup")) 
		{
			File src = new File("./backups/" + args[0]);
			File dir = new File("./" + args[0]);
			getServer().unloadWorld(args[0], false);
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

	public void startup() {
		//getServer().unloadWorld("world", false);
		getServer().unloadWorld("world2", false);
		/*File src = new File("./backups/world");
		File dir = new File("./world");
		try {
			FileUtils.copyDirectory(src, dir);
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		File src = new File("./backups/world2");
		File dir = new File("./world2");
		try {
			FileUtils.deleteDirectory(dir);
			FileUtils.copyDirectory(src, dir);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//getServer().createWorld(new WorldCreator("world"));
		getServer().createWorld(new WorldCreator("world2"));
	}
}