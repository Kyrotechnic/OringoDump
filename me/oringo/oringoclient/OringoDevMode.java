//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient;

import net.minecraft.launchwrapper.*;
import java.io.*;
import me.oringodevmode.*;

public class OringoDevMode implements IClassTransformer
{
    private boolean enabled;
    
    public OringoDevMode() {
        this.enabled = new File("OringoDev").exists();
    }
    
    public byte[] transform(final String name, final String transformedName, final byte[] basicClass) {
        if (this.enabled && !transformedName.startsWith("java") && !transformedName.startsWith("sun") && !transformedName.startsWith("org.lwjgl") && !transformedName.startsWith("org.apache") && !transformedName.startsWith("org.objectweb")) {
            Transformer.classes.remove(transformedName);
            Transformer.classes.put(transformedName, basicClass);
        }
        return basicClass;
    }
}
