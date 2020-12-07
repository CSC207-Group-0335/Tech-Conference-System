package Files;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class JSONReader{

    public Object readJson(String json) throws Exception {
        FileReader reader = new FileReader(json);
        JSONParser jsonParser = new JSONParser();
        return jsonParser.parse(reader);
    }

}
