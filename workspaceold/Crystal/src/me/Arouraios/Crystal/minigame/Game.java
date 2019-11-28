package me.Arouraios.Crystal.minigame;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;

import me.Arouraios.Crystal.Main;
import me.Arouraios.Crystal.objects.Crystal;
import me.Arouraios.Crystal.teams.TeamManager;
import me.Arouraios.Crystal.utils.Commander;
import me.Arouraios.Crystal.worlds.WorldManager;

public class Game implements Runnable {
	private Thread t;
	private String count;
	private Logger log;
//	private FileConfiguration conf;
	private WorldManager wm;
	private Server serv;
	private Commander c;
	private TeamManager tm;
	private int wait;
	private String status;
	private Main main;
	private Crystal[] crystals;
	private int teamCount, teamSize;

	public Game(String gameCount, Logger pLog, FileConfiguration pConf, WorldManager pwm, Server pServ, Commander pC,
			Main pMain, int pTeamCount, int pTeamSize) {
		count = gameCount;
		log = pLog;
//		conf=pConf;
		main = pMain;
		wm = pwm;
		serv = pServ;
		c = pC;
		wait = 0;
		teamCount = pTeamCount;
		teamSize = pTeamSize;
		status = "new";
		t = new Thread(this, count);
		t.run();
		log.info("game " + count + " started running");
		initTeams();
	}

	public void run() {
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				if(status!="ended")play();else { timer.cancel();main.discardGame(count);}
			}
		};
		timer.schedule(task, 0, 1000);
	}
	
	public void initTeams() {
		
	}

	private void play() {
		log.info("act");
		switch (status) {
		case "new":
			wm.reloadWorldSync("miniWorldLobby" + count);
			log.info("all players will be teleported to the new game-lobby in 5 seconds");
			status = "waitLobby";
			break;
		case "waitLobby":
			if (wait < 7)
				wait++;
			else {
				wait = 0;
				c.tpAllToSync(0, 200, 0, "miniWorldLobby" + count);
				status = "lobby";
			}
		case "lobby":
			if (wait < 20)
				wait++;
			else {
				wait = 0;
				status = "loadgame";
			}
			break;
		case "loadgame":
			wm.reloadWorldSync("miniWorld" + count);
			log.info("all players will be teleported to the new game-world in 5 seconds");
			status="waitGame";
			break;
		case "waitGame":
			if (wait < 7)
				wait++;
			else {
				wait = 0;
				c.tpAllToSync(0, 200, 0, "miniWorld" + count);
				status = "game";
			}
		case "game":
			if (wait < 20)
				wait++;
			else {
				wait = 0;
				status = "end";
			}
			break;
		case "end":
			c.tpAllToSync(0, 200, 0, "world");
			wm.deleteWorldSync("miniWorld" + count);
			wm.deleteWorldSync("miniWorldLobby" + count);
			status="ended";
			break;
		case "ended":
			break;
		}
	}

	public void reloadWorld(String WorldName) {
		serv.getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
			@Override
			public void run() {
				wm.loadWorld(WorldName);
			}
		}, 20);
	}

	public String getCount() {
		return count;
	}

	public Thread getThread() {
		return t;
	}

	public String getWorld() {
		return "miniWorld"+count;
	}
}
