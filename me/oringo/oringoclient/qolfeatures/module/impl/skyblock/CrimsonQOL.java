//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.impl.skyblock;

import me.oringo.oringoclient.qolfeatures.module.*;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.*;
import me.oringo.oringoclient.qolfeatures.module.settings.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;
import com.mojang.realmsclient.gui.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import java.util.*;
import java.util.regex.*;
import net.minecraftforge.fml.common.eventhandler.*;
import me.oringo.oringoclient.events.*;
import net.minecraft.entity.boss.*;
import java.awt.*;
import me.oringo.oringoclient.utils.*;
import net.minecraft.event.*;
import me.oringo.oringoclient.commands.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraft.item.*;

public class CrimsonQOL extends Module
{
    public static final BooleanSetting autoHostage;
    public static final BooleanSetting kuudraESP;
    public static final BooleanSetting autoCloak;
    public static final NumberSetting time;
    public static final NumberSetting distance;
    private int hostageId;
    private boolean hasCloaked;
    
    public CrimsonQOL() {
        super("Crimson QOL", Category.SKYBLOCK);
        this.hostageId = -1;
        this.addSettings(CrimsonQOL.autoHostage, CrimsonQOL.kuudraESP, CrimsonQOL.autoCloak, CrimsonQOL.time, CrimsonQOL.distance);
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (this.isToggled() && event.phase == TickEvent.Phase.END && CrimsonQOL.mc.thePlayer != null && CrimsonQOL.mc.theWorld != null) {
            final boolean hostage = this.hostageId != -1;
            boolean cloak = false;
            final int slot = PlayerUtils.getHotbar(stack -> stack.getItem() instanceof ItemSword && stack.getDisplayName().contains("Wither Cloak"));
            this.hostageId = -1;
            final Pattern pattern = Pattern.compile("(.*)s [1-8] hits", 2);
            for (final EntityArmorStand entity : CrimsonQOL.mc.theWorld.getEntities((Class)EntityArmorStand.class, e -> true)) {
                if (CrimsonQOL.autoHostage.isEnabled() && entity.getDisplayName().getUnformattedText().contains("Hostage")) {
                    this.hostageId = entity.getEntityId();
                }
                if (CrimsonQOL.autoCloak.isEnabled() && slot != -1 && entity.getDistanceSqToEntity((Entity)CrimsonQOL.mc.thePlayer) < CrimsonQOL.distance.getValue() * CrimsonQOL.distance.getValue()) {
                    final Matcher matcher = pattern.matcher(ChatFormatting.stripFormatting(entity.getDisplayName().getUnformattedText()));
                    if (!matcher.find()) {
                        continue;
                    }
                    final String name = matcher.group(1);
                    final int time = Integer.parseInt(name);
                    if (time > CrimsonQOL.time.getValue()) {
                        continue;
                    }
                    cloak = true;
                    if (this.hasCloaked) {
                        continue;
                    }
                    this.hasCloaked = true;
                    CrimsonQOL.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C09PacketHeldItemChange(slot));
                    CrimsonQOL.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C08PacketPlayerBlockPlacement(CrimsonQOL.mc.thePlayer.inventory.getStackInSlot(slot)));
                    CrimsonQOL.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C09PacketHeldItemChange(CrimsonQOL.mc.thePlayer.inventory.currentItem));
                }
            }
            if (CrimsonQOL.autoCloak.isEnabled() && this.hasCloaked && !cloak) {
                if (slot != -1) {
                    CrimsonQOL.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C09PacketHeldItemChange(slot));
                    CrimsonQOL.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C08PacketPlayerBlockPlacement(CrimsonQOL.mc.thePlayer.inventory.getStackInSlot(slot)));
                    CrimsonQOL.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C09PacketHeldItemChange(CrimsonQOL.mc.thePlayer.inventory.currentItem));
                }
                this.hasCloaked = false;
            }
            if (!hostage && this.hostageId != -1) {
                sendEntityInteract("§bOringoClient §3» §7§aClick here to interact with hostage!", this.hostageId);
            }
        }
    }
    
    @SubscribeEvent
    public void kuudraESP(final RenderLayersEvent event) {
        if (this.isToggled() && event.entity instanceof EntityWither && ((EntityWither)event.entity).getInvulTime() > 500 && CrimsonQOL.kuudraESP.isEnabled()) {
            OutlineUtils.outlineESP(event, Color.GREEN);
        }
    }
    
    private static void sendEntityInteract(final String message, final int entity) {
        final ChatComponentText chatComponentText = new ChatComponentText(message);
        final ChatStyle style = new ChatStyle();
        style.setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("%sarmorstands %s", CommandHandler.getCommandPrefix(), entity)));
        chatComponentText.setChatStyle(style);
        Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent)chatComponentText);
    }
    
    static {
        autoHostage = new BooleanSetting("Auto Hostage", true);
        kuudraESP = new BooleanSetting("Kuudra Outline", true);
        autoCloak = new BooleanSetting("Auto Cloak", true);
        time = new NumberSetting("Time", 1.0, 1.0, 8.0, 1.0, a -> !CrimsonQOL.autoCloak.isEnabled());
        distance = new NumberSetting("Distance", 30.0, 1.0, 64.0, 1.0, a -> !CrimsonQOL.autoCloak.isEnabled());
    }
}
