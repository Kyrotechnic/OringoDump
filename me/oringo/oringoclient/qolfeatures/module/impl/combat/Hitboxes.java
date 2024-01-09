//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.impl.combat;

import me.oringo.oringoclient.qolfeatures.module.*;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.*;
import me.oringo.oringoclient.qolfeatures.module.settings.*;

public class Hitboxes extends Module
{
    public BooleanSetting onlyPlayers;
    public NumberSetting expand;
    
    public Hitboxes() {
        super("Hitboxes", Category.COMBAT);
        this.onlyPlayers = new BooleanSetting("Only players", false);
        this.expand = new NumberSetting("Expand", 0.5, 0.1, 1.0, 0.1);
        this.addSettings(this.expand);
    }
}
