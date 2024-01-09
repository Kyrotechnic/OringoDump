//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package org.slf4j;

import java.io.*;
import java.util.*;

public interface Marker extends Serializable
{
    public static final String ANY_MARKER = "*";
    public static final String ANY_NON_NULL_MARKER = "+";
    
    String getName();
    
    void add(final Marker p0);
    
    boolean remove(final Marker p0);
    
    @Deprecated
    boolean hasChildren();
    
    boolean hasReferences();
    
    Iterator<Marker> iterator();
    
    boolean contains(final Marker p0);
    
    boolean contains(final String p0);
    
    boolean equals(final Object p0);
    
    int hashCode();
}
