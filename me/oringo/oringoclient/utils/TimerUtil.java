//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.utils;

import net.minecraft.util.*;
import me.oringo.oringoclient.mixins.*;
import me.oringo.oringoclient.*;

public class TimerUtil
{
    public static void setSpeed(final float speed) {
        getTimer().timerSpeed = speed;
    }
    
    public static void resetSpeed() {
        setSpeed(1.0f);
    }
    
    public static Timer getTimer() {
        return ((MinecraftAccessor)OringoClient.mc).getTimer();
    }
}
