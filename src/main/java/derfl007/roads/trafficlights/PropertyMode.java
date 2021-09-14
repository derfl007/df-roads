package derfl007.roads.trafficlights;

import java.util.Arrays;
import java.util.Collection;

import com.google.common.base.Optional;

import derfl007.roads.common.blocks.trafficlights.BlockRoadTrafficLightBase;
import derfl007.roads.common.blocks.trafficlights.BlockRoadTrafficLightBase.TrafficLightsControlMode;
import net.minecraft.block.properties.PropertyHelper;

public class PropertyMode extends PropertyHelper<BlockRoadTrafficLightBase.TrafficLightsControlMode> {

	public PropertyMode(String name) {
		super(name, TrafficLightsControlMode.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Collection<TrafficLightsControlMode> getAllowedValues() {
		// TODO Auto-generated method stub
		TrafficLightsControlMode[] modes = BlockRoadTrafficLightBase.TrafficLightsControlMode.values();
		return Arrays.asList(modes);
	}

	@Override
	public Optional<TrafficLightsControlMode> parseValue(String value) {
		// TODO Auto-generated method stub		
		TrafficLightsControlMode mode;
		try {
			mode = TrafficLightsControlMode.valueOf(value);
		}catch(IllegalArgumentException ex) {
			return Optional.absent();
		}
		if(!getAllowedValues().contains(mode)) {
			return Optional.absent();
		}
		return Optional.of(mode);
	}

	@Override
	public String getName(TrafficLightsControlMode value) {
		// TODO Auto-generated method stub
		 return value.name();
	}

}
