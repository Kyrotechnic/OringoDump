//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package org.json;

public class HTTPTokener extends JSONTokener
{
    public HTTPTokener(final String string) {
        super(string);
    }
    
    public String nextToken() throws JSONException {
        final StringBuilder sb = new StringBuilder();
        char c;
        do {
            c = this.next();
        } while (Character.isWhitespace(c));
        if (c != '\"' && c != '\'') {
            while (c != '\0' && !Character.isWhitespace(c)) {
                sb.append(c);
                c = this.next();
            }
            return sb.toString();
        }
        final char q = c;
        while (true) {
            c = this.next();
            if (c < ' ') {
                throw this.syntaxError("Unterminated string.");
            }
            if (c == q) {
                return sb.toString();
            }
            sb.append(c);
        }
    }
}
