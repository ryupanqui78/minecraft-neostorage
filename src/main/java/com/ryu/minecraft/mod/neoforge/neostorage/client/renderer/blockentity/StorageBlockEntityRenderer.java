package com.ryu.minecraft.mod.neoforge.neostorage.client.renderer.blockentity;

import java.util.List;

import org.joml.Vector3f;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ryu.minecraft.mod.neoforge.neostorage.blocks.entities.ToolStorageBlockEntity;
import com.ryu.minecraft.mod.neoforge.neostorage.helpers.StorageHelper;
import com.ryu.minecraft.mod.neoforge.neostorage.inventory.data.ItemStored;
import com.ryu.minecraft.mod.neoforge.neostorage.inventory.data.RendererItemData;

import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class StorageBlockEntityRenderer implements BlockEntityRenderer<ToolStorageBlockEntity> {
    
    public StorageBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
    }
    
    @Override
    public void render(ToolStorageBlockEntity pBlockEntity, float partialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int packedLight, int pPackedOverlay) {
        final Vector3f scale = new Vector3f(1);
        final Direction facing = pBlockEntity.getBlockState().getValue(HorizontalDirectionalBlock.FACING);
        final int combinedLightIn = LevelRenderer.getLightColor(pBlockEntity.getLevel(), pBlockEntity.getBlockState(),
                pBlockEntity.getBlockPos().relative(facing));
        final List<ItemStored> items = pBlockEntity.getItemsStoredByCount();
        if (!items.isEmpty()) {
            pPoseStack.pushPose();
            pPoseStack.mulPose(StorageHelper.createMatrix(new Vector3f(0), new Vector3f(0, 180, 0), scale));
            StorageHelper.updatePostionByDirection(facing, scale, pPoseStack);
            StorageHelper.renderInformation(pPoseStack, pBuffer, pPackedOverlay, pBlockEntity.getNumberFilledSlots(),
                    pBlockEntity.getNumberTotalSlots());
            this.renderSlot(pBlockEntity, items, pPoseStack, pBuffer, combinedLightIn, pPackedOverlay);
            pPoseStack.popPose();
        }
    }
    
    private void renderSlot(ToolStorageBlockEntity pBlockEntity, List<ItemStored> pItemsStored, PoseStack pPoseStack, MultiBufferSource pBuffer, int combinedLightIn, int combinedOverlayIn) {
        final ItemStack itemStack = pItemsStored.get(0).getItemStack();
        final float maxTextScale = 0.04f;
        
        if (!itemStack.isEmpty()) {
            final RendererItemData itemData = new RendererItemData(combinedLightIn, combinedOverlayIn, maxTextScale,
                    pBlockEntity.getLevel());
            pPoseStack.translate(0.5, 0.55, 0.0005f);
            StorageHelper.renderItemStack(pItemsStored.get(0), pPoseStack, pBuffer, itemData);
            StorageHelper.renderText(pPoseStack,
                    Component.literal(ChatFormatting.WHITE + String.valueOf(pItemsStored.get(0).getCount())), pBuffer,
                    combinedOverlayIn, maxTextScale);
        }
    }
    
}
