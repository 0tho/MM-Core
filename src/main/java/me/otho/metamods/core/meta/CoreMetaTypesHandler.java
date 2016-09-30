package me.otho.metamods.core.meta;

import me.otho.metamods.core.MmCore;
import me.otho.metamods.core.registry.RegisterHandler;

public class CoreMetaTypesHandler {

  public static void registerCoreMetaTypes() {
    RegisterHandler.addMetaTypeRegister(MmCore.MOD_ID + ".creativeTab",
        new MetaTypeCreativeTabRegister());
  }

}
