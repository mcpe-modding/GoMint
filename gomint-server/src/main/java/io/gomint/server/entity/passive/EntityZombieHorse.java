package io.gomint.server.entity.passive;

import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

@RegisterInfo( id = 27 )
public class EntityZombieHorse extends EntityLiving implements io.gomint.entity.passive.EntityZombieHorse {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityZombieHorse( WorldAdapter world ) {
        super( EntityType.ZOMBIE_HORSE, world );
        this.initEntity();
    }

    /**
     * Create new entity zombie horse for API
     */
    public EntityZombieHorse() {
        super( EntityType.ZOMBIE_HORSE, null );
        this.initEntity();
    }

    private void initEntity() {
        this.setSize( 1.3965f, 1.6f );
        this.addAttribute( Attribute.HEALTH );
        this.setMaxHealth( 30 );
        this.setHealth( 30 );
    }

    @Override
    public void update( long currentTimeMS, float dT ) {
        super.update( currentTimeMS, dT );
    }
}
