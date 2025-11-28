package cc.thonly.base_bridge.neoforge.impl;

import cc.thonly.base_bridge.inf.BrewingRecipeRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;

import java.util.ArrayList;
import java.util.List;

public class BrewingRecipeRegistryNeoForgeImpl implements BrewingRecipeRegistry {
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

    public void registerBrewingEvent(RegisterBrewingRecipesEvent event) {
        PotionBrewing.Builder builder = event.getBuilder();
        RegistryAccess registryAccess = event.getRegistryAccess();
        this.itemRecipes.forEach(itemRecipe -> {
            builder.addRecipe(Ingredient.of(itemRecipe.input()), itemRecipe.ingredient(), itemRecipe.output().getDefaultInstance());
        });
        this.potionRecipes.forEach(potionRecipe -> {
            for (Holder<Item> itemHolder : potionRecipe.ingredient().getValues()) {
                builder.addMix(potionRecipe.input(), itemHolder.value(), potionRecipe.output());
            }
        });
        this.recipes.forEach(recipe -> {
            for (Holder<Item> itemHolder : recipe.ingredient().getValues()) {
                builder.addStartMix(itemHolder.value(), recipe.potion());
            }
        });
    }
}
