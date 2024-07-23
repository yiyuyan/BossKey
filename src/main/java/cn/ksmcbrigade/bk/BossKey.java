package cn.ksmcbrigade.bk;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod("bk")
@Mod.EventBusSubscriber(modid = "bk",value = Dist.CLIENT)
public class BossKey {

    public static final KeyMapping bossKey = new KeyMapping(KeyMapping.CATEGORY_MISC, KeyConflictContext.UNIVERSAL,  InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_N, "key.bk.bk");
    public static final KeyMapping quitKey = new KeyMapping(KeyMapping.CATEGORY_MISC, KeyConflictContext.UNIVERSAL, InputConstants.Type.KEYSYM,GLFW.GLFW_KEY_END, "key.bk.fq");
    public static final KeyMapping closeKey = new KeyMapping(KeyMapping.CATEGORY_MISC, KeyConflictContext.UNIVERSAL, InputConstants.Type.KEYSYM,GLFW.GLFW_KEY_DELETE, "key.bk.sq");

    public static double music = -1d;

    public static boolean hidden = false;
    public static long window;

    public static Thread tick = new Thread(()->{
        while (true){
            if(GLFW.glfwGetKey(window,bossKey.getKey().getValue())==GLFW.GLFW_PRESS){
                Minecraft MC = Minecraft.getInstance();

                if(hidden){
                    GLFW.glfwShowWindow(window);

                    MC.options.getSoundSourceOptionInstance(SoundSource.MUSIC).set(music);
                    music = -1d;
                }
                else{
                    music = MC.options.getSoundSourceOptionInstance(SoundSource.MUSIC).get();
                    MC.options.getSoundSourceOptionInstance(SoundSource.MUSIC).set(0d);

                    GLFW.glfwHideWindow(window);
                }
                hidden = !hidden;
            }
        }
    });

    public BossKey(){
        window = Minecraft.getInstance().getWindow().getWindow();
        tick.start();
    }

    @SubscribeEvent
    public static void registerKeys(RegisterKeyMappingsEvent event){
        event.register(bossKey);
        event.register(quitKey);
    }

    @SubscribeEvent
    public static void onKeyInput(TickEvent.ClientTickEvent event){
        if(GLFW.glfwGetKey(window,quitKey.getKey().getValue())==GLFW.GLFW_PRESS){
            GLFW.glfwDestroyWindow(Minecraft.getInstance().getWindow().getWindow());
            System.exit(0);
        }

        if(GLFW.glfwGetKey(window,closeKey.getKey().getValue())==GLFW.GLFW_PRESS){
            GLFW.glfwSetWindowShouldClose(window,true);
            GLFW.nglfwSetWindowCloseCallback(window,-1);
            Minecraft.getInstance().getWindow().close();
        }
    }
}
