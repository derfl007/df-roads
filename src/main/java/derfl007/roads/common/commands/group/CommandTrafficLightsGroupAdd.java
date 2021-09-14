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

public class CommandTrafficLightsGroupAdd extends CommandTrafficLightsBase {

	@Override
	public String getName() {
		return "add";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "command.trafficlights.group.add.usage";
	}

	@Override
	public void execute(MinecraftServer server, EntityPlayer player, String[] args) throws CommandException {
		if (!CommandTrafficLights.inProgressCreations.containsKey(player)) {
			player.sendMessage(new TextComponentTranslation("command.trafficlights.set.none"));
			return;
		}

		PlayerTempSetCreation current = CommandTrafficLights.inProgressCreations.get(player);

		if (current.get(current.selectedGroupIndex).getLights().isEmpty()) {
			if (current.selectedGroupIndex == current.size() - 1) {
				player.sendMessage(
						new TextComponentTranslation("command.trafficlights.group.selected", current.size()));
			} else {
				player.sendMessage(new TextComponentTranslation("command.trafficlights.group.empty"));
			}
			return;
		}

		if (!current.get(current.selectedGroupIndex).getLights().isEmpty()
				&& (current.get(current.selectedGroupIndex).getGreenDuration() < 1
						|| current.get(current.selectedGroupIndex).getDelay() < 0)) {
			player.sendMessage(new TextComponentTranslation("command.trafficlights.group.notfinished"));
			return;
		}

		if (current.get(current.size() - 1).getLights().isEmpty()) {
			current.selectedGroupIndex = current.size() - 1;
			player.sendMessage(new TextComponentTranslation("command.trafficlights.group.selected", current.size()));
			return;
		}

		current.add(new LightsGroup());
		current.selectedGroupIndex = current.size() - 1;
		player.sendMessage(new TextComponentTranslation("command.trafficlights.group.created"));

	}

	@Override
	public String getHelp() {
		return "command.trafficlights.group.add.help";
	}

	@Override
	protected int expectedArgumentsCount() {
		// TODO Auto-generated method stub
		return 0;
	}

}