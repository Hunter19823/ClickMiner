package pie.ilikepiefoo2.clickminer.util;

import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.text.*;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;

import java.util.List;

public class TextHelper {
    public IFormattableTextComponent output = (IFormattableTextComponent) ITextComponent.getTextComponentOrEmpty("");
    private IFormattableTextComponent lastComponent = output;
    private Style lastStyle = Style.EMPTY;
    public TextHelper empty(){
        lastStyle = Style.EMPTY;
        return this;
    }
    public TextHelper color(Color color){
        lastStyle = lastStyle.setColor(color);
        return this;
    }
    public TextHelper bold(boolean bold){
        lastStyle = lastStyle.setBold(bold);
        return this;
    }
    public TextHelper italic(boolean italics){
        lastStyle = lastStyle.setItalic(italics);
        return this;
    }
    public TextHelper underline(boolean underline){
        lastStyle = lastStyle.setUnderlined(underline);
        return this;
    }
    public TextHelper strikethrough(boolean strikethrough){
        lastStyle = lastStyle.setStrikethrough(strikethrough);
        return this;
    }
    public TextHelper click(ClickEvent event){
        lastStyle = lastStyle.setClickEvent(event);
        return this;
    }
    public TextHelper hover(HoverEvent event){
        lastStyle = lastStyle.setHoverEvent(event);
        return this;
    }
    public TextHelper add(String text){
        lastComponent = (IFormattableTextComponent) ITextComponent.getTextComponentOrEmpty(text);
        output.append(lastComponent.mergeStyle(lastStyle));
        return this;
    }
    public TextHelper add(IFormattableTextComponent text){
        lastComponent = text;
        output.append(lastComponent.mergeStyle(lastStyle));
        return this;
    }

    public IFormattableTextComponent getOutput()
    {
        return output;
    }
}
