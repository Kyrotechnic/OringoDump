//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package org.spongepowered.asm.util;

public class ReEntranceLock
{
    private final int maxDepth;
    private int depth;
    private boolean semaphore;
    
    public ReEntranceLock(final int maxDepth) {
        this.depth = 0;
        this.semaphore = false;
        this.maxDepth = maxDepth;
    }
    
    public int getMaxDepth() {
        return this.maxDepth;
    }
    
    public int getDepth() {
        return this.depth;
    }
    
    public ReEntranceLock push() {
        ++this.depth;
        this.checkAndSet();
        return this;
    }
    
    public ReEntranceLock pop() {
        if (this.depth == 0) {
            throw new IllegalStateException("ReEntranceLock pop() with zero depth");
        }
        --this.depth;
        return this;
    }
    
    public boolean check() {
        return this.depth > this.maxDepth;
    }
    
    public boolean checkAndSet() {
        return this.semaphore |= this.check();
    }
    
    public ReEntranceLock set() {
        this.semaphore = true;
        return this;
    }
    
    public boolean isSet() {
        return this.semaphore;
    }
    
    public ReEntranceLock clear() {
        this.semaphore = false;
        return this;
    }
}
