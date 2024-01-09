//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.impl.combat;

import me.oringo.oringoclient.qolfeatures.module.*;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.*;
import me.oringo.oringoclient.qolfeatures.module.settings.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.passive.*;
import me.oringo.oringoclient.utils.*;
import net.minecraft.util.*;

public class AimAssist extends Module
{
    public NumberSetting fov;
    public NumberSetting speed;
    public NumberSetting minSpeed;
    public NumberSetting range;
    public BooleanSetting vertical;
    public BooleanSetting players;
    public BooleanSetting mobs;
    public BooleanSetting invisibles;
    public BooleanSetting teams;
    
    public AimAssist() {
        super("Aim Assist", Category.COMBAT);
        this.fov = new NumberSetting("Fov", 60.0, 30.0, 180.0, 1.0);
        this.speed = new NumberSetting("Max speed", 30.0, 1.0, 40.0, 0.1) {
            @Override
            public void setValue(final double value) {
                super.setValue(value);
                if (value < AimAssist.this.minSpeed.getValue()) {
                    this.setValue(AimAssist.this.minSpeed.getValue());
                }
            }
        };
        this.minSpeed = new NumberSetting("Min speed", 20.0, 1.0, 40.0, 0.1) {
            @Override
            public void setValue(final double value) {
                super.setValue(value);
                if (this.getValue() > AimAssist.this.speed.getValue()) {
                    this.setValue(AimAssist.this.speed.getValue());
                }
            }
        };
        this.range = new NumberSetting("Range", 5.0, 0.0, 6.0, 0.1);
        this.vertical = new BooleanSetting("Vertical", true);
        this.players = new BooleanSetting("Players", true);
        this.mobs = new BooleanSetting("Mobs", false);
        this.invisibles = new BooleanSetting("Invisibles", false);
        this.teams = new BooleanSetting("Teams", true);
        this.addSettings(this.fov, this.range, this.minSpeed, this.speed, this.players, this.mobs, this.teams, this.invisibles, this.vertical);
    }
    
    @SubscribeEvent
    public void onRender(final RenderWorldLastEvent event) {
        if (this.isToggled()) {
            final Entity target = this.getTarget();
            if (target != null && AimAssist.mc.objectMouseOver != null && AimAssist.mc.objectMouseOver.entityHit != target) {
                final Rotation rotation = this.getRotation(target);
                final float yaw = AimAssist.mc.thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float(rotation.getYaw() - AimAssist.mc.thePlayer.rotationYaw);
                final float pitch = AimAssist.mc.thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float(rotation.getPitch() - AimAssist.mc.thePlayer.rotationPitch);
                final float diffY = (float)((yaw - AimAssist.mc.thePlayer.rotationYaw) / MathUtil.getRandomInRange(this.speed.getValue(), this.minSpeed.getValue()));
                final float diffP = (float)((pitch - AimAssist.mc.thePlayer.rotationPitch) / MathUtil.getRandomInRange(this.speed.getValue(), this.minSpeed.getValue()));
                AimAssist.mc.thePlayer.rotationYaw += diffY;
                if (this.vertical.isEnabled()) {
                    AimAssist.mc.thePlayer.rotationPitch += diffP;
                }
            }
        }
    }
    
    public Entity getTarget() {
        final List<Entity> validEntities = (List<Entity>)AimAssist.mc.theWorld.getEntities((Class)EntityLivingBase.class, entity -> this.isValid((EntityLivingBase)entity));
        validEntities.sort(Comparator.comparingDouble(entity -> AimAssist.mc.thePlayer.getDistanceToEntity(entity)));
        if (!validEntities.isEmpty()) {
            return validEntities.get(0);
        }
        return null;
    }
    
    private boolean isValid(final EntityLivingBase entity) {
        return entity != AimAssist.mc.thePlayer && AntiBot.isValidEntity((Entity)entity) && (this.invisibles.isEnabled() || !entity.isInvisible()) && !(entity instanceof EntityArmorStand) && AimAssist.mc.thePlayer.canEntityBeSeen((Entity)entity) && entity.getHealth() > 0.0f && entity.getDistanceToEntity((Entity)AimAssist.mc.thePlayer) <= this.range.getValue() && Math.abs(MathHelper.wrapAngleTo180_float(AimAssist.mc.thePlayer.rotationYaw) - MathHelper.wrapAngleTo180_float(this.getRotation((Entity)entity).getYaw())) <= this.fov.getValue() && ((!(entity instanceof EntityMob) && !(entity instanceof EntityAmbientCreature) && !(entity instanceof EntityWaterMob) && !(entity instanceof EntityAnimal) && !(entity instanceof EntitySlime)) || this.mobs.isEnabled()) && (!(entity instanceof EntityPlayer) || ((!EntityUtils.isTeam(entity) || !this.teams.isEnabled()) && this.players.isEnabled())) && !(entity instanceof EntityVillager);
    }
    
    private Rotation getRotation(final Entity entity) {
        if (entity != null) {
            final Vec3 vec3 = AimAssist.mc.thePlayer.getPositionEyes(1.0f);
            final Vec3 vec4 = PlayerUtils.getVectorForRotation(AimAssist.mc.thePlayer.rotationYaw, AimAssist.mc.thePlayer.rotationPitch);
            final Vec3 vec5 = vec3.addVector(vec4.xCoord, vec4.yCoord, vec4.zCoord);
            return RotationUtils.getRotations(RotationUtils.getClosestPointInAABB(vec5, entity.getEntityBoundingBox()));
        }
        return null;
    }
}
