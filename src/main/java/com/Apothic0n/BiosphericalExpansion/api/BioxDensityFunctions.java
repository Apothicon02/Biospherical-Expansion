package com.Apothic0n.BiosphericalExpansion.api;

import com.Apothic0n.BiosphericalExpansion.BiosphericalExpansion;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import static com.Apothic0n.BiosphericalExpansion.core.BioxMath.progressBetweenInts;

public final class BioxDensityFunctions {
    public static final DeferredRegister<Codec<? extends DensityFunction>> DENSITY_FUNCTION_TYPES = DeferredRegister.create(Registries.DENSITY_FUNCTION_TYPE, BiosphericalExpansion.MODID);
    public static final RegistryObject<Codec<? extends DensityFunction>> TO_HEIGHTMAP_DENSITY_FUNCTION_TYPE = DENSITY_FUNCTION_TYPES.register("to_heightmap", ToHeightmap.CODEC::codec);
    public static void register(IEventBus eventBus) {
        DENSITY_FUNCTION_TYPES.register(eventBus);
    }

    protected record ToHeightmap(int minY, int maxY, DensityFunction input) implements DensityFunction {
        private static final MapCodec<BioxDensityFunctions.ToHeightmap> DATA_CODEC = RecordCodecBuilder.mapCodec((data) -> {
            return data.group(Codec.INT.fieldOf("min_y").forGetter(BioxDensityFunctions.ToHeightmap::minY), Codec.INT.fieldOf("max_y").forGetter(BioxDensityFunctions.ToHeightmap::maxY), DensityFunction.HOLDER_HELPER_CODEC.fieldOf("input").forGetter(BioxDensityFunctions.ToHeightmap::input)).apply(data, BioxDensityFunctions.ToHeightmap::new);
        });
        public static final KeyDispatchDataCodec<BioxDensityFunctions.ToHeightmap> CODEC = BioxDensityFunctions.makeCodec(DATA_CODEC);

        @Override
        public double compute(@NotNull FunctionContext context) {
            double returnValue = 0;
            for (int y = maxY(); y > minY(); y--) {
                double value = input().compute(new SinglePointContext(context.blockX(), y, context.blockZ()));
                if (value > 0) {
                    if (context.blockY() < y-5 && context.blockY() < 60) {
                        returnValue = -1;
                    } else {
                        returnValue = progressBetweenInts(minY(), maxY(), y);
                    }
                    break;
                }
            }
            return returnValue;
        }

        @Override
        public void fillArray(double @NotNull [] densities, ContextProvider context) {
            context.fillAllDirectly(densities, this);
        }

        @Override
        public @NotNull DensityFunction mapAll(Visitor visitor) {
            return visitor.apply(new ToHeightmap(minY(), maxY(), this.input().mapAll(visitor)));
        }

        @Override
        public double minValue() {
            return -1875000d;
        }

        @Override
        public double maxValue() {
            return 1875000d;
        }

        @Override
        public KeyDispatchDataCodec<? extends DensityFunction> codec() {
            return CODEC;
        }
    }

    static <O> KeyDispatchDataCodec<O> makeCodec(MapCodec<O> codec) {
        return KeyDispatchDataCodec.of(codec);
    }
}
