module com.braynsystem.analisissemantico {

    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    // Paquete principal
    opens com.braynsystem.analisissemantico to javafx.fxml;
    exports com.braynsystem.analisissemantico;

    // Arboles E
    opens com.braynsystem.analisissemantico.AnalizadorExpresiones to javafx.fxml;
    exports com.braynsystem.analisissemantico.AnalizadorExpresiones;

    // Acciones S
    opens com.braynsystem.analisissemantico.AccionesSemanticas to javafx.fxml;
    exports com.braynsystem.analisissemantico.AccionesSemanticas;

    // Comprobacion T
    opens com.braynsystem.analisissemantico.ComprobacionTipos to javafx.fxml;
    exports com.braynsystem.analisissemantico.ComprobacionTipos;

    //Pila
    opens com.braynsystem.analisissemantico.PilaSemantica to javafx.fxml;
    exports com.braynsystem.analisissemantico.PilaSemantica;

    //Traducir
    opens com.braynsystem.analisissemantico.EsquemaTraduccion to javafx.fxml;
    exports com.braynsystem.analisissemantico.EsquemaTraduccion;

    //Tabla de simbolos
    opens com.braynsystem.analisissemantico.TablaSimbolos to javafx.fxml;
    exports com.braynsystem.analisissemantico.TablaSimbolos;

    // Errores Sematicos
    opens com.braynsystem.analisissemantico.ErroresSemanticos to javafx.fxml;
    exports com.braynsystem.analisissemantico.ErroresSemanticos;
}