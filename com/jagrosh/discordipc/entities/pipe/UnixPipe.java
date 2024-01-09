//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package com.jagrosh.discordipc.entities.pipe;

import com.jagrosh.discordipc.*;
import java.util.*;
import org.newsclub.net.unix.*;
import java.net.*;
import com.jagrosh.discordipc.entities.*;
import java.nio.*;
import java.io.*;
import org.json.*;
import org.slf4j.*;

public class UnixPipe extends Pipe
{
    private static final Logger LOGGER;
    private final AFUNIXSocket socket;
    
    UnixPipe(final IPCClient ipcClient, final HashMap<String, Callback> callbacks, final String location) throws IOException {
        super(ipcClient, (HashMap)callbacks);
        (this.socket = AFUNIXSocket.newInstance()).connect((SocketAddress)new AFUNIXSocketAddress(new File(location)));
    }
    
    public Packet read() throws IOException, JSONException {
        final InputStream is = this.socket.getInputStream();
        while ((this.status == PipeStatus.CONNECTED || this.status == PipeStatus.CLOSING) && is.available() == 0) {
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
        byte[] d = new byte[8];
        is.read(d);
        final ByteBuffer bb = ByteBuffer.wrap(d);
        final Packet.OpCode op = Packet.OpCode.values()[Integer.reverseBytes(bb.getInt())];
        d = new byte[Integer.reverseBytes(bb.getInt())];
        is.read(d);
        final Packet p = new Packet(op, new JSONObject(new String(d)));
        UnixPipe.LOGGER.debug(String.format("Received packet: %s", p.toString()));
        if (this.listener != null) {
            this.listener.onPacketReceived(this.ipcClient, p);
        }
        return p;
    }
    
    public void write(final byte[] b) throws IOException {
        this.socket.getOutputStream().write(b);
    }
    
    public void close() throws IOException {
        UnixPipe.LOGGER.debug("Closing IPC pipe...");
        this.status = PipeStatus.CLOSING;
        this.send(Packet.OpCode.CLOSE, new JSONObject(), (Callback)null);
        this.status = PipeStatus.CLOSED;
        this.socket.close();
    }
    
    static {
        LOGGER = LoggerFactory.getLogger((Class)UnixPipe.class);
    }
}
