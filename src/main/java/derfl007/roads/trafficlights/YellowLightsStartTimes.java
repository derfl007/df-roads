package derfl007.roads.trafficlights;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import derfl007.roads.Reference;
import derfl007.roads.Roads;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;

public class YellowLightsStartTimes extends WorldSavedData{

	private static final String DATA_NAME = Reference.MOD_ID + "_YellowLightsStartTimes";

	public YellowLightsStartTimes(String name) {
		super(name);
	}

	public YellowLightsStartTimes() {
		super(DATA_NAME);
	}

	private final Map<BlockPos, Long> addedTimes = new HashMap<>();

	/**
	 * Call markDirty if changes have been made to the map.
	 */
	public Map<BlockPos, Long> getAddedTimes() {
		return addedTimes;
	}

	public Long getAddedTime(World worldIn, BlockPos pos) {
		if (!addedTimes.containsKey(pos)) {
			updateAddedTime(worldIn, pos);
		}
		return addedTimes.get(pos);
	}
	
	public void updateAddedTime(World worldIn, BlockPos pos) {
		addedTimes.put(pos, worldIn.getTotalWorldTime());
		markDirty();
	}
	
	public void removeAddedTime(BlockPos pos) {
		addedTimes.remove(pos);
		markDirty();
	}

	@Override
	public String toString() {
		List<String> list = new ArrayList<>();
		for (Entry<BlockPos, Long> entry : addedTimes.entrySet()) {
			list.add("["
					+ String.format("(%d,%d,%d)", entry.getKey().getX(), entry.getKey().getY(), entry.getKey().getZ())
					+ ":" + entry.getValue() + "]");
		}
		return String.join(";", list);
	}

	public void parseString(String value) throws IllegalArgumentException {
		addedTimes.clear();
		String[] objects = value.split(";");
		for (String startTime : objects) {
			Pattern pattern = Pattern.compile(formatSpecifier);
			Matcher matcher = pattern.matcher(startTime);
			if (!matcher.find()) {
				throw new IllegalArgumentException();
			}
			String[] details = startTime.substring(1, startTime.length() - 1).split("=", 2);
			String[] coord = details[0].substring(1, details[0].length() - 1).split(",");
			BlockPos blockPos = new BlockPos(Integer.valueOf(coord[0]), Integer.valueOf(coord[1]),
					Integer.valueOf(coord[2]));
			addedTimes.put(blockPos, Long.valueOf(details[1]));
		}

	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		try {
			parseString(nbt.getString("value"));
		} catch (IllegalArgumentException ex) {
			addedTimes.clear();
			Roads.logger.warn("Failed to load yellow lights start times, invalid value " + nbt.getString("value"));
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		// TODO Auto-generated method stub
		compound.setString("value", toString());
		return compound;
	}
	
	public static YellowLightsStartTimes get(World world) {
		MapStorage storage = world.getPerWorldStorage();
		YellowLightsStartTimes instance = (YellowLightsStartTimes) storage.getOrLoadData(YellowLightsStartTimes.class, DATA_NAME);

		if (instance == null) {
			instance = new YellowLightsStartTimes();
			storage.setData(DATA_NAME, instance);
		}
		return instance;
	}

	private static final String formatSpecifier = "^\\[\\((-)?\\d+,(-)?\\d+,(-)?\\d+\\):(-)?\\d+\\]$";

}
