//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.settings;

import com.google.gson.annotations.*;
import java.util.function.*;

public class Setting
{
    @Expose
    @SerializedName("name")
    public String name;
    private Predicate<Boolean> predicate;
    
    protected Setting(final String name, final Predicate<Boolean> predicate) {
        this.name = name;
        this.predicate = predicate;
    }
    
    protected Setting(final String name) {
        this(name, null);
    }
    
    public boolean isHidden() {
        return this.predicate != null && this.predicate.test(true);
    }
}
