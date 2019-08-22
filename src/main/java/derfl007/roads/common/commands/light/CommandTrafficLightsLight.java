package derfl007.roads.common.commands.light;

import derfl007.roads.common.commands.CommandTrafficLightsTreeBase;
import net.minecraft.command.ICommandSender;

public class CommandTrafficLightsLight extends CommandTrafficLightsTreeBase {

	@Override
	public String getName() {
		return "light";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "command.trafficlights.light.usage";
	}

	@Override
	public String getHelp() {
		return "command.trafficlights.light.help";
	}	
	

}
