//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.settings.impl;

import me.oringo.oringoclient.qolfeatures.module.settings.*;
import com.google.gson.annotations.*;
import java.util.function.*;

public class NumberSetting extends Setting
{
    double min;
    double max;
    double increment;
    @Expose
    @SerializedName("value")
    private double value;
    
    public NumberSetting(final String name, final double defaultValue, final double minimum, final double maximum, final double increment) {
        super(name);
        this.value = defaultValue;
        this.min = minimum;
        this.max = maximum;
        this.increment = increment;
    }
    
    public NumberSetting(final String name, final double defaultValue, final double min, final double max, final double increment, final Predicate<Boolean> isHidden) {
        super(name, isHidden);
        this.value = defaultValue;
        this.min = min;
        this.max = max;
        this.increment = increment;
    }
    
    public static double clamp(double value, final double min, final double max) {
        value = Math.max(min, value);
        value = Math.min(max, value);
        return value;
    }
    
    public double getValue() {
        return this.value;
    }
    
    public void setValue(double value) {
        value = clamp(value, this.getMin(), this.getMax());
        value = Math.round(value * (1.0 / this.getIncrement())) / (1.0 / this.getIncrement());
        this.value = value;
    }
    
    public void set(final double value) {
        this.value = value;
    }
    
    public double getMin() {
        return this.min;
    }
    
    public void setMin(final double min) {
        this.min = min;
    }
    
    public double getMax() {
        return this.max;
    }
    
    public void setMax(final double max) {
        this.max = max;
    }
    
    public double getIncrement() {
        return this.increment;
    }
    
    public void setIncrement(final double increment) {
        this.increment = increment;
    }
}
