package boat.golden.outlife;

/**
 * Created by Vipin soni on 10-07-2018.
 */

public class profile_datatype {
    String name,bio,place,pro,interest,isfirst;

    public profile_datatype() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPro() {
        return pro;
    }

    public void setPro(String pro) {
        this.pro = pro;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getIsfirst() {
        return isfirst;
    }

    public void setIsfirst(String isfirst) {
        this.isfirst = isfirst;
    }

    public profile_datatype(String name, String bio, String place, String pro, String interest, String isfirst) {
        this.name = name;
        this.bio = bio;
        this.place = place;
        this.pro = pro;
        this.interest = interest;
        this.isfirst = isfirst;
    }
}
