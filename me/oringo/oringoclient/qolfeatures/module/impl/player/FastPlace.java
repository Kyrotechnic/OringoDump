//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.impl.player;

import me.oringo.oringoclient.qolfeatures.module.*;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.*;
import me.oringo.oringoclient.qolfeatures.module.settings.*;

public class FastPlace extends Module
{
    private static FastPlace instance;
    public NumberSetting placeDelay;
    
    public static FastPlace getInstance() {
        return FastPlace.instance;
    }
    
    public FastPlace() {
        super("Fast Place", Category.PLAYER);
        this.addSetting(this.placeDelay = new NumberSetting("Place delay", 2.0, 0.0, 4.0, 1.0));
    }
    
    static {
        FastPlace.instance = new FastPlace();
    }
}
