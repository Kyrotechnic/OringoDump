//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.settings.impl;

import me.oringo.oringoclient.qolfeatures.module.settings.*;

public class StringSetting extends Setting
{
    private String value;
    int length;
    
    public StringSetting(final String name, final int length) {
        this(name, "", length);
    }
    
    public StringSetting(final String name, final String defaultValue) {
        this(name, defaultValue, -1);
    }
    
    public StringSetting(final String name) {
        this(name, "", -1);
    }
    
    public StringSetting(final String name, final String defaultValue, final int length) {
        super(name);
        this.value = defaultValue;
        this.length = length;
    }
    
    public boolean is(final String string) {
        return this.value.equals(string);
    }
    
    public String getValue() {
        return this.value;
    }
    
    public void setValue(final String value) {
        this.value = value;
        if (this.value.length() > this.length && this.length > 0) {
            this.value = this.value.substring(0, this.length - 1);
        }
    }
}
