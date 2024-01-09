//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.impl.keybinds;

import me.oringo.oringoclient.qolfeatures.module.*;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.*;
import me.oringo.oringoclient.*;
import net.minecraftforge.common.*;
import me.oringo.oringoclient.qolfeatures.module.settings.*;
import net.minecraftforge.client.event.*;
import me.oringo.oringoclient.qolfeatures.module.impl.other.*;
import com.mojang.realmsclient.gui.*;
import net.minecraftforge.fml.common.eventhandler.*;
import me.oringo.oringoclient.utils.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.entity.player.*;

public class Keybind extends Module
{
    public ModeSetting button;
    public ModeSetting mode;
    public NumberSetting delay;
    public NumberSetting clickCount;
    public StringSetting item;
    public BooleanSetting fromInv;
    public RunnableSetting remove;
    private boolean wasPressed;
    private final MilliTimer delayTimer;
    private boolean isEnabled;
    
    public Keybind(final String name) {
        super(name, Category.KEYBINDS);
        this.button = new ModeSetting("Button", "Right", new String[] { "Right", "Left", "Swing" });
        this.mode = new ModeSetting("Mode", "Normal", new String[] { "Normal", "Rapid", "Toggle" });
        this.delay = new NumberSetting("Delay", 50.0, 0.0, 5000.0, 1.0);
        this.clickCount = new NumberSetting("Click Count", 1.0, 1.0, 64.0, 1.0);
        this.item = new StringSetting("Item");
        this.fromInv = new BooleanSetting("From inventory", false);
        this.remove = new RunnableSetting("Remove keybinding", () -> {
            this.setToggled(false);
            OringoClient.modules.remove(this);
            MinecraftForge.EVENT_BUS.unregister((Object)this);
            return;
        });
        this.delayTimer = new MilliTimer();
        this.addSettings(this.item, this.button, this.mode, this.delay, this.fromInv, this.remove);
    }
    
    @Override
    public String getName() {
        return this.item.getValue().equals("") ? ("Keybind " + (Module.getModulesByCategory(Category.KEYBINDS).indexOf(this) + 1)) : this.item.getValue();
    }
    
    @Override
    public boolean isKeybind() {
        return true;
    }
    
    @SubscribeEvent
    public void onTick(final RenderWorldLastEvent event) {
        final boolean keyPressed = this.isPressed();
        if ((keyPressed || this.isEnabled) && this.isToggled() && !this.item.getValue().equals("") && Keybind.mc.currentScreen == null && this.delayTimer.hasTimePassed((long)this.delay.getValue()) && (this.mode.is("Rapid") || !this.wasPressed || (this.mode.is("Toggle") && this.isEnabled))) {
            if (!this.fromInv.isEnabled() || !Disabler.wasEnabled) {
                for (int i = 0; i < 9; ++i) {
                    if (Keybind.mc.thePlayer.inventory.getStackInSlot(i) != null && ChatFormatting.stripFormatting(Keybind.mc.thePlayer.inventory.getStackInSlot(i).getDisplayName()).toLowerCase().contains(this.item.getValue().toLowerCase())) {
                        final int held = Keybind.mc.thePlayer.inventory.currentItem;
                        PlayerUtils.swapToSlot(i);
                        this.click();
                        PlayerUtils.swapToSlot(held);
                        break;
                    }
                }
            }
            else {
                final int slot = PlayerUtils.getItem(this.item.getValue());
                if (slot != -1) {
                    if (slot >= 36) {
                        final int held = Keybind.mc.thePlayer.inventory.currentItem;
                        PlayerUtils.swapToSlot(slot - 36);
                        this.click();
                        PlayerUtils.swapToSlot(held);
                    }
                    else {
                        this.numberClick(slot, Keybind.mc.thePlayer.inventory.currentItem);
                        this.click();
                        this.numberClick(slot, Keybind.mc.thePlayer.inventory.currentItem);
                    }
                }
            }
        }
        if (this.mode.is("Toggle") && !this.wasPressed && keyPressed && this.isToggled()) {
            this.isEnabled = !this.isEnabled;
        }
        this.wasPressed = keyPressed;
    }
    
    private void click() {
        for (int i = 0; i < this.clickCount.getValue(); ++i) {
            final String selected = this.button.getSelected();
            switch (selected) {
                case "Left": {
                    SkyblockUtils.click();
                    break;
                }
                case "Right": {
                    Keybind.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C08PacketPlayerBlockPlacement(Keybind.mc.thePlayer.getHeldItem()));
                    break;
                }
                case "Swing": {
                    Keybind.mc.thePlayer.swingItem();
                    break;
                }
            }
        }
        this.delayTimer.reset();
    }
    
    public void numberClick(final int slot, final int button) {
        Keybind.mc.playerController.windowClick(Keybind.mc.thePlayer.inventoryContainer.windowId, slot, button, 2, (EntityPlayer)Keybind.mc.thePlayer);
    }
}
