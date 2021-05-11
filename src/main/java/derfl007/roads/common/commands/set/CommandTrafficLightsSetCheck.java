package derfl007.roads.common.commands.set;

import derfl007.roads.common.blocks.trafficlights.BlockRoadTrafficLightBase;
import derfl007.roads.common.blocks.trafficlights.BlockRoadTrafficLightBase.TrafficLightsControlMode;
import derfl007.roads.common.commands.CommandTrafficLights;
import derfl007.roads.common.commands.CommandTrafficLightsBase;
import derfl007.roads.trafficlights.LightsGroup;
import derfl007.roads.trafficlights.LightsSet;
import derfl007.roads.trafficlights.LightsSetList;
import derfl007.roads.trafficlights.PlayerTempSetCreation;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

public class CommandTrafficLightsSetCheck extends CommandTrafficLightsBase {

	@Override
	public String getName() {
		return "check";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "command.trafficlights.set.check.usage";
	}

	public static String getLocalizedString(String translationKey, Object... args) {
		return new TextComponentTranslation(translationKey, args).getFormattedText();
	}

	public static String getLocalizedString(String translationKey, TextFormatting color, Object... args) {
		TextComponentTranslation text = new TextComponentTranslation(translationKey, args);
		text.getStyle().setColor(color);
		return text.getFormattedText();
	}

	public static void showInfo(String name, LightsSet set, EntityPlayer player) {

		int selected = -1;

		if (CommandTrafficLights.inProgressCreations.containsKey(player)) {
			PlayerTempSetCreation current = CommandTrafficLights.inProgressCreations.get(player);
			if (current.equals(set)) {
				selected = current.selectedGroupIndex;
			}
		}

		int i = 1;
		String state = getLocalizedString("dfroads.partial.set", TextFormatting.DARK_PURPLE, name);
		for (LightsGroup group : set) {

			if (selected == i - 1) {
				state = state + "\n§9§l---" + getLocalizedString("dfroads.partial.group", TextFormatting.BLUE, i) + " "
						+ getLocalizedString("dfroads.partial.current", TextFormatting.BLUE) + " "
						+ getLocalizedString("dfroads.partial.groupprop", TextFormatting.BLUE, group.getGreenDuration(),
								group.getDelay());
			} else {
				state = state + "\n§r---" + getLocalizedString("dfroads.partial.group", i) + " "
						+ getLocalizedString("dfroads.partial.groupprop", group.getGreenDuration(), group.getYellowDuration(), group.getDelay());
			}

			for (BlockPos pos : group.getLights()) {

				String posString = String.format("x=%d, y=%d,  z=%d", pos.getX(), pos.getY(), pos.getZ());

				if (player.world.getBlockState(pos).getBlock() instanceof BlockRoadTrafficLightBase) {

					BlockRoadTrafficLightBase block = (BlockRoadTrafficLightBase) player.world.getBlockState(pos)
							.getBlock();
					if (block.getMode(player.world.getBlockState(pos)) == TrafficLightsControlMode.command_controlled) {
						if (LightsSetList.get(player.world).isDuplicated(pos)) {
							state = state + "\n§r------§m" + posString + "§r §6"
									+ getLocalizedString("dfroads.partial.duplicated");
						} else {
							state = state + "\n§r------" + posString + "§a" + getLocalizedString("dfroads.partial.ok");
						}
					} else {
						state = state + "\n§r------§m" + posString + "§r §6"
								+ getLocalizedString("dfroads.partial.illegalmode");
					}
				} else {
					state = state + "\n§r------§m" + posString + "§r §4"
							+ getLocalizedString("dfroads.partial.destroyed");
				}
			}
			i++;
		}
		player.sendMessage(new TextComponentString(state));
	}

	@Override
	public void execute(MinecraftServer server, EntityPlayer player, String[] args) throws CommandException {

		if (LightsSetList.get(player.world).setExists(args[0])) {
			LightsSet set = LightsSetList.get(player.world).getLightSet(args[0]);
			showInfo(args[0], set, player);
		} else {
			player.sendMessage(new TextComponentTranslation("command.trafficlights.set.nosuchset", args[0]));
		}

	}

	@Override
	public String getHelp() {
		return "command.trafficlights.set.check.help";
	}

	@Override
	protected int expectedArgumentsCount() {
		// TODO Auto-generated method stub
		return 1;
	}
}
