package cc.thonly.base_bridge.fabric.impl;

import cc.thonly.base_bridge.inf.BrewingRecipeRegistry;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class BrewingRecipeRegistryFabricImpl implements BrewingRecipeRegistry {
    private final List<ItemRecipe> itemRecipes = new ArrayList<>(128);
    private final List<PotionRecipe> potionRecipes = new ArrayList<>(128);
    private final List<Recipe> recipes = new ArrayList<>(128);

    @Override
    public void registerItemRecipe(Item input, Ingredient ingredient, Item output) {
        this.itemRecipes.add(new ItemRecipe(input, ingredient, output));
    }

    @Override
    public void registerPotionRecipe(Holder<Potion> input, Ingredient ingredient, Holder<Potion> output) {
        this.potionRecipes.add(new PotionRecipe(input, ingredient, output));
    }

    @Override
    public void registerRecipes(Ingredient ingredient, Holder<Potion> potion) {
        this.recipes.add(new Recipe(ingredient, potion));
    }

    public void registerInternalEvents() {
        FabricBrewingRecipeRegistryBuilder.BUILD.register(builder -> {
            this.itemRecipes.forEach(itemRecipe -> {
                builder.registerItemRecipe(itemRecipe.input(), itemRecipe.ingredient(), itemRecipe.output());
            });
            this.potionRecipes.forEach(potionRecipe -> {
                builder.registerPotionRecipe(potionRecipe.input(), potionRecipe.ingredient(), potionRecipe.output());
            });
            this.recipes.forEach(recipe -> {
                builder.registerRecipes(recipe.ingredient(), recipe.potion());
            });
        });
    }
}
