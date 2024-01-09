//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.impl.other;

import me.oringo.oringoclient.qolfeatures.module.*;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.*;
import me.oringo.oringoclient.qolfeatures.module.settings.*;
import me.oringo.oringoclient.events.*;
import me.oringo.oringoclient.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import net.minecraftforge.fml.common.eventhandler.*;
import me.oringo.oringoclient.ui.notifications.*;

public class Disabler extends Module
{
    public static boolean wasEnabled;
    public static final BooleanSetting first;
    public static final BooleanSetting timerSemi;
    
    public Disabler() {
        super("Disabler", Category.OTHER);
        this.addSettings(Disabler.timerSemi, Disabler.first);
    }
    
    @SubscribeEvent
    public void onUpdate(final PlayerUpdateEvent e) {
        if (!Disabler.mc.thePlayer.isSneaking() && !Disabler.mc.thePlayer.isRiding() && this.isToggled() && ((OringoClient.sprint.omni.isEnabled() && OringoClient.sprint.isToggled()) || OringoClient.derp.isToggled() || OringoClient.killAura.isToggled() || OringoClient.speed.isToggled())) {
            Disabler.mc.getNetHandler().addToSendQueue((Packet)new C0BPacketEntityAction((Entity)Disabler.mc.thePlayer, C0BPacketEntityAction.Action.START_SNEAKING));
            Disabler.mc.getNetHandler().addToSendQueue((Packet)new C0BPacketEntityAction((Entity)Disabler.mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
        }
    }
    
    @Override
    public void onEnable() {
        Notifications.showNotification("Disabler will work after lobby change", 2000, Notifications.NotificationType.WARNING);
    }
    
    static {
        Disabler.wasEnabled = false;
        first = new BooleanSetting("First2", true, aBoolean -> true);
        timerSemi = new BooleanSetting("Timer semi", false);
    }
}
