package crystal;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class WorldLoader 
{
	
	void loadWorld()
	{
		
		
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(cmd.getName().equalsIgnoreCase("reloadworld")){
			return true;
		}
		return false;
	}
	
}
