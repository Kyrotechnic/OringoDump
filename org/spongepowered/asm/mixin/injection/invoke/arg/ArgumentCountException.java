//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package org.spongepowered.asm.mixin.injection.invoke.arg;

public class ArgumentCountException extends IllegalArgumentException
{
    private static final long serialVersionUID = 1L;
    
    public ArgumentCountException(final int received, final int expected, final String desc) {
        super("Invalid number of arguments for setAll, received " + received + " but expected " + expected + ": " + desc);
    }
}
