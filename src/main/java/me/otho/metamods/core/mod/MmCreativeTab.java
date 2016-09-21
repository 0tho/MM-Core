package me.otho.metamods.core.mod;

import me.otho.metamods.core.MmCore;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class MmCreativeTab extends CreativeTabs {
	  public String iconItem;
	  protected String tabLabel;

	  public MmCreativeTab(String iconItem, String tabLabel) {
	    super(MmCore.MOD_ID + ":" + tabLabel);
	    
	    
	    this.iconItem = iconItem;
	    this.tabLabel = tabLabel;

	  }

	  @SuppressWarnings("deprecation")
	  @Override
	  public Item getTabIconItem() {
		 String[] parser = iconItem.split(":");
		 return GameRegistry.findItem(parser[0], parser[1]);
	  }

	  @Override
	  public String getTabLabel() {
	    return this.tabLabel;
	  };
	
	}
