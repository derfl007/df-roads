package derfl007.roads.common.commands.group;

import derfl007.roads.common.commands.CommandTrafficLights;
import derfl007.roads.common.commands.CommandTrafficLightsBase;
import derfl007.roads.trafficlights.PlayerTempSetCreation;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;

public class CommandTrafficLightsGroupRemove extends CommandTrafficLightsBase {

	@Override
	public String getName() {
		return "remove";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "command.trafficlights.group.remove.usage";
	}

	@Override
	public void execute(MinecraftServer server, EntityPlayer player, String[] args) throws CommandException {

		if (!CommandTrafficLights.inProgressCreations.containsKey(player)) {
			player.sendMessage(new TextComponentTranslation("command.trafficlights.set.none"));
			return;
		}

		PlayerTempSetCreation current = CommandTrafficLights.inProgressCreations.get(player);

		if (current.size() == 1) {
			current.get(0).getLights().clear();
		} else {
			current.remove(current.selectedGroupIndex);
		}
		current.selectedGroupIndex = current.size() - 1;
		player.sendMessage(new TextComponentTranslation("command.trafficlights.group.removed"));
	}

	@Override
	public String getHelp() {
		return "command.trafficlights.group.remove.help";
	}

	@Override
	protected int expectedArgumentsCount() {
		// TODO Auto-generated method stub
		return 0;
	}

}
