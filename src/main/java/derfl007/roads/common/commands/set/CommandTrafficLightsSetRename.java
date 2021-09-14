package derfl007.roads.common.commands.set;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import derfl007.roads.common.commands.CommandTrafficLightsBase;
import derfl007.roads.trafficlights.LightsSetList;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;

public class CommandTrafficLightsSetRename extends CommandTrafficLightsBase {

	private static final String nameFormat = "^[A-Za-z]\\w*$";

	@Override
	public String getName() {
		return "rename";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "command.trafficlights.set.rename.usage";
	}

	@Override
	public void execute(MinecraftServer server, EntityPlayer player, String[] args) throws CommandException {

		LightsSetList list = LightsSetList.get(player.world);
		if (list.setExists(args[0])) {
			if (!list.setExists(args[1])) {
				Pattern pattern = Pattern.compile(nameFormat);
				Matcher matcher = pattern.matcher(args[1]);
				if (matcher.find()) {
					list.addLightSet(args[1], list.getLightSet(args[0]));
					list.removeLightSet(args[0]);
					player.sendMessage(
							new TextComponentTranslation("command.trafficlights.set.renamed", args[0], args[1]));
				} else {
					player.sendMessage(new TextComponentTranslation("command.trafficlights.set.illegalname"));
				}
			} else {
				player.sendMessage(new TextComponentTranslation("command.trafficlights.set.duplicatename"));
			}
		} else {
			player.sendMessage(new TextComponentTranslation("command.trafficlights.set.nosuchset", args[0]));
		}

	}

	@Override
	public String getHelp() {
		return "command.trafficlights.set.rename.help";
	}
	
	@Override
	protected int expectedArgumentsCount() {
		// TODO Auto-generated method stub
		return 2;
	}
}


