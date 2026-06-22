package com.faboslav.friendsandfoes.common.world.processor;

import com.faboslav.friendsandfoes.common.init.FriendsAndFoesStructureProcessorTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

//? if <26.2 {
/*import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
*///?} else {
import org.jspecify.annotations.Nullable;
//?}

//? if >=26.2 {
public final class CitadelBottomProcessor implements StructureProcessor
//?} else {
/*public final class CitadelBottomProcessor extends StructureProcessor
*///?}
{
	public static final MapCodec<CitadelBottomProcessor> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
		.group(
			BlockState.CODEC.fieldOf("target_block").forGetter(config -> config.targetBlock),
			BlockState.CODEC.fieldOf("target_block_output").forGetter(config -> config.targetBlockOutput),
			Direction.CODEC.optionalFieldOf("direction", Direction.DOWN).forGetter(processor -> processor.direction),
			Codec.INT.optionalFieldOf("pillar_length", -1).forGetter(config -> config.length))
		.apply(instance, instance.stable(CitadelBottomProcessor::new)));

	public final BlockState targetBlock;
	public final BlockState targetBlockOutput;
	public final Direction direction;
	public final int length;

	private CitadelBottomProcessor(
		BlockState targetBlock,
		BlockState targetBlockOutput,
		Direction direction,
		int length
	) {
		this.targetBlock = targetBlock;
		this.targetBlockOutput = targetBlockOutput;
		this.direction = direction;
		this.length = length;
	}

	@Override
	public StructureTemplate.StructureBlockInfo processBlock(
		LevelReader world,
		BlockPos pos,
		BlockPos pivot,
		//? if >=26.2 {
		BlockPos templateRelativePos,
		 //?} else {
		/*StructureTemplate.StructureBlockInfo originalBlockInfo,
		*///?}
		StructureTemplate.StructureBlockInfo currentBlockInfo,
		StructurePlaceSettings structurePlacementData
	) {
		if (currentBlockInfo.state().is(this.targetBlock.getBlock())) {
			if (
				world instanceof WorldGenRegion chunkRegion
				&& !chunkRegion.getCenter().equals(new ChunkPos(SectionPos.blockToSectionCoord(currentBlockInfo.pos().getX()), SectionPos.blockToSectionCoord(currentBlockInfo.pos().getZ())))
			) {
				return currentBlockInfo;
			}

			currentBlockInfo = new StructureTemplate.StructureBlockInfo(
				currentBlockInfo.pos(),
				targetBlockOutput,
				currentBlockInfo.nbt()
			);
			BlockPos.MutableBlockPos mutable = currentBlockInfo.pos().mutable().move(Direction.DOWN);
			BlockState currentBlockState = world.getBlockState(mutable);
			RandomSource random = structurePlacementData.getRandom(currentBlockInfo.pos());

			int worldBottomY;
			int worldTopY;
			//? if >=1.21.3 {
			worldBottomY = world.getMinY();
			worldTopY = world.getMaxY();
			//?} else {
			/*worldBottomY = world.getMinBuildHeight();
			worldTopY = world.getMaxBuildHeight();
			*///?}

			while (
				mutable.getY() > worldBottomY
				&& mutable.getY() < worldTopY
				&& (currentBlockState.isAir() || !world.getFluidState(mutable).isEmpty())
			) {
				//? if >=1.21.5 {
				world.getChunk(mutable).setBlockState(mutable, targetBlockOutput);
				//?} else {
				/*world.getChunk(mutable).setBlockState(mutable, targetBlockOutput, false);
				 *///?}
				mutable.move(Direction.DOWN);
				currentBlockState = world.getBlockState(mutable);
			}
		}

		return currentBlockInfo;
	}

	@Override
	//? if >=26.2 {
	public MapCodec<? extends StructureProcessor> codec() {
		return FriendsAndFoesStructureProcessorTypes.CITADEL_BOTTOM_PROCESSOR.get();
	}
	//?} else {
	/*protected StructureProcessorType<?> getType() {
		return FriendsAndFoesStructureProcessorTypes.CITADEL_BOTTOM_PROCESSOR.get();
	}
	*///?}
}