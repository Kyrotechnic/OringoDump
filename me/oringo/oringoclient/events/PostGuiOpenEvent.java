//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.events;

import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.client.gui.*;

public class PostGuiOpenEvent extends Event
{
    public Gui gui;
    
    public PostGuiOpenEvent(final Gui gui) {
        this.gui = gui;
    }
}
