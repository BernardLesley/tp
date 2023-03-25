package wellnus.focus.command;

import java.util.HashMap;

import wellnus.command.Command;
import wellnus.exception.BadCommandException;
import wellnus.focus.feature.FocusManager;
import wellnus.focus.feature.Session;
import wellnus.ui.TextUi;

/**
 * The HomeCommand class is a command class that returns user back to the main WellNUS++ program.<br>
 */
public class HomeCommand extends Command {
    private static final int COMMAND_NUM_OF_ARGUMENTS = 1;
    private static final String COMMAND_INVALID_ARGUMENTS_MESSAGE = "That is not a valid home command for "
            + "focus timer!";
    private static final String COMMAND_INVALID_COMMAND_MESSAGE = "Wrong command given for home!";
    private static final String COMMAND_KEYWORD = "home";
    private static final String HOME_MESSAGE = "Thank you for using focus timer. Keep up the productivity!";
    private static final String NO_ADDITIONAL_MESSAGE = "";
    private final TextUi textUi;
    private final Session session;

    /**
     * Constructor for HomeCommand object.
     * Session in FocusManager is passed into this class.
     *
     * @param arguments Argument-Payload map generated by CommandParser
     * @param session   Session object which is an arraylist of Countdowns
     */
    public HomeCommand(HashMap<String, String> arguments, Session session) {
        super(arguments);
        this.textUi = new TextUi();
        this.session = session;
    }

    /**
     * Check if a HomeCommand is executed and user wants to return to home
     *
     * @param command User command
     * @return true If user wants to exit feature false if not
     */
    public static boolean isExit(Command command) {
        return command instanceof HomeCommand;
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
     * Identifies the feature that this Command is associated with. Override
     * this in subclasses so toString() returns the correct String representation.
     *
     * @return String Keyword for the feature associated with this Command
     */
    @Override
    protected String getFeatureKeyword() {
        return FocusManager.FEATURE_NAME;
    }

    /**
     * Stops the current countdown to close background thread.
     * Prints the exit feature message for the focus timer feature on the user's screen.
     */
    @Override
    public void execute() {
        try {
            validateCommand(super.getArguments());
        } catch (BadCommandException badCommandException) {
            textUi.printErrorFor(badCommandException, NO_ADDITIONAL_MESSAGE);
            return;
        }
        if (session.hasAnyCountdown()) {
            session.getSession().get(session.getCurrentCountdownIndex()).setStop();
        }
        textUi.printOutputMessage(HOME_MESSAGE);
    }

    /**
     * Validate the arguments and payloads from a commandMap generated by CommandParser.
     * User input is valid if no exceptions are thrown.
     *
     * @param arguments Argument-Payload map generated by CommandParser
     * @throws BadCommandException if the commandMap has any issues
     */
    @Override
    public void validateCommand(HashMap<String, String> arguments) throws BadCommandException {
        if (arguments.size() != HomeCommand.COMMAND_NUM_OF_ARGUMENTS) {
            throw new BadCommandException(HomeCommand.COMMAND_INVALID_ARGUMENTS_MESSAGE);
        }
        if (arguments.get(COMMAND_KEYWORD) != "") {
            throw new BadCommandException(HomeCommand.COMMAND_INVALID_ARGUMENTS_MESSAGE);
        }
        if (!arguments.containsKey(HomeCommand.COMMAND_KEYWORD)) {
            throw new BadCommandException(HomeCommand.COMMAND_INVALID_COMMAND_MESSAGE);
        }
    }
}


