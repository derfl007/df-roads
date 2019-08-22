package derfl007.roads.common.commands.set;

import derfl007.roads.common.commands.CommandTrafficLights;
import derfl007.roads.common.commands.CommandTrafficLightsBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;

public class CommandTrafficLightsSetCancel extends CommandTrafficLightsBase {

	@Override
	public String getName() {
		return "cancel";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "command.trafficlights.set.cancel.usage";
	}

	@Override
	public void execute(MinecraftServer server, EntityPlayer player, String[] args) throws CommandException {
	
		if (CommandTrafficLights.inProgressCreations.containsKey(player)) {
			CommandTrafficLights.inProgressCreations.remove(player);
			player.sendMessage(new TextComponentTranslation("command.trafficlights.set.cancelled"));
		} else {
			player.sendMessage(new TextComponentTranslation("command.trafficlights.set.none"));
		}
	}
	
	@Override
	public String getHelp() {
		return "command.trafficlights.set.cancel.help";
	}
	
	@Override
	protected int expectedArgumentsCount() {
		// TODO Auto-generated method stub
		return 0;
	}
	

}
