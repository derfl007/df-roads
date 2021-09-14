package derfl007.roads.common.commands.set;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

public class CommandTrafficLightsSetCreate extends CommandTrafficLightsBase {

	private static final String nameFormat = "^[A-Za-z]\\w*$";

	@Override
	public String getName() {
		return "create";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "command.trafficlights.set.create.usage";
	}

	@Override
	public void execute(MinecraftServer server, EntityPlayer player, String[] args) throws CommandException {

		if (CommandTrafficLights.inProgressCreations.containsKey(player)) {
			player.sendMessage(new TextComponentTranslation("command.trafficlights.set.alreadyPending"));
		} else {
			Pattern pattern = Pattern.compile(nameFormat);
			Matcher matcher = pattern.matcher(args[0]);
			if (matcher.find()) {

				if (LightsSetList.get(player.world).setExists(args[0])) {
					player.sendMessage(new TextComponentTranslation("command.trafficlights.set.duplicatename"));
				} else {
					CommandTrafficLights.inProgressCreations.put(player, new PlayerTempSetCreation(args[0]));
					CommandTrafficLights.inProgressCreations.get(player).add(new LightsGroup());

					player.sendMessage(new TextComponentTranslation("command.trafficlights.set.creating"));
				}

			} else {
				player.sendMessage(new TextComponentTranslation("command.trafficlights.set.illegalname"));
			}
		}

	}

	@Override
	public String getHelp() {
		return "command.trafficlights.set.create.help";
	}
	
	@Override
	protected int expectedArgumentsCount() {
		// TODO Auto-generated method stub
		return 1;
	}

}
