package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 165 )
 public class ItemSlimeBlock extends ItemStack implements io.gomint.inventory.item.ItemSlimeBlock {



    @Override
    public ItemType getType() {
        return ItemType.SLIME_BLOCK;
    }

}
