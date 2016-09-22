package me.otho.metamods.core.meta;

import com.google.gson.JsonObject;

import me.otho.metamods.core.api.IMetaTypeRegister;
import me.otho.metamods.core.mod.MmCreativeTab;

public class MetaTypeCreativeTabRegister implements IMetaTypeRegister {

	@Override
	public void register(JsonObject obj) {
		String id = obj.get("id").getAsString();
		String iconItem = obj.get("iconItem").getAsString();
		
		CreativeTabHandler.addNewCreativeTab(id, new MmCreativeTab(iconItem, id));
	}

	@Override
	public int getPriority() {
		return 1000;
	}

}
