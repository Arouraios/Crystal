package me.Arouraios.Crystal.utils;

import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;

@SuppressWarnings("unused")
public class Commander {
	private Server serv;
	private Logger log;

	public Commander(Server serv, Logger log) {
		this.serv = serv;
		this.log = log;
	}

	public boolean tpallto(Player s) {
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

	public boolean tpallto(int x, int y, int z, String wo) {
		Location loc = new Location(serv.getWorld(wo), x, y, z);
		for (Player pl : serv.getOnlinePlayers()) {
			pl.teleport(loc);
			log.info("Player " + pl + "was teleported to the given location");
		}
		return true;
	}
}
