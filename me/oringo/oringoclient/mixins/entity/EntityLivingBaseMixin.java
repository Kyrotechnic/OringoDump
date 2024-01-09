//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.mixins.entity;

import org.spongepowered.asm.mixin.*;
import net.minecraft.potion.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.item.*;

@Mixin({ EntityLivingBase.class })
public abstract class EntityLivingBaseMixin extends EntityMixin
{
    @Shadow
    public float rotationYawHead;
    @Shadow
    private int jumpTicks;
    @Shadow
    protected boolean isJumping;
    @Shadow
    public float jumpMovementFactor;
    @Shadow
    protected int newPosRotationIncrements;
    @Shadow
    public float moveForward;
    @Shadow
    public float moveStrafing;
    @Shadow
    protected float movedDistance;
    @Shadow
    protected int entityAge;
    @Shadow
    protected double newPosX;
    @Shadow
    public float field_70769_ao;
    @Shadow
    public float renderYawOffset;
    @Shadow
    protected float onGroundSpeedFactor;
    @Shadow
    public float prevLimbSwingAmount;
    @Shadow
    public float limbSwingAmount;
    @Shadow
    public float limbSwing;
    @Shadow
    protected float prevOnGroundSpeedFactor;
    @Shadow
    public float field_70770_ap;
    
    @Shadow
    protected abstract float getJumpUpwardsMotion();
    
    @Shadow
    public abstract boolean isPotionActive(final int p0);
    
    @Shadow
    public abstract PotionEffect getActivePotionEffect(final Potion p0);
    
    @Shadow
    protected abstract void jump();
    
    @Shadow
    public abstract IAttributeInstance getEntityAttribute(final IAttribute p0);
    
    @Shadow
    public abstract float getHealth();
    
    @Shadow
    public abstract boolean isOnLadder();
    
    @Shadow
    public abstract boolean isPotionActive(final Potion p0);
    
    @Shadow
    public abstract void setLastAttacker(final Entity p0);
    
    @Shadow
    public abstract float getSwingProgress(final float p0);
    
    @Shadow
    protected abstract void updateFallState(final double p0, final boolean p1, final Block p2, final BlockPos p3);
    
    @Shadow
    protected abstract void resetPotionEffectMetadata();
    
    @Shadow
    public abstract ItemStack getHeldItem();
    
    @Shadow
    @Override
    protected abstract void entityInit();
    
    @Shadow
    public abstract void setSprinting(final boolean p0);
    
    public void setJumpTicks(final int jumpTicks) {
        this.jumpTicks = jumpTicks;
    }
    
    public int getJumpTicks() {
        return this.jumpTicks;
    }
}
