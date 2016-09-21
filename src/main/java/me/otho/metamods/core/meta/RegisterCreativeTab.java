package me.otho.metamods.core.meta;

import com.google.gson.JsonObject;

import me.otho.metamods.core.mod.MmCreativeTab;
import me.otho.metamods.core.registry.IRegister;

public class RegisterCreativeTab implements IRegister {

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
