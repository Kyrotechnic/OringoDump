//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package org.slf4j.helpers;

public class NOPLogger extends MarkerIgnoringBase
{
    private static final long serialVersionUID = -517220405410904473L;
    public static final NOPLogger NOP_LOGGER;
    
    protected NOPLogger() {
    }
    
    public String getName() {
        return "NOP";
    }
    
    public final boolean isTraceEnabled() {
        return false;
    }
    
    public final void trace(final String msg) {
    }
    
    public final void trace(final String format, final Object arg) {
    }
    
    public final void trace(final String format, final Object arg1, final Object arg2) {
    }
    
    public final void trace(final String format, final Object... argArray) {
    }
    
    public final void trace(final String msg, final Throwable t) {
    }
    
    public final boolean isDebugEnabled() {
        return false;
    }
    
    public final void debug(final String msg) {
    }
    
    public final void debug(final String format, final Object arg) {
    }
    
    public final void debug(final String format, final Object arg1, final Object arg2) {
    }
    
    public final void debug(final String format, final Object... argArray) {
    }
    
    public final void debug(final String msg, final Throwable t) {
    }
    
    public final boolean isInfoEnabled() {
        return false;
    }
    
    public final void info(final String msg) {
    }
    
    public final void info(final String format, final Object arg1) {
    }
    
    public final void info(final String format, final Object arg1, final Object arg2) {
    }
    
    public final void info(final String format, final Object... argArray) {
    }
    
    public final void info(final String msg, final Throwable t) {
    }
    
    public final boolean isWarnEnabled() {
        return false;
    }
    
    public final void warn(final String msg) {
    }
    
    public final void warn(final String format, final Object arg1) {
    }
    
    public final void warn(final String format, final Object arg1, final Object arg2) {
    }
    
    public final void warn(final String format, final Object... argArray) {
    }
    
    public final void warn(final String msg, final Throwable t) {
    }
    
    public final boolean isErrorEnabled() {
        return false;
    }
    
    public final void error(final String msg) {
    }
    
    public final void error(final String format, final Object arg1) {
    }
    
    public final void error(final String format, final Object arg1, final Object arg2) {
    }
    
    public final void error(final String format, final Object... argArray) {
    }
    
    public final void error(final String msg, final Throwable t) {
    }
    
    static {
        NOP_LOGGER = new NOPLogger();
    }
}
