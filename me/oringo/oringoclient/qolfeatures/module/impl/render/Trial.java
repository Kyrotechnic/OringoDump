//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.impl.render;

import me.oringo.oringoclient.qolfeatures.module.*;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.*;
import net.minecraft.util.*;
import me.oringo.oringoclient.qolfeatures.module.settings.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.client.event.*;
import org.lwjgl.opengl.*;
import me.oringo.oringoclient.*;
import java.awt.*;
import me.oringo.oringoclient.events.*;
import java.util.*;

public class Trial extends Module
{
    public static final NumberSetting count;
    private static final List<Vec3> vecs;
    
    public Trial() {
        super("Trail", Category.RENDER);
        this.addSettings(Trial.count);
    }
    
    @SubscribeEvent
    public void onUpdate(final PlayerUpdateEvent event) {
        if (this.isToggled()) {
            Trial.vecs.add(new Vec3(Trial.mc.thePlayer.prevPosX, Trial.mc.thePlayer.prevPosY + 0.1, Trial.mc.thePlayer.prevPosZ));
            while (Trial.vecs.size() > Trial.count.getValue()) {
                Trial.vecs.remove(0);
            }
        }
    }
    
    @SubscribeEvent
    public void onRenderWorld(final RenderWorldLastEvent event) {
        if (this.isToggled() && !Trial.vecs.isEmpty() && Trial.mc.thePlayer != null && Trial.mc.getRenderManager() != null) {
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(3042);
            GL11.glLineWidth(2.5f);
            GL11.glDisable(3553);
            GL11.glDisable(2884);
            GL11.glShadeModel(7425);
            GL11.glEnable(2848);
            GL11.glHint(3154, 4354);
            GL11.glBegin(3);
            int index = 0;
            for (final Vec3 vec : Trial.vecs) {
                final boolean isFirst = index == 0;
                ++index;
                final Color color = OringoClient.clickGui.getColor(index);
                GL11.glColor3f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f);
                if (isFirst && Trial.vecs.size() > 2) {
                    final Vec3 newVec = Trial.vecs.get(1);
                    GL11.glVertex3d(this.interpolate(vec.xCoord, newVec.xCoord, event.partialTicks) - Trial.mc.getRenderManager().viewerPosX, this.interpolate(vec.yCoord, newVec.yCoord, event.partialTicks) - Trial.mc.getRenderManager().viewerPosY, this.interpolate(vec.zCoord, newVec.zCoord, event.partialTicks) - Trial.mc.getRenderManager().viewerPosZ);
                }
                else {
                    GL11.glVertex3d(vec.xCoord - Trial.mc.getRenderManager().viewerPosX, vec.yCoord - Trial.mc.getRenderManager().viewerPosY, vec.zCoord - Trial.mc.getRenderManager().viewerPosZ);
                }
            }
            final Color color = OringoClient.clickGui.getColor(index);
            GL11.glColor3f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f);
            GL11.glVertex3d(Trial.mc.thePlayer.prevPosX + (Trial.mc.thePlayer.posX - Trial.mc.thePlayer.prevPosX) * event.partialTicks - Trial.mc.getRenderManager().viewerPosX, Trial.mc.thePlayer.prevPosY + (Trial.mc.thePlayer.posY - Trial.mc.thePlayer.prevPosY) * event.partialTicks - Trial.mc.getRenderManager().viewerPosY + 0.1, Trial.mc.thePlayer.prevPosZ + (Trial.mc.thePlayer.posZ - Trial.mc.thePlayer.prevPosZ) * event.partialTicks - Trial.mc.getRenderManager().viewerPosZ);
            GL11.glEnd();
            GL11.glEnable(3553);
            GL11.glShadeModel(7424);
            GL11.glEnable(2884);
            GL11.glDisable(2848);
            GL11.glDisable(2881);
            GL11.glDisable(3042);
        }
    }
    
    @SubscribeEvent
    public void onWorldJoin(final WorldJoinEvent event) {
        Trial.vecs.clear();
    }
    
    private double interpolate(final double prev, final double newPos, final float partialTicks) {
        return prev + (newPos - prev) * partialTicks;
    }
    
    private boolean hasMoved() {
        return Trial.mc.thePlayer.posZ - Trial.mc.thePlayer.prevPosZ != 0.0 || Trial.mc.thePlayer.posY - Trial.mc.thePlayer.prevPosY != 0.0 || Trial.mc.thePlayer.posX - Trial.mc.thePlayer.prevPosX != 0.0;
    }
    
    static {
        count = new NumberSetting("Points", 20.0, 5.0, 100.0, 1.0);
        vecs = new ArrayList<Vec3>();
    }
}
