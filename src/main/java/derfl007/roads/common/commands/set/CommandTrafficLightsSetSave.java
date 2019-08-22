package derfl007.roads.common.commands.set;

import java.util.ArrayList;
import java.util.List;

import derfl007.roads.common.commands.CommandTrafficLights;
import derfl007.roads.common.commands.CommandTrafficLightsBase;
import derfl007.roads.trafficlights.LightsGroup;
import derfl007.roads.trafficlights.LightsSetList;
import derfl007.roads.trafficlights.PlayerTempSetCreation;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;

public class CommandTrafficLightsSetSave extends CommandTrafficLightsBase {

	@Override
	public String getName() {
		return "save";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "command.trafficlights.set.save.usage";
	}

	@Override
	public void execute(MinecraftServer server, EntityPlayer player, String[] args) throws CommandException {

		if (CommandTrafficLights.inProgressCreations.containsKey(player)) {

			PlayerTempSetCreation current = CommandTrafficLights.inProgressCreations.get(player);
			if (!current.get(current.selectedGroupIndex).getLights().isEmpty()
					&& (current.get(current.selectedGroupIndex).getGreenDuration() < 1
							|| current.get(current.selectedGroupIndex).getDelay() < 0)) {
				player.sendMessage(new TextComponentTranslation("command.trafficlights.group.notfinished"));
				return;
			}

			List<LightsGroup> emptyGroups = new ArrayList<>();
			int i = 1;
			for (LightsGroup group : current) {
				if (group.getLights().isEmpty()) {
					emptyGroups.add(group);
				} else if (group.getGreenDuration() < 1 || group.getDelay() < 0) {
					throw new IllegalStateException("Non empty group " + i + " not finished.");
				}
				i++;
			}

			if (current.size() - emptyGroups.size() < 2) {
				player.sendMessage(new TextComponentTranslation("command.trafficlights.set.empty"));
				return;
			}

			current.removeAll(emptyGroups);

			LightsSetList.get(player.world).addLightSet(current.tempSetName, current,
					current.replacing || current.editing, !current.editing);
			player.sendMessage(new TextComponentTranslation("command.trafficlights.set.saved", current.tempSetName));
			CommandTrafficLights.inProgressCreations.remove(player);

		} else {
			player.sendMessage(new TextComponentTranslation("command.trafficlights.set.none"));
		}

	}

	@Override
	public String getHelp() {
		return "command.trafficlights.set.save.help";
	}

	@Override
	protected int expectedArgumentsCount() {
		// TODO Auto-generated method stub
		return 0;
	}
}
