//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.mixins;

import net.minecraft.client.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.client.entity.*;
import net.minecraft.client.settings.*;
import net.minecraft.client.particle.*;
import com.mojang.authlib.properties.*;
import me.oringo.oringoclient.*;
import me.oringo.oringoclient.qolfeatures.module.impl.combat.*;
import net.minecraft.entity.*;
import me.oringo.oringoclient.mixins.entity.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.oringo.oringoclient.qolfeatures.module.impl.render.*;
import me.oringo.oringoclient.utils.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.lwjgl.input.*;
import me.oringo.oringoclient.commands.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.gui.*;
import me.oringo.oringoclient.qolfeatures.module.impl.player.*;
import me.oringo.oringoclient.events.*;
import net.minecraft.block.material.*;
import net.minecraft.util.*;

@Mixin({ Minecraft.class })
public abstract class MinecraftMixin
{
    @Shadow
    private int leftClickCounter;
    @Shadow
    public MovingObjectPosition objectMouseOver;
    @Shadow
    public WorldClient theWorld;
    @Shadow
    public PlayerControllerMP playerController;
    @Shadow
    private Entity renderViewEntity;
    @Shadow
    public EntityPlayerSP thePlayer;
    @Shadow
    public GuiScreen currentScreen;
    @Shadow
    public GameSettings gameSettings;
    @Shadow
    public boolean inGameHasFocus;
    @Shadow
    public boolean renderChunksMany;
    @Shadow
    private Timer timer;
    @Shadow
    private int rightClickDelayTimer;
    @Shadow
    public EffectRenderer effectRenderer;
    @Shadow
    public GuiIngame ingameGUI;
    @Shadow
    public boolean skipRenderWorld;
    
    @Shadow
    public abstract PropertyMap func_181037_M();
    
    @Inject(method = { "getRenderViewEntity" }, at = { @At("HEAD") })
    public void getRenderViewEntity(final CallbackInfoReturnable<Entity> cir) {
        if (!ServerRotations.getInstance().isToggled() || this.renderViewEntity == null || this.renderViewEntity != OringoClient.mc.thePlayer) {
            return;
        }
        if (!ServerRotations.getInstance().onlyKillAura.isEnabled() || KillAura.target != null) {
            ((EntityLivingBase)this.renderViewEntity).rotationYawHead = ((PlayerSPAccessor)this.renderViewEntity).getLastReportedYaw();
            ((EntityLivingBase)this.renderViewEntity).renderYawOffset = ((PlayerSPAccessor)this.renderViewEntity).getLastReportedYaw();
        }
    }
    
    @Inject(method = { "runGameLoop" }, at = { @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;skipRenderWorld:Z") })
    public void skipRenderWorld(final CallbackInfo ci) {
        if (this.skipRenderWorld) {
            NoRender.drawGui();
            try {
                Thread.sleep((long)(50.0f / TimerUtil.getTimer().timerSpeed));
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    @Inject(method = { "displayGuiScreen" }, at = { @At("RETURN") })
    public void onGuiOpen(final GuiScreen i, final CallbackInfo ci) {
        MinecraftForge.EVENT_BUS.post((Event)new PostGuiOpenEvent((Gui)i));
    }
    
    @Inject(method = { "runTick" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;dispatchKeypresses()V") })
    public void keyPresses(final CallbackInfo ci) {
        final int k = (Keyboard.getEventKey() == 0) ? (Keyboard.getEventCharacter() + '\u0100') : Keyboard.getEventKey();
        final char aChar = Keyboard.getEventCharacter();
        if (Keyboard.getEventKeyState()) {
            if (MinecraftForge.EVENT_BUS.post((Event)new KeyPressEvent(k, aChar))) {
                return;
            }
            if (OringoClient.mc.currentScreen == null) {
                if (aChar == CommandHandler.getCommandPrefix() && this.gameSettings.chatVisibility != EntityPlayer.EnumChatVisibility.HIDDEN) {
                    OringoClient.mc.displayGuiScreen((GuiScreen)new GuiChat());
                }
                OringoClient.handleKeypress(k);
            }
        }
    }
    
    @Inject(method = { "rightClickMouse" }, at = { @At("HEAD") }, cancellable = true)
    public void onRightClick(final CallbackInfo callbackInfo) {
        if (MinecraftForge.EVENT_BUS.post((Event)new RightClickEvent())) {
            callbackInfo.cancel();
        }
    }
    
    @Inject(method = { "rightClickMouse" }, at = { @At("RETURN") }, cancellable = true)
    public void onRightClickPost(final CallbackInfo callbackInfo) {
        if (FastPlace.getInstance().isToggled()) {
            this.rightClickDelayTimer = (int)FastPlace.getInstance().placeDelay.getValue();
        }
    }
    
    @Inject(method = { "clickMouse" }, at = { @At("HEAD") }, cancellable = true)
    public void onClick(final CallbackInfo callbackInfo) {
        if (MinecraftForge.EVENT_BUS.post((Event)new LeftClickEvent())) {
            callbackInfo.cancel();
        }
        if (OringoClient.noHitDelay.isToggled() || OringoClient.mithrilMacro.isToggled()) {
            this.leftClickCounter = 0;
        }
    }
    
    @Inject(method = { "sendClickBlockToController" }, at = { @At("RETURN") })
    public void sendClickBlock(final CallbackInfo callbackInfo) {
        final boolean click = this.currentScreen == null && this.gameSettings.keyBindAttack.isKeyDown() && this.inGameHasFocus;
        if (OringoClient.fastBreak.isToggled() && click && this.objectMouseOver != null && this.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            for (int i = 0; i < OringoClient.fastBreak.maxBlocks.getValue(); ++i) {
                final BlockPos prevBlockPos = this.objectMouseOver.getBlockPos();
                this.objectMouseOver = this.renderViewEntity.rayTrace((double)this.playerController.getBlockReachDistance(), 1.0f);
                final BlockPos blockpos = this.objectMouseOver.getBlockPos();
                if (this.objectMouseOver == null || blockpos == null || this.objectMouseOver.typeOfHit != MovingObjectPosition.MovingObjectType.BLOCK || blockpos == prevBlockPos || this.theWorld.getBlockState(blockpos).getBlock().getMaterial() == Material.air) {
                    break;
                }
                this.thePlayer.swingItem();
                this.playerController.clickBlock(blockpos, this.objectMouseOver.sideHit);
            }
        }
    }
}
