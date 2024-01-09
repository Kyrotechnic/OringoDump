//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.events;

import net.minecraftforge.fml.common.eventhandler.*;

public class KeyPressEvent extends Event
{
    public int key;
    public char aChar;
    
    public KeyPressEvent(final int key, final char aChar) {
        this.key = key;
        this.aChar = aChar;
    }
}
