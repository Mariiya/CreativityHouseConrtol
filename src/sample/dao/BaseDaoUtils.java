package sample.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.StringCharacterIterator;

public abstract class BaseDaoUtils {

    protected Connection connection;
    protected Statement stmnt;


    public BaseDaoUtils()  {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Creativity", "root", "root");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            stmnt = connection.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void shutdown() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    protected  String addSlashes(String text) {
        final StringBuffer sb = new StringBuffer(text.length() * 2);
        final StringCharacterIterator iterator = new StringCharacterIterator(text);

        char character = iterator.current();

        while (character != StringCharacterIterator.DONE) {
            if (character == '"')
                sb.append("\\\"");
            else if (character == '\'')
                sb.append("\\\'");
            else if (character == '\\')
                sb.append("\\\\");
            else if (character == '\n')
                sb.append("\\n");
            else if (character == '{')
                sb.append("\\{");
            else if (character == '}')
                sb.append("\\}");
            else/*from www  . ja  v a2 s.  c  o m*/
                sb.append(character);

            character = iterator.next();
        }

        return sb.toString();
    }

   public String getWeek(int i) {
        return switch (i) {
            case 1 -> "Monday";
            case 2 -> "Tuesday";
            case 3 -> "Wednesday";
            case 4 -> "Thursday";
            case 5 -> "Friday";
            case 6 -> "Saturday";
            case 7 -> "Sunday";
            default -> "Unsigt";
        };
    }

   public int getWeekBack(String day) {
        return switch (day) {
            case "Monday" -> 1;
            case "Tuesday" -> 2;
            case "Wednesday" -> 3;
            case "Thursday" -> 4;
            case "Friday" -> 5;
            case "Saturday" -> 6;
            case "Sunday" -> 7;
            default -> 0;
        };
    }
}
