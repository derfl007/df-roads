package derfl007.roads.common.commands.set;

import derfl007.roads.common.commands.CommandTrafficLights;
import derfl007.roads.common.commands.CommandTrafficLightsBase;
import derfl007.roads.trafficlights.LightsSetList;
import derfl007.roads.trafficlights.PlayerTempSetCreation;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;

public class CommandTrafficLightsSetEdit extends CommandTrafficLightsBase {

	@Override
	public String getName() {
		return "edit";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "command.trafficlights.set.edit.usage";
	}

	@Override
	public void execute(MinecraftServer server, EntityPlayer player, String[] args) throws CommandException {

		if (CommandTrafficLights.inProgressCreations.containsKey(player)) {
			player.sendMessage(new TextComponentTranslation("command.trafficlights.set.alreadyPending"));
		} else {
			LightsSetList list = LightsSetList.get(player.world);
			if (list.setExists(args[0])) {

				CommandTrafficLights.inProgressCreations.put(player,
						new PlayerTempSetCreation(args[0], list.getLightSet(args[0])));

				player.sendMessage(new TextComponentTranslation("command.trafficlights.set.editing", args[0]));

			} else {
				player.sendMessage(new TextComponentTranslation("command.trafficlights.set.nosuchset", args[0]));
			}
		}

	}

	@Override
	public String getHelp() {
		return "command.trafficlights.set.edit.help";
	}
	
	@Override
	protected int expectedArgumentsCount() {
		// TODO Auto-generated method stub
		return 1;
	}

}
