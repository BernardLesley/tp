package wellnus.focus.command;

import java.util.HashMap;

import wellnus.command.Command;
import wellnus.exception.BadCommandException;
import wellnus.focus.feature.FocusManager;
import wellnus.focus.feature.Session;
import wellnus.ui.TextUi;

/**
 * Represents a class to start the next countdown in the session.
 */
public class NextCommand extends Command {
    private static final String COMMAND_KEYWORD = "next";
    private static final int COMMAND_NUM_OF_ARGUMENTS = 1;
    private static final String COMMAND_INVALID_ARGUMENTS_MESSAGE = "Invalid command, expected 'next'";
    private static final String NO_ADDITIONAL_MESSAGE = "";
    private static final String COMMAND_KEYWORD_ASSERTION = "The key should be next.";
    private static final String NEXT_MESSAGE = "Starting next timer...";
    private final Session session;
    private final TextUi textUi;

    /**
     * Constructor for NextCommand object.
     * Session in FocusManager is passed into this class.
     *
     * @param arguments Argument-Payload Hashmap generated by CommandParser
     * @param session   Session object which is an arraylist of Countdowns
     */
    public NextCommand(HashMap<String, String> arguments, Session session) {
        super(arguments);
        this.session = session;
        this.textUi = new TextUi();
    }

    /**
     * Identifies this Command's keyword.
     * Override this in subclasses so toString() returns the correct String representation.
     *
     * @return String Keyword of this Command
     */
    @Override
    protected String getCommandKeyword() {
        return COMMAND_KEYWORD;
    }

    /**
     * Identifies the feature that this Command is associated with.
     * Override this in subclasses so toString() returns the correct String representation.
     *
     * @return String Keyword for the feature associated with this Command
     */
    @Override
    protected String getFeatureKeyword() {
        return FocusManager.FEATURE_NAME;
    }

    /**
     * Outputs unique description of the countdown.
     * Starts the session by starting the first countdown.
     * If the session has already started, it will start the next countdown.
     */
    @Override
    public void execute() {
        try {
            validateCommand(super.getArguments());
        } catch (BadCommandException badCommandException) {
            textUi.printErrorFor(badCommandException, NO_ADDITIONAL_MESSAGE);
            return;
        }
        if (session.getCurrentCountdownIndex() == session.getSession().size() - 1) {
            return;
        }
        session.checkPrevCountdown();
        if (!session.getSession().get(session.getCurrentCountdownIndex()).getIsCompletedCountdown()
                && !session.getSession().get(session.getCurrentCountdownIndex()).getIsRunning()) {
            session.getSession().get(session.getCurrentCountdownIndex()).start();
            session.getSession().get(session.getCurrentCountdownIndex()).setStart();
            textUi.printOutputMessage(session.getSession().get(session.getCurrentCountdownIndex()).getDescription());
        }
    }

    /**
     * Validate the arguments and payloads from a commandMap generated by CommandParser.
     * User input is valid if no exceptions are thrown.
     *
     * @param arguments Argument-Payload map generated by CommandParser
     * @throws BadCommandException If the arguments have any issues
     */
    @Override
    public void validateCommand(HashMap<String, String> arguments) throws BadCommandException {
        if (arguments.size() != COMMAND_NUM_OF_ARGUMENTS) {
            throw new BadCommandException(COMMAND_INVALID_ARGUMENTS_MESSAGE);
        }
        if (!arguments.containsKey(COMMAND_KEYWORD)) {
            throw new BadCommandException(COMMAND_INVALID_ARGUMENTS_MESSAGE);
        }
        if (arguments.get(COMMAND_KEYWORD) != "") {
            throw new BadCommandException(COMMAND_INVALID_ARGUMENTS_MESSAGE);
        }
    }

    /**
     * Method to ensure that developers add in a command usage.
     * <p>
     * For example, for the 'add' command in AtomicHabit package: <br>
     * "usage: add --name (name of habit)"
     *
     * @return String of the proper usage of the habit
     */
    @Override
    public String getCommandUsage() {
        return null;
    }

    /**
     * Method to ensure that developers add in a description for the command.
     * <p>
     * For example, for the 'add' command in AtomicHabit package: <br>
     * "add - add a habit to your list"
     *
     * @return String of the description of what the command does
     */
    @Override
    public String getCommandDescription() {
        return null;
    }
}
