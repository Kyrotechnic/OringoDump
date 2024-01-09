//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.impl.render;

import me.oringo.oringoclient.qolfeatures.module.*;
import java.awt.*;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.*;
import me.oringo.oringoclient.qolfeatures.module.settings.*;
import net.minecraftforge.client.event.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;
import me.oringo.oringoclient.utils.*;
import net.minecraftforge.fml.common.eventhandler.*;
import java.util.*;

public class CustomESP extends Module
{
    public static Map<String, Color> names;
    public ModeSetting mode;
    
    public CustomESP() {
        super("Custom ESP", Category.RENDER);
        this.mode = new ModeSetting("Mode", "2D", new String[] { "2D", "Box", "Tracers" });
        this.addSettings(this.mode);
    }
    
    @SubscribeEvent
    public void onRender(final RenderWorldLastEvent event) {
        if (!this.isToggled()) {
            return;
        }
        for (final Entity entity : CustomESP.mc.theWorld.getEntities((Class)EntityArmorStand.class, entity -> entity.getDistanceSqToEntity((Entity)CustomESP.mc.thePlayer) < 10000.0)) {
            for (final Map.Entry<String, Color> entry : CustomESP.names.entrySet()) {
                if (entity.getDisplayName().getUnformattedText().toLowerCase().contains(entry.getKey())) {
                    final List<Entity> entities = (List<Entity>)CustomESP.mc.theWorld.getEntitiesWithinAABBExcludingEntity(entity, entity.getEntityBoundingBox().expand(0.0, 2.0, 0.0));
                    if (!entities.isEmpty()) {
                        final Color color = entry.getValue();
                        final Entity toRender = entities.get(0);
                        final String selected = this.mode.getSelected();
                        switch (selected) {
                            case "2D": {
                                RenderUtils.draw2D(toRender, event.partialTicks, 1.0f, color);
                                break;
                            }
                            case "Box": {
                                RenderUtils.entityESPBox(toRender, event.partialTicks, color);
                                break;
                            }
                            case "Tracers": {
                                RenderUtils.tracerLine(toRender, event.partialTicks, 1.0f, color);
                                break;
                            }
                        }
                        break;
                    }
                    break;
                }
            }
        }
    }
    
    static {
        CustomESP.names = new HashMap<String, Color>();
    }
}
