//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package org.spongepowered.asm.mixin.gen;

import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.lib.*;

public abstract class AccessorGeneratorField extends AccessorGenerator
{
    protected final FieldNode targetField;
    protected final Type targetType;
    protected final boolean isInstanceField;
    
    public AccessorGeneratorField(final AccessorInfo info) {
        super(info);
        this.targetField = info.getTargetField();
        this.targetType = info.getTargetFieldType();
        this.isInstanceField = ((this.targetField.access & 0x8) == 0x0);
    }
}
