package derfl007.roads.trafficlights;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

import derfl007.roads.Reference;
import derfl007.roads.Roads;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;

public class LightsSetList extends WorldSavedData implements Iterable<Map.Entry<String, LightsSet>> {

	private static final String DATA_NAME = Reference.MOD_ID + "_TrafficLightsSets";

	public LightsSetList(String name) {
		super(name);
	}

	public LightsSetList() {
		super(DATA_NAME);
	}

	private Map<String, LightsSet> lightsSets = new HashMap<>();

	/**
	 * markDirty will be automatically called.
	 * 
	 * @return
	 */
	public synchronized void addLightSet(String name, LightsSet value) {
		addLightSet(name, value, false, false);
	}

	/**
	 * markDirty will be automatically called.
	 * 
	 * @return
	 */
	public synchronized void addLightSet(String name, LightsSet value, boolean force, boolean resetSyncState) {
		if (!force && lightsSets.containsKey(name)) {
			return;
		}
		if (force && !resetSyncState && lightsSets.containsKey(name)) {

			if (value.setCurrentGreenGroup(value.indexOf(lightsSets.get(name).getCurrentGreenGroup()))) {
				value.setGreenStartTime(lightsSets.get(name).getGreenStartTime());
			}

		} else {
			value.setCurrentGreenGroup(0);
			value.setGreenStartTime(0);
		}
		lightsSets.put(name, value);
		this.markDirty();
//		return true;

	}

	/**
	 * markDirty will be automatically called.
	 * 
	 * @return
	 */
	public boolean removeLightSet(String name) {
		try {
			return lightsSets.containsKey(name) && lightsSets.remove(name) != null;
		} finally {
			this.markDirty();
		}
	}

	/**
	 * Call markDirty if changes have been made to the set.
	 * 
	 * @return
	 */
	@Nullable
	public LightsSet getLightSet(String name) {
		return lightsSets.get(name);
	}

	/**
	 * Call markDirty if changes have been made to the collection.
	 * 
	 * @return
	 */
	public Set<String> getLightSetsNames() {
		return lightsSets.keySet();
	}

	/**
	 * Call markDirty if changes have been made to the collection.
	 * 
	 * @return
	 */
	public Collection<LightsSet> getLightSets() {
		return lightsSets.values();
	}

	public boolean setExists(String name) {
		return lightsSets.containsKey(name);
	}

	public static LightsSetList get(World world) {
		MapStorage storage = world.getPerWorldStorage();
		LightsSetList instance = (LightsSetList) storage.getOrLoadData(LightsSetList.class, DATA_NAME);

		if (instance == null) {
			instance = new LightsSetList();
			storage.setData(DATA_NAME, instance);
		}
		return instance;
	}

	@Override
	public Iterator<Map.Entry<String, LightsSet>> iterator() {
		return lightsSets.entrySet().iterator();
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		// TODO Auto-generated method stub
		lightsSets.clear();
		for (String setName : nbt.getKeySet()) {
			try {
				lightsSets.put(setName, LightsSet.parseString(nbt.getString(setName)));
			} catch (IllegalArgumentException ex) {
				ex.printStackTrace();
				Roads.logger.error(String.format("Failed to parse saved traffic lights set %s with value %s", setName,
						nbt.getString(setName)));
			}

		}

		// CHECKING
		BlockPos blockPos;
		if ((blockPos = findFirstDuplicatedLight()) != null) {
			lightsSets.clear();
			Roads.logger.error(String.format(
					"Found traffic lights linked to multiple groups; this is not authorized. Traffic lights sets cleared. First duplicate found at %s",
					blockPos.toString()));
			return;
		}
	}

	public BlockPos findFirstDuplicatedLight() {
		List<BlockPos> posList = new ArrayList<>();
		for (LightsSet set : lightsSets.values()) {
			for (LightsGroup group : set) {
				for (BlockPos blockPos : group.getLights()) {
					if (posList.contains(blockPos)) {
						return blockPos;
					}
					posList.add(blockPos);
				}
			}
		}
		posList.clear();
		return null;
	}

	public boolean isDuplicated(BlockPos lightPos) {
		boolean found = false;
		for (LightsSet set : lightsSets.values()) {
			for (LightsGroup group : set) {
				for (BlockPos blockPos : group.getLights()) {
					if (blockPos.equals(lightPos)) {
						if (found) {
							return true;
						} else {
							found = true;
						}
					}

				}
			}
		}
		return false;
	}

	public boolean lightAlreadyUsed(BlockPos pos) {
		for (LightsSet set : lightsSets.values()) {
			if (lightAlreadyUsed(set, pos)) {
				return true;
			}
		}
		return false;

	}

	public boolean lightAlreadyUsed(LightsSet set, BlockPos pos) {
		for (LightsGroup group : set) {
			for (BlockPos blockPos : group.getLights()) {
				if (blockPos.equals(pos)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		// TODO Auto-generated method stub
		for (Map.Entry<String, LightsSet> set : lightsSets.entrySet()) {
			compound.setString(set.getKey(), set.getValue().toString());
		}
		return compound;
	}
}
