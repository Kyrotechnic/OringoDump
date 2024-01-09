//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.impl.skyblock;

import me.oringo.oringoclient.qolfeatures.module.*;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.*;
import me.oringo.oringoclient.qolfeatures.module.settings.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.network.play.server.*;
import net.minecraft.init.*;
import me.oringo.oringoclient.events.*;
import net.minecraft.tileentity.*;
import com.mojang.authlib.properties.*;
import me.oringo.oringoclient.utils.*;
import net.minecraft.block.*;
import java.util.*;

public class GhostBlocks extends Module
{
    public NumberSetting range;
    public BooleanSetting cordGhostBlocks;
    public ModeSetting mode;
    private boolean wasPressed;
    private MilliTimer timer;
    private static ArrayList<BlockPos> ghostBlocks;
    private static HashMap<Long, BlockChangeEvent> eventQueue;
    private boolean hasSent;
    private static final int[][] cords;
    
    public GhostBlocks() {
        super("Ghost Blocks", 0, Category.SKYBLOCK);
        this.range = new NumberSetting("Range", 10.0, 1.0, 100.0, 1.0);
        this.cordGhostBlocks = new BooleanSetting("Cord blocks", true);
        this.mode = new ModeSetting("Speed", "Fast", new String[] { "Slow", "Fast" });
        this.timer = new MilliTimer();
        this.addSettings(this.range, this.mode, this.cordGhostBlocks);
    }
    
    @SubscribeEvent
    public void onKey(final TickEvent.ClientTickEvent event) {
        if (GhostBlocks.mc.currentScreen != null || GhostBlocks.mc.theWorld == null || !this.isToggled()) {
            return;
        }
        if (this.cordGhostBlocks.isEnabled() && SecretAura.inBoss) {
            for (final int[] i : GhostBlocks.cords) {
                GhostBlocks.mc.theWorld.setBlockToAir(new BlockPos(i[0], i[1], i[2]));
            }
        }
        this.hasSent = true;
        GhostBlocks.eventQueue.entrySet().removeIf(entry -> {
            if (System.currentTimeMillis() - entry.getKey() > 250L) {
                GhostBlocks.mc.theWorld.setBlockState(((BlockChangeEvent)entry.getValue()).pos, ((BlockChangeEvent)entry.getValue()).state);
                GhostBlocks.ghostBlocks.remove(((BlockChangeEvent)entry.getValue()).pos);
                return true;
            }
            else {
                return false;
            }
        });
        this.hasSent = false;
        if (this.isPressed() && ((this.mode.getSelected().equals("Slow") && !this.wasPressed) || this.mode.getSelected().equals("Fast"))) {
            final Vec3 vec3 = GhostBlocks.mc.thePlayer.getPositionEyes(0.0f);
            final Vec3 vec4 = GhostBlocks.mc.thePlayer.getLook(0.0f);
            final Vec3 vec5 = vec3.addVector(vec4.xCoord * this.range.getValue(), vec4.yCoord * this.range.getValue(), vec4.zCoord * this.range.getValue());
            final BlockPos obj = GhostBlocks.mc.theWorld.rayTraceBlocks(vec3, vec5, true, false, true).getBlockPos();
            if (this.isValidBlock(obj)) {
                return;
            }
            GhostBlocks.mc.theWorld.setBlockToAir(obj);
            GhostBlocks.ghostBlocks.add(obj);
        }
        this.wasPressed = this.isPressed();
    }
    
    @SubscribeEvent(receiveCanceled = true)
    public void onPacket(final PacketReceivedEvent event) {
        if (event.packet instanceof S08PacketPlayerPosLook) {
            GhostBlocks.eventQueue.clear();
        }
    }
    
    @SubscribeEvent
    public void onBlockChange(final BlockChangeEvent event) {
        if (event.state != null && GhostBlocks.ghostBlocks.contains(event.pos) && !this.hasSent && this.isToggled() && event.state.getBlock() != Blocks.air) {
            event.setCanceled(true);
            GhostBlocks.eventQueue.put(System.currentTimeMillis(), event);
        }
    }
    
    @SubscribeEvent
    public void onWorldJoin(final WorldJoinEvent event) {
        GhostBlocks.eventQueue.clear();
        GhostBlocks.ghostBlocks.clear();
    }
    
    @Override
    public boolean isKeybind() {
        return true;
    }
    
    private boolean isValidBlock(final BlockPos blockPos) {
        final Block block = GhostBlocks.mc.theWorld.getBlockState(blockPos).getBlock();
        if (block == Blocks.skull) {
            final TileEntitySkull tileEntity = (TileEntitySkull)GhostBlocks.mc.theWorld.getTileEntity(blockPos);
            if (tileEntity.getSkullType() == 3 && tileEntity.getPlayerProfile() != null && tileEntity.getPlayerProfile().getProperties() != null) {
                final Property property = SkyblockUtils.firstOrNull((Iterable<Property>)tileEntity.getPlayerProfile().getProperties().get((Object)"textures"));
                return property != null && property.getValue().equals("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzRkYjRhZGZhOWJmNDhmZjVkNDE3MDdhZTM0ZWE3OGJkMjM3MTY1OWZjZDhjZDg5MzQ3NDlhZjRjY2U5YiJ9fX0=");
            }
        }
        return block == Blocks.lever || block == Blocks.chest || block == Blocks.trapped_chest;
    }
    
    static {
        GhostBlocks.ghostBlocks = new ArrayList<BlockPos>();
        GhostBlocks.eventQueue = new HashMap<Long, BlockChangeEvent>();
        cords = new int[][] { { 275, 220, 231 }, { 275, 220, 232 }, { 299, 168, 243 }, { 299, 168, 244 }, { 299, 168, 246 }, { 299, 168, 247 }, { 299, 168, 247 }, { 300, 168, 247 }, { 300, 168, 246 }, { 300, 168, 244 }, { 300, 168, 243 }, { 298, 168, 247 }, { 298, 168, 246 }, { 298, 168, 244 }, { 298, 168, 243 }, { 287, 167, 240 }, { 288, 167, 240 }, { 289, 167, 240 }, { 290, 167, 240 }, { 291, 167, 240 }, { 292, 167, 240 }, { 293, 167, 240 }, { 294, 167, 240 }, { 295, 167, 240 }, { 290, 167, 239 }, { 291, 167, 239 }, { 292, 167, 239 }, { 293, 167, 239 }, { 294, 167, 239 }, { 295, 167, 239 }, { 290, 166, 239 }, { 291, 166, 239 }, { 292, 166, 239 }, { 293, 166, 239 }, { 294, 166, 239 }, { 295, 166, 239 }, { 290, 166, 240 }, { 291, 166, 240 }, { 292, 166, 240 }, { 293, 166, 240 }, { 294, 166, 240 }, { 295, 166, 240 } };
    }
}
