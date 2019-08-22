package derfl007.roads.common.commands.set;

import derfl007.roads.common.commands.CommandTrafficLightsBase;
import derfl007.roads.trafficlights.LightsSetList;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;

public class CommandTrafficLightsSetList extends CommandTrafficLightsBase {

	@Override
	public String getName() {
		return "list";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "command.trafficlights.set.list.usage";
	}

	@Override
	public void execute(MinecraftServer server, EntityPlayer player, String[] args) throws CommandException {
		
		if(LightsSetList.get(player.world).getLightSetsNames().size() > 0) {
		player.sendMessage(new TextComponentTranslation("command.trafficlights.set.list",
				String.join(", ", LightsSetList.get(player.world).getLightSetsNames())));
		}else {
			player.sendMessage(new TextComponentTranslation("command.trafficlights.set.list.empty"));
		}
	}

	@Override
	public String getHelp() {
		return "command.trafficlights.set.list.help";
	}
	
	@Override
	protected int expectedArgumentsCount() {
		// TODO Auto-generated method stub
		return 0;
	}
}
