package me.arouraios.worlds;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.FileConfiguration;

public class WorldManager {
	private Logger log;
	@SuppressWarnings("unused")
	private FileConfiguration conf;
	private Server serv;
	
	public WorldManager(Logger pLog, FileConfiguration pConf, Server pServ) {
		log=pLog;
		conf=pConf;
		serv=pServ;
	}
	
	public boolean reloadWorld(String[] args) {
		unloadWorld(args);
		copyBackup(args);
		loadWorld(args);
		
		return false;
	}
	
	public boolean unloadWorld(String[] args) {
		World wo = serv.getWorld(args[0]);
		if(wo != null) {
			log.info("World " + wo.getName() + " exists. Unloading...");
			if(wo.getPlayers().size() == 0){
				serv.unloadWorld(wo, false);
				try {
					FileUtils.deleteDirectory(wo.getWorldFolder());
					return true;
				} 
				catch (IOException e) {
					e.printStackTrace();
					return false;
				}
			}
			else {
				log.warning("Still players on world " + wo.getName());
				return false;
			}
		}
		else {
			log.warning("World " + args[0] + " doesn't exist");
			return false;
		}
	}
	
	public boolean loadWorld(String[] args) {
		try {
			serv.createWorld(new WorldCreator("./"+args[0]));
			return true;
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean copyBackup(String[] args) {
		World wo = serv.getWorld(args[0]);
		File src = new File("./backups/" + args[0]);
		File dir = new File("./" + args[0]);
		if(wo == null) {
			log.info("World " + args[0] + " is not loaded. Copying Backup");
			try {
				FileUtils.copyDirectory(src, dir);
				return true;
			}
			catch(IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		else {
			log.warning("World " + args[0] + " is still loaded! Cannot copy Backup!");
			return false;
		}
	}
}
