package me.otho.metamods.core.resourcePack;

import java.io.File;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.FolderResourcePack;
import net.minecraft.client.resources.IResourcePack;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class ResourcePackHandler {
	
	private static List<IResourcePack> defaultResourcePacks;
	private static File resourcesFolder;
	
	public static void init(String resourcesPath) {
		defaultResourcePacks = ReflectionHelper.getPrivateValue(Minecraft.class, Minecraft.getMinecraft(), "defaultResourcePacks", "field_110449_ao", "ap");
		
		resourcesFolder = new File( resourcesPath );
		if ( !resourcesFolder.exists() ) {
			resourcesFolder.mkdirs();
		}
		
		addFolderAsResourcePack(resourcesPath);
	}
	
	public static void addFolderAsResourcePack(String path) {
		File resourcePackFolder = new File(path);
		
		if ( resourcePackFolder.exists() ) {
	    	defaultResourcePacks.add(new FolderResourcePack( resourcePackFolder ));
		}
	}
}
