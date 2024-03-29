//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package org.spongepowered.asm.mixin.injection.code;

import org.spongepowered.asm.mixin.injection.struct.*;
import org.spongepowered.asm.mixin.transformer.meta.*;
import org.spongepowered.asm.util.*;
import java.lang.annotation.*;
import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.mixin.injection.*;
import java.util.*;

public class InjectorTarget
{
    private final ISliceContext context;
    private final Map<String, ReadOnlyInsnList> cache;
    private final Target target;
    private final String mergedBy;
    private final int mergedPriority;
    
    public InjectorTarget(final ISliceContext context, final Target target) {
        this.cache = new HashMap<String, ReadOnlyInsnList>();
        this.context = context;
        this.target = target;
        final AnnotationNode merged = Annotations.getVisible(target.method, MixinMerged.class);
        this.mergedBy = Annotations.getValue(merged, "mixin");
        this.mergedPriority = Annotations.getValue(merged, "priority", 1000);
    }
    
    @Override
    public String toString() {
        return this.target.toString();
    }
    
    public Target getTarget() {
        return this.target;
    }
    
    public MethodNode getMethod() {
        return this.target.method;
    }
    
    public boolean isMerged() {
        return this.mergedBy != null;
    }
    
    public String getMergedBy() {
        return this.mergedBy;
    }
    
    public int getMergedPriority() {
        return this.mergedPriority;
    }
    
    public InsnList getSlice(final String id) {
        ReadOnlyInsnList slice = this.cache.get(id);
        if (slice == null) {
            final MethodSlice sliceInfo = this.context.getSlice(id);
            if (sliceInfo != null) {
                slice = sliceInfo.getSlice(this.target.method);
            }
            else {
                slice = new ReadOnlyInsnList(this.target.method.instructions);
            }
            this.cache.put(id, slice);
        }
        return slice;
    }
    
    public InsnList getSlice(final InjectionPoint injectionPoint) {
        return this.getSlice(injectionPoint.getSlice());
    }
    
    public void dispose() {
        for (final ReadOnlyInsnList insns : this.cache.values()) {
            insns.dispose();
        }
        this.cache.clear();
    }
}
