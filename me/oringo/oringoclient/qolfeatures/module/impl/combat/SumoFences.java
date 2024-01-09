//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.impl.combat;

import me.oringo.oringoclient.qolfeatures.module.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.client.*;
import net.minecraft.init.*;
import net.minecraftforge.fml.common.eventhandler.*;
import me.oringo.oringoclient.qolfeatures.module.impl.macro.*;
import com.mojang.realmsclient.gui.*;
import me.oringo.oringoclient.*;
import me.oringo.oringoclient.utils.*;
import java.awt.*;
import java.io.*;
import net.minecraftforge.client.event.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.client.gui.*;
import java.util.*;

public class SumoFences extends Module
{
    private boolean sumo;
    private ArrayList<BlockPos> posList;
    private List<String> win;
    private List<String> lose;
    private String winTime;
    private static int shouldReconnect;
    private static int wins;
    private static int winstreak;
    private static int lost;
    
    public SumoFences() {
        super("Sumo Fences", 0, Category.COMBAT);
        this.sumo = false;
        this.posList = new ArrayList<BlockPos>();
        this.win = Arrays.asList("gg", "gf");
        this.lose = Arrays.asList("gg", "gf", "how");
        this.winTime = "";
    }
    
    @SubscribeEvent
    public void onSumoStart(final TickEvent.ClientTickEvent event) {
        try {
            if (this.isToggled()) {
                if (SkyblockUtils.hasLine("Mode: Sumo") && SkyblockUtils.hasLine("Opponent:")) {
                    if (!this.sumo) {
                        this.sumo = true;
                        final Minecraft mc = Minecraft.getMinecraft();
                        BlockPos pos = mc.thePlayer.getPosition();
                        int i = 0;
                        while (i++ != 10) {
                            if (Minecraft.getMinecraft().theWorld.getBlockState(pos = pos.down()).getBlock().equals(Blocks.air)) {
                                continue;
                            }
                            final BlockPos finalPos = pos;
                            final BlockPos blockPos;
                            new Thread(() -> {
                                try {
                                    Thread.sleep(1000L);
                                }
                                catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                this.sumo = false;
                                this.posList.clear();
                                this.checkBlock(blockPos.down());
                            }).start();
                        }
                    }
                }
                else {
                    if (this.sumo) {
                        this.posList.clear();
                    }
                    this.sumo = false;
                }
            }
        }
        catch (Exception ex) {}
    }
    
    @SubscribeEvent
    public void onChat(final ClientChatReceivedEvent event) {
        if (AutoSumoBot.thread == null) {
            return;
        }
        if (ChatFormatting.stripFormatting(event.message.getFormattedText()).contains("                           Sumo Duel - ")) {
            this.winTime = ChatFormatting.stripFormatting(event.message.getFormattedText()).replaceFirst("                           Sumo Duel - ", "");
        }
        if (event.message.getFormattedText().contains("§f§lMelee Accuracy")) {
            String title;
            DiscordWebhook webhook;
            DiscordWebhook.EmbedObject embed;
            DiscordWebhook webhook2;
            DiscordWebhook.EmbedObject embed2;
            DiscordWebhook webhook3;
            DiscordWebhook.EmbedObject embed3;
            final Exception ex;
            Exception e;
            new Thread(() -> {
                try {
                    Thread.sleep(250L);
                    if (Minecraft.getMinecraft().thePlayer.capabilities.isFlying) {
                        title = (String)ReflectionUtils.getFieldByName(Minecraft.getMinecraft().ingameGUI, "field_175201_x");
                        if (title != null && title.toUpperCase().contains("DRAW")) {
                            webhook = new DiscordWebhook(OringoClient.autoSumo.webhook.getValue());
                            webhook.setUsername("AutoSumo bot");
                            webhook.setAvatarUrl("https://cdn.discordapp.com/icons/913088401262137424/496d604510a63242db77526d8bfab9fa.png");
                            embed = new DiscordWebhook.EmbedObject();
                            embed.setTitle("Draw").setDescription("Opponent: " + AutoSumoBot.target.getName() + " Time: " + this.winTime).setColor(Color.ORANGE);
                            webhook.addEmbed(embed);
                            webhook.execute();
                            return;
                        }
                        else {
                            Minecraft.getMinecraft().thePlayer.sendChatMessage("/ac " + this.lose.get(new Random().nextInt(this.lose.size())));
                            webhook2 = new DiscordWebhook(OringoClient.autoSumo.webhook.getValue());
                            webhook2.setUsername("AutoSumo bot");
                            webhook2.setAvatarUrl("https://cdn.discordapp.com/icons/913088401262137424/496d604510a63242db77526d8bfab9fa.png");
                            embed2 = new DiscordWebhook.EmbedObject();
                            SumoFences.winstreak = 0;
                            ++SumoFences.lost;
                            embed2.setTitle("Lost").setDescription("Opponent: " + AutoSumoBot.target.getName() + " Time: " + this.winTime + " Loses this session: " + SumoFences.lost).addField("Blacklist", " Added " + AutoSumoBot.target.getUniqueID().toString() + " to blacklist!", false).setColor(Color.RED);
                            webhook2.addEmbed(embed2);
                            webhook2.execute();
                        }
                    }
                    else {
                        Minecraft.getMinecraft().thePlayer.sendChatMessage("/ac " + this.win.get(new Random().nextInt(this.win.size())));
                        webhook3 = new DiscordWebhook(OringoClient.autoSumo.webhook.getValue());
                        webhook3.setUsername("AutoSumo bot");
                        webhook3.setAvatarUrl("https://cdn.discordapp.com/icons/913088401262137424/496d604510a63242db77526d8bfab9fa.png");
                        embed3 = new DiscordWebhook.EmbedObject();
                        ++SumoFences.wins;
                        ++SumoFences.winstreak;
                        embed3.setTitle("WIN").setDescription("Opponent: " + AutoSumoBot.target.getName() + " Time: " + this.winTime + " Wins: " + SumoFences.wins + " winstreak: " + SumoFences.winstreak).setColor(Color.GREEN);
                        webhook3.addEmbed(embed3);
                        webhook3.execute();
                    }
                    Thread.sleep(100L);
                }
                catch (InterruptedException | IOException ex2) {
                    e = ex;
                    e.printStackTrace();
                }
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play duels_sumo_duel");
            }).start();
        }
    }
    
    @SubscribeEvent
    public void onDisconnect(final GuiOpenEvent event) {
        if (event.gui instanceof GuiDisconnected && AutoSumoBot.thread != null) {
            final DiscordWebhook webhook = new DiscordWebhook(OringoClient.autoSumo.webhook.getValue());
            webhook.setUsername("AutoSumo bot");
            webhook.setAvatarUrl("https://cdn.discordapp.com/icons/913088401262137424/496d604510a63242db77526d8bfab9fa.png");
            webhook.setContent("Bot Disconnected! <@884509916868517898>");
            SumoFences.shouldReconnect = 2000;
            try {
                webhook.execute();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    @SubscribeEvent
    public void reconnect(final TickEvent.ClientTickEvent event) {
        if (SumoFences.shouldReconnect-- == 0 && AutoSumoBot.thread != null) {
            SumoFences.mc.displayGuiScreen((GuiScreen)new GuiConnecting((GuiScreen)new GuiMainMenu(), SumoFences.mc, new ServerData("Hypixel", "play.Hypixel.net", false)));
        }
    }
    
    private void checkBlock(final BlockPos pos) {
        for (final BlockPos blockPos : Arrays.asList(pos.west(), pos.north().east(), pos.north().west(), pos.south().west(), pos.south().east(), pos.east(), pos.north(), pos.south())) {
            if (Minecraft.getMinecraft().theWorld.getBlockState(blockPos).getBlock().equals(Blocks.air)) {
                Minecraft.getMinecraft().theWorld.setBlockState(blockPos.up().up().up(), Blocks.air.getDefaultState());
                Minecraft.getMinecraft().theWorld.setBlockState(blockPos.up().up().up(), Blocks.oak_fence.getDefaultState());
                Minecraft.getMinecraft().theWorld.setBlockState(blockPos.up().up().up().up(), Blocks.oak_fence.getDefaultState());
            }
            else {
                if (this.posList.contains(blockPos)) {
                    continue;
                }
                this.posList.add(blockPos);
                this.checkBlock(blockPos);
            }
        }
    }
    
    static {
        SumoFences.shouldReconnect = -1;
        SumoFences.wins = -1;
        SumoFences.winstreak = 0;
        SumoFences.lost = 0;
    }
}
