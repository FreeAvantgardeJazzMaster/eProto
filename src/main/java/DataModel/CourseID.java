package DataModel;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;

public class CourseID {
    @Id
    private ObjectId ID;

    private int id;

    public ObjectId getID() {
        return ID;
    }

    public void setID(ObjectId ID) {
        this.ID = ID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CourseID(int id){
        this.id = id;
    }

    public CourseID(){}
}
