//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.impl.skyblock;

import me.oringo.oringoclient.qolfeatures.module.*;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.*;
import me.oringo.oringoclient.qolfeatures.module.settings.*;
import me.oringo.oringoclient.events.*;
import me.oringo.oringoclient.qolfeatures.module.impl.combat.*;
import net.minecraft.entity.*;
import net.minecraftforge.fml.common.eventhandler.*;
import java.util.function.*;
import java.util.stream.*;
import java.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.monster.*;
import me.oringo.oringoclient.utils.*;

public class TerminatorAura extends Module
{
    public NumberSetting range;
    public NumberSetting delay;
    public ModeSetting mode;
    public ModeSetting button;
    public BooleanSetting bossLock;
    public BooleanSetting inDungeon;
    public BooleanSetting teamCheck;
    public StringSetting customItem;
    public static EntityLivingBase target;
    private static boolean attack;
    private static ArrayList<EntityLivingBase> attackedMobs;
    
    public TerminatorAura() {
        super("Terminator Aura", 0, Category.SKYBLOCK);
        this.range = new NumberSetting("Range", 15.0, 5.0, 30.0, 1.0);
        this.delay = new NumberSetting("Use delay", 3.0, 1.0, 10.0, 1.0);
        this.mode = new ModeSetting("Mode", "Swap", new String[] { "Swap", "Held" });
        this.button = new ModeSetting("Mouse", "Right", new String[] { "Left", "Right" });
        this.bossLock = new BooleanSetting("Boss Lock", true);
        this.inDungeon = new BooleanSetting("only Dungeon", true);
        this.teamCheck = new BooleanSetting("Teamcheck", false);
        this.customItem = new StringSetting("Custom Item");
        this.addSettings(this.delay, this.range, this.button, this.mode, this.customItem, this.bossLock, this.inDungeon, this.teamCheck);
    }
    
    @SubscribeEvent(priority = EventPriority.LOW)
    public void onUpdate(final MotionUpdateEvent.Pre event) {
        if (KillAura.target != null || Aimbot.attack || !this.isToggled() || TerminatorAura.mc.thePlayer.ticksExisted % this.delay.getValue() != 0.0 || (!SkyblockUtils.inDungeon && this.inDungeon.isEnabled())) {
            return;
        }
        boolean hasTerm = TerminatorAura.mc.thePlayer.getHeldItem() != null && (TerminatorAura.mc.thePlayer.getHeldItem().getDisplayName().contains("Juju") || TerminatorAura.mc.thePlayer.getHeldItem().getDisplayName().contains("Terminator") || (!this.customItem.getValue().equals("") && TerminatorAura.mc.thePlayer.getHeldItem().getDisplayName().contains(this.customItem.getValue())));
        if (this.mode.getSelected().equals("Swap")) {
            for (int i = 0; i < 9; ++i) {
                if (TerminatorAura.mc.thePlayer.inventory.getStackInSlot(i) != null && (TerminatorAura.mc.thePlayer.inventory.getStackInSlot(i).getDisplayName().contains("Juju") || TerminatorAura.mc.thePlayer.inventory.getStackInSlot(i).getDisplayName().contains("Terminator") || (!this.customItem.is("") && TerminatorAura.mc.thePlayer.inventory.getStackInSlot(i).getDisplayName().contains(this.customItem.getValue())))) {
                    hasTerm = true;
                    break;
                }
            }
        }
        if (!hasTerm) {
            return;
        }
        TerminatorAura.target = this.getTarget(TerminatorAura.target);
        if (TerminatorAura.target != null) {
            TerminatorAura.attack = true;
            final Rotation angles = RotationUtils.getBowRotation((Entity)TerminatorAura.target);
            event.yaw = angles.getYaw();
            event.pitch = angles.getPitch();
        }
    }
    
    @SubscribeEvent
    public void onUpdatePost(final MotionUpdateEvent.Post event) {
        if (!TerminatorAura.attack) {
            return;
        }
        final int held = TerminatorAura.mc.thePlayer.inventory.currentItem;
        if (this.mode.getSelected().equals("Swap")) {
            for (int i = 0; i < 9; ++i) {
                if (TerminatorAura.mc.thePlayer.inventory.getStackInSlot(i) != null && (TerminatorAura.mc.thePlayer.inventory.getStackInSlot(i).getDisplayName().contains("Juju") || TerminatorAura.mc.thePlayer.inventory.getStackInSlot(i).getDisplayName().contains("Terminator") || (!this.customItem.is("") && TerminatorAura.mc.thePlayer.inventory.getStackInSlot(i).getDisplayName().contains(this.customItem.getValue())))) {
                    TerminatorAura.mc.thePlayer.inventory.currentItem = i;
                    break;
                }
            }
        }
        PlayerUtils.syncHeldItem();
        this.click();
        TerminatorAura.mc.thePlayer.inventory.currentItem = held;
        PlayerUtils.syncHeldItem();
        TerminatorAura.attack = false;
    }
    
    private EntityLivingBase getTarget(final EntityLivingBase lastTarget) {
        if (this.bossLock.isEnabled() && lastTarget != null && SkyblockUtils.isMiniboss((Entity)lastTarget) && lastTarget.getHealth() > 0.0f && !lastTarget.isDead && lastTarget.canEntityBeSeen((Entity)TerminatorAura.mc.thePlayer) && lastTarget.getDistanceToEntity((Entity)TerminatorAura.mc.thePlayer) < this.range.getValue()) {
            return lastTarget;
        }
        final List<Entity> validTargets = (List<Entity>)TerminatorAura.mc.theWorld.getLoadedEntityList().stream().filter(entity -> entity instanceof EntityLivingBase).filter(entity -> this.isValid(entity)).sorted(Comparator.comparingDouble((ToDoubleFunction<? super T>)TerminatorAura.mc.thePlayer::getDistanceToEntity)).sorted(Comparator.comparing(entity -> RotationUtils.getRotationDifference(RotationUtils.getRotations((lastTarget != null) ? lastTarget : entity), RotationUtils.getRotations(entity))).reversed()).collect(Collectors.toList());
        final Iterator<Entity> iterator = validTargets.iterator();
        if (iterator.hasNext()) {
            final Entity entity2 = iterator.next();
            TerminatorAura.attackedMobs.add((EntityLivingBase)entity2);
            final Object o;
            new Thread(() -> {
                try {
                    Thread.sleep(350L);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                TerminatorAura.attackedMobs.remove(o);
                return;
            }).start();
            return (EntityLivingBase)entity2;
        }
        return null;
    }
    
    private void click() {
        final String selected = this.button.getSelected();
        switch (selected) {
            case "Left": {
                TerminatorAura.mc.thePlayer.swingItem();
                break;
            }
            case "Right": {
                TerminatorAura.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C08PacketPlayerBlockPlacement(TerminatorAura.mc.thePlayer.getHeldItem()));
                break;
            }
        }
    }
    
    private boolean isValid(final EntityLivingBase entity) {
        return entity != TerminatorAura.mc.thePlayer && !(entity instanceof EntityArmorStand) && TerminatorAura.mc.thePlayer.canEntityBeSeen((Entity)entity) && entity.getHealth() > 0.0f && entity.getDistanceToEntity((Entity)TerminatorAura.mc.thePlayer) <= this.range.getValue() && ((!(entity instanceof EntityPlayer) && !(entity instanceof EntityBat) && !(entity instanceof EntityZombie) && !(entity instanceof EntityGiantZombie)) || !entity.isInvisible()) && !entity.getName().equals("Dummy") && !entity.getName().startsWith("Decoy") && !TerminatorAura.attackedMobs.contains(entity) && !(entity instanceof EntityBlaze) && (!EntityUtils.isTeam(entity) || !this.teamCheck.isEnabled());
    }
    
    static {
        TerminatorAura.attackedMobs = new ArrayList<EntityLivingBase>();
    }
}
