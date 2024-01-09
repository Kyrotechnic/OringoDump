//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.commands.impl;

import me.oringo.oringoclient.commands.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import me.oringo.oringoclient.ui.notifications.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

public class FireworkCommand extends Command
{
    public FireworkCommand() {
        super("firework", new String[0]);
    }
    
    public void execute(final String[] args) throws Exception {
        if (args.length == 2) {
            final ItemStack item = new ItemStack(Items.fireworks);
            item.stackSize = 64;
            item.setStackDisplayName("crash");
            final NBTTagList value = new NBTTagList();
            final NBTTagCompound nbtTagCompound = item.serializeNBT();
            final NBTTagCompound display = nbtTagCompound.getCompoundTag("tag").getCompoundTag("Fireworks");
            final NBTTagList explosions = new NBTTagList();
            final NBTTagCompound exp1 = new NBTTagCompound();
            exp1.setTag("Type", (NBTBase)new NBTTagByte((byte)1));
            exp1.setTag("Flicker", (NBTBase)new NBTTagByte((byte)1));
            exp1.setTag("Trail", (NBTBase)new NBTTagByte((byte)3));
            int[] colors = new int[Integer.parseInt(args[1])];
            for (int i = 0; i < Integer.parseInt(args[1]); ++i) {
                colors[i] = 261799 + i;
            }
            exp1.setIntArray("Colors", colors);
            colors = new int[100];
            for (int i = 0; i < 100; ++i) {
                colors[i] = 11250603 + i;
            }
            exp1.setIntArray("FadeColors", colors);
            for (int x = 0; x < Integer.parseInt(args[0]); ++x) {
                explosions.appendTag((NBTBase)exp1);
            }
            display.setTag("Explosions", (NBTBase)explosions);
            nbtTagCompound.getCompoundTag("tag").setTag("Fireworks", (NBTBase)display);
            Notifications.showNotification("Oringo Client", "NBT Size: " + nbtTagCompound.toString().length(), 2000);
            item.deserializeNBT(nbtTagCompound);
            FireworkCommand.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C10PacketCreativeInventoryAction(36, item));
        }
        else {
            Notifications.showNotification("Oringo Client", "/firework explosions colors", 1000);
        }
    }
    
    public String getDescription() {
        return "Gives you a crash firework. You need to have creative";
    }
}
