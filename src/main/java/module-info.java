module com.salmon.spicysalmon {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.salmon.spicysalmon to javafx.fxml;
    exports com.salmon.spicysalmon;
    exports models;
    opens models to javafx.fxml;
}