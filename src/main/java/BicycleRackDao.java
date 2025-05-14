import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class BicycleRackDao {
    String databaseUrl;

    public BicycleRackDao(String databaseUrl) {
        this.databaseUrl = databaseUrl;
        this.createRecordsTable();
    }

    public void createRecordsTable() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection(this.databaseUrl);
            String query = "CREATE TABLE IF NOT EXISTS records(" +
                    "id INTEGER PRIMARY KEY," +
                    "student_id TEXT NOT NULL," +
                    "student_name TEXT NOT NULL," +
                    "bicycle_description TEXT NOT NULL," +
                    "check_in TEXT NOT NULL," +
                    "check_out TEXT" +
                    ")";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void dropRecordsTable() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection(this.databaseUrl);
            String query = "DROP TABLE IF EXISTS records";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void createRecord(String studentId, String studentName, String bicycleDescription) {
        DaoValidator.validateRecordInput(studentId, studentName, bicycleDescription);
        validateBicycleRackCapacity();
        validateStudentHasANotCheckedOutRecord(studentId);
        executeCreateRecord(studentId, studentName, bicycleDescription);
    }

    private void validateBicycleRackCapacity() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(this.databaseUrl);
            String query = "SELECT COUNT(*) FROM records WHERE check_out IS NULL";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                int bicycleRackCapacity = ConfigReader.getIntValue("bicycleRack.maxCapacity");
                if (count >= bicycleRackCapacity) {
                    throw new IllegalStateException("The bicycle rack is full");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void validateStudentHasANotCheckedOutRecord(String studentId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(this.databaseUrl);
            String query = "SELECT * FROM records WHERE student_id = ? AND check_out IS NULL";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, studentId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                throw new IllegalStateException("The student has a not checked out record");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void executeCreateRecord(String studentId, String studentName, String bicycleDescription) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection(this.databaseUrl);
            String query = "INSERT INTO records (student_id, student_name, bicycle_description, check_in) VALUES (?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, studentId.trim());
            preparedStatement.setString(2, studentName.trim());
            preparedStatement.setString(3, bicycleDescription.trim());
            preparedStatement.setString(4, String.valueOf(LocalDateTime.now()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public ArrayList<Record> getRecords() {
        ArrayList<Record> records = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(this.databaseUrl);
            String query = "SELECT * FROM records";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Record record = mapResultSetToRecord(resultSet);
                records.add(record);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return records;
    }

    public void checkOutRecord(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection(this.databaseUrl);
            String query = "UPDATE records SET check_out = ? WHERE id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, String.valueOf(LocalDateTime.now()));
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void updateRecord(int id, String studentId, String studentName, String bicycleDescription) {
        DaoValidator.validateRecordInput(studentId, studentName, bicycleDescription);
        executeUpdateRecord(id, studentId, studentName, bicycleDescription);
    }
    private void executeUpdateRecord(int id, String studentId, String studentName, String bicycleDescription) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection(this.databaseUrl);
            String query = "UPDATE records SET student_id = ?, student_name = ?, bicycle_description = ? WHERE id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, studentId.trim());
            preparedStatement.setString(2, studentName.trim());
            preparedStatement.setString(3, bicycleDescription.trim());
            preparedStatement.setInt(4, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private Record mapResultSetToRecord(ResultSet resultSet) {
        Record record;
        try {
            Student student = new Student(
                    resultSet.getString("student_id"),
                    resultSet.getString("student_name")
            );
            record = new Record(
                    resultSet.getInt("id"),
                    student,
                    resultSet.getString("bicycle_description"),
                    LocalDateTime.parse(resultSet.getString("check_in")),
                    resultSet.getString("check_out") == null ? null : LocalDateTime.parse(resultSet.getString("check_out"))
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return record;
    }
}
