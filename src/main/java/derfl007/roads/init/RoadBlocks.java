package derfl007.roads.init;

import java.util.LinkedList;
import java.util.List;

import derfl007.roads.Roads;
import derfl007.roads.common.blocks.BlockAsphalt;
import derfl007.roads.common.blocks.BlockRoadGuardrail;
import derfl007.roads.common.blocks.BlockRoadLantern;
import derfl007.roads.common.blocks.BlockRoadLine;
import derfl007.roads.common.blocks.BlockRoadLines;
import derfl007.roads.common.blocks.BlockRoadRotatable;
import derfl007.roads.common.blocks.BlockRoadSidewalk;
import derfl007.roads.common.blocks.BlockRoadSign;
import derfl007.roads.common.blocks.BlockRoadSignPost;
import derfl007.roads.common.blocks.BlockRoadTownSign;
import derfl007.roads.common.blocks.BlockSignPrinter;
import derfl007.roads.common.blocks.trafficlights.BlockRoadTrafficLight;
import derfl007.roads.common.blocks.trafficlights.BlockRoadTrafficLightBlinkingYellow;
import derfl007.roads.common.blocks.trafficlights.BlockRoadTrafficLightGreen;
import derfl007.roads.common.blocks.trafficlights.BlockRoadTrafficLightRed;
import derfl007.roads.common.blocks.trafficlights.BlockRoadTrafficLightYellow;
import derfl007.roads.common.blocks.trafficlights.pedestriantrafficlights.BlockRoadPedestrianTrafficLight;
import derfl007.roads.common.blocks.trafficlights.pedestriantrafficlights.BlockRoadPedestrianTrafficLightGreen;
import derfl007.roads.common.blocks.trafficlights.pedestriantrafficlights.BlockRoadPedestrianTrafficLightOff;
import derfl007.roads.common.blocks.trafficlights.pedestriantrafficlights.BlockRoadPedestrianTrafficLightRed;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RoadBlocks {

	public static Block asphalt, // Asphalt Block
			road, // Road Block

			road_white, // White block
			road_white_half, // Half white block
			road_white_quarter, // Quarter white block
			road_line, // Single line
			road_line_simple, // Single line (not connecting)
			road_line_merge, // Merge single and double lines
			road_line_double, // Double Line
			road_line_double_simple, // Double Line (not connecting)
			road_line_half_double, // Half double line
			road_line_half_double_simple, // Half double line (not connecting)
			road_line_diagonal, // Diagonal Line
			road_arrow_s, // straight arrow
			road_arrow_r, // right arrow
			road_arrow_l, // left arrow
			road_arrow_rl, // Right- and left arrow
			road_arrow_sr, // Straight- and right arrow
			road_arrow_sl, // Straight- and left arrow
			road_arrow_srl, // Straight-, right- and left arrow
			road_crosswalk, // Crosswalk
			road_excl_zone, //
			road_excl_zone_line, //
			road_excl_zone_corner_in, //
			road_excl_zone_corner_out, //
			road_excl_zone_diagonal_in, //
			road_excl_zone_diagonal_out, //
			road_excl_zone_split_in_r, //
			road_excl_zone_split_in_l, //
			road_excl_zone_split_out_r, //
			road_excl_zone_split_out_l, //

			road_yellow, // White block
			road_white_half_yellow, // Half white block
			road_white_quarter_yellow, // Quarter white block
			road_line_yellow, // Single line
			road_line_simple_yellow, // Single line (not connecting)
			road_line_merge_yellow, // Merge single and double lines
			road_line_double_yellow, // Double Line
			road_line_double_simple_yellow, // Double Line (not connecting)
			road_line_half_double_yellow, // Half double line
			road_line_half_double_simple_yellow, // Half double line (not connecting)
			road_line_diagonal_yellow, // Diagonal Line
			road_arrow_s_yellow, // straight arrow
			road_arrow_r_yellow, // right arrow
			road_arrow_l_yellow, // left arrow
			road_arrow_rl_yellow, // Right- and left arrow
			road_arrow_sr_yellow, // Straight- and right arrow
			road_arrow_sl_yellow, // Straight- and left arrow
			road_arrow_srl_yellow, // Straight-_yellow, right- and left arrow
			road_crosswalk_yellow, // Crosswalk
			road_excl_zone_yellow, //
			road_excl_zone_line_yellow, //
			road_excl_zone_corner_in_yellow, //
			road_excl_zone_corner_out_yellow, //
			road_excl_zone_diagonal_in_yellow, //
			road_excl_zone_diagonal_out_yellow, //
			road_excl_zone_split_in_r_yellow, //
			road_excl_zone_split_in_l_yellow, //
			road_excl_zone_split_out_r_yellow, //
			road_excl_zone_split_out_l_yellow, //

			road_sidewalk, // Sidewalk
			road_guardrail, // Guardrail
			road_sign_post, // Sign Post

			road_sign_prohib_1, // closed in both directions for all vehicles
			road_sign_prohib_2, // no entry
			road_sign_prohib_3a, // no left turn
			road_sign_prohib_3b, // no right turn
			road_sign_prohib_3c, // no u-turn
			road_sign_prohib_4a, // no overtaking
			road_sign_prohib_4b, // end of overtaking restriction
			road_sign_prohib_4c, road_sign_prohib_4d, road_sign_prohib_5, road_sign_prohib_6a, road_sign_prohib_6b,
			road_sign_prohib_6c, road_sign_prohib_6d, road_sign_prohib_7a, road_sign_prohib_7al, road_sign_prohib_7aw,
			road_sign_prohib_7b, road_sign_prohib_7c, road_sign_prohib_7e, road_sign_prohib_7f, road_sign_prohib_8a,
			road_sign_prohib_8b, road_sign_prohib_8c, road_sign_prohib_9a, road_sign_prohib_9b, road_sign_prohib_9c,
			road_sign_prohib_9d, road_sign_prohib_10a20, // speed sign 20
			road_sign_prohib_10a30, // speed sign 30
			road_sign_prohib_10a40, // speed sign 40
			road_sign_prohib_10a50, // speed sign 50
			road_sign_prohib_10a60, // speed sign 60
			road_sign_prohib_10a70, // speed sign 70
			road_sign_prohib_10a80, // speed sign 80
			road_sign_prohib_10a100, // speed sign 100
			road_sign_prohib_10b20, // end speed sign 20
			road_sign_prohib_10b30, // end speed sign 30
			road_sign_prohib_10b40, // end speed sign 40
			road_sign_prohib_10b50, // end speed sign 50
			road_sign_prohib_10b60, // end speed sign 60
			road_sign_prohib_10b70, // end speed sign 70
			road_sign_prohib_10b80, // end speed sign 80
			road_sign_prohib_10b100, // end speed sign 100
			road_sign_prohib_11, road_sign_prohib_11a, road_sign_prohib_11b, road_sign_prohib_12, road_sign_prohib_13a,
			road_sign_prohib_13b, road_sign_prohib_13d, road_sign_prohib_13e, road_sign_prohib_14,

			road_sign_warn_1, // uneven road
			road_sign_warn_2a, // dangerous right curve
			road_sign_warn_2b, // dangerous left curve
			road_sign_warn_2c, // dangerous curves, first right
			road_sign_warn_2d, // dangerous curves, first left
			road_sign_warn_3a, // junction
			road_sign_warn_3b, // junction with roundabout
			road_sign_warn_4, // crossroad with non-priority road
			road_sign_warn_6a, road_sign_warn_6b, road_sign_warn_6c_i_r, road_sign_warn_6c_i_l, road_sign_warn_6c_ii_r,
			road_sign_warn_6c_ii_l, road_sign_warn_6c_iii_r, road_sign_warn_6c_iii_l, road_sign_warn_6d_a,
			road_sign_warn_6d_b, road_sign_warn_6d_c, road_sign_warn_6d_d, road_sign_warn_7, road_sign_warn_7a,
			road_sign_warn_8a, // road narrows on both sides
			road_sign_warn_8b, // road narrows from left
			road_sign_warn_8c, // road narrows from right
			road_sign_warn_9, // road works
			road_sign_warn_10, road_sign_warn_10a, // slippery road
			road_sign_warn_10b, // crosswind
			road_sign_warn_10c, // falling rocks
			road_sign_warn_11a, // pedestrian crossing
			road_sign_warn_11b, // cyclist crossing
			road_sign_warn_12, // children
			road_sign_warn_13, // animals
			road_sign_warn_14, // two-way traffic
			road_sign_warn_15, // traffic signals
			road_sign_warn_16, // other dangers

			road_sign_mandat_1, // left only
			road_sign_mandat_2, // right only
			road_sign_mandat_3, // straight only
			road_sign_mandat_4, // turn left
			road_sign_mandat_5, // turn right
			road_sign_mandat_6, // turn left or go straight
			road_sign_mandat_7, // turn right or go straight
			road_sign_mandat_8, // turn left or right
			road_sign_mandat_9, // follow left lane
			road_sign_mandat_10, // follow right lane
			road_sign_mandat_16, road_sign_mandat_16a, road_sign_mandat_17, road_sign_mandat_17a_a,
			road_sign_mandat_17a_b, road_sign_mandat_17a_c, road_sign_mandat_17a_d, road_sign_mandat_17b,
			road_sign_mandat_17c, road_sign_mandat_18, road_sign_mandat_22, road_sign_mandat_22a,

			road_sign_priority_1, // give way
			road_sign_priority_2, // stop
			road_sign_priority_3a, // priority road
			road_sign_priority_3b, // end of priority road

			road_sign_info_1a, // parking lot or parking lane
			road_sign_info_1b, // parking lot
			road_sign_info_2, // hospital
			road_sign_info_2a, // pedestrian crossing
			road_sign_info_2b, // bicycle crossing
			road_sign_info_2c_a, // pedestrian and bicycle crossing
			road_sign_info_2c_b, // bicycle and pedestrian crossing
			road_sign_info_3, // first aid
			road_sign_info_3a, // church
			road_sign_info_4, // breakdown services
			road_sign_info_5, // telephone
			road_sign_info_6a, // taxi
			road_sign_info_7a, // priority over oncoming traffic
			road_sign_info_8a, // expressway
			road_sign_info_8b, // end of expressway
			road_sign_info_8c, // motor road
			road_sign_info_8d, // end of motor road
			road_sign_info_9a, // pedestrian zone
			road_sign_info_9b, // end of pedestrian zone
			road_sign_info_9c, // residential street
			road_sign_info_9d, // end of residential street
			road_sign_info_9e, // shared space zone
			road_sign_info_9f, // end of shared space zone
			road_sign_info_9g, // tunnel
			road_sign_info_10a, // one-way left
			road_sign_info_10b, // one-way right
			road_sign_info_10c, // one-way left (german)
			road_sign_info_10d, // one-way right (german)
			road_sign_info_11, // dead end
			road_sign_info_16a, // detour right
			road_sign_info_16b, // detour left
			road_sign_info_16c, // detour right (german)
			road_sign_info_16d, // detour left (german)
			road_sign_info_17a, // town sign
			road_sign_info_17b, // end of town sign
			road_sign_info_23, // two lanes merge into one
			road_sign_info_23b, // two lanes merge into one (german)

			road_lantern, // Road Lantern unlit
			road_lantern_lit, // Road Lantern lit

			road_traffic_light, // Traffic Light (Auto animated)
			road_traffic_light_manual, // Traffic Light (Can be switched)
			road_traffic_light_yellow_fixed, // Traffic Light (Yellow) (fixed)
			road_traffic_light_red, // Traffic Light (Red) (Cannot be switched)
			road_traffic_light_red_dyn,// Traffic Light (Red)
			road_traffic_light_green, // Traffic Light (Green) (Cannot be switched)
			road_traffic_light_green_dyn,// Traffic Light (Green)
			road_traffic_light_yellow_dyn, // Traffic Light (Yellow) (Switching to red)
			road_traffic_light_yellow_blinking, // Traffic Light (Yellow blinking)

//			road_traffic_light_off,

			road_pedestrian_traffic_light, // Pedestrian Traffic Light (auto)
			road_pedestrian_traffic_light_manual, // Pedestrian Traffic Light (manual)
			road_pedestrian_traffic_light_green, road_pedestrian_traffic_light_red, road_pedestrian_traffic_light_off,
			sign_printer;

	public static void init() {
		asphalt = new BlockAsphalt("asphalt");
		road = new BlockRoadRotatable("road");
		// white road blocks
		road_white = new BlockRoadRotatable("road_white");
		road_white_half = new BlockRoadRotatable("road_white_half");
		road_white_quarter = new BlockRoadRotatable("road_white_quarter");
		road_line = new BlockRoadLine("road_line");
		road_line_simple = new BlockRoadRotatable("road_line_simple");
		road_line_merge = new BlockRoadRotatable("road_line_merge");
		road_line_double = new BlockRoadLines("road_line_double");
		road_line_double_simple = new BlockRoadRotatable("road_line_double_simple");
		road_line_half_double = new BlockRoadLines("road_line_half_double");
		road_line_half_double_simple = new BlockRoadRotatable("road_line_half_double_simple");
		road_line_diagonal = new BlockRoadRotatable("road_line_diagonal");
		road_arrow_s = new BlockRoadRotatable("road_arrow_s");
		road_arrow_r = new BlockRoadRotatable("road_arrow_r");
		road_arrow_l = new BlockRoadRotatable("road_arrow_l");
		road_arrow_rl = new BlockRoadRotatable("road_arrow_rl");
		road_arrow_sr = new BlockRoadRotatable("road_arrow_sr");
		road_arrow_sl = new BlockRoadRotatable("road_arrow_sl");
		road_arrow_srl = new BlockRoadRotatable("road_arrow_srl");
		road_crosswalk = new BlockRoadRotatable("road_crosswalk");
		road_excl_zone = new BlockRoadRotatable("road_excl_zone");
		road_excl_zone_line = new BlockRoadRotatable("road_excl_zone_line");
		road_excl_zone_corner_in = new BlockRoadRotatable("road_excl_zone_corner_in");
		road_excl_zone_corner_out = new BlockRoadRotatable("road_excl_zone_corner_out");
		road_excl_zone_diagonal_in = new BlockRoadRotatable("road_excl_zone_diagonal_in");
		road_excl_zone_diagonal_out = new BlockRoadRotatable("road_excl_zone_diagonal_out");
		road_excl_zone_split_in_r = new BlockRoadRotatable("road_excl_zone_split_in_r");
		road_excl_zone_split_in_l = new BlockRoadRotatable("road_excl_zone_split_in_l");
		road_excl_zone_split_out_r = new BlockRoadRotatable("road_excl_zone_split_out_r");
		road_excl_zone_split_out_l = new BlockRoadRotatable("road_excl_zone_split_out_l");

		// yellow road blocks
		road_yellow = new BlockRoadRotatable("road_yellow").setCreativeTab(Roads.YELLOW_ROADS_TAB);
		road_white_half_yellow = new BlockRoadRotatable("road_white_half_yellow")
				.setCreativeTab(Roads.YELLOW_ROADS_TAB);
		road_white_quarter_yellow = new BlockRoadRotatable("road_white_quarter_yellow")
				.setCreativeTab(Roads.YELLOW_ROADS_TAB);
		road_line_yellow = new BlockRoadLine("road_line_yellow").setCreativeTab(Roads.YELLOW_ROADS_TAB);
		road_line_simple_yellow = new BlockRoadRotatable("road_line_simple_yellow")
				.setCreativeTab(Roads.YELLOW_ROADS_TAB);
		road_line_merge_yellow = new BlockRoadRotatable("road_line_merge_yellow")
				.setCreativeTab(Roads.YELLOW_ROADS_TAB);
		road_line_double_yellow = new BlockRoadLines("road_line_double_yellow").setCreativeTab(Roads.YELLOW_ROADS_TAB);
		road_line_double_simple_yellow = new BlockRoadRotatable("road_line_double_simple_yellow")
				.setCreativeTab(Roads.YELLOW_ROADS_TAB);
		road_line_half_double_yellow = new BlockRoadLines("road_line_half_double_yellow")
				.setCreativeTab(Roads.YELLOW_ROADS_TAB);
		road_line_half_double_simple_yellow = new BlockRoadRotatable("road_line_half_double_simple_yellow")
				.setCreativeTab(Roads.YELLOW_ROADS_TAB);
		road_line_diagonal_yellow = new BlockRoadRotatable("road_line_diagonal_yellow")
				.setCreativeTab(Roads.YELLOW_ROADS_TAB);
		road_arrow_s_yellow = new BlockRoadRotatable("road_arrow_s_yellow").setCreativeTab(Roads.YELLOW_ROADS_TAB);
		road_arrow_r_yellow = new BlockRoadRotatable("road_arrow_r_yellow").setCreativeTab(Roads.YELLOW_ROADS_TAB);
		road_arrow_l_yellow = new BlockRoadRotatable("road_arrow_l_yellow").setCreativeTab(Roads.YELLOW_ROADS_TAB);
		road_arrow_rl_yellow = new BlockRoadRotatable("road_arrow_rl_yellow").setCreativeTab(Roads.YELLOW_ROADS_TAB);
		road_arrow_sr_yellow = new BlockRoadRotatable("road_arrow_sr_yellow").setCreativeTab(Roads.YELLOW_ROADS_TAB);
		road_arrow_sl_yellow = new BlockRoadRotatable("road_arrow_sl_yellow").setCreativeTab(Roads.YELLOW_ROADS_TAB);
		road_arrow_srl_yellow = new BlockRoadRotatable("road_arrow_srl_yellow").setCreativeTab(Roads.YELLOW_ROADS_TAB);
		road_crosswalk_yellow = new BlockRoadRotatable("road_crosswalk_yellow").setCreativeTab(Roads.YELLOW_ROADS_TAB);
		road_excl_zone_yellow = new BlockRoadRotatable("road_excl_zone_yellow").setCreativeTab(Roads.YELLOW_ROADS_TAB);
		road_excl_zone_line_yellow = new BlockRoadRotatable("road_excl_zone_line_yellow")
				.setCreativeTab(Roads.YELLOW_ROADS_TAB);
		road_excl_zone_corner_in_yellow = new BlockRoadRotatable("road_excl_zone_corner_in_yellow")
				.setCreativeTab(Roads.YELLOW_ROADS_TAB);
		road_excl_zone_corner_out_yellow = new BlockRoadRotatable("road_excl_zone_corner_out_yellow")
				.setCreativeTab(Roads.YELLOW_ROADS_TAB);
		road_excl_zone_diagonal_in_yellow = new BlockRoadRotatable("road_excl_zone_diagonal_in_yellow")
				.setCreativeTab(Roads.YELLOW_ROADS_TAB);
		road_excl_zone_diagonal_out_yellow = new BlockRoadRotatable("road_excl_zone_diagonal_out_yellow")
				.setCreativeTab(Roads.YELLOW_ROADS_TAB);
		road_excl_zone_split_in_r_yellow = new BlockRoadRotatable("road_excl_zone_split_in_r_yellow")
				.setCreativeTab(Roads.YELLOW_ROADS_TAB);
		road_excl_zone_split_in_l_yellow = new BlockRoadRotatable("road_excl_zone_split_in_l_yellow")
				.setCreativeTab(Roads.YELLOW_ROADS_TAB);
		road_excl_zone_split_out_r_yellow = new BlockRoadRotatable("road_excl_zone_split_out_r_yellow")
				.setCreativeTab(Roads.YELLOW_ROADS_TAB);
		road_excl_zone_split_out_l_yellow = new BlockRoadRotatable("road_excl_zone_split_out_l_yellow")
				.setCreativeTab(Roads.YELLOW_ROADS_TAB);

		// road signs
		road_sign_prohib_1 = new BlockRoadSign("road_sign_prohib_1");
		road_sign_prohib_2 = new BlockRoadSign("road_sign_prohib_2");
		road_sign_prohib_3a = new BlockRoadSign("road_sign_prohib_3a");
		road_sign_prohib_3b = new BlockRoadSign("road_sign_prohib_3b");
		road_sign_prohib_3c = new BlockRoadSign("road_sign_prohib_3c");
		road_sign_prohib_4a = new BlockRoadSign("road_sign_prohib_4a");
		road_sign_prohib_4b = new BlockRoadSign("road_sign_prohib_4b");
		road_sign_prohib_4c = new BlockRoadSign("road_sign_prohib_4c");
		road_sign_prohib_4d = new BlockRoadSign("road_sign_prohib_4d");
		road_sign_prohib_5 = new BlockRoadSign("road_sign_prohib_5");
		road_sign_prohib_6a = new BlockRoadSign("road_sign_prohib_6a");
		road_sign_prohib_6b = new BlockRoadSign("road_sign_prohib_6b");
		road_sign_prohib_6c = new BlockRoadSign("road_sign_prohib_6c");
		road_sign_prohib_6d = new BlockRoadSign("road_sign_prohib_6d");
		road_sign_prohib_7a = new BlockRoadSign("road_sign_prohib_7a");
		road_sign_prohib_7al = new BlockRoadSign("road_sign_prohib_7al");
		road_sign_prohib_7aw = new BlockRoadSign("road_sign_prohib_7aw");
		road_sign_prohib_7b = new BlockRoadSign("road_sign_prohib_7b");
		road_sign_prohib_7c = new BlockRoadSign("road_sign_prohib_7c");
		road_sign_prohib_7e = new BlockRoadSign("road_sign_prohib_7e");
		road_sign_prohib_7f = new BlockRoadSign("road_sign_prohib_7f");
		road_sign_prohib_8a = new BlockRoadSign("road_sign_prohib_8a");
		road_sign_prohib_8b = new BlockRoadSign("road_sign_prohib_8b");
		road_sign_prohib_8c = new BlockRoadSign("road_sign_prohib_8c");
		road_sign_prohib_9a = new BlockRoadSign("road_sign_prohib_9a");
		road_sign_prohib_9b = new BlockRoadSign("road_sign_prohib_9b");
		road_sign_prohib_9c = new BlockRoadSign("road_sign_prohib_9c");
		road_sign_prohib_9d = new BlockRoadSign("road_sign_prohib_9d");
		road_sign_prohib_10a20 = new BlockRoadSign("road_sign_prohib_10a20");
		road_sign_prohib_10a30 = new BlockRoadSign("road_sign_prohib_10a30");
		road_sign_prohib_10a40 = new BlockRoadSign("road_sign_prohib_10a40");
		road_sign_prohib_10a50 = new BlockRoadSign("road_sign_prohib_10a50");
		road_sign_prohib_10a60 = new BlockRoadSign("road_sign_prohib_10a60");
		road_sign_prohib_10a70 = new BlockRoadSign("road_sign_prohib_10a70");
		road_sign_prohib_10a80 = new BlockRoadSign("road_sign_prohib_10a80");
		road_sign_prohib_10a100 = new BlockRoadSign("road_sign_prohib_10a100");
		road_sign_prohib_10b20 = new BlockRoadSign("road_sign_prohib_10b20");
		road_sign_prohib_10b30 = new BlockRoadSign("road_sign_prohib_10b30");
		road_sign_prohib_10b40 = new BlockRoadSign("road_sign_prohib_10b40");
		road_sign_prohib_10b50 = new BlockRoadSign("road_sign_prohib_10b50");
		road_sign_prohib_10b60 = new BlockRoadSign("road_sign_prohib_10b60");
		road_sign_prohib_10b70 = new BlockRoadSign("road_sign_prohib_10b70");
		road_sign_prohib_10b80 = new BlockRoadSign("road_sign_prohib_10b80");
		road_sign_prohib_10b100 = new BlockRoadSign("road_sign_prohib_10b100");
		road_sign_prohib_11 = new BlockRoadSign("road_sign_prohib_11");
		road_sign_prohib_11a = new BlockRoadSign("road_sign_prohib_11a");
		road_sign_prohib_11b = new BlockRoadSign("road_sign_prohib_11b");
		road_sign_prohib_12 = new BlockRoadSign("road_sign_prohib_12");
		road_sign_prohib_13a = new BlockRoadSign("road_sign_prohib_13a");
		road_sign_prohib_13b = new BlockRoadSign("road_sign_prohib_13b");
		road_sign_prohib_13d = new BlockRoadSign("road_sign_prohib_13d");
		road_sign_prohib_13e = new BlockRoadSign("road_sign_prohib_13e");
		road_sign_prohib_14 = new BlockRoadSign("road_sign_prohib_14");

		road_sign_warn_1 = new BlockRoadSign("road_sign_warn_1");
		road_sign_warn_2a = new BlockRoadSign("road_sign_warn_2a");
		road_sign_warn_2b = new BlockRoadSign("road_sign_warn_2b");
		road_sign_warn_2c = new BlockRoadSign("road_sign_warn_2c");
		road_sign_warn_2d = new BlockRoadSign("road_sign_warn_2d");
		road_sign_warn_3a = new BlockRoadSign("road_sign_warn_3a");
		road_sign_warn_3b = new BlockRoadSign("road_sign_warn_3b");
		road_sign_warn_4 = new BlockRoadSign("road_sign_warn_4");
		road_sign_warn_6a = new BlockRoadSign("road_sign_warn_6a");
		road_sign_warn_6b = new BlockRoadSign("road_sign_warn_6b");
		road_sign_warn_6c_i_r = new BlockRoadSign("road_sign_warn_6c_i_r");
		road_sign_warn_6c_i_l = new BlockRoadSign("road_sign_warn_6c_i_l");
		road_sign_warn_6c_ii_r = new BlockRoadSign("road_sign_warn_6c_ii_r");
		road_sign_warn_6c_ii_l = new BlockRoadSign("road_sign_warn_6c_ii_l");
		road_sign_warn_6c_iii_r = new BlockRoadSign("road_sign_warn_6c_iii_r");
		road_sign_warn_6c_iii_l = new BlockRoadSign("road_sign_warn_6c_iii_l");
		road_sign_warn_6d_a = new BlockRoadSign("road_sign_warn_6d_a");
		road_sign_warn_6d_b = new BlockRoadSign("road_sign_warn_6d_b");
		road_sign_warn_6d_c = new BlockRoadSign("road_sign_warn_6d_c");
		road_sign_warn_6d_d = new BlockRoadSign("road_sign_warn_6d_d");
		road_sign_warn_7 = new BlockRoadSign("road_sign_warn_7");
		road_sign_warn_7a = new BlockRoadSign("road_sign_warn_7a");
		road_sign_warn_8a = new BlockRoadSign("road_sign_warn_8a");
		road_sign_warn_8b = new BlockRoadSign("road_sign_warn_8b");
		road_sign_warn_8c = new BlockRoadSign("road_sign_warn_8c");
		road_sign_warn_9 = new BlockRoadSign("road_sign_warn_9");
		road_sign_warn_10 = new BlockRoadSign("road_sign_warn_10");
		road_sign_warn_10a = new BlockRoadSign("road_sign_warn_10a");
		road_sign_warn_10b = new BlockRoadSign("road_sign_warn_10b");
		road_sign_warn_10c = new BlockRoadSign("road_sign_warn_10c");
		road_sign_warn_11a = new BlockRoadSign("road_sign_warn_11a");
		road_sign_warn_11b = new BlockRoadSign("road_sign_warn_11b");
		road_sign_warn_12 = new BlockRoadSign("road_sign_warn_12");
		road_sign_warn_13 = new BlockRoadSign("road_sign_warn_13");
		road_sign_warn_14 = new BlockRoadSign("road_sign_warn_14");
		road_sign_warn_15 = new BlockRoadSign("road_sign_warn_15");
		road_sign_warn_16 = new BlockRoadSign("road_sign_warn_16");

		road_sign_mandat_1 = new BlockRoadSign("road_sign_mandat_1");
		road_sign_mandat_2 = new BlockRoadSign("road_sign_mandat_2");
		road_sign_mandat_3 = new BlockRoadSign("road_sign_mandat_3");
		road_sign_mandat_4 = new BlockRoadSign("road_sign_mandat_4");
		road_sign_mandat_5 = new BlockRoadSign("road_sign_mandat_5");
		road_sign_mandat_6 = new BlockRoadSign("road_sign_mandat_6");
		road_sign_mandat_7 = new BlockRoadSign("road_sign_mandat_7");
		road_sign_mandat_8 = new BlockRoadSign("road_sign_mandat_8");
		road_sign_mandat_9 = new BlockRoadSign("road_sign_mandat_9");
		road_sign_mandat_10 = new BlockRoadSign("road_sign_mandat_10");
		road_sign_mandat_16 = new BlockRoadSign("road_sign_mandat_16");
		road_sign_mandat_16a = new BlockRoadSign("road_sign_mandat_16a");
		road_sign_mandat_17 = new BlockRoadSign("road_sign_mandat_17");
		road_sign_mandat_17a_a = new BlockRoadSign("road_sign_mandat_17a_a");
		road_sign_mandat_17a_b = new BlockRoadSign("road_sign_mandat_17a_b");
		road_sign_mandat_17a_c = new BlockRoadSign("road_sign_mandat_17a_c");
		road_sign_mandat_17a_d = new BlockRoadSign("road_sign_mandat_17a_d");
		road_sign_mandat_17b = new BlockRoadSign("road_sign_mandat_17b");
		road_sign_mandat_17c = new BlockRoadSign("road_sign_mandat_17c");
		road_sign_mandat_18 = new BlockRoadSign("road_sign_mandat_18");
		road_sign_mandat_22 = new BlockRoadSign("road_sign_mandat_22");
		road_sign_mandat_22a = new BlockRoadSign("road_sign_mandat_22a");

		road_sign_priority_1 = new BlockRoadSign("road_sign_priority_1");
		road_sign_priority_2 = new BlockRoadSign("road_sign_priority_2");
		road_sign_priority_3a = new BlockRoadSign("road_sign_priority_3a");
		road_sign_priority_3b = new BlockRoadSign("road_sign_priority_3b");

		road_sign_info_1a = new BlockRoadSign("road_sign_info_1a");
		road_sign_info_1b = new BlockRoadSign("road_sign_info_1b");
		road_sign_info_2 = new BlockRoadSign("road_sign_info_2");
		road_sign_info_2a = new BlockRoadSign("road_sign_info_2a");
		road_sign_info_2b = new BlockRoadSign("road_sign_info_2b");
		road_sign_info_2c_a = new BlockRoadSign("road_sign_info_2c_a");
		road_sign_info_2c_b = new BlockRoadSign("road_sign_info_2c_b");
		road_sign_info_3 = new BlockRoadSign("road_sign_info_3");
		road_sign_info_3a = new BlockRoadSign("road_sign_info_3a");
		road_sign_info_4 = new BlockRoadSign("road_sign_info_4");
		road_sign_info_5 = new BlockRoadSign("road_sign_info_5");
		road_sign_info_6a = new BlockRoadSign("road_sign_info_6a");
		road_sign_info_7a = new BlockRoadSign("road_sign_info_7a");
		road_sign_info_8a = new BlockRoadSign("road_sign_info_8a");
		road_sign_info_8b = new BlockRoadSign("road_sign_info_8b");
		road_sign_info_8c = new BlockRoadSign("road_sign_info_8c");
		road_sign_info_8d = new BlockRoadSign("road_sign_info_8d");
		road_sign_info_9a = new BlockRoadSign("road_sign_info_9a");
		road_sign_info_9b = new BlockRoadSign("road_sign_info_9b");
		road_sign_info_9c = new BlockRoadSign("road_sign_info_9c");
		road_sign_info_9d = new BlockRoadSign("road_sign_info_9d");
		road_sign_info_9e = new BlockRoadSign("road_sign_info_9e");
		road_sign_info_9f = new BlockRoadSign("road_sign_info_9f");
		road_sign_info_9g = new BlockRoadSign("road_sign_info_9g");
		road_sign_info_10a = new BlockRoadSign("road_sign_info_10a");
		road_sign_info_10b = new BlockRoadSign("road_sign_info_10b");
		road_sign_info_10c = new BlockRoadSign("road_sign_info_10c");
		road_sign_info_10d = new BlockRoadSign("road_sign_info_10d");
		road_sign_info_11 = new BlockRoadSign("road_sign_info_11");
		road_sign_info_16a = new BlockRoadSign("road_sign_info_16a");
		road_sign_info_16b = new BlockRoadSign("road_sign_info_16b");
		road_sign_info_16c = new BlockRoadSign("road_sign_info_16c");
		road_sign_info_16d = new BlockRoadSign("road_sign_info_16d");
		road_sign_info_17a = new BlockRoadTownSign("road_sign_info_17a");
		road_sign_info_17b = new BlockRoadTownSign("road_sign_info_17b");
		road_sign_info_23 = new BlockRoadSign("road_sign_info_23");
		road_sign_info_23b = new BlockRoadSign("road_sign_info_23b");

		road_sign_post = new BlockRoadSignPost();

		// other stuff
		road_sidewalk = new BlockRoadSidewalk();

		road_guardrail = new BlockRoadGuardrail();

		road_lantern = new BlockRoadLantern("road_lantern", false);
		road_lantern_lit = new BlockRoadLantern("road_lantern_lit", true);

		// road_traffic_light = new BlockRoadSign("road_traffic_light");
		road_traffic_light = new BlockRoadSign("road_traffic_light");
		road_traffic_light_manual = new BlockRoadTrafficLight();

		road_traffic_light_red =  new BlockRoadSign("road_traffic_light_red");		
		road_traffic_light_red_dyn = new BlockRoadTrafficLightRed();
		
		road_traffic_light_yellow_fixed = new BlockRoadSign("road_traffic_light_yellow");
		road_traffic_light_yellow_dyn = new BlockRoadTrafficLightYellow();
		road_traffic_light_yellow_blinking = new BlockRoadTrafficLightBlinkingYellow();
		
		road_traffic_light_green =  new BlockRoadSign("road_traffic_light_green");
		road_traffic_light_green_dyn = new BlockRoadTrafficLightGreen();
		

		// road_traffic_light_off = new BlockRoadSign("road_traffic_light_off");

		road_pedestrian_traffic_light = new BlockRoadSign("road_pedestrian_traffic_light");
		road_pedestrian_traffic_light_manual = new BlockRoadPedestrianTrafficLight();
		road_pedestrian_traffic_light_green = new BlockRoadPedestrianTrafficLightGreen();
		road_pedestrian_traffic_light_red = new BlockRoadPedestrianTrafficLightRed();
		road_pedestrian_traffic_light_off = new BlockRoadPedestrianTrafficLightOff();

		sign_printer = new BlockSignPrinter();
	}

	public static void register() {
		registerBlock(asphalt);
		registerBlock(road);
		registerBlock(road_white);
		registerBlock(road_white_half);
		registerBlock(road_white_quarter);
		registerBlock(road_line);
		registerBlock(road_line_simple);
		registerBlock(road_line_merge);
		registerBlock(road_line_double);
		registerBlock(road_line_double_simple);
		registerBlock(road_line_half_double);
		registerBlock(road_line_half_double_simple);
		registerBlock(road_line_diagonal);
		registerBlock(road_arrow_s);
		registerBlock(road_arrow_r);
		registerBlock(road_arrow_l);
		registerBlock(road_arrow_rl);
		registerBlock(road_arrow_sr);
		registerBlock(road_arrow_sl);
		registerBlock(road_arrow_srl);
		registerBlock(road_crosswalk);
		registerBlock(road_excl_zone);
		registerBlock(road_excl_zone_line);
		registerBlock(road_excl_zone_corner_in);
		registerBlock(road_excl_zone_corner_out);
		registerBlock(road_excl_zone_diagonal_in);
		registerBlock(road_excl_zone_diagonal_out);
		registerBlock(road_excl_zone_split_in_l);
		registerBlock(road_excl_zone_split_in_r);
		registerBlock(road_excl_zone_split_out_l);
		registerBlock(road_excl_zone_split_out_r);

		registerBlock(road_yellow);
		registerBlock(road_white_half_yellow);
		registerBlock(road_white_quarter_yellow);
		registerBlock(road_line_yellow);
		registerBlock(road_line_simple_yellow);
		registerBlock(road_line_merge_yellow);
		registerBlock(road_line_double_yellow);
		registerBlock(road_line_double_simple_yellow);
		registerBlock(road_line_half_double_yellow);
		registerBlock(road_line_half_double_simple_yellow);
		registerBlock(road_line_diagonal_yellow);
		registerBlock(road_arrow_s_yellow);
		registerBlock(road_arrow_r_yellow);
		registerBlock(road_arrow_l_yellow);
		registerBlock(road_arrow_rl_yellow);
		registerBlock(road_arrow_sr_yellow);
		registerBlock(road_arrow_sl_yellow);
		registerBlock(road_arrow_srl_yellow);
		registerBlock(road_crosswalk_yellow);
		registerBlock(road_excl_zone_yellow);
		registerBlock(road_excl_zone_line_yellow);
		registerBlock(road_excl_zone_corner_in_yellow);
		registerBlock(road_excl_zone_corner_out_yellow);
		registerBlock(road_excl_zone_diagonal_in_yellow);
		registerBlock(road_excl_zone_diagonal_out_yellow);
		registerBlock(road_excl_zone_split_in_l_yellow);
		registerBlock(road_excl_zone_split_in_r_yellow);
		registerBlock(road_excl_zone_split_out_l_yellow);
		registerBlock(road_excl_zone_split_out_r_yellow);

		registerBlock(road_sidewalk);
		registerBlock(road_guardrail);
		registerBlock(road_sign_post);
		registerBlock(road_sign_prohib_1);
		registerBlock(road_sign_prohib_2);
		registerBlock(road_sign_prohib_3a);
		registerBlock(road_sign_prohib_3b);
		registerBlock(road_sign_prohib_3c);
		registerBlock(road_sign_prohib_4a);
		registerBlock(road_sign_prohib_4b);
		registerBlock(road_sign_prohib_4c);
		registerBlock(road_sign_prohib_4d);
		registerBlock(road_sign_prohib_5);
		registerBlock(road_sign_prohib_6a);
		registerBlock(road_sign_prohib_6b);
		registerBlock(road_sign_prohib_6c);
		registerBlock(road_sign_prohib_6d);
		registerBlock(road_sign_prohib_7a);
		registerBlock(road_sign_prohib_7al);
		registerBlock(road_sign_prohib_7aw);
		registerBlock(road_sign_prohib_7b);
		registerBlock(road_sign_prohib_7c);
		registerBlock(road_sign_prohib_7e);
		registerBlock(road_sign_prohib_7f);
		registerBlock(road_sign_prohib_8a);
		registerBlock(road_sign_prohib_8b);
		registerBlock(road_sign_prohib_8c);
		registerBlock(road_sign_prohib_9a);
		registerBlock(road_sign_prohib_9b);
		registerBlock(road_sign_prohib_9c);
		registerBlock(road_sign_prohib_9d);
		registerBlock(road_sign_prohib_10a20);
		registerBlock(road_sign_prohib_10a30);
		registerBlock(road_sign_prohib_10a40);
		registerBlock(road_sign_prohib_10a50);
		registerBlock(road_sign_prohib_10a60);
		registerBlock(road_sign_prohib_10a70);
		registerBlock(road_sign_prohib_10a80);
		registerBlock(road_sign_prohib_10a100);
		registerBlock(road_sign_prohib_10b20);
		registerBlock(road_sign_prohib_10b30);
		registerBlock(road_sign_prohib_10b40);
		registerBlock(road_sign_prohib_10b50);
		registerBlock(road_sign_prohib_10b60);
		registerBlock(road_sign_prohib_10b70);
		registerBlock(road_sign_prohib_10b80);
		registerBlock(road_sign_prohib_10b100);
		registerBlock(road_sign_prohib_11);
		registerBlock(road_sign_prohib_11a);
		registerBlock(road_sign_prohib_11b);
		registerBlock(road_sign_prohib_12);
		registerBlock(road_sign_prohib_13a);
		registerBlock(road_sign_prohib_13b);
		registerBlock(road_sign_prohib_13d);
		registerBlock(road_sign_prohib_13e);
		registerBlock(road_sign_prohib_14);
		registerBlock(road_sign_warn_1);
		registerBlock(road_sign_warn_2a);
		registerBlock(road_sign_warn_2b);
		registerBlock(road_sign_warn_2c);
		registerBlock(road_sign_warn_2d);
		registerBlock(road_sign_warn_3a);
		registerBlock(road_sign_warn_3b);
		registerBlock(road_sign_warn_4);
		registerBlock(road_sign_warn_6a);
		registerBlock(road_sign_warn_6b);
		registerBlock(road_sign_warn_6c_i_r);
		registerBlock(road_sign_warn_6c_i_l);
		registerBlock(road_sign_warn_6c_ii_r);
		registerBlock(road_sign_warn_6c_ii_l);
		registerBlock(road_sign_warn_6c_iii_r);
		registerBlock(road_sign_warn_6c_iii_l);
		registerBlock(road_sign_warn_6d_a);
		registerBlock(road_sign_warn_6d_b);
		registerBlock(road_sign_warn_6d_c);
		registerBlock(road_sign_warn_6d_d);
		registerBlock(road_sign_warn_7);
		registerBlock(road_sign_warn_7a);
		registerBlock(road_sign_warn_8a);
		registerBlock(road_sign_warn_8b);
		registerBlock(road_sign_warn_8c);
		registerBlock(road_sign_warn_9);
		registerBlock(road_sign_warn_10);
		registerBlock(road_sign_warn_10a);
		registerBlock(road_sign_warn_10b);
		registerBlock(road_sign_warn_10c);
		registerBlock(road_sign_warn_11a);
		registerBlock(road_sign_warn_11b);
		registerBlock(road_sign_warn_12);
		registerBlock(road_sign_warn_13);
		registerBlock(road_sign_warn_14);
		registerBlock(road_sign_warn_15);
		registerBlock(road_sign_warn_16);
		registerBlock(road_sign_mandat_1);
		registerBlock(road_sign_mandat_2);
		registerBlock(road_sign_mandat_3);
		registerBlock(road_sign_mandat_4);
		registerBlock(road_sign_mandat_5);
		registerBlock(road_sign_mandat_6);
		registerBlock(road_sign_mandat_7);
		registerBlock(road_sign_mandat_8);
		registerBlock(road_sign_mandat_9);
		registerBlock(road_sign_mandat_10);
		registerBlock(road_sign_mandat_16);
		registerBlock(road_sign_mandat_16a);
		registerBlock(road_sign_mandat_17);
		registerBlock(road_sign_mandat_17a_a);
		registerBlock(road_sign_mandat_17a_b);
		registerBlock(road_sign_mandat_17a_c);
		registerBlock(road_sign_mandat_17a_d);
		registerBlock(road_sign_mandat_17b);
		registerBlock(road_sign_mandat_17c);
		registerBlock(road_sign_mandat_18);
		registerBlock(road_sign_mandat_22);
		registerBlock(road_sign_mandat_22a);
		registerBlock(road_sign_priority_1);
		registerBlock(road_sign_priority_2);
		registerBlock(road_sign_priority_3a);
		registerBlock(road_sign_priority_3b);
		registerBlock(road_sign_info_1a);
		registerBlock(road_sign_info_1b);
		registerBlock(road_sign_info_2);
		registerBlock(road_sign_info_2a);
		registerBlock(road_sign_info_2b);
		registerBlock(road_sign_info_2c_a);
		registerBlock(road_sign_info_2c_b);
		registerBlock(road_sign_info_3);
		registerBlock(road_sign_info_3a);
		registerBlock(road_sign_info_4);
		registerBlock(road_sign_info_5);
		registerBlock(road_sign_info_6a);
		registerBlock(road_sign_info_7a);
		registerBlock(road_sign_info_8a);
		registerBlock(road_sign_info_8b);
		registerBlock(road_sign_info_8c);
		registerBlock(road_sign_info_8d);
		registerBlock(road_sign_info_9a);
		registerBlock(road_sign_info_9b);
		registerBlock(road_sign_info_9c);
		registerBlock(road_sign_info_9d);
		registerBlock(road_sign_info_9e);
		registerBlock(road_sign_info_9f);
		registerBlock(road_sign_info_9g);
		registerBlock(road_sign_info_10a);
		registerBlock(road_sign_info_10b);
		registerBlock(road_sign_info_10c);
		registerBlock(road_sign_info_10d);
		registerBlock(road_sign_info_11);
		registerBlock(road_sign_info_16a);
		registerBlock(road_sign_info_16b);
		registerBlock(road_sign_info_16c);
		registerBlock(road_sign_info_16d);
		registerBlock(road_sign_info_17a);
		registerBlock(road_sign_info_17b);
		registerBlock(road_sign_info_23);
		registerBlock(road_sign_info_23b);

		registerBlock(road_lantern);
		registerBlock(road_lantern_lit);

		registerBlock(road_traffic_light);
		registerBlock(road_traffic_light_manual);

		registerBlock(road_traffic_light_red);
		registerBlock(road_traffic_light_red_dyn);
		
		registerBlock(road_traffic_light_yellow_dyn);
		registerBlock(road_traffic_light_yellow_blinking);
		registerBlock(road_traffic_light_yellow_fixed);
		
		registerBlock(road_traffic_light_green);
		registerBlock(road_traffic_light_green_dyn);

		registerBlock(road_pedestrian_traffic_light);
		registerBlock(road_pedestrian_traffic_light_manual);
		registerBlock(road_pedestrian_traffic_light_green);
		registerBlock(road_pedestrian_traffic_light_red);
		registerBlock(road_pedestrian_traffic_light_off);

		registerBlock(sign_printer);
	}

	private static void registerBlock(Block block) {
		registerBlock(block, new ItemBlock(block));
	}

	private static void registerBlock(Block block, ItemBlock item) {
		RegistrationHandler.BLOCKS.add(block);
		item.setRegistryName(block.getRegistryName());
		RoadItems.RegistrationHandler.ITEMS.add(item);
	}

	public static void registerModels() {
		registerModel(asphalt);
		registerModel(road);
		registerModel(road_white);
		registerModel(road_white_half);
		registerModel(road_white_quarter);
		registerModel(road_line);
		registerModel(road_line_simple);
		registerModel(road_line_merge);
		registerModel(road_line_double);
		registerModel(road_line_double_simple);
		registerModel(road_line_half_double);
		registerModel(road_line_half_double_simple);
		registerModel(road_line_diagonal);
		registerModel(road_arrow_s);
		registerModel(road_arrow_r);
		registerModel(road_arrow_l);
		registerModel(road_arrow_rl);
		registerModel(road_arrow_sr);
		registerModel(road_arrow_sl);
		registerModel(road_arrow_srl);
		registerModel(road_crosswalk);
		registerModel(road_excl_zone);
		registerModel(road_excl_zone_line);
		registerModel(road_excl_zone_corner_in);
		registerModel(road_excl_zone_corner_out);
		registerModel(road_excl_zone_diagonal_in);
		registerModel(road_excl_zone_diagonal_out);
		registerModel(road_excl_zone_split_in_l);
		registerModel(road_excl_zone_split_in_r);
		registerModel(road_excl_zone_split_out_l);
		registerModel(road_excl_zone_split_out_r);

		registerModel(road_yellow);
		registerModel(road_white_half_yellow);
		registerModel(road_white_quarter_yellow);
		registerModel(road_line_yellow);
		registerModel(road_line_simple_yellow);
		registerModel(road_line_merge_yellow);
		registerModel(road_line_double_yellow);
		registerModel(road_line_double_simple_yellow);
		registerModel(road_line_half_double_yellow);
		registerModel(road_line_half_double_simple_yellow);
		registerModel(road_line_diagonal_yellow);
		registerModel(road_arrow_s_yellow);
		registerModel(road_arrow_r_yellow);
		registerModel(road_arrow_l_yellow);
		registerModel(road_arrow_rl_yellow);
		registerModel(road_arrow_sr_yellow);
		registerModel(road_arrow_sl_yellow);
		registerModel(road_arrow_srl_yellow);
		registerModel(road_crosswalk_yellow);
		registerModel(road_excl_zone_yellow);
		registerModel(road_excl_zone_line_yellow);
		registerModel(road_excl_zone_corner_in_yellow);
		registerModel(road_excl_zone_corner_out_yellow);
		registerModel(road_excl_zone_diagonal_in_yellow);
		registerModel(road_excl_zone_diagonal_out_yellow);
		registerModel(road_excl_zone_split_in_l_yellow);
		registerModel(road_excl_zone_split_in_r_yellow);
		registerModel(road_excl_zone_split_out_l_yellow);
		registerModel(road_excl_zone_split_out_r_yellow);

		registerModel(road_sidewalk);
		registerModel(road_guardrail);
		registerModel(road_sign_post);
		registerModel(road_sign_prohib_1);
		registerModel(road_sign_prohib_2);
		registerModel(road_sign_prohib_3a);
		registerModel(road_sign_prohib_3b);
		registerModel(road_sign_prohib_3c);
		registerModel(road_sign_prohib_4a);
		registerModel(road_sign_prohib_4b);
		registerModel(road_sign_prohib_4c);
		registerModel(road_sign_prohib_4d);
		registerModel(road_sign_prohib_5);
		registerModel(road_sign_prohib_6a);
		registerModel(road_sign_prohib_6b);
		registerModel(road_sign_prohib_6c);
		registerModel(road_sign_prohib_6d);
		registerModel(road_sign_prohib_7a);
		registerModel(road_sign_prohib_7al);
		registerModel(road_sign_prohib_7aw);
		registerModel(road_sign_prohib_7b);
		registerModel(road_sign_prohib_7c);
		registerModel(road_sign_prohib_7e);
		registerModel(road_sign_prohib_7f);
		registerModel(road_sign_prohib_8a);
		registerModel(road_sign_prohib_8b);
		registerModel(road_sign_prohib_8c);
		registerModel(road_sign_prohib_9a);
		registerModel(road_sign_prohib_9b);
		registerModel(road_sign_prohib_9c);
		registerModel(road_sign_prohib_9d);
		registerModel(road_sign_prohib_10a20);
		registerModel(road_sign_prohib_10a30);
		registerModel(road_sign_prohib_10a40);
		registerModel(road_sign_prohib_10a50);
		registerModel(road_sign_prohib_10a60);
		registerModel(road_sign_prohib_10a70);
		registerModel(road_sign_prohib_10a80);
		registerModel(road_sign_prohib_10a100);
		registerModel(road_sign_prohib_10b20);
		registerModel(road_sign_prohib_10b30);
		registerModel(road_sign_prohib_10b40);
		registerModel(road_sign_prohib_10b50);
		registerModel(road_sign_prohib_10b60);
		registerModel(road_sign_prohib_10b70);
		registerModel(road_sign_prohib_10b80);
		registerModel(road_sign_prohib_10b100);
		registerModel(road_sign_prohib_11);
		registerModel(road_sign_prohib_11a);
		registerModel(road_sign_prohib_11b);
		registerModel(road_sign_prohib_12);
		registerModel(road_sign_prohib_13a);
		registerModel(road_sign_prohib_13b);
		registerModel(road_sign_prohib_13d);
		registerModel(road_sign_prohib_13e);
		registerModel(road_sign_prohib_14);
		registerModel(road_sign_warn_1);
		registerModel(road_sign_warn_2a);
		registerModel(road_sign_warn_2b);
		registerModel(road_sign_warn_2c);
		registerModel(road_sign_warn_2d);
		registerModel(road_sign_warn_3a);
		registerModel(road_sign_warn_3b);
		registerModel(road_sign_warn_4);
		registerModel(road_sign_warn_6a);
		registerModel(road_sign_warn_6b);
		registerModel(road_sign_warn_6c_i_r);
		registerModel(road_sign_warn_6c_i_l);
		registerModel(road_sign_warn_6c_ii_r);
		registerModel(road_sign_warn_6c_ii_l);
		registerModel(road_sign_warn_6c_iii_r);
		registerModel(road_sign_warn_6c_iii_l);
		registerModel(road_sign_warn_6d_a);
		registerModel(road_sign_warn_6d_b);
		registerModel(road_sign_warn_6d_c);
		registerModel(road_sign_warn_6d_d);
		registerModel(road_sign_warn_7);
		registerModel(road_sign_warn_7a);
		registerModel(road_sign_warn_8a);
		registerModel(road_sign_warn_8b);
		registerModel(road_sign_warn_8c);
		registerModel(road_sign_warn_9);
		registerModel(road_sign_warn_10);
		registerModel(road_sign_warn_10a);
		registerModel(road_sign_warn_10b);
		registerModel(road_sign_warn_10c);
		registerModel(road_sign_warn_11a);
		registerModel(road_sign_warn_11b);
		registerModel(road_sign_warn_12);
		registerModel(road_sign_warn_13);
		registerModel(road_sign_warn_14);
		registerModel(road_sign_warn_15);
		registerModel(road_sign_warn_16);
		registerModel(road_sign_mandat_1);
		registerModel(road_sign_mandat_2);
		registerModel(road_sign_mandat_3);
		registerModel(road_sign_mandat_4);
		registerModel(road_sign_mandat_5);
		registerModel(road_sign_mandat_6);
		registerModel(road_sign_mandat_7);
		registerModel(road_sign_mandat_8);
		registerModel(road_sign_mandat_9);
		registerModel(road_sign_mandat_10);
		registerModel(road_sign_mandat_16);
		registerModel(road_sign_mandat_16a);
		registerModel(road_sign_mandat_17);
		registerModel(road_sign_mandat_17a_a);
		registerModel(road_sign_mandat_17a_b);
		registerModel(road_sign_mandat_17a_c);
		registerModel(road_sign_mandat_17a_d);
		registerModel(road_sign_mandat_17b);
		registerModel(road_sign_mandat_17c);
		registerModel(road_sign_mandat_18);
		registerModel(road_sign_mandat_22);
		registerModel(road_sign_mandat_22a);
		registerModel(road_sign_priority_1);
		registerModel(road_sign_priority_2);
		registerModel(road_sign_priority_3a);
		registerModel(road_sign_priority_3b);
		registerModel(road_sign_info_1a);
		registerModel(road_sign_info_1b);
		registerModel(road_sign_info_2);
		registerModel(road_sign_info_2a);
		registerModel(road_sign_info_2b);
		registerModel(road_sign_info_2c_a);
		registerModel(road_sign_info_2c_b);
		registerModel(road_sign_info_3);
		registerModel(road_sign_info_3a);
		registerModel(road_sign_info_4);
		registerModel(road_sign_info_5);
		registerModel(road_sign_info_6a);
		registerModel(road_sign_info_7a);
		registerModel(road_sign_info_8a);
		registerModel(road_sign_info_8b);
		registerModel(road_sign_info_8c);
		registerModel(road_sign_info_8d);
		registerModel(road_sign_info_9a);
		registerModel(road_sign_info_9b);
		registerModel(road_sign_info_9c);
		registerModel(road_sign_info_9d);
		registerModel(road_sign_info_9e);
		registerModel(road_sign_info_9f);
		registerModel(road_sign_info_9g);
		registerModel(road_sign_info_10a);
		registerModel(road_sign_info_10b);
		registerModel(road_sign_info_10c);
		registerModel(road_sign_info_10d);
		registerModel(road_sign_info_11);
		registerModel(road_sign_info_16a);
		registerModel(road_sign_info_16b);
		registerModel(road_sign_info_16c);
		registerModel(road_sign_info_16d);
		registerModel(road_sign_info_17a);
		registerModel(road_sign_info_17b);
		registerModel(road_sign_info_23);
		registerModel(road_sign_info_23b);

		registerModel(road_lantern);
		registerModel(road_lantern_lit);

		registerModel(road_traffic_light);
		registerModel(road_traffic_light_manual);

		registerModel(road_traffic_light_red);
		registerModel(road_traffic_light_red_dyn);
		
		registerModel(road_traffic_light_yellow_fixed);
		registerModel(road_traffic_light_yellow_dyn);
		registerModel(road_traffic_light_yellow_blinking);
		
		registerModel(road_traffic_light_green);
		registerModel(road_traffic_light_green_dyn);

		// registerModel(road_traffic_light_off);
		registerModel(road_pedestrian_traffic_light);
		registerModel(road_pedestrian_traffic_light_manual);
		registerModel(road_pedestrian_traffic_light_green);
		registerModel(road_pedestrian_traffic_light_red);
		registerModel(road_pedestrian_traffic_light_off);
		registerModel(sign_printer);
	}

	@SideOnly(Side.CLIENT)
	private static void registerModel(Block block) {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0,
				new ModelResourceLocation(block.getRegistryName(), "inventory"));
	}

	@Mod.EventBusSubscriber()
	public static class RegistrationHandler {
		public static final List<Block> BLOCKS = new LinkedList<>();

		@SubscribeEvent
		public static void registerItems(final RegistryEvent.Register<Block> event) {
			RoadBlocks.init();
			RoadBlocks.register();
			BLOCKS.stream().forEach(block -> event.getRegistry().register(block));
		}
	}
}
