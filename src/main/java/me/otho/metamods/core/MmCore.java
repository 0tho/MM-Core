package me.otho.metamods.core;

import me.otho.metamods.core.jsonreader.JsonHandler;
import me.otho.metamods.core.meta.CoreMetaTypesHandler;
import me.otho.metamods.core.meta.CreativeTabHandler;
import me.otho.metamods.core.meta.DataDumpingHandler;
import me.otho.metamods.core.resourcepack.ResourcePackHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

@Mod(modid = MmCore.MOD_ID, name = MmCore.MOD_NAME, version = MmCore.VERSION)
public class MmCore {
  public static final String MOD_ID = "metamod-core";
  public static final String MOD_NAME = "MetaMod - Core";
  public static final String VERSION = "0.0.0";

  private static File forgeModsConfigFolder;

  @Instance(MmCore.MOD_ID)
  public static MmCore instance;

  private static String modConfigPath;
  private static String metamodResourcePackPath;
  private static String metamodConfigsPath;
  private static String metamodDumpFilePath;

  @EventHandler
  public void preinit(FMLPreInitializationEvent event) {
    forgeModsConfigFolder = event.getModConfigurationDirectory();
    modConfigPath = forgeModsConfigFolder.toString() + File.separator + MmCore.MOD_ID
        + File.separator;
    metamodResourcePackPath = modConfigPath + "resources" + File.separator;
    metamodConfigsPath = modConfigPath + "configs" + File.separator;
    metamodDumpFilePath = modConfigPath + "DataDump.txt";

    CreativeTabHandler.initVanillaCreativeTabs();
    ResourcePackHandler.init(metamodResourcePackPath);
    CoreMetaTypesHandler.registerCoreMetaTypes();
    JsonHandler.handleJsonConfigurationFolder(metamodConfigsPath);
    DataDumpingHandler.dumpDataFile(metamodDumpFilePath);
  }

  @EventHandler
  public void init(FMLInitializationEvent event) {

  }

  @EventHandler
  public void postinit(FMLInitializationEvent event) {
  }

}
