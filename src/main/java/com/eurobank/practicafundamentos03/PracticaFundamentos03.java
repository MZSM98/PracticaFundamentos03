
package com.eurobank.practicafundamentos03;

import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author User
 */
public class PracticaFundamentos03 extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        
        try{
            
            Parent vista = FXMLLoader.load(getClass().getResource("/com/eurobank/practicafundamentos03/FXMLPracticaFundamento.fxml"));
            Scene scene = new Scene (vista);
            primaryStage.setScene(scene);
            primaryStage.show();
            
        }catch (IOException ioe){
            
            System.out.println("No se pudo cargar la ventana");
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
