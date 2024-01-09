//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.impl.skyblock;

import me.oringo.oringoclient.qolfeatures.module.*;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.*;
import me.oringo.oringoclient.qolfeatures.module.settings.*;
import me.oringo.oringoclient.events.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.item.*;
import net.minecraft.network.*;
import java.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import me.oringo.oringoclient.mixins.*;
import net.minecraft.network.play.client.*;
import me.oringo.oringoclient.utils.*;
import net.minecraft.item.*;

public class BlazeSwapper extends Module
{
    public static final BooleanSetting ghost;
    private static final String[] ashNames;
    private static final String[] spiritNames;
    private final MilliTimer delay;
    
    public BlazeSwapper() {
        super("Blaze Swapper", Category.SKYBLOCK);
        this.delay = new MilliTimer();
        this.addSettings(BlazeSwapper.ghost);
    }
    
    @SubscribeEvent
    public void onAttack(final PreAttackEvent event) {
        if (this.isToggled() && ((event.entity instanceof EntitySkeleton && ((EntitySkeleton)event.entity).getSkeletonType() == 1) || event.entity instanceof EntityBlaze || event.entity instanceof EntityPigZombie)) {
            final List<EntityArmorStand> armorStands = (List<EntityArmorStand>)BlazeSwapper.mc.theWorld.getEntitiesWithinAABB((Class)EntityArmorStand.class, event.entity.getEntityBoundingBox().expand(0.1, 2.0, 0.1), entity -> {
                final String text = entity.getDisplayName().getUnformattedText().toLowerCase();
                return text.contains("spirit") || text.contains("ashen") || text.contains("auric") || text.contains("crystal");
            });
            if (!armorStands.isEmpty()) {
                final EntityArmorStand armorStand = armorStands.get(0);
                final Type type = this.getType(armorStand.getDisplayName().getUnformattedText());
                if (type != Type.NONE) {
                    final int slot = getSlot(type);
                    if (slot != -1) {
                        swap(slot);
                        if (this.delay.hasTimePassed(500L) && BlazeSwapper.mc.thePlayer.inventory.getStackInSlot(slot).getItem() instanceof ItemSword && !((ItemSword)BlazeSwapper.mc.thePlayer.inventory.getStackInSlot(slot).getItem()).getToolMaterialName().equals(type.material)) {
                            BlazeSwapper.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C08PacketPlayerBlockPlacement(BlazeSwapper.mc.thePlayer.inventory.getStackInSlot(slot)));
                            this.delay.reset();
                        }
                    }
                }
            }
        }
    }
    
    private Type getType(final String name) {
        for (final Type type : Type.values()) {
            if (name.toLowerCase().contains(type.toString().toLowerCase())) {
                return type;
            }
        }
        return Type.NONE;
    }
    
    private static void swap(final int slot) {
        if (BlazeSwapper.ghost.isEnabled()) {
            if (((PlayerControllerAccessor)BlazeSwapper.mc.playerController).getCurrentPlayerItem() != slot) {
                PacketUtils.sendPacketNoEvent((Packet<?>)new C09PacketHeldItemChange(slot));
                ((PlayerControllerAccessor)BlazeSwapper.mc.playerController).setCurrentPlayerItem(-1);
            }
        }
        else {
            PlayerUtils.swapToSlot(slot);
        }
    }
    
    private static int getSlot(final Type type) {
        if (type == Type.NONE) {
            return -1;
        }
        boolean flag;
        final String[] array;
        final int length;
        int i = 0;
        String name;
        return PlayerUtils.getHotbar(stack -> {
            flag = false;
            array = ((type == Type.ASHEN || type == Type.AURIC) ? BlazeSwapper.ashNames : BlazeSwapper.spiritNames);
            length = array.length;
            while (i < length) {
                name = array[i];
                if (stack.getDisplayName().contains(name)) {
                    flag = true;
                    break;
                }
                else {
                    ++i;
                }
            }
            return stack.getItem() instanceof ItemSword && flag;
        });
    }
    
    static {
        ghost = new BooleanSetting("Ghost", false);
        ashNames = new String[] { "Firedust", "Kindlebane", "Pyrochaos" };
        spiritNames = new String[] { "Mawdredge", "Twilight", "Deathripper" };
    }
    
    public enum Type
    {
        ASHEN("STONE"), 
        AURIC("GOLD"), 
        CRYSTAL("EMERALD"), 
        SPIRIT("IRON"), 
        NONE("NONE");
        
        public String material;
        
        private Type(final String material) {
            this.material = material;
        }
    }
}
