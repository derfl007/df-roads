package derfl007.roads.trafficlights;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import derfl007.roads.Roads;
import net.minecraft.world.World;

@SuppressWarnings("serial")
public class LightsSet extends ArrayList<LightsGroup> {

	// private static final String DATA_NAME = Reference.MOD_ID +
	// "_TrafficLightsSets";

	private int currentGreenGroup = -1;
	private long greenStartTime = -1;

	public static LightsSet parseString(String value) throws IllegalArgumentException {
		if (value == null || value.isEmpty()) {
			return null;
		}
		Roads.logger.debug("Parsing set: " + value);
		Pattern pattern = Pattern.compile(formatSpecifier);
		Matcher matcher = pattern.matcher(value);
		if (!matcher.find()) {
			throw new IllegalArgumentException();
		}
		LightsSet set = new LightsSet();
		String[] params = value.substring(1, value.length() - 1).split("!");
		for (String param : params) {
			String[] details = param.split("=", 2);
			switch (details[0]) {
			case "currentGreenGroup":
				set.currentGreenGroup = Integer.valueOf(details[1]);
				break;
			case "greenStartTime":
				set.greenStartTime = Long.valueOf(details[1]);
				break;
			case "groups":
				matcher = pattern.matcher(details[1]);
				if (!matcher.find()) {
					throw new IllegalArgumentException();
				}
				String[] groups = details[1].substring(1, details[1].length() - 1).split(":");
				for (String group : groups) {
					set.add(LightsGroup.parseString(group));
				}
				break;
			default:
				throw new IllegalArgumentException();
			}
		}
		if (set.currentGreenGroup < 0 || set.greenStartTime < 0 || set.isEmpty()) {
			throw new IllegalArgumentException();
		}
		return set;
	}

	@Override
	public boolean equals(Object other) {
		return other instanceof LightsSet && ((other == this) || groupsEqual((LightsSet) other));
	}

	private boolean groupsEqual(LightsSet other) {
		if (this.size() != other.size()) {
			return false;
		}
		for (int i = 0; i < this.size(); i++) {
			if (!this.get(i).equals(other.get(i))) {
				return false;
			}
		}
		return true;
	}

	@Override
	public String toString() {
		List<String> builder = new ArrayList<>();
		for (LightsGroup group : this) {
			builder.add(group.toString());
		}
		return String.format("[currentGreenGroup=%d!greenStartTime=%d!groups=[%s]]", currentGreenGroup, greenStartTime,
				StringUtils.join(builder, ":"));
	}

	private static final String formatSpecifier = "^\\[[\\s\\S]*\\]$";

	public LightsGroup getCurrentGreenGroup() {
		return get(currentGreenGroup);
	}

	public long getGreenStartTime() {
		return greenStartTime;
	}


	public void initialize(World world) {
		currentGreenGroup = 0;
		greenStartTime = world.getTotalWorldTime();
	}
	
	public void incrementGreenGroup(World world) {
		currentGreenGroup++;
		currentGreenGroup %= size();

		greenStartTime = world.getTotalWorldTime();
	}

	void setGreenStartTime(long startTime) {
		this.greenStartTime = startTime;
	}

	boolean setCurrentGreenGroup(int group) {
		if (group < 0)
			return false;

		this.currentGreenGroup = group;
		currentGreenGroup %= size();
		return true;
	}
}
