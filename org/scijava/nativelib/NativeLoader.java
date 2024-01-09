//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package org.scijava.nativelib;

import java.io.*;

public class NativeLoader
{
    private static JniExtractor jniExtractor;
    
    public static void loadLibrary(final String libname) throws IOException {
        System.load(NativeLoader.jniExtractor.extractJni("", libname).getAbsolutePath());
    }
    
    public static void extractRegistered() throws IOException {
        NativeLoader.jniExtractor.extractRegistered();
    }
    
    public static JniExtractor getJniExtractor() {
        return NativeLoader.jniExtractor;
    }
    
    public static void setJniExtractor(final JniExtractor jniExtractor) {
        NativeLoader.jniExtractor = jniExtractor;
    }
    
    static {
        NativeLoader.jniExtractor = null;
        try {
            if (NativeLoader.class.getClassLoader() == ClassLoader.getSystemClassLoader()) {
                NativeLoader.jniExtractor = (JniExtractor)new DefaultJniExtractor();
            }
            else {
                NativeLoader.jniExtractor = (JniExtractor)new WebappJniExtractor("Classloader");
            }
        }
        catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
    }
}
