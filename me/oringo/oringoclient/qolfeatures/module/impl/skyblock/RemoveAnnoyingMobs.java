//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.impl.skyblock;

import me.oringo.oringoclient.qolfeatures.module.*;
import net.minecraft.entity.*;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.*;
import me.oringo.oringoclient.qolfeatures.module.settings.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.*;
import net.minecraft.client.entity.*;
import net.minecraft.entity.monster.*;
import java.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.passive.*;

public class RemoveAnnoyingMobs extends Module
{
    private Entity golemEntity;
    public static ArrayList<Entity> seraphs;
    public BooleanSetting hidePlayers;
    
    public RemoveAnnoyingMobs() {
        super("Remove Mobs", 0, Category.SKYBLOCK);
        this.hidePlayers = new BooleanSetting("Hide players", false);
        this.addSettings(this.hidePlayers);
    }
    
    @SubscribeEvent
    public void onUpdate(final LivingEvent.LivingUpdateEvent event) {
        if (RemoveAnnoyingMobs.mc.theWorld == null || RemoveAnnoyingMobs.mc.thePlayer == null) {
            return;
        }
        if (this.isToggled()) {
            if (event.entity instanceof EntityPlayer && !event.entity.equals((Object)Minecraft.getMinecraft().thePlayer) && this.golemEntity != null && !this.golemEntity.isDead && this.golemEntity.getDistanceToEntity(event.entity) < 9.0f) {
                event.entity.posY = 999999.0;
                event.entity.lastTickPosY = 999999.0;
                event.setCanceled(true);
            }
            if (!(event.entity instanceof EntityArmorStand) && !(event.entity instanceof EntityEnderman) && !(event.entity instanceof EntityGuardian) && !(event.entity instanceof EntityFallingBlock) && !event.entity.equals((Object)Minecraft.getMinecraft().thePlayer)) {
                for (final Entity seraph : RemoveAnnoyingMobs.seraphs) {
                    if (event.entity.getDistanceToEntity(seraph) < 5.0f) {
                        event.entity.posY = 999999.0;
                        event.entity.lastTickPosY = 999999.0;
                        event.setCanceled(true);
                    }
                }
            }
            if (event.entity instanceof EntityOtherPlayerMP && this.hidePlayers.isEnabled()) {
                event.entity.posY = 999999.0;
                event.entity.lastTickPosY = 999999.0;
                event.setCanceled(true);
            }
            if (event.entity.getDisplayName().getFormattedText().contains("Endstone Protector")) {
                this.golemEntity = event.entity;
            }
            if (event.entity instanceof EntityCreeper && event.entity.isInvisible() && ((EntityCreeper)event.entity).getHealth() == 20.0f) {
                RemoveAnnoyingMobs.mc.theWorld.removeEntity(event.entity);
            }
            if (event.entity instanceof EntityCreeper && event.entity.isInvisible() && ((EntityCreeper)event.entity).getHealth() != 20.0f) {
                event.entity.setInvisible(false);
            }
        }
    }
    
    @SubscribeEvent
    public void onWorldRender(final TickEvent.ClientTickEvent event) {
        RemoveAnnoyingMobs.seraphs.clear();
        if (!this.isToggled() || RemoveAnnoyingMobs.mc.theWorld == null) {
            return;
        }
        for (final Entity entity : RemoveAnnoyingMobs.mc.theWorld.getLoadedEntityList()) {
            if (entity.getDisplayName().getFormattedText().contains("Voidgloom Seraph")) {
                RemoveAnnoyingMobs.seraphs.add(entity);
            }
            if (entity instanceof EntityFireworkRocket) {
                RemoveAnnoyingMobs.mc.theWorld.removeEntity(entity);
            }
            if (entity instanceof EntityHorse) {
                RemoveAnnoyingMobs.mc.theWorld.removeEntity(entity);
            }
        }
    }
    
    static {
        RemoveAnnoyingMobs.seraphs = new ArrayList<Entity>();
    }
}
