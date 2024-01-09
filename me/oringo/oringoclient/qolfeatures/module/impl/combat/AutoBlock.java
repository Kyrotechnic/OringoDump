//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.impl.combat;

import me.oringo.oringoclient.qolfeatures.module.*;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.*;
import me.oringo.oringoclient.qolfeatures.module.settings.*;
import net.minecraftforge.event.entity.player.*;
import me.oringo.oringoclient.*;
import net.minecraft.entity.player.*;
import me.oringo.oringoclient.utils.*;
import net.minecraftforge.fml.common.eventhandler.*;
import me.oringo.oringoclient.events.*;
import net.minecraft.item.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.*;

public class AutoBlock extends Module
{
    public ModeSetting mode;
    public NumberSetting blockTime;
    public BooleanSetting players;
    public BooleanSetting mobs;
    public BooleanSetting onDamage;
    public BooleanSetting noSlow;
    public MilliTimer blockTimer;
    private boolean isBlocking;
    
    public AutoBlock() {
        super("AutoBlock", Category.COMBAT);
        this.mode = new ModeSetting("Mode", "Hypixel", new String[] { "Hypixel", "Vanilla" });
        this.blockTime = new NumberSetting("Block time", 500.0, 50.0, 2000.0, 50.0);
        this.players = new BooleanSetting("Players", true);
        this.mobs = new BooleanSetting("Mobs", false);
        this.onDamage = new BooleanSetting("on Damage", true);
        this.noSlow = new BooleanSetting("No Slow", false);
        this.blockTimer = new MilliTimer();
        this.addSettings(this.mode, this.blockTime, this.players, this.mobs, this.onDamage, this.noSlow);
    }
    
    @SubscribeEvent
    public void onAttacK(final AttackEntityEvent event) {
        if (!this.isToggled() || OringoClient.killAura.isToggled()) {
            return;
        }
        if ((event.entityPlayer == AutoBlock.mc.thePlayer && ((event.target instanceof EntityPlayer && this.players.isEnabled()) || (!(event.target instanceof EntityPlayer) && this.mobs.isEnabled()))) || (event.target == AutoBlock.mc.thePlayer && this.onDamage.isEnabled())) {
            this.blockTimer.reset();
            if (event.entityPlayer == AutoBlock.mc.thePlayer && (!MovementUtils.isMoving() || this.mode.is("Vanilla")) && this.isBlocking) {
                this.stopBlocking();
            }
        }
    }
    
    @SubscribeEvent
    public void onUpdate(final MotionUpdateEvent.Post event) {
        if (!this.isToggled() || OringoClient.killAura.isToggled()) {
            return;
        }
        if (!this.blockTimer.hasTimePassed((long)this.blockTime.getValue())) {
            if ((!this.isBlocking || this.mode.is("Hypixel")) && this.canBlock()) {
                this.startBlocking();
            }
        }
        else if (this.isBlocking) {
            this.stopBlocking();
        }
    }
    
    public boolean canBlock() {
        return AutoBlock.mc.thePlayer.getHeldItem() != null && AutoBlock.mc.thePlayer.getHeldItem().getItem() instanceof ItemSword;
    }
    
    private void startBlocking() {
        AutoBlock.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C08PacketPlayerBlockPlacement(AutoBlock.mc.thePlayer.getHeldItem()));
        this.isBlocking = true;
    }
    
    private void stopBlocking() {
        if (this.isBlocking) {
            AutoBlock.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
            this.isBlocking = false;
        }
    }
    
    public boolean isBlocking() {
        return OringoClient.autoBlock.canBlock() && this.isBlocking && !this.noSlow.isEnabled();
    }
}
