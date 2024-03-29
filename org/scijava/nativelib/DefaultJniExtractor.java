//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package org.scijava.nativelib;

import java.io.*;

public class DefaultJniExtractor extends BaseJniExtractor
{
    private File nativeDir;
    
    public DefaultJniExtractor() throws IOException {
        super((Class)null);
        this.init("tmplib");
    }
    
    public DefaultJniExtractor(final Class libraryJarClass, final String tmplib) throws IOException {
        super(libraryJarClass);
        this.init(tmplib);
    }
    
    void init(final String tmplib) throws IOException {
        (this.nativeDir = new File(System.getProperty("java.library.tmpdir", tmplib))).mkdirs();
        if (!this.nativeDir.isDirectory()) {
            throw new IOException("Unable to create native library working directory " + this.nativeDir);
        }
    }
    
    public File getJniDir() {
        return this.nativeDir;
    }
    
    public File getNativeDir() {
        return this.nativeDir;
    }
}
