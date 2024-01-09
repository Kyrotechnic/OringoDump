//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.impl.render;

import me.oringo.oringoclient.qolfeatures.module.*;
import net.minecraftforge.client.event.*;
import me.oringo.oringoclient.qolfeatures.module.impl.combat.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import me.oringo.oringoclient.utils.font.*;
import java.awt.*;
import me.oringo.oringoclient.utils.*;
import me.oringo.oringoclient.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class Nametags extends Module
{
    public Nametags() {
        super("Nametags", Category.RENDER);
    }
    
    @SubscribeEvent
    public void onRender(final RenderLivingEvent.Specials.Pre<EntityLivingBase> event) {
        if (this.isToggled() && AntiBot.isValidEntity((Entity)event.entity) && event.entity instanceof EntityPlayer && event.entity != Nametags.mc.thePlayer && event.entity.getDistanceToEntity((Entity)Nametags.mc.thePlayer) < 100.0f) {
            event.setCanceled(true);
            GlStateManager.alphaFunc(516, 0.1f);
            final String name = event.entity.getName();
            final double x = event.x;
            final double y = event.y;
            final double z = event.z;
            final float f = Math.max(1.4f, event.entity.getDistanceToEntity((Entity)Nametags.mc.thePlayer) / 10.0f);
            final float scale = 0.016666668f * f;
            GlStateManager.pushMatrix();
            GlStateManager.translate((float)x + 0.0f, (float)y + event.entity.height + 0.5f, (float)z);
            GL11.glNormal3f(0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(-Nametags.mc.getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(Nametags.mc.getRenderManager().playerViewX, 1.0f, 0.0f, 0.0f);
            GlStateManager.scale(-scale, -scale, scale);
            GlStateManager.disableLighting();
            GlStateManager.depthMask(false);
            GlStateManager.disableDepth();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            final float textWidth = (float)Math.max(Fonts.robotoMediumBold.getStringWidth(name) / 2.0, 30.0);
            GlStateManager.disableTexture2D();
            RenderUtils.drawRect(-textWidth - 3.0f, (float)(Fonts.robotoMediumBold.getHeight() + 3), textWidth + 3.0f, -3.0f, new Color(20, 20, 20, 80).getRGB());
            RenderUtils.drawRect(-textWidth - 3.0f, (float)(Fonts.robotoMediumBold.getHeight() + 3), (float)((textWidth + 3.0f) * ((MathUtil.clamp(event.entity.getHealth() / event.entity.getMaxHealth(), 1.0, 0.0) - 0.5) * 2.0)), (float)(Fonts.robotoMediumBold.getHeight() + 2), OringoClient.clickGui.getColor(event.entity.getEntityId()).getRGB());
            GlStateManager.enableTexture2D();
            Fonts.robotoMediumBold.drawSmoothString(name, -Fonts.robotoMediumBold.getStringWidth(name) / 2.0, 0.0f, Color.WHITE.getRGB());
            GlStateManager.enableDepth();
            GlStateManager.depthMask(true);
            Fonts.robotoMediumBold.drawSmoothString(name, -Fonts.robotoMediumBold.getStringWidth(name) / 2.0, 0.0f, Color.WHITE.getRGB());
            GlStateManager.enableLighting();
            GlStateManager.disableBlend();
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.popMatrix();
        }
    }
}
