//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.impl.render;

import me.oringo.oringoclient.qolfeatures.module.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.gui.*;
import org.lwjgl.input.*;
import java.awt.*;
import me.oringo.oringoclient.utils.*;
import net.minecraftforge.client.*;
import java.util.concurrent.*;
import net.minecraft.util.*;
import net.minecraft.crash.*;
import me.oringo.oringoclient.events.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.client.*;

public class NoRender extends Module
{
    public NoRender() {
        super("NoRender", Category.RENDER);
    }
    
    @Override
    public void onEnable() {
        NoRender.mc.skipRenderWorld = true;
    }
    
    public static void drawGui() {
        if (NoRender.mc.skipRenderWorld && NoRender.mc.currentScreen != null) {
            NoRender.mc.setIngameNotInFocus();
            if (NoRender.mc.theWorld == null) {
                GlStateManager.viewport(0, 0, NoRender.mc.displayWidth, NoRender.mc.displayHeight);
                GlStateManager.matrixMode(5889);
                GlStateManager.loadIdentity();
                GlStateManager.matrixMode(5888);
                GlStateManager.loadIdentity();
                NoRender.mc.entityRenderer.setupOverlayRendering();
            }
            else {
                GlStateManager.alphaFunc(516, 0.1f);
                NoRender.mc.entityRenderer.setupOverlayRendering();
            }
            final ScaledResolution scaledresolution = new ScaledResolution(NoRender.mc);
            final int i1 = scaledresolution.getScaledWidth();
            final int j1 = scaledresolution.getScaledHeight();
            final int k1 = Mouse.getX() * i1 / NoRender.mc.displayWidth;
            final int l1 = j1 - Mouse.getY() * j1 / NoRender.mc.displayHeight - 1;
            GlStateManager.clear(256);
            RenderUtils.drawRect(0.0f, 0.0f, (float)i1, (float)j1, Color.black.getRGB());
            try {
                ForgeHooksClient.drawScreen(NoRender.mc.currentScreen, k1, l1, TimerUtil.getTimer().renderPartialTicks);
            }
            catch (Throwable throwable) {
                final CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Rendering screen");
                final CrashReportCategory crashreportcategory = crashreport.makeCategory("Screen render details");
                crashreportcategory.addCrashSectionCallable("Screen name", (Callable)new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        return NoRender.mc.currentScreen.getClass().getCanonicalName();
                    }
                });
                crashreportcategory.addCrashSectionCallable("Mouse location", (Callable)new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        return String.format("Scaled: (%d, %d). Absolute: (%d, %d)", k1, l1, Mouse.getX(), Mouse.getY());
                    }
                });
                crashreportcategory.addCrashSectionCallable("Screen size", (Callable)new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        return String.format("Scaled: (%d, %d). Absolute: (%d, %d). Scale factor of %d", scaledresolution.getScaledWidth(), scaledresolution.getScaledHeight(), NoRender.mc.displayWidth, NoRender.mc.displayHeight, scaledresolution.getScaleFactor());
                    }
                });
                throw new ReportedException(crashreport);
            }
        }
    }
    
    @SubscribeEvent
    public void onPostGui(final PostGuiOpenEvent event) {
        if (this.isToggled()) {
            NoRender.mc.skipRenderWorld = true;
        }
    }
    
    @Override
    public void onDisable() {
        NoRender.mc.skipRenderWorld = false;
    }
}
