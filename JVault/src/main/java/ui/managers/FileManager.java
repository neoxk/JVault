package ui.managers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class FileManager {

    private final StringProperty name;
    private final StringProperty location;
    private final StringProperty size;

    public FileManager(String name, String location, String size) {
        this.name = new SimpleStringProperty(name);
        this.location = new SimpleStringProperty(location);
        this.size = new SimpleStringProperty(size);
    }

    public String getName() { return name.get(); }
    public String getLocation() { return location.get(); }
    public String getSize() { return size.get(); }

    public StringProperty nameProperty() { return name; }
    public StringProperty locationProperty() { return location; }
    public StringProperty sizeProperty() { return size; }

}
