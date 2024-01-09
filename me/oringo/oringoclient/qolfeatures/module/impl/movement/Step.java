//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.impl.movement;

import me.oringo.oringoclient.qolfeatures.module.*;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.*;
import me.oringo.oringoclient.qolfeatures.module.settings.*;
import me.oringo.oringoclient.*;
import me.oringo.oringoclient.utils.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import me.oringo.oringoclient.mixins.*;
import net.minecraftforge.fml.common.eventhandler.*;
import me.oringo.oringoclient.events.*;

public class Step extends Module
{
    public ModeSetting mode;
    public NumberSetting timer;
    private boolean isStepping;
    private int stage;
    
    public Step() {
        super("Step", Category.MOVEMENT);
        this.mode = new ModeSetting("Mode", "NCP", new String[] { "NCP" });
        this.timer = new NumberSetting("Timer", 0.4, 0.1, 1.0, 0.1);
        this.addSettings(this.mode, this.timer);
    }
    
    @SubscribeEvent
    public void onStep(final StepEvent event) {
        if (this.isToggled() && !OringoClient.speed.isToggled() && !Step.mc.thePlayer.movementInput.jump && !Step.mc.thePlayer.isInWater() && !Step.mc.thePlayer.isInLava()) {
            if (event instanceof StepEvent.Post && PlayerUtils.getJumpMotion() < 1.0) {
                if (event.getHeight() > 0.87) {
                    for (final double offset : new double[] { event.getHeight() * 0.42, event.getHeight() * 0.75 }) {
                        Step.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C03PacketPlayer.C04PacketPlayerPosition(Step.mc.thePlayer.posX, Step.mc.thePlayer.posY + offset, Step.mc.thePlayer.posZ, false));
                    }
                    if (this.timer.getValue() != 1.0) {
                        ((MinecraftAccessor)Step.mc).getTimer().timerSpeed = (float)this.timer.getValue();
                        this.isStepping = true;
                    }
                }
            }
            else if (Step.mc.thePlayer.onGround) {
                event.setHeight(1.0);
            }
        }
    }
    
    @SubscribeEvent
    public void onMove(final MoveEvent event) {
        if (this.isStepping && this.mode.is("NCP")) {
            this.isStepping = false;
            ((MinecraftAccessor)Step.mc).getTimer().timerSpeed = 1.0f;
        }
    }
}
