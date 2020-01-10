package me.Arouraios.Crystal.teams;

import java.util.List;
import java.util.logging.Logger;

import org.bukkit.entity.Player;

import me.Arouraios.Crystal.minigame.Game;

public class Team {
	private int pc, cpc, id;
	private Game g;
	private Player[] team;
	private boolean generated;
	private Logger log;
	private String color;

	public Team(Game pG, int pId, String pColor, Logger pLog, int maxPlayerCount) {
		log=pLog;
		g = pG;
		pc = maxPlayerCount;
		cpc=0;
		team = new Player[maxPlayerCount];
		color = pColor;
		id = pId;
	}
	
	public int getTeamID() {
		return id;
	}
	
	public void toast(String title, String subtitle) {
		for(Player p: team) {
			//TODO: show title and subtitle on screen!!!
			p.sendMessage(title);
			p.sendMessage(subtitle);
			}
	}
	
	public void addPlayer(Player p) {
		if(cpc<pc) {
			team[cpc]=p;
			cpc++;
		}
		else log.warning("Too many Players!!");
	}
	
	public Game getGame() {
		return g;
	}
	
	public void addPlayer(Player[] p) {
		if(cpc + p.length <= pc) {
			for(int i = 0; i<p.length; i++) {
				team[cpc] = p[i];
				cpc++;
			}
		}
		else log.warning("Too many Players!!");
	}
	
	public void notifyTeam(String msg) {
		for(Player p : team) {
			p.sendMessage(msg);
		}
	}
	
	public void removePlayer(Player p) {
		int i=0;
		for(Player pl : team) {
			if(p==pl) {
				cpc--;
				team[i]=null;
				break;
			}
			i++;
		}
	}

	public boolean isEmpty() {
		return generated;
	}
}
