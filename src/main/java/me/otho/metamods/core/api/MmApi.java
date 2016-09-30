package me.otho.metamods.core.api;

import me.otho.metamods.core.registry.RegisterHandler;

public class MmApi {

  public static void addMetaTypeRegister(String type, IMetaTypeRegister register) {
    RegisterHandler.addMetaTypeRegister(type, register);
  }

}
