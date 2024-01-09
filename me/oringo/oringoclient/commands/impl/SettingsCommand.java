//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.commands.impl;

import me.oringo.oringoclient.commands.*;
import net.minecraft.client.*;
import me.oringo.oringoclient.*;
import java.util.*;
import net.minecraft.scoreboard.*;

public class SettingsCommand extends Command
{
    public SettingsCommand() {
        super("oringo", new String[0]);
    }
    
    public void execute(final String[] args) throws Exception {
        if (args.length != 0 && args[0].equalsIgnoreCase("scoreboard") && SettingsCommand.mc.thePlayer.getWorldScoreboard() != null) {
            final StringBuilder builder = new StringBuilder();
            final Scoreboard sb = Minecraft.getMinecraft().thePlayer.getWorldScoreboard();
            final List<Score> list = new ArrayList<Score>(sb.getSortedScores(sb.getObjectiveInDisplaySlot(1)));
            for (final Score score : list) {
                final ScorePlayerTeam team = sb.getPlayersTeam(score.getPlayerName());
                final String s = team.getColorPrefix() + score.getPlayerName() + team.getColorSuffix();
                for (final char c : s.toCharArray()) {
                    if (c < '\u0100') {
                        builder.append(c);
                    }
                }
                builder.append("\n");
            }
            builder.append(SettingsCommand.mc.thePlayer.getWorldScoreboard().getObjectiveInDisplaySlot(1).getDisplayName());
            System.out.println(builder);
            return;
        }
        OringoClient.clickGui.toggle();
    }
    
    public String getDescription() {
        return "Opens the menu";
    }
}
