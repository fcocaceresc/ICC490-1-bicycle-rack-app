import java.time.LocalDateTime;
import java.util.ArrayList;

public class BicycleRackService {
    private final ArrayList<Record> records = new ArrayList<>();

    public Record checkIn(Student student, String bicycleDescription) {
        validateCheckIn(student, bicycleDescription);
        Record record = new Record(records.size() + 1, student, bicycleDescription);
        records.add(record);
        return record;
    }

    private void validateCheckIn(Student student, String bicycleDescription) {
        if (student.getId() == null || student.getId().isEmpty()) {
            throw new IllegalArgumentException("Student ID can't be empty");
        }
        if (student.getName() == null || student.getName().isEmpty()) {
            throw new IllegalArgumentException("Student name can't be empty");
        }
        if (bicycleDescription == null || bicycleDescription.isEmpty()) {
            throw new IllegalArgumentException("Bicycle description can't be empty");
        }
        if (hasNotCheckedOutRecord(student.getId())) {
            throw new IllegalStateException("The student has a not checked out record");
        }
    }

    private boolean hasNotCheckedOutRecord(String studentId) {
        for (Record record : records) {
            if (record.getStudent().getId().equals(studentId) && record.getCheckOut() == null) {
                return true;
            }
        }
        return false;
    }

    public void checkOutByRecordId(int recordId) {
        Record recordToCheckOut = getRecordById(recordId);
        validateCheckOutByRecordId(recordToCheckOut);
        recordToCheckOut.setCheckOut(LocalDateTime.now());
    }

    private Record getRecordById(int recordId) {
        for (Record record : records) {
            if (record.getId() == recordId) {
                return record;
            }
        }
        return null;
    }

    private void validateCheckOutByRecordId(Record recordToCheckOut) {
        if (recordToCheckOut == null) {
            throw new IllegalArgumentException("Record not found");
        }
        if (recordToCheckOut.getCheckOut() != null) {
            throw new IllegalStateException("Record is already checked out");
        }
    }

    public void checkOutByStudentId(String studentId) {
        Record recordToCheckOut = getNotCheckedOutRecordForStudent(studentId);
        validateCheckOutByStudentId(recordToCheckOut);
        recordToCheckOut.setCheckOut(LocalDateTime.now());
    }

    private Record getNotCheckedOutRecordForStudent(String studentId) {
        for (Record record : records) {
            if (record.getStudent().getId().equals(studentId) && record.getCheckOut() == null) {
                return record;
            }
        }
        return null;
    }

    private void validateCheckOutByStudentId(Record recordToCheckOut) {
        if (recordToCheckOut == null) {
            throw new IllegalStateException("The student is already checked out");
        }
    }

    public ArrayList<Record> getRecords() {
        return new ArrayList<>(records);
    }

    public void updateStudentId(int recordId, String newStudentId) {
        Record record = getRecordById(recordId);
        validateUpdateStudentId(record, newStudentId);
        record.getStudent().setId(newStudentId);
    }

    private void validateUpdateStudentId(Record record, String newStudentId) {
        if (record == null) {
            throw new IllegalArgumentException("Record not found");
        }
        if (newStudentId == null || newStudentId.isEmpty()) {
            throw new IllegalArgumentException("New student ID can't be empty");
        }
        if (record.getStudent().getId().equals(newStudentId)) {
            throw new IllegalArgumentException("New student ID is the same as the current one");
        }
    }
}
