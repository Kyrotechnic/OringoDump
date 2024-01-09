//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.impl.other;

import me.oringo.oringoclient.qolfeatures.module.*;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.*;
import me.oringo.oringoclient.qolfeatures.module.settings.*;
import me.oringo.oringoclient.events.*;
import java.util.*;
import com.mojang.realmsclient.gui.*;
import me.oringo.oringoclient.*;
import net.minecraft.client.*;
import net.minecraft.init.*;
import net.minecraft.scoreboard.*;
import net.minecraftforge.fml.common.eventhandler.*;
import io.netty.channel.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import net.minecraft.network.play.server.*;

public class TntRunPing extends Module
{
    NumberSetting ping;
    
    public TntRunPing() {
        super("TNT Run ping", 0, Category.OTHER);
        this.ping = new NumberSetting("Ping", 2000.0, 1.0, 2000.0, 1.0);
        this.addSettings(this.ping);
    }
    
    @SubscribeEvent
    public void onPacket(final PacketReceivedEvent event) {
        if (!this.isToggled()) {
            return;
        }
        try {
            final ScoreObjective objective = TntRunPing.mc.thePlayer.getWorldScoreboard().getObjectiveInDisplaySlot(1);
            if (!Arrays.asList("TNT RUN", "PVP RUN").contains(ChatFormatting.stripFormatting(objective.getDisplayName()))) {
                return;
            }
        }
        catch (Exception e) {
            return;
        }
        if (event.packet instanceof S22PacketMultiBlockChange && ((S22PacketMultiBlockChange)event.packet).getChangedBlocks().length <= 10) {
            event.setCanceled(true);
            for (final S22PacketMultiBlockChange.BlockUpdateData changedBlock : ((S22PacketMultiBlockChange)event.packet).getChangedBlocks()) {
                this.threadBreak(event.context, changedBlock.getPos(), changedBlock.getBlockState());
            }
        }
        if (event.packet instanceof S23PacketBlockChange) {
            if (OringoClient.stop.contains(((S23PacketBlockChange)event.packet).getBlockPosition())) {
                event.setCanceled(true);
            }
            if (!Minecraft.getMinecraft().theWorld.getBlockState(((S23PacketBlockChange)event.packet).getBlockPosition()).getBlock().equals(Blocks.wool) && ((S23PacketBlockChange)event.packet).getBlockState().getBlock().equals(Blocks.air)) {
                event.setCanceled(true);
                this.threadBreak(event.context, ((S23PacketBlockChange)event.packet).getBlockPosition(), ((S23PacketBlockChange)event.packet).getBlockState());
            }
        }
    }
    
    private void threadBreak(final ChannelHandlerContext context, final BlockPos pos, final IBlockState state) {
        if (!this.isToggled()) {
            return;
        }
        Minecraft.getMinecraft().theWorld.setBlockState(pos, Blocks.wool.getDefaultState());
        int i;
        new Thread(() -> {
            OringoClient.stop.add(pos);
            for (i = 0; i < 10; ++i) {
                try {
                    Thread.sleep((long)((long)this.ping.getValue() / 10.0));
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    TntRunPing.mc.getNetHandler().handleBlockBreakAnim(new S25PacketBlockBreakAnim(pos.hashCode(), pos, i));
                }
                catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            OringoClient.stop.remove(pos);
            Minecraft.getMinecraft().theWorld.setBlockState(pos, state);
        }).start();
    }
}
