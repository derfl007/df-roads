package derfl007.roads.trafficlights;

import derfl007.roads.common.blocks.trafficlights.BlockRoadTrafficLightGen;
import derfl007.roads.common.blocks.trafficlights.BlockRoadTrafficLightGen.Mode;
import derfl007.roads.common.blocks.trafficlights.pedestriantrafficlights.BlockRoadPedestrianTrafficLightGen;
import derfl007.roads.init.RoadBlocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;
import net.minecraftforge.fml.relauncher.Side;

public class ServerTickHandler {

	public static final int TICK_RATE = 20;
	private int tickCount = 0;

	private BlockRoadTrafficLightGen getTrafficLight(World world, BlockPos pos) {
		if (world.getBlockState(pos).getBlock() instanceof BlockRoadTrafficLightGen) {

			BlockRoadTrafficLightGen block = (BlockRoadTrafficLightGen) world.getBlockState(pos).getBlock();
			if (block.getMode(world.getBlockState(pos)) == Mode.command_controlled) {
				return block;
			} /*
				 * else { Roads.logger.warn("Traffic light at " + pos.toString() +
				 * " is set to an illegal mode and was ignored."); }
				 */ // was spamming
		} /*
			 * else { Roads.logger.warn("Traffic light at " + pos.toString() +
			 * " no longer exists and was ignored."); }
			 */ // was spamming
		return null;
	}

	@SubscribeEvent
	public synchronized void onServerTick(ServerTickEvent event) {
		if (event.phase != Phase.END || event.side != Side.SERVER || ++tickCount % 2 != 0)
			return;

		MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();

		for (World world : server.worlds) {
			LightsSetList sets = LightsSetList.get(world);

			for (LightsSet set : sets.getLightSets()) {

				for (int i = 0; i < set.size(); i++) {
					if (i == set.currentGreenGroup) {

						if (set.greenStartTime == 0) // not initialised yet.
							set.greenStartTime = world.getTotalWorldTime();

						if (world.getTotalWorldTime()
								- set.greenStartTime >= (set.get(set.currentGreenGroup).getGreenDuration()
										+ set.get(set.currentGreenGroup).getDelay()) * TICK_RATE
										+ BlockRoadTrafficLightGen.YELLOW_DURATION) {

							for (BlockPos pos : set.get(i).getLights()) {
								BlockRoadTrafficLightGen block = getTrafficLight(world, pos);
								if (block == null)
									continue;

								turnRed(world, pos, block);
							}

							set.currentGreenGroup++;
							set.currentGreenGroup = set.currentGreenGroup % set.size();
							set.greenStartTime = world.getTotalWorldTime();
							sets.markDirty();
							onServerTick(event); // REFRESHING ALL as cycle changed
							return;

						} else if (world.getTotalWorldTime()
								- set.greenStartTime >= set.get(set.currentGreenGroup).getGreenDuration() * TICK_RATE
										+ BlockRoadTrafficLightGen.YELLOW_DURATION) {

							for (BlockPos pos : set.get(i).getLights()) {

								BlockRoadTrafficLightGen block = getTrafficLight(world, pos);
								if (block == null)
									continue;

								turnRed(world, pos, block);

							}

						} else if (world.getTotalWorldTime()
								- set.greenStartTime >= set.get(set.currentGreenGroup).getGreenDuration() * TICK_RATE) {

							for (BlockPos pos : set.get(i).getLights()) {

								BlockRoadTrafficLightGen block = getTrafficLight(world, pos);
								if (block == null)
									continue;

								turnYellow(world, pos, block);

							}

						} else {

							for (BlockPos pos : set.get(i).getLights()) {

								BlockRoadTrafficLightGen block = getTrafficLight(world, pos);
								if (block == null)
									continue;

								turnGreen(world, pos, block);

							}
						}
					} else {
						for (BlockPos pos : set.get(i).getLights()) {

							BlockRoadTrafficLightGen block = getTrafficLight(world, pos);
							if (block == null)
								continue;

							turnRed(world, pos, block);

						}
					}
				}

			}
		}

	}

	private void turnYellow(World world, BlockPos pos, BlockRoadTrafficLightGen block) {
		if (world.getBlockState(pos).getBlock() instanceof BlockRoadPedestrianTrafficLightGen) {
			block.setBlockState(world, pos, world.getBlockState(pos), RoadBlocks.road_pedestrian_traffic_light_red);
		} else {
			block.setBlockState(world, pos, world.getBlockState(pos), RoadBlocks.road_traffic_light_yellow_dyn);
		}
	}

	private void turnRed(World world, BlockPos pos, BlockRoadTrafficLightGen block) {
		if (world.getBlockState(pos).getBlock() instanceof BlockRoadPedestrianTrafficLightGen) {
			block.setBlockState(world, pos, world.getBlockState(pos), RoadBlocks.road_pedestrian_traffic_light_red);
		} else {
			block.setBlockState(world, pos, world.getBlockState(pos), RoadBlocks.road_traffic_light_red_dyn);
		}
	}

	private void turnGreen(World world, BlockPos pos, BlockRoadTrafficLightGen block) {
		if (world.getBlockState(pos).getBlock() instanceof BlockRoadPedestrianTrafficLightGen) {
			block.setBlockState(world, pos, world.getBlockState(pos), RoadBlocks.road_pedestrian_traffic_light_green);
		} else {
			block.setBlockState(world, pos, world.getBlockState(pos), RoadBlocks.road_traffic_light_green_dyn);
		}
	}

}
