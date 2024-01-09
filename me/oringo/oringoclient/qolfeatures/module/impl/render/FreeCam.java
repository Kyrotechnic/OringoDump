//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.impl.render;

import me.oringo.oringoclient.qolfeatures.module.*;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.*;
import me.oringo.oringoclient.qolfeatures.module.settings.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraft.client.entity.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.client.event.*;
import me.oringo.oringoclient.*;
import me.oringo.oringoclient.utils.*;
import net.minecraftforge.event.world.*;
import me.oringo.oringoclient.events.*;
import net.minecraft.network.play.client.*;

public class FreeCam extends Module
{
    private EntityOtherPlayerMP playerEntity;
    public NumberSetting speed;
    public BooleanSetting tracer;
    
    public FreeCam() {
        super("FreeCam", Category.RENDER);
        this.speed = new NumberSetting("Speed", 3.0, 0.1, 5.0, 0.1);
        this.tracer = new BooleanSetting("Show tracer", false);
        this.addSettings(this.speed, this.tracer);
    }
    
    @Override
    public void onEnable() {
        if (FreeCam.mc.theWorld != null) {
            (this.playerEntity = new EntityOtherPlayerMP((World)FreeCam.mc.theWorld, FreeCam.mc.thePlayer.getGameProfile())).copyLocationAndAnglesFrom((Entity)FreeCam.mc.thePlayer);
            this.playerEntity.onGround = FreeCam.mc.thePlayer.onGround;
            FreeCam.mc.theWorld.addEntityToWorld(-2137, (Entity)this.playerEntity);
        }
    }
    
    @Override
    public void onDisable() {
        if (FreeCam.mc.thePlayer == null || FreeCam.mc.theWorld == null || this.playerEntity == null) {
            return;
        }
        FreeCam.mc.thePlayer.noClip = false;
        FreeCam.mc.thePlayer.setPosition(this.playerEntity.posX, this.playerEntity.posY, this.playerEntity.posZ);
        FreeCam.mc.theWorld.removeEntityFromWorld(-2137);
        this.playerEntity = null;
        FreeCam.mc.thePlayer.setVelocity(0.0, 0.0, 0.0);
    }
    
    @SubscribeEvent
    public void onLivingUpdate(final LivingEvent.LivingUpdateEvent event) {
        if (this.isToggled()) {
            FreeCam.mc.thePlayer.noClip = true;
            FreeCam.mc.thePlayer.fallDistance = 0.0f;
            FreeCam.mc.thePlayer.onGround = false;
            FreeCam.mc.thePlayer.capabilities.isFlying = false;
            FreeCam.mc.thePlayer.motionY = 0.0;
            if (!MovementUtils.isMoving()) {
                FreeCam.mc.thePlayer.motionZ = 0.0;
                FreeCam.mc.thePlayer.motionX = 0.0;
            }
            final double speed = this.speed.getValue() * 0.1;
            FreeCam.mc.thePlayer.jumpMovementFactor = (float)speed;
            if (FreeCam.mc.gameSettings.keyBindJump.isKeyDown()) {
                final EntityPlayerSP thePlayer = FreeCam.mc.thePlayer;
                thePlayer.motionY += speed * 3.0;
            }
            if (FreeCam.mc.gameSettings.keyBindSneak.isKeyDown()) {
                final EntityPlayerSP thePlayer2 = FreeCam.mc.thePlayer;
                thePlayer2.motionY -= speed * 3.0;
            }
        }
    }
    
    @SubscribeEvent
    public void onRenderWorld(final RenderWorldLastEvent event) {
        if (this.isToggled() && this.playerEntity != null && this.tracer.isEnabled()) {
            RenderUtils.tracerLine((Entity)this.playerEntity, event.partialTicks, 1.0f, OringoClient.clickGui.getColor());
        }
    }
    
    @SubscribeEvent
    public void onWorldChange(final WorldEvent.Load event) {
        if (this.isToggled()) {
            this.toggle();
        }
    }
    
    @SubscribeEvent
    public void onPacket(final PacketSentEvent event) {
        if (this.isToggled() && event.packet instanceof C03PacketPlayer) {
            event.setCanceled(true);
        }
    }
}
