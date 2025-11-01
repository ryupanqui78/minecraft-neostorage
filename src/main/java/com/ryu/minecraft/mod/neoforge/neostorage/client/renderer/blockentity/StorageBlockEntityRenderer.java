package com.ryu.minecraft.mod.neoforge.neostorage.client.renderer.blockentity;

import java.util.List;

import org.joml.Vector3f;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ryu.minecraft.mod.neoforge.neostorage.blocks.AbstractStorageBlock;
import com.ryu.minecraft.mod.neoforge.neostorage.blocks.entities.AbstractStorageBlockEntity;
import com.ryu.minecraft.mod.neoforge.neostorage.helpers.StorageHelper;
import com.ryu.minecraft.mod.neoforge.neostorage.inventory.StorageMenu;
import com.ryu.minecraft.mod.neoforge.neostorage.inventory.data.ItemStored;
import com.ryu.minecraft.mod.neoforge.neostorage.inventory.data.RendererItemData;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.phys.Vec3;

public class StorageBlockEntityRenderer<T extends AbstractStorageBlockEntity> implements BlockEntityRenderer<T> {
    
    private final Font font;
    
    public StorageBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
        this.font = ctx.getFont();
    }
    
    @Override
    public void render(T blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay, Vec3 cameraPos) {
        final Vector3f scale = new Vector3f(1);
        final Direction facing = blockEntity.getBlockState().getValue(HorizontalDirectionalBlock.FACING);
        final int combinedLightIn = LevelRenderer.getLightColor(blockEntity.getLevel(),
                blockEntity.getBlockPos().relative(facing));
        final int levelSlots = blockEntity.getBlockState().getValue(AbstractStorageBlock.LEVEL);
        final List<ItemStored> items = blockEntity.getItemsStoredByCount();
        final ItemStack upgradeItem = blockEntity.getItem(StorageMenu.SLOT_UPGRADE);
        poseStack.pushPose();
        
        poseStack.mulPose(StorageHelper.createMatrix(new Vector3f(0), new Vector3f(0, 180, 0), scale));
        StorageHelper.updatePostionByDirection(facing, scale, poseStack);
        if (!upgradeItem.isEmpty()) {
            StorageHelper.renderUpgrade(blockEntity.getLevel(), upgradeItem, poseStack, bufferSource, combinedLightIn,
                    packedOverlay);
        }
        this.renderInformation(poseStack, bufferSource, blockEntity.getNumberFilledSlots(),
                blockEntity.getNumberTotalSlots());
        
        if (!items.isEmpty()) {
            poseStack.pushPose();
            if (levelSlots == 1) {
                this.render1Slot(blockEntity, items, poseStack, bufferSource, combinedLightIn, packedOverlay);
            }
            if (levelSlots == 2) {
                this.render2Slot(blockEntity, items, poseStack, bufferSource, combinedLightIn, packedOverlay);
            }
            if (levelSlots == 3) {
                this.render3Slot(blockEntity, items, poseStack, bufferSource, combinedLightIn, packedOverlay);
            }
            if (levelSlots == 4) {
                this.render4Slot(blockEntity, items, poseStack, bufferSource, combinedLightIn, packedOverlay);
            }
            poseStack.popPose();
        }
        poseStack.popPose();
    }
    
    private void render1Slot(T pBlockEntity, List<ItemStored> pItemsStored, PoseStack pPoseStack, MultiBufferSource pBuffer, int combinedLightIn, int combinedOverlayIn) {
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
    
    private void render2Slot(T pBlockEntity, List<ItemStored> pItemsStored, PoseStack pPoseStack, MultiBufferSource pBuffer, int combinedLightIn, int combinedOverlayIn) {
        final float maxTextScale = 0.05f;
        final RendererItemData itemData = new RendererItemData(combinedLightIn, combinedOverlayIn, maxTextScale,
                pBlockEntity.getLevel());
        
        this.renderSlotItem(pItemsStored.get(0), new Vector3f(0.5f, 0.80f, 0.0005f), pPoseStack, pBuffer, itemData);
        if (pItemsStored.size() > 1) {
            this.renderSlotItem(pItemsStored.get(1), new Vector3f(0.5f, 0.35f, 0.0005f), pPoseStack, pBuffer, itemData);
        }
    }
    
    private void render3Slot(T pBlockEntity, List<ItemStored> pItemsStored, PoseStack pPoseStack, MultiBufferSource pBuffer, int combinedLightIn, int combinedOverlayIn) {
        final float maxTextScale = 0.05f;
        final RendererItemData itemData = new RendererItemData(combinedLightIn, combinedOverlayIn, maxTextScale,
                pBlockEntity.getLevel());
        
        this.renderSlotItem(pItemsStored.get(0), new Vector3f(0.5f, 0.80f, 0.0005f), pPoseStack, pBuffer, itemData);
        if (pItemsStored.size() > 1) {
            this.renderSlotItem(pItemsStored.get(1), new Vector3f(0.25f, 0.35f, 0.0005f), pPoseStack, pBuffer,
                    itemData);
        }
        if (pItemsStored.size() > 2) {
            this.renderSlotItem(pItemsStored.get(2), new Vector3f(0.75f, 0.35f, 0.0005f), pPoseStack, pBuffer,
                    itemData);
        }
    }
    
    private void render4Slot(T pBlockEntity, List<ItemStored> pItemsStored, PoseStack pPoseStack, MultiBufferSource pBuffer, int combinedLightIn, int combinedOverlayIn) {
        final float maxTextScale = 0.05f;
        final RendererItemData itemData = new RendererItemData(combinedLightIn, combinedOverlayIn, maxTextScale,
                pBlockEntity.getLevel());
        
        this.renderSlotItem(pItemsStored.get(0), new Vector3f(0.25f, 0.80f, 0.0005f), pPoseStack, pBuffer, itemData);
        if (pItemsStored.size() > 1) {
            this.renderSlotItem(pItemsStored.get(1), new Vector3f(0.75f, 0.80f, 0.0005f), pPoseStack, pBuffer,
                    itemData);
        }
        if (pItemsStored.size() > 2) {
            this.renderSlotItem(pItemsStored.get(2), new Vector3f(0.25f, 0.35f, 0.0005f), pPoseStack, pBuffer,
                    itemData);
        }
        if (pItemsStored.size() > 3) {
            this.renderSlotItem(pItemsStored.get(3), new Vector3f(0.75f, 0.35f, 0.0005f), pPoseStack, pBuffer,
                    itemData);
        }
    }
    
    private void renderInformation(PoseStack poseStack, MultiBufferSource bufferSource, long pItemsStored, int pMaxItem) {
        final List<FormattedCharSequence> list = this.font
                .split(Component.translatable("text.storage.fill.information.literal", pItemsStored, pMaxItem), 100);
        final FormattedCharSequence formattedcharsequence = list.isEmpty() ? FormattedCharSequence.EMPTY : list.get(0);
        final Vec3 textOffset = new Vec3(0.5f, 0.092f, 0.01);
        final int requiredHeight = this.font.lineHeight + 2;
        final float scaleText = 0.007f;
        final int realSize = (int) Math.floor(1f / scaleText);
        final int offsetY = (realSize - requiredHeight) / 2;
        final float xPos = -this.font.width(formattedcharsequence) / 2f;
        final int yPos = (3 + offsetY) - (realSize / 2);
        final int color = DyeColor.WHITE.getTextColor();
        final int light = LightTexture.pack(15, 15);
        
        poseStack.pushPose();
        poseStack.translate(textOffset);
        poseStack.scale(scaleText, -scaleText, scaleText);
        
        this.font.drawInBatch(formattedcharsequence, xPos, yPos, color, false, poseStack.last().pose(), bufferSource,
                Font.DisplayMode.POLYGON_OFFSET, 0, light);
        
        poseStack.popPose();
    }
    
    private void renderSlotItem(ItemStored pItemStored, Vector3f pTranslation, PoseStack pPoseStack, MultiBufferSource pBuffer, RendererItemData pRendererItemData) {
        final ItemStack itemStack = pItemStored.getItemStack();
        
        if (!itemStack.isEmpty()) {
            pPoseStack.pushPose();
            pPoseStack.mulPose(StorageHelper.createMatrix(pTranslation, new Vector3f(0), new Vector3f(.5f, .5f, 1.0f)));
            StorageHelper.renderItemStack(pItemStored, pPoseStack, pBuffer, pRendererItemData);
            StorageHelper.renderText(pPoseStack,
                    Component.literal(ChatFormatting.WHITE + String.valueOf(pItemStored.getCount())), pBuffer,
                    pRendererItemData.getOverlayInValue(), pRendererItemData.getMaxScale());
            pPoseStack.popPose();
        }
    }
    
}
