package com.driver;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WhatsappService {
    WhatsappRepository waRepo=new WhatsappRepository();
    public String createUser(String name,String mobile) throws Exception {
        return waRepo.createUser(name,mobile);
    }

    public Group createGroup(List<User> users) {
        return waRepo.createGroup(users);
    }

    public int sendMessage(Message message, User sender, Group group) throws Exception {
        return waRepo.sendMsg(message,sender,group);
    }

    public int createMessage(String content) {
        return waRepo.createMsg(content);
    }

    public String changeAdmin(User approver, User user, Group group) throws Exception {
        return waRepo.changeAdmin(approver,user,group);
    }

//    public int removeUser(User user) {
//        return waRepo.removeUser(user);
//    }
}
