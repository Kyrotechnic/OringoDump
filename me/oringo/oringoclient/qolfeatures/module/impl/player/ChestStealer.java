//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.impl.player;

import me.oringo.oringoclient.qolfeatures.module.*;
import me.oringo.oringoclient.utils.*;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.*;
import me.oringo.oringoclient.qolfeatures.module.settings.*;
import net.minecraftforge.client.event.*;
import net.minecraft.client.gui.inventory.*;
import com.mojang.realmsclient.gui.*;
import net.minecraft.init.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class ChestStealer extends Module
{
    private MilliTimer timer;
    public NumberSetting delay;
    public BooleanSetting close;
    public BooleanSetting nameCheck;
    public BooleanSetting stealTrash;
    
    public ChestStealer() {
        super("Chest stealer", 0, Category.PLAYER);
        this.timer = new MilliTimer();
        this.delay = new NumberSetting("Delay", 100.0, 30.0, 200.0, 1.0);
        this.close = new BooleanSetting("Auto close", true);
        this.nameCheck = new BooleanSetting("Name check", true);
        this.stealTrash = new BooleanSetting("Steal trash", false);
        this.addSettings(this.delay, this.nameCheck, this.stealTrash, this.close);
    }
    
    @SubscribeEvent
    public void onGui(final GuiScreenEvent.BackgroundDrawnEvent event) {
        if (event.gui instanceof GuiChest && this.isToggled()) {
            final Container container = ((GuiChest)event.gui).inventorySlots;
            if (container instanceof ContainerChest && (!this.nameCheck.isEnabled() || ChatFormatting.stripFormatting(((ContainerChest)container).getLowerChestInventory().getDisplayName().getFormattedText()).equals("Chest") || ChatFormatting.stripFormatting(((ContainerChest)container).getLowerChestInventory().getDisplayName().getFormattedText()).equals("LOW"))) {
                for (int i = 0; i < ((ContainerChest)container).getLowerChestInventory().getSizeInventory(); ++i) {
                    if (container.getSlot(i).getHasStack() && this.timer.hasTimePassed((long)this.delay.getValue())) {
                        final Item item = container.getSlot(i).getStack().getItem();
                        if (this.stealTrash.isEnabled() || item instanceof ItemEnderPearl || item instanceof ItemTool || item instanceof ItemArmor || item instanceof ItemBow || item instanceof ItemPotion || item == Items.arrow || item instanceof ItemAppleGold || item instanceof ItemSword || item instanceof ItemBlock) {
                            ChestStealer.mc.playerController.windowClick(container.windowId, i, 0, 1, (EntityPlayer)ChestStealer.mc.thePlayer);
                            this.timer.reset();
                            return;
                        }
                    }
                }
                for (int i = 0; i < ((ContainerChest)container).getLowerChestInventory().getSizeInventory(); ++i) {
                    if (container.getSlot(i).getHasStack()) {
                        final Item item = container.getSlot(i).getStack().getItem();
                        if (this.stealTrash.isEnabled() || item instanceof ItemEnderPearl || item instanceof ItemTool || item instanceof ItemArmor || item instanceof ItemBow || item instanceof ItemPotion || item == Items.arrow || item instanceof ItemAppleGold || item instanceof ItemSword || item instanceof ItemBlock) {
                            return;
                        }
                    }
                }
                if (this.close.isEnabled()) {
                    ChestStealer.mc.thePlayer.closeScreen();
                }
            }
        }
    }
}
