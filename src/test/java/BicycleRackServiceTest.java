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

    @Test
    void updateStudentId() {
        Student student = new Student("1", "amadeus");
        String bicycleDescription = "oxford";
        Record record = bicycleRackService.checkIn(student, bicycleDescription);
        String newStudentId = "2";
        bicycleRackService.updateStudentId(record.getId(), newStudentId);
        assertEquals(newStudentId, record.getStudent().getId());
    }

    @Test
    void nullStudentIdUpdateStudentId() {
        Student student = new Student("1", "amadeus");
        String bicycleDescription = "oxford";
        Record record = bicycleRackService.checkIn(student, bicycleDescription);
        String newStudentId = null;
        Exception exception = assertThrows(IllegalArgumentException.class, () -> bicycleRackService.updateStudentId(record.getId(), newStudentId));
        assertEquals("New student ID can't be empty", exception.getMessage());
    }

    @Test
    void emptyStudentIdUpdateStudentId() {
        Student student = new Student("1", "amadeus");
        String bicycleDescription = "oxford";
        Record record = bicycleRackService.checkIn(student, bicycleDescription);
        String newStudentId = "";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> bicycleRackService.updateStudentId(record.getId(), newStudentId));
        assertEquals("New student ID can't be empty", exception.getMessage());
    }

    @Test
    void nonExistingRecordUpdateStudentId() {
        String newStudentId = "2";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> bicycleRackService.updateStudentId(1, newStudentId));
        assertEquals("Record not found", exception.getMessage());
    }

    @Test
    void sameStudentIdUpdateStudentId() {
        Student student = new Student("1", "amadeus");
        String bicycleDescription = "oxford";
        Record record = bicycleRackService.checkIn(student, bicycleDescription);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> bicycleRackService.updateStudentId(record.getId(), "1"));
        assertEquals("New student ID is the same as the current one", exception.getMessage());
    }

    @Test
    void updateStudentName() {
        Student student = new Student("1", "amadeus");
        String bicycleDescription = "oxford";
        Record record = bicycleRackService.checkIn(student, bicycleDescription);
        String newStudentName = "salieri";
        bicycleRackService.updateStudentName(record.getId(), newStudentName);
        assertEquals(newStudentName, record.getStudent().getName());
    }

    @Test
    void nullStudentNameUpdateStudentName() {
        Student student = new Student("1", "amadeus");
        String bicycleDescription = "oxford";
        Record record = bicycleRackService.checkIn(student, bicycleDescription);
        String newStudentName = null;
        Exception exception = assertThrows(IllegalArgumentException.class, () -> bicycleRackService.updateStudentName(record.getId(), newStudentName));
        assertEquals("New student name can't be empty", exception.getMessage());
    }

    @Test
    void emptyStudentNameUpdateStudentName() {
        Student student = new Student("1", "amadeus");
        String bicycleDescription = "oxford";
        Record record = bicycleRackService.checkIn(student, bicycleDescription);
        String newStudentName = "";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> bicycleRackService.updateStudentName(record.getId(), newStudentName));
        assertEquals("New student name can't be empty", exception.getMessage());
    }

    @Test
    void nonExistingRecordUpdateStudentName() {
        String newStudentName = "salieri";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> bicycleRackService.updateStudentName(1, newStudentName));
        assertEquals("Record not found", exception.getMessage());
    }

    @Test
    void sameStudentNameUpdateStudentName() {
        Student student = new Student("1", "amadeus");
        String bicycleDescription = "oxford";
        Record record = bicycleRackService.checkIn(student, bicycleDescription);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> bicycleRackService.updateStudentName(record.getId(), "amadeus"));
        assertEquals("New student name is the same as the current one", exception.getMessage());
    }
}