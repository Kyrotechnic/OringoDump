//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.utils.font;

import java.awt.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import java.io.*;
import java.util.*;

public class Fonts
{
    private static Map<String, Font> fontCache;
    public static MinecraftFontRenderer robotoMedium;
    public static MinecraftFontRenderer robotoMediumBold;
    public static MinecraftFontRenderer robotoBig;
    public static MinecraftFontRenderer robotoSmall;
    public static MinecraftFontRenderer tahoma;
    public static MinecraftFontRenderer tahomaBold;
    public static MinecraftFontRenderer tahomaSmall;
    public static MinecraftFontRenderer nameTagFont;
    
    private Fonts() {
    }
    
    private static Font getFont(final String location, final int size) {
        Font font;
        try {
            if (Fonts.fontCache.containsKey(location)) {
                font = Fonts.fontCache.get(location).deriveFont(0, (float)size);
            }
            else {
                final InputStream is = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("oringoclient", "fonts/" + location)).getInputStream();
                font = Font.createFont(0, is);
                Fonts.fontCache.put(location, font);
                font = font.deriveFont(0, (float)size);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading font");
            font = new Font("default", 0, size);
        }
        return font;
    }
    
    public static void bootstrap() {
        Fonts.robotoSmall = new MinecraftFontRenderer(getFont("roboto.ttf", 18), true, false);
        Fonts.robotoMedium = new MinecraftFontRenderer(getFont("roboto.ttf", 19), true, false);
        Fonts.robotoBig = new MinecraftFontRenderer(getFont("robotoMedium.ttf", 20), true, false);
        Fonts.robotoMediumBold = new MinecraftFontRenderer(getFont("robotoMedium.ttf", 19), true, false);
        Fonts.tahomaBold = new MinecraftFontRenderer(getFont("TAHOMAB0.TTF", 22), true, false);
        Fonts.tahoma = new MinecraftFontRenderer(getFont("TAHOMA_0.TTF", 22), true, false);
        Fonts.tahomaSmall = Fonts.robotoMediumBold;
        Fonts.nameTagFont = new MinecraftFontRenderer(getFont("robotoMedium.ttf", 38), true, false);
    }
    
    static {
        Fonts.fontCache = new HashMap<String, Font>();
    }
}
