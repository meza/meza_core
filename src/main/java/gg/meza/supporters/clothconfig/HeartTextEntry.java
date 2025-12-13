package gg.meza.supporters.clothconfig;

/*? if <= 1.21.1 {*/
/*import com.mojang.blaze3d.systems.RenderSystem;
 *//*?}*/

/*? if >= 1.21.6 {*/
import net.minecraft.client.gl.RenderPipelines;
/*?}*/

import me.shedaniel.clothconfig2.api.AbstractConfigListEntry;
import me.shedaniel.clothconfig2.gui.AbstractConfigScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
/*? if >= 1.21 {*/
/*? if >= 1.21.9 {*/
import net.minecraft.client.gui.Click;
/*?}*/
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
/*?} else {*/
/*import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
*//*?}*/
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Optional;

import static gg.meza.core.MezaCore.MOD_ID;

public class HeartTextEntry extends AbstractConfigListEntry<Void> {

    /*? if >= 1.21 {*/
    private static final Identifier ICONS_TEXTURE = Identifier.of(MOD_ID, "textures/heart.png");
    /*?} else {*/
    /*private static final Identifier ICONS_TEXTURE = new Identifier(MOD_ID, "textures/heart.png");
     *//*?}*/

    private final Text message;
    private int lastRenderY;

    public HeartTextEntry(Text message) {
        super(message, false);
        this.message = message;
    }

    @Override
    /*? if >= 1.21*/
    public void render(DrawContext drawContext, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean isHovered, float delta) {
        /*? if < 1.21*/
        /*public void render(MatrixStack drawContext, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean isHovered, float delta) {*/
        super.render(drawContext, index, y, x, entryWidth, entryHeight, mouseX, mouseY, isHovered, delta);
        lastRenderY = y;
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        int heartSize = 9;
        int elementSpacing = 4;
        int textWidth = textRenderer.getWidth(message);

        int totalWidth = heartSize + elementSpacing + textWidth + elementSpacing + heartSize;
        int centerX = x + (entryWidth / 2);
        int startX = centerX - (totalWidth / 2);

        int textY = y + 6;
        int heartY = textY + (9 / 2) - (9 / 2);


        // Left heart
        renderPulsingHeart(drawContext, startX, heartY);

        // Text
        /*? if >= 1.21 {*/
        drawContext.drawTextWithShadow(textRenderer, message, startX + heartSize + elementSpacing, textY, 0xFFFFFFFF);
        /*?} else {*/
        /*textRenderer.drawWithShadow(drawContext, message, startX + heartSize + elementSpacing, textY, 0xFFFFFF);
        *//*?}*/

        if (mouseX >= startX && mouseX <= startX + totalWidth &&
                mouseY >= y && mouseY <= y + entryHeight) {
            /*? if >= 1.21 {*/
            drawContext.drawHoverEvent(textRenderer, message.getStyle(), mouseX, mouseY);
            /*?}*/
        }

        // Right heart
        renderPulsingHeart(drawContext, startX + heartSize + elementSpacing + textWidth + elementSpacing, heartY);
    }

    /*? if >= 1.21.6 {*/
    private void renderPulsingHeart(DrawContext drawContext, int x, int y) {
        int speed = 2000;
        double time = System.currentTimeMillis() % speed;
        float scale = (float) (Math.sin(time / speed * 2 * Math.PI) * 0.1 + 1.0f);
        drawContext.getMatrices().pushMatrix();
        drawContext.getMatrices().translate(x + 4.5f, y + 4.5f);
        drawContext.getMatrices().scale(scale, scale);
        drawContext.getMatrices().translate(-4.5f, -4.5f);
        drawContext.drawTexture(RenderPipelines.GUI_TEXTURED_PREMULTIPLIED_ALPHA, ICONS_TEXTURE, 0, 0, 0, 0, 9, 9, 9, 9);
        drawContext.getMatrices().popMatrix();
    }
    /*?}*/

    /*? if >= 1.21 && <1.21.6 {*/
    /*private void renderPulsingHeart(DrawContext drawContext, int x, int y) {
        int speed = 2000;
        double time = System.currentTimeMillis() % speed;
        float scale = (float) (Math.sin(time / speed * 2 * Math.PI) * 0.1 + 1.0f);

        drawContext.getMatrices().push();
        drawContext.getMatrices().translate(x + 4.5f, y + 4.5f, 0);
        drawContext.getMatrices().scale(scale, scale, 1.0f);
        drawContext.getMatrices().translate(-4.5f, -4.5f, 0);


        /^? if > 1.21.1 {^/
        drawContext.drawTexture(RenderLayer::getGuiTextured, ICONS_TEXTURE, 0, 0, 0, 0, 9, 9, 9, 9);
        /^?}^/

        /^? if =1.21 {^/
        /^RenderSystem.setShaderTexture(0, ICONS_TEXTURE);  // slot 0, your identifier
        ^//^?}^/

        /^? if <=1.21.1 {^/
        /^drawContext.drawTexture(ICONS_TEXTURE, 0, 0, 0, 0, 9, 9, 9, 9);
        ^//^?}^/
        drawContext.getMatrices().pop();
    }
    *//*?}*/

    /*? if < 1.21 {*/
    /*private void renderPulsingHeart(MatrixStack drawContext, int x, int y) {
        int speed = 2000;
        double time = System.currentTimeMillis() % speed;
        float scale = (float) (Math.sin(time / speed * 2 * Math.PI) * 0.1 + 1.0f);
        drawContext.push();
        drawContext.translate(x + 4.5f, y + 4.5f, 0);
        drawContext.scale(scale, scale, 1.0f);
        drawContext.translate(-4.5f, -4.5f, 0);
        RenderSystem.setShaderTexture(0, ICONS_TEXTURE);  // slot 0, your identifier
        DrawableHelper.drawTexture(drawContext, 0, 0, 0, 0, 9, 9, 9, 9);
        drawContext.pop();
    }
    *//*?}*/

    /*? if >= 1.21.9 {*/
    public boolean mouseClicked(Click event, boolean doubleClick) {
    /*?} else {*/
    /*public boolean mouseClicked(double mouseX, double mouseY, int button) {
    *//*?}*/
        if (/*? if >= 1.21.9 {*/event.button()/*?} else {*//*button*//*?}*/ == 0) {
            TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;

            int heartSize = 9;
            int spacing = 4;
            int textWidth = textRenderer.getWidth(message);
            int totalWidth = heartSize + spacing + textWidth + spacing + heartSize;

            int screenWidth = 0;
            if (MinecraftClient.getInstance().currentScreen != null) {
                screenWidth = MinecraftClient.getInstance().currentScreen.width;
            }
            int centerX = screenWidth / 2;
            int startX = centerX - (totalWidth / 2);
            int textX = startX + heartSize + spacing;

            int textY = this.lastRenderY + 6;
            int height = textRenderer.fontHeight;

            // Bounding box of the whole [heart][text][heart] line
            int boundsX = textX;
            int boundsY = textY;
            int boundsW = textWidth;
            int boundsH = height;

            if (/*? if >= 1.21.9 {*/event.x()/*?} else {*//*mouseX*//*?}*/ >= boundsX &&
                /*? if >= 1.21.9 {*/event.x()/*?} else {*//*mouseX*//*?}*/ <= boundsX + boundsW &&
                /*? if >= 1.21.9 {*/event.y()/*?} else {*//*mouseY*//*?}*/ >= boundsY &&
                /*? if >= 1.21.9 {*/event.y()/*?} else {*//*mouseY*//*?}*/ <= boundsY + boundsH) {

                Style style = message.getStyle();
                AbstractConfigScreen configScreen = this.getConfigScreen();
                if (configScreen != null) {
                    /*? if >= 1.21.11 {*/
                    if (style.getClickEvent() != null) {
                        AbstractConfigScreen.handleClickEvent(style.getClickEvent(), MinecraftClient.getInstance(), MinecraftClient.getInstance().currentScreen);
                    }
                    /*?} else {*/
                    /*configScreen.handleTextClick(style);*/
                    /*?}*/
                    return true;
                }
            }
        }
        /*? if >= 1.21.9 {*/
        return super.mouseClicked(event, doubleClick);
        /*?} else {*/
        /*return super.mouseClicked(mouseX, mouseY, button);
        *//*?}*/
    }

    @Override
    public Void getValue() {
        return null;
    }

    @Override
    public Optional<Void> getDefaultValue() {
        return Optional.empty();
    }

    @Override
    public void save() {
    }

    @Override
    public List<? extends Selectable> narratables() {
        return List.of();
    }

    @Override
    public List<? extends Element> children() {
        return List.of();
    }
}


