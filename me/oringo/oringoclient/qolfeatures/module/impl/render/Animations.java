//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.impl.render;

import me.oringo.oringoclient.qolfeatures.module.*;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.*;
import me.oringo.oringoclient.qolfeatures.module.settings.*;

public class Animations extends Module
{
    public NumberSetting x;
    public NumberSetting y;
    public NumberSetting z;
    public NumberSetting size;
    public ModeSetting mode;
    public BooleanSetting showSwing;
    
    public Animations() {
        super("Animations", Category.RENDER);
        this.x = new NumberSetting("x", 1.0, 0.01, 3.0, 0.02);
        this.y = new NumberSetting("y", 1.0, 0.01, 3.0, 0.02);
        this.z = new NumberSetting("z", 1.0, 0.01, 3.0, 0.02);
        this.size = new NumberSetting("size", 1.0, 0.01, 3.0, 0.02);
        this.mode = new ModeSetting("Mode", "1.7", new String[] { "1.7", "chill", "push", "spin", "vertical spin", "helicopter" });
        this.showSwing = new BooleanSetting("Swing progress", false);
        this.addSettings(this.x, this.y, this.z, this.size, this.mode, this.showSwing);
    }
}
