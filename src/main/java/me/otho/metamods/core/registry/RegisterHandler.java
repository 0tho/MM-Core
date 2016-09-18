package me.otho.metamods.core.registry;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.JsonObject;

public class RegisterHandler {

	private static HashMap<String, IRegister> registerRegistry = new HashMap<String, IRegister>();
	
	public static void addRegisterType ( String type, IRegister register ) {
		if ( registerRegistry.containsKey( type ) ) {
			throw new Error ("Tried to register registerType: " + type + " more than once" );
		}
		
		registerRegistry.put( type, register );
	}
	
	public static void callRegisters ( ArrayList<JsonObject> jsonData ) {
		for( JsonObject obj : jsonData ) {
			String id = obj.get("id").getAsString();
			String type = obj.get("type").getAsString();
			if ( !registerRegistry.containsKey( type ) )  {
				throw new Error("Object with id: " + id + " tried to register with type: " + type + ", but type was not found." );
			}
			
			IRegister register = registerRegistry.get( type );
			
			register.register(obj);
		}
	}
}
