package derfl007.roads.common.commands.light;

import derfl007.roads.common.commands.CommandTrafficLights;
import derfl007.roads.common.commands.CommandTrafficLightsBase;
import derfl007.roads.trafficlights.PlayerTempSetCreation;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;

public class CommandTrafficLightsLightRemove extends CommandTrafficLightsBase {

	@Override
	public String getName() {
		return "remove";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "command.trafficlights.light.remove.usage";
	}

	@Override
	public void execute(MinecraftServer server, EntityPlayer player, String[] args) throws CommandException {
		if (!CommandTrafficLights.inProgressCreations.containsKey(player)) {
			player.sendMessage(new TextComponentTranslation("command.trafficlights.set.none"));
			return;
		}

		Integer x, y, z;

		try {
			x = Integer.valueOf(args[0]);
			y = Integer.valueOf(args[1]);
			z = Integer.valueOf(args[2]);
		} catch (NumberFormatException e) {
			unexpectedArguments(player);
			return;
		}

		BlockPos pos = new BlockPos(x, y, z);

		PlayerTempSetCreation current = CommandTrafficLights.inProgressCreations.get(player);

		if (current.get(current.selectedGroupIndex).getLights().contains(pos)) {
			player.sendMessage(new TextComponentTranslation("command.trafficlights.light.removed", x, y, z));
		} else {
			player.sendMessage(new TextComponentTranslation("command.trafficlights.light.notfound", x, y, z));
		}

	}

	@Override
	public String getHelp() {
		return "command.trafficlights.light.remove.help";
	}

	@Override
	protected int expectedArgumentsCount() {
		// TODO Auto-generated method stub
		return 3;
	}

}