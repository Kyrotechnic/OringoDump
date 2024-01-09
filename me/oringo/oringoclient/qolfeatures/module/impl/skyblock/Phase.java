//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.impl.skyblock;

import me.oringo.oringoclient.qolfeatures.module.*;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.*;
import me.oringo.oringoclient.qolfeatures.module.settings.*;
import me.oringo.oringoclient.mixins.*;
import net.minecraftforge.fml.common.eventhandler.*;
import me.oringo.oringoclient.events.*;
import net.minecraftforge.client.event.*;
import net.minecraft.client.gui.*;
import me.oringo.oringoclient.utils.font.*;
import me.oringo.oringoclient.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.block.*;

public class Phase extends Module
{
    private int ticks;
    public NumberSetting timer;
    public ModeSetting activate;
    public BooleanSetting clip;
    public BooleanSetting barrierClip;
    public BooleanSetting floatTrolling;
    public boolean isPhasing;
    public boolean wasPressed;
    public boolean canPhase;
    private double lastY;
    
    public Phase() {
        super("Stair Phase", Category.SKYBLOCK);
        this.timer = new NumberSetting("Timer", 1.0, 0.1, 1.0, 0.1);
        this.activate = new ModeSetting("Activate", "on Key", new String[] { "Auto", "on Key", "Always" });
        this.clip = new BooleanSetting("Autoclip", true);
        this.barrierClip = new BooleanSetting("Barrier clip", true);
        this.floatTrolling = new BooleanSetting("Float", true);
        this.addSettings(this.timer, this.clip, this.activate, this.barrierClip, this.floatTrolling);
    }
    
    @Override
    public void onDisable() {
        this.isPhasing = false;
    }
    
    @SubscribeEvent
    public void onUpdate(final MotionUpdateEvent.Pre event) {
        if (Phase.mc.thePlayer == null || Phase.mc.theWorld == null) {
            return;
        }
        --this.ticks;
        if (this.isToggled()) {
            if (this.isPhasing) {
                ((MinecraftAccessor)Phase.mc).getTimer().timerSpeed = (float)this.timer.getValue();
            }
            if (Phase.mc.thePlayer.onGround) {
                this.lastY = Phase.mc.thePlayer.posY;
            }
            if (this.lastY == Phase.mc.thePlayer.posY && this.floatTrolling.isEnabled() && this.isPhasing) {
                Phase.mc.thePlayer.motionY = 0.0;
                Phase.mc.thePlayer.onGround = true;
            }
            this.canPhase = (Phase.mc.thePlayer.onGround && Phase.mc.thePlayer.isCollidedVertically && isValidBlock(Phase.mc.theWorld.getBlockState(new BlockPos(Phase.mc.thePlayer.posX, Phase.mc.thePlayer.posY, Phase.mc.thePlayer.posZ)).getBlock()));
            if (!this.isPhasing && (!this.isKeybind() || (this.isPressed() && !this.wasPressed)) && Phase.mc.thePlayer.onGround && Phase.mc.thePlayer.isCollidedVertically && isValidBlock(Phase.mc.theWorld.getBlockState(new BlockPos(Phase.mc.thePlayer.posX, Phase.mc.thePlayer.posY, Phase.mc.thePlayer.posZ)).getBlock())) {
                this.isPhasing = true;
                this.ticks = 8;
            }
            else if (this.isPhasing && ((!isInsideBlock() && this.ticks < 0) || (this.isPressed() && !this.wasPressed && this.isKeybind()))) {
                Phase.mc.thePlayer.setVelocity(0.0, 0.0, 0.0);
                this.isPhasing = false;
                ((MinecraftAccessor)Phase.mc).getTimer().timerSpeed = 1.0f;
            }
        }
        this.wasPressed = this.isPressed();
    }
    
    @SubscribeEvent
    public void onBlockBounds(final BlockBoundsEvent event) {
        if (Phase.mc.thePlayer == null || !this.isToggled()) {
            return;
        }
        if (event.block instanceof BlockBarrier && this.barrierClip.isEnabled() && ((event.aabb != null && event.aabb.maxY > Phase.mc.thePlayer.getEntityBoundingBox().minY) || Phase.mc.gameSettings.keyBindSneak.isKeyDown())) {
            event.setCanceled(true);
        }
        if ((this.isPhasing || this.activate.is("Always")) && event.collidingEntity == Phase.mc.thePlayer && ((event.aabb != null && event.aabb.maxY > Phase.mc.thePlayer.getEntityBoundingBox().minY) || Phase.mc.gameSettings.keyBindSneak.isKeyDown() || (this.ticks == 7 && this.clip.isEnabled()))) {
            event.setCanceled(true);
        }
    }
    
    @SubscribeEvent
    public void onRender(final RenderGameOverlayEvent.Post event) {
        if (Phase.mc.theWorld == null || Phase.mc.thePlayer == null || !this.isToggled()) {
            return;
        }
        if (this.canPhase && this.activate.is("on Key") && event.type == RenderGameOverlayEvent.ElementType.HOTBAR) {
            final ScaledResolution resolution = new ScaledResolution(Phase.mc);
            Fonts.robotoMediumBold.drawSmoothCenteredStringWithShadow("Phase usage detected", resolution.getScaledWidth() / 2.0f, resolution.getScaledHeight() - resolution.getScaledHeight() / 4.5f, OringoClient.clickGui.getColor().getRGB());
        }
    }
    
    public static boolean isInsideBlock() {
        for (int x = MathHelper.floor_double(Phase.mc.thePlayer.getEntityBoundingBox().minX); x < MathHelper.floor_double(Phase.mc.thePlayer.getEntityBoundingBox().maxX) + 1; ++x) {
            for (int y = MathHelper.floor_double(Phase.mc.thePlayer.getEntityBoundingBox().minY); y < MathHelper.floor_double(Phase.mc.thePlayer.getEntityBoundingBox().maxY) + 1; ++y) {
                for (int z = MathHelper.floor_double(Phase.mc.thePlayer.getEntityBoundingBox().minZ); z < MathHelper.floor_double(Phase.mc.thePlayer.getEntityBoundingBox().maxZ) + 1; ++z) {
                    final Block block = Phase.mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
                    if (block != null && !(block instanceof BlockAir)) {
                        final AxisAlignedBB boundingBox = block.getCollisionBoundingBox((World)Phase.mc.theWorld, new BlockPos(x, y, z), Phase.mc.theWorld.getBlockState(new BlockPos(x, y, z)));
                        if (boundingBox != null && Phase.mc.thePlayer.getEntityBoundingBox().intersectsWith(boundingBox)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    @Override
    public boolean isKeybind() {
        return this.activate.is("on Key");
    }
    
    private static boolean isValidBlock(final Block block) {
        return block instanceof BlockStairs || block instanceof BlockFence || block instanceof BlockFenceGate || block instanceof BlockWall || block == Blocks.hopper || block instanceof BlockSkull;
    }
}
