public class DaoValidator {
    public static void validateCreateRecordInput(String studentId, String studentName, String bicycleDescription) {
        DaoValidator.validateStudentId(studentId);
        DaoValidator.validateStudentName(studentName);
        DaoValidator.validateBicycleDescription(bicycleDescription);
    }

    public static void validateStudentId(String studentId) {
        validateNotNullStudentId(studentId);
        validateNotEmptyStudentId(studentId);
        validateStudentIdDoesntExceedMaxLength(studentId);
    }

    public static void validateStudentName(String studentName) {
        validateNotNullStudentName(studentName);
        validateNotEmptyStudentName(studentName);
        validateStudentNameDoesntExceedMaxLength(studentName);
    }

    public static void validateBicycleDescription(String bicycleDescription) {
        validateNotNullBicycleDescription(bicycleDescription);
        validateNotEmptyBicycleDescription(bicycleDescription);
        validateBicycleDescriptionDoesntExceedMaxLength(bicycleDescription);
    }

    public static void validateNotNullStudentId(String studentId) {
        if (studentId == null) {
            throw new IllegalArgumentException("The student id can't be null");
        }
    }

    public static void validateNotEmptyStudentId(String studentId) {
        if (studentId.trim().isEmpty()) {
            throw new IllegalArgumentException("The student id can't be empty");
        }
    }

    public static void validateStudentIdDoesntExceedMaxLength(String studentId) {
        int studentIdMaxLength = ConfigReader.getIntValue("record.student.id.maxLength");
        if (studentId.length() > studentIdMaxLength) {
            throw new IllegalArgumentException("The student id exceeds the maximum length of " + studentIdMaxLength + " characters");
        }
    }

    public static void validateNotNullStudentName(String studentName) {
        if (studentName == null) {
            throw new IllegalArgumentException("The student name can't be null");
        }
    }

    public static void validateNotEmptyStudentName(String studentName) {
        if (studentName.trim().isEmpty()) {
            throw new IllegalArgumentException("The student name can't be empty");
        }
    }

    public static void validateStudentNameDoesntExceedMaxLength(String studentName) {
        int studentNameMaxLength = ConfigReader.getIntValue("record.student.name.maxLength");
        if (studentName.length() > studentNameMaxLength) {
            throw new IllegalArgumentException("The student name exceeds the maximum length of " + studentNameMaxLength + " characters");
        }
    }

    public static void validateNotNullBicycleDescription(String bicycleDescription) {
        if (bicycleDescription == null) {
            throw new IllegalArgumentException("The bicycle description can't be null");
        }
    }

    public static void validateNotEmptyBicycleDescription(String bicycleDescription) {
        if (bicycleDescription.trim().isEmpty()) {
            throw new IllegalArgumentException("The bicycle description can't be empty");
        }
    }

    public static void validateBicycleDescriptionDoesntExceedMaxLength(String bicycleDescription) {
        int bicycleDescriptionMaxLength = ConfigReader.getIntValue("record.bicycleDescription.maxLength");
        if (bicycleDescription.length() > bicycleDescriptionMaxLength) {
            throw new IllegalArgumentException("The bicycle description exceeds the maximum length of " + bicycleDescriptionMaxLength + " characters");
        }
    }
}
