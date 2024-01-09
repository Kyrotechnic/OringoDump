//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.mixins;

import net.minecraft.client.model.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.entity.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.oringo.oringoclient.qolfeatures.module.impl.render.*;
import net.minecraft.client.*;
import me.oringo.oringoclient.utils.*;
import me.oringo.oringoclient.mixins.entity.*;
import me.oringo.oringoclient.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ ModelBiped.class })
public class MixinModelBiped
{
    @Shadow
    public ModelRenderer bipedRightArm;
    @Shadow
    public int heldItemRight;
    @Shadow
    public ModelRenderer bipedHead;
    @Shadow
    public ModelRenderer bipedBody;
    
    @Inject(method = { "setRotationAngles" }, at = { @At(value = "FIELD", target = "Lnet/minecraft/client/model/ModelBiped;swingProgress:F") })
    private void setRotationAngles(final float p_setRotationAngles_1_, final float p_setRotationAngles_2_, final float p_setRotationAngles_3_, final float p_setRotationAngles_4_, final float p_setRotationAngles_5_, final float p_setRotationAngles_6_, final Entity p_setRotationAngles_7_, final CallbackInfo callbackInfo) {
        if (!ServerRotations.getInstance().isToggled()) {
            return;
        }
        if (p_setRotationAngles_7_ != null && p_setRotationAngles_7_.equals((Object)Minecraft.getMinecraft().thePlayer)) {
            this.bipedHead.rotateAngleX = (RotationUtils.lastLastReportedPitch + (((PlayerSPAccessor)p_setRotationAngles_7_).getLastReportedPitch() - RotationUtils.lastLastReportedPitch) * ((MinecraftAccessor)OringoClient.mc).getTimer().renderPartialTicks) / 57.295776f;
        }
    }
}
