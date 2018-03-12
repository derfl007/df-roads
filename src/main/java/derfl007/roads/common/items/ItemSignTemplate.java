package derfl007.roads.common.items;

import derfl007.roads.Roads;
import net.minecraft.item.Item;

public class ItemSignTemplate extends Item {
    public ItemSignTemplate(String name) {
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setCreativeTab(Roads.ROADS_TAB);
    }
}
