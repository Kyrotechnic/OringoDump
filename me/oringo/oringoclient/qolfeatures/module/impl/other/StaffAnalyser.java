//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.impl.other;

import me.oringo.oringoclient.qolfeatures.module.*;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.*;
import me.oringo.oringoclient.utils.*;
import me.oringo.oringoclient.qolfeatures.module.settings.*;
import net.minecraftforge.fml.common.gameevent.*;
import me.oringo.oringoclient.utils.api.*;
import me.oringo.oringoclient.ui.notifications.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class StaffAnalyser extends Module
{
    private NumberSetting delay;
    private MilliTimer timer;
    private int lastBans;
    
    public StaffAnalyser() {
        super("Staff Analyser", Category.OTHER);
        this.delay = new NumberSetting("Delay", 5.0, 5.0, 60.0, 1.0);
        this.timer = new MilliTimer();
        this.lastBans = -1;
        this.addSettings(this.delay);
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (this.isToggled() && this.timer.hasTimePassed((long)(this.delay.getValue() * 1000.0))) {
            this.timer.reset();
            final int bans;
            new Thread(() -> {
                bans = PlanckeScraper.getBans();
                if (bans != this.lastBans && this.lastBans != -1 && bans > this.lastBans) {
                    Notifications.showNotification(String.format("Staff has banned %s %s in last %s seconds", bans - this.lastBans, (bans - this.lastBans > 1) ? "people" : "person", (int)this.delay.getValue()), 2500, (bans - this.lastBans > 2) ? Notifications.NotificationType.WARNING : Notifications.NotificationType.INFO);
                }
                this.lastBans = bans;
            }).start();
        }
    }
}
