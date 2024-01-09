//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.mixins;

import net.minecraft.util.*;
import net.minecraft.client.settings.*;
import me.oringo.oringoclient.events.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.spongepowered.asm.mixin.*;

@Mixin(value = { MovementInputFromOptions.class }, priority = 1)
public abstract class MovementInputFromOptionsMixin extends MovementInputMixin
{
    @Shadow
    @Final
    private GameSettings gameSettings;
    
    @Overwrite
    public void updatePlayerMoveState() {
        this.moveStrafe = 0.0f;
        this.moveForward = 0.0f;
        if (this.gameSettings.keyBindForward.isKeyDown()) {
            ++this.moveForward;
        }
        if (this.gameSettings.keyBindBack.isKeyDown()) {
            --this.moveForward;
        }
        if (this.gameSettings.keyBindLeft.isKeyDown()) {
            ++this.moveStrafe;
        }
        if (this.gameSettings.keyBindRight.isKeyDown()) {
            --this.moveStrafe;
        }
        final MoveStateUpdateEvent event = new MoveStateUpdateEvent(this.moveForward, this.moveStrafe, this.gameSettings.keyBindJump.isKeyDown(), this.gameSettings.keyBindSneak.isKeyDown());
        if (MinecraftForge.EVENT_BUS.post((Event)event)) {
            return;
        }
        this.jump = event.isJump();
        this.sneak = event.isSneak();
        this.moveForward = event.getForward();
        this.moveStrafe = event.getStrafe();
        if (this.sneak) {
            this.moveStrafe *= (float)0.3;
            this.moveForward *= (float)0.3;
        }
    }
}
