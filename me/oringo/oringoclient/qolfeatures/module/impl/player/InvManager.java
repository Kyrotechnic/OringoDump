//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.impl.player;

import me.oringo.oringoclient.qolfeatures.module.*;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.*;
import me.oringo.oringoclient.qolfeatures.module.settings.*;
import net.minecraft.client.gui.inventory.*;
import me.oringo.oringoclient.qolfeatures.module.impl.combat.*;
import me.oringo.oringoclient.*;
import net.minecraftforge.fml.common.eventhandler.*;
import me.oringo.oringoclient.events.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.player.*;
import me.oringo.oringoclient.utils.*;
import java.util.function.*;
import java.util.stream.*;
import net.minecraftforge.event.entity.player.*;
import org.lwjgl.input.*;
import com.mojang.realmsclient.gui.*;
import me.oringo.oringoclient.ui.notifications.*;
import net.minecraft.inventory.*;
import net.minecraft.enchantment.*;
import me.oringo.oringoclient.mixins.item.*;
import net.minecraft.item.*;
import java.io.*;
import net.minecraft.entity.*;
import java.util.*;

public class InvManager extends Module
{
    public NumberSetting delay;
    public BooleanSetting dropTrash;
    public BooleanSetting autoArmor;
    public BooleanSetting middleClick;
    public ModeSetting trashItems;
    public ModeSetting mode;
    private MilliTimer delayTimer;
    private boolean wasPressed;
    public NumberSetting swordSlot;
    public NumberSetting blockSlot;
    public NumberSetting gappleSlot;
    public NumberSetting pickaxeSlot;
    public NumberSetting axeSlot;
    public NumberSetting shovelSlot;
    public NumberSetting bowSlot;
    private List<String> dropSkyblock;
    private List<String> dropSkywars;
    public static List<String> dropCustom;
    
    public InvManager() {
        super("Inventory Manager", 0, Category.PLAYER);
        this.delay = new NumberSetting("Delay", 30.0, 0.0, 300.0, 1.0);
        this.dropTrash = new BooleanSetting("Drop trash", true);
        this.autoArmor = new BooleanSetting("Auto Armor", false);
        this.middleClick = new BooleanSetting("Middle click to drop", false);
        this.trashItems = new ModeSetting("Trash items", "Skyblock", new String[] { "Skyblock", "Skywars", "Custom" });
        this.mode = new ModeSetting("Mode", "Inv open", new String[] { "Inv open", "Always" });
        this.delayTimer = new MilliTimer();
        this.swordSlot = new NumberSetting("Sword slot", 0.0, 0.0, 9.0, 1.0);
        this.blockSlot = new NumberSetting("Block slot", 0.0, 0.0, 9.0, 1.0);
        this.gappleSlot = new NumberSetting("Gapple slot", 0.0, 0.0, 9.0, 1.0);
        this.pickaxeSlot = new NumberSetting("Pickaxe slot", 0.0, 0.0, 9.0, 1.0);
        this.axeSlot = new NumberSetting("Axe slot", 0.0, 0.0, 9.0, 1.0);
        this.shovelSlot = new NumberSetting("Shovel slot", 0.0, 0.0, 9.0, 1.0);
        this.bowSlot = new NumberSetting("Bow slot", 0.0, 0.0, 9.0, 1.0);
        this.dropSkyblock = Arrays.asList("Training Weight", "Healing Potion", "Beating Heart", "Premium Flesh", "Mimic Fragment", "Enchanted Rotten Flesh", "Machine Gun Bow", "Enchanted Bone", "Defuse Kit", "Enchanted Ice", "Diamond Atom", "Silent Death", "Cutlass", "Soulstealer Bow", "Sniper Bow", "Optical Lens", "Tripwire Hook", "Button", "Carpet", "Lever", "Journal Entry", "Sign", "Zombie Commander", "Zombie Lord", "Skeleton Master, Skeleton Grunt, Skeleton Lord, Zombie Soldier", "Zombie Knight", "Heavy", "Super Heavy", "Undead", "Bouncy", "Skeletor", "Trap", "Inflatable Jerry");
        this.dropSkywars = Arrays.asList("Egg", "Snowball", "Poison", "Lava", "Steak", "Enchanting", "Poison");
        this.addSettings(this.mode, this.delay, this.dropTrash, this.trashItems, this.middleClick, this.autoArmor, this.swordSlot, this.pickaxeSlot, this.axeSlot, this.shovelSlot, this.blockSlot);
    }
    
    @SubscribeEvent
    public void onUpdate(final MotionUpdateEvent.Pre event) {
        if (this.isToggled() && (InvManager.mc.currentScreen instanceof GuiInventory || (InvManager.mc.currentScreen == null && this.mode.is("Always") && KillAura.target == null)) && !OringoClient.scaffold.isToggled()) {
            if (this.autoArmor.isEnabled()) {
                this.getBestArmor();
            }
            if (this.dropTrash.isEnabled()) {
                this.dropTrash();
            }
            if (this.swordSlot.getValue() != 0.0) {
                this.getBestSword();
            }
            this.getBestTools();
            if (this.blockSlot.getValue() != 0.0) {
                this.getBestBlock();
            }
        }
        else {
            this.delayTimer.reset();
        }
    }
    
    @SubscribeEvent
    public void onPacket(final PacketSentEvent event) {
        if (event.packet instanceof C02PacketUseEntity || event.packet instanceof C08PacketPlayerBlockPlacement) {
            this.delayTimer.reset();
        }
    }
    
    private EntityPlayer getClosestPlayer(final double distance) {
        final List<EntityPlayer> players = (List<EntityPlayer>)InvManager.mc.theWorld.playerEntities.stream().filter(entityPlayer -> entityPlayer != InvManager.mc.thePlayer && !EntityUtils.isTeam((EntityLivingBase)entityPlayer) && !SkyblockUtils.isNPC(entityPlayer) && InvManager.mc.thePlayer.getDistanceToEntity(entityPlayer) < distance).sorted(Comparator.comparingDouble((ToDoubleFunction<? super T>)InvManager.mc.thePlayer::getDistanceToEntity)).collect(Collectors.toList());
        if (!players.isEmpty()) {
            return players.get(0);
        }
        return null;
    }
    
    @SubscribeEvent
    public void onTooltip(final ItemTooltipEvent event) {
        if (Mouse.isButtonDown(2) && InvManager.mc.currentScreen instanceof GuiInventory && this.middleClick.isEnabled()) {
            if (!this.wasPressed) {
                this.wasPressed = true;
                final String name = ChatFormatting.stripFormatting(event.itemStack.getDisplayName());
                if (InvManager.dropCustom.contains(name)) {
                    InvManager.dropCustom.remove(name);
                    Notifications.showNotification("Oringo Client", "Removed " + name + " from custom drop list", 2000);
                }
                else {
                    InvManager.dropCustom.add(name);
                    Notifications.showNotification("Oringo Client", "Added " + ChatFormatting.AQUA + name + ChatFormatting.RESET + " to custom drop list", 2000);
                }
                save();
            }
        }
        else {
            this.wasPressed = false;
        }
    }
    
    public void dropTrash() {
        for (final Slot slot : InvManager.mc.thePlayer.inventoryContainer.inventorySlots) {
            if (slot.getHasStack() && this.canInteract()) {
                if (this.trashItems.getSelected().equals("Custom")) {
                    if (!InvManager.dropCustom.contains(ChatFormatting.stripFormatting(slot.getStack().getDisplayName()))) {
                        continue;
                    }
                    this.drop(slot.slotNumber);
                }
                else if (this.trashItems.getSelected().equals("Skyblock") && this.dropSkyblock.stream().anyMatch(a -> a.contains(ChatFormatting.stripFormatting(slot.getStack().getDisplayName())))) {
                    this.drop(slot.slotNumber);
                }
                else {
                    if (!this.trashItems.getSelected().equals("Skywars") || !this.dropSkywars.stream().anyMatch(a -> a.contains(ChatFormatting.stripFormatting(slot.getStack().getDisplayName())))) {
                        continue;
                    }
                    this.drop(slot.slotNumber);
                }
            }
        }
    }
    
    public void getBestArmor() {
        for (int i = 5; i < 9; ++i) {
            if (InvManager.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack() && this.canInteract()) {
                final ItemStack armor = InvManager.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (!isBestArmor(armor, i)) {
                    this.drop(i);
                }
            }
        }
        for (int i = 9; i < 45; ++i) {
            if (InvManager.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack() && this.canInteract()) {
                final ItemStack stack = InvManager.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (stack.getItem() instanceof ItemArmor) {
                    if (isBestArmor(stack, i)) {
                        this.shiftClick(i);
                    }
                    else {
                        this.drop(i);
                    }
                }
            }
        }
    }
    
    public static boolean isBestArmor(final ItemStack armor, final int slot) {
        if (!(armor.getItem() instanceof ItemArmor)) {
            return false;
        }
        for (int i = 5; i < 45; ++i) {
            if (InvManager.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                final ItemStack is = InvManager.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (is.getItem() instanceof ItemArmor && ((getProtection(is) > getProtection(armor) && slot < 9) || (slot >= 9 && getProtection(is) >= getProtection(armor) && slot != i)) && ((ItemArmor)is.getItem()).armorType == ((ItemArmor)armor.getItem()).armorType) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public static float getProtection(final ItemStack stack) {
        float prot = 0.0f;
        if (stack.getItem() instanceof ItemArmor) {
            final ItemArmor armor = (ItemArmor)stack.getItem();
            prot += (float)(armor.damageReduceAmount + (100 - armor.damageReduceAmount) * EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, stack) * 0.0075);
            prot += (float)(EnchantmentHelper.getEnchantmentLevel(Enchantment.blastProtection.effectId, stack) / 100.0);
            prot += (float)(EnchantmentHelper.getEnchantmentLevel(Enchantment.fireProtection.effectId, stack) / 100.0);
            prot += (float)(EnchantmentHelper.getEnchantmentLevel(Enchantment.thorns.effectId, stack) / 100.0);
            prot += (float)(EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack) / 50.0);
            prot += (float)(EnchantmentHelper.getEnchantmentLevel(Enchantment.projectileProtection.effectId, stack) / 100.0);
            prot += (float)(stack.getMaxDamage() / 1000.0);
        }
        return prot;
    }
    
    public void getBestSword() {
        for (int i = 9; i < 45; ++i) {
            if (InvManager.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack() && this.canInteract()) {
                final ItemStack stack = InvManager.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (stack.getItem() instanceof ItemSword) {
                    if (this.isBestSword(stack, i)) {
                        if (this.getHotbarID((int)this.swordSlot.getValue()) != i) {
                            this.numberClick(i, (int)this.swordSlot.getValue() - 1);
                        }
                    }
                    else {
                        this.drop(i);
                    }
                }
            }
        }
    }
    
    public boolean isBestSword(final ItemStack sword, final int slot) {
        if (!(sword.getItem() instanceof ItemSword)) {
            return false;
        }
        for (int i = 9; i < 45; ++i) {
            if (InvManager.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                final ItemStack is = InvManager.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (is.getItem() instanceof ItemSword && ((getToolDamage(is) > getToolDamage(sword) && slot == this.getHotbarID((int)this.swordSlot.getValue())) || (slot != this.getHotbarID((int)this.swordSlot.getValue()) && getToolDamage(is) >= getToolDamage(sword) && slot != i))) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public void getBestTools() {
        for (int i = 9; i < 45; ++i) {
            if (InvManager.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack() && this.canInteract()) {
                final ItemStack stack = InvManager.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (stack.getItem() instanceof ItemTool && this.getToolHotbarSlot(stack) != 0) {
                    if (this.isBestTool(stack, i)) {
                        if (this.getHotbarID(this.getToolHotbarSlot(stack)) != i) {
                            this.numberClick(i, this.getToolHotbarSlot(stack) - 1);
                        }
                    }
                    else {
                        this.drop(i);
                    }
                }
            }
        }
    }
    
    public boolean isBestTool(final ItemStack tool, final int slot) {
        if (!(tool.getItem() instanceof ItemTool)) {
            return false;
        }
        for (int i = 9; i < 45; ++i) {
            if (InvManager.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                final ItemStack is = InvManager.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (this.getToolHotbarSlot(is) != 0) {
                    if (tool.getItem() instanceof ItemAxe && is.getItem() instanceof ItemAxe) {
                        if ((getMaterial(is) > getMaterial(tool) && slot == this.getHotbarID((int)this.axeSlot.getValue())) || (slot != this.getHotbarID((int)this.axeSlot.getValue()) && getToolDamage(is) >= getToolDamage(tool) && slot != i)) {
                            return false;
                        }
                    }
                    else if (tool.getItem() instanceof ItemPickaxe && is.getItem() instanceof ItemPickaxe) {
                        if ((getMaterial(is) > getMaterial(tool) && slot == this.getHotbarID((int)this.pickaxeSlot.getValue())) || (slot != this.getHotbarID((int)this.pickaxeSlot.getValue()) && getToolDamage(is) >= getToolDamage(tool) && slot != i)) {
                            return false;
                        }
                    }
                    else if (tool.getItem() instanceof ItemSpade && is.getItem() instanceof ItemSpade && ((getMaterial(is) > getMaterial(tool) && slot == this.getHotbarID((int)this.shovelSlot.getValue())) || (slot != this.getHotbarID((int)this.pickaxeSlot.getValue()) && getToolDamage(is) >= getToolDamage(tool) && slot != i))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    public int getToolHotbarSlot(final ItemStack tool) {
        if (tool == null || !(tool.getItem() instanceof ItemTool)) {
            return 0;
        }
        final String toolClass = ((ItemToolAccessor)tool.getItem()).getToolClass();
        switch (toolClass) {
            case "pickaxe": {
                return (int)this.pickaxeSlot.getValue();
            }
            case "axe": {
                return (int)this.axeSlot.getValue();
            }
            case "shovel": {
                return (int)this.shovelSlot.getValue();
            }
            default: {
                return 0;
            }
        }
    }
    
    public static float getMaterial(final ItemStack item) {
        if (item.getItem() instanceof ItemTool) {
            return (float)(((ItemTool)item.getItem()).getToolMaterial().getHarvestLevel() + EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, item) * 0.75);
        }
        return 0.0f;
    }
    
    public void getBestBlock() {
        for (int i = 9; i < 45; ++i) {
            if (InvManager.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack() && this.canInteract()) {
                final ItemStack stack = InvManager.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (stack.getItem() instanceof ItemBlock && ((ItemBlock)stack.getItem()).block.isFullBlock() && this.isBestBlock(stack, i) && this.getHotbarID((int)this.blockSlot.getValue()) != i) {
                    this.numberClick(i, (int)this.blockSlot.getValue() - 1);
                }
            }
        }
    }
    
    public boolean isBestBlock(final ItemStack stack, final int slot) {
        if (!(stack.getItem() instanceof ItemBlock) || !((ItemBlock)stack.getItem()).block.isFullBlock()) {
            return false;
        }
        for (int i = 9; i < 45; ++i) {
            if (InvManager.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                final ItemStack is = InvManager.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (is.getItem() instanceof ItemBlock && ((ItemBlock)is.getItem()).block.isFullBlock() && is.stackSize >= stack.stackSize && slot != this.getHotbarID((int)this.blockSlot.getValue()) && slot != i) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public int getHotbarID(final int hotbarNumber) {
        return hotbarNumber + 35;
    }
    
    public static int getBowDamage(final ItemStack bow) {
        return EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, bow) + EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, bow) * 2;
    }
    
    public static float getToolDamage(final ItemStack tool) {
        float damage = 0.0f;
        if (tool != null && (tool.getItem() instanceof ItemTool || tool.getItem() instanceof ItemSword)) {
            if (tool.getItem() instanceof ItemSword) {
                damage += 4.0f;
                ++damage;
            }
            else if (tool.getItem() instanceof ItemAxe) {
                damage += 3.0f;
            }
            else if (tool.getItem() instanceof ItemPickaxe) {
                damage += 2.0f;
            }
            else if (tool.getItem() instanceof ItemSpade) {
                ++damage;
            }
            damage += ((tool.getItem() instanceof ItemTool) ? ((ItemTool)tool.getItem()).getToolMaterial().getDamageVsEntity() : ((ItemSword)tool.getItem()).getDamageVsEntity());
            damage += (float)(1.25 * EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, tool));
            damage += (float)(EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, tool) * 0.5);
        }
        return damage;
    }
    
    private boolean canInteract() {
        return this.delayTimer.hasTimePassed((long)this.delay.getValue());
    }
    
    private static void save() {
        try {
            final DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream("config/OringoClient/InventoryManager.cfg"));
            dataOutputStream.writeInt(InvManager.dropCustom.size());
            for (final String s : InvManager.dropCustom) {
                dataOutputStream.writeUTF(s);
            }
            dataOutputStream.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void shiftClick(final int slot) {
        this.delayTimer.reset();
        KillAura.DISABLE.reset();
        InvManager.mc.playerController.windowClick(InvManager.mc.thePlayer.inventoryContainer.windowId, slot, 0, 1, (EntityPlayer)InvManager.mc.thePlayer);
    }
    
    public void numberClick(final int slot, final int button) {
        this.delayTimer.reset();
        KillAura.DISABLE.reset();
        InvManager.mc.playerController.windowClick(InvManager.mc.thePlayer.inventoryContainer.windowId, slot, button, 2, (EntityPlayer)InvManager.mc.thePlayer);
    }
    
    public void drop(final int slot) {
        this.delayTimer.reset();
        KillAura.DISABLE.reset();
        InvManager.mc.playerController.windowClick(InvManager.mc.thePlayer.inventoryContainer.windowId, slot, 1, 4, (EntityPlayer)InvManager.mc.thePlayer);
    }
    
    static {
        InvManager.dropCustom = new ArrayList<String>();
    }
}
