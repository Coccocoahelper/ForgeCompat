package cc.polyfrost.oneconfig.utils.commands.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cc.polyfrost.oneconfig.libs.universal.ChatColor;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Command {
    String value();

    String[] aliases() default {};

    String description() default "";

    String[] customHelpMessage() default {};

    ChatColor chatColor() default ChatColor.GOLD;
}
