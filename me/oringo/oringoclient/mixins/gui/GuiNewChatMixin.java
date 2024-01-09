//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.mixins.gui;

import net.minecraft.client.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.oringo.oringoclient.*;
import net.minecraft.entity.player.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import me.oringo.oringoclient.utils.font.*;
import java.awt.*;
import me.oringo.oringoclient.utils.*;
import me.oringo.oringoclient.utils.shader.*;
import net.minecraft.util.*;
import java.util.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.injection.*;
import com.google.common.collect.*;

@Mixin(value = { GuiNewChat.class }, priority = 1)
public abstract class GuiNewChatMixin extends GuiMixin
{
    @Shadow
    @Final
    private Minecraft mc;
    @Shadow
    @Final
    private List<ChatLine> field_146253_i;
    @Shadow
    private int scrollPos;
    @Shadow
    private boolean isScrolled;
    
    @Shadow
    public abstract int getLineCount();
    
    @Shadow
    public abstract boolean getChatOpen();
    
    @Shadow
    public abstract float getChatScale();
    
    @Shadow
    public abstract int getChatWidth();
    
    @Inject(method = { "drawChat" }, at = { @At("HEAD") }, cancellable = true)
    private void drawChat(final int updateCounter, final CallbackInfo ci) {
        if (OringoClient.interfaces.customChat.isEnabled() && OringoClient.interfaces.isToggled()) {
            if (this.mc.gameSettings.chatVisibility != EntityPlayer.EnumChatVisibility.HIDDEN) {
                final ScaledResolution scaledresolution = new ScaledResolution(this.mc);
                GL11.glPopMatrix();
                GL11.glPushMatrix();
                GlStateManager.translate(0.0f, (float)(scaledresolution.getScaledHeight() - 60), 0.0f);
                final int maxLineCount = this.getLineCount();
                boolean isChatOpen = false;
                int j = 0;
                final int lineCount = this.field_146253_i.size();
                int fontHeight = OringoClient.interfaces.customChatFont.isEnabled() ? (Fonts.robotoBig.getHeight() + 3) : this.mc.fontRendererObj.FONT_HEIGHT;
                if (lineCount > 0) {
                    if (this.getChatOpen()) {
                        isChatOpen = true;
                    }
                    final float scale = this.getChatScale();
                    GlStateManager.pushMatrix();
                    GlStateManager.translate(2.0f, 20.0f, 0.0f);
                    GlStateManager.scale(scale, scale, 1.0f);
                    final int scaledWidth = MathHelper.ceiling_float_int(this.getChatWidth() / scale);
                    final float x = 0.0f;
                    float y = 0.0f;
                    boolean render = false;
                    for (int i = 0; i + this.scrollPos < this.field_146253_i.size() && i < maxLineCount; ++i) {
                        final ChatLine chatline = this.field_146253_i.get(i + this.scrollPos);
                        if (chatline != null && (updateCounter - chatline.getUpdatedCounter() < 200 || isChatOpen)) {
                            render = true;
                            if (!isChatOpen && updateCounter - chatline.getUpdatedCounter() > 195) {
                                float percent = 1.0f - (updateCounter - chatline.getUpdatedCounter() + TimerUtil.getTimer().renderPartialTicks - 195.0f) / 5.0f;
                                percent = MathUtil.clamp(percent, 0.0f, 1.0f);
                                y -= fontHeight * percent;
                            }
                            else {
                                y -= fontHeight;
                            }
                        }
                    }
                    if (render) {
                        int blur = 0;
                        final String selected = OringoClient.interfaces.blurStrength.getSelected();
                        switch (selected) {
                            case "Low": {
                                blur = 7;
                                break;
                            }
                            case "High": {
                                blur = 25;
                                break;
                            }
                        }
                        if (blur > 0) {
                            for (float k = 0.5f; k < 3.0f; k += 0.5f) {
                                RenderUtils.drawRoundedRect(x + k - 2.0f, y + k, x + scaledWidth + 4.0f + k, 1.0f + k, 5.0, new Color(20, 20, 20, 40).getRGB());
                            }
                        }
                        StencilUtils.initStencil();
                        StencilUtils.bindWriteStencilBuffer();
                        RenderUtils.drawRoundedRect(x - 2.0f, y, x + scaledWidth + 4.0f, 1.0, 5.0, Color.white.getRGB());
                        GL11.glPopMatrix();
                        GL11.glPopMatrix();
                        StencilUtils.bindReadStencilBuffer(1);
                        BlurUtils.renderBlurredBackground((float)blur, (float)scaledresolution.getScaledWidth(), (float)scaledresolution.getScaledHeight(), 0.0f, 0.0f, (float)scaledresolution.getScaledWidth(), (float)scaledresolution.getScaledHeight());
                        GL11.glPushMatrix();
                        GlStateManager.translate(0.0f, (float)(scaledresolution.getScaledHeight() - 60), 0.0f);
                        GL11.glPushMatrix();
                        GlStateManager.translate(2.0f, 20.0f, 0.0f);
                        GlStateManager.scale(scale, scale, 1.0f);
                        RenderUtils.drawRoundedRect(x - 2.0f, y, x + scaledWidth + 4.0f, 1.0, 5.0, new Color(20, 20, 20, 60).getRGB());
                    }
                    for (int i = 0; i + this.scrollPos < this.field_146253_i.size() && i < maxLineCount; ++i) {
                        final ChatLine chatline = this.field_146253_i.get(i + this.scrollPos);
                        if (chatline != null) {
                            final int j2 = updateCounter - chatline.getUpdatedCounter();
                            if (j2 < 200 || isChatOpen) {
                                ++j;
                                final int left = 0;
                                final int top = -i * fontHeight;
                                final String text = chatline.getChatComponent().getFormattedText();
                                GlStateManager.enableBlend();
                                if (OringoClient.interfaces.customChatFont.isEnabled()) {
                                    Fonts.robotoBig.drawSmoothStringWithShadow(text, (float)left, (float)(top - (fontHeight - 2.3)), Color.white.getRGB());
                                }
                                else {
                                    this.mc.fontRendererObj.drawStringWithShadow(text, (float)left, (float)(top - (fontHeight - 1)), 16777215);
                                }
                                GlStateManager.disableAlpha();
                                GlStateManager.disableBlend();
                            }
                        }
                    }
                    if (render) {
                        StencilUtils.uninitStencil();
                    }
                    if (isChatOpen) {
                        GlStateManager.translate(-3.0f, 0.0f, 0.0f);
                        fontHeight = this.mc.fontRendererObj.FONT_HEIGHT;
                        final int l2 = lineCount * fontHeight + lineCount;
                        final int i2 = j * fontHeight + j;
                        final int j3 = this.scrollPos * i2 / lineCount;
                        final int k2 = i2 * i2 / l2;
                        if (l2 != i2) {
                            final int opacity = (j3 > 0) ? 170 : 96;
                            final int l3 = this.isScrolled ? 13382451 : 3355562;
                            drawRect(0, -j3, 2, -j3 - k2, l3 + (opacity << 24));
                            drawRect(2, -j3, 1, -j3 - k2, 13421772 + (opacity << 24));
                        }
                    }
                    GlStateManager.popMatrix();
                }
            }
            ci.cancel();
        }
    }
    
    @Overwrite
    public IChatComponent getChatComponent(final int p_146236_1_, final int p_146236_2_) {
        if (!this.getChatOpen()) {
            return null;
        }
        final ScaledResolution scaledresolution = new ScaledResolution(this.mc);
        final int i = scaledresolution.getScaleFactor();
        final float f = this.getChatScale();
        int j = p_146236_1_ / i - 3;
        int k = p_146236_2_ / i - 27;
        if (OringoClient.interfaces.isToggled() && OringoClient.interfaces.customChat.isEnabled()) {
            k -= 12;
        }
        j = MathHelper.floor_float(j / f);
        k = MathHelper.floor_float(k / f);
        if (j < 0 || k < 0) {
            return null;
        }
        final int l = Math.min(this.getLineCount(), this.field_146253_i.size());
        if (j <= MathHelper.floor_float(this.getChatWidth() / this.getChatScale()) && k < this.getHeight() * l + l) {
            final int i2 = k / this.getHeight() + this.scrollPos;
            if (i2 >= 0 && i2 < this.field_146253_i.size()) {
                final ChatLine chatline = this.field_146253_i.get(i2);
                int j2 = 0;
                for (final IChatComponent ichatcomponent : chatline.getChatComponent()) {
                    if (ichatcomponent instanceof ChatComponentText) {
                        j2 += (int)((OringoClient.interfaces.customChatFont.isEnabled() && OringoClient.interfaces.isToggled() && OringoClient.interfaces.customChat.isEnabled()) ? Fonts.robotoBig.getStringWidth(GuiUtilRenderComponents.func_178909_a(((ChatComponentText)ichatcomponent).getChatComponentText_TextValue(), false)) : this.mc.fontRendererObj.getStringWidth(GuiUtilRenderComponents.func_178909_a(((ChatComponentText)ichatcomponent).getChatComponentText_TextValue(), false)));
                        if (j2 > j) {
                            return ichatcomponent;
                        }
                        continue;
                    }
                }
            }
            return null;
        }
        return null;
    }
    
    private int getHeight() {
        return (OringoClient.interfaces.customChatFont.isEnabled() && OringoClient.interfaces.customChat.isEnabled() && OringoClient.interfaces.isToggled()) ? (Fonts.robotoBig.getHeight() + 3) : this.mc.fontRendererObj.FONT_HEIGHT;
    }
    
    @Redirect(method = { "setChatLine" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiUtilRenderComponents;func_178908_a(Lnet/minecraft/util/IChatComponent;ILnet/minecraft/client/gui/FontRenderer;ZZ)Ljava/util/List;"))
    private List<IChatComponent> onFunc(final IChatComponent k, final int s1, final FontRenderer chatcomponenttext, final boolean l, final boolean chatcomponenttext2) {
        return (OringoClient.interfaces.customChatFont.isEnabled() && OringoClient.interfaces.isToggled() && OringoClient.interfaces.customChat.isEnabled()) ? this.wrapToLen(k, s1, chatcomponenttext) : GuiUtilRenderComponents.func_178908_a(k, s1, chatcomponenttext, l, chatcomponenttext2);
    }
    
    private List<IChatComponent> wrapToLen(final IChatComponent p_178908_0_, final int p_178908_1_, final FontRenderer p_178908_2_) {
        int i = 0;
        IChatComponent ichatcomponent = (IChatComponent)new ChatComponentText("");
        final List<IChatComponent> list = (List<IChatComponent>)Lists.newArrayList();
        final List<IChatComponent> list2 = (List<IChatComponent>)Lists.newArrayList((Iterable)p_178908_0_);
        for (int j = 0; j < list2.size(); ++j) {
            final IChatComponent ichatcomponent2 = list2.get(j);
            String s = ichatcomponent2.getUnformattedTextForChat();
            boolean flag = false;
            if (s.contains("\n")) {
                final int k = s.indexOf(10);
                final String s2 = s.substring(k + 1);
                s = s.substring(0, k + 1);
                final ChatComponentText chatcomponenttext = new ChatComponentText(s2);
                chatcomponenttext.setChatStyle(ichatcomponent2.getChatStyle().createShallowCopy());
                list2.add(j + 1, (IChatComponent)chatcomponenttext);
                flag = true;
            }
            final String s3 = GuiUtilRenderComponents.func_178909_a(ichatcomponent2.getChatStyle().getFormattingCode() + s, false);
            final String s4 = s3.endsWith("\n") ? s3.substring(0, s3.length() - 1) : s3;
            double i2 = Fonts.robotoBig.getStringWidth(s4);
            ChatComponentText chatcomponenttext2 = new ChatComponentText(s4);
            chatcomponenttext2.setChatStyle(ichatcomponent2.getChatStyle().createShallowCopy());
            if (i + i2 > p_178908_1_) {
                String s5 = Fonts.robotoBig.trimStringToWidth(s3, p_178908_1_ - i, false);
                String s6 = (s5.length() < s3.length()) ? s3.substring(s5.length()) : null;
                if (s6 != null && s6.length() > 0) {
                    final int l = s5.lastIndexOf(" ");
                    if (l >= 0 && Fonts.robotoBig.getStringWidth(s3.substring(0, l)) > 0.0) {
                        s5 = s3.substring(0, l);
                        s6 = s3.substring(l);
                    }
                    else if (i > 0 && !s3.contains(" ")) {
                        s5 = "";
                        s6 = s3;
                    }
                    s6 = FontRenderer.getFormatFromString(s5) + s6;
                    final ChatComponentText chatcomponenttext3 = new ChatComponentText(s6);
                    chatcomponenttext3.setChatStyle(ichatcomponent2.getChatStyle().createShallowCopy());
                    list2.add(j + 1, (IChatComponent)chatcomponenttext3);
                }
                i2 = Fonts.robotoBig.getStringWidth(s5);
                chatcomponenttext2 = new ChatComponentText(s5);
                chatcomponenttext2.setChatStyle(ichatcomponent2.getChatStyle().createShallowCopy());
                flag = true;
            }
            if (i + i2 <= p_178908_1_) {
                i += (int)i2;
                ichatcomponent.appendSibling((IChatComponent)chatcomponenttext2);
            }
            else {
                flag = true;
            }
            if (flag) {
                list.add(ichatcomponent);
                i = 0;
                ichatcomponent = (IChatComponent)new ChatComponentText("");
            }
        }
        list.add(ichatcomponent);
        return list;
    }
}
