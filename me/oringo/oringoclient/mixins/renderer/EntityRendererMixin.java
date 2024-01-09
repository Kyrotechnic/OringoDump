//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.mixins.renderer;

import net.minecraft.client.renderer.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.entity.*;
import net.minecraft.potion.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.oringo.oringoclient.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.util.*;
import me.oringo.oringoclient.utils.*;
import net.minecraft.client.multiplayer.*;

@Mixin({ EntityRenderer.class })
public class EntityRendererMixin
{
    @Shadow
    private float thirdPersonDistanceTemp;
    @Shadow
    private float thirdPersonDistance;
    
    @Redirect(method = { "setupFog" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLivingBase;isPotionActive(Lnet/minecraft/potion/Potion;)Z"))
    public boolean removeBlindness(final EntityLivingBase instance, final Potion potionIn) {
        return false;
    }
    
    @Inject(method = { "hurtCameraEffect" }, at = { @At("HEAD") }, cancellable = true)
    public void hurtCam(final float entitylivingbase, final CallbackInfo ci) {
        if (OringoClient.camera.noHurtCam.isEnabled() && OringoClient.camera.isToggled()) {
            ci.cancel();
        }
    }
    
    @Redirect(method = { "orientCamera" }, at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/EntityRenderer;thirdPersonDistanceTemp:F"))
    public float thirdPersonDistanceTemp(final EntityRenderer instance) {
        return (OringoClient.camera.isToggled() && !OringoClient.camera.smoothF5.isEnabled()) ? ((float)OringoClient.camera.cameraDistance.getValue()) : this.thirdPersonDistanceTemp;
    }
    
    @Redirect(method = { "orientCamera" }, at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/EntityRenderer;thirdPersonDistance:F"))
    public float thirdPersonDistance(final EntityRenderer instance) {
        return (OringoClient.camera.isToggled() && !OringoClient.camera.smoothF5.isEnabled()) ? ((float)OringoClient.camera.cameraDistance.getValue()) : this.thirdPersonDistance;
    }
    
    @Redirect(method = { "orientCamera" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Vec3;distanceTo(Lnet/minecraft/util/Vec3;)D"))
    public double onCamera(final Vec3 instance, final Vec3 vec) {
        return (OringoClient.camera.isToggled() && OringoClient.camera.cameraClip.isEnabled()) ? OringoClient.camera.cameraDistance.getValue() : instance.distanceTo(vec);
    }
    
    @Inject(method = { "updateRenderer" }, at = { @At("RETURN") })
    public void onUpdate(final CallbackInfo ci) {
        if (OringoClient.camera.isToggled() && OringoClient.camera.smoothF5.isEnabled()) {
            if (OringoClient.mc.gameSettings.thirdPersonView > 0) {
                this.thirdPersonDistance = (float)MathUtil.clamp(this.thirdPersonDistance + OringoClient.camera.speed.getValue(), OringoClient.camera.cameraDistance.getValue(), 0.0);
            }
            else {
                final float n = (float)OringoClient.camera.startSize.getValue();
                this.thirdPersonDistanceTemp = n;
                this.thirdPersonDistance = n;
            }
        }
    }
    
    @Redirect(method = { "getMouseOver" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/PlayerControllerMP;getBlockReachDistance()F"))
    private float getBlockReachDistance(final PlayerControllerMP instance) {
        return OringoClient.reach.isToggled() ? ((float)OringoClient.reach.blockReach.getValue()) : OringoClient.mc.playerController.getBlockReachDistance();
    }
    
    @Redirect(method = { "getMouseOver" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Vec3;distanceTo(Lnet/minecraft/util/Vec3;)D", ordinal = 2))
    private double distanceTo(final Vec3 instance, final Vec3 vec) {
        return (OringoClient.reach.isToggled() && instance.distanceTo(vec) <= OringoClient.reach.reach.getValue()) ? 0.0 : instance.distanceTo(vec);
    }
}
