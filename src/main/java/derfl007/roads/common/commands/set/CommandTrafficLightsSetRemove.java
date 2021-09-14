package derfl007.roads.common.commands.set;

import derfl007.roads.common.commands.CommandTrafficLightsBase;
import derfl007.roads.trafficlights.LightsSetList;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;

public class CommandTrafficLightsSetRemove extends CommandTrafficLightsBase {

	@Override
	public String getName() {
		return "remove";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "command.trafficlights.set.remove.usage";
	}

	@Override
	public void execute(MinecraftServer server, EntityPlayer player, String[] args) throws CommandException {

		if (LightsSetList.get(player.world).setExists(args[0])) {
			LightsSetList.get(player.world).removeLightSet(args[0]);
			player.sendMessage(new TextComponentTranslation("command.trafficlights.set.deleted", args[0]));
		} else {
			player.sendMessage(new TextComponentTranslation("command.trafficlights.set.nosuchset", args[0]));
		}

	}

	@Override
	public String getHelp() {
		return "command.trafficlights.set.remove.help";
	}
	
	@Override
	protected int expectedArgumentsCount() {
		// TODO Auto-generated method stub
		return 1;
	}
}
