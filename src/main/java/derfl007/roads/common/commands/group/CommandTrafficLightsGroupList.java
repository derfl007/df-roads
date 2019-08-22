package derfl007.roads.common.commands.group;

import derfl007.roads.common.commands.CommandTrafficLights;
import derfl007.roads.common.commands.CommandTrafficLightsBase;
import derfl007.roads.common.commands.set.CommandTrafficLightsSetCheck;
import derfl007.roads.trafficlights.PlayerTempSetCreation;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;

public class CommandTrafficLightsGroupList extends CommandTrafficLightsBase {

	@Override
	public String getName() {
		return "list";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "command.trafficlights.group.list.usage";
	}

	@Override
	public void execute(MinecraftServer server, EntityPlayer player, String[] args) throws CommandException {

		if (!CommandTrafficLights.inProgressCreations.containsKey(player)) {
			player.sendMessage(new TextComponentTranslation("command.trafficlights.set.none"));
			return;
		}
		
		PlayerTempSetCreation current = CommandTrafficLights.inProgressCreations.get(player);

		CommandTrafficLightsSetCheck.showInfo(current.tempSetName, current, player);

	}

	@Override
	public String getHelp() {
		return "command.trafficlights.group.list.help";
	}

	@Override
	protected int expectedArgumentsCount() {
		// TODO Auto-generated method stub
		return 0;
	}

}