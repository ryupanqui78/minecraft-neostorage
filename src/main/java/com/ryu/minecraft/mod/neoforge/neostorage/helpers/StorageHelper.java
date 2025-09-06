package com.ryu.minecraft.mod.neoforge.neostorage.helpers;

import java.util.function.Consumer;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.ryu.minecraft.mod.neoforge.neostorage.inventory.data.ItemStored;
import com.ryu.minecraft.mod.neoforge.neostorage.inventory.data.RendererItemData;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemDisplayContext;

public class StorageHelper {
    
    private static final int COLUMNS_PER_ROW = 9;
    private static final Font FONT = Minecraft.getInstance().font;
    private static final int SIZE_SLOT = 18;
    
    public static final Vector3f VECTOR_ZERO_VALUE = new Vector3f(0);
    
    public static void addDefaultInventorySlots(Inventory pInventory, int pSlotPosX, int pSlotHotbarPosY, int pSlotInventoryPosY, Consumer<Slot> pMenu) {
        int indexInventory = 0;
        
        for (int k = 0; k < StorageHelper.COLUMNS_PER_ROW; k++) {
            final int posX = pSlotPosX + (k * StorageHelper.SIZE_SLOT);
            pMenu.accept(new Slot(pInventory, indexInventory, posX, pSlotHotbarPosY));
            indexInventory++;
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < StorageHelper.COLUMNS_PER_ROW; j++) {
                final int posX = pSlotPosX + (j * StorageHelper.SIZE_SLOT);
                pMenu.accept(
                        new Slot(pInventory, indexInventory, posX, pSlotInventoryPosY + (i * StorageHelper.SIZE_SLOT)));
                indexInventory++;
            }
        }
    }
    
    public static Matrix4f createMatrix(Vector3f translation, Vector3f eulerDegrees, Vector3f scale) {
        final Matrix4f transform = new Matrix4f();
        final Quaternionf rotation = new Quaternionf().rotationXYZ((float) (eulerDegrees.x * (Math.PI / 180f)),
                (float) (eulerDegrees.y * (Math.PI / 180f)), (float) (eulerDegrees.z * (Math.PI / 180f)));
        transform.translation(translation).scale(scale).rotate(rotation);
        return transform;
    }
    
    public static void renderItemStack(ItemStored pItemStored, PoseStack pPoseStack, MultiBufferSource pBuffer, RendererItemData pRendererItemData) {
        pPoseStack.mulPose(StorageHelper.createMatrix(StorageHelper.VECTOR_ZERO_VALUE, StorageHelper.VECTOR_ZERO_VALUE,
                new Vector3f(.4f)));
        pPoseStack.mulPose(Axis.YP.rotationDegrees(180));
        Minecraft.getInstance().getItemRenderer().renderStatic(pItemStored.getItemStack(), ItemDisplayContext.FIXED,
                pRendererItemData.getLightInValue(), OverlayTexture.NO_WHITE_U, pPoseStack, pBuffer,
                pRendererItemData.getLevel(), 0);
        pPoseStack.mulPose(
                StorageHelper.createMatrix(StorageHelper.VECTOR_ZERO_VALUE, new Vector3f(0, 180, 0), new Vector3f(1)));
        pPoseStack.mulPose(StorageHelper.createMatrix(StorageHelper.VECTOR_ZERO_VALUE, StorageHelper.VECTOR_ZERO_VALUE,
                new Vector3f(.665f)));
    }
    
    public static void renderText(PoseStack pPoseStack, Component text, MultiBufferSource pBuffer, int combinedOverlayIn, float maxScale) {
        final float displayWidth = 1;
        final float displayHeight = 1;
        final int requiredWidth = Math.max(StorageHelper.FONT.width(text), 1);
        final int requiredHeight = StorageHelper.FONT.lineHeight + 2;
        final float scaler = 0.4F;
        final float scaleX = displayWidth / requiredWidth;
        
        float scale = scaleX * scaler;
        
        pPoseStack.translate(0, -1, 0.01);
        
        if (maxScale > 0) {
            scale = Math.min(scale, maxScale);
        }
        
        final int realHeight = (int) Math.floor(displayHeight / scale);
        final int realWidth = (int) Math.floor(displayWidth / scale);
        final int offsetX = (realWidth - requiredWidth) / 2;
        final int offsetY = 5 + ((realHeight - requiredHeight) / 2);
        final int xPos = offsetX - (realWidth / 2);
        final int yPos = offsetY - (realHeight / 2);
        
        pPoseStack.scale(scale, -scale, scale);
        StorageHelper.FONT.drawInBatch(text, xPos, yPos, combinedOverlayIn, false, pPoseStack.last().pose(), pBuffer,
                Font.DisplayMode.NORMAL, 0, 0xF000F0);
    }
    
    public static void updatePostionByDirection(Direction facing, Vector3f scale, PoseStack pPoseStack) {
        if (facing == Direction.NORTH) {
            final Vector3f eulerDegrees = new Vector3f(0);
            final Vector3f translation = new Vector3f(-1, 0, 0);
            pPoseStack.mulPose(StorageHelper.createMatrix(translation, eulerDegrees, scale));
        } else if (facing == Direction.EAST) {
            final Vector3f eulerDegrees = new Vector3f(0, -90, 0);
            final Vector3f translation = new Vector3f(-1, 0, -1);
            pPoseStack.mulPose(StorageHelper.createMatrix(translation, eulerDegrees, scale));
        } else if (facing == Direction.SOUTH) {
            final Vector3f eulerDegrees = new Vector3f(0, 180, 0);
            final Vector3f translation = new Vector3f(0, 0, -1);
            pPoseStack.mulPose(StorageHelper.createMatrix(translation, eulerDegrees, scale));
        } else if (facing == Direction.WEST) {
            final Vector3f eulerDegrees = new Vector3f(0, 90, 0);
            final Vector3f translation = new Vector3f(0, 0, 0);
            pPoseStack.mulPose(StorageHelper.createMatrix(translation, eulerDegrees, scale));
        }
    }
    
    private StorageHelper() {
    }
    
}
