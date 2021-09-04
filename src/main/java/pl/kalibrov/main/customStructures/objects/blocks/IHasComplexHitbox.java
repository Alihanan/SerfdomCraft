package pl.kalibrov.main.customStructures.objects.blocks;

import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IHasComplexHitbox {
	
	
	public double[] getComplexCollisionList(BlockPos pos, World worldIn);
	public List<AxisAlignedBB> getComplexTest(BlockPos pos, World worldIn);
}
