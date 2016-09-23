package me.otho.metamods.core.meta;

import me.otho.metamods.core.api.IMetaTypeRegister;
import me.otho.metamods.core.mod.MmCreativeTab;

public class MetaTypeCreativeTabRegister implements IMetaTypeRegister {
	
	class CreativeTab {
		public String id;
		public String iconItem = "minecraft:item_frame";
		
		public CreativeTab () {};
	}

	@Override
	public void register(Object obj) {
		
		CreativeTab data = (CreativeTab) obj;
		
		CreativeTabHandler.addNewCreativeTab(data.id, new MmCreativeTab(data.iconItem, data.id));
	}

	@Override
	public int getPriority() {
		return 1000;
	}

	@Override
	public Class<?> getReaderClass() {
		return CreativeTab.class;
	}

}
