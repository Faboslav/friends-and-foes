package com.faboslav.friendsandfoes.common.entity;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.api.MoobloomVariant;
import com.faboslav.friendsandfoes.common.api.MoobloomVariantManager;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesBlocks;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityTypes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Shearable;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;

public final class MoobloomEntity extends Cow implements Shearable
{
	public static final String VARIANT_NBT_NAME = "Variant";
	public static final String FLOWER_NBT_NAME = "Flower";

	private static final EntityDataAccessor<String> VARIANT;

	public MoobloomEntity(
		EntityType<? extends Cow> entityType,
		Level world
	) {
		super(entityType, world);
	}

	public static boolean canSpawn(
		EntityType<MoobloomEntity> moobloomEntityType,
		ServerLevelAccessor serverWorldAccess,
		MobSpawnType spawnReason,
		BlockPos blockPos,
		RandomSource random
	) {
		return serverWorldAccess.getBlockState(blockPos.below()).is(Blocks.GRASS_BLOCK) && isBrightEnoughToSpawn(serverWorldAccess, blockPos);
	}

	@Override
	public SpawnGroupData finalizeSpawn(
		ServerLevelAccessor serverWorldAccess,
		DifficultyInstance difficulty,
		MobSpawnType spawnReason,
		@Nullable SpawnGroupData entityData
	) {
		MoobloomVariant possibleMoobloomVariant = MoobloomVariantManager.MOOBLOOM_VARIANT_MANAGER.getRandomBiomeSpecificMoobloomVariant(serverWorldAccess, this.blockPosition());

		if (possibleMoobloomVariant != null) {
			this.setVariant(possibleMoobloomVariant);
		} else {
			this.setVariant(MoobloomVariantManager.MOOBLOOM_VARIANT_MANAGER.getRandomMoobloomVariant(serverWorldAccess.getRandom()));
		}

		return super.finalizeSpawn(serverWorldAccess, difficulty, spawnReason, entityData);
	}

	public boolean readyForShearing() {
		return this.isAlive() && !this.isBaby();
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);

		builder.define(VARIANT, MoobloomVariantManager.MOOBLOOM_VARIANT_MANAGER.getDefaultMoobloomVariant().getName());
	}

	@Override
	public void addAdditionalSaveData(CompoundTag nbt) {
		super.addAdditionalSaveData(nbt);

		nbt.putString(VARIANT_NBT_NAME, this.getVariant().getName());
		nbt.putString(FLOWER_NBT_NAME, this.getVariant().getFlowerName());
	}

	@Override
	public void readAdditionalSaveData(CompoundTag nbt) {
		super.readAdditionalSaveData(nbt);

		MoobloomVariant moobloomVariant = MoobloomVariantManager.MOOBLOOM_VARIANT_MANAGER.getMoobloomVariantByName(nbt.getString(VARIANT_NBT_NAME));

		if (moobloomVariant == null) {
			moobloomVariant = MoobloomVariantManager.MOOBLOOM_VARIANT_MANAGER.getDefaultMoobloomVariant();
		}

		this.setVariant(moobloomVariant);
	}

	public void shear(SoundSource shearedSoundCategory) {
		Level world = this.level();

		world.playSound(null, this, SoundEvents.MOOSHROOM_SHEAR, shearedSoundCategory, 1.0F, 1.0F);

		if (world.isClientSide()) {
			return;
		}

		((ServerLevel) world).sendParticles(ParticleTypes.EXPLOSION, this.getX(), this.getY(0.5D), this.getZ(), 1, 0.0D, 0.0D, 0.0D, 0.0D);
		this.discard();
		Cow cowEntity = EntityType.COW.create(world);

		if (cowEntity == null) {
			return;
		}

		cowEntity.setHealth(this.getHealth());
		cowEntity.copyPosition(this);
		cowEntity.yBodyRotO = this.yBodyRotO;
		cowEntity.yBodyRot = this.yBodyRot;
		cowEntity.yHeadRotO = this.yHeadRotO;
		cowEntity.yHeadRot = this.yHeadRot;

		if (this.hasCustomName()) {
			cowEntity.setCustomName(this.getCustomName());
			cowEntity.setCustomNameVisible(this.isCustomNameVisible());
		}

		if (this.isPersistenceRequired()) {
			cowEntity.setPersistenceRequired();
		}

		cowEntity.setInvulnerable(this.isInvulnerable());
		world.addFreshEntity(cowEntity);

		for (int i = 0; i < 5; ++i) {
			world.addFreshEntity(
				new ItemEntity(
					world,
					this.getX(),
					this.getY(1.0D),
					this.getZ(),
					new ItemStack(this.getVariant().getFlower())
				)
			);
		}
	}

	@Override
	public InteractionResult mobInteract(
		Player player,
		InteractionHand hand
	) {
		ItemStack itemStack = player.getItemInHand(hand);
		MoobloomVariant moobloomVariant = MoobloomVariantManager.MOOBLOOM_VARIANT_MANAGER.getByFlowerItem(itemStack.getItem());

		if (moobloomVariant != null && moobloomVariant != this.getVariant()) {
			this.setVariant(moobloomVariant);
			this.playSound(FriendsAndFoesSoundEvents.ENTITY_MOOBLOOM_CONVERT.get(), 2.0F, 1.0F);

			boolean isClientWorld = this.level().isClientSide();

			if (isClientWorld == false) {
				itemStack.consume(1, player);
			}

			return InteractionResult.sidedSuccess(isClientWorld);
		}

		if (itemStack.getItem() == Items.SHEARS && this.readyForShearing()) {
			this.shear(SoundSource.PLAYERS);
			this.gameEvent(GameEvent.SHEAR, player);

			boolean isClientWorld = this.level().isClientSide();

			if (isClientWorld == false) {
				itemStack.hurtAndBreak(1, player, Player.getSlotForHand(hand));
			}

			return InteractionResult.sidedSuccess(isClientWorld);
		} else {
			return super.mobInteract(player, hand);
		}
	}

	@Override
	public MoobloomEntity getBreedOffspring(
		ServerLevel serverWorld,
		AgeableMob entity
	) {
		MoobloomVariant moobloomVariant = this.getVariant();
		if (this.getRandom().nextIntBetweenInclusive(0, 1) == 0) {
			moobloomVariant = ((MoobloomEntity) entity).getVariant();
		}

		MoobloomEntity moobloom = FriendsAndFoesEntityTypes.MOOBLOOM.get().create(serverWorld);
		moobloom.setVariant(moobloomVariant);

		return moobloom;
	}

	@Override
	public void aiStep() {
		super.aiStep();

		if (this.level().isClientSide() || this.isBaby()) {
			return;
		}

		// On average once per five minutes (1/6000)
		if (this.getRandom().nextFloat() <= 0.00016666666) {
			Block blockUnderneath = this.level().getBlockState(
				new BlockPos(
					(int) this.getX(),
					(int) this.getY() - 1,
					(int) this.getZ()
				)
			).getBlock();

			if (blockUnderneath == Blocks.GRASS_BLOCK && this.level().isEmptyBlock(this.blockPosition())) {
				Block flower = this.getVariant().getFlower();

				if (MoobloomVariantManager.MOOBLOOM_VARIANT_MANAGER.getMoobloomVariants().size() == 1) {
					// 40% chance buttercup, 40% chance dandelion, 20% chance sunflower
					int flowerChance = this.getRandom().nextIntBetweenInclusive(1, 100);

					if (flowerChance >= 0 && flowerChance < 40) {
						this.level().setBlockAndUpdate(this.blockPosition(), FriendsAndFoesBlocks.BUTTERCUP.get().defaultBlockState());
					} else if (flowerChance >= 40 && flowerChance < 80) {
						this.level().setBlockAndUpdate(this.blockPosition(), Blocks.DANDELION.defaultBlockState());
					} else {
						BlockState sunflowerBlockState = Blocks.SUNFLOWER.defaultBlockState().setValue(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER);
						this.level().setBlockAndUpdate(this.blockPosition(), sunflowerBlockState.cycle(BlockStateProperties.DOUBLE_BLOCK_HALF));
						this.level().setBlockAndUpdate(this.blockPosition().above(), sunflowerBlockState);
					}
				} else {
					if (flower instanceof DoublePlantBlock) {
						BlockState upperHalfBlockState = flower.defaultBlockState().setValue(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER);
						this.level().setBlockAndUpdate(this.blockPosition(), upperHalfBlockState.cycle(BlockStateProperties.DOUBLE_BLOCK_HALF));
						this.level().setBlockAndUpdate(this.blockPosition().above(), upperHalfBlockState);
					} else {
						this.level().setBlockAndUpdate(this.blockPosition(), flower.defaultBlockState());
					}
				}
			}
		}
	}

	@Override
	public void tick() {
		if (FriendsAndFoes.getConfig().enableMoobloom == false) {
			this.discard();
		}

		super.tick();
	}

	private void setVariant(MoobloomVariant variant) {
		this.entityData.set(VARIANT, variant.getName());
	}

	public MoobloomVariant getVariant() {
		MoobloomVariant moobloomVariant = MoobloomVariantManager.MOOBLOOM_VARIANT_MANAGER.getMoobloomVariantByName(this.entityData.get(VARIANT));

		if (moobloomVariant == null) {
			moobloomVariant = MoobloomVariantManager.MOOBLOOM_VARIANT_MANAGER.getDefaultMoobloomVariant();
		}

		return moobloomVariant;
	}

	static {
		VARIANT = SynchedEntityData.defineId(MoobloomEntity.class, EntityDataSerializers.STRING);
	}
}