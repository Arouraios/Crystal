package me.Arouraios.Crystal.teams;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;

import me.Arouraios.Crystal.Main;
import me.Arouraios.Crystal.utils.Commander;
import me.Arouraios.Crystal.worlds.WorldManager;

public class TeamManager {
	private Logger log;
//	private FileConfiguration conf;
	private WorldManager WM;
	private Server serv;
//	private Commander C;
	private Main main;
	
	private List<Team> teams;
	public TeamManager(Server pServ, Main pMain, Logger pLog) {
		log=pLog; serv=pServ; main=pMain;
		teams = new ArrayList<Team>();
	}
	
	public void createTeam(Game g, int maxPlayerCount) {
		Team t = new Team(log, g, maxPlayerCount) {
			
		}
	}
}
