//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.impl.player;

import me.oringo.oringoclient.qolfeatures.module.*;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.*;
import me.oringo.oringoclient.qolfeatures.module.settings.*;
import me.oringo.oringoclient.events.*;
import me.oringo.oringoclient.utils.*;
import net.minecraft.network.play.client.*;
import net.minecraft.block.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.item.*;
import net.minecraft.enchantment.*;

public class AutoTool extends Module
{
    public BooleanSetting tools;
    public BooleanSetting swords;
    private MilliTimer delay;
    
    public AutoTool() {
        super("Auto Tool", Category.PLAYER);
        this.tools = new BooleanSetting("Tools", true);
        this.swords = new BooleanSetting("Swords", true);
        this.delay = new MilliTimer();
        this.addSettings(this.tools, this.swords);
    }
    
    @SubscribeEvent
    public void onPacket(final PacketSentEvent event) {
        if (!this.isToggled() || AutoTool.mc.thePlayer == null) {
            return;
        }
        if (this.tools.isEnabled() && !AutoTool.mc.thePlayer.isUsingItem() && event.packet instanceof C07PacketPlayerDigging && ((C07PacketPlayerDigging)event.packet).getStatus() == C07PacketPlayerDigging.Action.START_DESTROY_BLOCK) {
            for (int i = 0; i < 9; ++i) {
                final ItemStack stack = AutoTool.mc.thePlayer.inventory.getStackInSlot(i);
                final Block block = AutoTool.mc.theWorld.getBlockState(((C07PacketPlayerDigging)event.packet).getPosition()).getBlock();
                if (stack != null && block != null && stack.getStrVsBlock(block) > ((AutoTool.mc.thePlayer.inventory.getCurrentItem() == null) ? 1.0f : AutoTool.mc.thePlayer.inventory.getCurrentItem().getStrVsBlock(block))) {
                    AutoTool.mc.thePlayer.inventory.currentItem = i;
                }
            }
            PlayerUtils.syncHeldItem();
        }
        else if (this.delay.hasTimePassed(500L) && !AutoTool.mc.thePlayer.isUsingItem() && this.swords.isEnabled() && event.packet instanceof C02PacketUseEntity && ((C02PacketUseEntity)event.packet).getAction() == C02PacketUseEntity.Action.ATTACK) {
            for (int i = 0; i < 9; ++i) {
                final ItemStack stack = AutoTool.mc.thePlayer.inventory.getStackInSlot(i);
                if (stack != null && getToolDamage(stack) > ((AutoTool.mc.thePlayer.inventory.getCurrentItem() == null) ? 0.0f : getToolDamage(AutoTool.mc.thePlayer.inventory.getCurrentItem()))) {
                    AutoTool.mc.thePlayer.inventory.currentItem = i;
                }
            }
            PlayerUtils.syncHeldItem();
        }
        if ((event.packet instanceof C09PacketHeldItemChange && AutoTool.mc.thePlayer.inventory.getStackInSlot(((C09PacketHeldItemChange)event.packet).getSlotId()) != null) || (event.packet instanceof C08PacketPlayerBlockPlacement && ((C08PacketPlayerBlockPlacement)event.packet).getStack() != null)) {
            this.delay.reset();
        }
    }
    
    public static float getToolDamage(final ItemStack tool) {
        float damage = 0.0f;
        if (tool != null && (tool.getItem() instanceof ItemTool || tool.getItem() instanceof ItemSword)) {
            if (tool.getItem() instanceof ItemSword) {
                damage += 4.0f;
            }
            else if (tool.getItem() instanceof ItemAxe) {
                damage += 3.0f;
            }
            else if (tool.getItem() instanceof ItemPickaxe) {
                damage += 2.0f;
            }
            else if (tool.getItem() instanceof ItemSpade) {
                ++damage;
            }
            damage += ((tool.getItem() instanceof ItemTool) ? ((ItemTool)tool.getItem()).getToolMaterial().getDamageVsEntity() : ((ItemSword)tool.getItem()).getDamageVsEntity());
            damage += (float)(1.25 * EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, tool));
            damage += (float)(EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, tool) * 0.5);
        }
        return damage;
    }
}
