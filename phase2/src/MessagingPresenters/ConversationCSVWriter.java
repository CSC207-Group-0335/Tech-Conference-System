package MessagingPresenters;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ConversationCSVWriter {
    private String csv;

    /**
     * Initializes ConversationCSVWriter.
     */

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
                StringBuilder s = new StringBuilder();
                // FIX NEEDED TO ACCOUNT FOR NEW ARCHIVE/UNARCHIVE FUNCTIONALITY
                for (Message m : c.getArchivedMessages(c.getParticipants().get(0))) {
                    String message = m.getMessageContent();
                    message = message.replace(",", "commaseparator");
                    s.append(m.getRecipientEmail()).append("~").append(m.getSenderEmail()).append("~").append(m.getTimestamp().format(formatter).replace("T", " ")).append("~").append(message).append(";");
                }
                csvWriter.append(s.toString());
                i++;
                csvWriter.append("\n");
                csvWriter.flush();
            }

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

}



