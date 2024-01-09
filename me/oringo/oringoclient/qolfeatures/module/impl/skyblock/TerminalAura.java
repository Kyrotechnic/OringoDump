//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.impl.skyblock;

import me.oringo.oringoclient.qolfeatures.module.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.*;
import me.oringo.oringoclient.qolfeatures.module.settings.*;
import net.minecraft.network.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.network.play.server.*;
import me.oringo.oringoclient.events.*;
import net.minecraft.network.play.client.*;
import net.minecraftforge.event.world.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;
import net.minecraft.entity.player.*;
import me.oringo.oringoclient.utils.*;
import net.minecraft.inventory.*;

public class TerminalAura extends Module
{
    public static ArrayList<Entity> finishedTerms;
    public static EntityArmorStand currentTerminal;
    public static long termTime;
    public static long ping;
    public static long pingAt;
    public static boolean pinged;
    public BooleanSetting onGroud;
    public NumberSetting reach;
    
    public TerminalAura() {
        super("Terminal Aura", 0, Category.SKYBLOCK);
        this.onGroud = new BooleanSetting("Only ground", true);
        this.reach = new NumberSetting("Terminal Reach", 6.0, 2.0, 6.0, 0.1);
        this.addSettings(this.reach, this.onGroud);
    }
    
    @SubscribeEvent
    public void onTick(final MotionUpdateEvent.Post event) {
        if (TerminalAura.mc.thePlayer == null || !this.isToggled() || !SkyblockUtils.inDungeon) {
            return;
        }
        if (TerminalAura.currentTerminal != null && !this.isInTerminal() && System.currentTimeMillis() - TerminalAura.termTime > TerminalAura.ping * 2L) {
            TerminalAura.finishedTerms.add((Entity)TerminalAura.currentTerminal);
            TerminalAura.currentTerminal = null;
        }
        if (TerminalAura.mc.thePlayer.ticksExisted % 20 == 0 && !TerminalAura.pinged) {
            TerminalAura.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C16PacketClientStatus(C16PacketClientStatus.EnumState.REQUEST_STATS));
            TerminalAura.pinged = true;
            TerminalAura.pingAt = System.currentTimeMillis();
        }
        if (TerminalAura.currentTerminal == null && (TerminalAura.mc.thePlayer.onGround || !this.onGroud.isEnabled()) && !this.isInTerminal() && !TerminalAura.mc.thePlayer.isInLava()) {
            final Iterator<Entity> iterator = this.getValidTerminals().iterator();
            if (iterator.hasNext()) {
                final Entity entity = iterator.next();
                this.openTerminal((EntityArmorStand)entity);
            }
        }
    }
    
    @SubscribeEvent
    public void onPacket(final PacketReceivedEvent event) {
        if (event.packet instanceof S2EPacketCloseWindow && this.isInTerminal() && TerminalAura.currentTerminal != null) {
            this.openTerminal(TerminalAura.currentTerminal);
        }
        if (event.packet instanceof S37PacketStatistics && TerminalAura.pinged) {
            TerminalAura.pinged = false;
            TerminalAura.ping = System.currentTimeMillis() - TerminalAura.pingAt;
        }
    }
    
    @SubscribeEvent
    public void onSent(final PacketSentEvent.Post event) {
        if (event.packet instanceof C0DPacketCloseWindow && this.isInTerminal() && TerminalAura.currentTerminal != null) {
            this.openTerminal(TerminalAura.currentTerminal);
        }
    }
    
    @SubscribeEvent
    public void onWorldChange(final WorldEvent.Load event) {
        TerminalAura.finishedTerms.clear();
        TerminalAura.currentTerminal = null;
        TerminalAura.pinged = false;
        TerminalAura.termTime = System.currentTimeMillis();
        TerminalAura.ping = 300L;
        TerminalAura.pingAt = -1L;
    }
    
    private List<Entity> getValidTerminals() {
        return (List<Entity>)TerminalAura.mc.theWorld.getLoadedEntityList().stream().filter(entity -> entity instanceof EntityArmorStand).filter(entity -> entity.getName().contains("CLICK HERE")).filter(entity -> this.getDistance(entity) < this.reach.getValue() - 0.4).filter(entity -> !TerminalAura.finishedTerms.contains(entity)).sorted(Comparator.comparingDouble((ToDoubleFunction<? super T>)TerminalAura.mc.thePlayer::getDistanceToEntity)).collect(Collectors.toList());
    }
    
    private void openTerminal(final EntityArmorStand entity) {
        TerminalAura.mc.playerController.interactWithEntitySendPacket((EntityPlayer)TerminalAura.mc.thePlayer, (Entity)entity);
        TerminalAura.currentTerminal = entity;
        TerminalAura.termTime = System.currentTimeMillis();
    }
    
    private double getDistance(final EntityArmorStand terminal) {
        return RotationUtils.getClosestPointInAABB(TerminalAura.mc.thePlayer.getPositionEyes(1.0f), terminal.getEntityBoundingBox()).distanceTo(TerminalAura.mc.thePlayer.getPositionEyes(1.0f));
    }
    
    private boolean isInTerminal() {
        if (TerminalAura.mc.thePlayer == null) {
            return false;
        }
        final Container container = TerminalAura.mc.thePlayer.openContainer;
        String name = "";
        if (container instanceof ContainerChest) {
            name = ((ContainerChest)container).getLowerChestInventory().getName();
        }
        return container instanceof ContainerChest && (name.contains("Correct all the panes!") || name.contains("Navigate the maze!") || name.contains("Click in order!") || name.contains("What starts with:") || name.contains("Select all the") || name.contains("Change all to same color!") || name.contains("Click the button on time!"));
    }
    
    static {
        TerminalAura.finishedTerms = new ArrayList<Entity>();
        TerminalAura.termTime = -1L;
        TerminalAura.ping = 300L;
        TerminalAura.pingAt = -1L;
    }
}
