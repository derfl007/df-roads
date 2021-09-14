package derfl007.roads.common.commands.group;

import derfl007.roads.common.commands.CommandTrafficLights;
import derfl007.roads.common.commands.CommandTrafficLightsBase;
import derfl007.roads.trafficlights.PlayerTempSetCreation;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;

public class CommandTrafficLightsGroupSelect extends CommandTrafficLightsBase {

	@Override
	public String getName() {
		return "select";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "command.trafficlights.group.select.usage";
	}

	@Override
	public void execute(MinecraftServer server, EntityPlayer player, String[] args) throws CommandException {
		if (!CommandTrafficLights.inProgressCreations.containsKey(player)) {
			player.sendMessage(new TextComponentTranslation("command.trafficlights.set.none"));
			return;
		}

		PlayerTempSetCreation current = CommandTrafficLights.inProgressCreations.get(player);
		
		if (!current.get(current.selectedGroupIndex).getLights().isEmpty()
				&& (current.get(current.selectedGroupIndex).getGreenDuration() < 1
						|| current.get(current.selectedGroupIndex).getDelay() < 0)) {
			player.sendMessage(new TextComponentTranslation("command.trafficlights.group.notfinished"));
			return;
		}

		Integer number;
		try {
			number = Integer.valueOf(args[0]);
		} catch (NumberFormatException e) {
			unexpectedArguments(player);
			return;
		}

		if (number < 0 || number > current.size()) {
			player.sendMessage(new TextComponentTranslation("command.trafficlights.group.outofbounds", current.size()));
			return;
		}

		if (number == 0) {
			number = current.size();
		}

		current.selectedGroupIndex = number - 1;
		player.sendMessage(new TextComponentTranslation("command.trafficlights.group.selected", number));

	}

	@Override
	public String getHelp() {
		return "command.trafficlights.group.select.help";
	}

	@Override
	protected int expectedArgumentsCount() {
		// TODO Auto-generated method stub
		return 1;
	}

}