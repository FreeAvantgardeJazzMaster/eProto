package DataModel;

import org.mongodb.morphia.annotations.Id;
import org.bson.types.ObjectId;

public class StudentIndex {
    @Id
    private ObjectId ID;

    private int indexCount;

    public int getIndexCount() {
        return indexCount;
    }

    public void setIndexCount(int indexCount) {
        this.indexCount = indexCount;
    }

    public ObjectId getID() {
        return ID;
    }

    public void setID(ObjectId ID) {
        this.ID = ID;
    }

    public StudentIndex(int indexCount){
        this.indexCount = indexCount;
    }

    public StudentIndex(){}
}
