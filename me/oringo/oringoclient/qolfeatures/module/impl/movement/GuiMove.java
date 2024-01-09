//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.impl.movement;

import me.oringo.oringoclient.qolfeatures.module.*;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.*;
import me.oringo.oringoclient.qolfeatures.module.settings.*;
import me.oringo.oringoclient.events.*;
import net.minecraft.client.gui.*;
import me.oringo.oringoclient.qolfeatures.module.impl.other.*;
import net.minecraft.client.settings.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.client.event.*;
import net.minecraft.client.gui.inventory.*;
import org.lwjgl.input.*;
import org.lwjgl.opengl.*;
import net.minecraft.inventory.*;
import me.oringo.oringoclient.utils.*;

public class GuiMove extends Module
{
    private BooleanSetting rotate;
    private BooleanSetting drag;
    private BooleanSetting hideTerminalGui;
    private NumberSetting sensivity;
    public static final KeyBinding[] binds;
    
    public GuiMove() {
        super("InvMove", Category.MOVEMENT);
        this.rotate = new BooleanSetting("Rotate", true);
        this.drag = new BooleanSetting("Alt drag", true) {
            @Override
            public boolean isHidden() {
                return !GuiMove.this.rotate.isEnabled();
            }
        };
        this.hideTerminalGui = new BooleanSetting("Hide terminals", false);
        this.sensivity = new NumberSetting("Sensivity", 1.5, 0.1, 3.0, 0.01, aBoolean -> !this.rotate.isEnabled());
        this.addSettings(this.hideTerminalGui, this.rotate, this.sensivity, this.drag);
    }
    
    @Override
    public boolean isToggled() {
        return super.isToggled();
    }
    
    @Override
    public void onDisable() {
        if (GuiMove.mc.currentScreen != null) {
            for (final KeyBinding bind : GuiMove.binds) {
                KeyBinding.setKeyBindState(bind.getKeyCode(), false);
            }
        }
    }
    
    @SubscribeEvent
    public void onGUi(final PostGuiOpenEvent event) {
        if (!(event.gui instanceof GuiChat) && this.isToggled() && Disabler.wasEnabled) {
            for (final KeyBinding bind : GuiMove.binds) {
                KeyBinding.setKeyBindState(bind.getKeyCode(), GameSettings.isKeyDown(bind));
            }
        }
    }
    
    @SubscribeEvent
    public void onRender(final RenderWorldLastEvent event) {
        if (GuiMove.mc.currentScreen != null && !(GuiMove.mc.currentScreen instanceof GuiChat) && this.isToggled() && Disabler.wasEnabled) {
            for (final KeyBinding bind : GuiMove.binds) {
                KeyBinding.setKeyBindState(bind.getKeyCode(), GameSettings.isKeyDown(bind));
            }
            if ((GuiMove.mc.currentScreen instanceof GuiContainer || GuiMove.mc.currentScreen instanceof ICrafting) && this.rotate.isEnabled()) {
                GuiMove.mc.mouseHelper.mouseXYChange();
                float f = GuiMove.mc.gameSettings.mouseSensitivity * 0.6f + 0.2f;
                f *= (float)this.sensivity.getValue();
                final float f2 = f * f * f * 8.0f;
                final float f3 = GuiMove.mc.mouseHelper.deltaX * f2;
                final float f4 = GuiMove.mc.mouseHelper.deltaY * f2;
                int i = 1;
                if (GuiMove.mc.gameSettings.invertMouse) {
                    i = -1;
                }
                if (Keyboard.isKeyDown(56) && Mouse.isButtonDown(2) && this.drag.isEnabled()) {
                    Mouse.setCursorPosition(Display.getWidth() / 2, Display.getHeight() / 6);
                    GuiMove.mc.setIngameNotInFocus();
                    Mouse.setGrabbed(false);
                }
                GuiMove.mc.thePlayer.setAngles(f3, f4 * i);
            }
        }
    }
    
    public boolean shouldHideGui(final ContainerChest chest) {
        return SkyblockUtils.isTerminal(chest.getLowerChestInventory().getName()) && this.isToggled() && this.hideTerminalGui.isEnabled();
    }
    
    static {
        binds = new KeyBinding[] { GuiMove.mc.gameSettings.keyBindSneak, GuiMove.mc.gameSettings.keyBindJump, GuiMove.mc.gameSettings.keyBindSprint, GuiMove.mc.gameSettings.keyBindForward, GuiMove.mc.gameSettings.keyBindBack, GuiMove.mc.gameSettings.keyBindLeft, GuiMove.mc.gameSettings.keyBindRight };
    }
}
