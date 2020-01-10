package me.Arouraios.Crystal.utils;

import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;

import me.Arouraios.Crystal.Main;

@SuppressWarnings("unused")
public class Commander {
	private Server serv;
	private Logger log;
	private Main main;

	public Commander(Server serv, Logger log, Main pMain) {
		this.serv = serv;
		main = pMain;
		this.log = log;
	}
	
	public boolean tpAllToSync(Player s) {
		serv.getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
			@Override
			public void run() {
				tpAllTo(s);
			}
		}, 20);
		return true;
	}
	
	public boolean tpAllToSync(int x, int y, int z, String wo) {
		serv.getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
			@Override
			public void run() {
				tpAllTo(x, y, z, wo);
			}
		}, 20);
		return true;
	}
	
	public boolean tpAllTo(Player s) {
		if (s == null) {
			log.warning("Player does not exist!");
			return false;
		}
		Location loc = s.getLocation();
		for (Player pl : serv.getOnlinePlayers()) {
			pl.teleport(loc);
			log.info("Player " + pl + "was teleported to " + s.getName());
		}
		return true;
	}

	public boolean tpAllTo(int x, int y, int z, String wo) {
		Location loc = new Location(serv.getWorld(wo), x, y, z);
		for (Player pl : serv.getOnlinePlayers()) {
			pl.teleport(loc);
			log.info("Player " + pl + "was teleported to the given location");
		}
		return true;
	}
}
