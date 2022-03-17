package com.faboslav.friendsandfoes.mixin;

import com.faboslav.friendsandfoes.entity.passive.ai.goal.BeePollinateMoobloomGoal;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import com.faboslav.friendsandfoes.init.ModBlockEntityTypes;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BeeEntity.class)
public abstract class BeeEntityMixin extends AnimalEntity implements Angerable
{
	@Shadow
	@Nullable
	private BlockPos hivePos;

	@Shadow
	abstract boolean isHiveValid();

	BeePollinateMoobloomGoal pollinateMoobloomGoal;

	public BeeEntityMixin(
		EntityType<? extends AnimalEntity> entityType,
		World world
	) {
		super(entityType, world);
	}

	@Inject(
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/entity/ai/goal/GoalSelector;add(ILnet/minecraft/entity/ai/goal/Goal;)V",
			ordinal = 4,
			shift = At.Shift.AFTER
		),
		method = "initGoals"
	)
	private void addPollinateMoobloomGoal(CallbackInfo ci) {
		this.pollinateMoobloomGoal = new BeePollinateMoobloomGoal((BeeEntity) (Object) this, (BeeEntityAccessor) this);
		this.goalSelector.add(3, this.pollinateMoobloomGoal);
	}

	@Inject(method = "damage", at = @At("HEAD"), cancellable = true)
	public void damage(
		DamageSource source,
		float amount,
		CallbackInfoReturnable<Boolean> info
	) {
		if (this.isInvulnerableTo(source)) {
			info.setReturnValue(false);
		} else {
			if (!this.world.isClient) {
				this.pollinateMoobloomGoal.cancel();
			}
		}
	}

	@Inject(
		method = "isHiveValid",
		at = @At(
			value = "RETURN",
			ordinal = 1
		),
		cancellable = true
	)
	private void isHiveValid(
		CallbackInfoReturnable<Boolean> cir
	){
		var isHiveValid = cir.getReturnValue();
		if(isHiveValid) {
			cir.setReturnValue(isHiveValid);
		}

		BlockEntity blockEntity = this.world.getBlockEntity(this.hivePos);


		isHiveValid = blockEntity != null && blockEntity.getType() == ModBlockEntityTypes.FRIENDS_AND_FOES_BEEHIVES;
		if(!info.getReturnValueZ() && be instanceof ApiaryBlockEntity) info.setReturnValue(true);
	}
}
