package derfl007.roads.common.commands.group;

import derfl007.roads.common.blocks.trafficlights.BlockRoadTrafficLightBase;
import derfl007.roads.common.commands.CommandTrafficLights;
import derfl007.roads.common.commands.CommandTrafficLightsBase;
import derfl007.roads.trafficlights.LightsGroup;
import derfl007.roads.trafficlights.PlayerTempSetCreation;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;

public class CommandTrafficLightsGroupFinish extends CommandTrafficLightsBase {

	@Override
	public String getName() {
		return "finish";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "command.trafficlights.group.finish.usage";
	}

	@Override
	public void execute(MinecraftServer server, EntityPlayer player, String[] args) throws CommandException {
		if (!CommandTrafficLights.inProgressCreations.containsKey(player)) {
			player.sendMessage(new TextComponentTranslation("command.trafficlights.set.none"));
			return;
		}

		PlayerTempSetCreation current = CommandTrafficLights.inProgressCreations.get(player);

		if (current.get(current.selectedGroupIndex).getLights().isEmpty()) {
			new TextComponentTranslation("command.trafficlights.group.empty");
			return;
		}
		int greenDuration = Integer.valueOf(args[0]);
		int delay = Integer.valueOf(args[1]);
		int yellowDuration = BlockRoadTrafficLightBase.DEFAULT_YELLOW_DURATION;
		if(args.length == 3) {			
			yellowDuration = Integer.valueOf(args[2]);
		}
		if (greenDuration < 1 || delay < 0) {
			player.sendMessage(new TextComponentTranslation("command.trafficlights.group.invalidargs"));
			return;
		}

		current.get(current.selectedGroupIndex).setGreenDuration(greenDuration);
		current.get(current.selectedGroupIndex).setDelay(delay);
		current.get(current.selectedGroupIndex).setYellowDuration(yellowDuration);

		if (!current.get(current.size() - 1).getLights().isEmpty()) {
			current.add(new LightsGroup());
		}
		current.selectedGroupIndex++;

		player.sendMessage(new TextComponentTranslation("command.trafficlights.group.finished"));

	}

	@Override
	public String getHelp() {
		return "command.trafficlights.group.finish.help";
	}

	@Override
	protected int expectedArgumentsCount() {
		// TODO Auto-generated method stub
		return 2;
	}
	
	@Override
	protected boolean hasOptionalArguments() {
		// TODO Auto-generated method stub
		return true;
	}

}