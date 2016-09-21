package me.otho.metamods.core;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gson.JsonObject;

import me.otho.metamods.core.jsonReader.JsonHandler;
import me.otho.metamods.core.meta.CreativeTabHandler;
import me.otho.metamods.core.meta.RegisterCreativeTab;
import me.otho.metamods.core.registry.RegisterHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.FolderResourcePack;
import net.minecraft.client.resources.IResourcePack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

@Mod(modid = MmCore.MOD_ID, name = MmCore.MOD_NAME, version = MmCore.VERSION)
public class MmCore
{
    public static final String MOD_ID = "metamod-core";
    public static final String MOD_NAME = "MetaMod - Core";
    public static final String VERSION = "0.0.0";
    private static List<IResourcePack> defaultResourcePacks;
    
    private static File modConfigFolder;
    
    @Instance(MmCore.MOD_ID)
    public static MmCore instance;
    
    @EventHandler
    public void preinit (FMLPreInitializationEvent  event)
    {
    	modConfigFolder = event.getModConfigurationDirectory();
    	
    	CreativeTabHandler.initVanillaCreativeTabs();
    	MmCore.handleCustomResourcePack();    	
    	MmCore.addCoreMetaTypes();
    	MmCore.handleJsonConfiguration();
    }    
    
    private static void handleCustomResourcePack() {
    	defaultResourcePacks = ReflectionHelper.getPrivateValue(Minecraft.class, Minecraft.getMinecraft(), "defaultResourcePacks", "field_110449_ao", "ap");
    	String modResourcePackPath = modConfigFolder.toString() + File.separator + MmCore.MOD_ID + File.separator + "resources" + File.separator;
    	File modResourceFile = new File(modResourcePackPath);
    	if ( !modResourceFile.exists() ) {
    		modResourceFile.mkdirs();
    	}
    	defaultResourcePacks.add(new FolderResourcePack( modResourceFile ));
	}

	private static void addCoreMetaTypes() {
    	RegisterHandler.addRegisterType(MOD_ID + ".creativeTab", new RegisterCreativeTab() );
	}

	private static void handleJsonConfiguration() {
		// Get path of config files
    	String jsonConfigPath = modConfigFolder.toString() + File.separator + MmCore.MOD_ID + File.separator + "configs" + File.separator;
    	
    	// Get json folder
    	File jsonFolder = new File(jsonConfigPath); 
    	// Check if folder exists
    	if ( !jsonFolder.exists()) {
    		jsonFolder.mkdirs();
    	} else {
    		// Read Json files from path
    		ArrayList<JsonObject> jsonData = JsonHandler.read( jsonFolder );
    		
    		// Resolve prototype delegations
    		jsonData = JsonHandler.resolvePrototypeDelegations(jsonData);
    		
    		// Filter objects with no type
    		// Objects with no type are meant just as a data holder and can't be registered
    		
			Iterator<JsonObject> i = jsonData.iterator();
			while (i.hasNext()) {
				JsonObject s = i.next(); // must be called before you can call i.remove()
				if( !s.has("type") || (s.has("modelOnly") && s.get("modelOnly").getAsBoolean())) {
    				i.remove();
    			}
			}
    		
    		// Call stored registers
    		RegisterHandler.callRegisters(jsonData);	
    	}
	}

	@EventHandler
    public void init (FMLInitializationEvent event)
    {	
    	
    }
    
    @EventHandler
    public void postinit (FMLInitializationEvent event)
    {
    }
}
