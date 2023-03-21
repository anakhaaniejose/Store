/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package store;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import static store.CartController.emailforcart;

/**
 * FXML Controller class
 *
 * @author Anakha Anie Jose
 */
public class CartitemController{
    @FXML
    private Button close;
    @FXML
    private Label qty;
    
    @FXML
    private Label nameLabel;

    @FXML
    private Label priceLable;
    
    @FXML
    private ImageView img;
    private itemcart Itemcart;
    private MyListener1 myListener;
    @FXML
    private void click(MouseEvent mouseEvent) {
        myListener.onClickListener(Itemcart);
    }
    Connection con;
     PreparedStatement pst;
     ResultSet rs;
     static String email;
     public static void rememail(String emal)
     {
         email=emal;
     }
    @FXML
    void remove(ActionEvent event) {
        String name=nameLabel.getText();
        float price=Float.parseFloat(priceLable.getText());
        float quty=Float.parseFloat(qty.getText());
         try
         {
           Class.forName("com.mysql.cj.jdbc.Driver");
          con=DriverManager.getConnection("jdbc:mysql://localhost:3307/shopping_app","root","appukuttan123");
          String query="delete from cart where prod_name=? and qty=? and price=? and user_email=?";
          pst=con.prepareStatement(query);
          pst.setString(1, name);
          pst.setFloat(2, quty);
          pst.setFloat(3, price);
          pst.setString(4, email);
          
         pst.execute();
         JOptionPane.showMessageDialog(null,"Item Removed, press on cart again to see reloaded cart");
         
         }
        catch(ClassNotFoundException | SQLException e)
           {
           
           System.out.println("Connection failed");
           System.out.println(e);
           
       }
         Stage stage=(Stage)close.getScene().getWindow();
     stage.close();
        

    }



//    public void setData(itemcart ItemCart) {
//        //this.Itemcart = Itemcart;
//        //this.myListener = myListener;
//        //System.out.println(Itemcart.getName());
//         nameLabel.setText(Itemcart.getName());
//        priceLable.setText(""+Itemcart.getPrice());
//        Image image = new Image(getClass().getResourceAsStream(Itemcart.getImgSrc()));
//        img.setImage(image);
//    }

   
   public void nameset(String nam)
   {
     nameLabel.setText(nam);  
   }
   public void qtyset(float qt)
   {
     qty.setText(""+qt);  
   }
    public void priceset(double pr)
    {
        priceLable.setText(""+pr);
    }
    
    public void imageset( Image image )
   
    {
        img.setImage(image);
  }
//    
    
}
