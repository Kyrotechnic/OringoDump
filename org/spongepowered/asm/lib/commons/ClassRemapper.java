//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package org.spongepowered.asm.lib.commons;

import org.spongepowered.asm.lib.*;

public class ClassRemapper extends ClassVisitor
{
    protected final Remapper remapper;
    protected String className;
    
    public ClassRemapper(final ClassVisitor cv, final Remapper remapper) {
        this(327680, cv, remapper);
    }
    
    protected ClassRemapper(final int api, final ClassVisitor cv, final Remapper remapper) {
        super(api, cv);
        this.remapper = remapper;
    }
    
    public void visit(final int version, final int access, final String name, final String signature, final String superName, final String[] interfaces) {
        this.className = name;
        super.visit(version, access, this.remapper.mapType(name), this.remapper.mapSignature(signature, false), this.remapper.mapType(superName), (String[])((interfaces == null) ? null : this.remapper.mapTypes(interfaces)));
    }
    
    public AnnotationVisitor visitAnnotation(final String desc, final boolean visible) {
        final AnnotationVisitor av = super.visitAnnotation(this.remapper.mapDesc(desc), visible);
        return (av == null) ? null : this.createAnnotationRemapper(av);
    }
    
    public AnnotationVisitor visitTypeAnnotation(final int typeRef, final TypePath typePath, final String desc, final boolean visible) {
        final AnnotationVisitor av = super.visitTypeAnnotation(typeRef, typePath, this.remapper.mapDesc(desc), visible);
        return (av == null) ? null : this.createAnnotationRemapper(av);
    }
    
    public FieldVisitor visitField(final int access, final String name, final String desc, final String signature, final Object value) {
        final FieldVisitor fv = super.visitField(access, this.remapper.mapFieldName(this.className, name, desc), this.remapper.mapDesc(desc), this.remapper.mapSignature(signature, true), this.remapper.mapValue(value));
        return (fv == null) ? null : this.createFieldRemapper(fv);
    }
    
    public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, final String[] exceptions) {
        final String newDesc = this.remapper.mapMethodDesc(desc);
        final MethodVisitor mv = super.visitMethod(access, this.remapper.mapMethodName(this.className, name, desc), newDesc, this.remapper.mapSignature(signature, false), (String[])((exceptions == null) ? null : this.remapper.mapTypes(exceptions)));
        return (mv == null) ? null : this.createMethodRemapper(mv);
    }
    
    public void visitInnerClass(final String name, final String outerName, final String innerName, final int access) {
        super.visitInnerClass(this.remapper.mapType(name), (outerName == null) ? null : this.remapper.mapType(outerName), innerName, access);
    }
    
    public void visitOuterClass(final String owner, final String name, final String desc) {
        super.visitOuterClass(this.remapper.mapType(owner), (name == null) ? null : this.remapper.mapMethodName(owner, name, desc), (desc == null) ? null : this.remapper.mapMethodDesc(desc));
    }
    
    protected FieldVisitor createFieldRemapper(final FieldVisitor fv) {
        return new FieldRemapper(fv, this.remapper);
    }
    
    protected MethodVisitor createMethodRemapper(final MethodVisitor mv) {
        return new MethodRemapper(mv, this.remapper);
    }
    
    protected AnnotationVisitor createAnnotationRemapper(final AnnotationVisitor av) {
        return (AnnotationVisitor)new AnnotationRemapper(av, this.remapper);
    }
}
