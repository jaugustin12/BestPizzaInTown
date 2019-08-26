package bestpizzaintown;

// Fig. 24.29: DisplayQueryResultsController.java
// Controller for the DisplayQueryResults app
import java.sql.SQLException;
import java.util.regex.PatternSyntaxException;

import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;           
import javax.swing.table.TableModel;    
import javax.swing.table.TableRowSorter;

public class DisplayQueryResultsController {
   @FXML private BorderPane borderPane;
   @FXML private TextArea queryTextArea;
   @FXML private TextField filterTextField;
   @FXML private TextField lastNameTextField;
   @FXML private DatePicker dateFromTextField;
   @FXML private DatePicker dateToTextField;
   @FXML private TextField phoneTextField;
   private ResultSetTableModel tableModel;
   private TableRowSorter<TableModel> sorter;

   // database URL, username and password
   private static final String DATABASE_URL = "jdbc:derby://localhost:1527/pizzadb";
   private static final String USERNAME = "app";
   private static final String PASSWORD = "app";
   
   // default query retrieves all data from Authors table
   private static final String DEFAULT_QUERY = "SELECT * FROM orders";
   private static final String NUM_ORDERS_PRICE_PER_DAY_QUERY
   = "select date(ordertime) as Order_Day, count(*) as Order_Number, sum(totalprice) as   Order_Total \n"
     + "from orders \n" + "group by date(ordertime) \n" + "order by order_day desc";
   private static final String ORDER_SEARCH_BY = "select firstname, lastname, ordertime, pizza.pizzades, ordereditems.pizzaqn, sides.sidesdes, ordereditems.sidesqn, drinks.drinkdes, ordereditems.drinkqn\n"
   + "from customers\n" + "inner join orders on customers.phonenumber = orders.phonenumber\n"
//            + "inner join customers on orders.lastname = customers.lastname\n"
   + "inner join ordereditems on ordereditems.orderid = orders.orderid\n"
   + "inner join pizza on pizza.pizzaid = ordereditems.pizzaid\n"
   + "inner join sides on sides.sidesid=ordereditems.sidesid\n"
   + "inner join drinks on drinks.drinkid=ordereditems.drinkid\n";
   // used for configuring JTable to display and sort data

   public void initialize() {
      queryTextArea.setText(DEFAULT_QUERY);

      // create ResultSetTableModel and display database table
      try {
         // create TableModel for results of DEFAULT_QUERY
         tableModel = new ResultSetTableModel(DATABASE_URL,            
            USERNAME, PASSWORD, DEFAULT_QUERY);                        
         
         // create JTable based on the tableModel    
         JTable resultTable = new JTable(tableModel);

         // set up row sorting for JTable
         sorter = new TableRowSorter<TableModel>(tableModel);
         resultTable.setRowSorter(sorter);             

         // configure SwingNode to display JTable, then add to borderPane
         SwingNode swingNode = new SwingNode();
         swingNode.setContent(new JScrollPane(resultTable));
         borderPane.setCenter(swingNode);
      }
      catch (SQLException sqlException) {
         displayAlert(AlertType.ERROR, "Database Error", 
            sqlException.getMessage());
         tableModel.disconnectFromDatabase(); // close connection  
         System.exit(1); // terminate application
      } 
   }

   // query the database and display results in JTable
   @FXML
   void submitQueryButtonPressed(ActionEvent event) {
      // perform a new query
      try {
         tableModel.setQuery(queryTextArea.getText());
      } 
      catch (SQLException sqlException) {
         displayAlert(AlertType.ERROR, "Database Error", 
            sqlException.getMessage());
         
         // try to recover from invalid user query 
         // by executing default query
         try {
            tableModel.setQuery(DEFAULT_QUERY);
            queryTextArea.setText(DEFAULT_QUERY);
         } 
         catch (SQLException sqlException2) {
            displayAlert(AlertType.ERROR, "Database Error", 
               sqlException2.getMessage());
            tableModel.disconnectFromDatabase(); // close connection  
            System.exit(1); // terminate application
         } 
      } 
   }

   // apply specified filter to results
   @FXML
   void applyFilterButtonPressed(ActionEvent event) {
      String text = filterTextField.getText();

      if (text.length() == 0) {
         sorter.setRowFilter(null);
      }
      else {
         try {
            sorter.setRowFilter(RowFilter.regexFilter(text));
         } 
         catch (PatternSyntaxException pse) {
            displayAlert(AlertType.ERROR, "Regex Error", 
               "Bad regex pattern");
         }
      }
   }

   // display an Alert dialog
   private void displayAlert(
      AlertType type, String title, String message) {
      Alert alert = new Alert(type);
      alert.setTitle(title);
      alert.setContentText(message);
      alert.showAndWait();
   }

    @FXML
    private void handLastNameQuery(ActionEvent event) {
        String searchString = ORDER_SEARCH_BY + " where customers.lastname =" + "'" + lastNameTextField.getText() + "'";
        // perform a new query
      try {
         tableModel.setQuery(searchString);
      } 
      catch (SQLException sqlException) {
         displayAlert(AlertType.ERROR, "Database Error", 
            sqlException.getMessage());
         
         // try to recover from invalid user query 
         // by executing default query
         try {
            tableModel.setQuery(DEFAULT_QUERY);
            queryTextArea.setText(DEFAULT_QUERY);
         } 
         catch (SQLException sqlException2) {
            displayAlert(AlertType.ERROR, "Database Error", 
               sqlException2.getMessage());
            tableModel.disconnectFromDatabase(); // close connection  
            System.exit(1); // terminate application
         } 
      } 
    }

    @FXML
    private void orderIncomePressed(ActionEvent event) {
         try {
         tableModel.setQuery(NUM_ORDERS_PRICE_PER_DAY_QUERY);
      } 
      catch (SQLException sqlException) {
         displayAlert(AlertType.ERROR, "Database Error", 
            sqlException.getMessage());
         
         // try to recover from invalid user query 
         // by executing default query
         try {
            tableModel.setQuery(DEFAULT_QUERY);
            queryTextArea.setText(DEFAULT_QUERY);
         } 
         catch (SQLException sqlException2) {
            displayAlert(AlertType.ERROR, "Database Error", 
               sqlException2.getMessage());
            tableModel.disconnectFromDatabase(); // close connection  
            System.exit(1); // terminate application
         } 
      } 
    }

    @FXML
    private void handDateQuery(ActionEvent event) {
        String fromDate = dateFromTextField.getValue().toString();
        String toDate = dateToTextField.getValue().toString();
        String searchString = DEFAULT_QUERY + " where date(OrderTime) >= '" + fromDate + "' AND date(OrderTime) <= '" + toDate + "'";
        // perform a new query
      try {
         tableModel.setQuery(searchString);
      } 
      catch (SQLException sqlException) {
         displayAlert(AlertType.ERROR, "Database Error", 
            sqlException.getMessage());
         
         // try to recover from invalid user query 
         // by executing default query
         try {
            tableModel.setQuery(DEFAULT_QUERY);
            queryTextArea.setText(DEFAULT_QUERY);
         } 
         catch (SQLException sqlException2) {
            displayAlert(AlertType.ERROR, "Database Error", 
               sqlException2.getMessage());
            tableModel.disconnectFromDatabase(); // close connection  
            System.exit(1); // terminate application
         } 
      }
    }

    @FXML
    private void handPhoneQuery(ActionEvent event) {
      String searchString = ORDER_SEARCH_BY + " where orders.phonenumber =" + "'" + phoneTextField.getText() + "'";
        // perform a new query
      try {
         tableModel.setQuery(searchString);
      } 
      catch (SQLException sqlException) {
         displayAlert(AlertType.ERROR, "Database Error", 
            sqlException.getMessage());
         
         // try to recover from invalid user query 
         // by executing default query
         try {
            tableModel.setQuery(DEFAULT_QUERY);
            queryTextArea.setText(DEFAULT_QUERY);
         } 
         catch (SQLException sqlException2) {
            displayAlert(AlertType.ERROR, "Database Error", 
               sqlException2.getMessage());
            tableModel.disconnectFromDatabase(); // close connection  
            System.exit(1); // terminate application
         } 
      } 
    }
}



/**************************************************************************
 * (C) Copyright 1992-2018 by Deitel & Associates, Inc. and               *
 * Pearson Education, Inc. All Rights Reserved.                           *
 *                                                                        *
 * DISCLAIMER: The authors and publisher of this book have used their     *
 * best efforts in preparing the book. These efforts include the          *
 * development, research, and testing of the theories and programs        *
 * to determine their effectiveness. The authors and publisher make       *
 * no warranty of any kind, expressed or implied, with regard to these    *
 * programs or to the documentation contained in these books. The authors *
 * and publisher shall not be liable in any event for incidental or       *
 * consequential damages in connection with, or arising out of, the       *
 * furnishing, performance, or use of these programs.                     *
 *************************************************************************/
