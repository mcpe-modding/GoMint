package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 60 )
 public class ItemFarmland extends ItemStack implements io.gomint.inventory.item.ItemFarmland {



    @Override
    public ItemType getType() {
        return ItemType.FARMLAND;
    }

}
