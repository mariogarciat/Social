package co.edu.udea.compumovil.socialchallenge.entities;



import com.orm.SugarRecord;

import java.util.Calendar;

/**
 * Created by Steven on 31/08/2016.
 */
public class Task extends SugarRecord {

    private String name;
    private Calendar createAt;
    private Calendar finishAt;
    private boolean isFinish;

    public Task(String name, Calendar createAt, Calendar finishAt, boolean isFinish) {
        this.name = name;
        this.createAt = createAt;
        this.finishAt = finishAt;
        this.isFinish = isFinish;
    }

    public Task() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFinish() {
        return isFinish;
    }

    public void setFinish(boolean finish) {
        isFinish = finish;
    }

    public Calendar getFinishAt() {
        return finishAt;
    }

    public void setFinishAt(Calendar finishAt) {
        this.finishAt = finishAt;
    }
}
