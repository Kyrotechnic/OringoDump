//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.impl.macro;

import me.oringo.oringoclient.qolfeatures.module.*;
import net.minecraft.entity.player.*;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.*;
import me.oringo.oringoclient.qolfeatures.module.settings.*;
import me.oringo.oringoclient.ui.notifications.*;
import net.minecraft.client.*;
import net.minecraft.entity.*;
import me.oringo.oringoclient.*;
import net.minecraft.block.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.client.settings.*;
import me.oringo.oringoclient.qolfeatures.*;
import net.minecraft.util.*;
import java.util.*;
import me.oringo.oringoclient.utils.*;

public class AutoSumoBot extends Module
{
    public static ArrayList<String> playersChecked;
    public static Thread thread;
    public static EntityPlayer target;
    private static int ticksBack;
    public StringSetting webhook;
    public BooleanSetting skipNoLoses;
    
    public AutoSumoBot() {
        super("Auto Sumo", 0, Category.OTHER);
        this.webhook = new StringSetting("Webhook");
        this.skipNoLoses = new BooleanSetting("Skip no loses", true);
        this.addSettings(this.webhook);
    }
    
    @Override
    public void onEnable() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: ifnull          29
        //     6: getstatic       me/oringo/oringoclient/qolfeatures/module/impl/macro/AutoSumoBot.thread:Ljava/lang/Thread;
        //     9: invokevirtual   java/lang/Thread.stop:()V
        //    12: aconst_null    
        //    13: putstatic       me/oringo/oringoclient/qolfeatures/module/impl/macro/AutoSumoBot.thread:Ljava/lang/Thread;
        //    16: ldc             "Oringo Client"
        //    18: ldc             "AutoSumo has been disabled!"
        //    20: sipush          1000
        //    23: invokestatic    me/oringo/oringoclient/ui/notifications/Notifications.showNotification:(Ljava/lang/String;Ljava/lang/String;I)V
        //    26: goto            87
        //    29: aload_0         /* this */
        //    30: getfield        me/oringo/oringoclient/qolfeatures/module/impl/macro/AutoSumoBot.webhook:Lme/oringo/oringoclient/qolfeatures/module/settings/impl/StringSetting;
        //    33: invokevirtual   me/oringo/oringoclient/qolfeatures/module/settings/impl/StringSetting.getValue:()Ljava/lang/String;
        //    36: invokevirtual   java/lang/String.length:()I
        //    39: iconst_5       
        //    40: if_icmpge       58
        //    43: ldc             "Oringo Client"
        //    45: ldc             "You need to set a webhook"
        //    47: sipush          2500
        //    50: invokestatic    me/oringo/oringoclient/ui/notifications/Notifications.showNotification:(Ljava/lang/String;Ljava/lang/String;I)V
        //    53: aload_0         /* this */
        //    54: invokevirtual   me/oringo/oringoclient/qolfeatures/module/impl/macro/AutoSumoBot.toggle:()V
        //    57: return         
        //    58: new             Ljava/lang/Thread;
        //    61: dup            
        //    62: invokedynamic   BootstrapMethod #0, run:()Ljava/lang/Runnable;
        //    67: invokespecial   java/lang/Thread.<init>:(Ljava/lang/Runnable;)V
        //    70: dup            
        //    71: putstatic       me/oringo/oringoclient/qolfeatures/module/impl/macro/AutoSumoBot.thread:Ljava/lang/Thread;
        //    74: invokevirtual   java/lang/Thread.start:()V
        //    77: ldc             "Oringo Client"
        //    79: ldc             "AutoSumo has been enabled!"
        //    81: sipush          1000
        //    84: invokestatic    me/oringo/oringoclient/ui/notifications/Notifications.showNotification:(Ljava/lang/String;Ljava/lang/String;I)V
        //    87: return         
        //    StackMapTable: 00 03 1D 1C 1C
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Unsupported node type: com.strobel.decompiler.ast.Lambda
        //     at com.strobel.decompiler.ast.Error.unsupportedNode(Error.java:32)
        //     at com.strobel.decompiler.ast.GotoRemoval.exit(GotoRemoval.java:612)
        //     at com.strobel.decompiler.ast.GotoRemoval.exit(GotoRemoval.java:586)
        //     at com.strobel.decompiler.ast.GotoRemoval.trySimplifyGoto(GotoRemoval.java:248)
        //     at com.strobel.decompiler.ast.GotoRemoval.removeGotosCore(GotoRemoval.java:66)
        //     at com.strobel.decompiler.ast.GotoRemoval.removeGotos(GotoRemoval.java:53)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:276)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:42)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:214)
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
    
    @Override
    public void onDisable() {
        if (AutoSumoBot.thread != null) {
            AutoSumoBot.thread.stop();
            AutoSumoBot.thread = null;
            Notifications.showNotification("Oringo Client", "AutoSumo has been disabled!", 1000);
        }
    }
    
    public static boolean targetingPlayer() {
        return Minecraft.getMinecraft().objectMouseOver != null && Minecraft.getMinecraft().objectMouseOver.typeOfHit.equals((Object)MovingObjectPosition.MovingObjectType.ENTITY);
    }
    
    public static int smartStrafeOrRandomV2(final Entity target) {
        final Minecraft mc = Minecraft.getMinecraft();
        if (target != null) {
            int i = 0;
            while (i < 100) {
                final int angle = 60;
                if (checkBlocksBelow(mc.thePlayer.posX - Math.sin(Math.toRadians(target.rotationYaw - angle) * 0.13 * i), mc.thePlayer.posY, mc.thePlayer.posZ + Math.cos(Math.toRadians(target.rotationYaw - angle)) * 0.13 * i)) {
                    if (checkBlocksBelow(mc.thePlayer.posX - Math.sin(Math.toRadians(target.rotationYaw + angle) * 0.13 * i), mc.thePlayer.posY, mc.thePlayer.posZ + Math.cos(Math.toRadians(target.rotationYaw + angle)) * 0.13 * i)) {
                        return -1;
                    }
                    OringoClient.sendMessageWithPrefix("Smart: A");
                    return 0;
                }
                else {
                    if (checkBlocksBelow(mc.thePlayer.posX - Math.sin(Math.toRadians(target.rotationYaw + angle) * 0.13 * i), mc.thePlayer.posY, mc.thePlayer.posZ + Math.cos(Math.toRadians(target.rotationYaw + angle)) * 0.13 * i)) {
                        OringoClient.sendMessageWithPrefix("Smart: D");
                        return 1;
                    }
                    ++i;
                }
            }
            if (checkBlocksBelow(target.posX - Math.sin(Math.toRadians(mc.thePlayer.rotationYaw) * 3.0), target.posY, target.posZ + Math.cos(Math.toRadians(mc.thePlayer.rotationYaw)) * 3.0)) {
                OringoClient.sendMessageWithPrefix("Smart: No strafe");
                return -1;
            }
        }
        return new Random().nextInt(2);
    }
    
    private static boolean checkBlocksBelow(final double posX, final double posY, final double posZ) {
        final WorldClient theWorld = Minecraft.getMinecraft().theWorld;
        BlockPos bp = new BlockPos(posX, posY, posZ);
        for (int i = 0; i < 3; ++i) {
            bp = bp.down();
            if (!(theWorld.getBlockState(bp).getBlock() instanceof BlockAir)) {
                return false;
            }
        }
        return true;
    }
    
    static {
        AutoSumoBot.playersChecked = new ArrayList<String>();
        AutoSumoBot.thread = null;
        AutoSumoBot.target = null;
        AutoSumoBot.ticksBack = -1;
    }
}
