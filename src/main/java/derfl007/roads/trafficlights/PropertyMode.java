package derfl007.roads.trafficlights;

import java.util.Arrays;
import java.util.Collection;

import com.google.common.base.Optional;

import derfl007.roads.common.blocks.trafficlights.BlockRoadTrafficLightGen;
import derfl007.roads.common.blocks.trafficlights.BlockRoadTrafficLightGen.Mode;
import net.minecraft.block.properties.PropertyHelper;

public class PropertyMode extends PropertyHelper<BlockRoadTrafficLightGen.Mode> {

	public PropertyMode(String name) {
		super(name, Mode.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Collection<Mode> getAllowedValues() {
		// TODO Auto-generated method stub
		Mode[] modes = BlockRoadTrafficLightGen.Mode.values();
		return Arrays.asList(modes);
	}

	@Override
	public Optional<Mode> parseValue(String value) {
		// TODO Auto-generated method stub		
		Mode mode;
		try {
			mode = Mode.valueOf(value);
		}catch(IllegalArgumentException ex) {
			return Optional.absent();
		}
		if(!getAllowedValues().contains(mode)) {
			return Optional.absent();
		}
		return Optional.of(mode);
	}

	@Override
	public String getName(Mode value) {
		// TODO Auto-generated method stub
		 return value.name();
	}

}
