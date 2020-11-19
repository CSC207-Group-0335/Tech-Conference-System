package Files;

import MessagingPresenters.ConversationManager;
import MessagingPresenters.Message;
import Schedule.*;
import UserLogin.Speaker;
import UserLogin.User;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class CSVWriter {
    public CSVWriter() {

    }

    public void writeToUsers(String csv, ArrayList<User> storage) {

        try (FileWriter csvWriter = new FileWriter(csv)) {
            int i = 0;
            while (i < storage.size()) {
                User user = storage.get(i);
                csvWriter.append(user.getType());
                csvWriter.append(",");
                csvWriter.append(user.getName());
                csvWriter.append(",");
                csvWriter.append(user.getPassword());
                csvWriter.append(",");
                csvWriter.append(user.getEmail());
                csvWriter.append("\n");
                i++;
                csvWriter.flush();
            }

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void writeToConversations(String csv, ArrayList<ConversationManager> conversationstorage) {

        try (FileWriter csvWriter = new FileWriter(csv)) {
            int i = 0;
            while (i < conversationstorage.size()) {
                ConversationManager c = conversationstorage.get(i);
                csvWriter.append(c.getParticipants().get(0));
                csvWriter.append(",");
                csvWriter.append(c.getParticipants().get(1));
                csvWriter.append(",");
                String s = "";
                for (Message m : c.getMessages()) {
                    s = s + m.getRecipientEmail() + "~" + m.getSenderEmail() + "~" + m.getTimestamp().toString() + "~"
                            + m.getMessageContent() + "~" + m.getMessageId() + ";";
                }
                csvWriter.append(s);
                i++;
                csvWriter.append("\n");
                csvWriter.flush();
            }

        } catch(IOException ioException){
            ioException.printStackTrace();
        }

    }
    public void writeToRegistration(String csv,  HashMap<User, UserScheduleManager> talksignup){

        try (FileWriter csvWriter = new FileWriter(csv)) {
            int i = 0;
            while (i < talksignup.size()) {
                if(talksignup.get(i) != null){
                    UserScheduleManager userschedule = talksignup.get(i);
                    User user = userschedule.getUser();
                    csvWriter.append(user.getEmail());
                    csvWriter.append(",");
                    int j = 0;
                    while (j < userschedule.getTalkList().size() - 1){
                        csvWriter.append(userschedule.getTalkList().get(j).getTalkId());
                        csvWriter.append(',');
                        j ++;
                    }
                    csvWriter.append(userschedule.getTalkList().get(userschedule.getTalkList().size()).getTalkId());
                    csvWriter.append("\n");
                    i++;
                    csvWriter.flush();

                }
                i++;

            }

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }


    }
    public void writeToTalks(String csv, TalkManager talkmanage){
        try (FileWriter csvWriter = new FileWriter(csv)) {

            for(SpeakerScheduleManager speakersched: talkmanage.speakerScheduleMap.values()){
                String email = speakersched.getSpeaker().getEmail();
                ArrayList<Talk> talks = speakersched.getTalkList();
                for(Talk talk: talks){
                    for(RoomScheduleManager roomsched: talkmanage.roomScheduleMap.values()){
                        String roomname = roomsched.getRoom().getRoomName();
                        ArrayList<Talk> comparetalks = roomsched.getTalkList();
                        for(Talk compared: comparetalks){
                            if(talk == compared){
                                csvWriter.append(compared.getTalkId());
                                csvWriter.append(",");
                                csvWriter.append(compared.getTitle());
                                csvWriter.append(",");
                                csvWriter.append(email);
                                csvWriter.append(",");
                                csvWriter.append(roomname);
                                csvWriter.append(",");
                                csvWriter.append(compared.getStartTime().toString());
                                csvWriter.append("\n");
                                csvWriter.flush();
                            }
                        }
                    }
                }
            }
        }

         catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }
    public void writeToRooms(String csv, ArrayList<Room> roomlist){
        try (FileWriter csvWriter = new FileWriter(csv)) {
            int i = 0;
            while (i < roomlist.size()) {
                csvWriter.append(roomlist.get(i).getRoomName());
                csvWriter.append("\n");
                csvWriter.flush();
                i++;
            }

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }
}
