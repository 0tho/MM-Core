package me.otho.metamods.core.resourcepack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.FolderResourcePack;
import net.minecraft.client.resources.IResourcePack;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.io.File;
import java.util.List;

public class ResourcePackHandler {

  private static List<IResourcePack> defaultResourcePacks;
  private static File resourcesFolder;

  /**
   * Initialize custom resourcepack folder at path.
   * @param resourcesPath Path for custom resourcepack
   */
  public static void init(String resourcesPath) {
    defaultResourcePacks = ReflectionHelper.getPrivateValue(Minecraft.class,
        Minecraft.getMinecraft(), "defaultResourcePacks", "field_110449_ao", "ap");

    resourcesFolder = new File(resourcesPath);
    if (!resourcesFolder.exists()) {
      resourcesFolder.mkdirs();
    }

    addFolderAsResourcePack(resourcesPath);
  }

  private static void addFolderAsResourcePack(String path) {
    File resourcePackFolder = new File(path);

    if (resourcePackFolder.exists()) {
      defaultResourcePacks.add(new FolderResourcePack(resourcePackFolder));
    }
  }
}
