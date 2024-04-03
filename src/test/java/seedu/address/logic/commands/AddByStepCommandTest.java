package seedu.address.logic.commands;

import static seedu.address.logic.commands.AddByStepCommand.MESSAGE_SUCCESS;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class AddByStepCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_addbystep_success() {
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_SUCCESS, false, false, true);
        assertCommandSuccess(new AddByStepCommand(), model, expectedCommandResult, expectedModel);
    }
}



