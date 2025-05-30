package com.ryu.minecraft.mod.neoforge.neostorage;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = NeoStorage.MODID, bus = EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    
    private static final ModConfigSpec.BooleanValue LOG_DIRT_BLOCK = Config.BUILDER
            .comment("Whether to log the dirt block on common setup").define("logDirtBlock", true);
    
    private static final ModConfigSpec.IntValue MAGIC_NUMBER = Config.BUILDER.comment("A magic number")
            .defineInRange("magicNumber", 42, 0, Integer.MAX_VALUE);
    
    public static final ModConfigSpec.ConfigValue<String> MAGIC_NUMBER_INTRODUCTION = Config.BUILDER
            .comment("What you want the introduction message to be for the magic number")
            .define("magicNumberIntroduction", "The magic number is... ");
    
    // a list of strings that are treated as resource locations for items
    private static final ModConfigSpec.ConfigValue<List<? extends String>> ITEM_STRINGS = Config.BUILDER
            .comment("A list of items to log on common setup.")
            .defineListAllowEmpty("items", List.of("minecraft:iron_ingot"), Config::validateItemName);
    
    static final ModConfigSpec SPEC = Config.BUILDER.build();
    
    public static boolean logDirtBlock;
    public static int magicNumber;
    public static String magicNumberIntroduction;
    public static Set<Item> items;
    
    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        Config.logDirtBlock = Config.LOG_DIRT_BLOCK.get();
        Config.magicNumber = Config.MAGIC_NUMBER.get();
        Config.magicNumberIntroduction = Config.MAGIC_NUMBER_INTRODUCTION.get();
        
        // convert the list of strings into a set of items
        Config.items = Config.ITEM_STRINGS.get().stream()
                .map(itemName -> BuiltInRegistries.ITEM.get(new ResourceLocation(itemName)))
                .collect(Collectors.toSet());
    }
    
    private static boolean validateItemName(final Object obj) {
        return obj instanceof final String itemName
                && BuiltInRegistries.ITEM.containsKey(new ResourceLocation(itemName));
    }
}
