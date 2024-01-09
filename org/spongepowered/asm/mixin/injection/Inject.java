//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package org.spongepowered.asm.mixin.injection;

import java.lang.annotation.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Inject {
    String id() default "";
    
    String[] method();
    
    Slice[] slice() default {};
    
    At[] at();
    
    boolean cancellable() default false;
    
    LocalCapture locals() default LocalCapture.NO_CAPTURE;
    
    boolean remap() default true;
    
    int require() default -1;
    
    int expect() default 1;
    
    int allow() default -1;
    
    String constraints() default "";
}
