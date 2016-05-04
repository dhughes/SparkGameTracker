import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by doug on 5/3/16.
 */
public class Game implements Validatable {

    private String name;
    private String genre;
    private String platform;
    private int releaseYear;

    public Game(String name, String genre, String platform, int releaseYear) {
        this.name = name;
        this.genre = genre;
        this.platform = platform;
        this.releaseYear = releaseYear;
    }

    public Game() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public String getReleaseYearString(){
        if(this.releaseYear != 0){
            return String.valueOf(this.releaseYear);
        } else {
            return "";
        }
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    @Override
    public HashMap<String, String> validate() {
        HashMap<String, String> errors = new HashMap<>();

        // does the name have a length?
        if(this.name.trim().length() == 0){
            // add our error
            errors.put("name", "Please provide a name");
        }

        // does the genre have a length?
        if(this.genre.trim().length() == 0){
            // add our error
            errors.put("genre", "Please provide a genre");
        }

        // does the platform have a length?
        if(this.platform.trim().length() == 0){
            // add our error
            errors.put("platform", "Please provide a platform");
        }

        // did the user provide a release year?
        if(this.releaseYear == 0){
            errors.put("platform", "Please provide a release year");
        }
        // did the user provide a release year on or after 1970?
        if(this.releaseYear < 1958){
            errors.put("releaseYear", "There were no video games created before 1958.");
        }

        // return our errors collection
        return errors;
    }

}
