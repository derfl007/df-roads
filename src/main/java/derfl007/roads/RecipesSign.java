package derfl007.roads;

import static derfl007.roads.init.RoadBlocks.*;

import derfl007.roads.init.RoadItems;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

@SuppressWarnings("Duplicates")
public class RecipesSign {

    Item[] baseItem;

    public static Block[] signs_0 = {
            road_sign_warn_1,   // uneven road
            road_sign_warn_2a,  // dangerous right curve
            road_sign_warn_2b,  // dangerous left curve
            road_sign_warn_2c,  // dangerous curves, first right
            road_sign_warn_2d,  // dangerous curves, first left
            road_sign_warn_3a,  // junction
            road_sign_warn_3b,  // junction with roundabout
            road_sign_warn_4,   // crossroad with non-priority road
            road_sign_warn_6a,
            road_sign_warn_6b,
            road_sign_warn_6c_i_r,
            road_sign_warn_6c_i_l,
            road_sign_warn_6c_ii_r,
            road_sign_warn_6c_ii_l,
            road_sign_warn_6c_iii_r,
            road_sign_warn_6c_iii_l,
            road_sign_warn_6d_a,
            road_sign_warn_6d_b,
            road_sign_warn_6d_c,
            road_sign_warn_6d_d,
            road_sign_warn_7,
            road_sign_warn_7a,
            road_sign_warn_8a,  // road narrows on both sides
            road_sign_warn_8b,  // road narrows from left
            road_sign_warn_8c,  // road narrows from right
            road_sign_warn_9,   // road works
            road_sign_warn_10,
            road_sign_warn_10a, // slippery road
            road_sign_warn_10b, // crosswind
            road_sign_warn_10c, // falling rocks
            road_sign_warn_11a, // pedestrian crossing
            road_sign_warn_11b, // cyclist crossing
            road_sign_warn_12,  // children
            road_sign_warn_13,  // animals
            road_sign_warn_14,  // two-way traffic
            road_sign_warn_15,  // traffic signals
            road_sign_warn_16   // other dangers
    };

    public static Block[] signs_1 = {
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
            road_sign_mandat_16,
            road_sign_mandat_16a,
            road_sign_mandat_17,
            road_sign_mandat_17a_a,
            road_sign_mandat_17a_b,
            road_sign_mandat_17a_c,
            road_sign_mandat_17a_d,
            road_sign_mandat_17b,
            road_sign_mandat_17c,
            road_sign_mandat_18,
            road_sign_mandat_22,
            road_sign_mandat_22a
    };

    public static Block[] signs_2 = {
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
            road_sign_info_16c, //detour right (german)
            road_sign_info_16d, //detour left (german)
            road_sign_info_17a, //town sign
            road_sign_info_17b, //end of town sign
            road_sign_info_23, // two lanes merge into one
            road_sign_info_23b, // two lanes merge into one (german)
            road_sign_info_24, 
            road_sign_info_25a, 
            road_sign_info_25b, 
            road_sign_info_26a, 
            road_sign_info_26b, 
            road_sign_info_26c, 
            road_sign_info_27a, 
            road_sign_info_27b, 
            road_sign_info_27c,
            road_sign_info_28a, 
            road_sign_info_28b, 
    };

    public static Block[] signs_3 = {
            road_sign_prohib_1, // closed in both directions for all vehicles
            road_sign_prohib_2, // no entry
            road_sign_prohib_3a, // no left turn
            road_sign_prohib_3b, // no right turn
            road_sign_prohib_3c, // no u-turn
            road_sign_prohib_4a, // no overtaking
            road_sign_prohib_4b, // end of overtaking restriction
            road_sign_prohib_4c, //7
            road_sign_prohib_4d,
            road_sign_prohib_5,
            road_sign_prohib_6a,
            road_sign_prohib_6b,
            road_sign_prohib_6c,
            road_sign_prohib_6d,
            road_sign_prohib_7a,
            road_sign_prohib_7al,
            road_sign_prohib_7aw,
            road_sign_prohib_7b,
            road_sign_prohib_7c,
            road_sign_prohib_7e,
            road_sign_prohib_7f,
            road_sign_prohib_8a,
            road_sign_prohib_8b,
            road_sign_prohib_8c,
            road_sign_prohib_9a,
            road_sign_prohib_9b,
            road_sign_prohib_9c,
            road_sign_prohib_9d, //27
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
            road_sign_prohib_11,
            road_sign_prohib_11a,
            road_sign_prohib_11b,
            road_sign_prohib_12,
            road_sign_prohib_13a,
            road_sign_prohib_13b,
            road_sign_prohib_13d,
            road_sign_prohib_13e,
            road_sign_prohib_14
    };

    public static Block[] signs_4 = {
            road_sign_priority_1, // give way
            road_sign_priority_2, // stop
            road_sign_priority_3a, // priority road
            road_sign_priority_3b // end of priority road
    };

    public void setBaseItem(Item baseItem, int currentSet) {
        this.baseItem[currentSet] = baseItem;
    }

    private static int cmyDamage(String colorId, int cyan, int magenta, int yellow) {
        return (colorId == "C") ? cyan : (colorId == "M") ? magenta : (colorId == "Y") ? yellow : 0;
    }

    public static int getDamage(String colorId, int currentSet, int currentSign) {
        if(currentSet == 0) {
            switch (currentSign) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 32:
                    return cmyDamage(colorId, 1,2, 2); //red, black, white
                case 31:
                    return cmyDamage(colorId, 2,2, 4); //red, yellow, green
            }
        }
        if(currentSet == 1) {
            switch (currentSign) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 12:
                case 13:
                case 14:
                case 17:
                case 19:
                case 20:
                    return cmyDamage(colorId, 1,1, 0); //blue
                case 11:
                case 15:
                case 16:
                case 18:
                case 21:
                    return cmyDamage(colorId, 1, 2, 1); //blue, red
            }
        }
        if(currentSet == 2) {
            switch (currentSign) {
                case 0:
                case 1:
                case 2:
                case 13:
                case 15:
                case 19:
                    return cmyDamage(colorId, 1, 1, 0); //blue, white
                case 7:
                case 12:
                case 14:
                case 16:
                case 20:
                    return cmyDamage(colorId, 1, 2, 1); //blue, red, white
                case 3:
                case 4:
                case 5:
                case 6:
                case 8:
                case 9:
                case 10:
                case 11:
                case 17:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 33:
                    return cmyDamage(colorId, 2, 2, 1); //blue, black, white
                case 18:
                case 21:
                case 22:
                case 34:
                    return cmyDamage(colorId, 2, 3, 2); //blue, red, black, white
                case 35:
                case 36:
                    return cmyDamage(colorId, 1, 1, 1); //black, white
                case 29:
                case 30:
                case 31:
                case 32:
                    return cmyDamage(colorId, 1, 1, 2); //yellow, black
            }
        }
        if(currentSet == 3) {
            switch (currentSign) {
                case 0:
                case 1:
                    return cmyDamage(colorId, 0, 1, 1); //red, white
                case 2:
                case 3:
                case 4:
                case 5:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                case 17:
                case 18:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 45:
                case 52:
                    return cmyDamage(colorId, 1, 2, 2); //red, black, white
                case 6:
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                case 46:
                case 51:
                    return cmyDamage(colorId, 1, 1, 1); //black, white
                case 48:
                case 49:
                case 50:
                    return cmyDamage(colorId, 1, 2, 1); //red, blue, black, white
            }
        }
        if(currentSet == 4) {
            switch (currentSign) {
                case 0:
                case 1:
                    return cmyDamage(colorId, 0, 1, 1); //red, white
                case 2:
                case 3:
                    return cmyDamage(colorId, 1, 2, 1); //black, yellow, white
            }
        }
        return 0;
    }

    public static Item getBaseItem(int currentSet) {
        switch (currentSet) {
            case 0:
                return RoadItems.triangle_sign_template;
            case 1:
                return RoadItems.round_sign_template;
            case 2:
                return RoadItems.square_sign_template;
            case 3:
                return RoadItems.round_sign_template;
            case 4:
                return Items.IRON_INGOT;
            default:
                return RoadItems.triangle_sign_template;
        }
    }

    public static int getBaseItemCount(int currentSet) {
        switch(currentSet) {
            case 0:
            case 1:
            case 2:
            case 3:
                return 1;
            case 4:
                return 4;
            default:
                return 1;
        }
    }

    public Block[] getBlocksFromSet(int currentSet) {
        switch(currentSet) {
            case 0:
                return signs_0;
            case 1:
                return signs_1;
            case 2:
                return signs_2;
            case 3:
                return signs_3;
            case 4:
                return signs_4;
        }
        return signs_0;
    }

    public Block[] getSetById(int id) {
        switch(id) {
            case 0:
                return signs_0;
            case 1:
                return signs_1;
            case 2:
                return signs_2;
            case 3:
                return signs_3;
            case 4:
                return signs_4;
        }
        return signs_0;
    }

}
