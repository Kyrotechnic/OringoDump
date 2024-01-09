//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.impl.other;

import me.oringo.oringoclient.qolfeatures.module.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import me.oringo.oringoclient.qolfeatures.module.settings.*;
import net.minecraftforge.fml.common.gameevent.*;
import com.mojang.realmsclient.gui.*;
import me.oringo.oringoclient.ui.notifications.*;
import net.minecraft.scoreboard.*;
import java.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.client.event.*;
import net.minecraft.entity.*;
import java.awt.*;
import me.oringo.oringoclient.utils.*;
import net.minecraft.entity.item.*;

public class MurdererFinder extends Module
{
    private ArrayList<Item> knives;
    public static ArrayList<EntityPlayer> murderers;
    public static ArrayList<EntityPlayer> detectives;
    private BooleanSetting autoSay;
    private BooleanSetting ingotESP;
    private BooleanSetting bowESP;
    private boolean inMurder;
    
    public MurdererFinder() {
        super("Murder Mystery", Category.OTHER);
        this.knives = new ArrayList<Item>(Arrays.asList(Items.iron_sword, Items.stone_sword, Items.iron_shovel, Items.stick, Items.wooden_axe, Items.wooden_sword, Blocks.deadbush.getItem((World)null, (BlockPos)null), Items.stone_shovel, Items.diamond_shovel, Items.quartz, Items.pumpkin_pie, Items.golden_pickaxe, Items.apple, Items.name_tag, Blocks.sponge.getItem((World)null, (BlockPos)null), Items.carrot_on_a_stick, Items.bone, Items.carrot, Items.golden_carrot, Items.cookie, Items.diamond_axe, Blocks.red_flower.getItem((World)null, (BlockPos)null), Items.prismarine_shard, Items.cooked_beef, Items.golden_sword, Items.diamond_sword, Items.diamond_hoe, (Item)Items.shears, Items.fish, Items.dye, Items.boat, Items.speckled_melon, Items.blaze_rod, Items.fish));
        this.autoSay = new BooleanSetting("Say murderer", false);
        this.ingotESP = new BooleanSetting("Ingot ESP", true);
        this.bowESP = new BooleanSetting("Bow esp", true);
        this.addSettings(this.autoSay, this.ingotESP, this.bowESP);
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (!this.isToggled() || MurdererFinder.mc.thePlayer == null || MurdererFinder.mc.theWorld == null) {
            return;
        }
        try {
            if (MurdererFinder.mc.thePlayer.getWorldScoreboard() != null) {
                final ScoreObjective objective = MurdererFinder.mc.thePlayer.getWorldScoreboard().getObjectiveInDisplaySlot(1);
                if (objective != null && ChatFormatting.stripFormatting(objective.getDisplayName()).equals("MURDER MYSTERY") && SkyblockUtils.hasLine("Innocents Left:")) {
                    this.inMurder = true;
                    for (final EntityPlayer player : MurdererFinder.mc.theWorld.playerEntities) {
                        if (!MurdererFinder.murderers.contains(player)) {
                            if (MurdererFinder.detectives.contains(player)) {
                                continue;
                            }
                            if (player.getHeldItem() == null) {
                                continue;
                            }
                            if (MurdererFinder.detectives.size() < 2 && player.getHeldItem().getItem().equals(Items.bow)) {
                                MurdererFinder.detectives.add(player);
                                Notifications.showNotification("Oringo Client", String.format("§b%s is detective!", player.getName()), 2500);
                            }
                            if (!this.knives.contains(player.getHeldItem().getItem())) {
                                continue;
                            }
                            MurdererFinder.murderers.add(player);
                            Notifications.showNotification("Oringo Client", String.format("§c%s is murderer!", player.getName()), 2500);
                            if (!this.autoSay.isEnabled() || player == MurdererFinder.mc.thePlayer) {
                                continue;
                            }
                            MurdererFinder.mc.thePlayer.sendChatMessage(String.format("%s is murderer!", ChatFormatting.stripFormatting(player.getName())));
                        }
                    }
                    return;
                }
                this.inMurder = false;
                MurdererFinder.murderers.clear();
                MurdererFinder.detectives.clear();
            }
        }
        catch (Exception ex) {}
    }
    
    @SubscribeEvent
    public void onWorldRender(final RenderWorldLastEvent e) {
        if (!this.isToggled()) {
            return;
        }
        if (this.inMurder) {
            for (final Entity entity : MurdererFinder.mc.theWorld.loadedEntityList) {
                if (entity instanceof EntityPlayer) {
                    if (((EntityPlayer)entity).isPlayerSleeping()) {
                        continue;
                    }
                    if (entity == MurdererFinder.mc.thePlayer) {
                        continue;
                    }
                    if (MurdererFinder.murderers.contains(entity)) {
                        RenderUtils.draw2D(entity, e.partialTicks, 1.0f, Color.red);
                    }
                    else if (MurdererFinder.detectives.contains(entity)) {
                        RenderUtils.draw2D(entity, e.partialTicks, 1.0f, Color.blue);
                    }
                    else {
                        RenderUtils.draw2D(entity, e.partialTicks, 1.0f, Color.gray);
                    }
                }
                else if (entity instanceof EntityItem && ((EntityItem)entity).getEntityItem().getItem() == Items.gold_ingot && this.ingotESP.isEnabled()) {
                    RenderUtils.draw2D(entity, e.partialTicks, 1.0f, Color.yellow);
                }
                else {
                    if (!this.bowESP.isEnabled() || !(entity instanceof EntityArmorStand) || ((EntityArmorStand)entity).getEquipmentInSlot(0) == null || ((EntityArmorStand)entity).getEquipmentInSlot(0).getItem() != Items.bow) {
                        continue;
                    }
                    RenderUtils.tracerLine(entity, e.partialTicks, 1.0f, Color.CYAN);
                }
            }
        }
    }
    
    static {
        MurdererFinder.murderers = new ArrayList<EntityPlayer>();
        MurdererFinder.detectives = new ArrayList<EntityPlayer>();
    }
}
