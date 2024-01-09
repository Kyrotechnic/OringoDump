//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.impl.render;

import me.oringo.oringoclient.qolfeatures.module.*;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.*;
import me.oringo.oringoclient.qolfeatures.module.settings.*;
import me.oringo.oringoclient.qolfeatures.module.impl.combat.*;
import me.oringo.oringoclient.*;
import me.oringo.oringoclient.utils.*;
import net.minecraftforge.fml.common.eventhandler.*;
import me.oringo.oringoclient.events.*;
import me.oringo.oringoclient.ui.hud.impl.*;
import net.minecraftforge.client.event.*;

public class TargetHUD extends Module
{
    public static TargetHUD instance;
    public ModeSetting blurStrength;
    public BooleanSetting targetESP;
    public NumberSetting x;
    public NumberSetting y;
    
    public static TargetHUD getInstance() {
        return TargetHUD.instance;
    }
    
    public TargetHUD() {
        super("Target HUD", Category.RENDER);
        this.blurStrength = new ModeSetting("Blur Strength", "Low", new String[] { "None", "Low", "High" });
        this.targetESP = new BooleanSetting("Target ESP", true);
        this.x = new NumberSetting("X123", 0.0, -100000.0, 100000.0, 1.0E-5, a -> true);
        this.y = new NumberSetting("Y123", 0.0, -100000.0, 100000.0, 1.0E-5, a -> true);
        this.setToggled(true);
        this.addSettings(this.targetESP, this.blurStrength, this.x, this.y);
    }
    
    @SubscribeEvent
    public void onRender(final RenderWorldLastEvent event) {
        if (KillAura.target != null && KillAura.target.getHealth() > 0.0f && !KillAura.target.isDead && this.targetESP.isEnabled() && this.isToggled()) {
            RenderUtils.drawTargetESP(KillAura.target, OringoClient.clickGui.getColor(), event.partialTicks);
        }
    }
    
    @SubscribeEvent
    public void onChatEvent(final GuiChatEvent event) {
        if (!this.isToggled()) {
            return;
        }
        final TargetComponent component = TargetComponent.INSTANCE;
        if (event instanceof GuiChatEvent.MouseClicked) {
            if (component.isHovered(event.mouseX, event.mouseY)) {
                component.startDragging();
            }
        }
        else if (event instanceof GuiChatEvent.MouseReleased || event instanceof GuiChatEvent.Closed) {
            component.stopDragging();
        }
    }
    
    @SubscribeEvent
    public void onRender(final RenderGameOverlayEvent.Pre event) {
        if (this.isToggled() && event.type == RenderGameOverlayEvent.ElementType.CROSSHAIRS) {
            TargetComponent.INSTANCE.drawScreen();
        }
    }
    
    static {
        TargetHUD.instance = new TargetHUD();
    }
}
