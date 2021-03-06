package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 324 )
public class ItemOakWoodDoor extends ItemStack implements io.gomint.inventory.item.ItemOakWoodDoor {



    @Override
    public int getBlockId() {
        return 64;
    }

    @Override
    public ItemType getType() {
        return ItemType.WOODEN_DOOR;
    }

}
