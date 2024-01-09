//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient;

import net.minecraftforge.fml.common.*;
import net.minecraft.client.*;
import java.util.logging.*;
import java.util.concurrent.*;
import me.oringo.oringoclient.qolfeatures.module.*;
import me.oringo.oringoclient.qolfeatures.module.impl.macro.*;
import me.oringo.oringoclient.qolfeatures.module.impl.movement.*;
import me.oringo.oringoclient.qolfeatures.module.impl.combat.*;
import me.oringo.oringoclient.qolfeatures.module.impl.player.*;
import me.oringo.oringoclient.qolfeatures.module.impl.render.*;
import me.oringo.oringoclient.qolfeatures.module.impl.skyblock.*;
import me.oringo.oringoclient.qolfeatures.module.impl.other.*;
import me.oringo.oringoclient.utils.shader.*;
import net.minecraftforge.common.*;
import me.oringo.oringoclient.commands.*;
import me.oringo.oringoclient.ui.notifications.*;
import me.oringo.oringoclient.utils.*;
import me.oringo.oringoclient.config.*;
import me.oringo.oringoclient.ui.hud.impl.*;
import me.oringo.oringoclient.commands.impl.*;
import me.oringo.oringoclient.qolfeatures.*;
import me.oringo.oringoclient.utils.font.*;
import net.minecraftforge.fml.common.event.*;
import java.util.*;
import net.minecraft.client.renderer.*;
import java.lang.reflect.*;
import java.net.*;
import java.security.cert.*;
import java.security.*;
import javax.net.ssl.*;
import java.nio.*;
import javax.imageio.*;
import java.awt.image.*;
import java.io.*;
import net.minecraft.util.*;
import java.nio.file.*;
import net.minecraft.client.renderer.texture.*;

@Mod(modid = "examplemod", dependencies = "before:*", version = "1.7.1")
public class OringoClient
{
    public static final Minecraft mc;
    public static final String KEY = "NIGGER";
    public static final Logger LOGGER;
    public static final CopyOnWriteArrayList<Module> modules;
    public static final Gui clickGui;
    public static final KillAura killAura;
    public static final Velocity velocity;
    public static final Aimbot bloodAimbot;
    public static final Modless modless;
    public static final NoHitDelay noHitDelay;
    public static final NoSlow noSlow;
    public static final Sprint sprint;
    public static final Reach reach;
    public static final AutoSumoBot autoSumo;
    public static final FastBreak fastBreak;
    public static final AOTVReturn aotvReturn;
    public static final NickHider nickHider;
    public static final Animations animations;
    public static final AnimationCreator animationCreator;
    public static final Camera camera;
    public static final MithrilMacro mithrilMacro;
    public static final Derp derp;
    public static final Hitboxes hitboxes;
    public static final NoRotate noRotate;
    public static final Phase phase;
    public static final FreeCam freeCam;
    public static final Giants giants;
    public static final CustomInterfaces interfaces;
    public static final AutoBlock autoBlock;
    public static final Speed speed;
    public static final Test test;
    public static final TargetStrafe targetStrafe;
    public static final GuiMove guiMove;
    public static final DojoHelper dojoHelper;
    public static final PopupAnimation popupAnimation;
    public static final Disabler disabler;
    public static final Scaffold scaffold;
    public static final Flight fly;
    public static final InventoryDisplay inventoryHUDModule;
    public static boolean shouldUpdate;
    public static String[] vers;
    public static ArrayList<BlockPos> stop;
    public static final String MODID = "examplemod";
    public static final String PREFIX = "§bOringoClient §3» §7";
    public static final String VERSION = "1.7.1";
    public static boolean devMode;
    public static ArrayList<Runnable> scheduledTasks;
    public static HashMap<String, ResourceLocation> capes;
    public static HashMap<File, ResourceLocation> capesLoaded;
    private MilliTimer timer;
    private boolean wasOnline;
    
    public OringoClient() {
        this.timer = new MilliTimer();
    }
    
    @Mod.EventHandler
    public void onPre(final FMLPreInitializationEvent event) {
    }
    
    @Mod.EventHandler
    public void onInit(final FMLPreInitializationEvent event) {
        new File(OringoClient.mc.mcDataDir.getPath() + "/config/OringoClient").mkdir();
        new File(OringoClient.mc.mcDataDir.getPath() + "/config/OringoClient/capes").mkdir();
        new File(OringoClient.mc.mcDataDir.getPath() + "/config/OringoClient/configs").mkdir();
        update();
        OringoClient.modless.setToggled(true);
        OringoClient.modules.add(new AntiVoid());
        OringoClient.modules.add(OringoClient.clickGui);
        OringoClient.modules.add(OringoClient.killAura);
        OringoClient.modules.add(OringoClient.noRotate);
        OringoClient.modules.add(OringoClient.velocity);
        OringoClient.modules.add(OringoClient.bloodAimbot);
        OringoClient.modules.add(new AntiNicker());
        OringoClient.modules.add(new TerminalAura());
        OringoClient.modules.add(new ChatBypass());
        OringoClient.modules.add(new TerminatorAura());
        OringoClient.modules.add(new SecretAura());
        OringoClient.modules.add(new DungeonESP());
        OringoClient.modules.add(new SafeWalk());
        OringoClient.modules.add(new RemoveAnnoyingMobs());
        OringoClient.modules.add(new GhostBlocks());
        OringoClient.modules.add(new SumoFences());
        OringoClient.modules.add(OringoClient.dojoHelper);
        OringoClient.modules.add(new CrimsonQOL());
        OringoClient.modules.add(OringoClient.modless);
        OringoClient.modules.add(OringoClient.noHitDelay);
        OringoClient.modules.add(new TntRunPing());
        OringoClient.modules.add(OringoClient.noSlow);
        OringoClient.modules.add(OringoClient.sprint);
        OringoClient.modules.add(OringoClient.reach);
        OringoClient.modules.add(new AutoS1());
        OringoClient.modules.add(new InvManager());
        OringoClient.modules.add(new ChestStealer());
        OringoClient.modules.add(new PlayerESP());
        OringoClient.modules.add(OringoClient.autoSumo);
        OringoClient.modules.add(OringoClient.fastBreak);
        OringoClient.modules.add(OringoClient.nickHider);
        OringoClient.modules.add(new ChinaHat());
        OringoClient.modules.add(OringoClient.aotvReturn);
        OringoClient.modules.add(OringoClient.mithrilMacro);
        final RichPresenceModule richPresence = new RichPresenceModule();
        OringoClient.modules.add(richPresence);
        OringoClient.modules.add(OringoClient.inventoryHUDModule);
        OringoClient.modules.add(new CustomESP());
        OringoClient.modules.add(OringoClient.fly);
        OringoClient.modules.add(new AutoRogueSword());
        OringoClient.modules.add(new GuessTheBuildAFK());
        OringoClient.modules.add(new Snowballs());
        OringoClient.modules.add(new IceFillHelp());
        OringoClient.modules.add(OringoClient.animations);
        OringoClient.modules.add(ServerRotations.getInstance());
        OringoClient.modules.add(TargetHUD.getInstance());
        OringoClient.modules.add(new WTap());
        OringoClient.modules.add(new AutoTool());
        OringoClient.modules.add(OringoClient.camera);
        OringoClient.modules.add(OringoClient.interfaces);
        OringoClient.modules.add(new ServerBeamer());
        OringoClient.modules.add(FastPlace.getInstance());
        OringoClient.modules.add(OringoClient.derp);
        OringoClient.modules.add(new Blink());
        OringoClient.modules.add(OringoClient.freeCam);
        OringoClient.modules.add(OringoClient.hitboxes);
        OringoClient.modules.add(new MurdererFinder());
        OringoClient.modules.add(new ChestESP());
        OringoClient.modules.add(OringoClient.test);
        OringoClient.modules.add(new BoneThrower());
        OringoClient.modules.add(OringoClient.phase);
        OringoClient.modules.add(OringoClient.giants);
        OringoClient.modules.add(new AutoPot());
        OringoClient.modules.add(OringoClient.disabler);
        OringoClient.modules.add(OringoClient.guiMove);
        OringoClient.modules.add(OringoClient.autoBlock);
        OringoClient.modules.add(OringoClient.speed);
        OringoClient.modules.add(new NoFall());
        OringoClient.modules.add(new Step());
        OringoClient.modules.add(OringoClient.popupAnimation);
        OringoClient.modules.add(OringoClient.targetStrafe);
        OringoClient.modules.add(new AntiNukebi());
        OringoClient.modules.add(new NoRender());
        OringoClient.modules.add(new Trial());
        OringoClient.modules.add(new AutoClicker());
        OringoClient.modules.add(new StaffAnalyser());
        OringoClient.modules.add(new ArmorSwap());
        OringoClient.modules.add(OringoClient.scaffold);
        OringoClient.modules.add(new AimAssist());
        OringoClient.modules.add(AntiBot.getAntiBot());
        OringoClient.modules.add(new AutoHeal());
        OringoClient.modules.add(new Nametags());
        OringoClient.modules.add(new BlazeSwapper());
        OringoClient.modules.add(new Timer());
        OringoClient.interfaces.setToggled(true);
        BlurUtils.registerListener();
        for (final Module m : OringoClient.modules) {
            MinecraftForge.EVENT_BUS.register((Object)m);
        }
        CommandHandler.register((Command)new StalkCommand());
        CommandHandler.register((Command)new WardrobeCommand());
        CommandHandler.register((Command)new HelpCommand());
        CommandHandler.register((Command)new ArmorStandsCommand());
        CommandHandler.register((Command)new ChecknameCommand());
        CommandHandler.register((Command)new ClipCommand());
        CommandHandler.register((Command)new ConfigCommand());
        CommandHandler.register((Command)new FireworkCommand());
        CommandHandler.register((Command)new SettingsCommand());
        CommandHandler.register((Command)new SayCommand());
        CommandHandler.register((Command)new TestCommand());
        CommandHandler.register((Command)new CustomESPCommand());
        MinecraftForge.EVENT_BUS.register((Object)new Notifications());
        MinecraftForge.EVENT_BUS.register((Object)this);
        MinecraftForge.EVENT_BUS.register((Object)ServerUtils.instance);
        MinecraftForge.EVENT_BUS.register((Object)new Updater());
        MinecraftForge.EVENT_BUS.register((Object)new AttackQueue());
        MinecraftForge.EVENT_BUS.register((Object)new SkyblockUtils());
        ConfigManager.loadConfig();
        TargetComponent.INSTANCE.setPosition(TargetHUD.getInstance().x.getValue(), TargetHUD.getInstance().y.getValue());
        if (Disabler.first.isEnabled()) {
            OringoClient.disabler.setToggled(true);
            Disabler.first.setEnabled(false);
        }
        if (richPresence.isToggled()) {
            richPresence.onEnable();
        }
        if (new File("OringoDev").exists()) {
            OringoClient.devMode = true;
        }
        if (OringoClient.devMode) {
            CommandHandler.register((Command)new BanCommand());
            MinecraftForge.EVENT_BUS.register((Object)new LoginWithSession());
            OringoClient.modules.add(OringoClient.animationCreator);
        }
        Fonts.bootstrap();
    }
    
    @Mod.EventHandler
    public void onPost(final FMLPostInitializationEvent event) {
        loadCapes();
    }
    
    public static Map<Integer, DestroyBlockProgress> getBlockBreakProgress() {
        try {
            final Field field_72738_e = RenderGlobal.class.getDeclaredField("damagedBlocks");
            field_72738_e.setAccessible(true);
            return (Map<Integer, DestroyBlockProgress>)field_72738_e.get(Minecraft.getMinecraft().renderGlobal);
        }
        catch (Exception exception) {
            return new HashMap<Integer, DestroyBlockProgress>();
        }
    }
    
    public static void handleKeypress(final int key) {
        if (key == 0) {
            return;
        }
        for (final Module m : OringoClient.modules) {
            if (m.getKeycode() == key) {
                if (m.isKeybind()) {
                    continue;
                }
                m.toggle();
                if (OringoClient.clickGui.disableNotifs.isEnabled()) {
                    continue;
                }
                Notifications.showNotification("Oringo Client", m.getName() + (m.isToggled() ? " enabled!" : " disabled!"), 2500);
            }
        }
    }
    
    private static void update() {
        checkForUpdates();
        try {
            OringoClient.vers = new BufferedReader(new InputStreamReader(new URL("http://niger.5v.pl/version").openStream())).readLine().split(" ");
            if (!"1.7.1".equals(OringoClient.vers[0])) {
                OringoClient.shouldUpdate = true;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Couldn't update");
        }
    }
    
    private static void checkForUpdates() {
    }
    
    private static void loadCapes() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: dup            
        //     4: invokespecial   com/google/gson/Gson.<init>:()V
        //     7: new             Ljava/io/InputStreamReader;
        //    10: dup            
        //    11: new             Ljava/net/URL;
        //    14: dup            
        //    15: ldc_w           "http://niger.5v.pl/capes.txt"
        //    18: invokespecial   java/net/URL.<init>:(Ljava/lang/String;)V
        //    21: invokevirtual   java/net/URL.openStream:()Ljava/io/InputStream;
        //    24: invokespecial   java/io/InputStreamReader.<init>:(Ljava/io/InputStream;)V
        //    27: ldc             Ljava/util/HashMap;.class
        //    29: invokevirtual   com/google/gson/Gson.fromJson:(Ljava/io/Reader;Ljava/lang/Class;)Ljava/lang/Object;
        //    32: checkcast       Ljava/util/HashMap;
        //    35: astore_0        /* capeData */
        //    36: getstatic       me/oringo/oringoclient/OringoClient.capes:Ljava/util/HashMap;
        //    39: invokevirtual   java/util/HashMap.clear:()V
        //    42: aload_0         /* capeData */
        //    43: ifnull          87
        //    46: new             Ljava/util/HashMap;
        //    49: dup            
        //    50: invokespecial   java/util/HashMap.<init>:()V
        //    53: astore_1        /* cache */
        //    54: aload_0         /* capeData */
        //    55: aload_1         /* cache */
        //    56: invokedynamic   BootstrapMethod #0, accept:(Ljava/util/HashMap;)Ljava/util/function/BiConsumer;
        //    61: invokevirtual   java/util/HashMap.forEach:(Ljava/util/function/BiConsumer;)V
        //    64: getstatic       me/oringo/oringoclient/OringoClient.devMode:Z
        //    67: ifeq            87
        //    70: getstatic       java/lang/System.out:Ljava/io/PrintStream;
        //    73: new             Lcom/google/gson/Gson;
        //    76: dup            
        //    77: invokespecial   com/google/gson/Gson.<init>:()V
        //    80: aload_0         /* capeData */
        //    81: invokevirtual   com/google/gson/Gson.toJson:(Ljava/lang/Object;)Ljava/lang/String;
        //    84: invokevirtual   java/io/PrintStream.println:(Ljava/lang/String;)V
        //    87: goto            95
        //    90: astore_0        /* e */
        //    91: aload_0         /* e */
        //    92: invokevirtual   java/lang/Exception.printStackTrace:()V
        //    95: return         
        //    StackMapTable: 00 03 FB 00 57 42 07 02 12 04
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                 
        //  -----  -----  -----  -----  ---------------------
        //  0      87     90     95     Ljava/lang/Exception;
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Could not infer any expression.
        //     at com.strobel.decompiler.ast.TypeAnalysis.runInference(TypeAnalysis.java:374)
        //     at com.strobel.decompiler.ast.TypeAnalysis.run(TypeAnalysis.java:96)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:344)
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
    
    private void disableSSLVerification() {
        final TrustManager[] trustAllCerts = { new X509TrustManager() {
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                
                @Override
                public void checkClientTrusted(final X509Certificate[] arg0, final String arg1) throws CertificateException {
                }
                
                @Override
                public void checkServerTrusted(final X509Certificate[] arg0, final String arg1) throws CertificateException {
                }
            } };
        SSLContext sc = null;
        try {
            sc = SSLContext.getInstance("SSL");
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            sc.init(null, trustAllCerts, new SecureRandom());
        }
        catch (KeyManagementException e2) {
            e2.printStackTrace();
        }
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        final HostnameVerifier validHosts = new HostnameVerifier() {
            @Override
            public boolean verify(final String arg0, final SSLSession arg1) {
                return true;
            }
        };
        HttpsURLConnection.setDefaultHostnameVerifier(validHosts);
    }
    
    private ByteBuffer readImageToBuffer(final InputStream imageStream) throws IOException {
        final BufferedImage bufferedimage = ImageIO.read(imageStream);
        final int[] aint = bufferedimage.getRGB(0, 0, bufferedimage.getWidth(), bufferedimage.getHeight(), null, 0, bufferedimage.getWidth());
        final ByteBuffer bytebuffer = ByteBuffer.allocate(4 * aint.length);
        for (final int i : aint) {
            bytebuffer.putInt(i << 8 | (i >> 24 & 0xFF));
        }
        bytebuffer.flip();
        return bytebuffer;
    }
    
    public static void sendMessage(final String message) {
        Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent)new ChatComponentText(message));
    }
    
    public static void sendMessageWithPrefix(final String message) {
        Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent)new ChatComponentText("§bOringoClient §3» §7" + message));
    }
    
    static {
        mc = Minecraft.getMinecraft();
        LOGGER = Logger.getLogger("Oringo Client");
        modules = new CopyOnWriteArrayList<Module>();
        clickGui = new Gui();
        killAura = new KillAura();
        velocity = new Velocity();
        bloodAimbot = new Aimbot();
        modless = new Modless();
        noHitDelay = new NoHitDelay();
        noSlow = new NoSlow();
        sprint = new Sprint();
        reach = new Reach();
        autoSumo = new AutoSumoBot();
        fastBreak = new FastBreak();
        aotvReturn = new AOTVReturn();
        nickHider = new NickHider();
        animations = new Animations();
        animationCreator = new AnimationCreator();
        camera = new Camera();
        mithrilMacro = new MithrilMacro();
        derp = new Derp();
        hitboxes = new Hitboxes();
        noRotate = new NoRotate();
        phase = new Phase();
        freeCam = new FreeCam();
        giants = new Giants();
        interfaces = new CustomInterfaces();
        autoBlock = new AutoBlock();
        speed = new Speed();
        test = new Test();
        targetStrafe = new TargetStrafe();
        guiMove = new GuiMove();
        dojoHelper = new DojoHelper();
        popupAnimation = new PopupAnimation();
        disabler = new Disabler();
        scaffold = new Scaffold();
        fly = new Flight();
        inventoryHUDModule = new InventoryDisplay();
        OringoClient.stop = new ArrayList<BlockPos>();
        OringoClient.devMode = false;
        OringoClient.scheduledTasks = new ArrayList<Runnable>();
        OringoClient.capes = new HashMap<String, ResourceLocation>();
        OringoClient.capesLoaded = new HashMap<File, ResourceLocation>();
    }
}
