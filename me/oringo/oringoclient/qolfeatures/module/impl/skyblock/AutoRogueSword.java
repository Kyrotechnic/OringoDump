//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.impl.skyblock;

import me.oringo.oringoclient.qolfeatures.module.*;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.*;
import me.oringo.oringoclient.qolfeatures.module.settings.*;
import net.minecraftforge.fml.common.gameevent.*;
import me.oringo.oringoclient.utils.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class AutoRogueSword extends Module
{
    public NumberSetting clicks;
    public NumberSetting delay;
    public BooleanSetting onlyDung;
    private final MilliTimer time;
    
    public AutoRogueSword() {
        super("Auto Rogue", 0, Category.SKYBLOCK);
        this.clicks = new NumberSetting("Clicks", 50.0, 1.0, 1000.0, 10.0);
        this.delay = new NumberSetting("Delay", 29.0, 0.0, 100.0, 1.0);
        this.onlyDung = new BooleanSetting("Only dungeon", false);
        this.time = new MilliTimer();
        this.addSettings(this.clicks, this.delay, this.onlyDung);
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (AutoRogueSword.mc.thePlayer == null || (!SkyblockUtils.inDungeon && this.onlyDung.isEnabled()) || !this.isToggled()) {
            return;
        }
        if (this.time.hasTimePassed((long)this.delay.getValue())) {
            for (int i = 0; i < 9; ++i) {
                if (AutoRogueSword.mc.thePlayer.inventory.getStackInSlot(i) != null && AutoRogueSword.mc.thePlayer.inventory.getStackInSlot(i).getDisplayName().toLowerCase().contains("rogue sword")) {
                    PacketUtils.sendPacketNoEvent((Packet<?>)new C09PacketHeldItemChange(i));
                    for (int x = 0; x < this.clicks.getValue(); ++x) {
                        AutoRogueSword.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C08PacketPlayerBlockPlacement(AutoRogueSword.mc.thePlayer.inventory.getStackInSlot(i)));
                    }
                    PacketUtils.sendPacketNoEvent((Packet<?>)new C09PacketHeldItemChange(AutoRogueSword.mc.thePlayer.inventory.currentItem));
                    this.time.reset();
                    break;
                }
            }
        }
    }
}
