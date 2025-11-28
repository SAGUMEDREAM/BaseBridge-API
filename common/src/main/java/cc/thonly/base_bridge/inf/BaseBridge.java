package cc.thonly.base_bridge.inf;

import cc.thonly.base_bridge.impl.Event;
import cc.thonly.base_bridge.inf.c.CommonInfBiomeModification;
import cc.thonly.base_bridge.inf.c.CommonInfBiomeSelectionContext;
import cc.thonly.base_bridge.util.FixedLazyObject;
import cc.thonly.base_bridge.util.LazyObject;
import cc.thonly.base_bridge.util.PlatformType;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.ApiStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

public interface BaseBridge {
    Logger LOGGER = LoggerFactory.getLogger(BaseBridge.class);
    LazyObject<BaseBridge> RET = new FixedLazyObject<>();
    LazyObject<PlatformType> PLATFORM_TYPE = new FixedLazyObject<>(PlatformType.UNDEFINED);
    Set<Block> CUTOUT_BLOCKS = new HashSet<>(128);
    String EARLY_MESSAGE = "You may have accessed it before the bridge loaded, which could cause an error.";

    void addFlammableBlock(Block block, int burn, int spread);

    void addFlammableBlock(TagKey<Block> tag, int burn, int spread);

    void addSupportedBlock(BlockEntityType<?> blockEntityType, Block... block);

    CreativeModeTab registerItemGroup(ResourceKey<CreativeModeTab> key, CreativeModeTab group);

    <T> T register(Registry<T> registry, ResourceKey<T> key, T value);

    <T> Holder<T> registerForHolder(Registry<T> registry, ResourceKey<T> key, T value);

    <T> T register(Registry<T> registry, ResourceLocation id, T value);

    <T> Holder<T> registerForHolder(Registry<T> registry, ResourceLocation id, T value);

    FuelRegistry getFuelRegistry();

    TradeRegistry getTradeRegistry();

    CompostingRegistry getCompostingRegistry();

    CreativeTabRegistry getCreativeTabRegistry();

    LootTableRegistry getLootTableRegistry();

    StrippableBlockRegistry getStrippableBlockRegistry();

    <C extends CommonInfBiomeSelectionContext, M extends CommonInfBiomeModification> BiomeRegistry<C, M> getBiomeRegistry();

    DefaultAttributeRegistry getDefaultAttributeRegistry();

    BrewingRecipeRegistry getBrewingRecipeRegistry();

    ServerContentRegistry getServerContentRegistry();

    @ApiStatus.Internal
    default void registerInternalEvents() {

    }

    @ApiStatus.Internal
    static void setPlatformType(PlatformType platformType) {
        PLATFORM_TYPE.set(platformType);
    }

    static PlatformType getPlatformType() {
        PlatformType platformType = PLATFORM_TYPE.get();
        if (platformType == PlatformType.UNDEFINED) {
            LOGGER.error(EARLY_MESSAGE);
        }
        return platformType;
    }

    static BaseBridge getRet() {
        return RET.get();
    }

    static void setRet(BaseBridge bridge) {
        RET.set(bridge);
    }

    static boolean retLoaded() {
        try {
            return getRet() != null;
        } catch (Throwable e) {
            return false;
        }
    }

    static Set<Block> getCutoutBlock() {
        return CUTOUT_BLOCKS;
    }
}
