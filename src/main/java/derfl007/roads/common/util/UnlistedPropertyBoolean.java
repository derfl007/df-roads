package derfl007.roads.common.util;

import net.minecraftforge.common.property.IUnlistedProperty;

public class UnlistedPropertyBoolean implements IUnlistedProperty<Boolean> {
	
	private final String name;
	
	public UnlistedPropertyBoolean(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public boolean isValid(Boolean value) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Class<Boolean> getType() {
		// TODO Auto-generated method stub
		return Boolean.class;
	}

	@Override
	public String valueToString(Boolean value) {
		// TODO Auto-generated method stub
		return String.valueOf(value);
	}

}
