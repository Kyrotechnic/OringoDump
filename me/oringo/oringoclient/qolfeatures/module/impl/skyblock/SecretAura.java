//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.impl.skyblock;

import me.oringo.oringoclient.qolfeatures.module.*;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.*;
import me.oringo.oringoclient.qolfeatures.module.settings.*;
import java.util.*;
import me.oringo.oringoclient.utils.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.init.*;
import net.minecraft.tileentity.*;
import com.mojang.authlib.properties.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import me.oringo.oringoclient.*;
import me.oringo.oringoclient.events.*;
import com.mojang.realmsclient.gui.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraftforge.event.world.*;

public class SecretAura extends Module
{
    public NumberSetting reach;
    public StringSetting item;
    public BooleanSetting cancelChest;
    public BooleanSetting clickedCheck;
    public BooleanSetting rotation;
    public static ArrayList<BlockPos> clicked;
    public static boolean inBoss;
    private boolean sent;
    
    public SecretAura() {
        super("Secret Aura", 0, Category.SKYBLOCK);
        this.reach = new NumberSetting("Reach", 5.0, 2.0, 6.0, 0.1);
        this.item = new StringSetting("Item");
        this.cancelChest = new BooleanSetting("Cancel chests", true);
        this.clickedCheck = new BooleanSetting("Clicked check", true);
        this.rotation = new BooleanSetting("Rotation", false);
        this.addSettings(this.reach, this.item, this.rotation, this.cancelChest, this.clickedCheck);
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onUpdatePre(final MotionUpdateEvent.Pre event) {
        if (SecretAura.mc.thePlayer != null && this.isToggled() && SkyblockUtils.inDungeon && this.rotation.isEnabled()) {
            final Vec3i vec3i = new Vec3i(10, 10, 10);
            for (final BlockPos blockPos : BlockPos.getAllInBox(new BlockPos((Vec3i)SecretAura.mc.thePlayer.getPosition()).add(vec3i), new BlockPos((Vec3i)SecretAura.mc.thePlayer.getPosition().subtract(vec3i)))) {
                if (this.isValidBlock(blockPos) && SecretAura.mc.thePlayer.getDistance((double)blockPos.getX(), (double)(blockPos.getY() - SecretAura.mc.thePlayer.getEyeHeight()), (double)blockPos.getZ()) < this.reach.getValue()) {
                    final Rotation floats = RotationUtils.getRotations(new Vec3(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5));
                    event.yaw = floats.getYaw();
                    event.pitch = floats.getPitch();
                    break;
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onTick(final MotionUpdateEvent.Post event) {
        if (SecretAura.mc.thePlayer != null && this.isToggled() && SkyblockUtils.inDungeon) {
            final Vec3i vec3i = new Vec3i(10, 10, 10);
            for (final BlockPos blockPos : BlockPos.getAllInBox(new BlockPos((Vec3i)SecretAura.mc.thePlayer.getPosition()).add(vec3i), new BlockPos((Vec3i)SecretAura.mc.thePlayer.getPosition().subtract(vec3i)))) {
                if (this.isValidBlock(blockPos) && SecretAura.mc.thePlayer.getDistance((double)blockPos.getX(), (double)(blockPos.getY() - SecretAura.mc.thePlayer.getEyeHeight()), (double)blockPos.getZ()) < this.reach.getValue()) {
                    this.interactWithBlock(blockPos);
                    if (this.rotation.isEnabled()) {
                        break;
                    }
                    continue;
                }
            }
        }
    }
    
    private boolean isValidBlock(final BlockPos blockPos) {
        final Block block = SecretAura.mc.theWorld.getBlockState(blockPos).getBlock();
        if (block == Blocks.skull) {
            final TileEntitySkull tileEntity = (TileEntitySkull)SecretAura.mc.theWorld.getTileEntity(blockPos);
            if (tileEntity.getSkullType() == 3 && tileEntity.getPlayerProfile() != null && tileEntity.getPlayerProfile().getProperties() != null) {
                final Property property = SkyblockUtils.firstOrNull((Iterable<Property>)tileEntity.getPlayerProfile().getProperties().get((Object)"textures"));
                return property != null && property.getValue().equals("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzRkYjRhZGZhOWJmNDhmZjVkNDE3MDdhZTM0ZWE3OGJkMjM3MTY1OWZjZDhjZDg5MzQ3NDlhZjRjY2U5YiJ9fX0=") && (!SecretAura.clicked.contains(blockPos) || !this.clickedCheck.isEnabled());
            }
        }
        return (block == Blocks.lever || block == Blocks.chest || block == Blocks.trapped_chest) && (!SecretAura.clicked.contains(blockPos) || !this.clickedCheck.isEnabled());
    }
    
    private void interactWithBlock(final BlockPos pos) {
        for (int i = 0; i < 9; ++i) {
            if (SecretAura.mc.thePlayer.inventory.getStackInSlot(i) != null && SecretAura.mc.thePlayer.inventory.getStackInSlot(i).getDisplayName().toLowerCase().contains(this.item.getValue().toLowerCase())) {
                final int holding = SecretAura.mc.thePlayer.inventory.currentItem;
                SecretAura.mc.thePlayer.inventory.currentItem = i;
                if (SecretAura.mc.theWorld.getBlockState(pos).getBlock() == Blocks.lever && !SecretAura.inBoss) {
                    SecretAura.mc.playerController.onPlayerRightClick(SecretAura.mc.thePlayer, SecretAura.mc.theWorld, SecretAura.mc.thePlayer.inventory.getCurrentItem(), pos, EnumFacing.fromAngle((double)SecretAura.mc.thePlayer.rotationYaw), new Vec3(0.0, 0.0, 0.0));
                }
                SecretAura.mc.playerController.onPlayerRightClick(SecretAura.mc.thePlayer, SecretAura.mc.theWorld, SecretAura.mc.thePlayer.inventory.getCurrentItem(), pos, EnumFacing.fromAngle((double)SecretAura.mc.thePlayer.rotationYaw), new Vec3(0.0, 0.0, 0.0));
                SecretAura.mc.thePlayer.inventory.currentItem = holding;
                SecretAura.clicked.add(pos);
                return;
            }
        }
        if (!this.sent) {
            OringoClient.sendMessageWithPrefix("You don't have a required item in your hotbar!");
            this.sent = true;
        }
    }
    
    @SubscribeEvent
    public void onChat(final PacketReceivedEvent event) {
        if (event.packet instanceof S02PacketChat && ChatFormatting.stripFormatting(((S02PacketChat)event.packet).getChatComponent().getFormattedText()).startsWith("[BOSS] Necron")) {
            SecretAura.inBoss = true;
        }
    }
    
    @SubscribeEvent
    public void onPacket(final PacketReceivedEvent event) {
        if (event.packet instanceof S2DPacketOpenWindow && ChatFormatting.stripFormatting(((S2DPacketOpenWindow)event.packet).getWindowTitle().getFormattedText()).equals("Chest") && SkyblockUtils.inDungeon && this.cancelChest.isEnabled()) {
            event.setCanceled(true);
            SecretAura.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C0DPacketCloseWindow(((S2DPacketOpenWindow)event.packet).getWindowId()));
        }
    }
    
    @SubscribeEvent
    public void clear(final WorldEvent.Load event) {
        SecretAura.inBoss = false;
        SecretAura.clicked.clear();
    }
    
    static {
        SecretAura.clicked = new ArrayList<BlockPos>();
    }
}
