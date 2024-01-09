//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.impl.render;

import me.oringo.oringoclient.qolfeatures.module.*;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.*;
import net.minecraft.entity.player.*;
import me.oringo.oringoclient.qolfeatures.module.settings.*;
import me.oringo.oringoclient.*;
import net.minecraft.entity.*;
import java.awt.*;
import java.util.*;
import me.oringo.oringoclient.events.*;
import net.minecraftforge.client.event.*;
import me.oringo.oringoclient.utils.*;
import net.minecraft.client.entity.*;
import net.minecraftforge.fml.common.eventhandler.*;
import me.oringo.oringoclient.qolfeatures.module.impl.combat.*;

public class PlayerESP extends Module
{
    public ModeSetting mode;
    public NumberSetting opacity;
    private EntityPlayer lastRendered;
    
    public PlayerESP() {
        super("PlayerESP", 0, Category.RENDER);
        this.mode = new ModeSetting("Mode", "2D", new String[] { "Outline", "2D", "Chams", "Box", "Tracers" });
        this.opacity = new NumberSetting("Opacity", 255.0, 0.0, 255.0, 1.0) {
            @Override
            public boolean isHidden() {
                return !PlayerESP.this.mode.is("Chams");
            }
        };
        this.addSettings(this.mode, this.opacity);
    }
    
    @SubscribeEvent
    public void onRender(final RenderWorldLastEvent event) {
        if (!this.isToggled() || (!this.mode.getSelected().equals("2D") && !this.mode.getSelected().equals("Box") && !this.mode.getSelected().equals("Tracers"))) {
            return;
        }
        final Color color = OringoClient.clickGui.getColor();
        for (final EntityPlayer entityPlayer : PlayerESP.mc.theWorld.playerEntities) {
            if (this.isValidEntity(entityPlayer) && entityPlayer != PlayerESP.mc.thePlayer) {
                final String selected = this.mode.getSelected();
                switch (selected) {
                    case "2D": {
                        RenderUtils.draw2D((Entity)entityPlayer, event.partialTicks, 1.0f, color);
                        continue;
                    }
                    case "Box": {
                        RenderUtils.entityESPBox((Entity)entityPlayer, event.partialTicks, color);
                        continue;
                    }
                    case "Tracers": {
                        RenderUtils.tracerLine((Entity)entityPlayer, event.partialTicks, 1.0f, color);
                        continue;
                    }
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onRender(final RenderLayersEvent event) {
        final Color color = OringoClient.clickGui.getColor();
        if (this.isToggled() && event.entity instanceof EntityPlayer && this.isValidEntity((EntityPlayer)event.entity) && event.entity != PlayerESP.mc.thePlayer && this.mode.getSelected().equals("Outline")) {
            OutlineUtils.outlineESP(event, color);
        }
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onRenderLiving(final RenderLivingEvent.Pre event) {
        if (this.lastRendered != null) {
            this.lastRendered = null;
            RenderUtils.disableChams();
            MobRenderUtils.unsetColor();
        }
        if (!(event.entity instanceof EntityOtherPlayerMP) || !this.mode.getSelected().equals("Chams") || !this.isToggled()) {
            return;
        }
        final Color color = RenderUtils.applyOpacity(OringoClient.clickGui.getColor(event.entity.getEntityId()), (int)this.opacity.getValue());
        RenderUtils.enableChams();
        MobRenderUtils.setColor(color);
        this.lastRendered = (EntityPlayer)event.entity;
    }
    
    @SubscribeEvent(receiveCanceled = true)
    public void onRenderLivingPost(final RenderLivingEvent.Specials.Pre event) {
        if (event.entity == this.lastRendered) {
            this.lastRendered = null;
            RenderUtils.disableChams();
            MobRenderUtils.unsetColor();
        }
    }
    
    private boolean isValidEntity(final EntityPlayer player) {
        return AntiBot.isValidEntity((Entity)player) && player.getHealth() > 0.0f && !player.isDead;
    }
}
