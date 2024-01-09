//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.impl.combat;

import me.oringo.oringoclient.qolfeatures.module.*;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.*;
import java.util.*;
import me.oringo.oringoclient.qolfeatures.module.settings.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import me.oringo.oringoclient.utils.*;
import me.oringo.oringoclient.events.*;
import net.minecraft.client.*;

public class AntiBot extends Module
{
    private static AntiBot antiBot;
    private static final ModeSetting mode;
    private static final BooleanSetting ticksInvis;
    private static final BooleanSetting tabTicks;
    private static final BooleanSetting npcCheck;
    private static final HashMap<Integer, EntityData> entityData;
    
    public AntiBot() {
        super("Anti Bot", Category.COMBAT);
        this.addSettings(AntiBot.mode, AntiBot.ticksInvis, AntiBot.tabTicks, AntiBot.npcCheck);
    }
    
    public static AntiBot getAntiBot() {
        if (AntiBot.antiBot == null) {
            AntiBot.antiBot = new AntiBot();
        }
        return AntiBot.antiBot;
    }
    
    @SubscribeEvent
    public void onLivingUpdate(final LivingEvent.LivingUpdateEvent event) {
        final EntityData data = AntiBot.entityData.get(event.entity.getEntityId());
        if (data == null) {
            AntiBot.entityData.put(event.entity.getEntityId(), new EntityData(event.entity));
        }
        else {
            AntiBot.entityData.get(event.entity.getEntityId()).update();
        }
    }
    
    public static boolean isValidEntity(final Entity entity) {
        if (AntiBot.antiBot.isToggled() && entity instanceof EntityPlayer && entity != AntiBot.mc.thePlayer) {
            final EntityData data = AntiBot.entityData.get(entity.getEntityId());
            if (data != null && AntiBot.mode.is("Hypixel")) {
                return (!AntiBot.tabTicks.isEnabled() || data.getTabTicks() >= 150) && (!AntiBot.ticksInvis.isEnabled() || data.getTicksExisted() - data.getTicksInvisible() >= 150) && (!AntiBot.npcCheck.isEnabled() || !SkyblockUtils.isNPC(entity));
            }
        }
        return true;
    }
    
    @SubscribeEvent
    public void onWorldJOin(final WorldJoinEvent event) {
        AntiBot.entityData.clear();
    }
    
    static {
        mode = new ModeSetting("Mode", "Hypixel", new String[] { "Hypixel" });
        ticksInvis = new BooleanSetting("Invis ticks check", true, aBoolean -> !AntiBot.mode.is("Hypixel"));
        tabTicks = new BooleanSetting("Tab ticks check", false, aBoolean -> !AntiBot.mode.is("Hypixel"));
        npcCheck = new BooleanSetting("NPC check", true, aBoolean -> !AntiBot.mode.is("Hypixel"));
        entityData = new HashMap<Integer, EntityData>();
    }
    
    private static class EntityData
    {
        private int ticksInvisible;
        private int tabTicks;
        private final Entity entity;
        
        public EntityData(final Entity entity) {
            this.entity = entity;
            this.update();
        }
        
        public int getTabTicks() {
            return this.tabTicks;
        }
        
        public int getTicksInvisible() {
            return this.ticksInvisible;
        }
        
        public int getTicksExisted() {
            return this.entity.ticksExisted;
        }
        
        public void update() {
            if (this.entity instanceof EntityPlayer && AntiBot.mc.getNetHandler() != null && AntiBot.mc.getNetHandler().getPlayerInfo(this.entity.getUniqueID()) != null) {
                ++this.tabTicks;
            }
            if (this.entity.isInvisible()) {
                ++this.ticksInvisible;
            }
        }
    }
}
