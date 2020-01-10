package me.Arouraios.Crystal.teams;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.Arouraios.Crystal.Main;
import me.Arouraios.Crystal.minigame.Game;
import me.Arouraios.Crystal.utils.Commander;
import me.Arouraios.Crystal.worlds.WorldManager;

@SuppressWarnings("unused")
public class TeamManager {
	private Logger log;
//	private FileConfiguration conf;
	private WorldManager WM;
	private Server serv;
//	private Commander C;
	private Main main;

	private List<Team> teams;

	public TeamManager(Server pServ, Main pMain, Logger pLog) {
		log = pLog;
		serv = pServ;
		main = pMain;
		teams = new ArrayList<Team>();
	}

	public void createTeam(Game g, int teamID, String color, int maxPlayerCount) {
		Team t = new Team(g, teamID, color, log, maxPlayerCount);
		teams.add(t);
	}
	
	public void notifyTeam(Game g, int teamID, String msg) {
		for(Team t : teams) {
			if(t.getGame()==g&&t.getTeamID()==teamID) {
				t.notifyTeam(msg);
				return;
			}
		}
	}
	
	public void notifyGame (Game g, String msg) {
		for (Team t : teams)
			if (t.getGame() == g)
				t.notifyTeam(msg);
	}
	
	public void toastTeam(Game g, int teamID, String title, String subtitle) {
		for(Team t: teams) {
			if(t.getGame()==g&&t.getTeamID()==teamID) {
				t.toast(title, subtitle);
				return;
			}
		}
	}
	
	public void toastGame(Game g, String title, String subtitle) {
		for(Team t: teams) {
			if(t.getGame()==g) {
				t.toast(title, subtitle);
			}
		}
	}

	public void addTeamMember(Game g, int teamID, Player p) {
		for (Team t : teams) {
			if (t.getGame() == g && t.getTeamID() == teamID) {
				t.addPlayer(p);
				return;
			}
		}
	}

	public void sendGameMessage(Game g, String msg) {
		for (Team t : teams)
			if (t.getGame() == g)
				t.notifyTeam(msg);
	}

	public void gameEnded(Game g) {
		for (Team t : teams)
			if (t.getGame() == g)
				teams.remove(t);
	}
}
