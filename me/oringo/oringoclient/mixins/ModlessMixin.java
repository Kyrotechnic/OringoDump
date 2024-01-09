//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.mixins;

import net.minecraftforge.fml.common.network.handshake.*;
import org.spongepowered.asm.mixin.*;
import java.util.*;
import net.minecraftforge.fml.common.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.oringo.oringoclient.*;
import net.minecraft.client.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ FMLHandshakeMessage.ModList.class })
public class ModlessMixin
{
    @Shadow
    private Map<String, String> modTags;
    
    @Inject(method = { "<init>(Ljava/util/List;)V" }, at = { @At("RETURN") })
    public void test(final List<ModContainer> modList, final CallbackInfo ci) {
        if (!OringoClient.modless.isToggled()) {
            return;
        }
        try {
            if (Minecraft.getMinecraft().isSingleplayer()) {
                return;
            }
        }
        catch (Exception e) {
            return;
        }
        this.modTags.entrySet().removeIf(mod -> !mod.getKey().equalsIgnoreCase("fml") && !mod.getKey().equalsIgnoreCase("forge") && !mod.getKey().equalsIgnoreCase("mcp"));
    }
}
