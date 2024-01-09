//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package org.slf4j.helpers;

public class FormattingTuple
{
    public static FormattingTuple NULL;
    private String message;
    private Throwable throwable;
    private Object[] argArray;
    
    public FormattingTuple(final String message) {
        this(message, null, null);
    }
    
    public FormattingTuple(final String message, final Object[] argArray, final Throwable throwable) {
        this.message = message;
        this.throwable = throwable;
        this.argArray = argArray;
    }
    
    public String getMessage() {
        return this.message;
    }
    
    public Object[] getArgArray() {
        return this.argArray;
    }
    
    public Throwable getThrowable() {
        return this.throwable;
    }
    
    static {
        FormattingTuple.NULL = new FormattingTuple(null);
    }
}
