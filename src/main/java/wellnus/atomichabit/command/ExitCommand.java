package wellnus.atomichabit.command;

import wellnus.atomichabit.feature.AtomicHabitList;
import wellnus.atomichabit.feature.AtomicHabitManager;
import wellnus.command.Command;
import wellnus.exception.BadCommandException;
import wellnus.ui.TextUi;

import java.util.HashMap;

public class ExitCommand extends Command {
    private static final int COMMAND_NUM_OF_ARGUMENTS = 1;
    private static final String COMMAND_INVALID_ARGUMENTS_MESSAGE = "That is not a valid exit command for "
            + "atomic habits!";
    private static final String COMMAND_INVALID_COMMAND_MESSAGE = "Wrong command given for exit!";
    private static final String COMMAND_KEYWORD = "exit";
    private static final String COMMAND_DETAILED_DESCRIPTION = "";
    private static final String COMMAND_SUPPORTED_ARGUMENTS = "";
    private static final String EXIT_MESSAGE = "Thank you for using atomic habits. Do not forget about me!";
    private final TextUi textUi;

    public ExitCommand(HashMap<String, String> arguments) {
        super(arguments);
        this.textUi = new TextUi();
    }

    private TextUi getTextUi() {
        return textUi;
    }

    /**
     * Check if a ByeCommand is executed and user wants to exit program
     *
     * @param command User command
     * @return true If user wants to exit false if not
     */
    public static boolean isExit(Command command) {
        return command instanceof ExitCommand;
    }

    /**
     * Identifies this Command's keyword. Override this in subclasses so
     * toString() returns the correct String representation.
     *
     * @return String Keyword of this Command
     */
    @Override
    protected String getCommandKeyword() {
        return COMMAND_KEYWORD;
    }

    /**
     * Returns a detailed user-friendly description of what this specific command does.
     *
     * @return String Detailed explanation of this command
     */
    @Override
    protected String getDetailedDescription() {
        return COMMAND_DETAILED_DESCRIPTION;
    }

    /**
     * Identifies the feature that this Command is associated with. Override
     * this in subclasses so toString() returns the correct String representation.
     *
     * @return String Keyword for the feature associated with this Command
     */
    @Override
    protected String getFeatureKeyword() {
        return AtomicHabitManager.FEATURE_NAME;
    }

    /**
     * Returns all the supported arguments for this Command.
     *
     * @return String All supported arguments for this Command
     */
    @Override
    protected String getSupportedCommandArguments() {
        return COMMAND_SUPPORTED_ARGUMENTS;
    }

    /**
     * Prints the exit message for the atomic habits feature on the user's screen.
     */
    @Override
    public void execute() {
        try {
            validateCommand(super.getArguments());
        } catch (BadCommandException badCommandException) {
            String NO_ADDITIONAL_MESSAGE = "";
            this.getTextUi().printErrorFor(badCommandException, NO_ADDITIONAL_MESSAGE);
            return;
        }
        getTextUi().printOutputMessage(EXIT_MESSAGE);
    }

    /**
     * Validate the arguments and payloads from a commandMap generated by CommandParser <br>
     * <br>
     * The validation logic and strictness is up to the implementer. <br>
     * <br>
     * As a guideline, <code>isValidCommand</code> should minimally: <br>
     * <li>Verify that ALL MANDATORY arguments exist</li>
     * <li>Verify that ALL MANDATORY payloads exist</li>
     * <li>Safely verify the payload type (int, date, etc should be properly processed)</li>
     * <br>
     * Additionally, payload value cleanup (such as trimming) is also possible. <br>
     * As Java is pass (object reference) by value, any changes made to commandMap
     * will persist out of the function call.
     *
     * @param arguments Argument-Payload map generated by CommandParser
     * @throws BadCommandException if the commandMap has any issues
     */
    @Override
    public void validateCommand(HashMap<String, String> arguments) throws BadCommandException {
        if (arguments.size() != ExitCommand.COMMAND_NUM_OF_ARGUMENTS) {
            throw new BadCommandException(ExitCommand.COMMAND_INVALID_ARGUMENTS_MESSAGE);
        }
        String commandKeyword = arguments.get(AtomicHabitManager.FEATURE_NAME);
        if (!commandKeyword.equals(ExitCommand.COMMAND_KEYWORD)) {
            throw new BadCommandException(ExitCommand.COMMAND_INVALID_COMMAND_MESSAGE);
        }
    }
}


