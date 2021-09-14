package derfl007.roads.common.commands.light;

import derfl007.roads.common.blocks.trafficlights.BlockRoadTrafficLightBase;
import derfl007.roads.common.blocks.trafficlights.BlockRoadTrafficLightBase.TrafficLightsControlMode;
import derfl007.roads.common.commands.CommandTrafficLights;
import derfl007.roads.common.commands.CommandTrafficLightsBase;
import derfl007.roads.trafficlights.LightsSetList;
import derfl007.roads.trafficlights.PlayerTempSetCreation;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class CommandTrafficLightsLightAdd extends CommandTrafficLightsBase {

	@Override
	public String getName() {
		return "add";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "command.trafficlights.light.add.usage";
	}

	public static void addLight(BlockPos pos, EntityPlayer player) {
		if (player.world.getBlockState(pos).getBlock() instanceof BlockRoadTrafficLightBase) {
			BlockRoadTrafficLightBase block = (BlockRoadTrafficLightBase) player.world.getBlockState(pos).getBlock();
			addLight(pos, player, block, player.world, player.world.getBlockState(pos));
		} else {
			player.sendMessage(new TextComponentTranslation("command.trafficlights.light.notalight", pos.getX(),
					pos.getY(), pos.getZ()));
		}
	}

	public static void addLight(BlockPos pos, EntityPlayer player, BlockRoadTrafficLightBase block, World world,
			IBlockState state) {

		if (block.getMode(state) != TrafficLightsControlMode.command_controlled) {
			player.sendMessage(new TextComponentTranslation("command.trafficlights.light.invalidmode"));
			return;
		}

		if (CommandTrafficLights.inProgressCreations.containsKey(player)) {

			PlayerTempSetCreation tempSet = CommandTrafficLights.inProgressCreations.get(player);
			LightsSetList lightsSetList = LightsSetList.get(world);
			if (lightsSetList.lightAlreadyUsed(pos) || lightsSetList.lightAlreadyUsed(tempSet, pos)) {
				if (!tempSet.replacing || lightsSetList.lightAlreadyUsed(tempSet, pos)
						|| !lightsSetList.lightAlreadyUsed(lightsSetList.getLightSet(tempSet.tempSetName), pos)) {
					player.sendMessage(new TextComponentTranslation("command.trafficlights.light.duplicate", pos.getX(),
							pos.getY(), pos.getZ()));
					return;
				}
			}

			tempSet.get(tempSet.selectedGroupIndex).getLights().add(pos);
			player.sendMessage(new TextComponentTranslation("command.trafficlights.light.added", pos.getX(), pos.getY(),
					pos.getZ()));

		} else {
			player.sendMessage(new TextComponentTranslation("command.trafficlights.set.none"));
		}
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

		addLight(new BlockPos(x, y, z), player);

	}

	@Override
	public String getHelp() {
		return "command.trafficlights.light.add.help";
	}

	@Override
	protected int expectedArgumentsCount() {
		// TODO Auto-generated method stub
		return 3;
	}

}