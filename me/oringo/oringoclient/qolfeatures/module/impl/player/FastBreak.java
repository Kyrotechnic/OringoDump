//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.impl.player;

import me.oringo.oringoclient.qolfeatures.module.*;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.*;
import me.oringo.oringoclient.qolfeatures.module.settings.*;

public class FastBreak extends Module
{
    public NumberSetting mineSpeed;
    public NumberSetting maxBlocks;
    
    public FastBreak() {
        super("Fast break", 0, Category.PLAYER);
        this.mineSpeed = new NumberSetting("Mining speed", 1.4, 1.0, 1.6, 0.1);
        this.maxBlocks = new NumberSetting("Additional blocks", 0.0, 0.0, 4.0, 1.0);
        this.addSettings(this.maxBlocks, this.mineSpeed);
    }
}
