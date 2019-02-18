package derfl007.roads.common.tileentities;

import derfl007.roads.common.blocks.BlockRoadLantern;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.world.border.IBorderListener;

public class TileEntityRoadLantern extends TileEntity implements ITickable {

    BlockRoadLantern blockRoadLantern;
    IBlockState state;

    public TileEntityRoadLantern() {}
    public TileEntityRoadLantern(BlockRoadLantern blockRoadLantern, IBlockState state) {
        this.blockRoadLantern = blockRoadLantern;
        this.state = state;
    }
    /**
     * Like the old updateEntity(), except more generic.
     */
    public void update() {
        if (this.world != null && !this.world.isRemote && this.world.getTotalWorldTime() % 20L == 0L && this.blockRoadLantern != null && this.state != null) {
            this.blockType = this.getBlockType();
            this.blockRoadLantern.updateLight(this.world, this.pos, this.blockRoadLantern.getDefaultState().withProperty(BlockRoadLantern.FACING, this.state.getValue(BlockRoadLantern.FACING)));
        }
    }
}