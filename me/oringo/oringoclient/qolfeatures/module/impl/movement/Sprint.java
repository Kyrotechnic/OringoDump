//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.impl.movement;

import me.oringo.oringoclient.qolfeatures.module.*;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.*;
import me.oringo.oringoclient.qolfeatures.module.settings.*;

public class Sprint extends Module
{
    public BooleanSetting omni;
    public BooleanSetting keep;
    
    public Sprint() {
        super("Sprint", 0, Category.MOVEMENT);
        this.omni = new BooleanSetting("OmniSprint", false);
        this.keep = new BooleanSetting("KeepSprint", true);
        this.addSettings(this.keep, this.omni);
    }
}
