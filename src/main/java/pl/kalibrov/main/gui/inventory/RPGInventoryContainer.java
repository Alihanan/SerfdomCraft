package pl.kalibrov.main.gui.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

import net.minecraft.enchantment.EnchantmentHelper;


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
	
			int containerSlots = inventorySlots.size() - playerIn.inventory.mainInventory.size();
	
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
	
			slot.onTake(playerIn, itemstack1);
		}
	
		return itemstack;
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}
	private static boolean areItemStacksEqual(ItemStack stackA, ItemStack stackB)
	{
		return stackB.getItem() == stackA.getItem() && (!stackA.getHasSubtypes() || stackA.getMetadata() == stackB.getMetadata()) && ItemStack.areItemStackTagsEqual(stackA, stackB);
	}

}
