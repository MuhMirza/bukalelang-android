package id.clorus.bukalelang.data.realm;

import io.realm.RealmObject;

/**
 * Created by mirza on 25/05/17.
 */

public class SubCategoryRO extends RealmObject{

    private int id;
    private int masterId;
    private String name;
    private String url;

    public int getMasterId() {
        return masterId;
    }

    public void setMasterId(int masterId) {
        this.masterId = masterId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
