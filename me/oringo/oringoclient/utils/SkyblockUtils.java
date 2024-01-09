//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.utils;

import net.minecraft.client.*;
import net.minecraft.network.play.server.*;
import com.mojang.realmsclient.gui.*;
import me.oringo.oringoclient.*;
import me.oringo.oringoclient.ui.notifications.*;
import net.minecraftforge.fml.common.eventhandler.*;
import me.oringo.oringoclient.events.*;
import net.minecraftforge.fml.common.gameevent.*;
import com.google.common.collect.*;
import net.minecraft.scoreboard.*;
import java.lang.reflect.*;
import java.util.*;
import net.minecraft.client.entity.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import java.awt.*;
import net.minecraft.client.network.*;

public class SkyblockUtils
{
    private static final Minecraft mc;
    public static boolean inDungeon;
    public static boolean isInOtherGame;
    public static boolean onSkyblock;
    public static boolean inBlood;
    public static boolean inP3;
    
    public static boolean isTerminal(final String name) {
        return name.contains("Correct all the panes!") || name.contains("Navigate the maze!") || name.contains("Click in order!") || name.contains("What starts with:") || name.contains("Select all the") || name.contains("Change all to same color!") || name.contains("Click the button on time!");
    }
    
    @SubscribeEvent
    public void onChat(final PacketReceivedEvent event) {
        if (event.packet instanceof S02PacketChat) {
            if (ChatFormatting.stripFormatting(((S02PacketChat)event.packet).getChatComponent().getFormattedText()).startsWith("[BOSS] The Watcher: ") && !SkyblockUtils.inBlood) {
                SkyblockUtils.inBlood = true;
                if (OringoClient.bloodAimbot.isToggled()) {
                    Notifications.showNotification("Oringo Client", "Started Camp", 1000);
                }
            }
            if (ChatFormatting.stripFormatting(((S02PacketChat)event.packet).getChatComponent().getFormattedText()).equals("[BOSS] The Watcher: You have proven yourself. You may pass.")) {
                SkyblockUtils.inBlood = false;
                if (OringoClient.bloodAimbot.isToggled()) {
                    Notifications.showNotification("Oringo Client", "Stopped camp", 1000);
                }
            }
            if (ChatFormatting.stripFormatting(((S02PacketChat)event.packet).getChatComponent().getFormattedText()).startsWith("[BOSS] Necron: I hope you're in shape. BETTER GET RUNNING!")) {
                SkyblockUtils.inP3 = true;
            }
            if (ChatFormatting.stripFormatting(((S02PacketChat)event.packet).getChatComponent().getFormattedText()).startsWith("[BOSS] Necron: THAT'S IT YOU HAVE DONE IT! MY ENTIRE FACTORY IS RUINED! ARE YOU HAPPY?!")) {
                SkyblockUtils.inP3 = false;
            }
        }
    }
    
    @SubscribeEvent
    public void onWorldLoad(final WorldJoinEvent event) {
        SkyblockUtils.inBlood = false;
        SkyblockUtils.inP3 = false;
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (SkyblockUtils.mc.theWorld != null && event.phase == TickEvent.Phase.START) {
            SkyblockUtils.inDungeon = (hasLine("Cleared:") || hasLine("Start"));
            SkyblockUtils.isInOtherGame = isInOtherGame();
            SkyblockUtils.onSkyblock = isOnSkyBlock();
        }
    }
    
    public static <T> T firstOrNull(final Iterable<T> iterable) {
        return (T)Iterables.getFirst((Iterable)iterable, (Object)null);
    }
    
    public static boolean hasScoreboardTitle(final String title) {
        return SkyblockUtils.mc.thePlayer != null && SkyblockUtils.mc.thePlayer.getWorldScoreboard() != null && SkyblockUtils.mc.thePlayer.getWorldScoreboard().getObjectiveInDisplaySlot(1) != null && ChatFormatting.stripFormatting(SkyblockUtils.mc.thePlayer.getWorldScoreboard().getObjectiveInDisplaySlot(1).getDisplayName()).equalsIgnoreCase(title);
    }
    
    public static boolean isInOtherGame() {
        try {
            final Scoreboard sb = SkyblockUtils.mc.thePlayer.getWorldScoreboard();
            final List<Score> list = new ArrayList<Score>(sb.getSortedScores(sb.getObjectiveInDisplaySlot(1)));
            for (final Score score : list) {
                final ScorePlayerTeam team = sb.getPlayersTeam(score.getPlayerName());
                final String s = ChatFormatting.stripFormatting(ScorePlayerTeam.formatPlayerName((Team)team, score.getPlayerName()));
                if (s.contains("Map")) {
                    return true;
                }
            }
        }
        catch (Exception ex) {}
        return false;
    }
    
    public static boolean isOnSkyBlock() {
        try {
            final ScoreObjective titleObjective = SkyblockUtils.mc.thePlayer.getWorldScoreboard().getObjectiveInDisplaySlot(1);
            if (SkyblockUtils.mc.thePlayer.getWorldScoreboard().getObjectiveInDisplaySlot(0) != null) {
                return ChatFormatting.stripFormatting(SkyblockUtils.mc.thePlayer.getWorldScoreboard().getObjectiveInDisplaySlot(0).getDisplayName()).contains("SKYBLOCK");
            }
            return ChatFormatting.stripFormatting(SkyblockUtils.mc.thePlayer.getWorldScoreboard().getObjectiveInDisplaySlot(1).getDisplayName()).contains("SKYBLOCK");
        }
        catch (Exception e) {
            return false;
        }
    }
    
    public static boolean hasLine(final String line) {
        if (SkyblockUtils.mc.thePlayer != null && SkyblockUtils.mc.thePlayer.getWorldScoreboard() != null && SkyblockUtils.mc.thePlayer.getWorldScoreboard().getObjectiveInDisplaySlot(1) != null) {
            final Scoreboard sb = Minecraft.getMinecraft().thePlayer.getWorldScoreboard();
            final List<Score> list = new ArrayList<Score>(sb.getSortedScores(sb.getObjectiveInDisplaySlot(1)));
            for (final Score score : list) {
                final ScorePlayerTeam team = sb.getPlayersTeam(score.getPlayerName());
                if (team != null) {
                    final String s = ChatFormatting.stripFormatting(team.getColorPrefix() + score.getPlayerName() + team.getColorSuffix());
                    final StringBuilder builder = new StringBuilder();
                    for (final char c : s.toCharArray()) {
                        if (c < '\u0100') {
                            builder.append(c);
                        }
                    }
                    if (builder.toString().toLowerCase().contains(line.toLowerCase())) {
                        return true;
                    }
                    continue;
                }
            }
        }
        return false;
    }
    
    public static boolean isMiniboss(final Entity entity) {
        return entity.getName().equals("Shadow Assassin") || entity.getName().equals("Lost Adventurer") || entity.getName().equals("Diamond Guy");
    }
    
    public static void click() {
        try {
            Method clickMouse;
            try {
                clickMouse = Minecraft.class.getDeclaredMethod("clickMouse", (Class<?>[])new Class[0]);
            }
            catch (NoSuchMethodException e2) {
                clickMouse = Minecraft.class.getDeclaredMethod("clickMouse", (Class<?>[])new Class[0]);
            }
            clickMouse.setAccessible(true);
            clickMouse.invoke(Minecraft.getMinecraft(), new Object[0]);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static boolean anyTab(final String s) {
        return Minecraft.getMinecraft().getNetHandler().getPlayerInfoMap().stream().anyMatch(player -> player.getDisplayName() != null && ChatFormatting.stripFormatting(player.getDisplayName().getFormattedText()).toLowerCase().contains(s.toLowerCase(Locale.ROOT)));
    }
    
    public static boolean isNPC(final Entity entity) {
        if (!(entity instanceof EntityOtherPlayerMP)) {
            return false;
        }
        final EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
        return ChatFormatting.stripFormatting(entity.getDisplayName().getUnformattedText()).startsWith("[NPC]") || (entity.getUniqueID().version() == 2 && entityLivingBase.getHealth() == 20.0f && entityLivingBase.getMaxHealth() == 20.0f);
    }
    
    public static void rightClick() {
        try {
            Method rightClickMouse = null;
            try {
                rightClickMouse = Minecraft.class.getDeclaredMethod("rightClickMouse", (Class<?>[])new Class[0]);
            }
            catch (NoSuchMethodException e2) {
                rightClickMouse = Minecraft.class.getDeclaredMethod("rightClickMouse", (Class<?>[])new Class[0]);
            }
            rightClickMouse.setAccessible(true);
            rightClickMouse.invoke(Minecraft.getMinecraft(), new Object[0]);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static String getDisplayName(final ItemStack item) {
        if (item == null) {
            return "null";
        }
        return item.getDisplayName();
    }
    
    public static Color rainbow(final int delay) {
        double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20.0);
        rainbowState %= 360.0;
        return Color.getHSBColor((float)(rainbowState / 360.0), 1.0f, 1.0f);
    }
    
    public static int getPing() {
        final NetworkPlayerInfo networkPlayerInfo = SkyblockUtils.mc.getNetHandler().getPlayerInfo(Minecraft.getMinecraft().thePlayer.getUniqueID());
        return (networkPlayerInfo == null) ? 0 : networkPlayerInfo.getResponseTime();
    }
    
    static {
        mc = Minecraft.getMinecraft();
    }
}
