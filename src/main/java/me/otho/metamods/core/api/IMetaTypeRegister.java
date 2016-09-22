package me.otho.metamods.core.api;

import com.google.gson.JsonObject;

public interface IMetaTypeRegister {

	public void register (JsonObject obj);
	
	public int getPriority();
}
