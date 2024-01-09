//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.impl.skyblock;

import me.oringo.oringoclient.qolfeatures.module.*;
import net.minecraft.entity.*;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.*;
import me.oringo.oringoclient.qolfeatures.module.settings.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.client.settings.*;
import java.util.regex.*;
import net.minecraft.entity.item.*;
import com.mojang.realmsclient.gui.*;
import java.util.stream.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.common.gameevent.*;
import me.oringo.oringoclient.utils.*;
import net.minecraft.entity.monster.*;
import me.oringo.oringoclient.events.*;
import java.util.function.*;
import net.minecraft.item.*;
import java.util.*;

public class DojoHelper extends Module
{
    private int jumpStage;
    private int ticks;
    private static boolean inTenacity;
    private static boolean inMastery;
    private static final HashMap<Entity, Long> shot;
    public static final BooleanSetting hideZombies;
    public static final BooleanSetting swordSwap;
    public static final BooleanSetting tenacity;
    public static final BooleanSetting masteryAimbot;
    public static final BooleanSetting wTap;
    public static final NumberSetting time;
    public static final NumberSetting bowCharge;
    public static final ModeSetting color;
    
    public DojoHelper() {
        super("Dojo Helper", Category.SKYBLOCK);
        this.addSettings(DojoHelper.hideZombies, DojoHelper.swordSwap, DojoHelper.tenacity, DojoHelper.masteryAimbot, DojoHelper.wTap, DojoHelper.time, DojoHelper.bowCharge, DojoHelper.color);
    }
    
    @SubscribeEvent
    public void onPlayerUpdate(final MotionUpdateEvent event) {
        if (this.isToggled()) {
            if (DojoHelper.masteryAimbot.isEnabled() && DojoHelper.inMastery && DojoHelper.mc.thePlayer.getHeldItem() != null && DojoHelper.mc.thePlayer.getHeldItem().getItem() == Items.bow) {
                if (!DojoHelper.mc.thePlayer.isUsingItem()) {
                    DojoHelper.mc.playerController.sendUseItem((EntityPlayer)DojoHelper.mc.thePlayer, (World)DojoHelper.mc.theWorld, DojoHelper.mc.thePlayer.getHeldItem());
                }
                KeyBinding.setKeyBindState(DojoHelper.mc.gameSettings.keyBindUseItem.getKeyCode(), true);
                final Pattern pattern = Pattern.compile("\\d:\\d\\d\\d");
                Entity target = null;
                double time = 100.0;
                DojoHelper.shot.entrySet().removeIf(entry -> System.currentTimeMillis() - entry.getValue() > 5000L);
                final Pattern pattern2;
                final Iterator<Entity> iterator = ((List)DojoHelper.mc.theWorld.loadedEntityList.stream().filter(e -> e instanceof EntityArmorStand && getColor(e.getName()) && !DojoHelper.shot.containsKey(e) && pattern2.matcher(e.getName()).find()).sorted(Comparator.comparingDouble(entity -> this.getPriority(ChatFormatting.stripFormatting(entity.getName())))).collect(Collectors.toList())).iterator();
                if (iterator.hasNext()) {
                    final Entity entity2 = iterator.next();
                    final Rotation rotation = RotationUtils.getRotations(entity2.getPositionVector().addVector(0.0, 4.0, 0.0));
                    target = entity2;
                    time = this.getPriority(ChatFormatting.stripFormatting(entity2.getName()));
                    event.setRotation(rotation);
                }
                if (DojoHelper.mc.thePlayer.isUsingItem() && DojoHelper.mc.thePlayer.getItemInUse().getItem() == Items.bow && target != null && !event.isPre()) {
                    final ItemBow bow = (ItemBow)DojoHelper.mc.thePlayer.getItemInUse().getItem();
                    final int i = bow.getMaxItemUseDuration(DojoHelper.mc.thePlayer.getItemInUse()) - DojoHelper.mc.thePlayer.getItemInUseCount();
                    float f = i / 20.0f;
                    f = (f * f + f * 2.0f) / 3.0f;
                    if (f >= DojoHelper.bowCharge.getValue() && time <= DojoHelper.time.getValue()) {
                        if (DojoHelper.wTap.isEnabled()) {
                            DojoHelper.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C0BPacketEntityAction((Entity)DojoHelper.mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
                            DojoHelper.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C0BPacketEntityAction((Entity)DojoHelper.mc.thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
                        }
                        DojoHelper.mc.playerController.onStoppedUsingItem((EntityPlayer)DojoHelper.mc.thePlayer);
                        DojoHelper.shot.put(target, System.currentTimeMillis());
                    }
                }
            }
            if (event.isPre()) {
                if (DojoHelper.tenacity.isEnabled() && DojoHelper.inTenacity) {
                    if (this.jumpStage == 0) {
                        event.setPitch(90.0f);
                        if (PlayerUtils.isLiquid(0.01f) && DojoHelper.mc.thePlayer.onGround) {
                            final MovingObjectPosition rayrace = PlayerUtils.rayTrace(0.0f, 90.0f, 4.5f);
                            if (rayrace != null) {
                                final int held = DojoHelper.mc.thePlayer.inventory.currentItem;
                                PlayerUtils.swapToSlot(8);
                                DojoHelper.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C08PacketPlayerBlockPlacement(DojoHelper.mc.thePlayer.getHeldItem()));
                                final Vec3 hitVec = rayrace.hitVec;
                                final BlockPos hitPos = rayrace.getBlockPos();
                                final float f2 = (float)(hitVec.xCoord - hitPos.getX());
                                final float f3 = (float)(hitVec.yCoord - hitPos.getY());
                                final float f4 = (float)(hitVec.zCoord - hitPos.getZ());
                                DojoHelper.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C08PacketPlayerBlockPlacement(rayrace.getBlockPos(), rayrace.sideHit.getIndex(), DojoHelper.mc.thePlayer.getHeldItem(), f2, f3, f4));
                                DojoHelper.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C0APacketAnimation());
                                PlayerUtils.swapToSlot(held);
                            }
                            DojoHelper.mc.thePlayer.jump();
                            this.jumpStage = 1;
                        }
                    }
                    else if (this.jumpStage == 1) {
                        if (PlayerUtils.isLiquid(0.5f) && DojoHelper.mc.thePlayer.motionY < 0.0) {
                            this.jumpStage = 2;
                        }
                    }
                    else if (this.jumpStage == 2) {
                        this.ticks %= 40;
                        ++this.ticks;
                        if (this.ticks == 40) {
                            event.setY(event.y - 0.20000000298023224);
                        }
                        else if (this.ticks == 39) {
                            event.setY(event.y - 0.10000000149011612);
                        }
                        else if (this.ticks == 38) {
                            event.setY(event.y - 0.07999999821186066);
                            event.setX(event.x + 0.20000000298023224);
                            event.setZ(event.z + 0.20000000298023224);
                        }
                    }
                }
                else {
                    final int n = 0;
                    this.jumpStage = n;
                    this.ticks = n;
                }
            }
        }
    }
    
    private double getPriority(String name) {
        double timeLeft = 100000.0;
        name = name.replaceAll(":", ".");
        timeLeft = Double.parseDouble(name);
        return timeLeft;
    }
    
    @SubscribeEvent
    public void onMove(final MoveEvent event) {
        if (this.isToggled() && DojoHelper.tenacity.isEnabled() && DojoHelper.inTenacity && this.jumpStage == 2) {
            event.stop();
            DojoHelper.mc.thePlayer.setVelocity(0.0, 0.0, 0.0);
        }
    }
    
    private static boolean getColor(final String name) {
        if (DojoHelper.color.is("Red")) {
            return name.startsWith("§c§l");
        }
        if (DojoHelper.color.is("Green")) {
            return name.startsWith("§a§l");
        }
        return DojoHelper.color.is("Yellow") && name.startsWith("§e§l");
    }
    
    @SubscribeEvent
    public void onBlockBounds(final BlockBoundsEvent event) {
        if (event.block == Blocks.lava && DojoHelper.inTenacity && this.isToggled() && DojoHelper.tenacity.isEnabled()) {
            event.aabb = new AxisAlignedBB((double)event.pos.getX(), (double)event.pos.getY(), (double)event.pos.getZ(), (double)(event.pos.getX() + 1), (double)(event.pos.getY() + 1), (double)(event.pos.getZ() + 1));
        }
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (this.isToggled()) {
            DojoHelper.inTenacity = SkyblockUtils.hasLine("Challenge: Tenacity");
            DojoHelper.inMastery = SkyblockUtils.hasLine("Challenge: Mastery");
            if (DojoHelper.hideZombies.isEnabled() && DojoHelper.mc.thePlayer != null && DojoHelper.mc.theWorld != null && DojoHelper.mc.thePlayer.getWorldScoreboard() != null && SkyblockUtils.hasLine("Challenge: Force")) {
                for (final Entity entity : new ArrayList<Entity>(DojoHelper.mc.theWorld.loadedEntityList)) {
                    if (entity instanceof EntityZombie && ((EntityZombie)entity).getCurrentArmor(3) != null && ((EntityZombie)entity).getCurrentArmor(3).getItem() == Items.leather_helmet) {
                        entity.posY = -100.0;
                        entity.lastTickPosY = -100.0;
                    }
                    if (entity instanceof EntityArmorStand && entity.getDisplayName().getUnformattedText().startsWith("§c-")) {
                        entity.posY = -100.0;
                        entity.lastTickPosY = -100.0;
                    }
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onLeftClick(final PreAttackEvent event) {
        if (this.isToggled() && DojoHelper.swordSwap.isEnabled()) {
            this.left(event.entity);
        }
    }
    
    public void left(final Entity target) {
        if (SkyblockUtils.hasLine("Challenge: Discipline") && target instanceof EntityZombie && ((EntityZombie)target).getCurrentArmor(3) != null) {
            final Item item = ((EntityZombie)target).getCurrentArmor(3).getItem();
            if (Items.leather_helmet.equals(item)) {
                this.pickItem(stack -> stack.getItem() == Items.wooden_sword);
            }
            else if (Items.golden_helmet.equals(item)) {
                this.pickItem(stack -> stack.getItem() == Items.golden_sword);
            }
            else if (Items.diamond_helmet.equals(item)) {
                this.pickItem(stack -> stack.getItem() == Items.diamond_sword);
            }
            else if (Items.iron_helmet.equals(item)) {
                this.pickItem(stack -> stack.getItem() == Items.iron_sword);
            }
        }
    }
    
    private void pickItem(final Predicate<ItemStack> predicate) {
        final int slot = PlayerUtils.getHotbar(predicate);
        if (slot != -1) {
            PlayerUtils.swapToSlot(slot);
        }
    }
    
    static {
        shot = new HashMap<Entity, Long>();
        hideZombies = new BooleanSetting("Hide bad zombies", true);
        swordSwap = new BooleanSetting("Auto sword swap", true);
        tenacity = new BooleanSetting("Tenacity float", true);
        masteryAimbot = new BooleanSetting("Mastery aimbot", true);
        wTap = new BooleanSetting("W tap", true, a -> !DojoHelper.masteryAimbot.isEnabled());
        time = new NumberSetting("Time", 0.3, 0.1, 5.0, 0.05, a -> !DojoHelper.masteryAimbot.isEnabled());
        bowCharge = new NumberSetting("Bow charge", 0.6, 0.1, 1.0, 0.1, a -> !DojoHelper.masteryAimbot.isEnabled());
        color = new ModeSetting("Color", a -> !DojoHelper.masteryAimbot.isEnabled(), "Yellow", new String[] { "Red", "Yellow", "Green" });
    }
}
