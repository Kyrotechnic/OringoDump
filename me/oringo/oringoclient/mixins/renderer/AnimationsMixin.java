//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.mixins.renderer;

import net.minecraft.client.*;
import net.minecraft.client.entity.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.block.model.*;
import me.oringo.oringoclient.qolfeatures.module.impl.combat.*;
import me.oringo.oringoclient.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import org.spongepowered.asm.mixin.*;

@Mixin(value = { ItemRenderer.class }, priority = 1)
public abstract class AnimationsMixin
{
    @Shadow
    private float prevEquippedProgress;
    @Shadow
    private float equippedProgress;
    @Shadow
    @Final
    private Minecraft mc;
    @Shadow
    private ItemStack itemToRender;
    
    @Shadow
    protected abstract void func_178101_a(final float p0, final float p1);
    
    @Shadow
    protected abstract void func_178109_a(final AbstractClientPlayer p0);
    
    @Shadow
    protected abstract void func_178110_a(final EntityPlayerSP p0, final float p1);
    
    @Shadow
    protected abstract void renderItemMap(final AbstractClientPlayer p0, final float p1, final float p2, final float p3);
    
    @Shadow
    protected abstract void func_178104_a(final AbstractClientPlayer p0, final float p1);
    
    @Shadow
    protected abstract void func_178105_d(final float p0);
    
    @Shadow
    public abstract void renderItem(final EntityLivingBase p0, final ItemStack p1, final ItemCameraTransforms.TransformType p2);
    
    @Shadow
    protected abstract void func_178095_a(final AbstractClientPlayer p0, final float p1, final float p2);
    
    @Shadow
    protected abstract void func_178098_a(final float p0, final AbstractClientPlayer p1);
    
    @Overwrite
    public void renderItemInFirstPerson(final float partialTicks) {
        final float f = 1.0f - (this.prevEquippedProgress + (this.equippedProgress - this.prevEquippedProgress) * partialTicks);
        final AbstractClientPlayer abstractclientplayer = (AbstractClientPlayer)this.mc.thePlayer;
        final float f2 = abstractclientplayer.getSwingProgress(partialTicks);
        final float f3 = abstractclientplayer.prevRotationPitch + (abstractclientplayer.rotationPitch - abstractclientplayer.prevRotationPitch) * partialTicks;
        final float f4 = abstractclientplayer.prevRotationYaw + (abstractclientplayer.rotationYaw - abstractclientplayer.prevRotationYaw) * partialTicks;
        this.func_178101_a(f3, f4);
        this.func_178109_a(abstractclientplayer);
        this.func_178110_a((EntityPlayerSP)abstractclientplayer, partialTicks);
        GlStateManager.enableRescaleNormal();
        GlStateManager.pushMatrix();
        if (this.itemToRender != null) {
            final boolean shouldSpoofBlocking = (KillAura.target != null && !OringoClient.killAura.blockMode.getSelected().equals("None")) || (!OringoClient.autoBlock.blockTimer.hasTimePassed((long)OringoClient.autoBlock.blockTime.getValue()) && OringoClient.autoBlock.canBlock());
            if (this.itemToRender.getItem() instanceof ItemMap) {
                this.renderItemMap(abstractclientplayer, f3, f, f2);
            }
            else if (abstractclientplayer.getItemInUseCount() > 0 || shouldSpoofBlocking) {
                EnumAction enumaction = this.itemToRender.getItemUseAction();
                if (shouldSpoofBlocking) {
                    enumaction = EnumAction.BLOCK;
                }
                switch (enumaction) {
                    case NONE: {
                        this.transformFirstPersonItem(f, 0.0f);
                        break;
                    }
                    case EAT:
                    case DRINK: {
                        this.func_178104_a(abstractclientplayer, partialTicks);
                        this.transformFirstPersonItem(f, 0.0f);
                        break;
                    }
                    case BLOCK: {
                        if (OringoClient.animations.isToggled()) {
                            final String selected = OringoClient.animations.mode.getSelected();
                            switch (selected) {
                                case "1.7": {
                                    this.transformFirstPersonItem(f, f2);
                                    this.func_178103_d();
                                    break;
                                }
                                case "spin":
                                case "vertical spin": {
                                    this.transformFirstPersonItem(f, OringoClient.animations.showSwing.isEnabled() ? f2 : 0.0f);
                                    this.func_178103_d();
                                    break;
                                }
                                case "long hit": {
                                    this.transformFirstPersonItem(f, 0.0f);
                                    this.func_178103_d();
                                    final float var19 = MathHelper.sin(MathHelper.sqrt_float(f2) * 3.1415927f);
                                    GlStateManager.translate(-0.05f, 0.6f, 0.3f);
                                    GlStateManager.rotate(-var19 * 70.0f / 2.0f, -8.0f, -0.0f, 9.0f);
                                    GlStateManager.rotate(-var19 * 70.0f, 1.5f, -0.4f, -0.0f);
                                    break;
                                }
                                case "chill": {
                                    final float f5 = MathHelper.sin(MathHelper.sqrt_float(f2) * 3.1415927f);
                                    this.transformFirstPersonItem(f / 2.0f - 0.18f, 0.0f);
                                    GL11.glRotatef(f5 * 60.0f / 2.0f, -f5 / 2.0f, -0.0f, -16.0f);
                                    GL11.glRotatef(-f5 * 30.0f, 1.0f, f5 / 2.0f, -1.0f);
                                    this.func_178103_d();
                                    break;
                                }
                                case "push": {
                                    this.transformFirstPersonItem(f, -f2);
                                    this.func_178103_d();
                                    break;
                                }
                                case "custom": {
                                    this.transformFirstPersonItem(OringoClient.animationCreator.blockProgress.isEnabled() ? f : 0.0f, OringoClient.animationCreator.swingProgress.isEnabled() ? f2 : 0.0f);
                                    this.func_178103_d();
                                    break;
                                }
                                case "helicopter": {
                                    GlStateManager.rotate((float)(System.currentTimeMillis() / 3L % 360L), 0.0f, 0.0f, -0.1f);
                                    this.transformFirstPersonItem(f / 1.6f, 0.0f);
                                    this.func_178103_d();
                                    break;
                                }
                            }
                            break;
                        }
                        this.transformFirstPersonItem(f, 0.0f);
                        this.func_178103_d();
                        break;
                    }
                    case BOW: {
                        this.transformFirstPersonItem(f, 0.0f);
                        this.func_178098_a(partialTicks, abstractclientplayer);
                        break;
                    }
                }
            }
            else {
                this.func_178105_d(f2);
                this.transformFirstPersonItem(f, f2);
            }
            this.renderItem((EntityLivingBase)abstractclientplayer, this.itemToRender, ItemCameraTransforms.TransformType.FIRST_PERSON);
        }
        else if (!abstractclientplayer.isInvisible()) {
            this.func_178095_a(abstractclientplayer, f, f2);
        }
        GlStateManager.popMatrix();
        GlStateManager.disableRescaleNormal();
        RenderHelper.disableStandardItemLighting();
    }
    
    @Overwrite
    private void transformFirstPersonItem(final float equipProgress, final float swingProgress) {
        final float size = (float)OringoClient.animations.size.getValue();
        final float x = (float)OringoClient.animations.x.getValue();
        final float y = (float)OringoClient.animations.y.getValue();
        final float z = (float)OringoClient.animations.z.getValue();
        GlStateManager.translate(0.56f * x, -0.52f * y, -0.71999997f * z);
        GlStateManager.translate(0.0f, equipProgress * -0.6f, 0.0f);
        GlStateManager.rotate(45.0f, 0.0f, 1.0f, 0.0f);
        final float f = MathHelper.sin(swingProgress * swingProgress * 3.1415927f);
        final float f2 = MathHelper.sin(MathHelper.sqrt_float(swingProgress) * 3.1415927f);
        GlStateManager.rotate(f * -20.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(f2 * -20.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.rotate(f2 * -80.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.scale(0.4f * size, 0.4f * size, 0.4f * size);
    }
    
    @Overwrite
    private void func_178103_d() {
        float angle1 = 30.0f;
        float angle2 = -80.0f;
        float angle3 = 60.0f;
        float translateX = -0.5f;
        float translateY = 0.2f;
        float translateZ = 0.0f;
        float rotation1x = 0.0f;
        float rotation1y = 1.0f;
        float rotation1z = 0.0f;
        float rotation2x = 1.0f;
        float rotation2y = 0.0f;
        float rotation2z = 0.0f;
        final String selected = OringoClient.animations.mode.getSelected();
        switch (selected) {
            case "custom": {
                angle1 = (float)OringoClient.animationCreator.angle1.getValue();
                angle2 = (float)OringoClient.animationCreator.angle2.getValue();
                angle3 = (float)OringoClient.animationCreator.angle3.getValue();
                translateX = (float)OringoClient.animationCreator.translateX.getValue();
                translateY = (float)OringoClient.animationCreator.translateY.getValue();
                translateZ = (float)OringoClient.animationCreator.translateZ.getValue();
                rotation1x = (float)OringoClient.animationCreator.rotation1x.getValue();
                rotation1y = (float)OringoClient.animationCreator.rotation1y.getValue();
                rotation1z = (float)OringoClient.animationCreator.rotation1z.getValue();
                rotation2x = (float)OringoClient.animationCreator.rotation2x.getValue();
                rotation2y = (float)OringoClient.animationCreator.rotation2y.getValue();
                rotation2z = (float)OringoClient.animationCreator.rotation2z.getValue();
                break;
            }
            case "vertical spin": {
                angle1 = (float)(System.currentTimeMillis() % 720L);
                angle1 /= 2.0f;
                rotation2x = 0.0f;
                angle2 = 0.0f;
                break;
            }
            case "spin": {
                translateY = 0.8f;
                angle1 = 60.0f;
                angle2 = (float)(-System.currentTimeMillis() % 720L);
                angle2 /= 2.0f;
                rotation2z = 0.8f;
                angle3 = 30.0f;
                break;
            }
        }
        GlStateManager.translate(translateX, translateY, translateZ);
        GlStateManager.rotate(angle1, rotation1x, rotation1y, rotation1z);
        GlStateManager.rotate(angle2, rotation2x, rotation2y, rotation2z);
        GlStateManager.rotate(angle3, 0.0f, 1.0f, 0.0f);
    }
}
