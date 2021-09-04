package pl.modding.client.gui.inventories;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pl.modding.main.RPGMod;

public class RPGInventoryContainer extends Container{
	
	protected final int invenSize = 28;
    private static final EntityEquipmentSlot[] VALID_EQUIPMENT_SLOTS = new EntityEquipmentSlot[] {EntityEquipmentSlot.HEAD, 
    		EntityEquipmentSlot.CHEST, EntityEquipmentSlot.LEGS, EntityEquipmentSlot.FEET};

	
	public RPGInventoryContainer(InventoryPlayer playerInv) {	
		
		
		for (int k = 0; k < 4; ++k)
        {
            final EntityEquipmentSlot entityequipmentslot = VALID_EQUIPMENT_SLOTS[k];
            this.addSlotToContainer(new Slot(playerInv, 36 + (3 - k), 22, 68 + k * 18)
            {

                public int getSlotStackLimit()
                {
                    return 1;
                }

                public boolean isItemValid(ItemStack stack)
                {
                    return stack.getItem().isValidArmor(stack, entityequipmentslot, playerInv.player);
                }

                public boolean canTakeStack(EntityPlayer playerIn)
                {
                    ItemStack itemstack = this.getStack();
                    return !itemstack.isEmpty() && !playerIn.isCreative() && EnchantmentHelper.hasBindingCurse(itemstack) ? false : super.canTakeStack(playerIn);
                }
                /*
                @Nullable
                @SideOnly(Side.CLIENT)
                public String getSlotTexture()
                {
                    return ItemArmor.EMPTY_SLOT_NAMES[entityequipmentslot.getIndex()];
                }*/
            });
        }
		
        // MAIN PLAYER INVENTORY
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 6; x++) {
            	this.addSlotToContainer(new Slot(playerInv, 
            			x + (y * 6) + 9, // 9 - 32
            			154 + y * 18, 
            			28 + x * 18));
            }
        }     
        
        
		for (int k = 0; k < 4; k++) {
			this.addSlotToContainer(new Slot(playerInv, 
					k, 
					154 + k * 18, //     154
					140)); // 0 - 8     140
		}
	}
	@Override
	@Nonnull
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = inventorySlots.get(index);
	
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
	
			int containerSlots = inventorySlots.size() - RPGMod.m.player.inventory.mainInventory.size();
	
			if (index < containerSlots) {
				if (!this.mergeItemStack(itemstack1, containerSlots, inventorySlots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.mergeItemStack(itemstack1, 0, containerSlots, false)) {
				return ItemStack.EMPTY;
			}
	
			if (itemstack1.getCount() == 0) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}
	
			if (itemstack1.getCount() == itemstack.getCount()) {
				return ItemStack.EMPTY;
			}
	
			slot.onTake(RPGMod.m.player, itemstack1);
		}
	
		return itemstack;
	}
	/*
	@Override
	protected boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection){
		boolean flag = false;
		int i = startIndex;
		if (reverseDirection) i = endIndex - 1;
		
		if (stack.isStackable()){
			while (stack.getCount() > 0 && (!reverseDirection && i < endIndex || reverseDirection && i >= startIndex)){
				Slot slot = (Slot)this.inventorySlots.get(i);
				ItemStack itemstack = slot.getStack();
				int maxLimit = Math.min(stack.getMaxStackSize(), slot.getSlotStackLimit());
				
				if (!itemstack.isEmpty() && areItemStacksEqual(stack, itemstack)){
					int j = itemstack.getCount() + stack.getCount();
					if (j <= maxLimit){
						stack.setCount(0);
						itemstack.setCount(j);
						slot.onSlotChanged();
						flag = true;
						
					}else if (itemstack.getCount() < maxLimit){
						stack.shrink(maxLimit - itemstack.getCount());
						itemstack.setCount(maxLimit);
						slot.onSlotChanged();
						flag = true;
					}
				}
				if (reverseDirection){ 
					--i;
				}else ++i;
			}
		}
		if (stack.getCount() > 0){
			if (reverseDirection){
				i = endIndex - 1;
			}else i = startIndex;

			while (!reverseDirection && i < endIndex || reverseDirection && i >= startIndex){
				Slot slot1 = (Slot)this.inventorySlots.get(i);
				ItemStack itemstack1 = slot1.getStack();

				if (itemstack1.isEmpty() && slot1.isItemValid(stack)){ // Forge: Make sure to respect isItemValid in the slot.
					if(stack.getCount() <= slot1.getSlotStackLimit()){
						slot1.putStack(stack.copy());
						slot1.onSlotChanged();
						stack.setCount(0);
						flag = true;
						break;
					}else{
						itemstack1 = stack.copy();
						stack.shrink(slot1.getSlotStackLimit());
						itemstack1.setCount(slot1.getSlotStackLimit());
						slot1.putStack(itemstack1);
						slot1.onSlotChanged();
						flag = true;
					}					
				}
				if (reverseDirection){
					--i;
				}else ++i;
			}
		}
		return flag;
	}*/
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}
	private static boolean areItemStacksEqual(ItemStack stackA, ItemStack stackB)
	{
		return stackB.getItem() == stackA.getItem() && (!stackA.getHasSubtypes() || stackA.getMetadata() == stackB.getMetadata()) && ItemStack.areItemStackTagsEqual(stackA, stackB);
	}

}
