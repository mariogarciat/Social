package co.edu.udea.compumovil.socialchallenge.entities;




import java.util.Calendar;

/**
 * Created by Steven on 31/08/2016.
 */
public class Task {

    private String name;


    public Task(String name) {
        this.name = name;
    }

    public Task() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
