package seedu.ezdo.storage;


import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.ezdo.commons.events.model.EzDoChangedEvent;
import seedu.ezdo.commons.events.storage.DataSavingExceptionEvent;
import seedu.ezdo.model.AddressBook;
import seedu.ezdo.model.ReadOnlyEzDo;
import seedu.ezdo.model.UserPrefs;
import seedu.ezdo.storage.JsonUserPrefsStorage;
import seedu.ezdo.storage.Storage;
import seedu.ezdo.storage.StorageManager;
import seedu.ezdo.storage.XmlAddressBookStorage;
import seedu.ezdo.testutil.EventsCollector;
import seedu.ezdo.testutil.TypicalTestPersons;

public class StorageManagerTest {

    private StorageManager storageManager;

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();


    @Before
    public void setUp() {
        storageManager = new StorageManager(getTempFilePath("ab"), getTempFilePath("prefs"));
    }


    private String getTempFilePath(String fileName) {
        return testFolder.getRoot().getPath() + fileName;
    }


    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(300, 600, 4, 6);
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void addressBookReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link XmlAddressBookStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlAddressBookStorageTest} class.
         */
        AddressBook original = new TypicalTestPersons().getTypicalAddressBook();
        storageManager.saveAddressBook(original);
        ReadOnlyEzDo retrieved = storageManager.readAddressBook().get();
        assertEquals(original, new AddressBook(retrieved));
    }

    @Test
    public void getAddressBookFilePath() {
        assertNotNull(storageManager.getAddressBookFilePath());
    }

    @Test
    public void handleAddressBookChangedEvent_exceptionThrown_eventRaised() throws IOException {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlAddressBookStorageExceptionThrowingStub("dummy"),
                                             new JsonUserPrefsStorage("dummy"));
        EventsCollector eventCollector = new EventsCollector();
        storage.handleAddressBookChangedEvent(new EzDoChangedEvent(new AddressBook()));
        assertTrue(eventCollector.get(0) instanceof DataSavingExceptionEvent);
    }


    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlAddressBookStorageExceptionThrowingStub extends XmlAddressBookStorage {

        public XmlAddressBookStorageExceptionThrowingStub(String filePath) {
            super(filePath);
        }

        @Override
        public void saveAddressBook(ReadOnlyEzDo addressBook, String filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }


}
