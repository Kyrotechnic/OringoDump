//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.impl.other;

import me.oringo.oringoclient.qolfeatures.module.*;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.*;
import me.oringo.oringoclient.qolfeatures.module.settings.*;
import me.oringo.oringoclient.events.*;
import net.minecraft.network.play.client.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.client.event.*;
import com.mojang.realmsclient.gui.*;

public class ChatBypass extends Module
{
    private String prefix;
    private static String normal;
    private static String custom;
    public ModeSetting mode;
    
    public ChatBypass() {
        super("Chat bypass", 0, Category.OTHER);
        this.prefix = "";
        this.mode = new ModeSetting("mode", "font", new String[] { "font", "dots" });
        this.addSettings(this.mode);
    }
    
    @SubscribeEvent
    public void onPacket(final PacketSentEvent event) {
        if (event.packet instanceof C01PacketChatMessage) {
            this.prefix = "";
            if (((C01PacketChatMessage)event.packet).getMessage().charAt(0) == '/') {
                this.prefix = ((C01PacketChatMessage)event.packet).getMessage().split(" ")[0];
                if (this.prefix.equalsIgnoreCase("/msg") || this.prefix.equalsIgnoreCase("/message") || this.prefix.equalsIgnoreCase("/t") || this.prefix.equalsIgnoreCase("/tell") || this.prefix.equalsIgnoreCase("/w")) {
                    this.prefix += " ";
                    if (((C01PacketChatMessage)event.packet).getMessage().split(" ").length > 1) {
                        this.prefix += ((C01PacketChatMessage)event.packet).getMessage().split(" ")[1];
                    }
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onChat(final ClientChatReceivedEvent event) {
        if (!this.isToggled()) {
            return;
        }
        final String blockedmsg = ChatFormatting.stripFormatting(event.message.getFormattedText());
        if (event.message.getFormattedText().equals("§r§6§m-----------------------------------------§r")) {
            event.setCanceled(true);
        }
        if (blockedmsg.startsWith("We blocked your comment \"")) {
            final StringBuilder msg = new StringBuilder();
            final StringBuilder message = new StringBuilder();
            for (int i = 1; i < blockedmsg.split("\"").length - 1; ++i) {
                msg.append(blockedmsg.split("\"")[i]);
            }
            message.append(this.prefix).append(" ");
            for (int i = 0; i < msg.toString().length(); ++i) {
                final char Char = msg.toString().charAt(i);
                final String selected = this.mode.getSelected();
                switch (selected) {
                    case "dots": {
                        message.append(Char + ((Char == ' ') ? "" : "\u02cc"));
                        break;
                    }
                    case "font": {
                        message.append(ChatBypass.normal.contains(Char + "") ? ChatBypass.custom.toCharArray()[ChatBypass.normal.indexOf(Char)] : Char);
                        break;
                    }
                }
            }
            event.setCanceled(true);
            final StringBuilder sb;
            new Thread(() -> {
                try {
                    Thread.sleep(550L);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ChatBypass.mc.thePlayer.sendChatMessage(sb.toString());
            }).start();
        }
    }
    
    static {
        ChatBypass.normal = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM0123456789";
        ChatBypass.custom = "\uff51\uff57\uff45\uff52\uff54\uff59\uff55\uff49\uff4f\uff50\uff41\uff53\uff44\uff46\uff47\uff48\uff4a\uff4b\uff4c\uff5a\uff58\uff43\uff56\uff42\uff4e\uff4d\uff51\uff57\uff45\uff52\uff54\uff59\uff55\uff49\uff4f\uff50\uff41\uff53\uff44\uff46\uff47\uff48\uff4a\uff4b\uff4c\uff5a\uff58\uff43\uff56\uff42\uff4e\uff4d\uff10\uff11\uff12\uff13\uff14\uff15\uff16\uff17\uff18\uff19";
    }
}
