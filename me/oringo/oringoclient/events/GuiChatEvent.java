//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.events;

import net.minecraftforge.fml.common.eventhandler.*;

public class GuiChatEvent extends Event
{
    public int mouseX;
    public int mouseY;
    public int keyCode;
    public char keyChar;
    
    protected GuiChatEvent(final int mouseX, final int mouseY, final int keyCode, final char keyChar) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.keyCode = keyCode;
        this.keyChar = keyChar;
    }
    
    public static class DrawChatEvent extends GuiChatEvent
    {
        public DrawChatEvent(final int mouseX, final int mouseY) {
            super(mouseX, mouseY, -1, '\0');
        }
    }
    
    public static class KeyTyped extends GuiChatEvent
    {
        public KeyTyped(final int keyCode, final char keyChar) {
            super(0, 0, keyCode, keyChar);
        }
    }
    
    public static class MouseClicked extends GuiChatEvent
    {
        public MouseClicked(final int mouseX, final int mouseY, final int keyCode) {
            super(mouseX, mouseY, keyCode, '\0');
        }
    }
    
    public static class MouseReleased extends GuiChatEvent
    {
        public MouseReleased(final int mouseX, final int mouseY, final int keyCode) {
            super(mouseX, mouseY, keyCode, '\0');
        }
    }
    
    public static class Closed extends GuiChatEvent
    {
        public Closed() {
            super(0, 0, -1, '\0');
        }
    }
}
