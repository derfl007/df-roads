package derfl007.roads.common.tileentities;

import derfl007.roads.common.blocks.BlockRoadLantern;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntityRoadLantern extends TileEntity implements ITickable
{
    /**
     * Like the old updateEntity(), except more generic.
     */
    public void update() {
        if (this.world != null && !this.world.isRemote && this.world.getTotalWorldTime() % 20L == 0L) {
            this.blockType = this.getBlockType();

            if (this.blockType instanceof BlockRoadLantern) {
                ((BlockRoadLantern)this.blockType).updateLight(this.world, this.pos, this.blockType.getDefaultState());
            }
        }
    }
}