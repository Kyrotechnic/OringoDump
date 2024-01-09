//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures;

import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.material.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class AttackQueue
{
    public static boolean attack;
    private static int ticks;
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (AttackQueue.ticks != 0) {
            --AttackQueue.ticks;
        }
        final Minecraft mc = Minecraft.getMinecraft();
        if (mc.thePlayer != null && AttackQueue.attack && (AttackQueue.ticks == 0 || (mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit.equals((Object)MovingObjectPosition.MovingObjectType.ENTITY)))) {
            mc.thePlayer.swingItem();
            Label_0201: {
                if (mc.objectMouseOver != null) {
                    switch (mc.objectMouseOver.typeOfHit) {
                        case ENTITY: {
                            mc.playerController.attackEntity((EntityPlayer)mc.thePlayer, mc.objectMouseOver.entityHit);
                            break Label_0201;
                        }
                        case BLOCK: {
                            final BlockPos blockpos = mc.objectMouseOver.getBlockPos();
                            if (mc.theWorld.getBlockState(blockpos).getBlock().getMaterial() != Material.air) {
                                mc.playerController.clickBlock(blockpos, mc.objectMouseOver.sideHit);
                                break Label_0201;
                            }
                            break;
                        }
                    }
                    if (mc.playerController.isNotCreative()) {
                        AttackQueue.ticks = 10;
                    }
                }
            }
            AttackQueue.attack = false;
        }
    }
    
    static {
        AttackQueue.attack = false;
        AttackQueue.ticks = 0;
    }
}
