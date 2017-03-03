package seedu.ezdo.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.ezdo.commons.events.model.EzDoChangedEvent;
import seedu.ezdo.commons.events.storage.DataSavingExceptionEvent;
import seedu.ezdo.commons.exceptions.DataConversionException;
import seedu.ezdo.model.ReadOnlyEzDo;
import seedu.ezdo.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends AddressBookStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    String getAddressBookFilePath();

    @Override
    Optional<ReadOnlyEzDo> readAddressBook() throws DataConversionException, IOException;

    @Override
    void saveAddressBook(ReadOnlyEzDo addressBook) throws IOException;

    /**
     * Saves the current version of the Address Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleAddressBookChangedEvent(EzDoChangedEvent abce);
}
