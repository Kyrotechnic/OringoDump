//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.impl.skyblock;

import me.oringo.oringoclient.qolfeatures.module.*;
import net.minecraftforge.fml.common.gameevent.*;
import me.oringo.oringoclient.utils.*;
import net.minecraft.init.*;
import me.oringo.oringoclient.*;
import net.minecraftforge.fml.common.eventhandler.*;
import me.oringo.oringoclient.events.*;
import net.minecraftforge.event.world.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

public class AutoS1 extends Module
{
    private boolean clicked;
    private boolean clickedButton;
    private static BlockPos clickPos;
    
    public AutoS1() {
        super("Auto SS", 0, Category.SKYBLOCK);
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (AutoS1.mc.thePlayer == null || !SkyblockUtils.inDungeon || !this.isToggled() || !SkyblockUtils.inP3) {
            return;
        }
        if (AutoS1.mc.thePlayer.getPositionEyes(0.0f).distanceTo(new Vec3(309.0, 121.0, 290.0)) < 5.5 && !this.clicked && AutoS1.mc.theWorld.getBlockState(new BlockPos(309, 121, 290)).getBlock() == Blocks.stone_button) {
            this.clickBlock(new BlockPos(309, 121, 290));
            this.clicked = true;
            this.clickedButton = false;
        }
        if (AutoS1.clickPos != null && AutoS1.mc.thePlayer.getDistance((double)AutoS1.clickPos.getX(), (double)(AutoS1.clickPos.getY() - AutoS1.mc.thePlayer.getEyeHeight()), (double)AutoS1.clickPos.getZ()) < 5.5 && !this.clickedButton && AutoS1.mc.theWorld.getBlockState(AutoS1.clickPos).getBlock() == Blocks.stone_button) {
            for (int i = 0; i < 20; ++i) {
                this.clickBlock(AutoS1.clickPos);
            }
            AutoS1.clickPos = null;
            this.clickedButton = true;
            OringoClient.sendMessageWithPrefix("Clicked!");
        }
    }
    
    @SubscribeEvent
    public void onPacket(final BlockChangeEvent event) {
        if (this.clicked && !this.clickedButton && SkyblockUtils.inP3 && event.state.getBlock() == Blocks.sea_lantern && event.pos.getX() == 310 && event.pos.getY() >= 120 && event.pos.getY() <= 123 && event.pos.getZ() >= 291 && event.pos.getZ() <= 294) {
            AutoS1.clickPos = new BlockPos(event.pos.getX() - 1, event.pos.getY(), event.pos.getZ());
        }
    }
    
    @SubscribeEvent
    public void onWorldChange(final WorldEvent.Load event) {
        this.clicked = false;
        AutoS1.clickPos = null;
        this.clickedButton = false;
    }
    
    private void clickBlock(final BlockPos hitPos) {
        final Vec3 hitVec = new Vec3(0.0, 0.0, 0.0);
        final float f = (float)(hitVec.xCoord - hitPos.getX());
        final float f2 = (float)(hitVec.yCoord - hitPos.getY());
        final float f3 = (float)(hitVec.zCoord - hitPos.getZ());
        AutoS1.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C08PacketPlayerBlockPlacement(hitPos, EnumFacing.fromAngle((double)AutoS1.mc.thePlayer.rotationYaw).getIndex(), AutoS1.mc.thePlayer.inventory.getCurrentItem(), f, f2, f3));
    }
}
