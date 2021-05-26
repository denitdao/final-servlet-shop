package ua.denitdao.servlet.shop.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.denitdao.servlet.shop.util.Commands;

import java.util.HashMap;
import java.util.Map;

/**
 * This is a storage for all of the defined commands.
 */
public class CommandContainer {

    private static final Logger logger = LogManager.getLogger(CommandContainer.class);
    private static final Map<String, Command> commands;

    private CommandContainer() {
    }

    static {
        commands = new HashMap<>();
        commands.put(Commands.POST_LOGIN, new LoginCommand());
        commands.put(Commands.VIEW_LOGIN, new ViewLoginCommand());
        commands.put(Commands.POST_LOGOUT, new LogoutCommand());
        commands.put(Commands.VIEW_HOME, new ViewHomeCommand());
        commands.put(Commands.DEFAULT, new DefaultCommand());
        // ...
    }

    /**
     * Returns the command instance by the name. Or default command if the name was wrong.
     */
    public static Command getCommand(String commandName) {
        if (commandName != null && commands.containsKey(commandName))
            return commands.get(commandName);
        logger.warn("Command not found: '{}'", commandName);
        return commands.get(Commands.DEFAULT);
    }

}
