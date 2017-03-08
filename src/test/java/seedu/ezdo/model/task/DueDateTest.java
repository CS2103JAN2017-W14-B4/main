package seedu.ezdo.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.ezdo.model.todo.DueDate;

public class DueDateTest {

    @Test
    public void isValidDueDate() {
        // invalid dates
        assertFalse(DueDate.isValidTaskDate(" ")); // spaces only
        assertFalse(DueDate.isValidTaskDate("priority")); // non-numeric
        assertFalse(DueDate.isValidTaskDate("23 04 1995")); // spaces within digits
        assertFalse(DueDate.isValidTaskDate("15.12.1945")); // fullstops within digits
        assertFalse(DueDate.isValidTaskDate("20/01/p041")); // alphabets within digits

        // valid dates
        assertTrue(DueDate.isValidTaskDate("31/12/1993")); // month with 31 days
        assertTrue(DueDate.isValidTaskDate("30/04/2016")); // month with 30 days
        assertTrue(DueDate.isValidTaskDate("29/02/2016")); // leap year
    }
}
