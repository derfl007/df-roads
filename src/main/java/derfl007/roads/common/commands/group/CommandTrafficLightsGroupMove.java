package derfl007.roads.common.commands.group;

import derfl007.roads.common.commands.CommandTrafficLights;
import derfl007.roads.common.commands.CommandTrafficLightsBase;
import derfl007.roads.trafficlights.LightsGroup;
import derfl007.roads.trafficlights.PlayerTempSetCreation;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;

public class CommandTrafficLightsGroupMove extends CommandTrafficLightsBase {

	@Override
	public String getName() {
		return "move";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "command.trafficlights.group.move.usage";
	}

	@Override
	public void execute(MinecraftServer server, EntityPlayer player, String[] args) throws CommandException {
		if (!CommandTrafficLights.inProgressCreations.containsKey(player)) {
			player.sendMessage(new TextComponentTranslation("command.trafficlights.set.none"));
			return;
		}

		PlayerTempSetCreation current = CommandTrafficLights.inProgressCreations.get(player);

		Integer newIndex;
		try {
			newIndex = Integer.valueOf(args[0]);
		} catch (NumberFormatException e) {
			unexpectedArguments(player);
			return;
		}

		if (newIndex < 0 || newIndex > current.size()) {
			player.sendMessage(new TextComponentTranslation("command.trafficlights.group.outofbounds", current.size()));
			return;
		}

		if (newIndex == 0) {
			newIndex = current.size();
		}

		LightsGroup inter = current.get(current.selectedGroupIndex);
		current.remove(current.selectedGroupIndex);
		current.add(newIndex - 1, inter);
		player.sendMessage(new TextComponentTranslation("command.trafficlights.group.moved",
				current.selectedGroupIndex + 1, newIndex));
		current.selectedGroupIndex = newIndex - 1;

	}

	@Override
	public String getHelp() {
		return "command.trafficlights.group.move.help";
	}

	@Override
	protected int expectedArgumentsCount() {
		// TODO Auto-generated method stub
		return 1;
	}

}