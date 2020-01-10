package me.Arouraios.Crystal.worlds;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.FileConfiguration;

import me.Arouraios.Crystal.Main;

public class WorldManager {
	private Logger log;
//	private FileConfiguration conf;
	private Server serv;
	private Main main;

	public WorldManager(Logger pLog, FileConfiguration pConf, Server pServ, Main pMain) {
		log = pLog;
		main = pMain;
//		conf=pConf;
		serv = pServ;
	}

	public boolean reloadWorld(String args) {
		deleteWorld(args);
		copyBackup(args);
		loadWorld(args);
		return false;
	}

	public boolean reloadWorldSync(String args) {
		serv.getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
			@Override
			public void run() {
				deleteWorld(args);
				copyBackup(args);
				loadWorld(args);
			}
		}, 20);
		return true;
	}

	public boolean unloadWorld(String args) {
		World wo = serv.getWorld(args);
		if (wo != null) {
			log.info("World " + wo.getName() + " exists. Unloading...");
			if (wo.getPlayers().size() == 0) {
				serv.unloadWorld(wo, false);
				return true;
			}
		}
		return false;
	}

	public boolean deleteWorldSync(String args) {
		serv.getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
			@Override
			public void run() {
				deleteWorld(args);
			}
		}, 20);
		return true;
	}
	
	public boolean deleteWorld(String args) {
		World wo = serv.getWorld(args);
		if (wo != null) {
			log.info("World " + wo.getName() + " exists. Unloading...");
			if (wo.getPlayers().size() == 0) {
				serv.unloadWorld(wo, false);
				try {
					FileUtils.deleteDirectory(wo.getWorldFolder());
					return true;
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
			} else {
				log.warning("Still players on world " + wo.getName());
				return false;
			}
		} else {
			log.warning("World " + args + " doesn't exist. Looking for unloaded Directory");
			try {
				File f = new File(args);
				if (f.exists()) {
					FileUtils.deleteDirectory(f);
					log.info("File existed. Deleted");
					return true;
				} else
					log.info("File doesn't exist");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return false;
		}
	}
	
	public boolean loadWorldSync(String args) {
		serv.getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
			@Override
			public void run() {
				loadWorld(args);
			}
		}, 20);
		return true;
	}

	public boolean loadWorld(String WorldName) {
		try {
			serv.createWorld(new WorldCreator(WorldName));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean copyBackupSync(String args) {
		serv.getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
			@Override
			public void run() {
				copyBackup(args);
			}
		}, 20);
		return true;
	}
	
	public boolean copyBackup(String WorldName) {
		World wo = serv.getWorld(WorldName);
		File src = new File("backups/" + WorldName);
		File dir = new File(WorldName);
		if (wo == null) {
			log.info("World " + WorldName + " is not loaded. Copying Backup");
			try {
				FileUtils.copyDirectory(src, dir);
				return true;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		} else {
			log.warning("World " + WorldName + " is still loaded! Cannot copy Backup!");
			return false;
		}
	}
}
