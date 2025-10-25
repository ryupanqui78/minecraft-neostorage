package com.ryu.minecraft.mod.neoforge.neostorage.item;

import java.util.function.Consumer;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

public class UpgradeLevelItem extends Item {
    
    public static final String COPPER_ITEM = "upgrade_copper";
    public static final String IRON_ITEM = "upgrade_iron";
    public static final String EMERALD_ITEM = "upgrade_emerald";
    
    private final UpgradeTier upgradeTier;
    
    public UpgradeLevelItem(UpgradeTier upgradeTier, Properties properties) {
        super(properties);
        this.upgradeTier = upgradeTier;
    }
    
    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, TooltipDisplay pTooltipDisplay, Consumer<Component> pTooltip, TooltipFlag pTooltipFlag) {
        pTooltip.accept(Component.translatable("item.neostorage.upgrade_level.tooltip")
                .append(Component.literal((this.getUpgradeLevel() + 1) + "")).withStyle(ChatFormatting.GRAY));
    }
    
    public int getUpgradeLevel() {
        return this.upgradeTier.getLevel();
    }
    
    public UpgradeTier getUpgradeTier() {
        return this.upgradeTier;
    }
    
}
