//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.events;

import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;

@Cancelable
public class BlockChangeEvent extends Event
{
    public BlockPos pos;
    public IBlockState state;
    
    public BlockChangeEvent(final BlockPos pos, final IBlockState state) {
        this.pos = pos;
        this.state = state;
    }
}
