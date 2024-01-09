//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package org.spongepowered.asm.mixin.injection.throwables;

import org.spongepowered.asm.mixin.refmap.*;
import org.spongepowered.asm.mixin.injection.code.*;

public class InvalidSliceException extends InvalidInjectionException
{
    private static final long serialVersionUID = 1L;
    
    public InvalidSliceException(final IMixinContext context, final String message) {
        super(context, message);
    }
    
    public InvalidSliceException(final ISliceContext owner, final String message) {
        super(owner.getContext(), message);
    }
    
    public InvalidSliceException(final IMixinContext context, final Throwable cause) {
        super(context, cause);
    }
    
    public InvalidSliceException(final ISliceContext owner, final Throwable cause) {
        super(owner.getContext(), cause);
    }
    
    public InvalidSliceException(final IMixinContext context, final String message, final Throwable cause) {
        super(context, message, cause);
    }
    
    public InvalidSliceException(final ISliceContext owner, final String message, final Throwable cause) {
        super(owner.getContext(), message, cause);
    }
}
