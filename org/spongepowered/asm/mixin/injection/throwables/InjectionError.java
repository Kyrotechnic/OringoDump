//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package org.spongepowered.asm.mixin.injection.throwables;

public class InjectionError extends Error
{
    private static final long serialVersionUID = 1L;
    
    public InjectionError() {
    }
    
    public InjectionError(final String message) {
        super(message);
    }
    
    public InjectionError(final Throwable cause) {
        super(cause);
    }
    
    public InjectionError(final String message, final Throwable cause) {
        super(message, cause);
    }
}
