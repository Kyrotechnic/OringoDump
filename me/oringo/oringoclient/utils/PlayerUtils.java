//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.utils;

import me.oringo.oringoclient.*;
import net.minecraft.entity.player.*;
import java.util.function.*;
import net.minecraft.item.*;
import net.minecraft.inventory.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.block.*;
import me.oringo.oringoclient.mixins.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.potion.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

public class PlayerUtils
{
    public static boolean lastGround;
    
    private PlayerUtils() {
    }
    
    public static void swapToSlot(final int slot) {
        OringoClient.mc.thePlayer.inventory.currentItem = slot;
        syncHeldItem();
    }
    
    public static void numberClick(final int slot, final int button) {
        OringoClient.mc.playerController.windowClick(OringoClient.mc.thePlayer.inventoryContainer.windowId, slot, button, 2, (EntityPlayer)OringoClient.mc.thePlayer);
    }
    
    public static void shiftClick(final int slot) {
        OringoClient.mc.playerController.windowClick(OringoClient.mc.thePlayer.inventoryContainer.windowId, slot, 0, 1, (EntityPlayer)OringoClient.mc.thePlayer);
    }
    
    public static void drop(final int slot) {
        OringoClient.mc.playerController.windowClick(OringoClient.mc.thePlayer.inventoryContainer.windowId, slot, 1, 4, (EntityPlayer)OringoClient.mc.thePlayer);
    }
    
    public static int getHotbar(final Predicate<ItemStack> predicate) {
        for (int i = 0; i < 9; ++i) {
            if (OringoClient.mc.thePlayer.inventory.getStackInSlot(i) != null && predicate.test(OringoClient.mc.thePlayer.inventory.getStackInSlot(i))) {
                return i;
            }
        }
        return -1;
    }
    
    public static <T extends Item> int getHotbar(final Class<T> clazz) {
        return getHotbar(stack -> clazz.isAssignableFrom(stack.getItem().getClass()));
    }
    
    public static int getItem(final String name) {
        final List<Slot> slots = new ArrayList<Slot>(OringoClient.mc.thePlayer.inventoryContainer.inventorySlots);
        Collections.reverse(slots);
        for (final Slot slot : slots) {
            if (slot.getHasStack() && slot.getStack().getDisplayName().toLowerCase().contains(name.toLowerCase())) {
                return slot.slotNumber;
            }
        }
        return -1;
    }
    
    public static int getItem(final Predicate<ItemStack> predicate) {
        final List<Slot> slots = new ArrayList<Slot>(OringoClient.mc.thePlayer.inventoryContainer.inventorySlots);
        Collections.reverse(slots);
        for (final Slot slot : slots) {
            if (slot.getHasStack() && predicate.test(slot.getStack())) {
                return slot.slotNumber;
            }
        }
        return -1;
    }
    
    public static <T extends Item> int getItem(final Class<T> clazz) {
        final List<Slot> slots = new ArrayList<Slot>(OringoClient.mc.thePlayer.inventoryContainer.inventorySlots);
        Collections.reverse(slots);
        for (final Slot slot : slots) {
            if (slot.getHasStack() && clazz.isAssignableFrom(slot.getStack().getItem().getClass())) {
                return slot.slotNumber;
            }
        }
        return -1;
    }
    
    public static boolean isInsideBlock() {
        for (int x = MathHelper.floor_double(OringoClient.mc.thePlayer.getEntityBoundingBox().minX); x < MathHelper.floor_double(OringoClient.mc.thePlayer.getEntityBoundingBox().maxX) + 1; ++x) {
            for (int y = MathHelper.floor_double(OringoClient.mc.thePlayer.getEntityBoundingBox().minY); y < MathHelper.floor_double(OringoClient.mc.thePlayer.getEntityBoundingBox().maxY) + 1; ++y) {
                for (int z = MathHelper.floor_double(OringoClient.mc.thePlayer.getEntityBoundingBox().minZ); z < MathHelper.floor_double(OringoClient.mc.thePlayer.getEntityBoundingBox().maxZ) + 1; ++z) {
                    final Block block = OringoClient.mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
                    if (block != null && !(block instanceof BlockAir)) {
                        final AxisAlignedBB boundingBox = block.getCollisionBoundingBox((World)OringoClient.mc.theWorld, new BlockPos(x, y, z), OringoClient.mc.theWorld.getBlockState(new BlockPos(x, y, z)));
                        if (boundingBox != null && OringoClient.mc.thePlayer.getEntityBoundingBox().intersectsWith(boundingBox)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    public static Vec3 getVectorForRotation(final float yaw, final float pitch) {
        final float f = MathHelper.cos(-yaw * 0.017453292f - 3.1415927f);
        final float f2 = MathHelper.sin(-yaw * 0.017453292f - 3.1415927f);
        final float f3 = -MathHelper.cos(-pitch * 0.017453292f);
        final float f4 = MathHelper.sin(-pitch * 0.017453292f);
        return new Vec3((double)(f2 * f3), (double)f4, (double)(f * f3));
    }
    
    public static void syncHeldItem() {
        final int slot = OringoClient.mc.thePlayer.inventory.currentItem;
        if (slot != ((PlayerControllerAccessor)OringoClient.mc.playerController).getCurrentPlayerItem()) {
            ((PlayerControllerAccessor)OringoClient.mc.playerController).setCurrentPlayerItem(slot);
            PacketUtils.sendPacketNoEvent((Packet)new C09PacketHeldItemChange(slot));
        }
    }
    
    public static float getJumpMotion() {
        float motionY = 0.42f;
        if (OringoClient.mc.thePlayer.isPotionActive(Potion.jump)) {
            motionY += (OringoClient.mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1f;
        }
        return motionY;
    }
    
    public static float getFriction(final boolean onGround) {
        float f4 = 0.91f;
        if (onGround) {
            f4 = OringoClient.mc.thePlayer.worldObj.getBlockState(new BlockPos(MathHelper.floor_double(OringoClient.mc.thePlayer.posX), MathHelper.floor_double(OringoClient.mc.thePlayer.getEntityBoundingBox().minY) - 1, MathHelper.floor_double(OringoClient.mc.thePlayer.posZ))).getBlock().slipperiness * 0.91f;
        }
        final float f5 = 0.16277136f / (f4 * f4 * f4);
        float f6;
        if (onGround) {
            f6 = OringoClient.mc.thePlayer.getAIMoveSpeed() * f5;
        }
        else {
            f6 = OringoClient.mc.thePlayer.jumpMovementFactor;
        }
        return f6;
    }
    
    public static boolean isOnGround(final double height) {
        return !OringoClient.mc.theWorld.getCollidingBoundingBoxes((Entity)OringoClient.mc.thePlayer, OringoClient.mc.thePlayer.getEntityBoundingBox().offset(0.0, -height, 0.0)).isEmpty();
    }
    
    public static Vec3 getInterpolatedPos(final float partialTicks) {
        return new Vec3(interpolate(OringoClient.mc.thePlayer.prevPosX, OringoClient.mc.thePlayer.posX, partialTicks), interpolate(OringoClient.mc.thePlayer.prevPosY, OringoClient.mc.thePlayer.posY, partialTicks) + 0.1, interpolate(OringoClient.mc.thePlayer.prevPosZ, OringoClient.mc.thePlayer.posZ, partialTicks));
    }
    
    public static double interpolate(final double prev, final double newPos, final float partialTicks) {
        return prev + (newPos - prev) * partialTicks;
    }
    
    public static boolean isFall(final float distance) {
        return isFall(distance, 0.0, 0.0);
    }
    
    public static boolean isFall(final float distance, final double xOffset, final double zOffset) {
        final BlockPos block = new BlockPos(OringoClient.mc.thePlayer.posX, OringoClient.mc.thePlayer.posY, OringoClient.mc.thePlayer.posZ);
        if (!OringoClient.mc.theWorld.isBlockLoaded(block)) {
            return false;
        }
        final AxisAlignedBB player = OringoClient.mc.thePlayer.getEntityBoundingBox().offset(xOffset, 0.0, zOffset);
        return OringoClient.mc.theWorld.getCollidingBoundingBoxes((Entity)OringoClient.mc.thePlayer, new AxisAlignedBB(player.minX, player.minY - distance, player.minZ, player.maxX, player.maxY, player.maxZ)).isEmpty();
    }
    
    public static boolean isLiquid(final float distance) {
        return isLiquid(distance, 0.0, 0.0);
    }
    
    public static boolean isLiquid(final float distance, final double xOffset, final double zOffset) {
        final BlockPos block = new BlockPos(OringoClient.mc.thePlayer.posX, OringoClient.mc.thePlayer.posY, OringoClient.mc.thePlayer.posZ);
        if (!OringoClient.mc.theWorld.isBlockLoaded(block)) {
            return false;
        }
        final AxisAlignedBB player = OringoClient.mc.thePlayer.getEntityBoundingBox().offset(xOffset, 0.0, zOffset);
        return OringoClient.mc.theWorld.isAnyLiquid(new AxisAlignedBB(player.minX, player.minY - distance, player.minZ, player.maxX, player.maxY, player.maxZ));
    }
    
    public static boolean isOverVoid() {
        return isOverVoid(0.0, 0.0);
    }
    
    public static boolean isOverVoid(final double xOffset, final double zOffset) {
        final BlockPos block = new BlockPos(OringoClient.mc.thePlayer.posX, OringoClient.mc.thePlayer.posY, OringoClient.mc.thePlayer.posZ);
        if (!OringoClient.mc.theWorld.isBlockLoaded(block)) {
            return false;
        }
        final AxisAlignedBB player = OringoClient.mc.thePlayer.getEntityBoundingBox().offset(xOffset, 0.0, zOffset);
        return OringoClient.mc.theWorld.getCollidingBoundingBoxes((Entity)OringoClient.mc.thePlayer, new AxisAlignedBB(player.minX, 0.0, player.minZ, player.maxX, player.maxY, player.maxZ)).isEmpty();
    }
    
    public static MovingObjectPosition rayTrace(final float yaw, final float pitch, final float distance) {
        final Vec3 vec3 = OringoClient.mc.thePlayer.getPositionEyes(1.0f);
        final Vec3 vec4 = getVectorForRotation(yaw, pitch);
        final Vec3 vec5 = vec3.addVector(vec4.xCoord * distance, vec4.yCoord * distance, vec4.zCoord * distance);
        return OringoClient.mc.theWorld.rayTraceBlocks(vec3, vec5, false, true, true);
    }
}
