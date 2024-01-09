//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.impl.render;

import me.oringo.oringoclient.qolfeatures.module.*;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.*;
import me.oringo.oringoclient.qolfeatures.module.settings.*;
import net.minecraftforge.client.event.*;
import java.util.stream.*;
import net.minecraft.tileentity.*;
import me.oringo.oringoclient.*;
import me.oringo.oringoclient.utils.*;
import java.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import me.oringo.oringoclient.events.*;

public class ChestESP extends Module
{
    public BooleanSetting tracer;
    private boolean hasRendered;
    
    public ChestESP() {
        super("ChestESP", Category.RENDER);
        this.tracer = new BooleanSetting("Tracer", true);
        this.addSettings(this.tracer);
    }
    
    @SubscribeEvent
    public void onRender(final RenderWorldLastEvent event) {
        if (!this.isToggled() || !this.tracer.isEnabled()) {
            return;
        }
        for (final TileEntity tileEntityChest : (List)ChestESP.mc.theWorld.loadedTileEntityList.stream().filter(tileEntity -> tileEntity instanceof TileEntityChest).collect(Collectors.toList())) {
            RenderUtils.tracerLine(tileEntityChest.getPos().getX() + 0.5, tileEntityChest.getPos().getY() + 0.5, tileEntityChest.getPos().getZ() + 0.5, OringoClient.clickGui.getColor());
        }
    }
    
    @SubscribeEvent
    public void onRenderChest(final RenderChestEvent event) {
        if (this.isToggled()) {
            if (event.isPre() && event.getChest() == ChestESP.mc.theWorld.getTileEntity(event.getChest().getPos())) {
                RenderUtils.enableChams();
                this.hasRendered = true;
            }
            else if (this.hasRendered) {
                RenderUtils.disableChams();
                this.hasRendered = false;
            }
        }
    }
}
