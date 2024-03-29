//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package com.jagrosh.discordipc;

import java.util.*;
import com.jagrosh.discordipc.exceptions.*;
import com.jagrosh.discordipc.entities.pipe.*;
import java.io.*;
import com.jagrosh.discordipc.entities.*;
import org.json.*;
import java.lang.management.*;
import org.slf4j.*;

public final class IPCClient implements Closeable
{
    private static final Logger LOGGER;
    private final long clientId;
    private final HashMap<String, Callback> callbacks;
    private volatile Pipe pipe;
    private IPCListener listener;
    private Thread readThread;
    
    public IPCClient(final long clientId) {
        this.callbacks = new HashMap<String, Callback>();
        this.listener = null;
        this.readThread = null;
        this.clientId = clientId;
    }
    
    public void setListener(final IPCListener listener) {
        this.listener = listener;
        if (this.pipe != null) {
            this.pipe.setListener(listener);
        }
    }
    
    public void connect(final DiscordBuild... preferredOrder) throws NoDiscordClientException {
        this.checkConnected(false);
        this.callbacks.clear();
        this.pipe = null;
        this.pipe = Pipe.openPipe(this, this.clientId, (HashMap)this.callbacks, preferredOrder);
        IPCClient.LOGGER.debug("Client is now connected and ready!");
        if (this.listener != null) {
            this.listener.onReady(this);
        }
        this.startReading();
    }
    
    public void sendRichPresence(final RichPresence presence) {
        this.sendRichPresence(presence, null);
    }
    
    public void sendRichPresence(final RichPresence presence, final Callback callback) {
        this.checkConnected(true);
        IPCClient.LOGGER.debug("Sending RichPresence to discord: " + ((presence == null) ? null : presence.toJson().toString()));
        this.pipe.send(Packet.OpCode.FRAME, new JSONObject().put("cmd", (Object)"SET_ACTIVITY").put("args", (Object)new JSONObject().put("pid", getPID()).put("activity", (Object)((presence == null) ? null : presence.toJson()))), callback);
    }
    
    public void subscribe(final Event sub) {
        this.subscribe(sub, null);
    }
    
    public void subscribe(final Event sub, final Callback callback) {
        this.checkConnected(true);
        if (!sub.isSubscribable()) {
            throw new IllegalStateException("Cannot subscribe to " + sub + " event!");
        }
        IPCClient.LOGGER.debug(String.format("Subscribing to Event: %s", sub.name()));
        this.pipe.send(Packet.OpCode.FRAME, new JSONObject().put("cmd", (Object)"SUBSCRIBE").put("evt", (Object)sub.name()), callback);
    }
    
    public PipeStatus getStatus() {
        if (this.pipe == null) {
            return PipeStatus.UNINITIALIZED;
        }
        return this.pipe.getStatus();
    }
    
    @Override
    public void close() {
        this.checkConnected(true);
        try {
            this.pipe.close();
        }
        catch (IOException e) {
            IPCClient.LOGGER.debug("Failed to close pipe", (Throwable)e);
        }
    }
    
    public DiscordBuild getDiscordBuild() {
        if (this.pipe == null) {
            return null;
        }
        return this.pipe.getDiscordBuild();
    }
    
    private void checkConnected(final boolean connected) {
        if (connected && this.getStatus() != PipeStatus.CONNECTED) {
            throw new IllegalStateException(String.format("IPCClient (ID: %d) is not connected!", this.clientId));
        }
        if (!connected && this.getStatus() == PipeStatus.CONNECTED) {
            throw new IllegalStateException(String.format("IPCClient (ID: %d) is already connected!", this.clientId));
        }
    }
    
    private void startReading() {
        Packet p;
        final Object o;
        JSONObject json;
        Event event;
        String nonce;
        JSONObject data;
        JSONObject u;
        User user;
        final Exception ex2;
        Exception ex;
        this.readThread = new Thread(() -> {
            try {
                while (true) {
                    p = this.pipe.read();
                    if (((Packet)o).getOp() != Packet.OpCode.CLOSE) {
                        json = p.getJson();
                        event = Event.of(json.optString("evt", (String)null));
                        nonce = json.optString("nonce", (String)null);
                        switch (event) {
                            case NULL: {
                                if (nonce != null && this.callbacks.containsKey(nonce)) {
                                    this.callbacks.remove(nonce).succeed(p);
                                    break;
                                }
                                else {
                                    break;
                                }
                                break;
                            }
                            case ERROR: {
                                if (nonce != null && this.callbacks.containsKey(nonce)) {
                                    this.callbacks.remove(nonce).fail(json.getJSONObject("data").optString("message", (String)null));
                                    break;
                                }
                                else {
                                    break;
                                }
                                break;
                            }
                            case ACTIVITY_JOIN: {
                                IPCClient.LOGGER.debug("Reading thread received a 'join' event.");
                                break;
                            }
                            case ACTIVITY_SPECTATE: {
                                IPCClient.LOGGER.debug("Reading thread received a 'spectate' event.");
                                break;
                            }
                            case ACTIVITY_JOIN_REQUEST: {
                                IPCClient.LOGGER.debug("Reading thread received a 'join request' event.");
                                break;
                            }
                            case UNKNOWN: {
                                IPCClient.LOGGER.debug("Reading thread encountered an event with an unknown type: " + json.getString("evt"));
                                break;
                            }
                        }
                        if (this.listener != null && json.has("cmd") && json.getString("cmd").equals("DISPATCH")) {
                            try {
                                data = json.getJSONObject("data");
                                switch (Event.of(json.getString("evt"))) {
                                    case ACTIVITY_JOIN: {
                                        this.listener.onActivityJoin(this, data.getString("secret"));
                                        continue;
                                    }
                                    case ACTIVITY_SPECTATE: {
                                        this.listener.onActivitySpectate(this, data.getString("secret"));
                                        continue;
                                    }
                                    case ACTIVITY_JOIN_REQUEST: {
                                        u = data.getJSONObject("user");
                                        user = new User(u.getString("username"), u.getString("discriminator"), Long.parseLong(u.getString("id")), u.optString("avatar", (String)null));
                                        this.listener.onActivityJoinRequest(this, data.optString("secret", (String)null), user);
                                        continue;
                                    }
                                }
                            }
                            catch (Exception e) {
                                IPCClient.LOGGER.error("Exception when handling event: ", (Throwable)e);
                            }
                        }
                        else {
                            continue;
                        }
                    }
                    else {
                        break;
                    }
                }
                this.pipe.setStatus(PipeStatus.DISCONNECTED);
                if (this.listener != null) {
                    this.listener.onClose(this, p.getJson());
                }
            }
            catch (IOException | JSONException ex3) {
                ex = ex2;
                if (ex instanceof IOException) {
                    IPCClient.LOGGER.error("Reading thread encountered an IOException", (Throwable)ex);
                }
                else {
                    IPCClient.LOGGER.error("Reading thread encountered an JSONException", (Throwable)ex);
                }
                this.pipe.setStatus(PipeStatus.DISCONNECTED);
                if (this.listener != null) {
                    this.listener.onDisconnect(this, ex);
                }
            }
            return;
        });
        IPCClient.LOGGER.debug("Starting IPCClient reading thread!");
        this.readThread.start();
    }
    
    private static int getPID() {
        final String pr = ManagementFactory.getRuntimeMXBean().getName();
        return Integer.parseInt(pr.substring(0, pr.indexOf(64)));
    }
    
    static {
        LOGGER = LoggerFactory.getLogger((Class)IPCClient.class);
    }
    
    public enum Event
    {
        NULL(false), 
        READY(false), 
        ERROR(false), 
        ACTIVITY_JOIN(true), 
        ACTIVITY_SPECTATE(true), 
        ACTIVITY_JOIN_REQUEST(true), 
        UNKNOWN(false);
        
        private final boolean subscribable;
        
        private Event(final boolean subscribable) {
            this.subscribable = subscribable;
        }
        
        public boolean isSubscribable() {
            return this.subscribable;
        }
        
        static Event of(final String str) {
            if (str == null) {
                return Event.NULL;
            }
            for (final Event s : values()) {
                if (s != Event.UNKNOWN && s.name().equalsIgnoreCase(str)) {
                    return s;
                }
            }
            return Event.UNKNOWN;
        }
    }
}
