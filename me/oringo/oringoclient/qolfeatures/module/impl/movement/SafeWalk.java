//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.impl.movement;

import me.oringo.oringoclient.qolfeatures.module.*;
import net.minecraft.client.settings.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class SafeWalk extends Module
{
    public SafeWalk() {
        super("Eagle", 0, Category.MOVEMENT);
    }
    
    @Override
    public void onDisable() {
        KeyBinding.setKeyBindState(SafeWalk.mc.gameSettings.keyBindSneak.getKeyCode(), GameSettings.isKeyDown(SafeWalk.mc.gameSettings.keyBindSneak));
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (SafeWalk.mc.thePlayer == null || SafeWalk.mc.theWorld == null || !this.isToggled() || SafeWalk.mc.currentScreen != null) {
            return;
        }
        final BlockPos BP = new BlockPos(SafeWalk.mc.thePlayer.posX, SafeWalk.mc.thePlayer.posY - 0.5, SafeWalk.mc.thePlayer.posZ);
        if (SafeWalk.mc.theWorld.getBlockState(BP).getBlock() == Blocks.air && SafeWalk.mc.theWorld.getBlockState(BP.down()).getBlock() == Blocks.air && SafeWalk.mc.thePlayer.onGround && SafeWalk.mc.thePlayer.movementInput.moveForward < 0.1f) {
            KeyBinding.setKeyBindState(SafeWalk.mc.gameSettings.keyBindSneak.getKeyCode(), true);
        }
        else {
            KeyBinding.setKeyBindState(SafeWalk.mc.gameSettings.keyBindSneak.getKeyCode(), GameSettings.isKeyDown(SafeWalk.mc.gameSettings.keyBindSneak));
        }
    }
}
