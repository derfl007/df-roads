package derfl007.roads.common.commands.set;

import derfl007.roads.common.commands.CommandTrafficLightsTreeBase;
import net.minecraft.command.ICommandSender;

public class CommandTrafficLightsSet extends CommandTrafficLightsTreeBase {

	@Override
	public String getName() {
		return "set";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "command.trafficlights.set.usage";
	}

	@Override
	public String getHelp() {
		return "command.trafficlights.set.help";
	}	
	

}
