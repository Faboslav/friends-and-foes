package com.faboslav.friendsandfoes.common.entity;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.api.MoobloomVariant;
import com.faboslav.friendsandfoes.common.api.MoobloomVariantManager;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesBlocks;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityTypes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesSoundEvents;
import com.faboslav.friendsandfoes.common.versions.VersionedEntity;
import com.faboslav.friendsandfoes.common.versions.VersionedEntitySpawnReason;
import com.faboslav.friendsandfoes.common.versions.VersionedInteractionResult;
import com.faboslav.friendsandfoes.common.versions.VersionedNbt;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.SuspiciousEffectHolder;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;
import net.minecraft.world.entity.animal.Cow;
import java.util.Optional;

//? if >=1.21.6 {
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
//?} else {
/*import net.minecraft.nbt.CompoundTag;
*///?}

//? if >=1.21.5 {
import net.minecraft.world.entity.animal.AbstractCow;
//?}

//? if >=1.21.3 {
import net.minecraft.world.entity.EntitySpawnReason;
//?} else {
/*import net.minecraft.world.entity.MobSpawnType;
*///?}

//? if >=1.21.5 {
public final class MoobloomEntity extends AbstractCow implements Shearable
//?} else {
/*public final class MoobloomEntity extends Cow implements Shearable
*///?}
{
	public static final String VARIANT_NBT_NAME = "Variant";
	public static final String FLOWER_NBT_NAME = "Flower";

	private static final EntityDataAccessor<String> VARIANT;

	public MoobloomEntity(
		EntityType<? extends MoobloomEntity> entityType,
		Level world
	) {
		super(entityType, world);
	}

	public static boolean canSpawn(
		EntityType<MoobloomEntity> moobloomEntityType,
		ServerLevelAccessor serverWorldAccess,
		/*? if >=1.21.3 {*/
		EntitySpawnReason spawnReason,
		/*?} else {*/
		/*MobSpawnType spawnReason,
		 *//*?}*/
		BlockPos blockPos,
		RandomSource random
	) {
		return serverWorldAccess.getBlockState(blockPos.below()).is(Blocks.GRASS_BLOCK) && isBrightEnoughToSpawn(serverWorldAccess, blockPos);
	}

	@Override
	public SpawnGroupData finalizeSpawn(
		ServerLevelAccessor serverWorldAccess,
		DifficultyInstance difficulty,
		/*? if >=1.21.3 {*/
		EntitySpawnReason spawnReason,
		/*?} else {*/
		/*MobSpawnType spawnReason,
		*//*?}*/
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
	//? if >= 1.21.6 {
	public void addAdditionalSaveData(ValueOutput nbt)
	//?} else {
	/*public void addAdditionalSaveData(CompoundTag nbt)
	*///?}
	{
		super.addAdditionalSaveData(nbt);

		nbt.putString(VARIANT_NBT_NAME, this.getVariant().getName());
		nbt.putString(FLOWER_NBT_NAME, this.getVariant().getFlowerName());
	}

	@Override
	//? if >= 1.21.6 {
	public void readAdditionalSaveData(ValueInput nbt)
	//?} else {
	/*public void readAdditionalSaveData(CompoundTag nbt)
	*///?}
	{
		super.readAdditionalSaveData(nbt);

		MoobloomVariant moobloomVariant = MoobloomVariantManager.MOOBLOOM_VARIANT_MANAGER.getMoobloomVariantByName(VersionedNbt.getString(nbt, VARIANT_NBT_NAME, this.getVariant().getName()));

		if (moobloomVariant == null) {
			moobloomVariant = MoobloomVariantManager.MOOBLOOM_VARIANT_MANAGER.getDefaultMoobloomVariant();
		}

		this.setVariant(moobloomVariant);
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

			if (!this.level().isClientSide()) {
				itemStack.consume(1, player);
			}

			return VersionedInteractionResult.success(this);
		}

		if (itemStack.is(Items.BOWL) && !this.isBaby()) {
			if (!this.level().isClientSide()) {
				ItemStack suspiciousStew;
				SoundEvent soundEvent;
				var stewEffect = this.getEffectsFromItemStack(this.getVariant().getFlowerAsItem().getDefaultInstance());

				if (stewEffect.isPresent()) {
					suspiciousStew = new ItemStack(Items.SUSPICIOUS_STEW);
					suspiciousStew.set(DataComponents.SUSPICIOUS_STEW_EFFECTS, stewEffect.get());
					soundEvent = SoundEvents.MOOSHROOM_MILK_SUSPICIOUSLY;
				} else {
					suspiciousStew = new ItemStack(Items.SUSPICIOUS_STEW);
					suspiciousStew.set(DataComponents.SUSPICIOUS_STEW_EFFECTS, SuspiciousStewEffects.EMPTY);
					soundEvent = SoundEvents.MOOSHROOM_MILK;
				}

				ItemStack itemStack3 = ItemUtils.createFilledResult(itemStack, player, suspiciousStew, false);
				player.setItemInHand(hand, itemStack3);
				this.playSound(soundEvent, 2.0F, 1.0F);
			}

			return InteractionResult.SUCCESS;
		}

		if (itemStack.getItem() == Items.SHEARS && this.readyForShearing()) {
			if(this.level() instanceof ServerLevel serverLevel) {
				this.shear(/*? if >=1.21.3 {*/serverLevel, /*?}*/SoundSource.PLAYERS/*? if >=1.21.3 {*/, itemStack /*?}*/);
				this.gameEvent(GameEvent.SHEAR, player);
				itemStack.hurtAndBreak(1, player, VersionedEntity.getEquipmentSlotForItem(hand));
			}

			return VersionedInteractionResult.success(this);
		} else {
			return super.mobInteract(player, hand);
		}
	}

	@Override
	//? if >=1.21.3 {
	public void shear(ServerLevel level, SoundSource soundSource, ItemStack shears)
	//?} else {
	/*public void shear(SoundSource soundSource)
	*///?}
	{
		//? if <1.21.3 {
		/*ServerLevel level = (ServerLevel) this.level();
		*///?}

		level.playSound(null, this, FriendsAndFoesSoundEvents.ENTITY_MOOBLOOM_SHEAR.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
		this.transformToCow(level);
		this.dropShearedItems(level);
	}

	private void transformToCow(ServerLevel level) {
		this.discard();
		Cow cowEntity = EntityType.COW.create(level/*? if >=1.21.3 {*/, VersionedEntitySpawnReason.CONVERSION/*?}*/);

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
		level.addFreshEntity(cowEntity);
	}

	private void dropShearedItems(ServerLevel level) {
		for (int i = 0; i < 5; ++i) {
			level.addFreshEntity(
				new ItemEntity(
					level,
					this.getX(),
					this.getY(1.0D),
					this.getZ(),
					new ItemStack(this.getVariant().getFlower())
				)
			);
		}
	}

	private Optional<SuspiciousStewEffects> getEffectsFromItemStack(ItemStack stack) {
		SuspiciousEffectHolder suspiciousEffectHolder = SuspiciousEffectHolder.tryGet(stack.getItem());
		return suspiciousEffectHolder != null ? Optional.of(suspiciousEffectHolder.getSuspiciousEffects()) : Optional.empty();
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

		MoobloomEntity moobloom = FriendsAndFoesEntityTypes.MOOBLOOM.get().create(serverWorld/*? if >=1.21.3 {*/, VersionedEntitySpawnReason.BREEDING/*?}*/);
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
		if (!FriendsAndFoes.getConfig().enableMoobloom) {
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