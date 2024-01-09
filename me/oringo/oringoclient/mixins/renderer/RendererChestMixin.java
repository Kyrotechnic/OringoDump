//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.mixins.renderer;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.tileentity.*;
import net.minecraft.tileentity.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraftforge.common.*;
import me.oringo.oringoclient.events.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ TileEntityChestRenderer.class })
public class RendererChestMixin
{
    @Inject(method = { "renderTileEntityAt(Lnet/minecraft/tileentity/TileEntityChest;DDDFI)V" }, at = { @At("HEAD") }, cancellable = true)
    public void onDrawChest(final TileEntityChest te, final double x, final double y, final double z, final float partialTicks, final int destroyStage, final CallbackInfo ci) {
        if (MinecraftForge.EVENT_BUS.post((Event)new RenderChestEvent.Pre(te, x, y, z, partialTicks, destroyStage))) {
            ci.cancel();
        }
    }
    
    @Inject(method = { "renderTileEntityAt(Lnet/minecraft/tileentity/TileEntityChest;DDDFI)V" }, at = { @At("RETURN") }, cancellable = true)
    public void onDrawChestPost(final TileEntityChest te, final double x, final double y, final double z, final float partialTicks, final int destroyStage, final CallbackInfo ci) {
        if (MinecraftForge.EVENT_BUS.post((Event)new RenderChestEvent.Post(te, x, y, z, partialTicks, destroyStage))) {
            ci.cancel();
        }
    }
}
