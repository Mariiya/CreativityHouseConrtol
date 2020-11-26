package sample.dao;


import sample.model.Lesson;
import sample.model.TimeTable;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LessonDao {

    private Connection connection;
    private Statement stmnt;

    public LessonDao() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Creativity", "root", "root");
        stmnt = connection.createStatement();
    }

    public void shutdown() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    public List<Lesson> getLessonsList() {
        try (
                ResultSet rs = stmnt.executeQuery("select * from lessons");
        ) {
            List<Lesson> lessonList = new ArrayList<>();
            while (rs.next()) {
                int group_id = rs.getInt("group_id");
                int day = rs.getInt("lesson_day_of_week");
                String time = rs.getString("time");
                int duration = rs.getInt("duration");
                int roomId = rs.getInt("room_id");

                Lesson lesson = new Lesson(getWeek(day), group_id, time, duration, roomId);
                lessonList.add(lesson);
            }
            return lessonList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public void create (int lessonDay,int groupId,String time,int duration) throws SQLException {
        stmnt.execute("INSERT INTO Lessons VALUES(" +lessonDay + ","+groupId+",'"+time+"',"+duration+",NULL);");
    }


    public List<Integer> getRoomsList() {
        List<Integer> roomList = new ArrayList<>();
        try {
            ResultSet rs = stmnt.executeQuery("select number from rooms");
            while (rs.next()) {
                int room = rs.getInt("number");
               roomList.add(room);
            }
            return roomList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    String getWeek(int i) {
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

   int getWeekBack(String day) {
        return switch (day) {
            case "Monday" -> 1;
            case "Tuesday" -> 2;
            case "Wednesday" -> 3;
            case "Thursday" -> 4;
            case "Friday" -> 5;
            case "Saturday" -> 6;
            case "Sunday" -> 7;
            default ->0;
        };
    }

    public void updateWeek(int groupId, String newWeek,String lastWeek) throws SQLException {
        stmnt.executeUpdate("UPDATE lessons set lesson_day_of_week=" + getWeekBack(newWeek) + " WHERE lesson_day_of_week=" + getWeekBack(lastWeek) + " AND group_id="+groupId+";");
    }

    public void updateTime(int groupId, String newTime,String week) throws SQLException {
        stmnt.executeUpdate("UPDATE lessons set time='" + newTime + "' WHERE lesson_day_of_week=" + getWeekBack(week) + " AND group_id="+groupId+";");
    }
    public void updateRoom(int groupId, int newRoom,String week) throws SQLException {
        stmnt.executeUpdate("UPDATE lessons set room_id=" + newRoom + " WHERE lesson_day_of_week=" + getWeekBack(week) + " AND group_id="+groupId+";");
    }
    public void updateDuration(int groupId, int newDuration,String week) throws SQLException {
        stmnt.executeUpdate("UPDATE lessons set duration=" + newDuration + " WHERE lesson_day_of_week=" + getWeekBack(week) + " AND group_id="+groupId+";");
    }

      public void delete(int groupId, String week) throws SQLException {
        stmnt.execute("DELETE FROM lessons WHERE lesson_day_of_week=" + getWeekBack(week) + " AND group_id="+groupId+";");
    }

    public List<TimeTable> getTimeTable() throws SQLException {
        ResultSet rs = stmnt.executeQuery("SELECT (case when s.type LIKE '%групповые%' then 'G' else 'I'end) as type ,s.section_name,CONCAT(g.age_min,'-',g.age_max)as age,g.group_id,\n" +
                "    l.lesson_day_of_week as week_day,\n" +
                "       l.time,l.duration,rooms.number  as room_number,rooms.room_id\n" +
                "from lessons l left JOIN (\n" +
                "    GROUPS g LEFT JOIN Sections s\n" +
                "        ON g.section_id=s.section_id\n" +
                "    )\n" +
                "                         ON l.group_id=g.group_id\n" +
                "               LEFT JOIN Rooms\n" +
                "                         ON l.room_id=rooms.room_id;");

        List<TimeTable> timeTable = new ArrayList<>();
        while (rs.next()) {
            String type = rs.getString("type");
            String section = rs.getString("section_name");
            String age = rs.getString("age");
            int group_id = rs.getInt("group_id");
            int day = rs.getInt("week_day");
            String time = rs.getString("time");
            int duration = rs.getInt("duration");
            int room_id = rs.getInt("room_id");
            int room_n = rs.getInt("room_number");

            TimeTable timeTable1=new TimeTable(type,getWeek(day),group_id,section+" "+age,time,duration,room_id,room_n);
            timeTable.add(timeTable1);
        }
        return timeTable;
    }
}
