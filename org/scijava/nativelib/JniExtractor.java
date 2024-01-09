//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package org.scijava.nativelib;

import java.io.*;

public interface JniExtractor
{
    File extractJni(final String p0, final String p1) throws IOException;
    
    void extractRegistered() throws IOException;
}
