package derfl007.roads.common.commands;

import java.util.HashMap;
import java.util.Map;

import derfl007.roads.trafficlights.PlayerTempSetCreation;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;

public class CommandTrafficLights extends CommandTrafficLightsTreeBase {

	public static final Map<EntityPlayer, PlayerTempSetCreation> inProgressCreations = new HashMap<>();

	@Override
	public String getName() {
		return "trafficlights";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "command.trafficlights.usage";
	}

	@Override
	public String getHelp() {
		return "command.trafficlights.help";
	}
	

}
