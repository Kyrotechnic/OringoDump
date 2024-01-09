//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.mixins.renderer;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.oringo.oringoclient.qolfeatures.module.impl.macro.*;
import java.awt.*;
import me.oringo.oringoclient.qolfeatures.module.impl.other.*;
import me.oringo.oringoclient.utils.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ RenderManager.class })
public abstract class RenderManagerMixin
{
    @Inject(method = { "doRenderEntity" }, at = { @At("HEAD") })
    public void doRenderEntityPre(final Entity entity, final double x, final double y, final double z, final float entityYaw, final float partialTicks, final boolean p_147939_10_, final CallbackInfoReturnable<Boolean> cir) {
        if (entity.equals((Object)AutoSumoBot.target)) {
            MobRenderUtils.setColor(new Color(255, 0, 0, 80));
        }
        if (AntiNicker.nicked.contains(entity.getUniqueID())) {
            RenderUtils.enableChams();
            MobRenderUtils.setColor(new Color(255, 0, 0, 80));
        }
    }
    
    @Inject(method = { "doRenderEntity" }, at = { @At("RETURN") })
    public void doRenderEntityPost(final Entity entity, final double x, final double y, final double z, final float entityYaw, final float partialTicks, final boolean p_147939_10_, final CallbackInfoReturnable<Boolean> cir) {
        if (entity.equals((Object)AutoSumoBot.target)) {
            MobRenderUtils.unsetColor();
        }
        if (AntiNicker.nicked.contains(entity.getUniqueID())) {
            RenderUtils.disableChams();
            MobRenderUtils.unsetColor();
        }
    }
}
