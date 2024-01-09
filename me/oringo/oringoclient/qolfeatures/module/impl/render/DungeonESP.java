//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.impl.render;

import me.oringo.oringoclient.qolfeatures.module.*;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.*;
import java.awt.*;
import me.oringo.oringoclient.qolfeatures.module.settings.*;
import net.minecraft.entity.*;
import java.util.stream.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.item.*;
import net.minecraft.client.entity.*;
import net.minecraft.item.*;
import java.util.*;
import me.oringo.oringoclient.events.*;
import net.minecraftforge.client.event.*;
import me.oringo.oringoclient.utils.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mojang.realmsclient.gui.*;
import net.minecraftforge.event.world.*;

public class DungeonESP extends Module
{
    public BooleanSetting bat;
    public BooleanSetting starred;
    public BooleanSetting enderman;
    public BooleanSetting miniboss;
    public BooleanSetting bowWarning;
    public ModeSetting mode;
    public NumberSetting opacity;
    private static final Color starredColor;
    private static final Color batColor;
    private static final Color saColor;
    private static final Color laColor;
    private static final Color aaColor;
    private HashMap<Entity, Color> starredMobs;
    private Entity lastRendered;
    
    public DungeonESP() {
        super("Dungeon ESP", 0, Category.RENDER);
        this.bat = new BooleanSetting("Bat ESP", true);
        this.starred = new BooleanSetting("Starred ESP", true);
        this.enderman = new BooleanSetting("Show endermen", true);
        this.miniboss = new BooleanSetting("Miniboss ESP", true);
        this.bowWarning = new BooleanSetting("Bow warning", false);
        this.mode = new ModeSetting("Mode", "2D", new String[] { "Outline", "2D", "Chams", "Box", "Tracers" });
        this.opacity = new NumberSetting("Opacity", 255.0, 0.0, 255.0, 1.0) {
            @Override
            public boolean isHidden() {
                return !DungeonESP.this.mode.is("Chams");
            }
        };
        this.starredMobs = new HashMap<Entity, Color>();
        this.addSettings(this.mode, this.opacity, this.bat, this.starred, this.enderman, this.miniboss);
    }
    
    @SubscribeEvent
    public void onUpdate(final MotionUpdateEvent.Pre event) {
        if (DungeonESP.mc.thePlayer.ticksExisted % 20 == 0 && SkyblockUtils.inDungeon) {
            this.starredMobs.clear();
            for (final Entity entity2 : (List)DungeonESP.mc.theWorld.loadedEntityList.stream().filter(entity -> entity instanceof EntityLivingBase).collect(Collectors.toList())) {
                if (this.starredMobs.containsKey(entity2)) {
                    continue;
                }
                if (entity2 instanceof EntityBat && !entity2.isInvisible() && this.bat.isEnabled()) {
                    this.starredMobs.put(entity2, DungeonESP.batColor);
                }
                else {
                    if (this.starred.isEnabled()) {
                        if (entity2 instanceof EntityEnderman && entity2.getName().equals("Dinnerbone")) {
                            entity2.setInvisible(false);
                            if (this.enderman.isEnabled()) {
                                this.starredMobs.put(entity2, DungeonESP.starredColor);
                                continue;
                            }
                            continue;
                        }
                        else if (entity2 instanceof EntityArmorStand && entity2.getName().contains("\u272f")) {
                            final List<Entity> possibleMobs = (List<Entity>)DungeonESP.mc.theWorld.getEntitiesWithinAABBExcludingEntity(entity2, entity2.getEntityBoundingBox().expand(0.1, 3.0, 0.1));
                            if (!possibleMobs.isEmpty() && !SkyblockUtils.isMiniboss(possibleMobs.get(0)) && !this.starredMobs.containsKey(possibleMobs.get(0))) {
                                this.starredMobs.put(possibleMobs.get(0), DungeonESP.starredColor);
                                continue;
                            }
                            continue;
                        }
                    }
                    if (!this.miniboss.isEnabled() || !(entity2 instanceof EntityOtherPlayerMP) || !SkyblockUtils.isMiniboss(entity2)) {
                        continue;
                    }
                    final String getName = entity2.getName();
                    switch (getName) {
                        case "Lost Adventurer": {
                            this.starredMobs.put(entity2, DungeonESP.laColor);
                            break;
                        }
                        case "Shadow Assassin": {
                            entity2.setInvisible(false);
                            this.starredMobs.put(entity2, DungeonESP.saColor);
                            break;
                        }
                        case "Diamond Guy": {
                            this.starredMobs.put(entity2, DungeonESP.aaColor);
                            break;
                        }
                    }
                    if (!this.bowWarning.isEnabled() || ((EntityOtherPlayerMP)entity2).getHeldItem() == null || !(((EntityOtherPlayerMP)entity2).getHeldItem().getItem() instanceof ItemBow)) {
                        continue;
                    }
                    this.drawBowWarning();
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onRenderWorld(final RenderWorldLastEvent event) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokevirtual   me/oringo/oringoclient/qolfeatures/module/impl/render/DungeonESP.isToggled:()Z
        //     4: ifeq            49
        //     7: getstatic       me/oringo/oringoclient/utils/SkyblockUtils.inDungeon:Z
        //    10: ifeq            49
        //    13: aload_0         /* this */
        //    14: getfield        me/oringo/oringoclient/qolfeatures/module/impl/render/DungeonESP.mode:Lme/oringo/oringoclient/qolfeatures/module/settings/impl/ModeSetting;
        //    17: ldc             "2D"
        //    19: invokevirtual   me/oringo/oringoclient/qolfeatures/module/settings/impl/ModeSetting.is:(Ljava/lang/String;)Z
        //    22: ifne            50
        //    25: aload_0         /* this */
        //    26: getfield        me/oringo/oringoclient/qolfeatures/module/impl/render/DungeonESP.mode:Lme/oringo/oringoclient/qolfeatures/module/settings/impl/ModeSetting;
        //    29: ldc             "Box"
        //    31: invokevirtual   me/oringo/oringoclient/qolfeatures/module/settings/impl/ModeSetting.is:(Ljava/lang/String;)Z
        //    34: ifne            50
        //    37: aload_0         /* this */
        //    38: getfield        me/oringo/oringoclient/qolfeatures/module/impl/render/DungeonESP.mode:Lme/oringo/oringoclient/qolfeatures/module/settings/impl/ModeSetting;
        //    41: ldc             "Tracers"
        //    43: invokevirtual   me/oringo/oringoclient/qolfeatures/module/settings/impl/ModeSetting.is:(Ljava/lang/String;)Z
        //    46: ifne            50
        //    49: return         
        //    50: aload_0         /* this */
        //    51: getfield        me/oringo/oringoclient/qolfeatures/module/impl/render/DungeonESP.starredMobs:Ljava/util/HashMap;
        //    54: aload_0         /* this */
        //    55: aload_1         /* event */
        //    56: invokedynamic   BootstrapMethod #1, accept:(Lme/oringo/oringoclient/qolfeatures/module/impl/render/DungeonESP;Lnet/minecraftforge/client/event/RenderWorldLastEvent;)Ljava/util/function/BiConsumer;
        //    61: invokevirtual   java/util/HashMap.forEach:(Ljava/util/function/BiConsumer;)V
        //    64: return         
        //    StackMapTable: 00 02 31 00
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException: Cannot invoke "com.strobel.assembler.metadata.TypeReference.getSimpleType()" because the return value of "com.strobel.decompiler.ast.Variable.getType()" is null
        //     at com.strobel.decompiler.languages.java.ast.NameVariables.generateNameForVariable(NameVariables.java:264)
        //     at com.strobel.decompiler.languages.java.ast.NameVariables.assignNamesToVariables(NameVariables.java:198)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:276)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at com.strobel.decompiler.Decompiler.decompile(Decompiler.java:70)
        //     at org.ugp.mc.deobfuscator.Deobfuscator3000.decompile(Deobfuscator3000.java:538)
        //     at org.ugp.mc.deobfuscator.Deobfuscator3000.decompileAndDeobfuscate(Deobfuscator3000.java:552)
        //     at org.ugp.mc.deobfuscator.Deobfuscator3000.processMod(Deobfuscator3000.java:510)
        //     at org.ugp.mc.deobfuscator.Deobfuscator3000.lambda$21(Deobfuscator3000.java:329)
        //     at java.base/java.lang.Thread.run(Thread.java:833)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @SubscribeEvent
    public void onEntityRender(final RenderLayersEvent event) {
        if (!this.isToggled() || !SkyblockUtils.inDungeon || !this.mode.is("Outline")) {
            return;
        }
        if (this.starredMobs.containsKey(event.entity)) {
            OutlineUtils.outlineESP(event, this.starredMobs.get(event.entity));
        }
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onRenderPre(final RenderLivingEvent.Pre<EntityLivingBase> event) {
        if (!this.isToggled() || !SkyblockUtils.inDungeon || !this.mode.is("Chams")) {
            return;
        }
        if (this.starredMobs.containsKey(event.entity)) {
            MobRenderUtils.setColor(RenderUtils.applyOpacity(this.starredMobs.get(event.entity), (int)this.opacity.getValue()));
            RenderUtils.enableChams();
            this.lastRendered = (Entity)event.entity;
        }
    }
    
    @SubscribeEvent
    public void onRenderPost(final RenderLivingEvent.Specials.Pre<EntityLivingBase> event) {
        if (this.lastRendered == event.entity) {
            this.lastRendered = null;
            RenderUtils.disableChams();
            MobRenderUtils.unsetColor();
        }
    }
    
    private void drawBowWarning() {
        DungeonESP.mc.ingameGUI.displayTitle((String)null, ChatFormatting.DARK_RED + "Bow", 0, 20, 0);
    }
    
    @SubscribeEvent
    public void onWorldLoad(final WorldEvent.Load event) {
        this.starredMobs.clear();
    }
    
    static {
        starredColor = new Color(245, 81, 66);
        batColor = new Color(139, 69, 19);
        saColor = new Color(75, 0, 130);
        laColor = new Color(34, 139, 34);
        aaColor = new Color(97, 226, 255);
    }
}
