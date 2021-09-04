package pl.kalibrov.main.customStructures.objects.items;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import pl.kalibrov.main.customStructures.init.ModBlocksClient;
import pl.kalibrov.main.customStructures.objects.blocks.TradePostBlock;
import pl.kalibrov.main.customStructures.objects.blocks.TradePostBlock.EnumTradePostPart;

public class TradePostItem extends Item{

	Block block;
	
	public TradePostItem(String name, Block block) {
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		
		this.block = block;
	}
	
	/**
	 * Called when a Block is right-clicked with this Item
	 * @param facing = direction of block facing (e.g. chest open side)
	 */
	@Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, 
    		EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
    	if (facing != EnumFacing.UP)
        {
            return EnumActionResult.FAIL;
        }
        else
        {
            IBlockState iblockstate = worldIn.getBlockState(pos);
            Block block = iblockstate.getBlock();

            if (!block.isReplaceable(worldIn, pos))
            {
                pos = pos.offset(facing);
            }

            ItemStack itemstack = player.getHeldItem(hand);
            EnumFacing enumfacing = EnumFacing.fromAngle((double)player.rotationYaw);
            
            if (player.canPlayerEdit(pos, facing, itemstack) && 
            		((TradePostBlock)(this.block)).canPlaceBlockAt(worldIn, pos, enumfacing))
            {
                
                int i = enumfacing.getFrontOffsetX();
                int j = enumfacing.getFrontOffsetZ();
                boolean flag = i < 0 && hitZ < 0.5F || i > 0 && hitZ > 0.5F || j < 0 && hitX > 0.5F || j > 0 && hitX < 0.5F;
                placeTradePost(worldIn, pos, enumfacing, this.block, flag);
                //SoundType soundtype = worldIn.getBlockState(pos).getBlock().getSoundType(worldIn.getBlockState(pos), worldIn, pos, player);
                //worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                itemstack.shrink(1);
                return EnumActionResult.SUCCESS;
            }
            else
            {
                return EnumActionResult.FAIL;
            }
        }
    }
	public static void placeTradePost(World worldIn, BlockPos pos, EnumFacing facing, Block door, boolean isRightHinge)
    {                  
        for(int i = 0; i < EnumTradePostPart.values().length; i++) {
        	// Get needed part 
        	IBlockState iblockstate = 
        			TradePostBlock.tradePostBlock[i].getDefaultState().
            		withProperty(TradePostBlock.FACING, facing);   
        	
        	BlockPos curr_p = EnumTradePostPart.getPosByID(pos, facing, i);
        	worldIn.setBlockState(curr_p, iblockstate, 2);
        }
        /*
        worldIn.setBlockState(pos, iblockstate.withProperty(
        		TradePostBlock.PART, EnumTradePostPart.LOWER_FRONT_RIGHT).
        		withProperty(TradePostBlock.HALF_FACING, second_bit).
        		withProperty(TradePostBlock.IS_BOTTOM, true).
        		withProperty(TradePostBlock.BOTTOM_PART, EnumBottomPart.FRONT_RIGHT), 2);
        */
    }
}
