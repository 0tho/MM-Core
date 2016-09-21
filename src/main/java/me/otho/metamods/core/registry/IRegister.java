package me.otho.metamods.core.registry;

import com.google.gson.JsonObject;

public interface IRegister {

	public void register (JsonObject obj);
	
	public int getPriority();
}
