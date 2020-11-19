package MessagingPresenters;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ConversationCSVWriter {
    private String csv;


    public ConversationCSVWriter(String csv, ArrayList<ConversationManager> storage) {
        this.csv = csv;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        try (FileWriter csvWriter = new FileWriter(this.csv)) {
            int i = 0;
            while (i < storage.size()) {
                ConversationManager c = storage.get(i);
                csvWriter.append(c.getParticipants().get(0));
                csvWriter.append(",");
                csvWriter.append(c.getParticipants().get(1));
                csvWriter.append(",");
                String s = "";
                for (Message m : c.getMessages()) {
                    String message = m.getMessageContent();
                    message = message.replace(",", "commaseparator");
                    s = s + m.getRecipientEmail() + "~" + m.getSenderEmail() + "~" + m.getTimestamp().format(formatter).replace("T",  " ") + "~"
                            + message+ ";";
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

    }



