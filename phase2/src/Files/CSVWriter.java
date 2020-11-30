package Files;

import MessagingPresenters.ConversationManager;
import MessagingPresenters.Message;
import Schedule.*;
import UserLogin.Speaker;
import UserLogin.User;
import UserLogin.UserStorage;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A general Gateway class that handles writing to the assorted files in the program
 */

public class CSVWriter {
    public CSVWriter() {

    }

    /**
     * A method to specifically write to the Users.csv, in the proper format that the program requires for Users.csv
     * @param csv the csv file that is being written to.
     * @param storage an array of Users that will be written to the csv.
     */

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

    /**
     * A method to specifically write to Conversations.csv, in the proper format that the program requires
     * @param csv the csv file that is being written to.
     * @param conversationStorage an array of ConversationManagers that will be written to the csv.
     */

    public void writeToConversations(String csv, ArrayList<ConversationManager> conversationStorage) {

        try (FileWriter csvWriter = new FileWriter(csv)) {
            int i = 0;
            while (i < conversationStorage.size()) {
                ConversationManager c = conversationStorage.get(i);
                csvWriter.append(c.getParticipants().get(0));
                csvWriter.append(",");
                csvWriter.append(c.getParticipants().get(1));
                csvWriter.append(",");
                String s = "";
                for (Message m : c.getMessages()) {

                    LocalDateTime time;
                    time = m.getTimestamp();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                    String formatted = time.format(formatter);

                    s = s + m.getRecipientEmail() + "~" + m.getSenderEmail() + "~" + formatted + "~"
                            + m.getMessageContent() + ";";
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

    /**
     * A method to specifically write to Registration.csv, in the proper format that the program.
     * @param csv the csv file that is being written to.
     */

    public void writeToRegistration(String csv, UserStorage userStorage){
        try (FileWriter csvWriter = new FileWriter(csv)) {
            for(String userEmail: userStorage.getUserEmailList()) {
                    csvWriter.append(userEmail);
                    csvWriter.append(",");
                    int j = 0;
                    while (j <= userStorage.emailToTalkList(userEmail).size() - 1){
                        csvWriter.append(userStorage.emailToTalkList(userEmail).get(j));
                        csvWriter.append(',');
                        j ++;
                    }
                    csvWriter.append("\n");
                    csvWriter.flush();
            }

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    /**
     * A method to specifically write to the Events.csv, in the proper format that the program requires.
     * @param csv the csv file that is being written to.
     * @param eventManager a TalkManager that will be used to get the information for the Talks that will be written.
     */

    public void writeToEvents(String csv, EventManager eventManager){
        try (FileWriter csvWriter = new FileWriter(csv)) {
            for (String eventId: eventManager.getEventIdsList()) {
                csvWriter.append(eventId);
                csvWriter.append(",");
                csvWriter.append(eventManager.eventIdToTitle(eventId));
                csvWriter.append(",");
                csvWriter.append("<");
                ArrayList<String> speakers = eventManager.eventIdToSpeakerEmails(eventId);
                for (String speaker: speakers){
                    csvWriter.append(speaker + "/");
                }
                csvWriter.append(">");
                csvWriter.append(",");
                csvWriter.append(eventManager.eventIdToRoomName(eventId));
                csvWriter.append(",");
                LocalDateTime startTime = eventManager.eventIdToStartTime(eventId);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                String formatted = startTime.format(formatter);
                csvWriter.append(formatted);
                csvWriter.append(",");
                LocalDateTime endTime = eventManager.eventIdToStartTime(eventId);
                String formatted2 = endTime.format(formatter);
                csvWriter.append(formatted2);
                csvWriter.append(",");
                csvWriter.append(eventManager.eventIdToVIPStatus(eventId));
                csvWriter.append("\n");
                csvWriter.flush();
            }}catch (IOException ioException) {
            ioException.printStackTrace();
        }}

    /**
     * A method to specifically write to the Rooms.txt, in the proper format that the program requires
     * @param f the file that is being written to.
     * @param roomList an array of Rooms that will be written to the file.
     */

    public void writeToRooms(String f, ArrayList<Room> roomList){
        try (FileWriter fileWriter = new FileWriter(f)) {
            int i = 0;
            while (i < roomList.size()) {
                fileWriter.append(roomList.get(i).getRoomName());
                fileWriter.append("\n");
                fileWriter.flush();
                i++;
            }

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }
}
