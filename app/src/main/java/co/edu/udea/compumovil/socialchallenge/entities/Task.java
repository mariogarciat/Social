package co.edu.udea.compumovil.socialchallenge.entities;




import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.sql.Time;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Steven on 31/08/2016.
 */
public class Task{

    private String name;
    private String timeBegin;
    private String timeEnd;
    private boolean isFinished;
    private List<String> days;

    public Task(String name, String timeBegin, String timeEnd, boolean isFinished, List<String> days) {
        this.name = name;
        this.timeBegin = timeBegin;
        this.timeEnd = timeEnd;
        this.isFinished = isFinished;
        this.days = days;
    }

    public Task() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimeBegin() {
        return timeBegin;
    }

    public void setTimeBegin(String timeBegin) {
        this.timeBegin = timeBegin;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public List<String> getDays() {
        return days;
    }

    public void setDays(List<String> days) {
        this.days = days;
    }

/*
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(timeBegin);
        dest.writeString(timeEnd);
        dest.writeInt(isFinished ? 1 : 0);
        dest.writeStringList(days);
    }

    public Task(Parcel in){
        name = in.readString();
        timeBegin = in.readString();
        timeEnd = in.readString();
        isFinished = in.readInt() != 0;
        in.readStringList(days);
    }

    public static final Parcelable.Creator<Task> CREATOR = new Parcelable.Creator<Task>(){

        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[0];
        }
    };*/


}
