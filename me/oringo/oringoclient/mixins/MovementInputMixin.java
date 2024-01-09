//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.mixins;

import net.minecraft.util.*;
import org.spongepowered.asm.mixin.*;

@Mixin({ MovementInput.class })
public class MovementInputMixin
{
    @Shadow
    public boolean sneak;
    @Shadow
    public boolean jump;
    @Shadow
    public float moveStrafe;
    @Shadow
    public float moveForward;
}
