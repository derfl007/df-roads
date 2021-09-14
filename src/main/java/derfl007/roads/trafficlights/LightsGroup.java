package derfl007.roads.trafficlights;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import derfl007.roads.common.blocks.trafficlights.BlockRoadTrafficLightBase;
import net.minecraft.util.math.BlockPos;

public class LightsGroup {
	
	private int greenDuration = -1;
	private int delay = -1;
	private int yellowDuration = BlockRoadTrafficLightBase.DEFAULT_YELLOW_DURATION;

	private List<BlockPos> lights;

	public LightsGroup(List<BlockPos> lights, int greenDuration, int delay) {
		if (greenDuration < 0 || delay < 0) {
			throw new IllegalArgumentException("Duration and delay should be negative.");
		}
		this.lights = new ArrayList<>(lights);
		this.greenDuration = greenDuration;
		this.delay = delay;
	}

	public LightsGroup(LightsGroup baseGroup) {
		this.lights = new ArrayList<>(baseGroup.lights);
		this.greenDuration = baseGroup.greenDuration;
		this.delay = baseGroup.delay;
	}

	public LightsGroup() {
		// TODO Auto-generated constructor stub
		lights = new ArrayList<>();
	}

	public int getGreenDuration() {
		return greenDuration;
	}

	public void setGreenDuration(int greenDuration) {
		this.greenDuration = greenDuration;
	}

	public List<BlockPos> getLights() {
		return lights;
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}
	
	public int getYellowDuration() {
		return yellowDuration;
	}
	
	public void setYellowDuration(int duration) {
		this.yellowDuration = duration;
	}

	@Override
	public boolean equals(Object other) {
		return other instanceof LightsGroup && ((other == this) || lightsEqual((LightsGroup) other));
	}

	private boolean lightsEqual(LightsGroup other) {
		if (this.lights.size() != other.lights.size()) {
			return false;
		}
		for (int i = 0; i < this.lights.size(); i++) {
			if (!this.lights.get(i).equals(other.lights.get(i))) {
				return false;
			}
		}
		return true;
	}

	@Override
	public String toString() {
		List<String> builder = new ArrayList<>();
		for (BlockPos pos : lights) {
			builder.add(String.format("(%d,%d,%d)", pos.getX(), pos.getY(), pos.getZ()));
		}
		return String.format("[greenDuration=%d;yellowDuration=%d;delay=%d;pos=[%s]]", greenDuration, yellowDuration, delay,
				StringUtils.join(builder, "|"));
	}

	public static LightsGroup parseString(String value) throws IllegalArgumentException {
		if (value == null || value.isEmpty()) {
			return null;
		}
		Pattern pattern = Pattern.compile(formatSpecifier);
		Matcher matcher = pattern.matcher(value);
		if (!matcher.find()) {
			throw new IllegalArgumentException();
		}
		LightsGroup group = new LightsGroup();
		String[] params = value.substring(1, value.length() - 1).split(";");
		for (String param : params) {
			String[] details = param.split("=", 2);
			switch (details[0]) {
			case "greenDuration":
				group.greenDuration = Integer.valueOf(details[1]);
				break;
			case "yellowDuration":
				group.yellowDuration = Integer.valueOf(details[1]);
			case "delay":
				group.delay = Integer.valueOf(details[1]);
				break;
			case "pos":
				String[] posList = details[1].substring(1, details[1].length() - 1).split("\\|");
				Pattern pattern1 = Pattern.compile(posFormat);
				for (String pos : posList) {
					Matcher matcher1 = pattern1.matcher(pos);
					if (!matcher1.find()) {
						throw new IllegalArgumentException();
					}
					String[] coord = pos.substring(1, pos.length() - 1).split(",");
					if (coord.length != 3) {
						throw new IllegalArgumentException();
					}
					BlockPos blockPos = new BlockPos(Integer.valueOf(coord[0]), Integer.valueOf(coord[1]),
							Integer.valueOf(coord[2]));
					if (group.lights.contains(blockPos)) {
						throw new IllegalArgumentException();
					}
					group.lights.add(blockPos);
				}
				break;
			default:
				throw new IllegalArgumentException();
			}
		}
		if (group.greenDuration < 1 || group.delay < 0 || group.lights == null || group.lights.isEmpty()) {
			throw new IllegalArgumentException();
		}
		return group;
	}

	private static final String posFormat = "^\\((-)?\\d+,(-)?\\d+,(-)?\\d+\\)$";
	private static final String formatSpecifier = "^\\[[\\s\\S]*\\]$";

}
