package me.arouraios.crystal;

import java.util.logging.Logger;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import me.arouraios.worlds.WorldManager;

public class Main extends JavaPlugin{
	private Logger log;
	private FileConfiguration conf;
	private WorldManager WM;
	private Server serv;

	@Override
	public void onEnable(){
		serv = getServer();
		conf = getConfig();
		log = getLogger();
		log.info("Crystal enabled!");
		WM = new WorldManager(log, conf, serv);
	}

	@Override
	public void onDisable(){
		log.info("Crystal stopped!");
	}

	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent e) 
	{
		Location loc = new Location(getServer().getWorld("world"), 0.5, 100.5, 0.5);
		e.getPlayer().teleport(loc);
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		Player s = (Player) sender;
		
		if(cmd.getName().equalsIgnoreCase("reloadworld")){
			return WM.reloadWorld(args);
		}
		
		else if(cmd.getName().equalsIgnoreCase("deleteworld")) {
			return WM.unloadWorld(args);
		}
		
		else if(cmd.getName().equalsIgnoreCase("loadworld")) {
			return WM.loadWorld(args);
		}
		
		else if(cmd.getName().equalsIgnoreCase("copyBackup")) {
			return WM.copyBackup(args);
		}
		
		else if(cmd.getName().equalsIgnoreCase("tpallto")) {
			World wo = getServer().getWorld(args[0]);
			Location loc = new Location(wo, Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));
			for(Player pl : wo.getPlayers()) {
				pl.teleport(loc);
			}
			return true;
		}
		
		else if(cmd.getName().equalsIgnoreCase("getWorlds")) {
			for(World wo : getServer().getWorlds()) {
				s.sendRawMessage(wo.getName());
			}
			return true;
		}
		
		return false;
	}
}