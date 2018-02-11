package me.Plugins.Crystal.Teams;


import java.util.ArrayList;
import java.util.HashMap;
 
import org.bukkit.entity.Player;
 
public class Team 
{

	public static ArrayList<Team> teams = new ArrayList<Team>();
	public static HashMap<String, Team> playerTeams = new HashMap<String, Team>();
	public String teamColor;
	 
	
	public Team(String teamcolor)
	{
	
		teamColor = teamcolor;
	
		teams.add(this);
	}
	
	public void addPlayer(Player p)
	{
		playerTeams.put(p.getName(), this);
	}
	
	public void removePlayer(Player p)
	{
		playerTeams.remove(p.getName());	
	}
	
	public boolean hasTeam(Player p)
	{
	
		return playerTeams.containsKey(p.getName());
	}
	
	public Team getTeam(Player p)
	{
		 return playerTeams.get(p);
	}
	
	
	public String getTeamColor()
	{
		return teamColor;
	}
 
}
 