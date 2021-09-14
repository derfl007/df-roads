package derfl007.roads.trafficlights;

import derfl007.roads.Reference;
import derfl007.roads.common.blocks.trafficlights.BlockRoadTrafficLightBase;
import derfl007.roads.common.blocks.trafficlights.BlockRoadTrafficLightBase.LightsState;
import derfl007.roads.common.blocks.trafficlights.BlockRoadTrafficLightBase.TrafficLightsControlMode;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;
import net.minecraftforge.fml.relauncher.Side;

public class ServerTickHandler {

	private int tickCount = 0;

	private BlockRoadTrafficLightBase getTrafficLight(World world, BlockPos pos) {
		if (world.getBlockState(pos).getBlock() instanceof BlockRoadTrafficLightBase) {

			BlockRoadTrafficLightBase block = (BlockRoadTrafficLightBase) world.getBlockState(pos).getBlock();
			if (block.getMode(world.getBlockState(pos)) == TrafficLightsControlMode.command_controlled) {
				return block;
			}
		}
		return null;
	}

	private synchronized boolean shouldIncrementCurrentGroup(World world, long greenStartTime, LightsGroup group) {
		return world.getTotalWorldTime()
				- greenStartTime >= (group.getGreenDuration() + group.getYellowDuration() + group.getDelay())
						* Reference.TICK_RATE;

	}

	private synchronized LightsState expectedLightsState(World world, long greenStartTime, LightsGroup group) {
		long elapsed = world.getTotalWorldTime() - greenStartTime;

		if (elapsed >= group.getGreenDuration() * Reference.TICK_RATE) {

			if (elapsed >= (group.getGreenDuration() + group.getYellowDuration()) * Reference.TICK_RATE) {

				return LightsState.RED;

			}

			return LightsState.YELLOW;
		}

		return LightsState.GREEN;
	}

	private synchronized void updateLightsSet(World world, LightsSet set) {
		if (set.getGreenStartTime() == 0)
			set.initialize(world);

		if (shouldIncrementCurrentGroup(world, set.getGreenStartTime(), set.getCurrentGreenGroup())) {
			set.incrementGreenGroup(world);
		}

		for (int i = 0; i < set.size(); i++) {

			LightsGroup group = set.get(i);

			if (group == set.getCurrentGreenGroup()) {

				LightsState enforcedState = expectedLightsState(world, set.getGreenStartTime(), group);

				for (BlockPos pos : group.getLights()) {
					BlockRoadTrafficLightBase block = getTrafficLight(world, pos);
					if (block == null)
						continue;

					block.setLightsState(world, pos, enforcedState);

				}

			} else {
				for (BlockPos pos : set.get(i).getLights()) {

					BlockRoadTrafficLightBase block = getTrafficLight(world, pos);
					if (block == null)
						continue;

					block.setLightsState(world, pos, LightsState.RED);

				}
			}
		}

	}

	@SubscribeEvent
	public synchronized void onServerTick(ServerTickEvent event) {
		if (event.phase != Phase.END || event.side != Side.SERVER || ++tickCount % 2 != 0)
			return;

		MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();

		for (World world : server.worlds) {
			LightsSetList sets = LightsSetList.get(world);

			for (LightsSet set : sets.getLightSets()) {

				updateLightsSet(world, set);
			}

		}
	}

}
