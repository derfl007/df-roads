package derfl007.roads.common.util;

import net.minecraftforge.common.property.IUnlistedProperty;

public class UnlistedPropertyEnum<T extends Enum<T>> implements IUnlistedProperty<T> {

	private final String name;
	private final Class<T> instance;

	public static <T extends Enum<T>> UnlistedPropertyEnum<T> create(String name, Class<T> instance) {
		return new UnlistedPropertyEnum<T>(name, instance);
	}

	private UnlistedPropertyEnum(String name, Class<T> instance) {
		this.name = name;
		this.instance = instance;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public boolean isValid(T value) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Class<T> getType() {
		// TODO Auto-generated method stub
		return instance;
	}

	@Override
	public String valueToString(T value) {
		// TODO Auto-generated method stub
		return value.name();
	}

}
