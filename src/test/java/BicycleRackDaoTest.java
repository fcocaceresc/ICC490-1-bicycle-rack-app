import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BicycleRackDaoTest {
    private BicycleRackDao bicycleRackDao;

    @BeforeEach
    void setUp() {
        bicycleRackDao = new BicycleRackDao("jdbc:sqlite:test.db");
    }

    @AfterEach
    void tearDown() {
        bicycleRackDao.dropRecordsTable();
    }

    @Test
    void createRecord() {
        bicycleRackDao.createRecord("4", "amadeus", "oxford");
        ArrayList<Record> records = bicycleRackDao.getRecords();
        assertEquals(1, records.size());
        Record record = records.getFirst();
        Student student = record.getStudent();
        assertEquals(1, record.getId());
        assertEquals("4", student.getId());
        assertEquals("amadeus", student.getName());
        assertEquals("oxford", record.getBicycleDescription());
        assertNotNull(record.getCheckIn());
        assertNull(record.getCheckOut());
    }

    @Test
    void createRecordNullStudentId() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            bicycleRackDao.createRecord(null, "amadeus", "oxford");
        });
        assertEquals("The student id can't be null", exception.getMessage());
    }

    @Test
    void createRecordEmptyStudentId() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            bicycleRackDao.createRecord("", "amadeus", "oxford");
        });
        assertEquals("The student id can't be empty", exception.getMessage());
    }

    @Test
    void createRecordNullStudentName() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            bicycleRackDao.createRecord("4", null, "oxford");
        });
        assertEquals("The student name can't be null", exception.getMessage());
    }

    @Test
    void createRecordEmptyStudentName() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            bicycleRackDao.createRecord("4", "", "oxford");
        });
        assertEquals("The student name can't be empty", exception.getMessage());
    }

    @Test
    void createRecordNullBicycleDescription() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            bicycleRackDao.createRecord("4", "amadeus", null);
        });
        assertEquals("The bicycle description can't be null", exception.getMessage());
    }

    @Test
    void createRecordEmptyBicycleDescription() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            bicycleRackDao.createRecord("4", "amadeus", "");
        });
        assertEquals("The bicycle description can't be empty", exception.getMessage());
    }

    @Test
    void createRecordStudentIdExceedsMaxLength() {
        int studentIdMaxLength = ConfigReader.getIntValue("record.student.id.maxLength");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            bicycleRackDao.createRecord("a".repeat(studentIdMaxLength + 1), "amadeus", "oxford");
        });
        assertEquals("The student id exceeds the maximum length of " + studentIdMaxLength + " characters", exception.getMessage());
    }

    @Test
    void createRecordStudentNameExceedsMaxLength() {
        int studentNameMaxLength = ConfigReader.getIntValue("record.student.name.maxLength");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            bicycleRackDao.createRecord("4", "a".repeat(studentNameMaxLength + 1), "oxford");
        });
        assertEquals("The student name exceeds the maximum length of " + studentNameMaxLength + " characters", exception.getMessage());
    }

    @Test
    void createRecordBicycleDescriptionExceedsMaxLength() {
        int bicycleDescriptionMaxLength = ConfigReader.getIntValue("record.bicycleDescription.maxLength");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            bicycleRackDao.createRecord("4", "amadeus", "a".repeat(bicycleDescriptionMaxLength + 1));
        });
        assertEquals("The bicycle description exceeds the maximum length of " + bicycleDescriptionMaxLength + " characters", exception.getMessage());
    }

    @Test
    void createRecordExceedsBicycleRackMaxCapacity() {
        int bicycleRackMaxCapacity = ConfigReader.getIntValue("bicycleRack.maxCapacity");
        for (int i = 0; i < bicycleRackMaxCapacity; i++) {
            bicycleRackDao.createRecord(String.valueOf(i), "amadeus", "oxford");
        }
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            bicycleRackDao.createRecord(String.valueOf(bicycleRackMaxCapacity), "amadeus", "oxford");
        });
        assertEquals("The bicycle rack is full", exception.getMessage());
    }

    @Test
    void createRecordStudentHasANotCheckedOutRecord() {
        bicycleRackDao.createRecord("4", "amadeus", "oxford");
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            bicycleRackDao.createRecord("4", "amadeus", "oxford");
        });
        assertEquals("The student has a not checked out record", exception.getMessage());
    }
}
