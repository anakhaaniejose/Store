/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package store;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Anakha Anie Jose
 */
public class ChooseController implements Initializable {
 static String emai;
 public static void getema(String ema)
 {
    emai=ema;
    System.out.println(emai);
 }
 @FXML
    private Button close;
 @FXML
    private Button close1;
    
    @FXML
    void buy(ActionEvent event) throws IOException {
        Stage stage1=(Stage)close1.getScene().getWindow();
     stage1.close();
        MarketController.getcat("all",emai);
FXMLLoader fxmlloader= new FXMLLoader(getClass().getResource("market.fxml"));
         
        Parent root=fxmlloader.load();
        Stage stage=new Stage();
        stage.setScene(new Scene(root));
        //MarketController controller=fxmlloader.getController();
        
        stage.setTitle("Buy Products");
        stage.show();
    }

    @FXML
    void sell(ActionEvent event) throws IOException {
         Seller_pgController.getemailforseller(emai);
         
FXMLLoader fxmlloader= new FXMLLoader(getClass().getResource("seller_pg.fxml"));
        Parent root=(Parent)fxmlloader.load();
        Stage stage=new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Sell Products");
        stage.show();
    }
    /**
     * Initializes the controller class.
     */
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
