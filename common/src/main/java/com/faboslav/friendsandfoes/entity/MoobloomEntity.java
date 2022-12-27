package com.faboslav.friendsandfoes.entity;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.api.MoobloomVariant;
import com.faboslav.friendsandfoes.api.MoobloomVariants;
import com.faboslav.friendsandfoes.init.FriendsAndFoesBlocks;
import com.faboslav.friendsandfoes.init.FriendsAndFoesEntityTypes;
import com.faboslav.friendsandfoes.init.FriendsAndFoesSoundEvents;
import com.faboslav.friendsandfoes.util.RandomGenerator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.*;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

public final class MoobloomEntity extends CowEntity implements Shearable
{
	private static final TrackedData<String> VARIANT;

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

	@Override
	public EntityData initialize(
		ServerWorldAccess world,
		LocalDifficulty difficulty,
		SpawnReason spawnReason,
		@Nullable EntityData entityData,
		@Nullable NbtCompound entityNbt
	) {
		if (
			MoobloomVariants.getNumberOfVariants() > 1
			&& spawnReason != SpawnReason.COMMAND) {
			this.setVariant(MoobloomVariants.getRandomVariant());
		}

		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}

	public boolean isShearable() {
		return this.isAlive() && !this.isBaby();
	}

	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(VARIANT, MoobloomVariants.DEFAULT_VARIANT_NAME);
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		nbt.putString("Variant", this.getVariant().getName());
		nbt.putString("Flower", this.getVariant().getFlowerName());
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);

		String variantName = MoobloomVariants.DEFAULT_VARIANT_NAME;

		if (nbt.contains("Variant") && MoobloomVariants.contains(nbt.getString("Variant"))) {
			variantName = nbt.getString("Variant");
		}

		this.setVariant(MoobloomVariants.getByName(variantName));
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
					new ItemStack(this.getVariant().getFlower())
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
		MoobloomVariant moobloomVariant = MoobloomVariants.getByFlowerItem(itemStack.getItem());

		if (moobloomVariant != null && moobloomVariant != this.getVariant()) {
			this.setVariant(moobloomVariant);
			this.playSound(FriendsAndFoesSoundEvents.ENTITY_MOOBLOOM_CONVERT.get(), 2.0F, 1.0F);

			boolean isClientWorld = this.getWorld().isClient();

			if (isClientWorld == false) {
				itemStack.damage(1, player, (playerx) -> {
					playerx.sendToolBreakStatus(hand);
				});
			}

			return ActionResult.success(isClientWorld);
		}

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
		MoobloomVariant moobloomVariant = this.getVariant();
		if (RandomGenerator.generateInt(0, 1) == 0) {
			moobloomVariant = ((MoobloomEntity) entity).getVariant();
		}

		MoobloomEntity moobloom = FriendsAndFoesEntityTypes.MOOBLOOM.get().create(serverWorld);
		moobloom.setVariant(moobloomVariant);

		return moobloom;
	}

	@Override
	public void tickMovement() {
		super.tickMovement();

		if (this.getWorld().isClient() || this.isBaby()) {
			return;
		}

		// On average once per five minutes (1/6000)
		if (this.getRandom().nextFloat() <= 0.00016666666) {
			Block blockUnderneath = this.getWorld().getBlockState(new BlockPos(this.getX(), this.getY() - 1, this.getZ())).getBlock();

			if (blockUnderneath == Blocks.GRASS_BLOCK && this.getWorld().isAir(this.getBlockPos())) {
				Block flower = this.getVariant().getFlower();

				if (MoobloomVariants.getNumberOfVariants() == 1) {
					// 40% chance buttercup, 40% chance dandelion, 20% chance sunflower
					int flowerChance = RandomGenerator.generateInt(1, 100);

					if (flowerChance >= 0 && flowerChance < 40) {
						this.getWorld().setBlockState(this.getBlockPos(), FriendsAndFoesBlocks.BUTTERCUP.get().getDefaultState());
					} else if (flowerChance >= 40 && flowerChance < 80) {
						this.getWorld().setBlockState(this.getBlockPos(), Blocks.DANDELION.getDefaultState());
					} else {
						BlockState sunflowerBlockState = Blocks.SUNFLOWER.getDefaultState().with(TallPlantBlock.HALF, DoubleBlockHalf.UPPER);
						this.getWorld().setBlockState(this.getBlockPos(), sunflowerBlockState.cycle(Properties.DOUBLE_BLOCK_HALF));
						this.getWorld().setBlockState(this.getBlockPos().up(), sunflowerBlockState);
					}
				} else {
					if (flower instanceof TallPlantBlock) {
						BlockState upperHalfBlockState = flower.getDefaultState().with(TallPlantBlock.HALF, DoubleBlockHalf.UPPER);
						this.getWorld().setBlockState(this.getBlockPos(), upperHalfBlockState.cycle(Properties.DOUBLE_BLOCK_HALF));
						this.getWorld().setBlockState(this.getBlockPos().up(), upperHalfBlockState);
					} else {
						this.getWorld().setBlockState(this.getBlockPos(), flower.getDefaultState());
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
		this.dataTracker.set(VARIANT, variant.getName());
	}

	public MoobloomVariant getVariant() {
		return MoobloomVariants.getByName(this.dataTracker.get(VARIANT));
	}

	static {
		VARIANT = DataTracker.registerData(MoobloomEntity.class, TrackedDataHandlerRegistry.STRING);
	}
}