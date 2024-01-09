//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.impl.other;

import me.oringo.oringoclient.qolfeatures.module.*;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.*;
import me.oringo.oringoclient.qolfeatures.module.settings.*;
import net.minecraftforge.fml.common.gameevent.*;
import me.oringo.oringoclient.utils.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class Timer extends Module
{
    public static final NumberSetting timer;
    
    public Timer() {
        super("Timer", Category.OTHER);
        this.addSettings(Timer.timer);
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START && this.isToggled()) {
            TimerUtil.setSpeed((float)Timer.timer.getValue());
        }
    }
    
    @Override
    public void onDisable() {
        if (TimerUtil.getTimer() != null) {
            TimerUtil.resetSpeed();
        }
    }
    
    static {
        timer = new NumberSetting("Timer", 1.0, 0.1, 5.0, 0.1);
    }
}
