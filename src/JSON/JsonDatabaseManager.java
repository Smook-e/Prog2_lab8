package JSON;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JsonDatabaseManager <T>{
    protected ArrayList<T> db;
    protected Path file;
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public JsonDatabaseManager() {};
    public JsonDatabaseManager(String filePath, Class<T> clazz) throws IOException {
        file = Path.of(filePath);
        db = load(clazz);
    }
    public ArrayList<T> load(Class<T> clazz) throws IOException {

        try {
            if (!Files.exists(file)) return new ArrayList<>();

            String json = Files.readString(file);
            JsonArray arr = JsonParser.parseString(json).getAsJsonArray();

            ArrayList<T> list = new ArrayList<>();
            for (JsonElement el : arr) {
                list.add(gson.fromJson(el,clazz));   // one object → no type-erasure
            }
            return list;

        } catch (Exception ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        } // change string into arraylist
    }

    public void  save() {
        try {
            String json = gson.toJson(db);
            Files.writeString(file, json);
        } catch (IOException e) {
            System.out.println("Error while saving database.");
        }
    }


    public ArrayList<T> getDb() {
        return db;
    }

    public void print(){
        for(T t: db){
            System.out.println(t);
        }
    }


}
