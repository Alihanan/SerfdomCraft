package pl.modding.objects.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import pl.modding.init.ItemInit;
import pl.modding.main.RPGMod;
import pl.modding.proxy.ClientProxy;

public class ItemBase extends Item{

	
	public ItemBase(String name) {
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		
		ItemInit.ITEMS.add(this);
	}

}
