//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.impl.player;

import me.oringo.oringoclient.qolfeatures.module.*;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.*;
import me.oringo.oringoclient.qolfeatures.module.settings.*;
import net.minecraftforge.fml.common.gameevent.*;
import me.oringo.oringoclient.qolfeatures.module.impl.other.*;
import me.oringo.oringoclient.utils.*;
import net.minecraft.item.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class ArmorSwap extends Module
{
    public static NumberSetting items;
    public static NumberSetting startIndex;
    private boolean wasPressed;
    
    public ArmorSwap() {
        super("ArmorSwapper", Category.PLAYER);
        this.addSettings(ArmorSwap.items, ArmorSwap.startIndex);
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (this.isToggled() && ArmorSwap.mc.thePlayer != null && Disabler.wasEnabled) {
            if (this.isPressed() && !this.wasPressed) {
                for (int i = 0; i < ArmorSwap.items.getValue(); ++i) {
                    if (ArmorSwap.mc.thePlayer.inventoryContainer.getSlot((int)(ArmorSwap.startIndex.getValue() + i)).getHasStack()) {
                        final ItemStack stack = ArmorSwap.mc.thePlayer.inventoryContainer.getSlot((int)(ArmorSwap.startIndex.getValue() + i)).getStack();
                        int button = -1;
                        if (stack.getItem() instanceof ItemArmor) {
                            button = ((ItemArmor)stack.getItem()).armorType;
                        }
                        else if (stack.getItem() instanceof ItemSkull) {
                            button = 0;
                        }
                        if (button != -1) {
                            PlayerUtils.numberClick((int)(ArmorSwap.startIndex.getValue() + i), 0);
                            PlayerUtils.numberClick(5 + button, 0);
                            PlayerUtils.numberClick((int)(ArmorSwap.startIndex.getValue() + i), 0);
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
    
    static {
        ArmorSwap.items = new NumberSetting("Armor count", 1.0, 1.0, 4.0, 1.0);
        ArmorSwap.startIndex = new NumberSetting("Start index", 9.0, 9.0, 35.0, 1.0);
    }
}
