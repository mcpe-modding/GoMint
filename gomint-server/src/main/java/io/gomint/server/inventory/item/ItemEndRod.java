package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 208 )
 public class ItemEndRod extends ItemStack implements io.gomint.inventory.item.ItemEndRod {



    @Override
    public ItemType getType() {
        return ItemType.END_ROD;
    }

}
