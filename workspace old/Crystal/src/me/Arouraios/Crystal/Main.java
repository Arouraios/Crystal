package me.Arouraios.Crystal;

import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.Packets;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ConnectionSide;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.events.PacketListener;
import com.comphenix.protocol.wrappers.EnumWrappers;

import me.Arouraios.Crystal.minigame.Game;
import me.Arouraios.Crystal.utils.Commander;
import me.Arouraios.Crystal.worlds.WorldManager;
import net.minecraft.server.v1_8_R3.Packet;

@SuppressWarnings("unused")
public class Main extends JavaPlugin implements Listener {
	private Logger log;
	private FileConfiguration conf;
	private WorldManager wm;
	private Server serv;
	private File forLaterUse;
	private Commander C;
	private List<Game> runningGames;
	private ProtocolManager pM;

	@Override
	public void onEnable() {
		serv = getServer();
		conf = getConfig();
		log = getLogger();
		C = new Commander(serv, log, this);
		runningGames = new ArrayList<Game>();

		wm = new WorldManager(log, conf, serv, this);
//		loadforLaterUse();

		reloadConfig();
		conf.options().copyDefaults(true);
		saveConfig();
		log.info("worlds created");
		log.info("Crystal enabled!");
		wm = new WorldManager(log, conf, serv, this);
		pM=	ProtocolLibrary.getProtocolManager();
	}

	@Override
	public void onDisable() {
		saveConfig();
		log.info("Crystal stopped!");
	}

	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent e) {
		Location loc = new Location(getServer().getWorld("world"), 0.5, 100.5, 0.5);
		e.getPlayer().teleport(loc);
		log.info("Player joined. Was teleported to default location");
	}

//	public void loadforLaterUse() {
//		forLaterUse = FileUtils.getFile("./forLaterUse.yml");
//		if (!forLaterUse.exists()) {
//			try {
//				log.info("trying to create new forLaterUse.yml");
//				forLaterUse.createNewFile();
//			} catch (IOException e) {
//				log.warning("couldn't create new forLaterUse.yml");
//				e.printStackTrace();
//			}
//		} else {
//			log.info("forLaterUse loaded");
//		}
//	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player s;
		World w;
		if (sender instanceof Player) {
			s = ((Player) sender);
			w = s.getWorld();
		} else {
			s = null;
			w = null;
		}
		try {
			if (cmd.getName().equalsIgnoreCase("reloadworld")) {
				return wm.reloadWorld(args[0]);
			} else if (cmd.getName().equalsIgnoreCase("deleteworld")) {
				return wm.deleteWorld(args[0]);
			} else if (cmd.getName().equalsIgnoreCase("newgame")) {
				Game g = new Game(args[0], log, conf, wm, serv, C, this, 2, 2);
				log.info("game " + args[0] + " successfully created");
				runningGames.add(g);
			} else if (cmd.getName().equalsIgnoreCase("loadworld")) {
				return wm.loadWorld(args[0]);
			} else if (cmd.getName().equalsIgnoreCase("unloadworld")) {
				return wm.unloadWorld(args[0]);
			} else if (cmd.getName().equalsIgnoreCase("copyBackup")) {
				return wm.copyBackup(args[0]);
			} else if (cmd.getName().equalsIgnoreCase("tpallto")) {
				switch (args.length) {
				case 0:
					if (s != null)
						return C.tpAllTo(s);
					else {
						log.warning("Not enough arguments");
						return false;
					}
				case 3:
					try {
						if (s != null)
							return C.tpAllTo(Integer.parseInt(args[0]), Integer.parseInt(args[1]),
									Integer.parseInt(args[2]), s.getWorld().getName());
						else {
							log.warning("Please specify a world to teleport to!");
							return false;
						}
					} catch (NumberFormatException e) {
						if (s != null)
							s.sendMessage("Invalid arguments. Check usage!");
						else
							log.warning("Invalid arguments. Check usage!");
						return false;
					}
				case 4:
					try {
						return C.tpAllTo(Integer.parseInt(args[0]), Integer.parseInt(args[1]),
								Integer.parseInt(args[2]), args[3]);
					} catch (NumberFormatException e) {
						if (s != null)
							s.sendMessage("Invalid arguments. Check usage!");
						else
							log.warning("Invalid arguments. Check usage!");
						return false;
					}
				}
			} else if (cmd.getName().equalsIgnoreCase("getWorlds")) {
				for (World wo : getServer().getWorlds()) {
					if (s != null)
						s.sendMessage(wo.getName());
					else
						log.info(wo.getName());
				}
				return true;
			}
		} catch (IndexOutOfBoundsException e) {
			if (s != null)
				s.sendMessage("Not enough arguments!");
			else
				log.warning("Not enough arguments!");
		}
		return false;
	}
	
	public void test() {
		ProtocolManager pm = ProtocolLibrary.getProtocolManager();
		PacketContainer packet = pm.createPacket(PacketType.Play.Client.BLOCK_DIG);
		packet.getModifier().writeDefaults();
		pm.addPacketListener(new PacketAdapter(this, ListenerPriority.NORMAL, PacketType.Play.Client.BLOCK_DIG) {
			@Override
			public void onPacketReceiving(PacketEvent e) {
				if (e.getPacketType() == PacketType.Play.Client.BLOCK_DIG) {
					PacketContainer p = e.getPacket();
					p.getIntegers().write(1,1);
				}
			}
		});
		
	}

	public void discardGame(String count) {
		for(Game g : runningGames) {
			if(g.getCount().equalsIgnoreCase(count))runningGames.remove(g);
		}
		
	}
}