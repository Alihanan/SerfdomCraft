package pl.modding.init;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import pl.modding.objects.blocks.BlockBase;
import pl.modding.objects.items.ItemBase;

public class BlockInit {
	public static final List<Block> BLOCKS = new ArrayList<Block>();
	
	public static Block RUBY_BLOCK = new BlockBase("ruby_block", Material.IRON, 5.0f, 30.0f, 3, "pickaxe");
}
