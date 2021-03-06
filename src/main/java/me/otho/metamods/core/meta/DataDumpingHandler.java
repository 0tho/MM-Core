package me.otho.metamods.core.meta;

import me.otho.metamods.core.registry.RegisterHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityList;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

public class DataDumpingHandler {

  private static final String SEPARATOR = "===================================================\n\n";

  /**
   * Dump all in game ids to a text file.
   * 
   * @param path
   *          path for the dump file
   */
  public static void dumpDataFile(String path) {
    PrintWriter dumpingFile = null;
    try {
      dumpingFile = new PrintWriter(path);

      dumpMetaTypesIds(dumpingFile);
      dumpBlockIds(dumpingFile);
      dumpItemIds(dumpingFile);
      dumpBiomeIds(dumpingFile);
      dumpPotionIds(dumpingFile);
      dumpCreativeTabIds(dumpingFile);
      dumpEntitiesIds(dumpingFile);

      dumpingFile.close();
    } catch (FileNotFoundException ee) {
      ee.printStackTrace();
    }
  }

  private static void dumpPotionIds(PrintWriter dumpingFile) {
    // Dump potion ids
    dumpingFile.write("\nPotion ids \n");
    dumpingFile.write(SEPARATOR);

    Iterator<Potion> potionIter = ForgeRegistries.POTIONS.iterator();

    while (potionIter.hasNext()) {
      Potion potion = potionIter.next();

      String name = potion.getRegistryName().toString();

      dumpingFile.write(name + "\n");
    }

  }

  private static void dumpBlockIds(PrintWriter dumpingFile) {
    // Dump block ids
    dumpingFile.write("\nBlocks ids \n");
    dumpingFile.write(SEPARATOR);

    Iterator<Block> blockIter = ForgeRegistries.BLOCKS.iterator();
    while (blockIter.hasNext()) {
      Block block = blockIter.next();
      String name = block.getRegistryName().toString();

      dumpingFile.write(name + "\n");
    }
  }

  private static void dumpItemIds(PrintWriter dumpingFile) {
    // Dump item ids
    dumpingFile.write("\nItems ids \n");
    dumpingFile.write(SEPARATOR);

    Iterator<Item> itemIter = ForgeRegistries.ITEMS.iterator();
    while (itemIter.hasNext()) {
      Item item = itemIter.next();
      String name = item.getRegistryName().toString();

      dumpingFile.write(name + "\n");
    }
  }

  private static void dumpBiomeIds(PrintWriter dumpingFile) {
    // Dump biome ids
    dumpingFile.write("\nBiomes ids \n");
    dumpingFile.write(SEPARATOR);

    Iterator<Biome> itemIter = ForgeRegistries.BIOMES.iterator();
    while (itemIter.hasNext()) {
      Biome biome = itemIter.next();
      String name = biome.getBiomeName();
      int id = Biome.getIdForBiome(biome);

      dumpingFile.write("Id: " + id + " Name: " + name + "\n");
    }
  }

  private static void dumpEntitiesIds(PrintWriter dumpingFile) {
    // Dump entities ids
    dumpingFile.write("\nEntities ids \n");
    dumpingFile.write(SEPARATOR);

    List<String> entities = EntityList.getEntityNameList();
    Iterator<String> entityIter = entities.iterator();

    while (entityIter.hasNext()) {
      String name = entityIter.next();

      dumpingFile.write(name + "\n");
    }
  }

  private static void dumpCreativeTabIds(PrintWriter dumpingFile) {
    // Dump creativeTabs ids
    dumpingFile.write("\nCreative Tabs ids \n");
    dumpingFile.write(SEPARATOR);

    Iterator<String> creatveTabIter = CreativeTabHandler.getCreativeTabs().iterator();
    while (creatveTabIter.hasNext()) {
      String id = creatveTabIter.next();

      dumpingFile.write(id + "\n");
    }
  }

  private static void dumpMetaTypesIds(PrintWriter dumpingFile) {
    // Dump metatypes ids
    dumpingFile.write("\nMeta types ids \n");
    dumpingFile.write(SEPARATOR);

    Iterator<String> metaTypeIter = RegisterHandler.getKeys().iterator();
    while (metaTypeIter.hasNext()) {
      String id = metaTypeIter.next();

      dumpingFile.write(id + "\n");
    }
  }
}
