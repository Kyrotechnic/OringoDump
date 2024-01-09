//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.mixins;

import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.*;
import me.oringo.oringoclient.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Mixin({ FontRenderer.class })
public abstract class NickHider
{
    @Shadow
    protected abstract void renderStringAtPos(final String p0, final boolean p1);
    
    @Shadow
    public abstract int getStringWidth(final String p0);
    
    @Shadow
    public abstract int getCharWidth(final char p0);
    
    @Inject(method = { "renderStringAtPos" }, at = { @At("HEAD") }, cancellable = true)
    private void renderString(final String text, final boolean shadow, final CallbackInfo ci) {
        if (OringoClient.nickHider.isToggled() && text.contains(OringoClient.mc.getSession().getUsername()) && !OringoClient.mc.getSession().getUsername().equals(OringoClient.nickHider.name.getValue())) {
            ci.cancel();
            this.renderStringAtPos(text.replaceAll(OringoClient.mc.getSession().getUsername(), OringoClient.nickHider.name.getValue()), shadow);
        }
    }
    
    @Inject(method = { "getStringWidth" }, at = { @At("RETURN") }, cancellable = true)
    private void getStringWidth(final String text, final CallbackInfoReturnable<Integer> cir) {
        if (text != null && OringoClient.nickHider.isToggled() && text.contains(OringoClient.mc.getSession().getUsername()) && !OringoClient.mc.getSession().getUsername().equals(OringoClient.nickHider.name.getValue())) {
            cir.setReturnValue((Object)this.getStringWidth(text.replaceAll(OringoClient.mc.getSession().getUsername(), OringoClient.nickHider.name.getValue())));
        }
    }
}
