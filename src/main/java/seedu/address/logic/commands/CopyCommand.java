//package seedu.address.logic.commands;
//
//import seedu.address.commons.core.index.Index;
//import seedu.address.model.Model;
//
//import java.util.ArrayList;
//
//import static java.util.Objects.requireNonNull;
//
//public class CopyCommand extends Command {
//
//    public static final String COMMAND_WORD = "copy";
//    public static final String MESSAGE_SUCCESS = "Successfully copied!";
//    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Copies the information of a contact.\n"
//            + "copy INDEX name/contact/address/email\n"
//            + "Example: copy 1 name email\n"
//            + "INDEX should be a positive integer";
//
//    private final Index targetIndex;
//    private ArrayList<String> toCopyList;
//
//    public CopyCommand(Index targetIndex, ArrayList<String> toCopyList) {
//        requireNonNull(targetIndex);
//        this.targetIndex = targetIndex;
//        this.toCopyList = toCopyList;
//    }
//
//    @Override
//    public CommandResult execute(Model model) {
//        requireNonNull(model);
//        //return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
//    }
//
//
//}
