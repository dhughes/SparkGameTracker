import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by doug on 5/3/16.
 */
public class User implements Validatable {

    private String name;
    ArrayList<Game> games = new ArrayList<>();

    public User(String name) {
        this.name = name;
    }

    public User() {
        // no argument constructor
        this.name = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Game> getGames() {
        return games;
    }

    public void setGames(ArrayList<Game> games) {
        this.games = games;
    }

    @Override
    public HashMap<String, String> validate() {
        HashMap<String, String> errors = new HashMap<>();

        // does the name have a length?
        if(this.name.trim().length() == 0){
            // add our error
            errors.put("name", "Please provide a name");
        }

        // return our errors collection
        return errors;
    }
}
