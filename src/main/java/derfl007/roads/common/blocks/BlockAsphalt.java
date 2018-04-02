package derfl007.roads.common.blocks;


import derfl007.roads.Roads;
import derfl007.roads.init.RoadBlocks;
import derfl007.roads.init.RoadItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import java.util.Random;

public class BlockAsphalt extends Block {
    public BlockAsphalt(String name) {
        super(Material.ROCK);
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setCreativeTab(Roads.ROADS_TAB);
        this.setHardness(1.5F);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return new ItemStack(RoadBlocks.asphalt).getItem();
    }
}
