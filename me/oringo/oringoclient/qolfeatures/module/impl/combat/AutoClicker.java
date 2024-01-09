//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.impl.combat;

import me.oringo.oringoclient.qolfeatures.module.*;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.*;
import me.oringo.oringoclient.qolfeatures.module.settings.*;
import net.minecraftforge.client.event.*;
import me.oringo.oringoclient.utils.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class AutoClicker extends Module
{
    public static final NumberSetting maxCps;
    public static final NumberSetting minCps;
    public static final ModeSetting mode;
    private MilliTimer timer;
    private double nextDelay;
    
    public AutoClicker() {
        super("AutoClicker", Category.COMBAT);
        this.timer = new MilliTimer();
        this.nextDelay = 10.0;
        this.addSettings(AutoClicker.minCps, AutoClicker.maxCps, AutoClicker.mode);
    }
    
    @SubscribeEvent
    public void onTick(final RenderWorldLastEvent event) {
        if (this.isToggled() && AutoClicker.mc.thePlayer != null && this.isPressed() && !AutoClicker.mc.thePlayer.isUsingItem() && AutoClicker.mc.currentScreen == null && this.timer.hasTimePassed((long)(1000.0 / this.nextDelay))) {
            this.timer.reset();
            this.nextDelay = MathUtil.getRandomInRange(AutoClicker.maxCps.getValue(), AutoClicker.minCps.getValue());
            SkyblockUtils.click();
        }
    }
    
    @Override
    public boolean isPressed() {
        final String selected = AutoClicker.mode.getSelected();
        switch (selected) {
            case "Key held": {
                return super.isPressed();
            }
            case "Toggle": {
                return this.isToggled();
            }
            default: {
                return AutoClicker.mc.gameSettings.keyBindAttack.isKeyDown();
            }
        }
    }
    
    @Override
    public boolean isKeybind() {
        return !AutoClicker.mode.is("Toggle");
    }
    
    static {
        maxCps = new NumberSetting("Max CPS", 12.0, 1.0, 20.0, 1.0) {
            @Override
            public void setValue(final double value) {
                super.setValue(value);
                AutoClicker.minCps.setMax(AutoClicker.maxCps.getValue());
                if (AutoClicker.minCps.getValue() > AutoClicker.minCps.getMax()) {
                    AutoClicker.minCps.setValue(AutoClicker.minCps.getMin());
                }
            }
        };
        minCps = new NumberSetting("Min CPS", 10.0, 1.0, 20.0, 1.0) {
            @Override
            public void setValue(final double value) {
                super.setValue(value);
                AutoClicker.maxCps.setMin(AutoClicker.minCps.getValue());
                if (AutoClicker.maxCps.getValue() < AutoClicker.maxCps.getMin()) {
                    AutoClicker.maxCps.setValue(AutoClicker.maxCps.getMin());
                }
            }
        };
        mode = new ModeSetting("Mode", "Attack held", new String[] { "Key held", "Toggle", "Attack held" });
    }
}
