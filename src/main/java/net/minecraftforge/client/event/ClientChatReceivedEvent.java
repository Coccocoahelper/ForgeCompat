package net.minecraftforge.client.event;

import net.minecraft.text.Text;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public class ClientChatReceivedEvent extends Event {
    public Text message;
    public final byte type;

    public ClientChatReceivedEvent(byte type, Text message) {
        this.type = type;
        this.message = message;
    }
}
