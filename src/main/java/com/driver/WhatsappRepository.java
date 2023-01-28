package com.driver;

import java.time.LocalDate;
import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class WhatsappRepository {

    //Assume that each user belongs to at most one group
    //You can use the below mentioned hashmaps or delete these and create your own.
    private HashMap<Group, List<User>> groupUserMap;
    private HashMap<Group, List<Message>> groupMessageMap;
    private HashMap<Message, User> senderMap;
    private HashMap<Group, User> adminMap;
    private HashMap<String,User> userHM=new HashMap<>();
    private HashSet<String> userMobile;
    private int customGroupCount;
    private int messageId;

    public WhatsappRepository() {
        this.groupMessageMap = new HashMap<Group, List<Message>>();
        this.groupUserMap = new HashMap<Group, List<User>>();
        this.senderMap = new HashMap<Message, User>();
        this.adminMap = new HashMap<Group, User>();
        this.userMobile = new HashSet<>();
        this.customGroupCount = 0;
        this.messageId = 0;
    }

    public String createUser(String name, String mobile) throws Exception {
       if(userHM!=null && !userHM.containsKey(name)){
           User temp=new User(name,mobile);
           return "SUCCESS";
       }
       throw new Exception("User already exist");
    }

        public Group createGroup(List<User> users){
            Group group=null;
            if(users.size()==2){
                group=new Group(users.get(1).getName(),users.size());
                groupUserMap.put(group,users);
            }
            else{
                customGroupCount+=1;
                group=new Group("Group "+customGroupCount);
                groupUserMap.put(group,users);
            }
            adminMap.put(group,users.get(0));
            return group;

    }
    public int createMsg(String content) {
        messageId++;
      return messageId;

    }

    public int sendMsg(Message message, User sender, Group group) throws Exception {
       if(groupUserMap.containsKey(group)){
           List<User>al=groupUserMap.get(group);
           for(User user:al){
               if(Objects.equals(user.getMobile(), sender.getMobile()) && Objects.equals(user.getName(), sender.getName())){
                   List<Message>msg=new ArrayList<>();
                   if(!groupMessageMap.containsKey(group)){
                       msg.add(message);
                       groupMessageMap.put(group,msg);
                   }else{
                       msg=groupMessageMap.get(group);
                       msg.add(message);
                       groupMessageMap.put(group,msg);
                   }
                    return msg.size();
               }
           }
           throw new Exception("You are not allowed to send message");
       }
        throw new Exception("Group does not exist");
    }
    public String changeAdmin(User approver, User user, Group group) throws Exception {
       if(adminMap.containsKey(group) && groupUserMap.containsKey(group)){
           if(adminMap.get(group) == approver){
               List<User> al=groupUserMap.get(group);
               for(User userlist:al){
                   if(userlist==user){
                       adminMap.put(group,user);
                       return "Success";
                   }
               }
               throw new Exception("User is not a participant");
           }
           throw new Exception("Approver does not have rights");
       }
       throw new Exception("Group does not exist");
    }
    public int getCustomGroupCount() {
        return customGroupCount;
    }

    public void setCustomGroupCount(int customGroupCount) {
        this.customGroupCount = customGroupCount;
    }

}


