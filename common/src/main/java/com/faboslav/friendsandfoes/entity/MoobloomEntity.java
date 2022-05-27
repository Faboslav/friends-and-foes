package com.faboslav.friendsandfoes.entity;

import com.faboslav.friendsandfoes.init.ModBlocks;
import com.faboslav.friendsandfoes.init.ModEntity;
import com.faboslav.friendsandfoes.util.RandomGenerator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.Shearable;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.Random;

public final class MoobloomEntity extends CowEntity implements Shearable
{
	public MoobloomEntity(
		EntityType<? extends CowEntity> entityType,
		World world
	) {
		super(entityType, world);
	}

	public static boolean canSpawn(
		EntityType<MoobloomEntity> moobloomEntityType,
		ServerWorldAccess serverWorldAccess,
		SpawnReason spawnReason,
		BlockPos blockPos,
		Random random
	) {
		return serverWorldAccess.getBlockState(blockPos.down()).isOf(Blocks.GRASS_BLOCK) && isLightLevelValidForNaturalSpawn(serverWorldAccess, blockPos);
	}

	public boolean isShearable() {
		return this.isAlive() && !this.isBaby();
	}

	public void sheared(SoundCategory shearedSoundCategory) {
		World world = this.getWorld();

		world.playSoundFromEntity(null, this, SoundEvents.ENTITY_MOOSHROOM_SHEAR, shearedSoundCategory, 1.0F, 1.0F);

		if (world.isClient()) {
			return;
		}

		((ServerWorld) world).spawnParticles(ParticleTypes.EXPLOSION, this.getX(), this.getBodyY(0.5D), this.getZ(), 1, 0.0D, 0.0D, 0.0D, 0.0D);
		this.discard();
		CowEntity cowEntity = EntityType.COW.create(world);

		if (cowEntity == null) {
			return;
		}

		cowEntity.setHealth(this.getHealth());
		cowEntity.copyPositionAndRotation(this);
		cowEntity.prevBodyYaw = this.prevBodyYaw;
		cowEntity.bodyYaw = this.bodyYaw;
		cowEntity.prevHeadYaw = this.prevHeadYaw;
		cowEntity.headYaw = this.headYaw;

		if (this.hasCustomName()) {
			cowEntity.setCustomName(this.getCustomName());
			cowEntity.setCustomNameVisible(this.isCustomNameVisible());
		}

		if (this.isPersistent()) {
			cowEntity.setPersistent();
		}

		cowEntity.setInvulnerable(this.isInvulnerable());
		world.spawnEntity(cowEntity);

		for (int i = 0; i < 5; ++i) {
			world.spawnEntity(
				new ItemEntity(
					world,
					this.getX(),
					this.getBodyY(1.0D),
					this.getZ(),
					new ItemStack(ModBlocks.BUTTERCUP.get())
				)
			);
		}
	}

	@Override
	public ActionResult interactMob(
		PlayerEntity player,
		Hand hand
	) {
		ItemStack itemStack = player.getStackInHand(hand);

		if (itemStack.getItem() == Items.SHEARS && this.isShearable()) {
			this.sheared(SoundCategory.PLAYERS);
			this.emitGameEvent(GameEvent.SHEAR, player);

			boolean isClientWorld = this.getWorld().isClient();

			if (isClientWorld == false) {
				itemStack.damage(1, player, (playerx) -> {
					playerx.sendToolBreakStatus(hand);
				});
			}

			return ActionResult.success(isClientWorld);
		} else {
			return super.interactMob(player, hand);
		}
	}

	@Override
	public MoobloomEntity createChild(
		ServerWorld serverWorld,
		PassiveEntity entity
	) {
		return ModEntity.MOOBLOOM.get().create(serverWorld);
	}

	@Override
	public void tickMovement() {
		if (this.getWorld().isClient() || this.isBaby()) {
			return;
		}

		// On average once per five minutes (1/6000)
		if (this.getRandom().nextFloat() <= 0.00016666666) {
			Block blockUnderneath = this.world.getBlockState(new BlockPos(this.getX(), this.getY() - 1, this.getZ())).getBlock();

			if (blockUnderneath == Blocks.GRASS_BLOCK && this.world.isAir(this.getBlockPos())) {
				// 40% chance buttercup, 40% chance dandelion, 20% chance sunflower
				int flowerChance = RandomGenerator.generateInt(1, 100);

				if (flowerChance >= 0 && flowerChance < 40) {
					this.world.setBlockState(this.getBlockPos(), ModBlocks.BUTTERCUP.get().getDefaultState());
				} else if (flowerChance >= 40 && flowerChance < 80) {
					this.world.setBlockState(this.getBlockPos(), Blocks.DANDELION.getDefaultState());
				} else {
					BlockState sunflowerBlockState = Blocks.SUNFLOWER.getDefaultState().with(TallPlantBlock.HALF, DoubleBlockHalf.UPPER);
					this.world.setBlockState(this.getBlockPos(), sunflowerBlockState.cycle(Properties.DOUBLE_BLOCK_HALF));
					this.world.setBlockState(this.getBlockPos().up(), sunflowerBlockState);
				}
			}
		}

		super.tickMovement();
	}
}