//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.impl.macro;

import me.oringo.oringoclient.qolfeatures.module.*;
import net.minecraft.client.*;
import net.minecraft.entity.item.*;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.*;
import me.oringo.oringoclient.qolfeatures.module.settings.*;
import net.minecraftforge.event.world.*;
import me.oringo.oringoclient.*;
import net.minecraftforge.fml.common.eventhandler.*;
import java.awt.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraftforge.client.event.*;
import com.mojang.realmsclient.gui.*;
import net.minecraft.client.settings.*;
import net.minecraft.client.gui.*;
import net.minecraft.inventory.*;
import me.oringo.oringoclient.events.*;
import net.minecraft.network.play.server.*;
import net.minecraft.client.gui.inventory.*;
import java.util.stream.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import me.oringo.oringoclient.utils.*;
import java.util.function.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import java.util.*;
import net.minecraft.client.renderer.*;

public class MithrilMacro extends Module
{
    private Minecraft mc;
    private BlockPos target;
    private BlockPos test;
    private Vec3 targetRotation;
    private Vec3 targetRotation2;
    private ArrayList<Float> yaw;
    private ArrayList<Float> pitch;
    private boolean stopLoop;
    private int ticksTargeting;
    private int ticksMining;
    private int ticks;
    private int ticksSeen;
    private int shouldReconnect;
    public EntityArmorStand drillnpc;
    private int lastKey;
    private int timeLeft;
    private int pause;
    private BooleanSetting drillRefuel;
    private NumberSetting rotations;
    private NumberSetting accuracyChecks;
    private NumberSetting maxBreakTime;
    private NumberSetting quickBreak;
    private NumberSetting panic;
    private BooleanSetting titanium;
    private BooleanSetting sneak;
    private BooleanSetting under;
    private BooleanSetting autoAbility;
    private NumberSetting moreMovement;
    private NumberSetting walking;
    private NumberSetting walkingTime;
    private ModeSetting mode;
    
    public MithrilMacro() {
        super("Mithril Macro", 0, Category.OTHER);
        this.mc = Minecraft.getMinecraft();
        this.target = null;
        this.test = null;
        this.targetRotation = null;
        this.targetRotation2 = null;
        this.yaw = new ArrayList<Float>();
        this.pitch = new ArrayList<Float>();
        this.stopLoop = false;
        this.ticksTargeting = 0;
        this.ticksMining = 0;
        this.ticks = 0;
        this.ticksSeen = 0;
        this.shouldReconnect = -1;
        this.lastKey = -1;
        this.timeLeft = 0;
        this.pause = 0;
        this.drillRefuel = new BooleanSetting("Drill Refuel", false);
        this.rotations = new NumberSetting("Rotations", 10.0, 1.0, 20.0, 1.0);
        this.accuracyChecks = new NumberSetting("Accuracy", 5.0, 3.0, 10.0, 1.0);
        this.maxBreakTime = new NumberSetting("Max break time", 160.0, 40.0, 400.0, 1.0);
        this.quickBreak = new NumberSetting("Block skip progress", 0.9, 0.0, 1.0, 0.1);
        this.panic = new NumberSetting("Auto leave", 100.0, 0.0, 200.0, 1.0);
        this.titanium = new BooleanSetting("Prioritize titanium", true);
        this.sneak = new BooleanSetting("Sneak", false);
        this.under = new BooleanSetting("Mine under", false);
        this.autoAbility = new BooleanSetting("Auto ability", true);
        this.moreMovement = new NumberSetting("Head movements", 5.0, 0.0, 50.0, 1.0);
        this.walking = new NumberSetting("Walking %", 0.1, 0.0, 5.0, 0.1);
        this.walkingTime = new NumberSetting("Walking ticks", 5.0, 0.0, 60.0, 1.0);
        this.mode = new ModeSetting("Target", "Clay", new String[] { "Clay", "Prismarine", "Wool", "Blue", "Gold" });
        this.addSettings(this.rotations, this.drillRefuel, this.accuracyChecks, this.titanium, this.sneak, this.quickBreak, this.maxBreakTime, this.autoAbility, this.under, this.panic, this.moreMovement, this.walking, this.walkingTime, this.mode);
    }
    
    @SubscribeEvent
    public void onLoad(final WorldEvent.Load event) {
        this.drillnpc = null;
        if (this.isToggled()) {
            this.setToggled(false);
            if (OringoClient.aotvReturn.isToggled()) {
                OringoClient.aotvReturn.start(() -> this.setToggled(true), false);
            }
        }
    }
    
    @SubscribeEvent
    public void onWorldRender(final RenderWorldLastEvent event) {
        if (!this.isToggled()) {
            return;
        }
        if (this.target != null) {
            RenderUtils.blockBox(this.target, Color.CYAN);
        }
        if (this.targetRotation != null) {
            RenderUtils.miniBlockBox(this.targetRotation, Color.GREEN);
        }
        if (this.targetRotation2 != null) {
            RenderUtils.miniBlockBox(this.targetRotation2, Color.RED);
        }
    }
    
    @SubscribeEvent
    public void reconnect(final TickEvent.ClientTickEvent event) {
        if (this.mc.currentScreen instanceof GuiDisconnected && this.shouldReconnect < 0 && this.isToggled()) {
            this.shouldReconnect = 250;
            this.setToggled(false);
        }
        if (this.shouldReconnect-- == 0) {
            this.mc.displayGuiScreen((GuiScreen)new GuiConnecting((GuiScreen)new GuiMainMenu(), this.mc, new ServerData("Hypixel", "play.Hypixel.net", false)));
            new Thread(() -> {
                try {
                    Thread.sleep(15000L);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (this.mc.thePlayer != null && OringoClient.aotvReturn.isToggled()) {
                    OringoClient.aotvReturn.start(() -> this.setToggled(true), false);
                }
            }).start();
        }
    }
    
    @Override
    public void onEnable() {
        this.ticksSeen = 0;
        this.ticksMining = 0;
        this.ticksTargeting = 0;
        if (this.autoAbility.isEnabled() && this.mc.thePlayer.getHeldItem() != null) {
            this.mc.playerController.sendUseItem((EntityPlayer)this.mc.thePlayer, (World)this.mc.theWorld, this.mc.thePlayer.getHeldItem());
        }
    }
    
    @SubscribeEvent
    public void onChat(final ClientChatReceivedEvent event) {
        if (!this.isToggled()) {
            return;
        }
        final String message = event.message.getFormattedText();
        if (this.drillRefuel.isEnabled() && ChatFormatting.stripFormatting(message).startsWith("Your") && ChatFormatting.stripFormatting(message).endsWith("Refuel it by talking to a Drill Mechanic!") && this.drillnpc != null) {
            this.setToggled(false);
            int[] array;
            int length;
            int l = 0;
            int a;
            int i;
            Slot slot;
            int j;
            Slot slot2;
            new Thread(() -> {
                try {
                    array = new int[] { this.mc.gameSettings.keyBindForward.getKeyCode(), this.mc.gameSettings.keyBindLeft.getKeyCode(), this.mc.gameSettings.keyBindBack.getKeyCode(), this.mc.gameSettings.keyBindRight.getKeyCode(), this.mc.gameSettings.keyBindSneak.getKeyCode(), this.mc.gameSettings.keyBindAttack.getKeyCode() };
                    for (length = array.length; l < length; ++l) {
                        a = array[l];
                        KeyBinding.setKeyBindState(a, false);
                    }
                    Thread.sleep(500L);
                    this.mc.playerController.interactWithEntitySendPacket((EntityPlayer)this.mc.thePlayer, (Entity)this.drillnpc);
                    Thread.sleep(2500L);
                    if (this.mc.thePlayer.openContainer instanceof ContainerChest && ((ContainerChest)this.mc.thePlayer.openContainer).getLowerChestInventory().getDisplayName().getUnformattedText().contains("Drill Anvil")) {
                        i = 0;
                        while (i < this.mc.thePlayer.openContainer.inventorySlots.size()) {
                            slot = this.mc.thePlayer.openContainer.getSlot(i);
                            if (slot.getHasStack() && slot.getStack().getDisplayName().contains("Drill") && slot.getStack().getItem() == Items.prismarine_shard) {
                                this.mc.playerController.windowClick(this.mc.thePlayer.openContainer.windowId, slot.slotNumber, 0, 1, (EntityPlayer)this.mc.thePlayer);
                                break;
                            }
                            else {
                                ++i;
                            }
                        }
                        Thread.sleep(500L);
                        j = 0;
                        while (j < this.mc.thePlayer.openContainer.inventorySlots.size()) {
                            slot2 = this.mc.thePlayer.openContainer.getSlot(j);
                            if (slot2.getHasStack() && (slot2.getStack().getDisplayName().contains("Volta") || slot2.getStack().getDisplayName().contains("Oil Barrel") || slot2.getStack().getDisplayName().contains("Biofuel"))) {
                                this.mc.playerController.windowClick(this.mc.thePlayer.openContainer.windowId, slot2.slotNumber, 0, 1, (EntityPlayer)this.mc.thePlayer);
                                break;
                            }
                            else {
                                ++j;
                            }
                        }
                        Thread.sleep(500L);
                        this.mc.playerController.windowClick(this.mc.thePlayer.openContainer.windowId, 22, 0, 0, (EntityPlayer)this.mc.thePlayer);
                        Thread.sleep(250L);
                        this.mc.playerController.windowClick(this.mc.thePlayer.openContainer.windowId, 13, 0, 1, (EntityPlayer)this.mc.thePlayer);
                        Thread.sleep(250L);
                        this.mc.thePlayer.closeScreen();
                    }
                    Thread.sleep(2500L);
                    this.setToggled(true);
                    KeyBinding.setKeyBindState(this.mc.gameSettings.keyBindAttack.getKeyCode(), true);
                    this.mc.displayGuiScreen((GuiScreen)new GuiChat());
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return;
            }).start();
        }
        if (ChatFormatting.stripFormatting(event.message.getUnformattedText()).equals("Mining Speed Boost is now available!") && this.autoAbility.isEnabled() && this.mc.thePlayer.getHeldItem() != null) {
            OringoClient.sendMessageWithPrefix("Auto ability");
            this.mc.playerController.sendUseItem((EntityPlayer)this.mc.thePlayer, (World)this.mc.theWorld, this.mc.thePlayer.getHeldItem());
        }
        if (ChatFormatting.stripFormatting(event.message.getUnformattedText()).equals("Oh no! Your Pickonimbus 2000 broke!")) {
            int k;
            new Thread(() -> {
                try {
                    Thread.sleep(1000L);
                }
                catch (InterruptedException e2) {
                    e2.printStackTrace();
                }
                k = 0;
                while (k < 9) {
                    if (this.mc.thePlayer.inventory.getStackInSlot(k) != null && this.mc.thePlayer.inventory.getStackInSlot(k).getDisplayName().contains("Pickonimbus")) {
                        this.mc.thePlayer.inventory.currentItem = k;
                        break;
                    }
                    else {
                        ++k;
                    }
                }
            }).start();
        }
    }
    
    @Override
    public void onDisable() {
        KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindAttack.getKeyCode(), false);
        KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindSneak.getKeyCode(), false);
    }
    
    @SubscribeEvent(receiveCanceled = true)
    public void onPacket(final PacketReceivedEvent event) {
        if (event.packet instanceof S08PacketPlayerPosLook && this.isToggled()) {
            this.pause = 200;
            this.target = null;
            KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindAttack.getKeyCode(), false);
            KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindSneak.getKeyCode(), false);
            for (final int a : new int[] { this.mc.gameSettings.keyBindForward.getKeyCode(), this.mc.gameSettings.keyBindLeft.getKeyCode(), this.mc.gameSettings.keyBindBack.getKeyCode(), this.mc.gameSettings.keyBindRight.getKeyCode() }) {
                KeyBinding.setKeyBindState(a, false);
            }
        }
    }
    
    private boolean isPickaxe(final ItemStack itemStack) {
        return itemStack != null && (itemStack.getDisplayName().contains("Pickaxe") || itemStack.getItem() == Items.prismarine_shard || itemStack.getDisplayName().contains("Gauntlet"));
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        --this.pause;
        if (this.isToggled() && !(this.mc.currentScreen instanceof GuiContainer) && !(this.mc.currentScreen instanceof GuiEditSign) && this.pause < 1) {
            ++this.ticks;
            if (this.mc.thePlayer != null && this.mc.thePlayer.getHeldItem() != null && this.mc.thePlayer.getHeldItem().getItem() instanceof ItemMap) {
                this.setToggled(false);
                this.mc.thePlayer.sendChatMessage("/l");
            }
            if (this.mc.theWorld != null) {
                if (this.drillnpc == null && this.drillRefuel.isEnabled()) {
                    for (final Entity entityArmorStand : (List)this.mc.theWorld.getLoadedEntityList().stream().filter(entity -> entity instanceof EntityArmorStand).collect(Collectors.toList())) {
                        if (entityArmorStand.getDisplayName().getFormattedText().contains("§e§lDRILL MECHANIC§r")) {
                            OringoClient.mithrilMacro.drillnpc = (EntityArmorStand)entityArmorStand;
                            OringoClient.sendMessageWithPrefix("Mechanic");
                            return;
                        }
                    }
                    this.setToggled(false);
                    OringoClient.aotvReturn.start(() -> this.setToggled(true), false);
                    return;
                }
                if (!this.isPickaxe(this.mc.thePlayer.getHeldItem())) {
                    for (int i = 0; i < 9; ++i) {
                        if (this.isPickaxe(this.mc.thePlayer.inventory.getStackInSlot(i))) {
                            PlayerUtils.swapToSlot(i);
                        }
                    }
                }
                if (this.timeLeft-- <= 0) {
                    final int[] keybinds = { this.mc.gameSettings.keyBindForward.getKeyCode(), this.mc.gameSettings.keyBindLeft.getKeyCode(), this.mc.gameSettings.keyBindBack.getKeyCode(), this.mc.gameSettings.keyBindRight.getKeyCode(), this.mc.gameSettings.keyBindLeft.getKeyCode(), this.mc.gameSettings.keyBindBack.getKeyCode(), this.mc.gameSettings.keyBindRight.getKeyCode(), this.mc.gameSettings.keyBindBack.getKeyCode(), this.mc.gameSettings.keyBindBack.getKeyCode() };
                    if (this.lastKey != -1) {
                        KeyBinding.setKeyBindState(this.lastKey, false);
                        KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindSneak.getKeyCode(), this.sneak.isEnabled());
                    }
                    if (new Random().nextFloat() < this.walking.getValue() / 100.0) {
                        this.lastKey = keybinds[new Random().nextInt(keybinds.length)];
                        KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindSneak.getKeyCode(), true);
                        KeyBinding.setKeyBindState(this.lastKey, true);
                        this.timeLeft = (int)this.walkingTime.getValue();
                    }
                }
                else {
                    KeyBinding.setKeyBindState(this.lastKey, true);
                    KeyBinding.setKeyBindState(this.mc.gameSettings.keyBindSneak.getKeyCode(), true);
                }
                if (this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
                    final Entity entity2 = this.mc.objectMouseOver.entityHit;
                    if (entity2 instanceof EntityPlayer && !EntityUtils.isTeam((EntityLivingBase)entity2)) {
                        SkyblockUtils.click();
                        this.pause = 5;
                        return;
                    }
                }
                if (this.mc.theWorld.playerEntities.stream().anyMatch(playerEntity -> !playerEntity.equals((Object)this.mc.thePlayer) && playerEntity.getDistanceToEntity((Entity)this.mc.thePlayer) < 10.0f && EntityUtils.isTeam((EntityLivingBase)playerEntity) && (!playerEntity.isInvisible() || playerEntity.posY - this.mc.thePlayer.posY <= 5.0))) {
                    ++this.ticksSeen;
                }
                else {
                    this.ticksSeen = 0;
                }
                final boolean inDwarven = SkyblockUtils.anyTab("Dwarven Mines");
                if ((this.panic.getValue() <= this.ticksSeen && this.panic.getValue() != 0.0) || !inDwarven) {
                    this.setToggled(false);
                    if (OringoClient.aotvReturn.isToggled()) {
                        OringoClient.aotvReturn.start(() -> this.setToggled(true), false);
                    }
                    this.ticksSeen = 0;
                    OringoClient.sendMessageWithPrefix(inDwarven ? ("You have been seen by " + ((EntityPlayer)this.mc.theWorld.playerEntities.stream().filter(playerEntity -> !((EntityPlayer)playerEntity).equals((Object)this.mc.thePlayer) && ((EntityPlayer)playerEntity).getDistanceToEntity((Entity)this.mc.thePlayer) < 10.0f && EntityUtils.isTeam(playerEntity)).findFirst().get()).getName()) : "Not in dwarven");
                    return;
                }
                if (this.target == null) {
                    if (!this.findTarget()) {
                        OringoClient.sendMessageWithPrefix("No possible target found");
                    }
                    return;
                }
                if (this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
                    if (this.ticksTargeting++ == 40) {
                        this.setToggled(false);
                        if (OringoClient.aotvReturn.isToggled()) {
                            OringoClient.aotvReturn.start(() -> this.setToggled(true), false);
                        }
                        return;
                    }
                }
                else {
                    this.ticksTargeting = 0;
                }
                KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindAttack.getKeyCode(), true);
                if (this.sneak.isEnabled() || this.timeLeft != 0) {
                    KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindSneak.getKeyCode(), true);
                }
                if (this.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && this.mc.currentScreen != null && !(this.mc.currentScreen instanceof GuiContainer) && this.ticks % 2 == 0) {
                    SkyblockUtils.click();
                }
                if (!this.yaw.isEmpty() && (this.stopLoop || !this.isTitanium(this.target))) {
                    this.mc.thePlayer.rotationYaw = this.yaw.get(0);
                    this.mc.thePlayer.rotationPitch = this.pitch.get(0);
                    this.yaw.remove(0);
                    this.pitch.remove(0);
                    if (this.yaw.isEmpty() && this.isBlockVisible(this.target) && this.moreMovement.getValue() != 0.0) {
                        this.stopLoop = false;
                        final Vec3 targetRotationTemp = this.targetRotation;
                        this.targetRotation = this.getRandomVisibilityLine(this.target);
                        this.targetRotation2 = this.targetRotation;
                        this.getRotations(false);
                        this.targetRotation = targetRotationTemp;
                        return;
                    }
                    if (this.moreMovement.getValue() == 0.0) {
                        this.targetRotation2 = null;
                    }
                    if (this.stopLoop) {
                        return;
                    }
                }
                if (this.mc.theWorld.getBlockState(this.target).getBlock().equals(Blocks.bedrock)) {
                    if (!this.findTarget()) {}
                    return;
                }
                if (this.mc.objectMouseOver.typeOfHit != MovingObjectPosition.MovingObjectType.BLOCK) {
                    if (!this.findTarget()) {}
                    return;
                }
                final BlockPos pos = this.mc.objectMouseOver.getBlockPos();
                if (!pos.equals((Object)this.target)) {
                    if (!this.findTarget()) {}
                    return;
                }
                if (this.quickBreak.getValue() != 0.0 && !this.isTitanium(this.target) && OringoClient.getBlockBreakProgress().values().stream().anyMatch(progress -> progress.getPosition().equals((Object)this.target)) && OringoClient.getBlockBreakProgress().values().stream().anyMatch(progress -> progress.getPosition().equals((Object)this.target) && progress.getPartialBlockDamage() == (int)(this.quickBreak.getValue() * 10.0))) {
                    this.findTarget();
                }
                if (this.ticksMining++ == this.maxBreakTime.getValue()) {
                    OringoClient.sendMessageWithPrefix("Mining one block took too long");
                    this.findTarget();
                }
            }
        }
    }
    
    private void getRotations(final boolean stop) {
        final Vec3 lookVec = this.mc.thePlayer.getLookVec().add(this.mc.thePlayer.getPositionEyes(0.0f));
        if (!this.yaw.isEmpty()) {
            this.yaw.clear();
            this.pitch.clear();
        }
        final double max = (this.rotations.getValue() + 1.0) * (stop ? 1.0 : this.moreMovement.getValue());
        for (int i = 0; i < max; ++i) {
            final Vec3 target = new Vec3(lookVec.xCoord + (this.targetRotation.xCoord - lookVec.xCoord) / max * i, lookVec.yCoord + (this.targetRotation.yCoord - lookVec.yCoord) / max * i, lookVec.zCoord + (this.targetRotation.zCoord - lookVec.zCoord) / max * i);
            final Rotation rotation = RotationUtils.getRotations(target);
            this.yaw.add(rotation.getYaw());
            this.pitch.add(rotation.getPitch());
        }
        this.stopLoop = stop;
    }
    
    private boolean findTarget() {
        final ArrayList<BlockPos> blocks = new ArrayList<BlockPos>();
        for (int x = -5; x < 6; ++x) {
            for (int y = -5; y < 6; ++y) {
                for (int z = -5; z < 6; ++z) {
                    blocks.add(new BlockPos(this.mc.thePlayer.posX + x, this.mc.thePlayer.posY + y, this.mc.thePlayer.posZ + z));
                }
            }
        }
        final BlockPos sortingCenter = (this.target != null) ? this.target : this.mc.thePlayer.getPosition();
        final BlockPos pos2;
        Optional<BlockPos> any = blocks.stream().filter(pos -> !pos.equals((Object)this.target)).filter(this::matchesMode).filter(pos -> this.mc.thePlayer.getDistance((double)pos.getX(), (double)(pos.getY() - this.mc.thePlayer.getEyeHeight()), (double)pos.getZ()) < 5.5).filter(this::isBlockVisible).min(Comparator.comparingDouble(pos -> (this.isTitanium(pos) && this.titanium.isEnabled()) ? 0.0 : this.getDistance(pos, pos2, 0.6)));
        if (any.isPresent()) {
            this.target = any.get();
            this.targetRotation2 = null;
            this.targetRotation = this.getRandomVisibilityLine(any.get());
            this.getRotations(true);
        }
        else {
            final BlockPos pos3;
            any = blocks.stream().filter(pos -> !pos.equals((Object)this.target)).filter(this::matchesAny).filter(pos -> this.mc.thePlayer.getDistance((double)pos.getX(), (double)(pos.getY() - this.mc.thePlayer.getEyeHeight()), (double)pos.getZ()) < 5.5).filter(this::isBlockVisible).min(Comparator.comparingDouble(pos -> (this.isTitanium(pos) && this.titanium.isEnabled()) ? 0.0 : this.getDistance(pos, pos3, 0.6)));
            if (any.isPresent()) {
                this.target = any.get();
                this.targetRotation2 = null;
                this.targetRotation = this.getRandomVisibilityLine(any.get());
                this.getRotations(true);
            }
        }
        this.ticksMining = 0;
        return any.isPresent();
    }
    
    private double getDistance(final BlockPos pos1, final BlockPos pos2, final double multiY) {
        final double deltaX = pos1.getX() - pos2.getX();
        final double deltaY = (pos1.getY() - pos2.getY()) * multiY;
        final double deltaZ = pos1.getZ() - pos2.getZ();
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
    }
    
    private boolean isBlockVisible(final BlockPos pos) {
        return this.getRandomVisibilityLine(pos) != null;
    }
    
    private Vec3 getRandomVisibilityLine(final BlockPos pos) {
        final List<Vec3> lines = new ArrayList<Vec3>();
        for (int x = 0; x < this.accuracyChecks.getValue(); ++x) {
            for (int y = 0; y < this.accuracyChecks.getValue(); ++y) {
                for (int z = 0; z < this.accuracyChecks.getValue(); ++z) {
                    final Vec3 target = new Vec3(pos.getX() + x / this.accuracyChecks.getValue(), pos.getY() + y / this.accuracyChecks.getValue(), pos.getZ() + z / this.accuracyChecks.getValue());
                    this.test = new BlockPos(target.xCoord, target.yCoord, target.zCoord);
                    final MovingObjectPosition movingObjectPosition = this.mc.theWorld.rayTraceBlocks(this.mc.thePlayer.getPositionEyes(0.0f), target, true, false, true);
                    if (movingObjectPosition != null) {
                        final BlockPos obj = movingObjectPosition.getBlockPos();
                        if (obj.equals((Object)this.test) && this.mc.thePlayer.getDistance(target.xCoord, target.yCoord - this.mc.thePlayer.getEyeHeight(), target.zCoord) < 4.5 && (this.under.isEnabled() || Math.abs(this.mc.thePlayer.posY - target.yCoord) > 1.3)) {
                            lines.add(target);
                        }
                    }
                }
            }
        }
        return lines.isEmpty() ? null : lines.get(new Random().nextInt(lines.size()));
    }
    
    private boolean isTitanium(final BlockPos pos) {
        final IBlockState state = this.mc.theWorld.getBlockState(pos);
        return state.getBlock() == Blocks.stone && ((BlockStone.EnumType)state.getValue((IProperty)BlockStone.VARIANT)).equals((Object)BlockStone.EnumType.DIORITE_SMOOTH);
    }
    
    private boolean matchesMode(final BlockPos pos) {
        final IBlockState state = this.mc.theWorld.getBlockState(pos);
        if (this.isTitanium(pos)) {
            return true;
        }
        final String selected = this.mode.getSelected();
        switch (selected) {
            case "Clay": {
                return state.getBlock().equals(Blocks.stained_hardened_clay) || (state.getBlock().equals(Blocks.wool) && ((EnumDyeColor)state.getValue((IProperty)BlockColored.COLOR)).equals((Object)EnumDyeColor.GRAY));
            }
            case "Prismarine": {
                return state.getBlock().equals(Blocks.prismarine);
            }
            case "Wool": {
                return state.getBlock().equals(Blocks.wool) && ((EnumDyeColor)state.getValue((IProperty)BlockColored.COLOR)).equals((Object)EnumDyeColor.LIGHT_BLUE);
            }
            case "Blue": {
                return (state.getBlock().equals(Blocks.wool) && ((EnumDyeColor)state.getValue((IProperty)BlockColored.COLOR)).equals((Object)EnumDyeColor.LIGHT_BLUE)) || state.getBlock().equals(Blocks.prismarine);
            }
            case "Gold": {
                return state.getBlock().equals(Blocks.gold_block);
            }
            default: {
                return false;
            }
        }
    }
    
    private boolean matchesAny(final BlockPos pos) {
        final IBlockState state = this.mc.theWorld.getBlockState(pos);
        return (state.getBlock().equals(Blocks.wool) && state.getProperties().entrySet().stream().anyMatch(entry -> entry.toString().contains("lightBlue"))) || state.getBlock().equals(Blocks.prismarine) || state.getBlock().equals(Blocks.stained_hardened_clay) || (state.getBlock().equals(Blocks.wool) && state.getProperties().entrySet().stream().anyMatch(entry -> entry.toString().contains("gray"))) || this.isTitanium(pos);
    }
}
