package me.otho.metamods.core.proxy;

import me.otho.metamods.core.resourcepack.ResourcePackHandler;

public class ClientProxy extends CommonProxy {
  
  public void handleResourcePackFolder(String path) {
    ResourcePackHandler.init(path);
  }

}
