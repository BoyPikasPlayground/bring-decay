package net.boypika.bring_decay.potion;

import net.boypika.bring_decay.Bring_Decay;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registry;
import net.minecraft.registry.Registries;
import net.minecraft.entity.effect.StatusEffects;



public class ModPotions {
    public static Potion DECAY_POTION;
    public static Potion registerPotion(String name) {
        return Registry.register(Registries.POTION, new Identifier(Bring_Decay.MOD_ID, name),
                new Potion(new StatusEffectInstance(StatusEffects.WITHER, 800, 1)));
    }
    public static void registerPotions() {
        DECAY_POTION = registerPotion("decay_potion");
    }
}
