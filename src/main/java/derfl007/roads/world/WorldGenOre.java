package derfl007.roads.world;

import java.util.Random;

import com.google.common.base.Predicate;

import derfl007.roads.init.RoadBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

public class WorldGenOre implements IWorldGenerator {
    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        switch (world.provider.getDimension()) { //-1: Nether, 0: Overworld, 1: End
            case -1:
            case 0:
                genSurface(world, random, chunkX, chunkZ);
            case 1:
        }
    }

    private void genSurface(World world, Random random, int chunkX, int chunkZ){
        addOreSpawn(RoadBlocks.asphalt.getDefaultState(), world, random, chunkX, chunkZ, 16, 16, 10, 15, 10, 100, BlockMatcher.forBlock(Blocks.STONE));
    }

    private void addOreSpawn(IBlockState block, World world, Random random, int blockXPos, int blockZPos, int maxX, int maxZ, int maxVeinSize, int chance, int minY, int maxY, Predicate<IBlockState> blockToSpawnIn){
        int diffMinMaxY = maxY - minY;
        for(int x = 0; x < chance; x++){
            int posX = blockXPos * maxX + random.nextInt(maxX);
            int posY = minY + random.nextInt(diffMinMaxY);
            int posZ = blockZPos * maxZ + random.nextInt(maxZ);
            (new WorldGenMinable(block, maxVeinSize, blockToSpawnIn)).generate(world, random, new BlockPos(posX, posY, posZ));
        }
    }
}
