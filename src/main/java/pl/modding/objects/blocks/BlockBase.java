package pl.modding.objects.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import pl.modding.init.BlockInit;
import pl.modding.init.ItemInit;

public class BlockBase extends Block{
	
	public BlockBase(String name, Material material, float hardness, float resistance, int miningLevel, String tool) {
		super(material);
		
		setUnlocalizedName(name);
		setRegistryName(name);
		
		setHardness(hardness);
		setResistance(resistance);
		setHarvestLevel(tool, miningLevel);
		
		setCreativeTab(CreativeTabs.MATERIALS);
		
		BlockInit.BLOCKS.add(this);
		ItemInit.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}
}
