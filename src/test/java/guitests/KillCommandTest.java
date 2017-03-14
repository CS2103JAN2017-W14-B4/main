package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.ezdo.logic.commands.KillCommand.MESSAGE_KILL_TASK_SUCCESS;

import org.junit.Test;

import seedu.ezdo.testutil.TestTask;
import seedu.ezdo.testutil.TestUtil;

public class KillCommandTest extends EzDoGuiTest {

    @Test
    public void kill() {

        //delete the first in the list
        TestTask[] currentList = td.getTypicalTasks();
        int targetIndex = 1;
        assertKillSuccess(targetIndex, currentList);

        //delete the last in the list
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
        targetIndex = currentList.length;
        assertKillSuccess(targetIndex, currentList);

        //delete from the middle of the list
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
        targetIndex = currentList.length / 2;
        assertKillSuccess(targetIndex, currentList);

        //invalid index
        commandBox.runCommand("kill " + currentList.length + 1);
        assertResultMessage("The task index provided is invalid.");

    }

    /**
     * Runs the kill command to delete the task at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. index 1 to delete the first task in the list,
     * @param currentList A copy of the current list of tasks (before deletion).
     */
    private void assertKillSuccess(int targetIndexOneIndexed, final TestTask[] currentList) {
        TestTask taskToKill = currentList[targetIndexOneIndexed - 1]; // -1 as array uses zero indexing
        TestTask[] expectedRemainder = TestUtil.removeTaskFromList(currentList, targetIndexOneIndexed);

        commandBox.runCommand("kill " + targetIndexOneIndexed);

        //confirm the list now contains all previous tasks except the deleted task
        assertTrue(taskListPanel.isListMatching(expectedRemainder));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_KILL_TASK_SUCCESS, taskToKill));
    }

}
