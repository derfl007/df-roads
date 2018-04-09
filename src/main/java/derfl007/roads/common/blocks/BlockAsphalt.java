package derfl007.roads.common.blocks;


import derfl007.roads.Roads;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockAsphalt extends Block {
    public BlockAsphalt(String name) {
        super(Material.ROCK);
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setCreativeTab(Roads.ROADS_TAB);
        this.setHardness(1.5F);
    }
}
