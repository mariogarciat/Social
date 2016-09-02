package co.edu.udea.compumovil.socialchallenge.entities;

import com.orm.SugarRecord;

import java.util.List;

/**
 * Created by Steven on 31/08/2016.
 */
public class Challenge extends SugarRecord{

    private String title;
    private String timeSpent;
    private List<Task> tasks;

    public Challenge(String title, String timeSpent, List<Task> tasks) {
        this.title = title;
        this.timeSpent = timeSpent;
        this.tasks = tasks;
    }
    public Challenge(){

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(String timeSpent) {
        this.timeSpent = timeSpent;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
