//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.mixins.entity;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.entity.*;
import net.minecraft.util.*;
import me.oringo.oringoclient.*;
import org.apache.commons.codec.digest.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ AbstractClientPlayer.class })
public abstract class AbstractClientPlayerMixin extends PlayerMixin
{
    private static ResourceLocation getCape(final String uuid) {
        return OringoClient.capes.get(DigestUtils.sha256Hex(uuid));
    }
    
    @Inject(method = { "getLocationCape" }, at = { @At("RETURN") }, cancellable = true)
    public void getLocationCape(final CallbackInfoReturnable<ResourceLocation> cir) {
        final ResourceLocation minecons = getCape(((AbstractClientPlayer)this).getUniqueID().toString());
        if (minecons != null) {
            cir.setReturnValue((Object)minecons);
        }
    }
}
