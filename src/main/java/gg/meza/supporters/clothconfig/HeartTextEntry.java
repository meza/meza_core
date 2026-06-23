package gg.meza.supporters.clothconfig;

import me.shedaniel.clothconfig2.api.AbstractConfigListEntry;
import me.shedaniel.clothconfig2.gui.AbstractConfigScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.Identifier;

import java.util.List;
import java.util.Optional;

import static gg.meza.core.MezaCore.MOD_ID;

public class HeartTextEntry extends AbstractConfigListEntry<Void> {

    private static final Identifier ICONS_TEXTURE = Identifier.fromNamespaceAndPath(MOD_ID, "textures/heart.png");

    private final Component message;
    private int lastRenderY;

    public HeartTextEntry(Component message) {
        super(message, false);
        this.message = message;
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor drawContext, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean isHovered, float delta) {
        super.extractRenderState(drawContext, index, y, x, entryWidth, entryHeight, mouseX, mouseY, isHovered, delta);
        lastRenderY = y;
        Font textRenderer = Minecraft.getInstance().font;
        int heartSize = 9;
        int elementSpacing = 4;
        int textWidth = textRenderer.width(message);

        int totalWidth = heartSize + elementSpacing + textWidth + elementSpacing + heartSize;
        int centerX = x + (entryWidth / 2);
        int startX = centerX - (totalWidth / 2);

        int textY = y + 6;
        int heartY = textY + (9 / 2) - (9 / 2);


        // Left heart
        renderPulsingHeart(drawContext, startX, heartY);

        // Text
        drawContext.text(textRenderer, message, startX + heartSize + elementSpacing, textY, 0xFFFFFFFF);

        if (mouseX >= startX && mouseX <= startX + totalWidth &&
                mouseY >= y && mouseY <= y + entryHeight) {
            if (message.getStyle().getHoverEvent() instanceof HoverEvent.ShowText hoverEvent) {
                drawContext.setTooltipForNextFrame(textRenderer, hoverEvent.value(), mouseX, mouseY);
            }
        }

        // Right heart
        renderPulsingHeart(drawContext, startX + heartSize + elementSpacing + textWidth + elementSpacing, heartY);
    }

    private void renderPulsingHeart(GuiGraphicsExtractor drawContext, int x, int y) {
        int speed = 2000;
        double time = System.currentTimeMillis() % speed;
        float scale = (float) (Math.sin(time / speed * 2 * Math.PI) * 0.1 + 1.0f);
        drawContext.pose().pushMatrix();
        drawContext.pose().translate(x + 4.5f, y + 4.5f);
        drawContext.pose().scale(scale, scale);
        drawContext.pose().translate(-4.5f, -4.5f);
        drawContext.blit(RenderPipelines.GUI_TEXTURED_PREMULTIPLIED_ALPHA, ICONS_TEXTURE, 0, 0, 0, 0, 9, 9, 9, 9);
        drawContext.pose().popMatrix();
    }

    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        if (event.button() == 0) {
            Font textRenderer = Minecraft.getInstance().font;

            int heartSize = 9;
            int spacing = 4;
            int textWidth = textRenderer.width(message);
            int totalWidth = heartSize + spacing + textWidth + spacing + heartSize;

            int screenWidth = 0;
            if (Minecraft.getInstance().gui.screen() != null) {
                screenWidth = Minecraft.getInstance().gui.screen().width;
            }
            int centerX = screenWidth / 2;
            int startX = centerX - (totalWidth / 2);
            int textX = startX + heartSize + spacing;

            int textY = this.lastRenderY + 6;
            int height = textRenderer.lineHeight;

            // Bounding box of the whole [heart][text][heart] line
            int boundsX = textX;
            int boundsY = textY;
            int boundsW = textWidth;
            int boundsH = height;

            if (event.x() >= boundsX &&
                    event.x() <= boundsX + boundsW &&
                    event.y() >= boundsY &&
                    event.y() <= boundsY + boundsH) {

                Style style = message.getStyle();
                AbstractConfigScreen configScreen = this.getConfigScreen();
                if (configScreen != null) {

                    if (style.getClickEvent() != null) {
                        AbstractConfigScreen.handleClickEvent(style.getClickEvent(), Minecraft.getInstance(), Minecraft.getInstance().gui.screen());
                    }

                    return true;
                }
            }
        }
        return super.mouseClicked(event, doubleClick);
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
    public List<? extends NarratableEntry> narratables() {
        return List.of();
    }

    @Override
    public List<? extends GuiEventListener> children() {
        return List.of();
    }
}

