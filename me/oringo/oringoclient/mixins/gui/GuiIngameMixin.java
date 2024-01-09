//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.mixins.gui;

import org.spongepowered.asm.mixin.*;
import net.minecraft.scoreboard.*;
import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraftforge.common.*;
import me.oringo.oringoclient.events.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ GuiIngame.class })
public abstract class GuiIngameMixin
{
    @Shadow
    public abstract FontRenderer getFontRenderer();
    
    @Inject(method = { "renderScoreboard" }, at = { @At("HEAD") }, cancellable = true)
    public void renderScoreboard(final ScoreObjective s, final ScaledResolution score, final CallbackInfo ci) {
        if (MinecraftForge.EVENT_BUS.post((Event)new ScoreboardRenderEvent(s, score))) {
            ci.cancel();
        }
    }
}
