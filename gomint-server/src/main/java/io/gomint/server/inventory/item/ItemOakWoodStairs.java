package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 53 )
 public class ItemOakWoodStairs extends ItemStack implements io.gomint.inventory.item.ItemOakWoodStairs {



    @Override
    public ItemType getType() {
        return ItemType.OAK_WOOD_STAIRS;
    }

}
