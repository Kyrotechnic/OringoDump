//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package org.newsclub.net.unix;

import java.net.*;
import java.io.*;

public class AFUNIXSocketAddress extends InetSocketAddress
{
    private static final long serialVersionUID = 1L;
    private final String socketFile;
    
    public AFUNIXSocketAddress(final File socketFile) throws IOException {
        this(socketFile, 0);
    }
    
    public AFUNIXSocketAddress(final File socketFile, final int port) throws IOException {
        super(0);
        if (port != 0) {
            NativeUnixSocket.setPort1(this, port);
        }
        this.socketFile = socketFile.getCanonicalPath();
    }
    
    public String getSocketFile() {
        return this.socketFile;
    }
    
    @Override
    public String toString() {
        return this.getClass().getName() + "[host=" + this.getHostName() + ";port=" + this.getPort() + ";file=" + this.socketFile + "]";
    }
}
