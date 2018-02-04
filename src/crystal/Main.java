package crystal;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
	public void onEnable(){
		System.out.println("Vorlage gestartet");
	}
	public void onDisable(){
		System.out.println("Vorlage gestoppt");
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(cmd.getName().equalsIgnoreCase("Vorlage")){
			return true;
		}
		return false;
	}
}
