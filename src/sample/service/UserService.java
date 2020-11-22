package sample.service;

import sample.model.User;

import java.util.List;

public class UserService {

   public int isUser(List<User> list,String login,String password){
        String user_type="";
        User user;
       for (User value : list) {
           user = value;
           if (user.getLogin().equals(login)
                   && user.getPassword().equals(password)) {
               user_type = user.getType();
               System.out.println(user_type);
               break;
           }
       }
       return switch (user_type) {
           case "admin" -> 0;
           case "staff" -> 1;
           case "members" -> 2;
           default -> 5;
       };
    }

}
