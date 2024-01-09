//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.impl.macro;

import me.oringo.oringoclient.qolfeatures.module.*;
import me.oringo.oringoclient.qolfeatures.module.settings.*;
import me.oringo.oringoclient.events.*;
import me.oringo.oringoclient.mixins.entity.*;
import me.oringo.oringoclient.utils.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class AutoFish extends Module
{
    private final MilliTimer hookDelay;
    private static int nextDelay;
    private static int waterticks;
    private boolean hook;
    
    public AutoFish() {
        super("Auto Fish", Category.OTHER);
        this.hookDelay = new MilliTimer();
        this.addSettings(new Setting[0]);
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
    }
    
    @SubscribeEvent
    public void onTick(final PlayerUpdateEvent event) {
        if (this.isToggled()) {
            if (AutoFish.mc.thePlayer.fishEntity == null || (((EntityFishHookAccessor)AutoFish.mc.thePlayer.fishEntity).inGround() && MathUtil.hypot(AutoFish.mc.thePlayer.fishEntity.motionZ, AutoFish.mc.thePlayer.fishEntity.motionX) < 0.001)) {
                if (this.hookDelay.hasTimePassed(300L)) {
                    AutoFish.mc.playerController.sendUseItem((EntityPlayer)AutoFish.mc.thePlayer, (World)AutoFish.mc.theWorld, AutoFish.mc.thePlayer.getHeldItem());
                    this.hookDelay.reset();
                }
                this.hook = false;
                AutoFish.waterticks = 0;
            }
            else if (AutoFish.mc.thePlayer.fishEntity.isInWater() || AutoFish.mc.thePlayer.fishEntity.isInLava()) {
                ++AutoFish.waterticks;
                if (!this.hook && AutoFish.waterticks > 75 && MathUtil.hypot(AutoFish.mc.thePlayer.fishEntity.motionZ, AutoFish.mc.thePlayer.fishEntity.motionX) < 0.001 && AutoFish.mc.thePlayer.fishEntity.posY - AutoFish.mc.thePlayer.fishEntity.prevPosY < -0.04) {
                    this.hook = true;
                    AutoFish.nextDelay = (int)MathUtil.getRandomInRange(75.0, 200.0);
                    if (this.hookDelay.hasTimePassed(AutoFish.nextDelay)) {
                        this.hookDelay.reset();
                    }
                }
                if (this.hook && this.hookDelay.hasTimePassed(AutoFish.nextDelay)) {
                    AutoFish.mc.playerController.sendUseItem((EntityPlayer)AutoFish.mc.thePlayer, (World)AutoFish.mc.theWorld, AutoFish.mc.thePlayer.getHeldItem());
                    AutoFish.mc.thePlayer.fishEntity = null;
                    this.hookDelay.reset();
                    AutoFish.waterticks = 0;
                    this.hook = false;
                }
            }
        }
    }
}
