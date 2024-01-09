//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.ui.hud;

public class DraggableComponent extends Component
{
    private double startX;
    private double startY;
    private boolean dragging;
    
    public boolean isDragging() {
        return this.dragging;
    }
    
    public void startDragging() {
        this.dragging = true;
        this.startX = this.x - getMouseX();
        this.startY = this.y - getMouseY();
    }
    
    public void stopDragging() {
        this.dragging = false;
    }
    
    public void onTick() {
    }
    
    public HudVec drawScreen() {
        if (this.dragging) {
            this.y = getMouseY() + this.startY;
            this.x = getMouseX() + this.startX;
        }
        return null;
    }
}
