//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.impl.skyblock;

import me.oringo.oringoclient.qolfeatures.module.*;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.*;
import net.minecraft.entity.*;
import me.oringo.oringoclient.qolfeatures.module.settings.*;
import me.oringo.oringoclient.events.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import me.oringo.oringoclient.utils.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraftforge.event.world.*;
import java.util.*;

public class Aimbot extends Module
{
    public NumberSetting yOffset;
    private static List<Entity> killed;
    public static boolean attack;
    
    public Aimbot() {
        super("Blood aimbot", 0, Category.SKYBLOCK);
        this.addSetting(this.yOffset = new NumberSetting("Y offset", 0.0, -2.0, 2.0, 0.1));
    }
    
    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onMove(final MotionUpdateEvent.Pre event) {
        if (!this.isToggled() || !SkyblockUtils.inDungeon || !SkyblockUtils.inBlood || Aimbot.mc.theWorld == null) {
            return;
        }
        for (final Entity entity : Aimbot.mc.theWorld.playerEntities) {
            if (entity.getDistanceToEntity((Entity)Aimbot.mc.thePlayer) < 20.0f && entity instanceof EntityPlayer && !entity.isDead && !Aimbot.killed.contains(entity)) {
                for (final String name : new String[] { "Revoker", "Psycho", "Reaper", "Cannibal", "Mute", "Ooze", "Putrid", "Freak", "Leech", "Tear", "Parasite", "Flamer", "Skull", "Mr. Dead", "Vader", "Frost", "Walker", "WanderingSoul" }) {
                    if (entity.getName().contains(name)) {
                        Aimbot.attack = true;
                        final Rotation angles = RotationUtils.getRotations(new Vec3(entity.posX, entity.posY + this.yOffset.getValue(), entity.posZ));
                        event.yaw = angles.getYaw();
                        event.pitch = angles.getPitch();
                        Aimbot.killed.add(entity);
                        break;
                    }
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onMovePost(final MotionUpdateEvent.Post event) {
        if (!Aimbot.attack) {
            return;
        }
        Aimbot.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C0APacketAnimation());
        Aimbot.attack = false;
    }
    
    @SubscribeEvent
    public void onWorldLoad(final WorldEvent.Load event) {
        Aimbot.killed.clear();
    }
    
    static {
        Aimbot.killed = new ArrayList<Entity>();
    }
}
