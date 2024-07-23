package cn.ksmcbrigade.bk.mixin;

import cn.ksmcbrigade.bk.BossKey;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Inject(method = "stop",at = @At("HEAD"))
    public void onStop(CallbackInfo ci){
        BossKey.tick.stop();
    }
}
