//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package org.slf4j.event;

import org.slf4j.*;

public interface LoggingEvent
{
    Level getLevel();
    
    Marker getMarker();
    
    String getLoggerName();
    
    String getMessage();
    
    String getThreadName();
    
    Object[] getArgumentArray();
    
    long getTimeStamp();
    
    Throwable getThrowable();
}
