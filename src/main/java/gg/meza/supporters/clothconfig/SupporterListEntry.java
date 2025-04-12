package gg.meza.supporters.clothconfig;

import me.shedaniel.clothconfig2.gui.entries.TextListEntry;
import net.minecraft.text.Text;

import java.util.UUID;

public class SupporterListEntry extends TextListEntry {
    public SupporterListEntry(Text text) {
        super(Text.literal(UUID.randomUUID().toString()), text);
    }

    @Override
    public boolean isMouseInside(int mouseX, int mouseY, int x, int y, int entryWidth, int entryHeight) {
        return false;
    }
}
