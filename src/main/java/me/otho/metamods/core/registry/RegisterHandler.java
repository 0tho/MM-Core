package me.otho.metamods.core.registry;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.JsonObject;

public class RegisterHandler {

	private static HashMap<String, IRegister> registerRegistry = new HashMap<String, IRegister>();
	
	public static void addRegisterType (String type, IRegister register) {
		if ( registerRegistry.containsKey(type) ) {
			throw new Error ("Tried to register registerType: " + type + " more than once" );
		}
		
		registerRegistry.put(type, register);
	}
	
	public static void callRegisters( ArrayList<JsonObject> jsonData ) {
		for( JsonObject obj : jsonData ) {
			IRegister register = registerRegistry.get(obj.get("type").getAsString());
			
			register.register(obj);
		}
	}
}
