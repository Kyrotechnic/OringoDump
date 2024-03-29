//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.mixin.transformer.ext.*;
import org.spongepowered.asm.mixin.struct.*;
import org.spongepowered.asm.mixin.injection.struct.*;
import java.util.*;
import org.spongepowered.asm.mixin.*;
import java.lang.annotation.*;
import org.spongepowered.asm.util.*;
import java.io.*;
import org.spongepowered.asm.lib.tree.*;
import org.apache.logging.log4j.*;

class TargetClassContext extends ClassContext implements ITargetClassContext
{
    private static final Logger logger;
    private final MixinEnvironment env;
    private final Extensions extensions;
    private final String sessionId;
    private final String className;
    private final ClassNode classNode;
    private final ClassInfo classInfo;
    private final SourceMap sourceMap;
    private final ClassSignature signature;
    private final SortedSet<MixinInfo> mixins;
    private final Map<String, Target> targetMethods;
    private final Set<MethodNode> mixinMethods;
    private int nextUniqueMethodIndex;
    private int nextUniqueFieldIndex;
    private boolean applied;
    private boolean forceExport;
    
    TargetClassContext(final MixinEnvironment env, final Extensions extensions, final String sessionId, final String name, final ClassNode classNode, final SortedSet<MixinInfo> mixins) {
        this.targetMethods = new HashMap<String, Target>();
        this.mixinMethods = new HashSet<MethodNode>();
        this.env = env;
        this.extensions = extensions;
        this.sessionId = sessionId;
        this.className = name;
        this.classNode = classNode;
        this.classInfo = ClassInfo.fromClassNode(classNode);
        this.signature = this.classInfo.getSignature();
        this.mixins = mixins;
        (this.sourceMap = new SourceMap(classNode.sourceFile)).addFile(this.classNode);
    }
    
    public String toString() {
        return this.className;
    }
    
    boolean isApplied() {
        return this.applied;
    }
    
    boolean isExportForced() {
        return this.forceExport;
    }
    
    Extensions getExtensions() {
        return this.extensions;
    }
    
    String getSessionId() {
        return this.sessionId;
    }
    
    String getClassRef() {
        return this.classNode.name;
    }
    
    String getClassName() {
        return this.className;
    }
    
    public ClassNode getClassNode() {
        return this.classNode;
    }
    
    List<MethodNode> getMethods() {
        return (List<MethodNode>)this.classNode.methods;
    }
    
    List<FieldNode> getFields() {
        return (List<FieldNode>)this.classNode.fields;
    }
    
    public ClassInfo getClassInfo() {
        return this.classInfo;
    }
    
    SortedSet<MixinInfo> getMixins() {
        return this.mixins;
    }
    
    SourceMap getSourceMap() {
        return this.sourceMap;
    }
    
    void mergeSignature(final ClassSignature signature) {
        this.signature.merge(signature);
    }
    
    void addMixinMethod(final MethodNode method) {
        this.mixinMethods.add(method);
    }
    
    void methodMerged(final MethodNode method) {
        if (!this.mixinMethods.remove(method)) {
            TargetClassContext.logger.debug("Unexpected: Merged unregistered method {}{} in {}", new Object[] { method.name, method.desc, this });
        }
    }
    
    MethodNode findMethod(final Deque<String> aliases, final String desc) {
        return this.findAliasedMethod(aliases, desc, true);
    }
    
    MethodNode findAliasedMethod(final Deque<String> aliases, final String desc) {
        return this.findAliasedMethod(aliases, desc, false);
    }
    
    private MethodNode findAliasedMethod(final Deque<String> aliases, final String desc, final boolean includeMixinMethods) {
        final String alias = aliases.poll();
        if (alias == null) {
            return null;
        }
        for (final MethodNode target : this.classNode.methods) {
            if (target.name.equals(alias) && target.desc.equals(desc)) {
                return target;
            }
        }
        if (includeMixinMethods) {
            for (final MethodNode target : this.mixinMethods) {
                if (target.name.equals(alias) && target.desc.equals(desc)) {
                    return target;
                }
            }
        }
        return this.findAliasedMethod(aliases, desc);
    }
    
    FieldNode findAliasedField(final Deque<String> aliases, final String desc) {
        final String alias = aliases.poll();
        if (alias == null) {
            return null;
        }
        for (final FieldNode target : this.classNode.fields) {
            if (target.name.equals(alias) && target.desc.equals(desc)) {
                return target;
            }
        }
        return this.findAliasedField(aliases, desc);
    }
    
    Target getTargetMethod(final MethodNode method) {
        if (!this.classNode.methods.contains(method)) {
            throw new IllegalArgumentException("Invalid target method supplied to getTargetMethod()");
        }
        final String targetName = method.name + method.desc;
        Target target = this.targetMethods.get(targetName);
        if (target == null) {
            target = new Target(this.classNode, method);
            this.targetMethods.put(targetName, target);
        }
        return target;
    }
    
    String getUniqueName(final MethodNode method, final boolean preservePrefix) {
        final String uniqueIndex = Integer.toHexString(this.nextUniqueMethodIndex++);
        final String pattern = preservePrefix ? "%2$s_$md$%1$s$%3$s" : "md%s$%s$%s";
        return String.format(pattern, this.sessionId.substring(30), method.name, uniqueIndex);
    }
    
    String getUniqueName(final FieldNode field) {
        final String uniqueIndex = Integer.toHexString(this.nextUniqueFieldIndex++);
        return String.format("fd%s$%s$%s", this.sessionId.substring(30), field.name, uniqueIndex);
    }
    
    void applyMixins() {
        if (this.applied) {
            throw new IllegalStateException("Mixins already applied to target class " + this.className);
        }
        this.applied = true;
        final MixinApplicatorStandard applicator = this.createApplicator();
        applicator.apply((SortedSet)this.mixins);
        this.applySignature();
        this.upgradeMethods();
        this.checkMerges();
    }
    
    private MixinApplicatorStandard createApplicator() {
        if (this.classInfo.isInterface()) {
            return (MixinApplicatorStandard)new MixinApplicatorInterface(this);
        }
        return new MixinApplicatorStandard(this);
    }
    
    private void applySignature() {
        this.getClassNode().signature = this.signature.toString();
    }
    
    private void checkMerges() {
        for (final MethodNode method : this.mixinMethods) {
            if (!method.name.startsWith("<")) {
                TargetClassContext.logger.debug("Unexpected: Registered method {}{} in {} was not merged", new Object[] { method.name, method.desc, this });
            }
        }
    }
    
    void processDebugTasks() {
        if (!this.env.getOption(MixinEnvironment.Option.DEBUG_VERBOSE)) {
            return;
        }
        final AnnotationNode classDebugAnnotation = Annotations.getVisible(this.classNode, (Class<? extends Annotation>)Debug.class);
        if (classDebugAnnotation != null) {
            this.forceExport = Boolean.TRUE.equals(Annotations.getValue(classDebugAnnotation, "export"));
            if (Boolean.TRUE.equals(Annotations.getValue(classDebugAnnotation, "print"))) {
                Bytecode.textify(this.classNode, System.err);
            }
        }
        for (final MethodNode method : this.classNode.methods) {
            final AnnotationNode methodDebugAnnotation = Annotations.getVisible(method, (Class<? extends Annotation>)Debug.class);
            if (methodDebugAnnotation != null && Boolean.TRUE.equals(Annotations.getValue(methodDebugAnnotation, "print"))) {
                Bytecode.textify(method, System.err);
            }
        }
    }
    
    static {
        logger = LogManager.getLogger("mixin");
    }
}
