//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.impl.player;

import me.oringo.oringoclient.qolfeatures.module.*;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.*;
import me.oringo.oringoclient.qolfeatures.module.settings.*;
import me.oringo.oringoclient.events.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import me.oringo.oringoclient.mixins.*;
import net.minecraft.client.gui.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class NoRotate extends Module
{
    public BooleanSetting keepMotion;
    public BooleanSetting pitch;
    
    public NoRotate() {
        super("No Rotate", 0, Category.PLAYER);
        this.keepMotion = new BooleanSetting("Keep motion", true);
        this.pitch = new BooleanSetting("0 pitch", false);
        this.addSettings(this.keepMotion, this.pitch);
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST, receiveCanceled = false)
    public void onPacket(final PacketReceivedEvent event) {
        if (event.packet instanceof S08PacketPlayerPosLook && this.isToggled() && NoRotate.mc.thePlayer != null && (((S08PacketPlayerPosLook)event.packet).getPitch() != 0.0 || this.pitch.isEnabled())) {
            event.setCanceled(true);
            final EntityPlayer entityplayer = (EntityPlayer)NoRotate.mc.thePlayer;
            double d0 = ((S08PacketPlayerPosLook)event.packet).getX();
            double d2 = ((S08PacketPlayerPosLook)event.packet).getY();
            double d3 = ((S08PacketPlayerPosLook)event.packet).getZ();
            float f = ((S08PacketPlayerPosLook)event.packet).getYaw();
            float f2 = ((S08PacketPlayerPosLook)event.packet).getPitch();
            if (((S08PacketPlayerPosLook)event.packet).func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.X)) {
                d0 += entityplayer.posX;
            }
            else if (!this.keepMotion.isEnabled()) {
                entityplayer.motionX = 0.0;
            }
            if (((S08PacketPlayerPosLook)event.packet).func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.Y)) {
                d2 += entityplayer.posY;
            }
            else {
                entityplayer.motionY = 0.0;
            }
            if (((S08PacketPlayerPosLook)event.packet).func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.Z)) {
                d3 += entityplayer.posZ;
            }
            else if (!this.keepMotion.isEnabled()) {
                entityplayer.motionZ = 0.0;
            }
            if (((S08PacketPlayerPosLook)event.packet).func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.X_ROT)) {
                f2 += entityplayer.rotationPitch;
            }
            if (((S08PacketPlayerPosLook)event.packet).func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.Y_ROT)) {
                f += entityplayer.rotationYaw;
            }
            entityplayer.setPosition(d0, d2, d3);
            NoRotate.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C03PacketPlayer.C06PacketPlayerPosLook(entityplayer.posX, entityplayer.getEntityBoundingBox().minY, entityplayer.posZ, f % 360.0f, f2 % 360.0f, false));
            if (!((NetPlayHandlerAccessor)NoRotate.mc.getNetHandler()).isDoneLoadingTerrain()) {
                NoRotate.mc.thePlayer.prevPosX = NoRotate.mc.thePlayer.posX;
                NoRotate.mc.thePlayer.prevPosY = NoRotate.mc.thePlayer.posY;
                NoRotate.mc.thePlayer.prevPosZ = NoRotate.mc.thePlayer.posZ;
                NoRotate.mc.displayGuiScreen((GuiScreen)null);
                ((NetPlayHandlerAccessor)NoRotate.mc.getNetHandler()).setDoneLoadingTerrain(true);
            }
        }
    }
}
