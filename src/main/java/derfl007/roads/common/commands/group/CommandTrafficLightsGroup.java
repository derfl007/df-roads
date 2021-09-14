package derfl007.roads.common.commands.group;

import derfl007.roads.common.commands.CommandTrafficLightsTreeBase;
import net.minecraft.command.ICommandSender;

public class CommandTrafficLightsGroup extends CommandTrafficLightsTreeBase {

	@Override
	public String getName() {
		return "group";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "command.trafficlights.group.usage";
	}

	@Override
	public String getHelp() {
		return "command.trafficlights.group.help";
	}
	

}
