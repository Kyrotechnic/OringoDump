//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.events;

import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;

@Cancelable
public class BlockBoundsEvent extends Event
{
    public AxisAlignedBB aabb;
    public Block block;
    public BlockPos pos;
    public Entity collidingEntity;
    
    public BlockBoundsEvent(final Block block, final AxisAlignedBB aabb, final BlockPos pos, final Entity collidingEntity) {
        this.aabb = aabb;
        this.block = block;
        this.pos = pos;
        this.collidingEntity = collidingEntity;
    }
}
