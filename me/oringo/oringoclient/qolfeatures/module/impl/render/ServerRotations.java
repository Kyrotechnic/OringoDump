//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.impl.render;

import me.oringo.oringoclient.qolfeatures.module.*;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.*;
import me.oringo.oringoclient.qolfeatures.module.settings.*;

public class ServerRotations extends Module
{
    private static ServerRotations instance;
    public BooleanSetting onlyKillAura;
    
    public static ServerRotations getInstance() {
        return ServerRotations.instance;
    }
    
    public ServerRotations() {
        super("Server Rotations", Category.RENDER);
        this.onlyKillAura = new BooleanSetting("Only aura rotations", false);
        this.setToggled(true);
        this.addSettings(this.onlyKillAura);
    }
    
    static {
        ServerRotations.instance = new ServerRotations();
    }
}
