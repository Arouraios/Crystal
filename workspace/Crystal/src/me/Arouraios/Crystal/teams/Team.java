package me.Arouraios.Crystal.teams;

import java.util.List;
import java.util.logging.Logger;

import org.bukkit.entity.Player;

import me.Arouraios.Crystal.minigame.Game;

public class Team {
	int pc, cpc;
	Game g;
	String colour;
	Player[] team;
	List<Player> playerList;
	boolean generated;
	Logger log;

	public Team(Logger pLog, Game pG, int maxPlayerCount) {
		log=pLog;
		g = pG;
		pc = maxPlayerCount;
		cpc=0;
		team = new Player[maxPlayerCount];
		generated = true;
	}
	
	
	public void addPlayer(Player p) {
		if(cpc<pc) {
			team[cpc]=p;
			cpc++;
		}
		else log.warning("Too many Players!!");
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
