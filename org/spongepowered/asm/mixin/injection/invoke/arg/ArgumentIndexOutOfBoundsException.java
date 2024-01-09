//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package org.spongepowered.asm.mixin.injection.invoke.arg;

public class ArgumentIndexOutOfBoundsException extends IndexOutOfBoundsException
{
    private static final long serialVersionUID = 1L;
    
    public ArgumentIndexOutOfBoundsException(final int index) {
        super("Argument index is out of bounds: " + index);
    }
}
