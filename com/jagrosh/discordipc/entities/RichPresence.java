//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package com.jagrosh.discordipc.entities;

import java.time.*;
import org.json.*;

public class RichPresence
{
    private final String state;
    private final String details;
    private final OffsetDateTime startTimestamp;
    private final OffsetDateTime endTimestamp;
    private final String largeImageKey;
    private final String largeImageText;
    private final String smallImageKey;
    private final String smallImageText;
    private final String partyId;
    private final int partySize;
    private final int partyMax;
    private final String matchSecret;
    private final String joinSecret;
    private final String spectateSecret;
    private final boolean instance;
    
    public RichPresence(final String state, final String details, final OffsetDateTime startTimestamp, final OffsetDateTime endTimestamp, final String largeImageKey, final String largeImageText, final String smallImageKey, final String smallImageText, final String partyId, final int partySize, final int partyMax, final String matchSecret, final String joinSecret, final String spectateSecret, final boolean instance) {
        this.state = state;
        this.details = details;
        this.startTimestamp = startTimestamp;
        this.endTimestamp = endTimestamp;
        this.largeImageKey = largeImageKey;
        this.largeImageText = largeImageText;
        this.smallImageKey = smallImageKey;
        this.smallImageText = smallImageText;
        this.partyId = partyId;
        this.partySize = partySize;
        this.partyMax = partyMax;
        this.matchSecret = matchSecret;
        this.joinSecret = joinSecret;
        this.spectateSecret = spectateSecret;
        this.instance = instance;
    }
    
    public JSONObject toJson() {
        return new JSONObject().put("state", (Object)this.state).put("details", (Object)this.details).put("timestamps", (Object)new JSONObject().put("start", (Object)((this.startTimestamp == null) ? null : Long.valueOf(this.startTimestamp.toEpochSecond()))).put("end", (Object)((this.endTimestamp == null) ? null : Long.valueOf(this.endTimestamp.toEpochSecond())))).put("assets", (Object)new JSONObject().put("large_image", (Object)this.largeImageKey).put("large_text", (Object)this.largeImageText).put("small_image", (Object)this.smallImageKey).put("small_text", (Object)this.smallImageText)).put("party", (Object)((this.partyId == null) ? null : new JSONObject().put("id", (Object)this.partyId).put("size", (Object)new JSONArray().put(this.partySize).put(this.partyMax)))).put("secrets", (Object)new JSONObject().put("join", (Object)this.joinSecret).put("spectate", (Object)this.spectateSecret).put("match", (Object)this.matchSecret)).put("instance", this.instance);
    }
    
    public static class Builder
    {
        private String state;
        private String details;
        private OffsetDateTime startTimestamp;
        private OffsetDateTime endTimestamp;
        private String largeImageKey;
        private String largeImageText;
        private String smallImageKey;
        private String smallImageText;
        private String partyId;
        private int partySize;
        private int partyMax;
        private String matchSecret;
        private String joinSecret;
        private String spectateSecret;
        private boolean instance;
        
        public RichPresence build() {
            return new RichPresence(this.state, this.details, this.startTimestamp, this.endTimestamp, this.largeImageKey, this.largeImageText, this.smallImageKey, this.smallImageText, this.partyId, this.partySize, this.partyMax, this.matchSecret, this.joinSecret, this.spectateSecret, this.instance);
        }
        
        public Builder setState(final String state) {
            this.state = state;
            return this;
        }
        
        public Builder setDetails(final String details) {
            this.details = details;
            return this;
        }
        
        public Builder setStartTimestamp(final OffsetDateTime startTimestamp) {
            this.startTimestamp = startTimestamp;
            return this;
        }
        
        public Builder setEndTimestamp(final OffsetDateTime endTimestamp) {
            this.endTimestamp = endTimestamp;
            return this;
        }
        
        public Builder setLargeImage(final String largeImageKey, final String largeImageText) {
            this.largeImageKey = largeImageKey;
            this.largeImageText = largeImageText;
            return this;
        }
        
        public Builder setLargeImage(final String largeImageKey) {
            return this.setLargeImage(largeImageKey, null);
        }
        
        public Builder setSmallImage(final String smallImageKey, final String smallImageText) {
            this.smallImageKey = smallImageKey;
            this.smallImageText = smallImageText;
            return this;
        }
        
        public Builder setSmallImage(final String smallImageKey) {
            return this.setSmallImage(smallImageKey, null);
        }
        
        public Builder setParty(final String partyId, final int partySize, final int partyMax) {
            this.partyId = partyId;
            this.partySize = partySize;
            this.partyMax = partyMax;
            return this;
        }
        
        public Builder setMatchSecret(final String matchSecret) {
            this.matchSecret = matchSecret;
            return this;
        }
        
        public Builder setJoinSecret(final String joinSecret) {
            this.joinSecret = joinSecret;
            return this;
        }
        
        public Builder setSpectateSecret(final String spectateSecret) {
            this.spectateSecret = spectateSecret;
            return this;
        }
        
        public Builder setInstance(final boolean instance) {
            this.instance = instance;
            return this;
        }
    }
}
