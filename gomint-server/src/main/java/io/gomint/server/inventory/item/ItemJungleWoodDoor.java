package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 429 )
public class ItemJungleWoodDoor extends ItemStack implements io.gomint.inventory.item.ItemJungleWoodDoor {



    @Override
    public int getBlockId() {
        return 195;
    }

    @Override
    public ItemType getType() {
        return ItemType.JUNGLE_DOOR;
    }

}
