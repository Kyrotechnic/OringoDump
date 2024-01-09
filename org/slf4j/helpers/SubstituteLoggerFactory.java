//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package org.slf4j.helpers;

import java.util.concurrent.*;
import org.slf4j.event.*;
import org.slf4j.*;
import java.util.*;

public class SubstituteLoggerFactory implements ILoggerFactory
{
    boolean postInitialization;
    final Map<String, SubstituteLogger> loggers;
    final LinkedBlockingQueue<SubstituteLoggingEvent> eventQueue;
    
    public SubstituteLoggerFactory() {
        this.postInitialization = false;
        this.loggers = new HashMap<String, SubstituteLogger>();
        this.eventQueue = new LinkedBlockingQueue<SubstituteLoggingEvent>();
    }
    
    public synchronized Logger getLogger(final String name) {
        SubstituteLogger logger = this.loggers.get(name);
        if (logger == null) {
            logger = new SubstituteLogger(name, (Queue)this.eventQueue, this.postInitialization);
            this.loggers.put(name, logger);
        }
        return (Logger)logger;
    }
    
    public List<String> getLoggerNames() {
        return new ArrayList<String>(this.loggers.keySet());
    }
    
    public List<SubstituteLogger> getLoggers() {
        return new ArrayList<SubstituteLogger>(this.loggers.values());
    }
    
    public LinkedBlockingQueue<SubstituteLoggingEvent> getEventQueue() {
        return this.eventQueue;
    }
    
    public void postInitialization() {
        this.postInitialization = true;
    }
    
    public void clear() {
        this.loggers.clear();
        this.eventQueue.clear();
    }
}
