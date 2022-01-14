package ca.naln1.rainflake.hurtcommand;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;

import net.minecraft.util.DamageSource;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class DamageSourceArgumentType implements ArgumentType<DamageSource> {

    private static final Collection<String> EXAMPLES = Arrays.asList("GENERIC", "ON_FIRE");
    private final DamageSource damageSource;

    private DamageSourceArgumentType(final DamageSource damageSource) {
        this.damageSource = damageSource;
    }

    public static DamageSourceArgumentType damage(){
        return new DamageSourceArgumentType(DamageSource.GENERIC);
    }

    public static DamageSourceArgumentType damage(DamageSource damageSource){
        return new DamageSourceArgumentType(damageSource);
    }

    public static DamageSource getDamageSource(final CommandContext<?> context, final String name) {
        return context.getArgument(name, DamageSource.class);
    }

    public DamageSource getDamageSource(){
        return damageSource;
    }

    @Override
    public DamageSource parse(StringReader reader) throws CommandSyntaxException {
        for (String s : EXAMPLES) {
            if (s.contains(reader.getString())){
                return DamageSource.DRAGON_BREATH;
            }
        }

        return DamageSource.ON_FIRE;
    }

    @Override
    public boolean equals(final Object o) {
        return true;
        /*
        if (this == o) return true;
        if (!(o instanceof DamageSourceArgumentType)) return false;

        final DamageSourceArgumentType that = (DamageSourceArgumentType) o;
        return that.damageSource == damageSource;

         */
    }

    @Override
    public int hashCode() {
        return damageSource.msgId.hashCode();
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        return builder.suggest("GENERIC").suggest("ON_FIRE").buildFuture();
    }

    @Override
    public String toString() {
        if (damageSource == DamageSource.GENERIC){
            return "DamageSource()";
        } else {
            return "DamageSource(" + damageSource.toString() + ")";
        }
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }
}
