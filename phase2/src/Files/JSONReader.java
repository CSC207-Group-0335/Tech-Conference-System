package Files;

import org.json.simple.parser.JSONParser;

import java.io.FileReader;

public class JSONReader{

    public Object readJson(String json) throws Exception {
        FileReader reader = new FileReader(json);
        JSONParser jsonParser = new JSONParser();
        return jsonParser.parse(reader);
    }

}
