//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package org.spongepowered.asm.lib;

class CurrentFrame extends Frame
{
    @Override
    void execute(final int opcode, final int arg, final ClassWriter cw, final Item item) {
        super.execute(opcode, arg, cw, item);
        final Frame successor = new Frame();
        this.merge(cw, successor, 0);
        this.set(successor);
        this.owner.inputStackTop = 0;
    }
}
