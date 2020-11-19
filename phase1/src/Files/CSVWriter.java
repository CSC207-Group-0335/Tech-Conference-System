package Files;

import MessagingPresenters.ConversationManager;
import MessagingPresenters.Message;
import Schedule.Talk;
import Schedule.UserScheduleManager;
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

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }


    }

}
