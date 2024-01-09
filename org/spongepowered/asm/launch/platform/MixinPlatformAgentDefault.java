//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package org.spongepowered.asm.launch.platform;

import java.net.*;

public class MixinPlatformAgentDefault extends MixinPlatformAgentAbstract
{
    public MixinPlatformAgentDefault(final MixinPlatformManager manager, final URI uri) {
        super(manager, uri);
    }
    
    public void prepare() {
        final String compatibilityLevel = this.attributes.get("MixinCompatibilityLevel");
        if (compatibilityLevel != null) {
            this.manager.setCompatibilityLevel(compatibilityLevel);
        }
        final String mixinConfigs = this.attributes.get("MixinConfigs");
        if (mixinConfigs != null) {
            for (final String config : mixinConfigs.split(",")) {
                this.manager.addConfig(config.trim());
            }
        }
        final String tokenProviders = this.attributes.get("MixinTokenProviders");
        if (tokenProviders != null) {
            for (final String provider : tokenProviders.split(",")) {
                this.manager.addTokenProvider(provider.trim());
            }
        }
    }
    
    public void initPrimaryContainer() {
    }
    
    public void inject() {
    }
    
    public String getLaunchTarget() {
        return this.attributes.get("Main-Class");
    }
}
