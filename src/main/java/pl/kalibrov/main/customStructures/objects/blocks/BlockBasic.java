package pl.kalibrov.main.customStructures.objects.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockBasic extends Block{

	public BlockBasic(String name, Material mat) {
		super(mat);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(CreativeTabs.MATERIALS);
	}
	
}
