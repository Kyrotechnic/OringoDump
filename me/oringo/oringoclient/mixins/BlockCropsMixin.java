//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.block.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ BlockCrops.class })
public abstract class BlockCropsMixin extends BlockMixin
{
    @Inject(method = { "<init>" }, at = { @At("RETURN") })
    private void init(final CallbackInfo ci) {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }
}
