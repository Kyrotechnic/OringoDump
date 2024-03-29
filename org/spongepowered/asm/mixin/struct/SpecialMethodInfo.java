//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package org.spongepowered.asm.mixin.struct;

import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.mixin.transformer.*;
import org.spongepowered.asm.mixin.refmap.*;

public abstract class SpecialMethodInfo implements IInjectionPointContext
{
    protected final AnnotationNode annotation;
    protected final ClassNode classNode;
    protected final MethodNode method;
    protected final MixinTargetContext mixin;
    
    public SpecialMethodInfo(final MixinTargetContext mixin, final MethodNode method, final AnnotationNode annotation) {
        this.mixin = mixin;
        this.method = method;
        this.annotation = annotation;
        this.classNode = mixin.getTargetClassNode();
    }
    
    public final IMixinContext getContext() {
        return (IMixinContext)this.mixin;
    }
    
    public final AnnotationNode getAnnotation() {
        return this.annotation;
    }
    
    public final ClassNode getClassNode() {
        return this.classNode;
    }
    
    public final MethodNode getMethod() {
        return this.method;
    }
}
