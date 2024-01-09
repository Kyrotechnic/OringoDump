//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package org.slf4j.helpers;

import org.slf4j.*;
import java.util.concurrent.*;

public class BasicMarkerFactory implements IMarkerFactory
{
    private final ConcurrentMap<String, Marker> markerMap;
    
    public BasicMarkerFactory() {
        this.markerMap = new ConcurrentHashMap<String, Marker>();
    }
    
    public Marker getMarker(final String name) {
        if (name == null) {
            throw new IllegalArgumentException("Marker name cannot be null");
        }
        Marker marker = this.markerMap.get(name);
        if (marker == null) {
            marker = (Marker)new BasicMarker(name);
            final Marker oldMarker = this.markerMap.putIfAbsent(name, marker);
            if (oldMarker != null) {
                marker = oldMarker;
            }
        }
        return marker;
    }
    
    public boolean exists(final String name) {
        return name != null && this.markerMap.containsKey(name);
    }
    
    public boolean detachMarker(final String name) {
        return name != null && this.markerMap.remove(name) != null;
    }
    
    public Marker getDetachedMarker(final String name) {
        return (Marker)new BasicMarker(name);
    }
}
