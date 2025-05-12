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
                    LocalDateTime.parse(resultSet.getString("check_in"))
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return record;
    }
}
