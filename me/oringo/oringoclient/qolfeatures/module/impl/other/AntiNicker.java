//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.impl.other;

import me.oringo.oringoclient.qolfeatures.module.*;
import com.mojang.authlib.*;
import java.util.concurrent.atomic.*;
import com.google.gson.*;
import com.mojang.authlib.properties.*;
import net.minecraftforge.event.entity.*;
import net.minecraft.client.entity.*;
import me.oringo.oringoclient.utils.*;
import com.mojang.realmsclient.gui.*;
import net.minecraft.entity.player.*;
import me.oringo.oringoclient.*;
import net.minecraftforge.fml.common.eventhandler.*;
import me.oringo.oringoclient.events.*;
import java.util.*;

public class AntiNicker extends Module
{
    public static ArrayList<UUID> nicked;
    
    public AntiNicker() {
        super("Anti Nicker", 0, Category.OTHER);
    }
    
    public static String getRealName(final GameProfile profile) {
        final AtomicReference<String> toReturn = new AtomicReference<String>("");
        JsonParser parser;
        final AtomicReference<String> atomicReference;
        profile.getProperties().entries().forEach(entry -> {
            if (entry.getKey().equals("textures")) {
                try {
                    parser = new JsonParser();
                    atomicReference.set(parser.parse(new String(Base64.getDecoder().decode(((Property)entry.getValue()).getValue()))).getAsJsonObject().get("profileName").getAsString());
                }
                catch (Exception ex) {}
            }
            return;
        });
        return toReturn.get();
    }
    
    @SubscribeEvent
    public void onWorldJoin(final EntityJoinWorldEvent e) {
        if (!this.isToggled()) {
            return;
        }
        if (e.entity instanceof EntityOtherPlayerMP && !AntiNicker.nicked.contains(e.entity.getUniqueID()) && !e.entity.equals((Object)AntiNicker.mc.thePlayer) && !SkyblockUtils.isNPC(e.entity) && AntiNicker.mc.getNetHandler().getPlayerInfo(e.entity.getUniqueID()) != null && !e.entity.getDisplayName().getUnformattedText().contains(ChatFormatting.RED.toString()) && (SkyblockUtils.onSkyblock || SkyblockUtils.isInOtherGame)) {
            final String realName = getRealName(((EntityPlayer)e.entity).getGameProfile());
            final String stipped = ChatFormatting.stripFormatting(e.entity.getName());
            if (stipped.equals(e.entity.getName()) && !realName.equals(stipped)) {
                AntiNicker.nicked.add(e.entity.getUniqueID());
                OringoClient.sendMessageWithPrefix((e.entity.getDisplayName().getUnformattedText().contains(ChatFormatting.OBFUSCATED.toString()) ? e.entity.getName() : e.entity.getDisplayName().getUnformattedText()) + ChatFormatting.RESET + ChatFormatting.GRAY + " is nicked!" + ((realName.equals("") || realName.equals("Tactful")) ? "" : (" Their real name is " + realName + "!")));
            }
        }
    }
    
    @SubscribeEvent
    public void onWorldJoin(final WorldJoinEvent event) {
        AntiNicker.nicked.clear();
    }
    
    static {
        AntiNicker.nicked = new ArrayList<UUID>();
    }
}
