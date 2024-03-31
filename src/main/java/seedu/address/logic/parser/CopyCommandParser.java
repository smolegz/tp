package seedu.address.logic.parser;

import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CopyCommand;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new CopyCommand object
 */
public class CopyCommandParser implements Parser<CopyCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CopyCommand
     * and returns a CopyCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CopyCommand parse(String args) throws ParseException {
        String[] argsList = args.trim().split(" ");
        Index index = ParserUtil.parseIndex(argsList[0]);

        String[] toCopyList = Arrays.copyOfRange(argsList, 1, argsList.length);
        List<String> fieldsList = Arrays.asList(toCopyList);

        ParserUtil.parseFieldsToCopy(fieldsList);

        return new CopyCommand(index, fieldsList);
    }
}
