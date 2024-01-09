//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.commands.impl;

import net.minecraft.network.play.client.*;
import me.oringo.oringoclient.mixins.packet.*;
import net.minecraft.network.*;
import net.minecraft.entity.item.*;
import java.util.*;
import java.util.function.*;
import net.minecraft.entity.*;
import net.minecraft.event.*;
import me.oringo.oringoclient.commands.*;
import net.minecraft.client.*;
import net.minecraft.util.*;

public class ArmorStandsCommand extends Command
{
    public ArmorStandsCommand() {
        super("armorstands", new String[0]);
    }
    
    public void execute(final String[] args) throws Exception {
        if (args.length == 1) {
            final C02PacketUseEntity packet = new C02PacketUseEntity();
            ((C02Accessor)packet).setEntityId(Integer.parseInt(args[0]));
            ((C02Accessor)packet).setAction(C02PacketUseEntity.Action.INTERACT);
            ArmorStandsCommand.mc.getNetHandler().getNetworkManager().sendPacket((Packet)packet);
            return;
        }
        ArmorStandsCommand.mc.theWorld.loadedEntityList.stream().filter(entity -> entity instanceof EntityArmorStand && entity.getDisplayName().getFormattedText().length() > 5 && entity.hasCustomName()).sorted(Comparator.comparingDouble((ToDoubleFunction<? super Object>)ArmorStandsCommand.mc.thePlayer::getDistanceToEntity).reversed()).forEach(ArmorStandsCommand::sendEntityInteract);
    }
    
    private static void sendEntityInteract(final Entity entity) {
        final ChatComponentText chatComponentText = new ChatComponentText("Name: " + entity.getDisplayName().getFormattedText());
        final ChatStyle style = new ChatStyle();
        style.setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("%sarmorstands %s", CommandHandler.getCommandPrefix(), entity.getEntityId())));
        chatComponentText.setChatStyle(style);
        Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent)chatComponentText);
    }
    
    public String getDescription() {
        return "Shows you a list of loaded armor stands.";
    }
}
