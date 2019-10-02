package me.Arouraios.Crystal.teams;

import java.util.List;

import org.bukkit.entity.Player;

import me.Arouraios.Crystal.minigame.Game;

public class Team {
	int pc, cpc;
	Game g;
	String colour;
	Player[] team;
	List<Player> playerList;
	boolean generated;

	public Team(Game pG, int maxPlayerCount, List<Player> teamMembers) {
		g = pG;
		pc = maxPlayerCount;
		cpc=teamMembers.size();
		team = new Player[maxPlayerCount];
		playerList=teamMembers;
		generated = false;
		makeArray();
	}

	public void makeArray() {
		int i=0;
		for(Player p : playerList) {
			team[i]=p;
			i++;
		}
	}
	
	public void addPlayer(Player p) {
		if(cpc<pc) {
			team[cpc]=p;
			cpc++;
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
