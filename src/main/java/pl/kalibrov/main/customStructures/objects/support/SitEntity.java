package pl.kalibrov.main.customStructures.objects.support;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import pl.kalibrov.main.MathAlikhan;
import pl.kalibrov.main.RPGMod;

public class SitEntity extends Entity{
		
	private double x, y, z;
	private boolean firstUpdate = true;
	private double yOffset = -0.5D;
	
	public SitEntity(World worldIn) {
		super(worldIn);
		this.noClip = true;
        this.height = 0.00001F;
        this.width =  0.00001F;
	}

	public SitEntity(World worldIn, double x, double y, double z) {
		this(worldIn);
		this.x = x;
		this.y = y;
		this.z = z;
		
		setPosition(x, y, z);
	}
	
	/**
	 * Combined from vanilla code(compilation of vanilla methods)
	 */
	@Override
	public void updatePassenger(Entity passenger) {
		for (Entity entity : this.getPassengers())
        {
            if (entity.equals(passenger))
            {
            	if(firstUpdate) {
            		passenger.setPositionAndRotation(
            				this.posX, this.posY + this.getMountedYOffset(), this.posZ, 
            				this.rotationYaw, 
                    		0.0f);
            		firstUpdate = false;
            		return;
            	}
            	// this one just to ensure position change
            	passenger.setPosition(this.posX, this.posY + this.getMountedYOffset(), this.posZ);    
            	
            	float currYaw = MathAlikhan.normalizeAngleToCircle(passenger.rotationYaw);	
            	float currPitch = MathAlikhan.normalizeAngleToCircle(passenger.rotationPitch);	
            	
            	float resYaw = MathAlikhan.clampAngleInterval(currYaw, this.rotationYaw, RPGMod.tentViewYawAngle);            	
            	float resPitch = MathAlikhan.clampAngleInterval(currPitch, 0.0f, RPGMod.tentViewPitchAngle);
            	// convert to <-180, 180> range ----> should be used with pitch!
            	if(resPitch > 180.0f) {
            		resPitch -= 360.0f;
            	}
            	// prevent teleport
            	/*
            	float diffY = MathAlikhan.getMinArc(currYaw, this.rotationYaw, RPGMod.tentViewYawAngle);            	
            	float diffP = MathAlikhan.getMinArc(currPitch, 0.0f, RPGMod.tentViewPitchAngle);
            	if((diffY) > ) {
            		
            	}*/
            	
            	//passenger.sendMessage(new TextComponentString("Pitch: " + currPitch + " clamped to " + resPitch));
            	
            	passenger.setPositionAndRotation(
            			this.posX, 
            			this.posY + this.getMountedYOffset(), 
            			this.posZ, 
            			resYaw, 
            			resPitch);
            	return;
            }
        }	
	}
	
	@Override
    public void onEntityUpdate()
    {
        if(!this.world.isRemote)
        {
            if(!this.isBeingRidden() || this.world.isAirBlock(new BlockPos(x, y, z)))
            {
                this.setDead();
                world.updateComparatorOutputLevel(getPosition(), world.getBlockState(getPosition()).getBlock());
            }
        }
    }

	@Override
	public void removePassengers() {
		List<Entity> pass = this.getPassengers();
		for(Entity ent : pass) {
			exitOnFront(ent);
		}
		super.removePassengers();
		for(Entity ent : pass) {
			exitOnFront(ent);
		}
		RPGMod.proxy.dismountTent(new BlockPos(this.x, this.y, this.z));
	}
	@Override
	protected void removePassenger(Entity passenger) {
		exitOnFront(passenger);
		super.removePassenger(passenger);
		exitOnFront(passenger);
		RPGMod.proxy.dismountTent(new BlockPos(this.x, this.y, this.z));
	}
	
	private void exitOnFront(Entity pass) {
		if(pass != null) {
			if(!this.world.isRemote) {
				//pass.sendMessage(new TextComponentString("SERVERED"));
				Vec3d exit_pos = this.getExitPos(this.getAdjustedHorizontalFacing(), 
						this.getPositionVector());
				this.setPosition(exit_pos.x, exit_pos.y, exit_pos.z);
				pass.setPositionAndUpdate(exit_pos.x, exit_pos.y, exit_pos.z);
			}
			
		}	
	}
	private Vec3d getExitPos(EnumFacing face, Vec3d pos) {
    	
    	Vec3i offset = face.getDirectionVec();
    	Vec3d doff = new Vec3d(offset);
    	//doff = new Vec3d(doff.x * 1.5D, doff.y * 1.5D, doff.z * 1.5D);
    	return MathAlikhan.add(pos, doff);
    }
	@Override
    public double getMountedYOffset()
    {
        return this.yOffset;
    }

	@Override
    protected boolean shouldSetPosAfterLoading()
    {
        return false;
    }
	@Override
	protected void entityInit() {		
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {		
	}
	
	
	
	
	public static void sitOnBlock(World world, EntityPlayer player, double x, double y, double z, float yawIn) {
		
		if(!world.isRemote && !player.isSneaking())
        {
			//&& !checkForExistingSeat(world, x, y, z, player)
			// technically already checked in upper part, but TODO
			float currYaw = yawIn;
			
			SitEntity seat = new SitEntity(world, x, y, z); // pos
			seat.rotationYaw = currYaw;
            world.spawnEntity(seat);			
            
            player.startRiding(seat);
        }
		
	}
	
	protected static void setSize(EntityPlayer player, float width, float height)
    {
        if (width != player.width || height != player.height)
        {
            float f = player.width;
            player.width = width;
            player.height = height;

            if (player.width < f)
            {
                double d0 = (double)width / 2.0D;
                player.setEntityBoundingBox(new AxisAlignedBB(player.posX - d0, player.posY, player.posZ - d0, player.posX + d0, player.posY + (double)player.height, player.posZ + d0));
                return;
            }

            AxisAlignedBB axisalignedbb = player.getEntityBoundingBox();
            player.setEntityBoundingBox(new AxisAlignedBB(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ, axisalignedbb.minX + (double)player.width, axisalignedbb.minY + (double)player.height, axisalignedbb.minZ + (double)player.width));

            if (player.width > f && !player.world.isRemote)
            {
                player.move(MoverType.SELF, (double)(f - player.width), 0.0D, (double)(f - player.width));
            }
        }
    }
}
