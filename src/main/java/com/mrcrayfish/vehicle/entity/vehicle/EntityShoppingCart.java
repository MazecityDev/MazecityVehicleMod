package com.mrcrayfish.vehicle.entity.vehicle;

import com.mrcrayfish.vehicle.common.CommonEvents;
import com.mrcrayfish.vehicle.entity.EntityVehicle;
import com.mrcrayfish.vehicle.init.ModItems;
import com.mrcrayfish.vehicle.init.ModSounds;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

/**
 * Author: MrCrayfish
 */
public class EntityShoppingCart extends EntityVehicle
{
    private EntityPlayer pusher;

    public EntityShoppingCart(World worldIn)
    {
        super(worldIn);
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();

        if(world.isRemote)
        {
            body = new ItemStack(ModItems.SHOPPING_CART_BODY);
            wheel = new ItemStack(ModItems.WHEEL);
        }
    }

    @Override
    public void onUpdate()
    {
        if(pusher != null)
        {
            this.prevRotationYaw = this.rotationYaw;
            this.prevPosX = this.posX;
            this.prevPosY = this.posY;
            this.prevPosZ = this.posZ;
            float x = MathHelper.sin(-pusher.rotationYaw * 0.017453292F) * 1.3F;
            float z = MathHelper.cos(-pusher.rotationYaw * 0.017453292F) * 1.3F;
            this.posX = pusher.posX + x;
            this.posY = pusher.posY;
            this.posZ = pusher.posZ + z;
            this.lastTickPosX = this.posX;
            this.lastTickPosY = this.posY;
            this.lastTickPosZ = this.posZ;
            this.rotationYaw = pusher.rotationYaw;
        }
        else
        {
            super.onUpdate();
        }
    }

    @Override
    public boolean processInitialInteract(EntityPlayer player, EnumHand hand)
    {
        if(!world.isRemote)
        {
            if(player.isSneaking())
            {
                if(pusher == player)
                {
                    pusher = null;
                    player.getDataManager().set(CommonEvents.PUSHING_CART, false);
                    return true;
                }
                else if(pusher == null)
                {
                    pusher = player;
                    player.getDataManager().set(CommonEvents.PUSHING_CART, true);
                }
            }
            else if(pusher != player)
            {
                super.processInitialInteract(player, hand);
            }

        }
        return true;
    }

    @Override
    public SoundEvent getMovingSound()
    {
        return null;
    }

    @Override
    public SoundEvent getRidingSound()
    {
        return null;
    }

    @Override
    public double getMountedYOffset()
    {
        return 0.0625 * 7.5;
    }

    @Override
    public void dismountRidingEntity()
    {
        super.dismountRidingEntity();
    }
}