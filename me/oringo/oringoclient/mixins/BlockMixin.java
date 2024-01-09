//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.mixins;

import net.minecraft.block.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.entity.*;
import me.oringo.oringoclient.events.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.spongepowered.asm.mixin.*;

@Mixin(value = { Block.class }, priority = 1)
public abstract class BlockMixin
{
    @Shadow
    public abstract void setBlockBounds(final float p0, final float p1, final float p2, final float p3, final float p4, final float p5);
    
    @Shadow
    public abstract AxisAlignedBB getCollisionBoundingBox(final World p0, final BlockPos p1, final IBlockState p2);
    
    @Overwrite
    public void addCollisionBoxesToList(final World worldIn, final BlockPos pos, final IBlockState state, final AxisAlignedBB mask, final List<AxisAlignedBB> list, final Entity collidingEntity) {
        final BlockBoundsEvent event = new BlockBoundsEvent((Block)this, this.getCollisionBoundingBox(worldIn, pos, state), pos, collidingEntity);
        if (MinecraftForge.EVENT_BUS.post((Event)event)) {
            return;
        }
        if (event.aabb != null && mask.intersectsWith(event.aabb)) {
            list.add(event.aabb);
        }
    }
}
