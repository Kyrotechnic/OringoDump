//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.impl.skyblock;

import me.oringo.oringoclient.qolfeatures.module.*;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.*;
import net.minecraft.entity.item.*;
import me.oringo.oringoclient.qolfeatures.module.settings.*;
import me.oringo.oringoclient.events.*;
import net.minecraft.entity.*;
import java.util.stream.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.client.event.*;
import java.awt.*;
import me.oringo.oringoclient.utils.*;
import java.util.*;

public class AntiNukebi extends Module
{
    public static final BooleanSetting attack;
    public static final BooleanSetting tracer;
    public static final NumberSetting timeOut;
    public static final NumberSetting distance;
    private static final List<EntityArmorStand> attackedList;
    public static EntityArmorStand currentNukebi;
    private final MilliTimer timeoutTimer;
    
    public AntiNukebi() {
        super("AntiNukekubi", Category.SKYBLOCK);
        this.timeoutTimer = new MilliTimer();
        this.addSettings(AntiNukebi.distance, AntiNukebi.attack, AntiNukebi.timeOut, AntiNukebi.tracer);
    }
    
    private void reset() {
        AntiNukebi.currentNukebi = null;
        AntiNukebi.attackedList.clear();
    }
    
    @Override
    public void onDisable() {
        this.reset();
    }
    
    @SubscribeEvent
    public void onWorldJoin(final WorldJoinEvent event) {
        this.reset();
    }
    
    @SubscribeEvent(priority = EventPriority.LOW)
    public void onMotionUpdate(final MotionUpdateEvent.Pre event) {
        if (this.isToggled()) {
            if (AntiNukebi.currentNukebi == null || this.timeoutTimer.hasTimePassed((long)(AntiNukebi.timeOut.getValue() * 50.0)) || AntiNukebi.currentNukebi.isDead || !AntiNukebi.currentNukebi.canEntityBeSeen((Entity)AntiNukebi.mc.thePlayer)) {
                AntiNukebi.currentNukebi = null;
                final Iterator<Entity> iterator = ((List)AntiNukebi.mc.theWorld.loadedEntityList.stream().filter(entity -> entity instanceof EntityArmorStand && isNukebi((EntityArmorStand)entity) && !AntiNukebi.attackedList.contains(entity) && entity.getDistanceToEntity((Entity)AntiNukebi.mc.thePlayer) < AntiNukebi.distance.getValue() && ((EntityArmorStand)entity).canEntityBeSeen((Entity)AntiNukebi.mc.thePlayer) && Math.hypot(entity.posX - entity.prevPosX, entity.posZ - entity.prevPosZ) < 0.1).collect(Collectors.toList())).iterator();
                if (iterator.hasNext()) {
                    final Entity entity2 = iterator.next();
                    final EntityArmorStand armorStand = AntiNukebi.currentNukebi = (EntityArmorStand)entity2;
                    this.timeoutTimer.reset();
                    AntiNukebi.attackedList.add(armorStand);
                }
            }
            if (AntiNukebi.currentNukebi != null) {
                final Rotation angle = RotationUtils.getRotations(AntiNukebi.currentNukebi.posX, AntiNukebi.currentNukebi.posY + 0.85, AntiNukebi.currentNukebi.posZ);
                event.setRotation(angle);
            }
        }
    }
    
    @SubscribeEvent
    public void onWorldRender(final RenderWorldLastEvent event) {
        if (this.isToggled() && AntiNukebi.currentNukebi != null) {
            RenderUtils.tracerLine((Entity)AntiNukebi.currentNukebi, event.partialTicks, 1.0f, Color.white);
        }
    }
    
    public static boolean isNukebi(final EntityArmorStand entity) {
        return entity.getCurrentArmor(3) != null && entity.getCurrentArmor(3).serializeNBT().getCompoundTag("tag").getCompoundTag("SkullOwner").getCompoundTag("Properties").toString().contains("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWIwNzU5NGUyZGYyNzM5MjFhNzdjMTAxZDBiZmRmYTExMTVhYmVkNWI5YjIwMjllYjQ5NmNlYmE5YmRiYjRiMyJ9fX0=");
    }
    
    static {
        attack = new BooleanSetting("Attack with aura", true);
        tracer = new BooleanSetting("Tracer", true);
        timeOut = new NumberSetting("Timeout", 100.0, 10.0, 250.0, 1.0);
        distance = new NumberSetting("Distance", 10.0, 5.0, 20.0, 1.0);
        attackedList = new ArrayList<EntityArmorStand>();
    }
}
