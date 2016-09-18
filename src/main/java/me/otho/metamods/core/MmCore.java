package me.otho.metamods.core;

import java.io.File;
import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import me.otho.metamods.core.jsonReader.JsonHandler;
import me.otho.metamods.core.registry.RegisterHandler;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = MmCore.MOD_ID, version = MmCore.VERSION)
public class MmCore
{
    public static final String MOD_ID = "metamod-core";
    public static final String VERSION = "0.0.0";
    
    private static File modConfigFolder;
    
    @Instance(MmCore.MOD_ID)
    public static MmCore instance;
    
    @EventHandler
    public void preinit (FMLPreInitializationEvent  event)
    {
    	modConfigFolder = event.getModConfigurationDirectory();
    }    
    
    @EventHandler
    public void init (FMLInitializationEvent event)
    {	
    	// Wait until init so other mods can interact with mm-core
    	// Get path of config files
    	String modConfigPath = modConfigFolder.toString() + File.separator + MmCore.MOD_ID + File.separator;
    	String jsonConfigPath = modConfigPath + "configs" + File.separator;
    	// Mod Config Folder
    	File configsFolder = new File(modConfigPath);
    	// If it does not exist, make it
    	if ( !configsFolder.exists()) {
    		configsFolder.mkdir();
    	}
    	// Get json folder
    	File jsonFolder = new File(jsonConfigPath); 
    	// Check if folder exists
    	if ( !jsonFolder.exists()) {
    		jsonFolder.mkdir();
    	} else {
    		// Read Json files from path
    		ArrayList<JsonObject> jsonData = JsonHandler.read( jsonFolder );
    		
    		// Resolve prototype delegations
    		jsonData = JsonHandler.resolvePrototypeDelegations(jsonData);
    		
    		// Filter objects with no type
    		// Objects with no type are meant just as a data holder and can't be registered
    		for ( JsonObject obj : jsonData ) {
    			if( !obj.has("type") ) {
    				jsonData.remove(obj);
    			}
    		}
    		
    		// Call stored registers
    		RegisterHandler.callRegisters(jsonData);	
    	}    	
    }
    
    @EventHandler
    public void postinit (FMLInitializationEvent event)
    {
    }
}
