//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.impl.skyblock;

import me.oringo.oringoclient.qolfeatures.module.*;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.*;
import me.oringo.oringoclient.qolfeatures.module.settings.*;
import me.oringo.oringoclient.events.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class IceFillHelp extends Module
{
    public NumberSetting slowdown;
    public BooleanSetting noIceSlip;
    public BooleanSetting autoStop;
    
    public IceFillHelp() {
        super("Ice Fill Helper", Category.SKYBLOCK);
        this.slowdown = new NumberSetting("Ice slowdown", 0.15, 0.05, 1.0, 0.05);
        this.noIceSlip = new BooleanSetting("No ice slip", true);
        this.autoStop = new BooleanSetting("Auto stop", true);
        this.addSettings(this.autoStop, this.slowdown, this.noIceSlip);
    }
    
    @SubscribeEvent
    public void onMove(final MoveEvent event) {
        if (!this.isToggled() || !IceFillHelp.mc.thePlayer.onGround) {
            return;
        }
        final BlockPos currentPos = new BlockPos(IceFillHelp.mc.thePlayer.posX, IceFillHelp.mc.thePlayer.posY - 0.4, IceFillHelp.mc.thePlayer.posZ);
        if (IceFillHelp.mc.theWorld.getBlockState(currentPos).getBlock() == Blocks.ice) {
            event.setZ(event.getZ() * this.slowdown.getValue());
            event.setX(event.getX() * this.slowdown.getValue());
            final BlockPos nextPos = new BlockPos(IceFillHelp.mc.thePlayer.posX + event.getX(), IceFillHelp.mc.thePlayer.posY - 0.4, IceFillHelp.mc.thePlayer.posZ + event.getZ());
            if (this.autoStop.isEnabled() && !currentPos.equals((Object)nextPos) && IceFillHelp.mc.theWorld.getBlockState(nextPos).getBlock() == Blocks.ice) {
                event.setZ(0.0);
                event.setX(0.0);
            }
        }
        if (this.noIceSlip.isEnabled()) {
            Blocks.packed_ice.slipperiness = 0.6f;
            Blocks.ice.slipperiness = 0.6f;
        }
    }
    
    @Override
    public void onDisable() {
        Blocks.packed_ice.slipperiness = 0.98f;
        Blocks.ice.slipperiness = 0.98f;
    }
}
