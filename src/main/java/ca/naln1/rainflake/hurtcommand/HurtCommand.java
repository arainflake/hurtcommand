package ca.naln1.rainflake.hurtcommand;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod(HurtCommand.MOD_ID)
public class HurtCommand
{
    public static final String MOD_ID = "hurtcommand";

    public HurtCommand() {
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Mod.EventBusSubscriber()
    public static class ForgeEvents
    {
        @SubscribeEvent
        public static void onCommandsRegistry(final RegisterCommandsEvent event) {
            System.out.println("Hurt Command: Registering Command Dispatcher");
            HurtCommand.hurt(event.getDispatcher());
        }

    }

    public static void hurt(CommandDispatcher<CommandSource> dispatcher) {
        //).then(Commands.argument("source", DamageSourceArgumentType.damage()
        dispatcher.register(Commands.literal("hurt").requires(commandSource -> {
                    return commandSource.hasPermission(2);
                }
        ).then(Commands.argument("targets", EntityArgument.entities()
        ).then(Commands.argument("amount", FloatArgumentType.floatArg(0)
        ).executes(context -> {
            EntityArgument.getEntities(context, "targets").forEach((entity -> {
                //entity.hurt(DamageSourceArgumentType.getDamageSource(context, "source")
                //        , FloatArgumentType.getFloat(context, "amount"));
                entity.hurt(DamageSource.GENERIC, FloatArgumentType.getFloat(context, "amount"));
            }));
            return 1;
        }
        ))));
    }
}
