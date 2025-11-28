package cc.thonly.base_bridge.fabric.impl;

import cc.thonly.base_bridge.impl.ServerContentRegistryImpl;
import cc.thonly.base_bridge.inf.*;
import cc.thonly.base_bridge.inf.c.CommonInfBiomeModification;
import cc.thonly.base_bridge.inf.c.CommonInfBiomeSelectionContext;
import eu.pb4.polymer.core.api.item.PolymerItemGroupUtils;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

@SuppressWarnings({"rawtypes", "unchecked"})
public class BaseBridgeFabricImpl implements BaseBridge {
    private boolean loadedEvent = false;
    final FuelRegistry fuelRegistry = new FuelRegistryFabricImpl();
    final TradeRegistry tradeRegistry = new TradeRegistryFabricImpl();
    final CompostingRegistry compostingRegistry = new CompostingRegistryFabricImpl();
    final CreativeTabRegistry creativeTabRegistry = new CreativeTabRegistryFabricImpl();
    final BiomeRegistry biomeRegistry = new BiomeRegistryFabricImpl();
    final DefaultAttributeRegistry defaultAttributeRegistry = new DefaultAttributeRegistryFabricImpl();
    final LootTableRegistry lootTableRegistry = new LootTableRegistryFabricImpl();
    final StrippableBlockRegistry strippableBlockRegistry = new StrippableBlockRegistryFabricImpl();
    final BrewingRecipeRegistry brewingRecipeRegistry = new BrewingRecipeRegistryFabricImpl();
    final ServerContentRegistry serverContentRegistry = new ServerContentRegistryImpl();

    @Override
    public void addFlammableBlock(Block block, int burn, int spread) {
        FlammableBlockRegistry.getDefaultInstance().add(block, burn, spread);
    }

    @Override
    public void addFlammableBlock(TagKey<Block> tag, int burn, int spread) {
        FlammableBlockRegistry.getDefaultInstance().add(tag, burn, spread);
    }

    @Override
    public void addSupportedBlock(BlockEntityType<?> blockEntity, Block... blocks) {
        for (Block block : blocks) {
            blockEntity.addSupportedBlock(block);
        }
    }

    @Override
    public CreativeModeTab registerItemGroup(ResourceKey<CreativeModeTab> key, CreativeModeTab group) {
        if (FabricLoader.getInstance().isModLoaded("polymer-core")) {
            registerPolymerItemGroup(key, group);
        } else {
            register(BuiltInRegistries.CREATIVE_MODE_TAB, key, group);
        }
        return group;
    }

    @Override
    public <T> T register(Registry<T> registry, ResourceKey<T> key, T value) {
        return Registry.register(registry, key, value);
    }

    @Override
    public <T> Holder.Reference<T> registerForHolder(Registry<T> registry, ResourceKey<T> key, T value) {
        return Registry.registerForHolder(registry, key, value);
    }

    @Override
    public <T> T register(Registry<T> registry, ResourceLocation id, T value) {
        return register(registry, ResourceKey.create(registry.key(), id), value);
    }

    @Override
    public <T> Holder.Reference<T> registerForHolder(Registry<T> registry, ResourceLocation id, T value) {
        return registerForHolder(registry, ResourceKey.create(registry.key(), id), value);
    }

    @Override
    public FuelRegistry getFuelRegistry() {
        return this.fuelRegistry;
    }

    @Override
    public TradeRegistry getTradeRegistry() {
        return this.tradeRegistry;
    }

    @Override
    public CompostingRegistry getCompostingRegistry() {
        return this.compostingRegistry;
    }

    @Override
    public CreativeTabRegistry getCreativeTabRegistry() {
        return this.creativeTabRegistry;
    }

    @Override
    public StrippableBlockRegistry getStrippableBlockRegistry() {
        return this.strippableBlockRegistry;
    }

    @Override
    public LootTableRegistry getLootTableRegistry() {
        return this.lootTableRegistry;
    }

    @Override
    public BrewingRecipeRegistry getBrewingRecipeRegistry() {
        return this.brewingRecipeRegistry;
    }

    @Override
    public ServerContentRegistry getServerContentRegistry() {
        return this.serverContentRegistry;
    }

    @Override
    public <C extends CommonInfBiomeSelectionContext, M extends CommonInfBiomeModification> BiomeRegistry<C, M> getBiomeRegistry() {
        return this.biomeRegistry;
    }

    @Override
    public DefaultAttributeRegistry getDefaultAttributeRegistry() {
        return this.defaultAttributeRegistry;
    }

    @Override
    public void registerInternalEvents() {
        if (this.loadedEvent) {
            return;
        }
        this.loadedEvent = true;
        ((BrewingRecipeRegistryFabricImpl) this.brewingRecipeRegistry).registerInternalEvents();
    }

    void registerPolymerItemGroup(ResourceKey<CreativeModeTab> key, CreativeModeTab group) {
        PolymerItemGroupUtils.registerPolymerItemGroup(key, group);
    }
}
