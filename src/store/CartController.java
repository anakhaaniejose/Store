/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package store;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Anakha Anie Jose
 */
     
public class CartController implements Initializable {
    //@FXML
    //private GridPane grid;
    static String emailforcart;
    public static void getemailfrommarket(String emailfrom)
    {
        emailforcart=emailfrom;
        System.out.println(emailforcart);
    }
    @FXML
    private Button closebtn;
    
     @FXML
    private Button close;

    float amount=0;
    @FXML
    private Label bill;
    @FXML
    private Label namelabel;
    @FXML
    private GridPane grid;
    @FXML
    private ScrollPane scroll;
    Connection con,con1,con2,con3;
     PreparedStatement pst,pst1,pst2,pst3;
     ResultSet rs,rs1;
     @FXML
    void closeaction(ActionEvent event) {
          Stage stage=(Stage)closebtn.getScene().getWindow();
     stage.close();

    }
      @FXML
    void buynow(ActionEvent event) {
        try
         {
           Class.forName("com.mysql.cj.jdbc.Driver");
          con=DriverManager.getConnection("jdbc:mysql://localhost:3307/shopping_app","root","appukuttan123");
          String query1;
              query1="select * from cart where user_email=?";
              pst=con.prepareStatement(query1);
              pst.setString(1,emailforcart );
              rs=pst.executeQuery();
              while(rs.next())
              {
                  float quantity=rs.getFloat("qty");
                  String sellemail=rs.getString("selleremail");
                  String productname=rs.getString("prod_name");
                 String query2="select * from product where email=? and name=?";
                 float qtyupdate=0;
                 pst1=con.prepareStatement(query2);
                 pst1.setString(1, sellemail);
                 pst1.setString(2, productname);
                 rs1=pst1.executeQuery();
                 while(rs1.next()){
                 qtyupdate=rs1.getFloat("qty");
                 qtyupdate=qtyupdate-quantity;
                         }
                 
                 String query5="update product set qty=? where email=? and name=?";
                 pst2=con.prepareStatement(query5);
                 pst2.setFloat(1, qtyupdate);
                 pst2.setString(2, sellemail);
                 pst2.setString(3,productname);
                 pst2.execute();
              }
          String query6="delete from product where qty<=?";
          pst3=con.prepareStatement(query6);
          pst3.setFloat(1,0);
          pst3.execute();
          String query7="delete from cart where user_email=?";
          pst3=con.prepareStatement(query7);
          pst3.setString(1, emailforcart);
          pst3.execute();
          JOptionPane.showMessageDialog(null,"Congradulations,You have successfully placed order!");
          JOptionPane.showMessageDialog(null,"The seller will be contacting you for delivery.Thankyou!");
      
         
 
         }catch(ClassNotFoundException | SQLException e)
           {
           
           System.out.println("Connection failed");
           System.out.println(e);
           
       }
        Stage stage=(Stage)close.getScene().getWindow();
     stage.close();
    }
    
   
   // @Override
   /* public void initialize(URL url, ResourceBundle rb) {
        try
         {
           Class.forName("com.mysql.cj.jdbc.Driver");
          con=DriverManager.getConnection("jdbc:mysql://localhost:3307/shopping_app","root","appukuttan123");
          String query="select * from signup where email=?";
          pst1=con.prepareStatement(query);
          pst1.setString(1,emailforcart);
          rs1=pst1.executeQuery();
          if(rs1.next())
          {
          
              String fullnam=rs1.getString("Full_name");
              namelabel.setText(fullnam);
          }
         }
        catch(ClassNotFoundException | SQLException e)
           {
           
           System.out.println("Connection failed");
           System.out.println(e);
           
       }
        

    }*/

private List<itemcart> Itemcarts = new ArrayList<>();
    private Image image;
    private MyListener1 myListener;
    private List<itemcart> getData() throws FileNotFoundException, IOException, SQLException, ClassNotFoundException {
       List<itemcart> Itemcarts = new ArrayList<>();
        itemcart Itemcart;
        try
         {
           Class.forName("com.mysql.cj.jdbc.Driver");
          con=DriverManager.getConnection("jdbc:mysql://localhost:3307/shopping_app","root","appukuttan123");
          String query1;
              query1="select * from cart where user_email=?";
         
          
          //pst.setString(1, New);
         pst=con.prepareStatement(query1);
         pst.setString(1,emailforcart);
         rs=pst.executeQuery();
         while(rs.next())
         {
        String seller= rs.getString("sellname");
        String productname= rs.getString("Prod_name");
        String type_of_quantity= rs.getString("qtytype");
        Float qty_float=rs.getFloat("qty");
        System.out.println(qty_float);
        Float price_float=rs.getFloat("price");
        amount=amount+(qty_float*price_float);
        String url=(productname+".png");
        Itemcart = new itemcart();
        Itemcart.setQty(qty_float);
        Itemcart.setName(productname);
        Itemcart.setPrice(price_float);
        Itemcart.setImgSrc(url);
        //fruit.setColor("EEDC82");
        Itemcart.setSeller(seller);
        Itemcarts.add(Itemcart);
        
          }
         
 
         }catch(ClassNotFoundException | SQLException e)
           {
           
           System.out.println("Connection failed");
           System.out.println(e);
           
       }
        
       

        return Itemcarts;
    }
    
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try
         {
           Class.forName("com.mysql.cj.jdbc.Driver");
          con=DriverManager.getConnection("jdbc:mysql://localhost:3307/shopping_app","root","appukuttan123");
          String query="select * from signup where email=?";
          pst1=con.prepareStatement(query);
          pst1.setString(1,emailforcart);
          rs1=pst1.executeQuery();
          if(rs1.next())
          {
          
              String fullnam=rs1.getString("Full_name");
              namelabel.setText(fullnam);
          }
         }
        catch(ClassNotFoundException | SQLException e)
           {
           
           System.out.println("Connection failed");
           System.out.println(e);
           
       }
        
        try {
            boolean addAll = Itemcarts.addAll(getData());
        } catch (IOException ex) {
            Logger.getLogger(CartController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CartController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CartController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        int column = 0;
        int row = 1;
        System.out.println(Itemcarts.size());
        try {
            for (int i = 0; i < Itemcarts.size(); i++) {
                CartitemController.rememail(emailforcart);
                FXMLLoader fxmlLoader = new FXMLLoader();
                
                fxmlLoader.setLocation(getClass().getResource("cartitem.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

               CartitemController cartitemcontroller = fxmlLoader.getController();
               //cartitemcontroller.setData(Itemcarts.get(i));
               itemcart Itemcart=Itemcarts.get(i);
               //System.out.println(Itemcart.getName());
               cartitemcontroller.nameset(Itemcart.getName());
               cartitemcontroller.priceset(Itemcart.getPrice());
               cartitemcontroller.qtyset(Itemcart.getQty());
               Image img=new Image(getClass().getResourceAsStream(Itemcart.getImgSrc()));
               cartitemcontroller.imageset(img);
                if (column == 3) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row); //(child,column,row)
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
         bill.setText("Total Bill: Rs "+amount);

    }    
   
    
}
