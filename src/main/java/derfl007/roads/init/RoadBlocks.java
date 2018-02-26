package derfl007.roads.init;

import derfl007.roads.common.blocks.*;
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

import java.util.LinkedList;
import java.util.List;

public class RoadBlocks {

    public static Block
            road, //Road Block
            road_white, //White block
            road_white_half, //Half white block
            road_white_quarter, //Quarter white block
            road_slope, //Normal slope
            road_line_slope, //Slope with line
            road_line, //Single line
            road_line_merge, //Merge single and double lines
            road_line_double, //Double Line
            road_line_half_double, //Half double line
            road_line_diagonal, //Diagonal Line
            road_arrow_s, //straight arrow
            road_arrow_r, //right arrow
            road_arrow_l, //left arrow
            road_arrow_rl, //Right- and left arrow
            road_arrow_sr, //Straight- and right arrow
            road_arrow_sl, //Straight- and left arrow
            road_arrow_srl, //Straight-, right- and left arrow
            road_crosswalk, //Crosswalk
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
            road_sidewalk, //Sidewalk
            road_guardrail, //Guardrail
            road_sign_post, //Sign Post
            road_sign_prohib_1, // closed in both directions for all vehicles
            road_sign_prohib_2, // no entry
            road_sign_prohib_3a, // no left turn
            road_sign_prohib_3b, // no right turn
            road_sign_prohib_3c, // no u-turn
            road_sign_prohib_4a, // no overtaking
            road_sign_prohib_4b, // end of overtaking restriction
            road_sign_prohib_10a20, // speed sign 20
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
            road_sign_warn_1, // uneven road
            road_sign_warn_2a, // dangerous right curve
            road_sign_warn_2b, // dangerous left curve
            road_sign_warn_2c, // dangerous curves, first right
            road_sign_warn_2d, // dangerous curves, first left
            road_sign_warn_3a, // junction
            road_sign_warn_3b, // junction with roundabout
            road_sign_warn_4, // crossroad with non-priority road
            road_sign_warn_8a, // road narrows on both sides
            road_sign_warn_8b, // road narrows from left
            road_sign_warn_8c, // road narrows from right
            road_sign_warn_9, // road works
            road_sign_warn_10a, // slippery road
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
            road_sign_priority_1, // give way
            road_sign_priority_2, // stop
            road_sign_priority_3a, // priority road
            road_sign_priority_3b, // end of priority road
            road_sign_info_1a, // parking lot or parking lane
            road_sign_info_1b, // parking lot
            road_sign_info_8a, // expressway
            road_sign_info_8b, // end of expressway
            road_sign_info_8c, // motor road
            road_sign_info_8d, // end of motor road
            road_sign_info_9a, // pedestrian zone
            road_sign_info_9b, // end of pedestrian zone
            road_sign_info_10a, // one-way left
            road_sign_info_10b, // one-way right
            road_sign_info_11, // dead end
            road_sign_info_16a, // detour right
            road_sign_info_16b, // detour left
            road_sign_info_23, // two lanes merge into one
            road_town_sign,
            road_lantern, //Road Lantern unlit
            road_lantern_lit, //Road Lantern lit
            road_traffic_light, //Traffic Light (Animated)
            road_traffic_light_red, //Traffic Light (Red)
            road_traffic_light_green, //Traffic Light (Green)
            road_traffic_light_yellow, //Traffic Light (Yellow blinking)
            road_pedestrian_traffic_light; //Pedestrian Traffic Light

    public static void init() {
        road = new BlockRoad("road");
        road_white = new BlockRoad("road_white");
        road_white_half = new BlockRoadRotatable("road_white_half");
        road_white_quarter = new BlockRoadRotatable("road_white_quarter");
        road_slope = new BlockRoadSlope("road_slope");
        road_line_slope = new BlockRoadRotatable("road_line_slope");
        road_line = new BlockRoadLine();
        road_line_merge = new BlockRoadRotatable("road_line_merge");
        road_line_double = new BlockRoadRotatable("road_line_double");
        road_line_half_double = new BlockRoadRotatable("road_line_half_double");
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
        road_sidewalk = new BlockRoadSidewalk();
        road_guardrail = new BlockRoadGuardrail();
        road_sign_post = new BlockRoadSignPost();
        road_sign_prohib_1 = new BlockRoadSign("road_sign_prohib_1");
        road_sign_prohib_2 = new BlockRoadSign("road_sign_prohib_2");
        road_sign_prohib_3a = new BlockRoadSign("road_sign_prohib_3a");
        road_sign_prohib_3b = new BlockRoadSign("road_sign_prohib_3b");
        road_sign_prohib_3c = new BlockRoadSign("road_sign_prohib_3c");
        road_sign_prohib_4a = new BlockRoadSign("road_sign_prohib_4a");
        road_sign_prohib_4b = new BlockRoadSign("road_sign_prohib_4b");
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
        road_sign_warn_1 = new BlockRoadSign("road_sign_warn_1");
        road_sign_warn_2a = new BlockRoadSign("road_sign_warn_2a");
        road_sign_warn_2b = new BlockRoadSign("road_sign_warn_2b");
        road_sign_warn_2c = new BlockRoadSign("road_sign_warn_2c");
        road_sign_warn_2d = new BlockRoadSign("road_sign_warn_2d");
        road_sign_warn_3a = new BlockRoadSign("road_sign_warn_3a");
        road_sign_warn_3b = new BlockRoadSign("road_sign_warn_3b");
        road_sign_warn_4 = new BlockRoadSign("road_sign_warn_4");
        road_sign_warn_8a = new BlockRoadSign("road_sign_warn_8a");
        road_sign_warn_8b = new BlockRoadSign("road_sign_warn_8b");
        road_sign_warn_8c = new BlockRoadSign("road_sign_warn_8c");
        road_sign_warn_9 = new BlockRoadSign("road_sign_warn_9");
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
        road_sign_priority_1 = new BlockRoadSign("road_sign_priority_1");
        road_sign_priority_2 = new BlockRoadSign("road_sign_priority_2");
        road_sign_priority_3a = new BlockRoadSign("road_sign_priority_3a");
        road_sign_priority_3b = new BlockRoadSign("road_sign_priority_3b");
        road_sign_info_1a = new BlockRoadSign("road_sign_info_1a");
        road_sign_info_1b = new BlockRoadSign("road_sign_info_1b");
        road_sign_info_8a = new BlockRoadSign("road_sign_info_8a");
        road_sign_info_8b = new BlockRoadSign("road_sign_info_8b");
        road_sign_info_8c = new BlockRoadSign("road_sign_info_8c");
        road_sign_info_8d = new BlockRoadSign("road_sign_info_8d");
        road_sign_info_9a = new BlockRoadSign("road_sign_info_9a");
        road_sign_info_9b = new BlockRoadSign("road_sign_info_9b");
        road_sign_info_10a = new BlockRoadSign("road_sign_info_10a");
        road_sign_info_10b = new BlockRoadSign("road_sign_info_10b");
        road_sign_info_11 = new BlockRoadSign("road_sign_info_11");
        road_sign_info_16a = new BlockRoadSign("road_sign_info_16a");
        road_sign_info_16b = new BlockRoadSign("road_sign_info_16b");
        road_sign_info_23 = new BlockRoadSign("road_sign_info_23");
        road_town_sign = new BlockRoadTownSign("road_town_sign");
        road_lantern = new BlockRoadLantern("road_lantern", false);
        road_lantern_lit = new BlockRoadLantern("road_lantern_lit", true);
        road_traffic_light = new BlockRoadSign("road_traffic_light");
        road_traffic_light_red = new BlockRoadSign("road_traffic_light_red");
        road_traffic_light_yellow = new BlockRoadSign("road_traffic_light_yellow");
        road_traffic_light_green = new BlockRoadSign("road_traffic_light_green");
        road_pedestrian_traffic_light = new BlockRoadSign("road_pedestrian_traffic_light");
    }

    public static void register()
    {
        registerBlock(road);
        registerBlock(road_white);
        registerBlock(road_white_half);
        registerBlock(road_white_quarter);
        registerBlock(road_slope);
        registerBlock(road_line_slope);
        registerBlock(road_line);
        registerBlock(road_line_merge);
        registerBlock(road_line_double);
        registerBlock(road_line_half_double);
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
        registerBlock(road_sign_warn_1);
        registerBlock(road_sign_warn_2a);
        registerBlock(road_sign_warn_2b);
        registerBlock(road_sign_warn_2c);
        registerBlock(road_sign_warn_2d);
        registerBlock(road_sign_warn_3a);
        registerBlock(road_sign_warn_3b);
        registerBlock(road_sign_warn_4);
        registerBlock(road_sign_warn_8a);
        registerBlock(road_sign_warn_8b);
        registerBlock(road_sign_warn_8c);
        registerBlock(road_sign_warn_9);
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
        registerBlock(road_sign_priority_1);
        registerBlock(road_sign_priority_2);
        registerBlock(road_sign_priority_3a);
        registerBlock(road_sign_priority_3b);
        registerBlock(road_sign_info_1a);
        registerBlock(road_sign_info_1b);
        registerBlock(road_sign_info_8a);
        registerBlock(road_sign_info_8b);
        registerBlock(road_sign_info_8c);
        registerBlock(road_sign_info_8d);
        registerBlock(road_sign_info_9a);
        registerBlock(road_sign_info_9b);
        registerBlock(road_sign_info_10a);
        registerBlock(road_sign_info_10b);
        registerBlock(road_sign_info_11);
        registerBlock(road_sign_info_16a);
        registerBlock(road_sign_info_16b);
        registerBlock(road_sign_info_23);
        registerBlock(road_town_sign);
        registerBlock(road_lantern);
        registerBlock(road_lantern_lit);
        registerBlock(road_traffic_light);
        registerBlock(road_traffic_light_red);
        registerBlock(road_traffic_light_yellow);
        registerBlock(road_traffic_light_green);
        registerBlock(road_pedestrian_traffic_light);
    }

    private static void registerBlock(Block block)
    {
        registerBlock(block, new ItemBlock(block));
    }

    private static void registerBlock(Block block, ItemBlock item)
    {
        RegistrationHandler.BLOCKS.add(block);
        item.setRegistryName(block.getRegistryName());
        RoadItems.RegistrationHandler.ITEMS.add(item);
    }

    public static void registerModels() {
        registerModel(road);
        registerModel(road_white);
        registerModel(road_white_half);
        registerModel(road_white_quarter);
        registerModel(road_slope);
        registerModel(road_line_slope);
        registerModel(road_line);
        registerModel(road_line_merge);
        registerModel(road_line_double);
        registerModel(road_line_half_double);
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
        registerModel(road_sign_warn_1);
        registerModel(road_sign_warn_2a);
        registerModel(road_sign_warn_2b);
        registerModel(road_sign_warn_2c);
        registerModel(road_sign_warn_2d);
        registerModel(road_sign_warn_3a);
        registerModel(road_sign_warn_3b);
        registerModel(road_sign_warn_4);
        registerModel(road_sign_warn_8a);
        registerModel(road_sign_warn_8b);
        registerModel(road_sign_warn_8c);
        registerModel(road_sign_warn_9);
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
        registerModel(road_sign_priority_1);
        registerModel(road_sign_priority_2);
        registerModel(road_sign_priority_3a);
        registerModel(road_sign_priority_3b);
        registerModel(road_sign_info_1a);
        registerModel(road_sign_info_1b);
        registerModel(road_sign_info_8a);
        registerModel(road_sign_info_8b);
        registerModel(road_sign_info_8c);
        registerModel(road_sign_info_8d);
        registerModel(road_sign_info_9a);
        registerModel(road_sign_info_9b);
        registerModel(road_sign_info_10a);
        registerModel(road_sign_info_10b);
        registerModel(road_sign_info_11);
        registerModel(road_sign_info_16a);
        registerModel(road_sign_info_16b);
        registerModel(road_sign_info_23);
        registerModel(road_town_sign);
        registerModel(road_lantern);
        registerModel(road_lantern_lit);
        registerModel(road_traffic_light);
        registerModel(road_traffic_light_red);
        registerModel(road_traffic_light_yellow);
        registerModel(road_traffic_light_green);
        registerModel(road_pedestrian_traffic_light);
    }

    @SideOnly(Side.CLIENT)
    private static void registerModel(Block block){
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
    }

    @Mod.EventBusSubscriber()
    public static class RegistrationHandler
    {
        public static final List<Block> BLOCKS = new LinkedList<>();

        @SubscribeEvent
        public static void registerItems(final RegistryEvent.Register<Block> event)
        {
            RoadBlocks.init();
            RoadBlocks.register();
            BLOCKS.stream().forEach(block -> event.getRegistry().register(block));
        }
    }
}
