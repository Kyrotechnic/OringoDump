//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package com.jagrosh.discordipc.entities.pipe;

import com.jagrosh.discordipc.*;
import java.util.*;
import java.io.*;
import com.jagrosh.discordipc.entities.*;
import org.json.*;
import org.slf4j.*;

public class WindowsPipe extends Pipe
{
    private static final Logger LOGGER;
    private final RandomAccessFile file;
    
    WindowsPipe(final IPCClient ipcClient, final HashMap<String, Callback> callbacks, final String location) {
        super(ipcClient, (HashMap)callbacks);
        try {
            this.file = new RandomAccessFile(location, "rw");
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    
    public void write(final byte[] b) throws IOException {
        this.file.write(b);
    }
    
    public Packet read() throws IOException, JSONException {
        while ((this.status == PipeStatus.CONNECTED || this.status == PipeStatus.CLOSING) && this.file.length() == 0L) {
            try {
                Thread.sleep(50L);
            }
            catch (InterruptedException ex) {}
        }
        if (this.status == PipeStatus.DISCONNECTED) {
            throw new IOException("Disconnected!");
        }
        if (this.status == PipeStatus.CLOSED) {
            return new Packet(Packet.OpCode.CLOSE, (JSONObject)null);
        }
        final Packet.OpCode op = Packet.OpCode.values()[Integer.reverseBytes(this.file.readInt())];
        final int len = Integer.reverseBytes(this.file.readInt());
        final byte[] d = new byte[len];
        this.file.readFully(d);
        final Packet p = new Packet(op, new JSONObject(new String(d)));
        WindowsPipe.LOGGER.debug(String.format("Received packet: %s", p.toString()));
        if (this.listener != null) {
            this.listener.onPacketReceived(this.ipcClient, p);
        }
        return p;
    }
    
    public void close() throws IOException {
        WindowsPipe.LOGGER.debug("Closing IPC pipe...");
        this.status = PipeStatus.CLOSING;
        this.send(Packet.OpCode.CLOSE, new JSONObject(), (Callback)null);
        this.status = PipeStatus.CLOSED;
        this.file.close();
    }
    
    static {
        LOGGER = LoggerFactory.getLogger((Class)WindowsPipe.class);
    }
}
