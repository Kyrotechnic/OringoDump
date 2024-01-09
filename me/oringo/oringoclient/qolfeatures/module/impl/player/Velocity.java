//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.impl.player;

import me.oringo.oringoclient.qolfeatures.module.*;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.*;
import me.oringo.oringoclient.qolfeatures.module.settings.*;

public class Velocity extends Module
{
    public NumberSetting vModifier;
    public NumberSetting hModifier;
    public BooleanSetting skyblockKB;
    
    public Velocity() {
        super("Velocity", 0, Category.PLAYER);
        this.vModifier = new NumberSetting("Vertical", 0.0, -2.0, 2.0, 0.05);
        this.hModifier = new NumberSetting("Horizontal", 0.0, -2.0, 2.0, 0.05);
        this.skyblockKB = new BooleanSetting("Skyblock kb", true);
        this.addSettings(this.hModifier, this.vModifier, this.skyblockKB);
    }
}
