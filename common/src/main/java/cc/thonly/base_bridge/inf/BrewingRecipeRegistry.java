package cc.thonly.base_bridge.inf;

import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.crafting.Ingredient;

public interface BrewingRecipeRegistry {

    void registerItemRecipe(Item input, Ingredient ingredient, Item output);

    void registerPotionRecipe(Holder<Potion> input, Ingredient ingredient, Holder<Potion> output);

    void registerRecipes(Ingredient ingredient, Holder<Potion> potion);

    record ItemRecipe(Item input, Ingredient ingredient, Item output) {

    }

    record PotionRecipe(Holder<Potion> input, Ingredient ingredient, Holder<Potion> output) {

    }

    record Recipe(Ingredient ingredient, Holder<Potion> potion) {

    }
}
