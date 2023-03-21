/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package store;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 * FXML Controller class
 *
 * @author Anakha Anie Jose
 */
public class Seller_pgController implements Initializable {
    Connection con,con1,con2;
     PreparedStatement pst,pst1,pst2;
     ResultSet rs,rs1,rs2;

      

    @FXML
    private Button btnSbmt;

    @FXML
    private TextField tfPname;

    @FXML
    private TextField tfPrice;

    @FXML
    private ChoiceBox tfcat;

    @FXML
    private TextField tfqty;

    @FXML
    private ChoiceBox tfqtytype;


    
    static String emailforsell;
    public static void getemailforseller(String emailfor)
    {
        emailforsell=emailfor;
    }
    String name;
    float qty;
    String cat;
    float price;
    String quatitytype;
    @FXML
    void submit(ActionEvent event) throws IOException {
        int Flag=-1;
        name=tfPname.getText();
       qty=Float.parseFloat(tfqty.getText());
      cat=(String) tfcat.getValue();
      price=Float.parseFloat(tfPrice.getText());
      quatitytype=(String) tfqtytype.getValue();
       System.out.println(quatitytype);
         try
         {
           Class.forName("com.mysql.cj.jdbc.Driver");
          con=DriverManager.getConnection("jdbc:mysql://localhost:3307/shopping_app","root","appukuttan123");
          
          String querymain="select * from product where email=? and name=?";
          pst1=con.prepareStatement(querymain);
          pst1.setString(1,emailforsell);
          pst1.setString(2, name);
          rs1=pst1.executeQuery();
          float updatedqty=0;
          while(rs1.next())
          {
              JOptionPane.showMessageDialog(null,"Same product you previously sold is left in the market. You can add this stock to thwm with uodated price1");
              updatedqty=rs1.getFloat("qty");
             updatedqty=updatedqty+qty;
              //qty=qty+rs1.getFloat("qty");
             //System.out.println(qty);
              
              Flag=0;
          }if(Flag==0)
          {String queryagain="update product set qty=? where email=? and name=?";
              pst2=con.prepareStatement(queryagain);
              pst2.setFloat(1, updatedqty);
              
              pst2.setString(2,emailforsell);
              pst2.setString(3, name);
              pst2.execute();
              
              queryagain="update product set price=? where email=? and name=?";
              pst2=con.prepareStatement(queryagain);
             
              pst2.setFloat(1, price);
              pst2.setString(2,emailforsell);
              pst2.setString(3, name);
              pst2.execute();
          }
          else if(Flag==-1){
          String query1="insert into product values(?,?,?,?,?,?)";
         pst=con.prepareStatement(query1);
         pst.setString(1, emailforsell);
          pst.setString(2, name);
           pst.setString(3, cat);
           pst.setFloat(4,price);
           pst.setFloat(5,qty);
           pst.setString(6, quatitytype);
           pst.execute();
           
          }
              JOptionPane.showMessageDialog(null,"Upload success!!");
           
         
          
           }catch(ClassNotFoundException | SQLException e)
           {
           
           System.out.println("Connection failed");
           System.out.println(e);
           
       }
     Stage stage=(Stage)btnSbmt.getScene().getWindow();
     stage.close();


    }


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        tfcat.getItems().addAll("fruits","vegetables","cutlery","footwear","electronics");
        tfqtytype.getItems().addAll("Kg","No");
    }    
     @FXML
    void quitbtn(ActionEvent event) {
 Platform.exit();
    }
    
}
