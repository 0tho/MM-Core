# Metamod Core
This mod is a spiritual sucessor of [CustomItems](https://mods.curse.com/mc-mods/minecraft/224312-customitems)

MM-Core provides an api for other mods to read JSON config files and register their own customizations.

MM-Core reads all .json files from its configuration folder, performs some magic (so you can write less config files) and pass the data read to other mods that "own" it.

MM-Core also provides tools for knowing all block ids, biome ids, item ids, entities ids, metamod register types and effects ids.

## Configuring

### Json Files
All .json files are read from .../config/metamod-core/configs/*.json

Your json can be just a single object or an array of objects.
MM-Core handles by default 4 properties:

* "id" - Every object MUST have a unique identifier
* "type" - Types are used to know to who MM-Core should send this data. Ex: "metamod-items.item"
* "prototype" - This refers to another data object "id". See more info below.
* "onlyPrototype" - This defines if this data is just a model to be copied or real info

### Default Resource Pack
MM-Core reads .../config/metamod-core/resources/ as a default resourcePack ( it won't show in the list )

Now it is easier to add textures, models and translations

## Prototyping

This feature is based on [this](http://gameprogrammingpatterns.com/prototype.html#prototypes-for-data-modeling)

If you have an config file like this:

``` json
[
  {
    "id": "shinyIngot",
    "onlyPrototype": true,
    "type": "metamod-items.item",
    "maxStackSize": 16,
    "glows": true
  },
  {
    "id": "platinumIngot",
    "prototype": "shinyIngot"
  },
  {
    "id": "mithrilIngot",
    "prototype": "shinyIngot"
  },
  {
    "id": "uraniumIngot",
    "prototype": "shinyIngot",
    "maxStackSize": 8
  }
]
```

MM-Core will read it and transform it in:

``` json
[
  {
    "id": "platinumIngot",
    "type": "metamod-items.item",
    "maxStackSize": 16,
    "glows": true
  },
  {
    "id": "mithrilIngot",
    "type": "metamod-items.item",
    "maxStackSize": 16,
    "glows": true
  },
  {
    "id": "uraniumIngot",
    "type": "metamod-items.item",
    "maxStackSize": 8,
    "glows": true
  }
]
```

# Mods that are using MM-Core

* MM-Items (WIP)

# Contibuting

1. Use it
* Speak about it
* Report bugs
* Help with the code
* Buy me a beer
