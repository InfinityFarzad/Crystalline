package net.farzad.crystalline.common.block;

import com.mojang.serialization.MapCodec;
import net.farzad.crystalline.common.init.CrystallineParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.redstone.Orientation;
import org.jetbrains.annotations.Nullable;

public class AmethystLampBlock extends Block {
    public static final MapCodec<AmethystLampBlock> CODEC = simpleCodec(AmethystLampBlock::new);
    public static final BooleanProperty POWERED;
    public static final BooleanProperty LIT;

    protected MapCodec<? extends AmethystLampBlock> codec() {
        return CODEC;
    }

    public AmethystLampBlock(Properties settings) {
        super(settings);
        this.registerDefaultState((BlockState)((BlockState)this.defaultBlockState().setValue(LIT, false)).setValue(POWERED, false));
    }

    protected void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean notify) {
        if (oldState.getBlock() != state.getBlock() && world instanceof ServerLevel serverWorld) {
            this.update(state, serverWorld, pos);
        }

    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
         if (state.getValue(LIT)) {
             world.addParticle(CrystallineParticles.AMETHYST_LAMP_PARTICLE, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0, 0, 0);
             world.addParticle(CrystallineParticles.AMETHYST_LAMP_PARTICLE, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0, 0, 0);
             world.addParticle(CrystallineParticles.AMETHYST_LAMP_PARTICLE, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0, 0, 0);
         }

    }

    protected void neighborChanged(BlockState state, Level world, BlockPos pos, Block sourceBlock, @Nullable Orientation wireOrientation, boolean notify) {
        if (world instanceof ServerLevel serverWorld) {
            this.update(state, serverWorld, pos);
        }
    }

    public void update(BlockState state, ServerLevel world, BlockPos pos) {
        boolean bl = world.hasNeighborSignal(pos);
        if (bl != (Boolean)state.getValue(POWERED)) {
            BlockState blockState = state;
            if (!(Boolean)state.getValue(POWERED)) {
                blockState = (BlockState)state.cycle(LIT);
                world.playSound((Entity)null, pos, (Boolean)blockState.getValue(LIT) ? SoundEvents.COPPER_BULB_TURN_ON : SoundEvents.COPPER_BULB_TURN_OFF, SoundSource.BLOCKS);
            }

            world.setBlock(pos, (BlockState)blockState.setValue(POWERED, bl), 3);
        }
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{LIT, POWERED});
    }

    protected boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    protected int getComparatorOutput(BlockState state, Level world, BlockPos pos) {
        return (Boolean)world.getBlockState(pos).getValue(LIT) ? 15 : 0;
    }

    static {
        POWERED = BlockStateProperties.POWERED;
        LIT = BlockStateProperties.LIT;
    }
}
