package sample.dao;


import sample.model.TimeTable;
import sample.model.Lessons;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class LessonDao extends BaseDaoUtils {


    public List<TimeTable> getGroupTimeTable() {
        try (
                ResultSet rs = stmnt.executeQuery("SELECT CONCAT(s.section_name,' ',g.age_min,'-',g.age_max)as 'group',\n" +
                        "      lesson_day_of_week as week_day,\n" +
                        "       l.time,rooms.number  as room_number\n" +
                        "from lessons l left JOIN (\n" +
                        "    GROUPS g LEFT JOIN Sections s\n" +
                        "        ON g.section_id=s.section_id\n" +
                        "    )\n" +
                        "                         ON l.group_id=g.group_id\n" +
                        "               LEFT JOIN Rooms\n" +
                        "                         ON l.room_id=rooms.room_id\n" +
                        "WHERE s.type LIKE '%групповые%';");
        ) {
            List<TimeTable> tt = new ArrayList<>();
            boolean flag = false;
            while (rs.next()) {
                int day = rs.getInt("week_day");
                String time = rs.getString("time");
                String group = rs.getString("group");
                int room_num = rs.getInt("room_number");

                TimeTable timetable = new TimeTable("Time: " + time + '\n' + "Room: " + room_num, day, group);
                flag=false;
                for (int i = 0; i < tt.size(); i++) {
                    if (tt.get(i).getGroup().equals(group)) {
                        flag = true;
                        switch (day) {
                            case 1:
                                tt.get(i).setMon("Time: " + time + '\n' + "Room: " + room_num);
                                break;
                            case 2:
                                tt.get(i).setTue("Time: " + time + '\n' + "Room: " + room_num);
                                break;
                            case 3:
                                tt.get(i).setWed("Time: " + time + '\n' + "Room: " + room_num);
                                break;
                            case 4:
                                tt.get(i).setThu("Time: " + time + '\n' + "Room: " + room_num);
                                break;
                            case 5:
                                tt.get(i).setFri("Time: " + time + '\n' + "Room: " + room_num);
                                break;
                            case 6:
                                tt.get(i).setSat("Time: " + time + '\n' + "Room: " + room_num);
                                break;
                            case 7:
                                tt.get(i).setSun("Time: " + time + '\n' + "Room: " + room_num);
                                break;
                        }
                    }
                }
                if (!flag) {
                    tt.add(timetable);
                }

            }
            return tt;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public void create(int lessonDay, int groupId, String time, int duration) throws SQLException {
        stmnt.execute("INSERT INTO Lessons VALUES(" + lessonDay + "," + groupId + ",'" + addSlashes(time) + "'," + duration + ",NULL);");
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
            default -> 0;
        };
    }

    public void updateWeek(int groupId, String newWeek, String lastWeek) throws SQLException {
        stmnt.executeUpdate("UPDATE lessons set lesson_day_of_week=" + getWeekBack(newWeek) + " WHERE lesson_day_of_week=" + getWeekBack(lastWeek) + " AND group_id=" + groupId + ";");
    }

    public void updateTime(int groupId, String newTime, String week) throws SQLException {
        stmnt.executeUpdate("UPDATE lessons set time='" + addSlashes(newTime) + "' WHERE lesson_day_of_week=" + getWeekBack(week) + " AND group_id=" + groupId + ";");
    }

    public void updateRoom(int groupId, int newRoom, String week) throws SQLException {
        stmnt.executeUpdate("UPDATE lessons set room_id=" + newRoom + " WHERE lesson_day_of_week=" + getWeekBack(week) + " AND group_id=" + groupId + ";");
    }

    public void updateDuration(int groupId, int newDuration, String week) throws SQLException {
        stmnt.executeUpdate("UPDATE lessons set duration=" + newDuration + " WHERE lesson_day_of_week=" + getWeekBack(week) + " AND group_id=" + groupId + ";");
    }

    public void delete(int groupId, String week) throws SQLException {
        stmnt.execute("DELETE FROM lessons WHERE lesson_day_of_week=" + getWeekBack(week) + " AND group_id=" + groupId + ";");
    }

    public List<Lessons> getTimeTable() throws SQLException {
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

        List<Lessons> lessons = new ArrayList<>();
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

            Lessons lessons1 = new Lessons(type, getWeek(day), group_id, section + " " + age, time, duration, room_id, room_n);
            lessons.add(lessons1);
        }
        return lessons;
    }
}
