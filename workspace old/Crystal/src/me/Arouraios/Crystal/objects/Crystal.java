package me.Arouraios.Crystal.objects;

import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ConnectionSide;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.*;

import me.Arouraios.Crystal.minigame.Game;
import me.Arouraios.Crystal.teams.Team;

public class Crystal implements Listener{
	private int health, teamID;
	private String color;
	private Game g;
	private Block block;
	private ProtocolManager pM;
	
	public Crystal(Game pG, int pHealth, int pTeamID, Block b) {
		g = pG;
		health=pHealth;
		teamID = pTeamID;
		block = b;
		pM = ProtocolLibrary.getProtocolManager();
	}
	
	public void setHealth(int h) {
		health = h;
	}
	
	public int getHealth() {
		return health;
	}
	
	@EventHandler
	public void onBlockDamageEvent(BlockDamageEvent e) {
		if(e.getBlock()==block) {
			playerMining(e.getPlayer());
			e.setCancelled(true);
			return;
		}
		return;
	}
	
	public void test() {
	
	}
	
	public void playerMining(Player p) {
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				//p.getTargetBlock(null, 6);
				//TODO: set up task, every 0.1 seconds block health is decreased, but first it is checked wether the player is still looking at the block
			}
		};
		timer.schedule(task, 0, 100);
	}
}
