package fuzs.bagofholding.network;

import fuzs.bagofholding.world.inventory.BagItemMenu;
import fuzs.bagofholding.world.inventory.LockableInventorySlot;
import fuzs.puzzleslib.api.network.v2.MessageV2;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

public class S2CLockSlotMessage implements MessageV2<S2CLockSlotMessage> {
    private int containerId;
    private int slotId;

    public S2CLockSlotMessage() {

    }

    public S2CLockSlotMessage(int containerId, int slotId) {
        this.containerId = containerId;
        this.slotId = slotId;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeByte(this.containerId);
        buf.writeShort(this.slotId);
    }

    @Override
    public void read(FriendlyByteBuf buf) {
        this.containerId = buf.readByte();
        this.slotId = buf.readShort();
    }

    @Override
    public MessageHandler<S2CLockSlotMessage> makeHandler() {
        return new MessageHandler<>() {

            @Override
            public void handle(S2CLockSlotMessage message, Player player, Object gameInstance) {
                if (player.containerMenu.containerId == message.containerId && player.containerMenu instanceof BagItemMenu menu) {
                    ((LockableInventorySlot) menu.getSlot(message.slotId)).lock();
                }
            }
        };
    }
}
