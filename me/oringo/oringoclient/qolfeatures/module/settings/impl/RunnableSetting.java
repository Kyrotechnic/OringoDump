//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.settings.impl;

import me.oringo.oringoclient.qolfeatures.module.settings.*;

public class RunnableSetting extends Setting
{
    private final Runnable runnable;
    
    public RunnableSetting(final String name, final Runnable runnable) {
        super(name);
        this.runnable = runnable;
    }
    
    public void execute() {
        this.runnable.run();
    }
}
