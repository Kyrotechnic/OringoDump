//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.impl.player;

import me.oringo.oringoclient.qolfeatures.module.*;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.*;
import me.oringo.oringoclient.qolfeatures.module.settings.*;
import me.oringo.oringoclient.events.*;
import me.oringo.oringoclient.utils.*;
import net.minecraft.init.*;
import net.minecraft.entity.item.*;
import net.minecraft.network.*;
import java.util.*;
import net.minecraftforge.client.event.*;
import java.util.regex.*;
import net.minecraftforge.fml.common.eventhandler.*;
import java.util.function.*;
import net.minecraft.item.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.*;

public class AutoHeal extends Module
{
    public static final BooleanSetting heads;
    public static final BooleanSetting soup;
    public static final BooleanSetting witherImpact;
    public static final BooleanSetting wand;
    public static final BooleanSetting gloomLock;
    public static final BooleanSetting zombieSword;
    public static final BooleanSetting powerOrb;
    public static final BooleanSetting fromInv;
    public static final NumberSetting hp;
    public static final NumberSetting gloomLockHP;
    public static final NumberSetting overFlowMana;
    public static final NumberSetting orbHp;
    public static final NumberSetting witherImpactHP;
    private static final MilliTimer hypDelay;
    private static final MilliTimer wandDelay;
    private static final MilliTimer generalDelay;
    private int lastOverflow;
    
    public AutoHeal() {
        super("Auto Heal", Category.PLAYER);
        this.lastOverflow = 0;
        this.addSettings(AutoHeal.hp, AutoHeal.heads, AutoHeal.soup, AutoHeal.witherImpact, AutoHeal.witherImpactHP, AutoHeal.wand, AutoHeal.zombieSword, AutoHeal.powerOrb, AutoHeal.orbHp, AutoHeal.gloomLock, AutoHeal.gloomLockHP, AutoHeal.overFlowMana, AutoHeal.fromInv);
    }
    
    @SubscribeEvent
    public void onUpdate(final MotionUpdateEvent event) {
        if (this.isToggled()) {
            if (event.isPre()) {
                if (AutoHeal.mc.thePlayer.getHealth() / AutoHeal.mc.thePlayer.getMaxHealth() <= AutoHeal.witherImpactHP.getValue() / 100.0 && AutoHeal.witherImpact.isEnabled() && (AutoHeal.fromInv.isEnabled() ? PlayerUtils.getItem(AutoHeal::isImpact) : PlayerUtils.getHotbar(AutoHeal::isImpact)) != -1 && AutoHeal.hypDelay.hasTimePassed(5200L)) {
                    event.setPitch(90.0f);
                }
            }
            else {
                if (AutoHeal.generalDelay.hasTimePassed(500L) && AutoHeal.gloomLock.isEnabled() && AutoHeal.mc.thePlayer.getHealth() / AutoHeal.mc.thePlayer.getMaxHealth() >= AutoHeal.gloomLockHP.getValue() / 100.0 && this.lastOverflow < AutoHeal.overFlowMana.getValue() && swapToItem(itemstack -> itemstack.getItem() == Items.book && itemstack.getDisplayName().toLowerCase().contains("gloomlock grimoire"), true)) {
                    AutoHeal.generalDelay.reset();
                }
                if (AutoHeal.generalDelay.hasTimePassed(500L) && AutoHeal.powerOrb.isEnabled() && AutoHeal.mc.thePlayer.getHealth() / AutoHeal.mc.thePlayer.getMaxHealth() <= AutoHeal.orbHp.getValue() / 100.0) {
                    final List<EntityArmorStand> stands = (List<EntityArmorStand>)AutoHeal.mc.theWorld.getEntities((Class)EntityArmorStand.class, e -> getPowerLevel(e.getName()) < 4 && e.getDistanceToEntity((Entity)AutoHeal.mc.thePlayer) < 20.0f);
                    stands.sort(Comparator.comparingDouble(e -> getPowerLevel(e.getName())));
                    int level = 4;
                    if (!stands.isEmpty()) {
                        level = getPowerLevel(stands.get(0).getName());
                    }
                    int i = 0;
                    int slot = -1;
                    while (i < level) {
                        final int finalI = i;
                        if (AutoHeal.fromInv.isEnabled()) {
                            slot = PlayerUtils.getItem(itemstack -> getPowerLevel(itemstack.getDisplayName()) == finalI);
                        }
                        else {
                            slot = PlayerUtils.getHotbar(itemstack -> getPowerLevel(itemstack.getDisplayName()) == finalI);
                        }
                        if (slot != -1) {
                            break;
                        }
                        ++i;
                    }
                    if (slot != -1) {
                        if (AutoHeal.fromInv.isEnabled()) {
                            if (slot >= 36) {
                                AutoHeal.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C09PacketHeldItemChange(slot));
                                AutoHeal.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C08PacketPlayerBlockPlacement(AutoHeal.mc.thePlayer.inventoryContainer.getSlot(slot).getStack()));
                                AutoHeal.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C09PacketHeldItemChange(AutoHeal.mc.thePlayer.inventory.currentItem));
                            }
                            else {
                                final short short1 = AutoHeal.mc.thePlayer.openContainer.getNextTransactionID(AutoHeal.mc.thePlayer.inventory);
                                AutoHeal.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C0EPacketClickWindow(AutoHeal.mc.thePlayer.openContainer.windowId, slot, AutoHeal.mc.thePlayer.inventory.currentItem, 2, AutoHeal.mc.thePlayer.openContainer.getSlot(slot).getStack(), short1));
                                AutoHeal.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C08PacketPlayerBlockPlacement(AutoHeal.mc.thePlayer.inventoryContainer.getSlot(slot).getStack()));
                                AutoHeal.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C0EPacketClickWindow(AutoHeal.mc.thePlayer.openContainer.windowId, slot, AutoHeal.mc.thePlayer.inventory.currentItem, 2, AutoHeal.mc.thePlayer.openContainer.getSlot(slot).getStack(), short1));
                            }
                        }
                        else {
                            AutoHeal.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C09PacketHeldItemChange(slot));
                            AutoHeal.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C08PacketPlayerBlockPlacement(AutoHeal.mc.thePlayer.inventory.getStackInSlot(slot)));
                            AutoHeal.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C09PacketHeldItemChange(AutoHeal.mc.thePlayer.inventory.currentItem));
                        }
                        AutoHeal.generalDelay.reset();
                    }
                }
                if (AutoHeal.witherImpact.isEnabled() && AutoHeal.mc.thePlayer.getHealth() / AutoHeal.mc.thePlayer.getMaxHealth() <= AutoHeal.witherImpactHP.getValue() / 100.0 && AutoHeal.hypDelay.hasTimePassed(5200L) && swapToItem(AutoHeal::isImpact, false)) {
                    AutoHeal.hypDelay.reset();
                }
                if (AutoHeal.mc.thePlayer.getHealth() / AutoHeal.mc.thePlayer.getMaxHealth() <= AutoHeal.hp.getValue() / 100.0) {
                    if (AutoHeal.generalDelay.hasTimePassed(500L)) {
                        if (AutoHeal.soup.isEnabled() && swapToItem(itemstack -> itemstack.getItem() == Items.mushroom_stew, false)) {
                            AutoHeal.generalDelay.reset();
                        }
                        if (AutoHeal.heads.isEnabled() && swapToItem(itemstack -> itemstack.getItem() == Items.skull && itemstack.getDisplayName().toLowerCase().contains("golden head"), false)) {
                            AutoHeal.generalDelay.reset();
                        }
                        if (AutoHeal.zombieSword.isEnabled() && swapToItem(itemstack -> itemstack.getItem() instanceof ItemSword && itemstack.getDisplayName().contains("Zombie Sword"), false)) {
                            AutoHeal.generalDelay.reset();
                        }
                    }
                    if (AutoHeal.wandDelay.hasTimePassed(7200L) && AutoHeal.wand.isEnabled() && swapToItem(itemstack -> itemstack.getDisplayName().contains("Wand of "), false)) {
                        AutoHeal.wandDelay.reset();
                    }
                }
            }
        }
    }
    
    private static int getPowerLevel(final String name) {
        if (name.contains("Plasmaflux")) {
            return 0;
        }
        if (name.contains("Overflux")) {
            return 1;
        }
        if (name.contains("Mana Flux")) {
            return 2;
        }
        if (name.contains("Radiant")) {
            return 3;
        }
        return 4;
    }
    
    @SubscribeEvent(receiveCanceled = true, priority = EventPriority.HIGHEST)
    public void onChat(final ClientChatReceivedEvent event) {
        if (event.type == 2 && event.message.getUnformattedText().contains("\u2748 Defense")) {
            final Matcher pattern = Pattern.compile("§3(.*?)\u02ac").matcher(event.message.getUnformattedText());
            if (pattern.find()) {
                final String string = pattern.group(1);
                this.lastOverflow = Integer.parseInt(string);
            }
            else {
                this.lastOverflow = 0;
            }
        }
    }
    
    private static boolean swapToItem(final Predicate<ItemStack> stack, final boolean left) {
        int slot;
        if (AutoHeal.fromInv.isEnabled()) {
            slot = PlayerUtils.getItem(stack);
            if (slot != -1) {
                if (slot >= 36) {
                    AutoHeal.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C09PacketHeldItemChange(slot));
                    if (left) {
                        click();
                    }
                    else {
                        AutoHeal.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C08PacketPlayerBlockPlacement(AutoHeal.mc.thePlayer.inventoryContainer.getSlot(slot).getStack()));
                    }
                    AutoHeal.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C09PacketHeldItemChange(AutoHeal.mc.thePlayer.inventory.currentItem));
                }
                else {
                    final short short1 = AutoHeal.mc.thePlayer.openContainer.getNextTransactionID(AutoHeal.mc.thePlayer.inventory);
                    AutoHeal.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C0EPacketClickWindow(AutoHeal.mc.thePlayer.openContainer.windowId, slot, AutoHeal.mc.thePlayer.inventory.currentItem, 2, AutoHeal.mc.thePlayer.openContainer.getSlot(slot).getStack(), short1));
                    if (left) {
                        click();
                    }
                    else {
                        AutoHeal.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C08PacketPlayerBlockPlacement(AutoHeal.mc.thePlayer.inventoryContainer.getSlot(slot).getStack()));
                    }
                    AutoHeal.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C0EPacketClickWindow(AutoHeal.mc.thePlayer.openContainer.windowId, slot, AutoHeal.mc.thePlayer.inventory.currentItem, 2, AutoHeal.mc.thePlayer.openContainer.getSlot(slot).getStack(), short1));
                }
            }
        }
        else {
            slot = PlayerUtils.getHotbar(stack);
            if (slot != -1) {
                AutoHeal.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C09PacketHeldItemChange(slot));
                if (left) {
                    click();
                }
                else {
                    AutoHeal.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C08PacketPlayerBlockPlacement(AutoHeal.mc.thePlayer.inventory.getStackInSlot(slot)));
                }
                AutoHeal.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C09PacketHeldItemChange(AutoHeal.mc.thePlayer.inventory.currentItem));
            }
        }
        return slot != -1;
    }
    
    private static boolean isImpact(final ItemStack stack) {
        return stack.getItem() instanceof ItemSword && (stack.getDisplayName().contains("Hyperion") || stack.getDisplayName().contains("Astraea") || stack.getDisplayName().contains("Scylla") || stack.getDisplayName().contains("Valkyrie"));
    }
    
    private static void click() {
        AutoHeal.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C0APacketAnimation());
    }
    
    static {
        heads = new BooleanSetting("Heads", false);
        soup = new BooleanSetting("Soup", false);
        witherImpact = new BooleanSetting("Wither Impact", true);
        wand = new BooleanSetting("Wand", false);
        gloomLock = new BooleanSetting("Gloom Lock", false);
        zombieSword = new BooleanSetting("Zombie Sword", false);
        powerOrb = new BooleanSetting("Power Orb", false);
        fromInv = new BooleanSetting("From Inv", false);
        hp = new NumberSetting("Health", 70.0, 0.0, 100.0, 1.0);
        gloomLockHP = new NumberSetting("Gloomlock Health", 70.0, 0.0, 100.0, 1.0, a -> !AutoHeal.gloomLock.isEnabled());
        overFlowMana = new NumberSetting("Overflow mana", 600.0, 0.0, 1000.0, 50.0, a -> !AutoHeal.gloomLock.isEnabled());
        orbHp = new NumberSetting("Power Orb Health", 85.0, 0.0, 100.0, 1.0, a -> !AutoHeal.powerOrb.isEnabled());
        witherImpactHP = new NumberSetting("Impact Health", 40.0, 0.0, 100.0, 1.0, a -> !AutoHeal.witherImpact.isEnabled());
        hypDelay = new MilliTimer();
        wandDelay = new MilliTimer();
        generalDelay = new MilliTimer();
    }
}
