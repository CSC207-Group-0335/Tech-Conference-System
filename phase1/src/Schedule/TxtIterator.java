package Schedule;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class TxtIterator implements Iterator<String> {
    String fileName;
    private List<String> properties = new ArrayList<>();
    private int current = 0;

    /**
     * The prompt Strings are read from a file, student_properties.txt,
     * and added to the list of student properties.
     */
    public TxtIterator(String fileName) {
        this.fileName = fileName;
        //open file and read from it
        BufferedReader br = null;
        try {
            Scanner myReader = new Scanner(new File(fileName));
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                properties.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println(fileName + "is missing");
            e.printStackTrace();
        }
    }

    public List<String> getProperties() {
        return properties;
    }

    @Override
    public boolean hasNext() {
        return current < properties.size();
    }

    @Override
    public String next() {
        String res;

        // List.get(i) throws an IndexOutBoundsException if
        // we call it with i >= properties.size().
        // But Iterator's next() needs to throw a
        // NoSuchElementException if there are no more elements.
        try {
            res = properties.get(current);
        } catch (IndexOutOfBoundsException e) {
            throw new NoSuchElementException();
        }
        current += 1;
        return res;
    }
}
