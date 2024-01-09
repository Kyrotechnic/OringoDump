//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.impl.player;

import me.oringo.oringoclient.qolfeatures.module.*;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.*;
import me.oringo.oringoclient.qolfeatures.module.settings.*;
import me.oringo.oringoclient.events.*;
import net.minecraft.potion.*;
import net.minecraft.item.*;
import java.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import me.oringo.oringoclient.utils.*;
import net.minecraft.network.*;
import me.oringo.oringoclient.qolfeatures.module.impl.combat.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.play.client.*;

public class AutoPot extends Module
{
    public NumberSetting delay;
    public NumberSetting health;
    public BooleanSetting onGround;
    public BooleanSetting fromInv;
    private final HashMap<Potion, Long> delays;
    private final List<Integer> slots;
    
    public AutoPot() {
        super("AutoPot", Category.PLAYER);
        this.delay = new NumberSetting("Delay", 1000.0, 250.0, 2500.0, 1.0);
        this.health = new NumberSetting("Health", 15.0, 1.0, 20.0, 1.0);
        this.onGround = new BooleanSetting("Ground only", true);
        this.fromInv = new BooleanSetting("From inventory", false);
        this.delays = new HashMap<Potion, Long>();
        this.slots = new ArrayList<Integer>();
        this.addSettings(this.delay, this.onGround);
        this.addSettings(this.health, this.fromInv);
    }
    
    @SubscribeEvent(priority = EventPriority.LOW)
    public void onUpdate(final MotionUpdateEvent.Pre event) {
        this.slots.clear();
        if (!this.isToggled() || (!AutoPot.mc.thePlayer.onGround && this.onGround.isEnabled())) {
            return;
        }
        if (this.fromInv.isEnabled() && AutoPot.mc.thePlayer.openContainer.windowId == AutoPot.mc.thePlayer.inventoryContainer.windowId) {
            for (int i = 9; i < 45; ++i) {
                if (AutoPot.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                    final ItemStack stack = AutoPot.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                    if (stack.getItem() instanceof ItemPotion && ItemPotion.isSplash(stack.getMetadata())) {
                        for (final PotionEffect effect : ((ItemPotion)stack.getItem()).getEffects(stack)) {
                            final Potion potion = Potion.potionTypes[effect.getPotionID()];
                            if (this.isBestEffect(effect, stack) && !potion.isBadEffect() && !this.isDelayed(potion) && (!AutoPot.mc.thePlayer.isPotionActive(potion) || effect.getAmplifier() > AutoPot.mc.thePlayer.getActivePotionEffect(potion).getAmplifier())) {
                                if (potion != Potion.heal && potion != Potion.regeneration) {
                                    this.updateDelay(potion);
                                    this.slots.add(i);
                                    event.pitch = 87.9f;
                                    break;
                                }
                                if (AutoPot.mc.thePlayer.getHealth() <= this.health.getValue()) {
                                    this.updateDelay(potion);
                                    this.slots.add(i);
                                    event.pitch = 87.9f;
                                    break;
                                }
                                continue;
                            }
                        }
                    }
                }
            }
        }
        else {
            for (int i = 0; i < 9; ++i) {
                if (AutoPot.mc.thePlayer.inventory.getStackInSlot(i) != null) {
                    final ItemStack stack = AutoPot.mc.thePlayer.inventory.getStackInSlot(i);
                    if (stack.getItem() instanceof ItemPotion && ItemPotion.isSplash(stack.getMetadata())) {
                        for (final PotionEffect effect : ((ItemPotion)stack.getItem()).getEffects(stack)) {
                            final Potion potion = Potion.potionTypes[effect.getPotionID()];
                            if (this.isBestEffect(effect, stack) && !potion.isBadEffect() && !this.isDelayed(potion) && (!AutoPot.mc.thePlayer.isPotionActive(potion) || effect.getAmplifier() > AutoPot.mc.thePlayer.getActivePotionEffect(potion).getAmplifier())) {
                                if (potion != Potion.heal && potion != Potion.regeneration) {
                                    this.updateDelay(potion);
                                    this.slots.add(36 + i);
                                    event.pitch = 87.9f;
                                    break;
                                }
                                if (AutoPot.mc.thePlayer.getHealth() <= this.health.getValue()) {
                                    this.updateDelay(potion);
                                    this.slots.add(36 + i);
                                    event.pitch = 87.9f;
                                    break;
                                }
                                continue;
                            }
                        }
                    }
                }
            }
        }
    }
    
    private boolean isBestEffect(final PotionEffect effect, final ItemStack itemStack) {
        if (this.fromInv.isEnabled()) {
            for (int i = 9; i < 45; ++i) {
                if (AutoPot.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                    final ItemStack stack = AutoPot.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                    if (stack != itemStack && stack.getItem() instanceof ItemPotion && ItemPotion.isSplash(stack.getMetadata())) {
                        for (final PotionEffect potionEffect : ((ItemPotion)stack.getItem()).getEffects(stack)) {
                            if (potionEffect.getPotionID() == effect.getPotionID() && potionEffect.getAmplifier() > effect.getAmplifier()) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        else {
            for (int i = 0; i < 9; ++i) {
                if (AutoPot.mc.thePlayer.inventory.getStackInSlot(i) != null) {
                    final ItemStack stack = AutoPot.mc.thePlayer.inventory.getStackInSlot(i);
                    if (stack != itemStack && stack.getItem() instanceof ItemPotion && ItemPotion.isSplash(stack.getMetadata())) {
                        for (final PotionEffect potionEffect : ((ItemPotion)stack.getItem()).getEffects(stack)) {
                            if (potionEffect.getPotionID() == effect.getPotionID() && potionEffect.getAmplifier() > effect.getAmplifier()) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
    
    @SubscribeEvent
    public void onUpdatePost(final MotionUpdateEvent.Post event) {
        final int slot = AutoPot.mc.thePlayer.inventory.currentItem;
        for (final int hotbar : this.slots) {
            if (hotbar >= 36) {
                PlayerUtils.swapToSlot(hotbar - 36);
                AutoPot.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C08PacketPlayerBlockPlacement(AutoPot.mc.thePlayer.getHeldItem()));
            }
            else {
                this.click(hotbar, AutoPot.mc.thePlayer.openContainer.getSlot(hotbar).getStack());
                PlayerUtils.swapToSlot(1);
                AutoPot.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C08PacketPlayerBlockPlacement(AutoPot.mc.thePlayer.openContainer.getSlot(hotbar).getStack()));
                this.click(hotbar, AutoPot.mc.thePlayer.openContainer.getSlot(37).getStack());
                KillAura.DISABLE.reset();
            }
        }
        PlayerUtils.swapToSlot(slot);
    }
    
    private void click(final int slot, final ItemStack stack) {
        final short short1 = AutoPot.mc.thePlayer.openContainer.getNextTransactionID((InventoryPlayer)null);
        AutoPot.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C0EPacketClickWindow(AutoPot.mc.thePlayer.openContainer.windowId, slot, 1, 2, stack, short1));
    }
    
    private void updateDelay(final Potion potion) {
        if (this.delays.containsKey(potion)) {
            this.delays.replace(potion, System.currentTimeMillis());
        }
        else {
            this.delays.put(potion, System.currentTimeMillis());
        }
    }
    
    private boolean isDelayed(final Potion potion) {
        return this.delays.containsKey(potion) && System.currentTimeMillis() - this.delays.get(potion) < this.delay.getValue();
    }
}
