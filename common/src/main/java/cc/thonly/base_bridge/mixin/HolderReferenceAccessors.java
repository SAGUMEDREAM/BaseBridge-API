package cc.thonly.base_bridge.mixin;

import net.minecraft.core.Holder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Holder.Reference.class)
public interface HolderReferenceAccessors<T> {
    @Accessor("value")
    void setValue(T value);
}
