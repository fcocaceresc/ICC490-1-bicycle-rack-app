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
}
