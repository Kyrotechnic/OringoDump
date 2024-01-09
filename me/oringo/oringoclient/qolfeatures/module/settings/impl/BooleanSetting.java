//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.settings.impl;

import me.oringo.oringoclient.qolfeatures.module.settings.*;
import com.google.gson.annotations.*;
import java.util.function.*;

public class BooleanSetting extends Setting
{
    @Expose
    @SerializedName("value")
    private boolean enabled;
    
    public BooleanSetting(final String name, final boolean enabled) {
        super(name);
        this.enabled = enabled;
    }
    
    public BooleanSetting(final String name, final boolean enabled, final Predicate<Boolean> isHidden) {
        super(name, isHidden);
        this.enabled = enabled;
    }
    
    public boolean isEnabled() {
        return this.enabled;
    }
    
    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }
    
    public void toggle() {
        this.setEnabled(!this.isEnabled());
    }
}
