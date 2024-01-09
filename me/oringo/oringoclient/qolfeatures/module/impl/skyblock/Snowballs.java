//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.impl.skyblock;

import me.oringo.oringoclient.qolfeatures.module.*;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.*;
import me.oringo.oringoclient.qolfeatures.module.settings.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.item.*;
import me.oringo.oringoclient.utils.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class Snowballs extends Module
{
    private boolean wasPressed;
    public BooleanSetting pickupstash;
    public BooleanSetting inventory;
    
    public Snowballs() {
        super("Snowballs", Category.SKYBLOCK);
        this.pickupstash = new BooleanSetting("Pick up stash", true);
        this.inventory = new BooleanSetting("Inventory", false);
        this.addSettings(this.pickupstash, this.inventory);
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (Snowballs.mc.currentScreen != null || !this.isToggled()) {
            return;
        }
        if (this.isPressed() && !this.wasPressed) {
            if (this.inventory.isEnabled()) {
                for (int i = 9; i < 45; ++i) {
                    if (Snowballs.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack() && (Snowballs.mc.thePlayer.inventoryContainer.getSlot(i).getStack().getItem() instanceof ItemSnowball || Snowballs.mc.thePlayer.inventoryContainer.getSlot(i).getStack().getItem() instanceof ItemEgg)) {
                        if (i >= 36) {
                            final int held = Snowballs.mc.thePlayer.inventory.currentItem;
                            PlayerUtils.swapToSlot(i - 36);
                            for (int e = 0; e < 16; ++e) {
                                Snowballs.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C08PacketPlayerBlockPlacement(Snowballs.mc.thePlayer.getHeldItem()));
                            }
                            PlayerUtils.swapToSlot(held);
                        }
                        else {
                            PlayerUtils.numberClick(i, Snowballs.mc.thePlayer.inventory.currentItem);
                            for (int e2 = 0; e2 < 16; ++e2) {
                                Snowballs.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C08PacketPlayerBlockPlacement(Snowballs.mc.thePlayer.getHeldItem()));
                            }
                            PlayerUtils.numberClick(i, Snowballs.mc.thePlayer.inventory.currentItem);
                        }
                    }
                }
            }
            else {
                final int holding = Snowballs.mc.thePlayer.inventory.currentItem;
                for (int x = 0; x < 9; ++x) {
                    if (Snowballs.mc.thePlayer.inventory.getStackInSlot(x) != null && (Snowballs.mc.thePlayer.inventory.getStackInSlot(x).getItem() instanceof ItemSnowball || Snowballs.mc.thePlayer.inventory.getStackInSlot(x).getItem() instanceof ItemEgg)) {
                        Snowballs.mc.thePlayer.inventory.currentItem = x;
                        PlayerUtils.syncHeldItem();
                        for (int e = 0; e < 16; ++e) {
                            Snowballs.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C08PacketPlayerBlockPlacement(Snowballs.mc.thePlayer.getHeldItem()));
                        }
                    }
                }
                Snowballs.mc.thePlayer.inventory.currentItem = holding;
                PlayerUtils.syncHeldItem();
            }
            if (this.pickupstash.isEnabled()) {
                Snowballs.mc.thePlayer.sendChatMessage("/pickupstash");
            }
        }
        this.wasPressed = this.isPressed();
    }
    
    @Override
    public boolean isKeybind() {
        return true;
    }
}
