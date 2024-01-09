//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.impl.combat;

import me.oringo.oringoclient.qolfeatures.module.*;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.*;
import me.oringo.oringoclient.qolfeatures.module.settings.*;
import net.minecraftforge.fml.common.gameevent.*;
import org.lwjgl.input.*;
import net.minecraft.entity.item.*;
import com.mojang.realmsclient.gui.*;
import me.oringo.oringoclient.ui.notifications.*;
import me.oringo.oringoclient.qolfeatures.module.impl.player.*;
import me.oringo.oringoclient.*;
import me.oringo.oringoclient.qolfeatures.module.impl.movement.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.*;
import net.minecraft.network.*;
import me.oringo.oringoclient.events.*;
import net.minecraft.network.play.server.*;
import net.minecraftforge.client.event.*;
import net.minecraft.client.gui.inventory.*;
import java.util.stream.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import me.oringo.oringoclient.utils.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.*;
import net.minecraftforge.event.world.*;
import java.util.*;

public class KillAura extends Module
{
    public static EntityLivingBase target;
    public BooleanSetting namesOnly;
    public BooleanSetting middleClick;
    public BooleanSetting players;
    public BooleanSetting mobs;
    public BooleanSetting walls;
    public BooleanSetting teams;
    public BooleanSetting toggleOnLoad;
    public BooleanSetting toggleInGui;
    public BooleanSetting onlySword;
    public BooleanSetting movementFix;
    public BooleanSetting rotationSwing;
    public BooleanSetting shovelSwap;
    public BooleanSetting attackOnly;
    public BooleanSetting invisibles;
    public ModeSetting sorting;
    public ModeSetting rotationMode;
    public ModeSetting blockMode;
    public ModeSetting namesonlyMode;
    public ModeSetting mode;
    public NumberSetting range;
    public NumberSetting rotationRange;
    public NumberSetting fov;
    public NumberSetting maxRotation;
    public NumberSetting minRotation;
    public NumberSetting maxCps;
    public NumberSetting minCps;
    public NumberSetting smoothing;
    public NumberSetting switchDelay;
    public static List<String> names;
    private boolean wasDown;
    private boolean isBlocking;
    private int nextCps;
    private int lastSlot;
    private int targetIndex;
    private int attacks;
    private MilliTimer lastAttack;
    private MilliTimer switchDelayTimer;
    private MilliTimer blockDelay;
    public static final MilliTimer DISABLE;
    
    public KillAura() {
        super("Kill Aura", 0, Category.COMBAT);
        this.namesOnly = new BooleanSetting("Names only", false);
        this.middleClick = new BooleanSetting("Middle click to add", false);
        this.players = new BooleanSetting("Players", false);
        this.mobs = new BooleanSetting("Mobs", true);
        this.walls = new BooleanSetting("Through walls", true);
        this.teams = new BooleanSetting("Teams", true);
        this.toggleOnLoad = new BooleanSetting("Disable on join", true);
        this.toggleInGui = new BooleanSetting("No containers", true);
        this.onlySword = new BooleanSetting("Only swords", false);
        this.movementFix = new BooleanSetting("Movement fix", false);
        this.rotationSwing = new BooleanSetting("Swing on rotation", false);
        this.shovelSwap = new BooleanSetting("Shovel swap", false);
        this.attackOnly = new BooleanSetting("Click only", false);
        this.invisibles = new BooleanSetting("Invisibles", false);
        this.sorting = new ModeSetting("Sorting", "Distance", new String[] { "Distance", "Health", "Hurt", "Hp reverse" });
        this.rotationMode = new ModeSetting("Rotation mode", "Simple", new String[] { "Simple", "Smooth", "None" });
        this.blockMode = new ModeSetting("Autoblock", "None", new String[] { "Vanilla", "Hypixel", "Fake", "None" });
        this.namesonlyMode = new ModeSetting("Names mode", "Enemies", new String[] { "Friends", "Enemies" });
        this.mode = new ModeSetting("Mode", "Single", new String[] { "Single", "Switch" });
        this.range = new NumberSetting("Range", 4.2, 2.0, 6.0, 0.1) {
            @Override
            public void setValue(final double value) {
                super.setValue(value);
                if (this.getValue() > KillAura.this.rotationRange.getValue()) {
                    this.setValue(KillAura.this.rotationRange.getValue());
                }
            }
        };
        this.rotationRange = new NumberSetting("Rotation Range", 6.0, 2.0, 12.0, 0.1) {
            @Override
            public void setValue(final double value) {
                super.setValue(value);
                if (this.getValue() < KillAura.this.range.getValue()) {
                    this.setValue(KillAura.this.range.getValue());
                }
            }
        };
        this.fov = new NumberSetting("Fov", 360.0, 30.0, 360.0, 1.0);
        this.maxRotation = new NumberSetting("Max rotation", 100.0, 10.0, 180.0, 0.1) {
            @Override
            public boolean isHidden() {
                return !KillAura.this.rotationMode.is("Simple");
            }
            
            @Override
            public void setValue(final double value) {
                super.setValue(value);
                if (KillAura.this.minRotation.getValue() > this.getValue()) {
                    this.setValue(KillAura.this.minRotation.getValue());
                }
            }
        };
        this.minRotation = new NumberSetting("Min rotation", 60.0, 5.0, 180.0, 0.1) {
            @Override
            public boolean isHidden() {
                return !KillAura.this.rotationMode.is("Simple");
            }
            
            @Override
            public void setValue(final double value) {
                super.setValue(value);
                if (this.getValue() > KillAura.this.maxRotation.getValue()) {
                    this.setValue(KillAura.this.maxRotation.getValue());
                }
            }
        };
        this.maxCps = new NumberSetting("Max CPS", 13.0, 1.0, 20.0, 1.0) {
            @Override
            public void setValue(final double value) {
                super.setValue(value);
                if (KillAura.this.minCps.getValue() > this.getValue()) {
                    this.setValue(KillAura.this.minCps.getValue());
                }
            }
        };
        this.minCps = new NumberSetting("Min CPS", 11.0, 1.0, 20.0, 1.0) {
            @Override
            public void setValue(final double value) {
                super.setValue(value);
                if (KillAura.this.maxCps.getValue() < this.getValue()) {
                    this.setValue(KillAura.this.maxCps.getValue());
                }
            }
        };
        this.smoothing = new NumberSetting("Smoothing", 12.0, 1.0, 20.0, 0.1) {
            @Override
            public boolean isHidden() {
                return !KillAura.this.rotationMode.is("Smooth");
            }
        };
        this.switchDelay = new NumberSetting("Switch delay", 100.0, 0.0, 250.0, 1.0, aBoolean -> !this.mode.is("Switch"));
        this.nextCps = 10;
        this.lastSlot = -1;
        this.lastAttack = new MilliTimer();
        this.switchDelayTimer = new MilliTimer();
        this.blockDelay = new MilliTimer();
        this.addSettings(this.mode, this.switchDelay, this.range, this.rotationRange, this.minCps, this.maxCps, this.sorting, this.rotationMode, this.smoothing, this.maxRotation, this.minRotation, this.fov, this.blockMode, this.players, this.mobs, this.invisibles, this.teams, this.rotationSwing, this.movementFix, this.namesOnly, this.namesonlyMode, this.middleClick, this.attackOnly, this.walls, this.toggleInGui, this.toggleOnLoad, this.onlySword, this.shovelSwap);
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (KillAura.mc.thePlayer == null || KillAura.mc.theWorld == null || !this.middleClick.isEnabled()) {
            return;
        }
        if (Mouse.isButtonDown(2) && KillAura.mc.currentScreen == null) {
            if (KillAura.mc.pointedEntity != null && !this.wasDown && !(KillAura.mc.pointedEntity instanceof EntityArmorStand) && KillAura.mc.pointedEntity instanceof EntityLivingBase) {
                final String name = ChatFormatting.stripFormatting(KillAura.mc.pointedEntity.getName());
                if (!KillAura.names.contains(name)) {
                    KillAura.names.add(name);
                    Notifications.showNotification("Oringo Client", "Added " + ChatFormatting.AQUA + name + ChatFormatting.RESET + " to name sorting", 2000);
                }
                else {
                    KillAura.names.remove(name);
                    Notifications.showNotification("Oringo Client", "Removed " + ChatFormatting.AQUA + name + ChatFormatting.RESET + " from name sorting", 2000);
                }
            }
            this.wasDown = true;
        }
        else {
            this.wasDown = false;
        }
    }
    
    @Override
    public void onEnable() {
        this.attacks = 0;
    }
    
    @Override
    public void onDisable() {
        KillAura.target = null;
        this.isBlocking = false;
    }
    
    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void onMovePre(final MotionUpdateEvent.Pre event) {
        if (AntiVoid.isBlinking() || (OringoClient.scaffold.isToggled() && Scaffold.disableAura.isEnabled()) || !KillAura.DISABLE.hasTimePassed(100L) || !this.isToggled() || Aimbot.attack || (this.onlySword.isEnabled() && (KillAura.mc.thePlayer.getHeldItem() == null || !(KillAura.mc.thePlayer.getHeldItem().getItem() instanceof ItemSword)))) {
            KillAura.target = null;
            return;
        }
        KillAura.target = this.getTarget();
        if (this.attackOnly.isEnabled() && !KillAura.mc.gameSettings.keyBindAttack.isKeyDown()) {
            return;
        }
        if (KillAura.target != null) {
            final Rotation angles = RotationUtils.getRotations(KillAura.target, 0.2f);
            if (!OringoClient.speed.isToggled()) {
                final String selected = this.rotationMode.getSelected();
                switch (selected) {
                    case "Smooth": {
                        event.setRotation(RotationUtils.getSmoothRotation(RotationUtils.getLastReportedRotation(), angles, (float)this.smoothing.getValue()));
                        break;
                    }
                    case "Simple": {
                        event.setRotation(RotationUtils.getLimitedRotation(RotationUtils.getLastReportedRotation(), angles, (float)(this.minRotation.getValue() + Math.abs(this.maxRotation.getValue() - this.minRotation.getValue()) * new Random().nextFloat())));
                        break;
                    }
                }
            }
            event.setPitch(MathUtil.clamp(event.pitch, 90.0f, -90.0f));
            final String selected2 = this.blockMode.getSelected();
            switch (selected2) {
            }
            if (this.shovelSwap.isEnabled() && KillAura.target instanceof EntityPlayer && this.hasDiamondArmor((EntityPlayer)KillAura.target)) {
                this.lastSlot = KillAura.mc.thePlayer.inventory.currentItem;
                for (int i = 0; i < 9; ++i) {
                    if (KillAura.mc.thePlayer.inventory.getStackInSlot(i) != null && KillAura.mc.thePlayer.inventory.getStackInSlot(i).getItem() instanceof ItemSpade) {
                        PlayerUtils.swapToSlot(i);
                        this.isBlocking = false;
                        break;
                    }
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onMoveFlying(final MoveFlyingEvent event) {
        if (this.isToggled() && KillAura.target != null && this.movementFix.isEnabled()) {
            event.setYaw(RotationUtils.getRotations(KillAura.target).getYaw());
        }
    }
    
    private boolean hasDiamondArmor(final EntityPlayer player) {
        for (int i = 1; i < 5; ++i) {
            if (player.getEquipmentInSlot(i) != null && player.getEquipmentInSlot(i).getItem() instanceof ItemArmor && ((ItemArmor)player.getEquipmentInSlot(i).getItem()).getArmorMaterial() == ItemArmor.ArmorMaterial.DIAMOND) {
                return true;
            }
        }
        return false;
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onMovePost(final MotionUpdateEvent.Post event) {
        if (this.attackOnly.isEnabled() && !KillAura.mc.gameSettings.keyBindAttack.isKeyDown()) {
            this.attacks = 0;
            return;
        }
        if (KillAura.target != null && KillAura.mc.thePlayer.getDistanceToEntity((Entity)KillAura.target) < Math.max(this.rotationRange.getValue(), this.range.getValue()) && this.attacks > 0) {
            final String selected = this.blockMode.getSelected();
            switch (selected) {
                case "None":
                case "Fake": {}
                case "Vanilla": {
                    this.stopBlocking();
                    break;
                }
            }
            while (this.attacks > 0) {
                KillAura.mc.thePlayer.swingItem();
                if (KillAura.mc.thePlayer.getDistanceToEntity((Entity)KillAura.target) < this.range.getValue() && (RotationUtils.getRotationDifference(RotationUtils.getRotations(KillAura.target), RotationUtils.getLastReportedRotation()) < 15.0 || this.rotationMode.is("None") || OringoClient.speed.isToggled() || (AntiNukebi.currentNukebi != null && AntiNukebi.attack.isEnabled()))) {
                    KillAura.mc.playerController.attackEntity((EntityPlayer)KillAura.mc.thePlayer, (Entity)KillAura.target);
                    if (this.switchDelayTimer.hasTimePassed((long)this.switchDelay.getValue())) {
                        ++this.targetIndex;
                        this.switchDelayTimer.reset();
                    }
                }
                --this.attacks;
            }
            if (KillAura.mc.thePlayer.getHeldItem() != null && KillAura.mc.thePlayer.getHeldItem().getItem() instanceof ItemSword) {
                final String selected2 = this.blockMode.getSelected();
                switch (selected2) {
                    case "Vanilla": {
                        if (!this.isBlocking) {
                            this.startBlocking();
                            break;
                        }
                        break;
                    }
                    case "Hypixel": {
                        if (this.blockDelay.hasTimePassed(250L)) {
                            this.startBlocking();
                            KillAura.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C0BPacketEntityAction((Entity)KillAura.mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
                            KillAura.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C0BPacketEntityAction((Entity)KillAura.mc.thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
                            this.blockDelay.reset();
                            break;
                        }
                        break;
                    }
                }
            }
        }
        else {
            this.attacks = 0;
        }
        if (this.shovelSwap.isEnabled() && this.lastSlot != -1) {
            PlayerUtils.swapToSlot(this.lastSlot);
            this.lastSlot = -1;
        }
    }
    
    @SubscribeEvent(receiveCanceled = true)
    public void onPacket(final PacketReceivedEvent event) {
        if (event.packet instanceof S08PacketPlayerPosLook) {
            KillAura.DISABLE.reset();
        }
    }
    
    @SubscribeEvent
    public void onRender(final RenderWorldLastEvent event) {
        if (this.isToggled() && KillAura.target != null && this.lastAttack.hasTimePassed(1000 / this.nextCps) && KillAura.mc.thePlayer.getDistanceToEntity((Entity)KillAura.target) < (this.rotationSwing.isEnabled() ? this.getRotationRange() : this.range.getValue())) {
            this.nextCps = (int)(this.minCps.getValue() + Math.abs(this.maxCps.getValue() - this.minCps.getValue()) * new Random().nextFloat());
            this.lastAttack.reset();
            ++this.attacks;
        }
    }
    
    private EntityLivingBase getTarget() {
        if ((KillAura.mc.currentScreen instanceof GuiContainer && this.toggleInGui.isEnabled()) || KillAura.mc.theWorld == null) {
            return null;
        }
        final List<Entity> validTargets = (List<Entity>)KillAura.mc.theWorld.getLoadedEntityList().stream().filter(entity -> entity instanceof EntityLivingBase).filter(entity -> this.isValid(entity)).sorted(Comparator.comparingDouble(e -> e.getDistanceToEntity((Entity)KillAura.mc.thePlayer))).collect(Collectors.toList());
        final String selected = this.sorting.getSelected();
        switch (selected) {
            case "Health": {
                validTargets.sort(Comparator.comparingDouble(e -> e.getHealth()));
                break;
            }
            case "Hurt": {
                validTargets.sort(Comparator.comparing(e -> e.hurtTime));
                break;
            }
            case "Hp reverse": {
                validTargets.sort(Comparator.comparingDouble(e -> e.getHealth()).reversed());
                break;
            }
        }
        if (!validTargets.isEmpty()) {
            if (this.targetIndex >= validTargets.size()) {
                this.targetIndex = 0;
            }
            final String selected2 = this.mode.getSelected();
            switch (selected2) {
                case "Switch": {
                    return (EntityLivingBase)validTargets.get(this.targetIndex);
                }
                case "Single": {
                    return (EntityLivingBase)validTargets.get(0);
                }
            }
        }
        return null;
    }
    
    private boolean isValid(final EntityLivingBase entity) {
        if (entity == KillAura.mc.thePlayer || !AntiBot.isValidEntity((Entity)entity) || (!this.invisibles.isEnabled() && entity.isInvisible()) || entity instanceof EntityArmorStand || (!KillAura.mc.thePlayer.canEntityBeSeen((Entity)entity) && !this.walls.isEnabled()) || entity.getHealth() <= 0.0f || entity.getDistanceToEntity((Entity)KillAura.mc.thePlayer) > ((KillAura.target != null && KillAura.target != entity) ? this.range.getValue() : Math.max(this.rotationRange.getValue(), this.range.getValue())) || RotationUtils.getRotationDifference(RotationUtils.getRotations(entity), RotationUtils.getPlayerRotation()) > this.fov.getValue()) {
            return false;
        }
        if (this.namesOnly.isEnabled()) {
            final boolean flag = KillAura.names.contains(ChatFormatting.stripFormatting(entity.getName()));
            if (this.namesonlyMode.is("Enemies") || flag) {
                return this.namesonlyMode.is("Enemies") && flag;
            }
        }
        return ((!(entity instanceof EntityMob) && !(entity instanceof EntityAmbientCreature) && !(entity instanceof EntityWaterMob) && !(entity instanceof EntityAnimal) && !(entity instanceof EntitySlime)) || this.mobs.isEnabled()) && (!(entity instanceof EntityPlayer) || ((!EntityUtils.isTeam(entity) || !this.teams.isEnabled()) && this.players.isEnabled())) && !(entity instanceof EntityVillager);
    }
    
    private double getRotationRange() {
        return Math.max(this.rotationRange.getValue(), this.range.getValue());
    }
    
    private void startBlocking() {
        PacketUtils.sendPacketNoEvent((Packet<?>)new C08PacketPlayerBlockPlacement(KillAura.mc.thePlayer.getHeldItem()));
        this.isBlocking = true;
    }
    
    private void stopBlocking() {
        if (this.isBlocking) {
            KillAura.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
            this.isBlocking = false;
        }
    }
    
    @SubscribeEvent
    public void onWorldLoad(final WorldEvent.Load event) {
        if (this.isToggled() && this.toggleOnLoad.isEnabled()) {
            this.toggle();
        }
    }
    
    static {
        KillAura.names = new ArrayList<String>();
        DISABLE = new MilliTimer();
    }
}
