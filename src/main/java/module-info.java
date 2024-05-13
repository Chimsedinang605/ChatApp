module demo {
    requires emoji.java;
    requires jakarta.persistence;
    requires java.persistence;
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires org.hibernate.orm.core;

    opens Controller to javafx.fxml;
    exports Controller;
}