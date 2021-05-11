package derfl007.roads.common.util;

import net.minecraftforge.common.property.IUnlistedProperty;

public class UnlistedPropertyInteger implements IUnlistedProperty<Integer> {

	private final int minValue;
	private final int maxValue;

	private final String name;

	public UnlistedPropertyInteger(String name, int minValue, int maxValue) {
		this.name = name;
		this.minValue = minValue;
		this.maxValue = maxValue;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public boolean isValid(Integer value) {
		// TODO Auto-generated method stub
		return value >= minValue && value <= maxValue;
	}

	@Override
	public Class<Integer> getType() {
		// TODO Auto-generated method stub
		return Integer.class;
	}

	@Override
	public String valueToString(Integer value) {
		// TODO Auto-generated method stub
		return String.valueOf(value);
	}

}
