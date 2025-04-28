import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BicycleRackServiceTest {
    private BicycleRackService bicycleRackService;

    @BeforeEach
    void setUp() {
        bicycleRackService = new BicycleRackService();
    }

    @Test
    void checkIn() {
        Student student = new Student("1", "amadeus");
        String bicycleDescription = "oxford";
        Record record = bicycleRackService.checkIn(student, bicycleDescription);
        assertNotNull(record);
    }

    @Test
    void emptyStudentIdCheckIn() {
        Student student = new Student("", "amadeus");
        String bicycleDescription = "oxford";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> bicycleRackService.checkIn(student, bicycleDescription));
        assertEquals("Student ID can't be empty", exception.getMessage());
    }

    @Test
    void emptyStudentNameCheckIn() {
        Student student = new Student("1", "");
        String bicycleDescription = "oxford";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> bicycleRackService.checkIn(student, bicycleDescription));
        assertEquals("Student name can't be empty", exception.getMessage());
    }

    @Test
    void emptyBicycleDescriptionCheckIn() {
        Student student = new Student("1", "amadeus");
        String bicycleDescription = "";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> bicycleRackService.checkIn(student, bicycleDescription));
        assertEquals("Bicycle description can't be empty", exception.getMessage());
    }

    @Test
    void studentHasNotCheckedOutRecordCheckIn() {
        Student student = new Student("1", "amadeus");
        String bicycleDescription = "oxford";
        bicycleRackService.checkIn(student, bicycleDescription);
        Exception exception = assertThrows(IllegalStateException.class, () -> bicycleRackService.checkIn(student, bicycleDescription));
        assertEquals("The student has a not checked out record", exception.getMessage());
    }

    @Test
    void checkOutByRecordId() {
        Student student = new Student("1", "amadeus");
        String bicycleDescription = "oxford";
        Record record = bicycleRackService.checkIn(student, bicycleDescription);
        bicycleRackService.checkOutByRecordId(record.getId());
        assertNotNull(record.getCheckOut());
    }

    @Test
    void notExistingRecordCheckOutByRecordId() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> bicycleRackService.checkOutByRecordId(1));
        assertEquals("Record not found", exception.getMessage());
    }

    @Test
    void alreadyCheckedOutRecordCheckOutByRecordId() {
        Student student = new Student("1", "amadeus");
        String bicycleDescription = "oxford";
        Record record = bicycleRackService.checkIn(student, bicycleDescription);
        bicycleRackService.checkOutByRecordId(record.getId());
        Exception exception = assertThrows(IllegalStateException.class, () -> bicycleRackService.checkOutByRecordId(record.getId()));
        assertEquals("Record is already checked out", exception.getMessage());
    }

    @Test
    void checkOutByStudentId() {
        Student student = new Student("1", "amadeus");
        String bicycleDescription = "oxford";
        Record record = bicycleRackService.checkIn(student, bicycleDescription);
        bicycleRackService.checkOutByStudentId(student.getId());
        assertNotNull(record.getCheckOut());
    }

    @Test
    void alreadyCheckedOutRecordCheckOutByStudentId() {
        Student student = new Student("1", "amadeus");
        String bicycleDescription = "oxford";
        bicycleRackService.checkIn(student, bicycleDescription);
        bicycleRackService.checkOutByStudentId(student.getId());
        Exception exception = assertThrows(IllegalStateException.class, () -> bicycleRackService.checkOutByStudentId(student.getId()));
        assertEquals("The student is already checked out", exception.getMessage());
    }
}