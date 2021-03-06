package io.gomint.command;

import io.gomint.command.annotation.Alias;
import io.gomint.command.annotation.Aliases;
import io.gomint.command.annotation.Description;
import io.gomint.command.annotation.Name;
import io.gomint.command.annotation.Overload;
import io.gomint.command.annotation.Overloads;
import io.gomint.command.annotation.Parameter;
import io.gomint.command.annotation.Permission;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author geNAZt
 * @version 1.0
 * <p>
 * This class builds up a command. A command can have defined parameters and permissions
 * <p>
 * Quick notes on the API design idea:
 * <p>
 * Command cmd = new Command( "test" );
 * cmd.description("This is just a test command");
 */
@Getter
public abstract class Command {

    private static final Logger LOGGER = LoggerFactory.getLogger( Command.class );

    private final String name;
    private String description;
    private List<CommandOverload> overload;
    private Set<String> alias;
    private String permission;

    /**
     * Constructor for loading commands from annotations like {@link Name}.
     */
    public Command() {
        // Search for Name and Description annotation
        Class<? extends Command> clazz = getClass();
        if ( !clazz.isAnnotationPresent( Name.class ) ||
            !clazz.isAnnotationPresent( Description.class ) ) {
            throw new IllegalArgumentException( "Command needs to be annotated with at least @Name and @Description: " +
                clazz.isAnnotationPresent( Name.class ) + " - " + clazz.isAnnotationPresent( Description.class ) );
        }

        this.name = clazz.getAnnotation( Name.class ).value();
        this.description = clazz.getAnnotation( Description.class ).value();

        // Setup permission
        if ( clazz.isAnnotationPresent( Permission.class ) ) {
            this.permission = clazz.getAnnotation( Permission.class ).value();
        }

        // Setup alias
        if ( clazz.isAnnotationPresent( Aliases.class ) ) {
            Alias[] aliases = clazz.getAnnotation( Aliases.class ).value();
            for ( Alias alias1 : aliases ) {
                alias( alias1.value() );
            }
        } else if ( clazz.isAnnotationPresent( Alias.class ) ) {
            alias( clazz.getAnnotation( Alias.class ).value() );
        }

        // Setup overload
        if ( clazz.isAnnotationPresent( Overloads.class ) ) {
            Overload[] overloads = clazz.getAnnotation( Overloads.class ).value();
            for ( Overload overload1 : overloads ) {
                registerOverloadAnnotation( overload1 );
            }
        } else if ( clazz.isAnnotationPresent( Overload.class ) ) {
            registerOverloadAnnotation( clazz.getAnnotation( Overload.class ) );
        }
    }

    /**
     * Construct a new command builder for a command name
     *
     * @param name The base name of the command
     */
    public Command( String name ) {
        this.name = name;
    }

    private void registerOverloadAnnotation( Overload annotation ) {
        CommandOverload overloadHolder = this.overload();

        for ( Parameter parameter : annotation.value() ) {
            // Search for either a no arg, string or a list<string> constructor
            Constructor<? extends ParamValidator> constructor;
            boolean needsList = false;
            boolean needsString = false;

            try {
                constructor = parameter.validator().getConstructor();
            } catch ( NoSuchMethodException e ) {
                // No args not present
                try {
                    constructor = parameter.validator().getConstructor( String.class );
                    needsString = true;
                } catch ( NoSuchMethodException e1 ) {
                    try {
                        constructor = parameter.validator().getConstructor( List.class );
                        needsList = true;
                    } catch ( NoSuchMethodException e2 ) {
                        throw new IllegalArgumentException( "Validator does not have a no args, string or list constructor." );
                    }
                }
            }

            String argumentString = null;
            List<String> argumentList = null;

            // Prepare constructor
            for ( String arg : parameter.arguments() ) {
                if ( needsString ) {
                    argumentString = arg;
                    break;
                } else if ( needsList ) {
                    if ( argumentList == null ) {
                        argumentList = new ArrayList<>();
                    }

                    argumentList.add( arg );
                }
            }

            // Construct the validator
            ParamValidator validator = null;
            if ( needsString ) {
                try {
                    validator = constructor.newInstance( argumentString );
                } catch ( InstantiationException | IllegalAccessException | InvocationTargetException e ) {
                    LOGGER.error( "Could not build command", e );
                }
            } else if ( needsList ) {
                try {
                    validator = constructor.newInstance( argumentList );
                } catch ( InstantiationException | IllegalAccessException | InvocationTargetException e ) {
                    LOGGER.error( "Could not build command", e );
                }
            } else {
                try {
                    validator = constructor.newInstance();
                } catch ( InstantiationException | IllegalAccessException | InvocationTargetException e ) {
                    LOGGER.error( "Could not build command", e );
                }
            }

            if ( validator != null ) {
                // Do we have a postfix?
                if ( !"".equals( parameter.postfix() ) ) {
                    overloadHolder.param( parameter.name(), validator, parameter.optional(), parameter.postfix() );
                } else {
                    overloadHolder.param( parameter.name(), validator, parameter.optional() );
                }
            }
        }
    }

    /**
     * Set the description for this command
     *
     * @param description of the command
     * @return the command currently build
     */
    public Command description( String description ) {
        this.description = description;
        return this;
    }

    /**
     * Set the permission a player needs to execute this command
     *
     * @param permission The permission which the player needs to execute
     * @return the command currently build
     */
    public Command permission( String permission ) {
        this.permission = permission;
        return this;
    }

    /**
     * Add an alias to the command
     *
     * @param alias which should be added
     * @return the command currently build
     */
    public Command alias( String alias ) {
        if ( this.alias == null ) {
            this.alias = new HashSet<>();
        }

        this.alias.add( alias );
        return this;
    }


    /**
     * Execute a command for a command sender
     *
     * @param commandSender which has executed the command
     * @param alias         which the user used to execute this command
     * @param arguments     which the player has given, optional parameters may be missing
     * @return CommandOutput which has been generated by the execution
     */
    public abstract CommandOutput execute( CommandSender commandSender, String alias, Map<String, Object> arguments );

    /**
     * Add a version of this command. You can add multiple version of a command using this system. For example:
     * - /list
     * - /list <filter: string>
     *
     * @return overload storage for parameter definition
     */
    public CommandOverload overload() {
        if ( this.overload == null ) {
            this.overload = new ArrayList<>();
        }

        CommandOverload commandOverload = new CommandOverload();
        this.overload.add( commandOverload );
        return commandOverload;
    }

}
