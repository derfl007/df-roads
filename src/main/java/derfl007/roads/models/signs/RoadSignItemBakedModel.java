package derfl007.roads.models.signs;

import derfl007.roads.common.blocks.BlockRoadSignRotatable;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraftforge.client.model.IModel;

public class RoadSignItemBakedModel extends RoadSignBakedModel {

	private final BlockRoadSignRotatable block;

	 RoadSignItemBakedModel(RoadSignBakedModel parentModel, BlockRoadSignRotatable block) {
		super(parentModel);

		this.block = block;
	}

	@Override
	protected final IModel retexture(IModel model) {
		return retexture(model, block);
	}

	@Deprecated()
	@Override
	public ItemOverrideList getOverrides() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

}
