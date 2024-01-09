//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.impl.skyblock;

import me.oringo.oringoclient.qolfeatures.module.*;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.*;
import me.oringo.oringoclient.qolfeatures.module.settings.*;
import me.oringo.oringoclient.events.*;
import me.oringo.oringoclient.utils.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.item.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class BoneThrower extends Module
{
    public BooleanSetting autoDisable;
    public BooleanSetting inventory;
    private boolean wasPressed;
    
    public BoneThrower() {
        super("BoneThrower", Category.SKYBLOCK);
        this.autoDisable = new BooleanSetting("Disable", true);
        this.inventory = new BooleanSetting("Inventory", false);
        this.addSettings(this.autoDisable, this.inventory);
    }
    
    @SubscribeEvent
    public void onUpdate(final PlayerUpdateEvent event) {
        if (this.isToggled()) {
            if (!this.wasPressed && this.isPressed()) {
                if (!this.inventory.isEnabled()) {
                    final int last = BoneThrower.mc.thePlayer.inventory.currentItem;
                    for (int i = 0; i < 9; ++i) {
                        final ItemStack stack = BoneThrower.mc.thePlayer.inventory.getStackInSlot(i);
                        if (stack != null && stack.getDisplayName().contains("Bonemerang")) {
                            BoneThrower.mc.thePlayer.inventory.currentItem = i;
                            PlayerUtils.syncHeldItem();
                            BoneThrower.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C08PacketPlayerBlockPlacement(BoneThrower.mc.thePlayer.getHeldItem()));
                        }
                    }
                    BoneThrower.mc.thePlayer.inventory.currentItem = last;
                    PlayerUtils.syncHeldItem();
                }
                else {
                    for (int j = 9; j < 45; ++j) {
                        if (BoneThrower.mc.thePlayer.inventoryContainer.getSlot(j).getHasStack() && BoneThrower.mc.thePlayer.inventoryContainer.getSlot(j).getStack().getDisplayName().contains("Bonemerang")) {
                            if (j >= 36) {
                                final int held = BoneThrower.mc.thePlayer.inventory.currentItem;
                                PlayerUtils.swapToSlot(j - 36);
                                BoneThrower.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C08PacketPlayerBlockPlacement(BoneThrower.mc.thePlayer.getHeldItem()));
                                PlayerUtils.swapToSlot(held);
                            }
                            else {
                                PlayerUtils.numberClick(j, BoneThrower.mc.thePlayer.inventory.currentItem);
                                BoneThrower.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C08PacketPlayerBlockPlacement(BoneThrower.mc.thePlayer.getHeldItem()));
                                PlayerUtils.numberClick(j, BoneThrower.mc.thePlayer.inventory.currentItem);
                            }
                        }
                    }
                }
            }
            this.wasPressed = this.isPressed();
        }
    }
    
    @Override
    public boolean isKeybind() {
        return true;
    }
}
