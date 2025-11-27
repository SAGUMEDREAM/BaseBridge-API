package cc.thonly.base_bridge.fabric;

import cc.thonly.base_bridge.impl.Event;
import cc.thonly.base_bridge.impl.EventFactory;
import cc.thonly.base_bridge.inf.BaseBridge;
import cc.thonly.base_bridge.inf.LootTableRegistry;
import cc.thonly.base_bridge.inf.ServerContentRegistry;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.dialog.*;
import net.minecraft.server.dialog.body.PlainMessage;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Test implements ModInitializer {
    @Override
    public void onInitialize() {
        interface TestInf1 {
            void run();
        }
        interface TestInf2 {
            int getNumber();
        }
        Event<TestInf1> event1 = EventFactory.createArrayBacked(TestInf1.class, (listener) -> () -> {
            for (var callback : listener) {
                callback.run();
            }
        });
        event1.register(() -> System.out.println(111));
        event1.register(() -> System.out.println(222));
        event1.register(() -> System.out.println(333));
        event1.invoker().run();

        Event<TestInf2> event2 = EventFactory.createArrayBacked(TestInf2.class, (listener) -> () -> {
            for (var callback : listener) {
                int number = callback.getNumber();
                System.out.println(number);
            }
            return 0;
        });
        event2.register(() -> 0);
        event2.register(() -> 1);
        event2.register(() -> 2);
        event2.register(() -> 3);
        event2.invoker().getNumber();

        LootTableRegistry lootTableRegistry = BaseBridge.getRet().getLootTableRegistry();
        lootTableRegistry.addReplace((key, lootTable) -> {
            Optional<ResourceKey<LootTable>> lootTableKey = Blocks.STONE.getLootTable();
            if (lootTableKey.isPresent() && key == lootTableKey.get()) {
                LootPool.Builder poolBuilder = LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.DIAMOND));
                LootTable.Builder newTableBuilder = LootTable.lootTable();
                newTableBuilder.withPool(poolBuilder);
                return newTableBuilder.build();
            }
            return lootTable;
        });
        ServerContentRegistry serverContentRegistry = BaseBridge.getRet().getServerContentRegistry();
        serverContentRegistry.register(Registries.DIALOG,
                ResourceLocation.withDefaultNamespace("test"),
                new MultiActionDialog(
                        new CommonDialogData(
                                Component.literal("Test Title"),
                                Optional.of(Component.empty().append("Sub Title")),
                                true,
                                false,
                                DialogAction.CLOSE,
                                List.of(new PlainMessage(
                                        Component.literal("Hello World\nThis is an example text"),
                                        350
                                )),
                                List.of()
                        ),
                        List.of(new ActionButton(new CommonButtonData(Component.literal("Click Me!"), Optional.of(Component.literal("tooltip")), 300), Optional.empty())),
                        Optional.empty(),
                        1
                        )
        );
    }
}
