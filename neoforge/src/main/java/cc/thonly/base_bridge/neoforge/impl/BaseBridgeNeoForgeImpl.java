package cc.thonly.base_bridge.neoforge.impl;

import cc.thonly.base_bridge.event.ServerReloadFinishedCallback;
import cc.thonly.base_bridge.impl.ServerContentRegistryImpl;
import cc.thonly.base_bridge.inf.*;
import cc.thonly.base_bridge.inf.c.CommonInfBiomeModification;
import cc.thonly.base_bridge.inf.c.CommonInfBiomeSelectionContext;
import cc.thonly.base_bridge.neoforge.inf.IFireBlock;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.LootTableLoadEvent;
import net.neoforged.neoforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;

import java.util.HashSet;
import java.util.Set;

@SuppressWarnings({"deprecation", "rawtypes", "unchecked"})
public class BaseBridgeNeoForgeImpl implements BaseBridge {
    private boolean loadedEvent = false;
    private final IEventBus eventBus;
    private final Set<FlammableEntry> addedFlammableBlockTagEntries = new HashSet<>(128);
    private final Set<Block> dynamicFlammableBlocks = new HashSet<>(128);
    final FuelRegistry fuelRegistry = new FuelRegistryNeoForgeImpl();
    final TradeRegistry tradeRegistry = new TradeRegistryNeoForgeImpl();
    final CompostingRegistry compostingRegistry = new CompostingRegistryNeoForgeImpl();
    final CreativeTabRegistry creativeTabRegistry = new CreativeTabRegistryNeoForgeImpl();
    final LootTableRegistry lootTableRegistry = new LootTableRegistryNeoForgeImpl();
    final BiomeRegistry biomeRegistry = new BiomeRegistryNeoForgeImpl();
    final DefaultAttributeRegistry defaultAttributeRegistry = new DefaultAttributeRegistryNeoForgeImpl();
    final StrippableBlockRegistry strippableBlockRegistry = new StrippableBlockRegistryNeoForgeImpl();
    final ServerContentRegistry serverContentRegistry = new ServerContentRegistryImpl();

    public BaseBridgeNeoForgeImpl() {
        this.eventBus = NeoForge.EVENT_BUS;
    }

    @Override
    public void addFlammableBlock(Block block, int burn, int spread) {
        FireBlock fire = (FireBlock) Blocks.FIRE;
        fire.setFlammable(block, burn, spread);
    }

    @Override
    public void addFlammableBlock(TagKey<Block> tag, int burn, int spread) {
        this.addedFlammableBlockTagEntries.add(new FlammableEntry(tag, burn, spread));
    }

    @Override
    public void addSupportedBlock(BlockEntityType<?> blockEntityType, Block... block) {
        eventBus.addListener((BlockEntityTypeAddBlocksEvent event) -> {
            event.modify(blockEntityType, block);
        });
    }

    @Override
    public CreativeModeTab registerItemGroup(ResourceKey<CreativeModeTab> key, CreativeModeTab group) {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, key, group);
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
    public LootTableRegistry getLootTableRegistry() {
        return this.lootTableRegistry;
    }

    @Override
    public <C extends CommonInfBiomeSelectionContext, M extends CommonInfBiomeModification> BiomeRegistry<C, M> getBiomeRegistry() {
        return this.biomeRegistry;
    }

    @Override
    public StrippableBlockRegistry getStrippableBlockRegistry() {
        return this.strippableBlockRegistry;
    }

    @Override
    public DefaultAttributeRegistry getDefaultAttributeRegistry() {
        return this.defaultAttributeRegistry;
    }

    @Override
    public ServerContentRegistry getServerContentRegistry() {
        return this.serverContentRegistry;
    }

    @Override
    public void registerEvents() {
        if (this.loadedEvent) {
            return;
        }
        this.loadedEvent = true;
        ServerReloadFinishedCallback.EVENT.register(server -> {
            FireBlock fireBlock = (FireBlock) Blocks.FIRE;
            IFireBlock iFireBlock = (IFireBlock) fireBlock;
            for (Block block : BaseBridgeNeoForgeImpl.this.dynamicFlammableBlocks) {
                iFireBlock.base_bridge$getIgniteOdds().removeInt(block);
                iFireBlock.base_bridge$getBurnOdds().removeInt(block);
            }
            this.dynamicFlammableBlocks.clear();
            RegistryAccess registryAccess = server.registryAccess();
            Registry<Block> blocks = registryAccess.lookupOrThrow(Registries.BLOCK);
            for (FlammableEntry entry : BaseBridgeNeoForgeImpl.this.addedFlammableBlockTagEntries) {
                Iterable<Holder<Block>> tagOrEmpty = blocks.getTagOrEmpty(entry.tag());
                for (Holder<Block> next : tagOrEmpty) {
                    Block block = next.value();
                    BaseBridgeNeoForgeImpl.this.dynamicFlammableBlocks.add(block);
                    fireBlock.setFlammable(block, entry.spread(), entry.burn());
                }
            }
        });
        ServerReloadFinishedCallback.EVENT.register(server -> {
            CompostingRegistryNeoForgeImpl compostingRegistry = (CompostingRegistryNeoForgeImpl) BaseBridgeNeoForgeImpl.this.compostingRegistry;
            Set<Item> addedDynamicItem = compostingRegistry.getAddedDynamicItem();
            Set<Tuple<TagKey<Item>, Float>> addedItemTagEntries = compostingRegistry.getAddedItemTagEntries();
            for (Item item : addedDynamicItem) {
                ComposterBlock.COMPOSTABLES.removeFloat(item);
            }
            addedDynamicItem.clear();
            RegistryAccess registryAccess = server.registryAccess();
            Registry<Item> items = registryAccess.lookupOrThrow(Registries.ITEM);
            for (Tuple<TagKey<Item>, Float> tuple : addedItemTagEntries) {
                TagKey<Item> tagKey = tuple.getA();
                Float chance = tuple.getB();
                Iterable<Holder<Item>> tagOrEmpty = items.getTagOrEmpty(tagKey);
                for (Holder<Item> itemHolder : tagOrEmpty) {
                    ComposterBlock.COMPOSTABLES.put(itemHolder.value(), chance);
                }
            }
        });
        this.eventBus.addListener(EventPriority.HIGHEST, (FurnaceFuelBurnTimeEvent event) -> {
            ((FuelRegistryNeoForgeImpl) this.fuelRegistry).onFurnaceFuelBurnTime(event);
        });
        this.eventBus.addListener(EventPriority.HIGHEST, (VillagerTradesEvent event) -> {
            ((TradeRegistryNeoForgeImpl) this.tradeRegistry).onVillagerTrades(event);
        });
        this.eventBus.addListener(EventPriority.HIGHEST, (BuildCreativeModeTabContentsEvent event) -> {
            ((CreativeTabRegistryNeoForgeImpl) this.creativeTabRegistry).buildContents(event);
        });
        this.eventBus.addListener(EventPriority.HIGHEST, (LootTableLoadEvent event) -> {
            ((LootTableRegistryNeoForgeImpl) this.creativeTabRegistry).onModify(event);
        });

    }

    public record FlammableEntry(TagKey<Block> tag, int burn, int spread) {
    }
}
