//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.impl.combat;

import me.oringo.oringoclient.qolfeatures.module.*;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.*;
import me.oringo.oringoclient.qolfeatures.module.settings.*;

public class Reach extends Module
{
    public NumberSetting reach;
    public NumberSetting blockReach;
    
    public Reach() {
        super("Reach", 0, Category.COMBAT);
        this.reach = new NumberSetting("Range", 3.0, 2.0, 4.5, 0.1);
        this.blockReach = new NumberSetting("Block Range", 4.5, 2.0, 6.0, 0.01);
        this.addSettings(this.reach, this.blockReach);
    }
}
