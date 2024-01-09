//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.commands.impl;

import me.oringo.oringoclient.commands.*;
import me.oringo.oringoclient.utils.*;
import net.minecraft.network.*;
import me.oringo.oringoclient.events.*;
import net.minecraft.network.play.server.*;
import net.minecraft.item.*;
import net.minecraft.network.play.client.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class WardrobeCommand extends Command
{
    private int slot;
    private MilliTimer timeout;
    
    public WardrobeCommand() {
        super("wd", new String[] { "wardrobe" });
        this.slot = -1;
        this.timeout = new MilliTimer();
    }
    
    public void execute(final String[] args) throws Exception {
        if (args.length > 0) {
            this.slot = Math.min(Math.max(Integer.parseInt(args[0]), 1), 18);
            WardrobeCommand.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C01PacketChatMessage("/pets"));
            this.timeout.reset();
        }
        else {
            WardrobeCommand.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C01PacketChatMessage("/wd"));
        }
    }
    
    @SubscribeEvent
    public void onPacket(final PacketReceivedEvent event) {
        if (this.slot != -1 && event.packet instanceof S2DPacketOpenWindow) {
            if (this.timeout.hasTimePassed(2500L)) {
                this.slot = -1;
                return;
            }
            if (((S2DPacketOpenWindow)event.packet).getWindowTitle().getFormattedText().startsWith("Pets")) {
                final int windowId = ((S2DPacketOpenWindow)event.packet).getWindowId();
                WardrobeCommand.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C0EPacketClickWindow(windowId, 48, 0, 3, (ItemStack)null, (short)0));
                WardrobeCommand.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C0EPacketClickWindow(windowId + 1, 32, 0, 3, (ItemStack)null, (short)0));
                if (this.slot <= 9) {
                    WardrobeCommand.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C0EPacketClickWindow(windowId + 2, 35 + this.slot, 0, 0, (ItemStack)null, (short)0));
                    WardrobeCommand.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C0DPacketCloseWindow(windowId + 2));
                }
                else {
                    WardrobeCommand.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C0EPacketClickWindow(windowId + 2, 53, 0, 3, (ItemStack)null, (short)0));
                    WardrobeCommand.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C0EPacketClickWindow(windowId + 3, 35 + this.slot, 0, 0, (ItemStack)null, (short)0));
                    WardrobeCommand.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C0DPacketCloseWindow(windowId + 3));
                }
                event.setCanceled(true);
            }
            else if (((S2DPacketOpenWindow)event.packet).getWindowTitle().getFormattedText().startsWith("SkyBlock Menu")) {
                event.setCanceled(true);
            }
            else if (((S2DPacketOpenWindow)event.packet).getWindowTitle().getFormattedText().startsWith("Wardrobe")) {
                event.setCanceled(true);
                this.slot = -1;
            }
        }
    }
    
    public String getDescription() {
        return "Instant wardrobe";
    }
}
