package fuzs.bagofholding.network;

import fuzs.bagofholding.world.inventory.BagItemMenu;
import fuzs.bagofholding.world.inventory.LockableInventorySlot;
import fuzs.puzzleslib.api.network.v4.message.MessageListener;
import fuzs.puzzleslib.api.network.v4.message.play.ClientboundPlayMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record ClientboundLockSlotMessage(int containerId, int slotId) implements ClientboundPlayMessage {
    public static final StreamCodec<ByteBuf, ClientboundLockSlotMessage> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.SHORT.map(Short::intValue, Integer::shortValue),
            ClientboundLockSlotMessage::containerId,
            ByteBufCodecs.VAR_INT,
            ClientboundLockSlotMessage::slotId,
            ClientboundLockSlotMessage::new);

    @Override
    public MessageListener<Context> getListener() {
        return new MessageListener<Context>() {
            @Override
            public void accept(Context context) {
                if (context.player().containerMenu instanceof BagItemMenu menu &&
                        menu.containerId == ClientboundLockSlotMessage.this.containerId) {
                    if (menu.getSlot(ClientboundLockSlotMessage.this.slotId) instanceof LockableInventorySlot slot) {
                        slot.lock();
                    }
                }
            }
        };
    }
}
