package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 72 )
 public class ItemWoodenPressurePlate extends ItemStack implements io.gomint.inventory.item.ItemWoodenPressurePlate {



    @Override
    public ItemType getType() {
        return ItemType.WOODEN_PRESSURE_PLATE;
    }

}
