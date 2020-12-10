package Files;

import org.json.simple.parser.JSONParser;

import java.io.FileReader;

public class JSONReader{

    /**
     * Method which returns a JSON Parser..
     * @param json The string location of the JSON.
     * @return A JSON Parser.
     * @throws Exception
     */
    public Object readJson(String json) throws Exception {
        FileReader reader = new FileReader(json);
        JSONParser jsonParser = new JSONParser();
        return jsonParser.parse(reader);
    }

}
