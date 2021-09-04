package pl.kalibrov.main.customStructures.objects.blocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;
import javax.vecmath.Vector3f;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFlowerPot;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Mirror;
import net.minecraft.util.NonNullList;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.GameType;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pl.kalibrov.main.MathAlikhan;
import pl.kalibrov.main.RPGMod;
import pl.kalibrov.main.customStructures.init.ModItems;
import pl.kalibrov.main.customStructures.objects.blocks.TradePostBlock.EnumTradePostPart;
import pl.kalibrov.main.customStructures.objects.support.SitEntity;
import pl.kalibrov.main.server.ServerProxy;

public class TradePostBlock extends Block implements IHasComplexHitbox{
	
	public static TradePostBlock[] tradePostBlock = new TradePostBlock[EnumTradePostPart.values().length];
	public static Item[] tradePostBlock_item = new Item[EnumTradePostPart.values().length];
	
	public final EnumTradePostPart part;
	private final int id;
	
	public TradePostBlock(Material mat, String name, EnumTradePostPart part, int id) {
		super(mat);
		
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		
		this.part = part;
		this.id = id;
		
		this.setDefaultState(
				this.blockState.getBaseState().withProperty(
						FACING, EnumFacing.NORTH).withProperty(
						OCCUPIED, false));
				/*
						.withProperty(
						HALF_FACING, Boolean.valueOf(false)).withProperty(
						PART, EnumTradePostPart.LOWER_FRONT_RIGHT));*/
	}

	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final PropertyBool OCCUPIED = PropertyBool.create("occupied");
    
	
	@Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, 
    		AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes,
    		@Nullable Entity entityIn, boolean isActualState)
    {
    	List<AxisAlignedBB> myPartCollision = this.part.getMyCollider(state.getValue(this.FACING));
    	
    	for(AxisAlignedBB aBB : myPartCollision) {
    		addCollisionBoxToList(pos, entityBox, collidingBoxes, aBB);
    	}    	
    }	
	
	
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        state = state.getActualState(source, pos);
        EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);
        
        List<AxisAlignedBB> myPartCollision = this.part.getMyCollider(enumfacing);
        AxisAlignedBB ret = myPartCollision.get(0);
        for(int i = 1; i < myPartCollision.size(); i++) {
        	ret = ret.union(myPartCollision.get(i));
        }
        
        return ret;
    }
	/**
	 * Debug purpose!!!
	 */
	public List<AxisAlignedBB> getComplexTest(BlockPos pos, World worldIn){
		BlockPos heart = pos;
		IBlockState state = worldIn.getBlockState(pos);
		EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);
		List<AxisAlignedBB> total = new ArrayList<AxisAlignedBB>();
		
		if(this.part != EnumTradePostPart.LOWER_FRONT_LEFT) {
			Vec3i offset = EnumTradePostPart.getOffset(enumfacing, this.part.id);
			Vec3i reverse = new Vec3i(-offset.getX(), -offset.getY(), -offset.getZ());
			heart = pos.add(reverse);
		}
		
		for(int i = 0; i < EnumTradePostPart.values().length; i++) {
			BlockPos curr_p = EnumTradePostPart.getPosByID(heart, enumfacing, i);
			List<AxisAlignedBB> curr_aabb = (EnumTradePostPart.values())[i].getMyCollider(enumfacing);
			for(AxisAlignedBB aabb : curr_aabb) {
				total.add(aabb.offset(curr_p));
			}
		}	
		return total;
	}
	/**
	 * Complex highlightning implementation
	 */
	@Override
	public double[] getComplexCollisionList(BlockPos pos, World worldIn) {
		BlockPos heart = pos;
		IBlockState state = worldIn.getBlockState(pos);
		EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);
		List<AxisAlignedBB> total = new ArrayList<AxisAlignedBB>();
		
		if(this.part != EnumTradePostPart.LOWER_FRONT_LEFT) {
			Vec3i offset = EnumTradePostPart.getOffset(enumfacing, this.part.id);
			Vec3i reverse = new Vec3i(-offset.getX(), -offset.getY(), -offset.getZ());
			heart = pos.add(reverse);
		}
		
		
		/*
		for(int i = 0; i < EnumTradePostPart.values().length; i++) {
			BlockPos curr_p = EnumTradePostPart.getPosByID(heart, enumfacing, i);
			List<AxisAlignedBB> curr_aabb = (EnumTradePostPart.values())[i].getMyCollider(enumfacing);
			for(AxisAlignedBB aabb : curr_aabb) {
				total.add(aabb.offset(curr_p));
			}
		}	*/
		
		
        return EnumTradePostPart.getHightlight(heart, enumfacing);
	}
	
	
	
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        //return new ItemStack(this.getItem());
		return new ItemStack(this.getItem());
    }
	private Item getItem()
    {
		//return tradePostBlock_item[id];
		return ModItems.getTradePostItem();
    } 
	public boolean isPassable(IBlockAccess worldIn, BlockPos pos)
    {
        return false;
    }
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }
    /**
     * Called when the block is right clicked by a player.
     */
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {	
    	EnumFacing face = state.getValue(FACING);
    	if (worldIn.isRemote)
        {
            return true;
        }
        else
        {
        	if(this.part != EnumTradePostPart.LOWER_FRONT_LEFT) {
        		pos = this.part.getHeartBlock(pos, face);
        		state = worldIn.getBlockState(pos);
        		if(!(state.getBlock() instanceof TradePostBlock)) {
        			return true;
        		}
        	}
            
        	// SAME AS BED - can't use in Hell/Ender worlds
            net.minecraft.world.WorldProvider.WorldSleepResult sleepResult = worldIn.provider.canSleepAt(playerIn, pos);
            if (sleepResult != net.minecraft.world.WorldProvider.WorldSleepResult.BED_EXPLODES)
            {
                //if (sleepResult == net.minecraft.world.WorldProvider.WorldSleepResult.DENY) return true;
                if (((Boolean)state.getValue(OCCUPIED)).booleanValue())
                {
                    EntityPlayer entityplayer = RPGMod.proxy.getTentRider(pos);

                    if (entityplayer != null)
                    {
                        playerIn.sendStatusMessage(new TextComponentTranslation("tile.tradepost_block.occupied", new Object[0]), true);
                        return true;
                    }
                    
                    // Remove null player from tent
                    state = state.withProperty(OCCUPIED, Boolean.valueOf(false));
                    RPGMod.proxy.dismountTent(pos);
                    worldIn.setBlockState(pos, state, 4);
                }                        
                
                //EntityPlayer.SleepResult entityplayer$sleepresult = playerIn.trySleep(pos);
                boolean result = this.tryEnter(pos, playerIn, face, worldIn);
                if(result) {
                	playerIn.sendStatusMessage(new TextComponentTranslation(
        					"tile.tradepost_block.infoEntered", new Object[0]), true);
                	state = state.withProperty(OCCUPIED, Boolean.valueOf(true));
                    worldIn.setBlockState(pos, state, 4);
                }
                return true;
            }
            else
            {
            	deleteAllBlocks(pos, state, worldIn);
                worldIn.newExplosion(
                		(Entity)null, 
                		(double)pos.getX() + 0.5D, 
                		(double)pos.getY() + 0.5D, 
                		(double)pos.getZ() + 0.5D, 
                		5.0F, true, true);
                return true;
            }
        }
    }
    
    private boolean tryEnter(BlockPos pos, EntityPlayer player, EnumFacing face, World worldIn) {
    	
    	if (!player.world.isRemote)
        {
    		if (!this.tentInRange(pos, face, player))
            {
    			player.sendStatusMessage(new TextComponentTranslation("tile.tradepost_block.tooFarAway", new Object[0]), true);
                return false;
            }
        }
    	
    	
    	if (player.isRiding())
        {
    		player.dismountRidingEntity();
        }
    	    	
    	
    	
    	Vec3d ent_pos = this.getSitEntityPos(face, pos);
    	//Vec3d exit_pos = this.getExitPos(face, new Vec3d(pos));
    	boolean res_sev = RPGMod.proxy.mountTent(new BlockPos(ent_pos), player);
    	if(!res_sev) {
    		return false;
    	}
    	float yawAngle = (face.getOpposite()).getHorizontalAngle();
    
    	SitEntity.sitOnBlock(worldIn, player, ent_pos.x, ent_pos.y, ent_pos.z, yawAngle);    	
    	
    	return true;
    }
    
    
    
    private Vec3d getSitEntityPos(EnumFacing face, BlockPos pos) {
    	/**
    	 * WE ALWAYS SIT IN SOUTH-EAST CORNER OF HEART BLOCK
    	 */
    	Vec3i offset = Vec3i.NULL_VECTOR;
    	
    	switch(face) {
    	case NORTH:
    		offset = face.rotateY().getDirectionVec();
    		break;
    	case SOUTH:
    		offset = face.getDirectionVec();
    		break;    		
    	case EAST:	
    		Vec3i north = face.rotateY().getDirectionVec();
    		Vec3i west = face.getDirectionVec();
    		offset = new Vec3i(north.getX() + west.getX(), 0.0F, north.getZ() + west.getZ());    		
    		break;
    	case WEST:
    	default:
    		break;
    	}
    	
    	double x_pos = ((double)pos.getX()) + ((double)(offset.getX()));
    	double y_pos = ((double)pos.getY()) + 0.5D;
    	double z_pos = ((double)pos.getZ()) + ((double)(offset.getZ()));
    	
    	return new Vec3d(x_pos, y_pos, z_pos);
    }
   
    /**
     * COPIED FROM EntityPlayer.bedInRange
     */
    private boolean tentInRange(BlockPos p_190774_1_, EnumFacing p_190774_2_, EntityPlayer p)
    {
        if (Math.abs(p.posX - (double)p_190774_1_.getX()) <= 3.0D && 
        		Math.abs(p.posY - (double)p_190774_1_.getY()) <= 2.0D && 
        		Math.abs(p.posZ - (double)p_190774_1_.getZ()) <= 3.0D)
        {
            return true;
        }
        else if (p_190774_2_ == null) return false;
        else
        {
            BlockPos blockpos = p_190774_1_.offset(p_190774_2_.getOpposite());
            return Math.abs(p.posX - (double)blockpos.getX()) <= 3.0D && 
            		Math.abs(p.posY - (double)blockpos.getY()) <= 2.0D &&
            		Math.abs(p.posZ - (double)blockpos.getZ()) <= 3.0D;
        }
    }
    
    
    private void deleteAllBlocks(BlockPos pos, IBlockState state, World worldIn) {
    	BlockPos orig = pos;
    	int orig_id = this.part.id;
    	EnumFacing face = state.getValue(FACING);

    	if(this.part != EnumTradePostPart.LOWER_FRONT_LEFT) {
    		orig = this.part.getHeartBlock(pos, face);
    	}
    	
    	for(int i = 0; i < EnumTradePostPart.values().length; i++) {    
    		/*
    		if(avoid && (i == EnumTradePostPart.LOWER_FRONT_LEFT.id)) {
    			//i == orig_id || 
    			continue;
    		}*/
        	BlockPos curr_p = EnumTradePostPart.getPosByID(orig, face, i);
        	if (worldIn.getBlockState(curr_p).getBlock() instanceof TradePostBlock)
            {
                worldIn.setBlockToAir(curr_p);
            }
        	
        }
    }
    
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return part == EnumTradePostPart.LOWER_FRONT_LEFT ? this.getItem() : Items.AIR;
    }
	
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos, EnumFacing facing)
    {
        if (pos.getY() >= worldIn.getHeight() - 1)
        {
            return false;
        }
        else if(facing == EnumFacing.UP) {
        	return false;
        }
        else
        {
            IBlockState state = worldIn.getBlockState(pos.down());
            
            
            boolean areBlocksFree = true;
            boolean areUnderSolidShape = true;
            boolean areUnderSolid = true;
            
            for(int i = 0; i < EnumTradePostPart.values().length; i++) {
            	BlockPos curr_p = EnumTradePostPart.getPosByID(pos, facing, i);
            	areBlocksFree = areBlocksFree && super.canPlaceBlockAt(worldIn, curr_p);
            	
            	
            	if(EnumTradePostPart.values()[i].isLower()) {
            		areUnderSolidShape = areUnderSolidShape &&
            				state.getBlockFaceShape(worldIn, curr_p.down(), EnumFacing.UP) == BlockFaceShape.SOLID;
            		areUnderSolid = areUnderSolid && worldIn.getBlockState(curr_p.down()).isTopSolid();
            	}
            }
            
            return areBlocksFree && areUnderSolidShape && areUnderSolid;
        }
    }
    public EnumPushReaction getMobilityFlag(IBlockState state)
    {
        return EnumPushReaction.BLOCK;
    }
    
    /**
     * Called before the Block is set to air in the world. Called regardless of if the player's tool can actually
     * collect this block
     */
    public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player)
    {
    	deleteAllBlocks(pos, state, worldIn);   	//!player.capabilities.isCreativeMode
    }
    
    @Override
    public void getDrops(net.minecraft.util.NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
    	Item drop = this.getItemDropped(state, RANDOM, fortune);
    	System.out.println("trying to drop: " + this.part + ", drop: " + drop.toString());
    	if(drop != Items.AIR) {
    		System.out.println("DROPPED " + drop.toString());
    		drops.add(new ItemStack(drop));
    	}
    }
    
    /*
    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        NonNullList<ItemStack> ret = NonNullList.create();
        this.getDrops(ret, world, pos, state, fortune);
        return ret;
    }*/
    /*
    @Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {    	
    	Item drop = this.getItemDropped(state, RANDOM, fortune);
    	System.out.println("trying to drop: " + this.part + ", drop: " + drop.toString());
    	if(drop != Items.AIR) {
    		System.out.println("DROPPED " + drop.toString());
    		drops.add(new ItemStack(drop));
    	}
    }*/
    
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;        
    }    
    
    
    
    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
    	EnumFacing face = state.getValue(FACING);
    	BlockPos heart = this.part.getHeartBlock(pos, face);
    	boolean flag1 = false;
    	
    	if(this.part != EnumTradePostPart.LOWER_FRONT_LEFT) {
    		IBlockState blockState = worldIn.getBlockState(heart);
    		blockState.neighborChanged(worldIn, heart, blockIn, fromPos);//blockState.getBlock()
			return;
    	}
    	
    	if(!EnumTradePostPart.isMyUnderSolid(heart, face, worldIn) ||
    	   !EnumTradePostPart.areAllBlocksStillThere(heart, face, worldIn, this.part.id)) {
    		flag1 = true;
    	}

    	if (flag1 && !worldIn.isRemote && state.getBlock() == this)
        {
    		worldIn.setBlockToAir(pos); // should be first!! - so that we get here only once
        	this.deleteAllBlocks(pos, state, worldIn); // delete all
            this.dropBlockAsItem(worldIn, pos, state, 0);
        }
    }
    
    /**
     * Get the actual Block state of this Block at the given position. This applies properties not visible in the
     * metadata, such as fence connections.
     */
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
    	// TODO remake
    	/**
    	 * GET THE OCCUPIED STATE FROM HEART BLOCK
    	 */
    	/*
    	EnumBottomPart bottom = state.getValue(BOTTOM_PART);
        int half_face = state.getValue(HALF_FACING) ? 1 : 0;
        boolean is_bottom = state.getValue(IS_BOTTOM);
    	int height = -1;
    	if(is_bottom) {
    		height = 0;
    		IBlockState upState = worldIn.getBlockState(pos.up());
    		if(upState.getBlock() != this) {
    			return state;
    		}
    		int half_face2 = upState.getValue(HALF_FACING) ? 1 : 0;
    		int facing_index = half_face2 * 2 + half_face;
    		EnumFacing face = EnumFacing.getHorizontal(facing_index).rotateYCCW();
    		state = state.withProperty(FACING, face);
    		EnumTradePostPart part = EnumTradePostPart.getEnumFromTwoBits(face, bottom, height);
    		state = state.withProperty(PART, part);
    	}
    	else {
    		height = 1;
    		IBlockState downState = worldIn.getBlockState(pos.down());
    		if(downState.getBlock() != this) {
    			return state;
    		}
    		boolean is_bottom_down = downState.getValue(IS_BOTTOM);
    		if(!is_bottom_down) {
    			height++;
    			downState = worldIn.getBlockState(pos.down().down());
    		}    		
    		if(downState.getBlock() != this) {
    			return state;
    		}
    		int half_face2 = downState.getValue(HALF_FACING) ? 1 : 0;
    		int facing_index = half_face * 2 + half_face2;
    		EnumFacing face = EnumFacing.getHorizontal(facing_index).rotateYCCW();
    		state = state.withProperty(FACING, face);
    		EnumTradePostPart part = EnumTradePostPart.getEnumFromTwoBits(face, bottom, height);
    		state = state.withProperty(PART, part);
    	}    	
    	*/
        return state;
    }

    /**
     * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     */
    public IBlockState withRotation(IBlockState state, Rotation rot)
    {
    	return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
    }
    
	
    public IBlockState getStateFromMeta(int meta)
    {
    	IBlockState state = this.getDefaultState();
    	EnumFacing face = EnumFacing.getHorizontal(meta).rotateYCCW();
    	state = state.withProperty(FACING, face);
    	return state;
    }

	protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {FACING, OCCUPIED});
    }
	/**
     * Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     */
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
    {
        return mirrorIn == Mirror.NONE ? 
        		state : 
        		state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
    }
    
    
    public int getMetaFromState(IBlockState state)
    {
        int i = 0;
        
        int index_face = ((EnumFacing)state.getValue(FACING)).rotateY().getHorizontalIndex();
        i = index_face;
        
        
        return i;
    }
    
	/**
	 * Can't place torches, buttons etc. <<Surface type>>
	 */
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }
    
    private static int curr_enum_id = 0;
    
    public static enum EnumTradePostPart implements IStringSerializable
    {
    	/**
    	 * x --- offset in direction of <rotateY()>
    	 * y --- offset in direction of y axis
    	 * z --- offset in direction of facing
    	 */
    	UPPER_BACK_LEFT(0, 2, 1),
        MIDDLE_BACK_LEFT(0, 1, 1),
        LOWER_BACK_LEFT(0, 0, 1),
        
        UPPER_FRONT_LEFT(0, 2, 0), //
        MIDDLE_FRONT_LEFT(0, 1, 0), //
        LOWER_FRONT_LEFT(0, 0, 0), //
        
        UPPER_BACK_RIGHT(1, 2, 1), 
        MIDDLE_BACK_RIGHT(1, 1, 1), 
        LOWER_BACK_RIGHT(1, 0, 1), 
        
        UPPER_FRONT_RIGHT(1, 2, 0),
        MIDDLE_FRONT_RIGHT(1, 1, 0),
        LOWER_FRONT_RIGHT(1, 0, 0);
    	
    	
    	public final Vec3i offset;
    	private int id;
    	
    	private EnumTradePostPart(int x, int y, int z) {
    		this.offset = new Vec3i(x, y, z);
    		this.id = curr_enum_id++;
    	}
    	
    	
    	private static final EnumTradePostPart[] all = EnumTradePostPart.values();
    	private static final EnumTradePostPart[] lowers = {
    			LOWER_BACK_LEFT, LOWER_FRONT_LEFT, LOWER_BACK_RIGHT, LOWER_FRONT_RIGHT
    	};
    	private static final EnumTradePostPart[] middles = {
    			MIDDLE_BACK_LEFT, MIDDLE_FRONT_LEFT, MIDDLE_BACK_RIGHT, MIDDLE_FRONT_RIGHT
    	};
    	private static final EnumTradePostPart[] uppers = {
    			UPPER_BACK_LEFT, UPPER_FRONT_LEFT, 
    			UPPER_BACK_RIGHT, UPPER_FRONT_RIGHT
    	};
    	private static final EnumTradePostPart[] fronts = {
    			LOWER_FRONT_LEFT, MIDDLE_FRONT_LEFT, UPPER_FRONT_LEFT,
    			LOWER_FRONT_RIGHT, MIDDLE_FRONT_RIGHT, UPPER_FRONT_RIGHT
    	};
    	private static final EnumTradePostPart[] backs = {
    			LOWER_BACK_LEFT, MIDDLE_BACK_LEFT, UPPER_BACK_LEFT,
    			LOWER_BACK_RIGHT, MIDDLE_BACK_RIGHT, UPPER_BACK_RIGHT
    	};
    	private static final EnumTradePostPart[] lefts = {
    			LOWER_BACK_LEFT, MIDDLE_BACK_LEFT, UPPER_BACK_LEFT,
    			LOWER_FRONT_LEFT, MIDDLE_FRONT_LEFT, UPPER_FRONT_LEFT
    	};
    	private static final EnumTradePostPart[] rights = {
    			LOWER_BACK_RIGHT, MIDDLE_BACK_RIGHT, UPPER_BACK_RIGHT,
    			LOWER_FRONT_RIGHT, MIDDLE_FRONT_RIGHT, UPPER_FRONT_RIGHT
    	};
    	
    	public static BlockPos getPosByID(BlockPos orig, EnumFacing face, int id) {
    		Vec3i final_offset = getOffset(face, id);
    		BlockPos final_pos = orig.add(final_offset);
    		return final_pos;
    	}
    	
    	private static Vec3i getOffset(EnumFacing face, int id) {
    		EnumTradePostPart part = (values())[id];
    		
    		int final_y = part.offset.getY();
    		int final_x = 0;
    		int final_z = 0;
    		/**
    		 * see for explanation
    		 * https://static.wikia.nocookie.net/minecraft_gamepedia/images/5/51/Coordinates.png/revision/latest?cb=20200729013357
    		 */
    		switch(face) {
	    		case NORTH:
	    			final_z -= part.offset.getZ();
	    			final_x += part.offset.getX();
	    			break;
	    		case SOUTH:
	    			final_z += part.offset.getZ();
	    			final_x -= part.offset.getX();
	    			break;
	    		case WEST:
	    			final_x -= part.offset.getZ();
	    			final_z -= part.offset.getX();
	    			break;
	    		case EAST:
	    		default:
	    			final_x += part.offset.getZ();
	    			final_z += part.offset.getX();
    		}

    		Vec3i final_offset = new Vec3i(final_x, final_y, final_z);
    		return final_offset;
    	}
    	
    	
    	public static boolean isMyUnderSolid(BlockPos heart, EnumFacing facing, World worldIn) {
            boolean areUnderSolidShape = true;
            boolean areUnderSolid = true;
            IBlockState state = worldIn.getBlockState(heart.down());
            
            for(int i = 0; i < EnumTradePostPart.values().length; i++) {
            	BlockPos curr_p = EnumTradePostPart.getPosByID(heart, facing, i);
            	
            	
            	if(EnumTradePostPart.values()[i].isLower()) {
            		areUnderSolidShape = areUnderSolidShape &&
            				state.getBlockFaceShape(worldIn, curr_p.down(), EnumFacing.UP) == BlockFaceShape.SOLID;
            		areUnderSolid = areUnderSolid && worldIn.getBlockState(curr_p.down()).isTopSolid();
            	}
            }
    		
    		
    		return areUnderSolidShape && areUnderSolid;
    	}
    	
    	public static boolean areAllBlocksStillThere(BlockPos heart, EnumFacing face, World worldIn, int except) {
    		for(int i = 0; i < EnumTradePostPart.values().length; i++) {    
    			if(i == except) {
    				continue;
    			}
            	BlockPos curr_p = EnumTradePostPart.getPosByID(heart, face, i);
            	if (!(worldIn.getBlockState(curr_p).getBlock() instanceof TradePostBlock))
                {
                    return false;
                }
            	
            }
    		return true;
    	}
    	
    	public BlockPos getHeartBlock(BlockPos pos, EnumFacing face) {
    		
    		int id = Arrays.asList(values()).indexOf(this);
    		Vec3i final_offset = getOffset(face, id);
    		// invert
    		final_offset = new Vec3i(-final_offset.getX(), -final_offset.getY(), -final_offset.getZ());
    		BlockPos final_pos = pos.add(final_offset);
    		
    		return final_pos;
    	}
    	
    	public boolean isUpper() {
    		return Arrays.asList(uppers).contains(this);
    	}
    	public boolean isMiddle() {
    		return Arrays.asList(middles).contains(this);
    	}
    	public boolean isLower() {
    		return Arrays.asList(lowers).contains(this);
    	}
    	public boolean isFront() {
    		return Arrays.asList(fronts).contains(this);
    	}
    	public boolean isBack() {
    		return Arrays.asList(backs).contains(this);
    	}
    	public boolean isLeft() {
    		return Arrays.asList(lefts).contains(this);
    	}
    	public boolean isRight() {
    		return Arrays.asList(rights).contains(this);
    	}
    	public int getID() {
    		return id;
    	}
        public String toString()
        {
            return this.getName();
        }    
        public String getName()
        {
    		switch(this) {
    		case UPPER_BACK_LEFT:
        		return "upper_back_left";
        	case MIDDLE_BACK_LEFT:
        		return "middle_back_left";
        	case LOWER_BACK_LEFT:
        		return "lower_back_left";
        	case UPPER_FRONT_LEFT:
        		return "upper_front_left";
        	case MIDDLE_FRONT_LEFT:
        		return "middle_front_left";
        	case LOWER_FRONT_LEFT:
        		return "lower_front_left";
        		
        	case UPPER_BACK_RIGHT:
        		return "upper_back_right";
        	case MIDDLE_BACK_RIGHT:
        		return "middle_back_right";
        	case LOWER_BACK_RIGHT:
        		return "lower_back_right";
        	case UPPER_FRONT_RIGHT:
        		return "upper_front_right";
        	case MIDDLE_FRONT_RIGHT:
        		return "middle_front_right";
        	case LOWER_FRONT_RIGHT:
        	default:
        		return "lower_front_right";
        	}
        }
        
        
        
        
        
        protected static final double[] LOWER_FRONT_LEFT_COMPLEX = new double[] {
        	0.4D, 0.0D, 0.0D, 1.0D, 1.0D, 0.1D
        	,
        	0.85D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D	
        };
        protected static final double[] LOWER_BACK_LEFT_COMPLEX = new double[] {
        	0.0D, 0.0D, 0.75D, 1.0D, 1.0D, 1.0D
        	,
        	0.85D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D	
        };
        protected static final double[] LOWER_FRONT_RIGHT_COMPLEX = new double[] {
        	0.0D, 0.0D, 0.0D, 0.6D, 1.0D, 0.1D
        	,
        	0.0D, 0.0D, 0.0D, 0.15D, 1.0D, 1.0D
        };
        protected static final double[] LOWER_BACK_RIGHT_COMPLEX = new double[] {
        	0.0D, 0.0D, 0.75D, 1.0D, 1.0D, 1.0D
        	,
        	0.0D, 0.0D, 0.0D, 0.15D, 1.0D, 1.0D	
        };
        protected static final double[] UPPER_LEFT_COMPLEX = new double[] {
        	0.0D, 0.0D, 0.0D, 0.5D, 0.75D, 1.0D
        };
        protected static final double[] UPPER_RIGHT_COMPLEX = new double[] {
        	0.5D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D
        };
        
        
        
        protected static final AxisAlignedBB NORTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
        protected static final AxisAlignedBB WEST_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
        protected static final AxisAlignedBB EAST_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.1D, 1.0D, 1.0D);
        
        protected static final AxisAlignedBB SOUTH_RIGHT = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.1D, 1.0D, 1.0D);
        protected static final AxisAlignedBB SOUTH_LEFT = new AxisAlignedBB(0.9D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);

        
        
        public List<AxisAlignedBB> getMyCollider(EnumFacing face) {
        	List<AxisAlignedBB> colliders = new ArrayList<AxisAlignedBB>();        	
        	
        	double[] coords = this.getSouthColliders();
        	
        	for(int i = 0; i < coords.length / 6; i++) {
        		AxisAlignedBB aabb = null;
        		switch(face) {
            	case NORTH:
            		aabb = new AxisAlignedBB(
    				1.0D-coords[6*i], coords[6*i+1], 1.0D-coords[6*i+2], 1.0D-coords[6*i+3], coords[6*i+4], 1.0D-coords[6*i+5]
    				);
            		break;
            	case WEST:
            		aabb = new AxisAlignedBB(
            		1.0D-coords[6*i + 2], coords[6*i+1], coords[6*i], 1.0D-coords[6*i+5], coords[6*i+4], coords[6*i+3]
    				);
            		break;
            	case EAST:
            		aabb = new AxisAlignedBB(
    				coords[6*i + 2], coords[6*i+1], 1.0D-coords[6*i], coords[6*i+5], coords[6*i+4], 1.0D-coords[6*i+3]
    				);
            		break;
            	case SOUTH:
            	default:
            		aabb = new AxisAlignedBB(
    				coords[6*i], coords[6*i+1], coords[6*i+2], coords[6*i+3], coords[6*i+4], coords[6*i+5]
    				);
            		break;	
            	}
        		colliders.add(aabb);
        	}        	
        	
        	return colliders;
        }
        
        private double[] getSouthColliders(){
        	
        	if(this.isUpper()) {
        		return this.isLeft() ? UPPER_LEFT_COMPLEX : UPPER_RIGHT_COMPLEX; 
        	}
        	
        	
        	if(this.isLeft() && this.isFront()) {
        		return LOWER_FRONT_LEFT_COMPLEX;
        	}
        	else if(this.isLeft() && this.isBack()) {
        		return LOWER_BACK_LEFT_COMPLEX; 
        	}
        	else if(this.isRight() && this.isFront()) {
        		return LOWER_FRONT_RIGHT_COMPLEX; 
        	}
        	else {
        		return LOWER_BACK_RIGHT_COMPLEX;
        	}
        }
        
        private static final double[] vertices = {
        		// Front
        		-1.0D, 0.0D, 0.0D, 0.0D,
        		-1.0D, 0.0D, 0.0D, 1.0D,
        		-0.4D, 0.0D, 0.0D, 1.0D,
        		-0.4D, 1.5D, 0.0D, 1.0D,
        		 0.4D, 1.5D, 0.0D, 1.0D,
        		 0.4D, 0.0D, 0.0D, 1.0D,
        		 1.0D, 0.0D, 0.0D, 1.0D,
        		 1.0D, 2.0D, 0.0D, 1.0D,
        		-1.0D, 2.0D, 0.0D, 1.0D,       		
        		-1.0D, 0.0D, 0.0D, 1.0D,
        		
        		// Back
        		-1.0D, 0.0D, 2.0D, 1.0D,
        		-1.0D, 2.0D, 2.0D, 1.0D,
        		 1.0D, 2.0D, 2.0D, 1.0D,
        		 1.0D, 0.0D, 2.0D, 1.0D,
        		-1.0D, 0.0D, 2.0D, 1.0D,
        		
        		// Right
        		-1.0D, 2.0D, 2.0D, 0.0D,  // alpha!
        		-1.0D, 2.0D, 0.0D, 1.0D,
        		
        		// Top
        		 0.0D, 3.0D, 1.0D, 1.0D,
        		//Right
        		-1.0D, 2.0D, 2.0D, 1.0D,
        		// Top
        		 0.0D, 3.0D, 1.0D, 0.0D, // alpha !
        		// Left
        		 1.0D, 2.0D, 2.0D, 1.0D,
        		 1.0D, 2.0D, 0.0D, 1.0D,
        		// Top
        		 0.0D, 3.0D, 1.0D, 1.0D,
        		// Left
        		 1.0D, 2.0D, 0.0D, 0.0D, // alpha !
        		 1.0D, 0.0D, 0.0D, 0.0D,//alpha !
        		 1.0D, 0.0D, 2.0D, 1.0D       		
        };
        
        private static double[] getHightlight(BlockPos heart, EnumFacing face) {
        	int x_offset = heart.getX();
        	int y_offset = heart.getY();
        	int z_offset = heart.getZ();
        	
        	double[] ret = new double[vertices.length];
        	for(int i = 0; i < vertices.length / 4; i++) {        		
        		// translate to change heart center
        		Vec3d offset = MathAlikhan.translationOriginBlock(face);
        		Vec3d orig = new Vec3d(vertices[i*4], vertices[i*4+1], vertices[i*4+2]);
        		Vec3d translated = MathAlikhan.add(
        				orig,
        				offset);    
        		
        		// Normal rotation <matrix> , e.g. NON 0-90-180-270 rotations
        		/*
        		Vec3d rotated = MathAlikhan.rotateVertexAroundAxis(
        				translated,
        				0.0D, (double)(face.getHorizontalAngle()), 0.0D, false);*/
        		
        		
        		Vec3d rotated = MathAlikhan.easyRotate(translated, face);
        		translated = rotated;
        		
        		// * 1.002D so that border is a little outside
        		// x/y/z offsets are for world position
        		ret[i*4]     = translated.x * 1.002D  + x_offset;
        		ret[i*4 + 1] = translated.y * 1.01D   + y_offset;
        		ret[i*4 + 2] = translated.z * 1.002D  + z_offset;
        		ret[i*4 + 3] = vertices[i*4 + 3];
        	}        	
        	
        	return ret;
        }
    }
}
