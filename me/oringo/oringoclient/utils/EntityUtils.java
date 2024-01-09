//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.utils;

import net.minecraft.entity.player.*;
import me.oringo.oringoclient.*;
import net.minecraft.scoreboard.*;
import java.util.*;
import net.minecraft.entity.*;

public class EntityUtils
{
    private static boolean isOnTeam(final EntityPlayer player) {
        for (final Score score : OringoClient.mc.thePlayer.getWorldScoreboard().getScores()) {
            if (score.getObjective().getName().equals("health") && score.getPlayerName().contains(player.getName())) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isTeam(final EntityLivingBase e2) {
        if (!(e2 instanceof EntityPlayer) || e2.getDisplayName().getUnformattedText().length() < 4) {
            return false;
        }
        if (SkyblockUtils.onSkyblock) {
            return isOnTeam((EntityPlayer)e2);
        }
        return OringoClient.mc.thePlayer.getDisplayName().getFormattedText().charAt(2) == '§' && e2.getDisplayName().getFormattedText().charAt(2) == '§' && OringoClient.mc.thePlayer.getDisplayName().getFormattedText().charAt(3) == e2.getDisplayName().getFormattedText().charAt(3);
    }
}
