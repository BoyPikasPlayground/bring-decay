package net.boypika.bring_decay.mixin;

import net.boypika.bring_decay.Bring_Decay;
import net.boypika.bring_decay.potion.ModPotions;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.WitchEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(WitchEntity.class)
public class WitchEntityMixin {

    public void attack(LivingEntity target, float pullProgress) {
        WitchEntity witchEntity = (WitchEntity) (Object) this;
        if (!witchEntity.isDrinking()) {
            Vec3d vec3d = target.getVelocity();
            double d = target.getX() + vec3d.x - witchEntity.getX();
            double e = target.getEyeY() - 1.100000023841858 - witchEntity.getY();
            double f = target.getZ() + vec3d.z - witchEntity.getZ();
            double g = Math.sqrt(d * d + f * f);
            Potion potion = Potions.HARMING;
            if (target instanceof RaiderEntity) {
                if (target.getHealth() <= 4.0F) {
                    potion = Potions.HEALING;
                } else {
                    potion = Potions.REGENERATION;
                }

                witchEntity.setTarget((LivingEntity)null);
            } else if (g >= 8.0 && !target.hasStatusEffect(StatusEffects.SLOWNESS)) {
                potion = Potions.SLOWNESS;
            } else if (target.getHealth() >= Bring_Decay.tulipInstance.getFloat("min_health_for_decay_from_witch") && !target.hasStatusEffect(StatusEffects.WITHER) && Bring_Decay.tulipInstance.getBoolean("witch_throw_wither")) {
                potion = ModPotions.DECAY_POTION;
            } else if (!Bring_Decay.tulipInstance.getBoolean("witch_throw_wither") && target.getHealth() >= 8.0F && !target.hasStatusEffect(StatusEffects.POISON)) {
                potion = Potions.POISON;
            } else if (g <= 3.0 && !target.hasStatusEffect(StatusEffects.WEAKNESS) && witchEntity.getRandom().nextFloat() < 0.25F) {
                potion = Potions.WEAKNESS;
            }

            PotionEntity potionEntity = new PotionEntity(witchEntity.world, witchEntity);
            potionEntity.setItem(PotionUtil.setPotion(new ItemStack(Items.SPLASH_POTION), potion));
            potionEntity.setPitch(potionEntity.getPitch() - -20.0F);
            potionEntity.setVelocity(d, e + g * 0.2, f, 0.75F, 8.0F);
            if (!witchEntity.isSilent()) {
                witchEntity.world.playSound((PlayerEntity)null, witchEntity.getX(), witchEntity.getY(), witchEntity.getZ(), SoundEvents.ENTITY_WITCH_THROW, witchEntity.getSoundCategory(), 1.0F, 0.8F + witchEntity.getRandom().nextFloat() * 0.4F);
            }

            witchEntity.world.spawnEntity(potionEntity);
        }
    }
}
