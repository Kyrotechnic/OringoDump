//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package org.spongepowered.asm.mixin.injection.callback;

public enum LocalCapture
{
    NO_CAPTURE(false, false), 
    PRINT(false, true), 
    CAPTURE_FAILSOFT, 
    CAPTURE_FAILHARD, 
    CAPTURE_FAILEXCEPTION;
    
    private final boolean captureLocals;
    private final boolean printLocals;
    
    private LocalCapture() {
        this(true, false);
    }
    
    private LocalCapture(final boolean captureLocals, final boolean printLocals) {
        this.captureLocals = captureLocals;
        this.printLocals = printLocals;
    }
    
    boolean isCaptureLocals() {
        return this.captureLocals;
    }
    
    boolean isPrintLocals() {
        return this.printLocals;
    }
}
