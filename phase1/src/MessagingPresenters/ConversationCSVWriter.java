package MessagingPresenters;

import java.io.*;
import java.util.ArrayList;

public class ConversationCSVWriter {
    private String csv;


    public ConversationCSVWriter(String csv, ArrayList<ConversationManager> storage) {
        this.csv = csv;


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
                    s = s + m.getRecipientEmail() + "~" + m.getSenderEmail() + "~" + m.getTimestamp().toString() + "~"
                            + m.getMessageContent() + "~" + m.getMessageId() + ";";
                }
                csvWriter.append(s);
                i++;
                csvWriter.flush();
                }

            } catch(IOException ioException){
                ioException.printStackTrace();
            }

        }

    }



