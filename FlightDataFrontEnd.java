// --== CS400 Project Three File Header ==--
// Name: Tom Rosen
// Email: trrosen@wisc.edu
// Team: Blue
// Group: CI
// TA: Harper
// Lecturer: Florian
// Notes to Grader:
import java.io.FileInputStream;
import java.util.NoSuchElementException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import java.util.List;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
/**
 * Class creates the front end for flight data application.  Uses JavaFX to create a working, smooth front end interface for customers to enjoy.  Shows flights and costs between busy airports based on dijsktra algo.
 */
public class FlightDataFrontEnd extends Application{
        private String airport1;
        private String airport2;
        private static FlightDataBackEnd gp;
        public int cost;
        //private FlightNode airport1;
        //private FlightNode airport2;
        /**
         * Starts our instance and does all inital setup for JavaFX; also manages events that will be handeled by calling helper methods.
         */
        @Override
        public void start(final Stage stage) {
            // update this method definition to complete the First JavaFX Activity
            Label lb = new Label("Enter Travel Destinations:");
            lb.setFont(new Font("Comic Sans MS", 32));
            lb.setTextFill(Color.web("#0076a3"));
            BorderPane borderPane = new BorderPane();
            VBox vb = new VBox(5);
            vb.setPadding(new Insets(25, 5 , 5, 50));
            Scene scene = new Scene(borderPane, 875, 480);

            Pane sp = new Pane();
            Image im = null;

            try
            {
                    im = new Image(new FileInputStream("D:\\Eclipse\\eclipse-workspace\\ProjectThree\\csvFolder/us-busiest-airports.jpg"));
            }
            catch(Exception e)
            {
                    System.out.println("Error with image");
            }
            ImageView imageView = new ImageView(im);
            imageView.setX(10);
            imageView.setY(10);
            imageView.setFitWidth(575);
            imageView.setPreserveRatio(true);
            imageView.setViewOrder(1.0);


            borderPane.setTop(lb);
            BorderPane.setAlignment(lb, Pos.TOP_LEFT);

            Label label1 = new Label("Airport One(ABV): ");
            label1.setTextFill(Color.web("rgb(0,50,255)"));
            label1.setFont(new Font("Comic Sans MS", 14));
            Label label2 = new Label("Airport Two(ABV): ");
            label2.setTextFill(Color.web("rgb(0,50,255)"));
            label2.setFont(new Font("Comic Sans MS", 14));

            TextField textField1 = new TextField();
            TextField textField2 = new TextField();

            textField1.setPromptText("Enter Airport One(Abreviation)");
            textField2.setPromptText("Enter Airport Two(Abreviation)");

            Button submit = new Button("Submit");
            submit.setOnAction(new EventHandler<ActionEvent>() {
              @Override
              public void handle(ActionEvent e) {
                  if ((textField1.getText() != null && !textField1.getText().isEmpty()) &&
                                  (textField2.getText() != null && !textField2.getText().isEmpty())) {
                      lb.setText(textField1.getText() + " to " + textField2.getText() + ", "
                          + "Showing Trip!");
                      if(airport1 != null || airport2 != null)
                      {
                          resetCreateFlight(sp, borderPane);
                      }
                      List<String> ls = StoreAirports(textField1.getText(), textField2.getText());
                    costAirports(textField1.getText(), textField2.getText());
                                          if (ls == null) {
                                                  lb.setText("No flights from " + airport1 + " to " + airport2);
                                          } else {
                                                  createFlight(sp, lb);
                                          }

                  } else {
                      lb.setText("You have not selected two viable locations.");
                  }
               }
           });

  vb.getChildren().addAll(label1, textField1, label2, textField2, submit);

  borderPane.setLeft(vb);
  BorderPane.setAlignment(vb, Pos.BOTTOM_LEFT);





  Button reset = new Button("Reset");
  reset.setOnAction(new EventHandler<ActionEvent>() {

          @Override
              public void handle(ActionEvent e) {
                  textField1.clear();
                  textField2.clear();
                  lb.setText("Enter travel destinations");
                  resetCreateFlight(sp, borderPane);

              }
          });

  Button exit = new Button("Exit");
  exit.setOnAction(event -> {
      Platform.exit();
  });

  HBox hb = new HBox();
  hb.getChildren().addAll(reset, exit);
  hb.setSpacing(795);
//borderPane.setBottom(reset);
  //BorderPane.setAlignment(reset, Pos.BOTTOM_RIGHT);

  //borderPane.setBottom(submit);
  //BorderPane.setAlignment(submit, Pos.BASELINE_LEFT);

  borderPane.setBottom(hb);
  //borderPane.getChildren().add(imageView2);


  sp.getChildren().addAll(imageView);
  //sp.setAlignment(imageView, Pos.CENTER);

  borderPane.setCenter(sp);
  //BorderPane.setAlignment(imageView, Pos.CENTER);
  //borderPane.getChildren().addAll(imageView2);





  //sp.getChildren().add(imageView3);



  stage.setScene(scene);
  stage.setTitle("Airport Map");
  stage.show();
}
        /**
         * Stores the airports taken in from user, passes them to backend, and then uses them to compute shortest path.
         */
        public List<String> StoreAirports(String s1, String s2)
        {
                if(s1 == null || s2 == null)
                {
                        throw new NoSuchElementException("ERROR airports are null!");
                }
                airport1 = s1.toUpperCase();
                airport2 = s2.toUpperCase();
                System.out.println(airport1 + " " + airport2);
                try {
                        return gp.shortestPath(airport1, airport2);
                } catch (NoSuchElementException e) {
                        return null;
                }
                //Set airports to nodes in fields
                //List<FlightNodes> ogList = loadFile(airports.csv);
                //for(int i = 0; i < ogList.size(); i++) {
                //      if(ogList.get(i).data == airport1.data){
                //              airport1(node) = ogList.get(i); }
                // Repeat for node 2
                //List<FlightNode> ls = new List<>();
                //ls.add(airport1);
                //ls.add(airport2);
                //return ls;
        }
        /**
         * Finds the cost of the best path between to airports and saves it for later printing.
         */
        public int costAirports(String s1, String s2)
        {
                if(s1 == null || s2 == null)
                {
                        throw new NoSuchElementException("ERROR airports are null!");
                }
                airport1 = s1.toUpperCase();
                airport2 = s2.toUpperCase();
                //System.out.println(airport1 + " " + airport2);

                try
                {
                        cost = gp.getPathCost(airport1, airport2);
                        return cost;
                }
                catch(NoSuchElementException e)
                {
                        return 0;
                }
        }
        
        /**
         * Creates the model planes that appear on the map and then inputs them on screen for user to track based on the flight path they will be traveling.
         */
        public void createFlight(Pane p, Label lb)
        {
                if(p == null || lb == null)
                {
                        throw new NoSuchElementException("ERROR Pane or Label set to null");
                }
                //List<FlightNode> ls = loadFile(airports.csv);
                List<String> anws = StoreAirports(airport1, airport2);
                //anws = backEnd.getPath();
                Image im2 = null;
                try
        {
                im2 = new Image(new FileInputStream("D:\\Eclipse\\eclipse-workspace\\ProjectThree\\csvFolder/plane.png"));
        }
        catch(Exception e)
        {
                System.out.println("Error with image");
        }

                for(int i = 0; i < anws.size(); i++)
                {
                        String curr = anws.get(i);
                        switch(curr)
                        {
                                case "DEN":
                                        ImageView imageView = new ImageView(im2);
                                        imageView.setX(212);
                                imageView.setY(179);
                                imageView.setFitWidth(30);
                                imageView.setPreserveRatio(true);
                                imageView.setViewOrder(0.0);
                                p.getChildren().add(imageView);
                                        break;

                                case "LAS":
                                        ImageView imageView2 = new ImageView(im2);
                                        imageView2.setX(120);
                                imageView2.setY(210);
                                imageView2.setFitWidth(30);
                                imageView2.setPreserveRatio(true);
                                imageView2.setViewOrder(0.0);
                                p.getChildren().add(imageView2);
                                        break;

                                case "LAX":
                                        ImageView imageView3 = new ImageView(im2);
                                        imageView3.setX(85);
                                imageView3.setY(220);
                                imageView3.setFitWidth(30);
                                imageView3.setPreserveRatio(true);
                                imageView3.setViewOrder(0.0);
                                p.getChildren().add(imageView3);
                                        break;

                                case "SFO":
                                        ImageView imageView4 = new ImageView(im2);
                                        imageView4.setX(50);
                                imageView4.setY(170);
                                imageView4.setFitWidth(30);
                                imageView4.setPreserveRatio(true);
                                imageView4.setViewOrder(0.0);
                                p.getChildren().add(imageView4);
                                        break;

                                case "PHX":
                                        ImageView imageView5 = new ImageView(im2);
                                        imageView5.setX(131);
                                imageView5.setY(240);
                                imageView5.setFitWidth(30);
                                imageView5.setPreserveRatio(true);
                                imageView5.setViewOrder(0.0);
                                p.getChildren().add(imageView5);
                                        break;
                                case "DFW":
                                  ImageView imageView6 = new ImageView(im2);
                                  imageView6.setX(287);
                          imageView6.setY(267);
                          imageView6.setFitWidth(30);
                          imageView6.setPreserveRatio(true);
                          imageView6.setViewOrder(0.0);
                          p.getChildren().add(imageView6);
                                  break;
                          case "IAH":
                                  ImageView imageView7 = new ImageView(im2);
                                  imageView7.setX(313);
                          imageView7.setY(295);
                          imageView7.setFitWidth(30);
                          imageView7.setPreserveRatio(true);
                          imageView7.setViewOrder(0.0);
                          p.getChildren().add(imageView7);
                                  break;
                          case "ATL":
                                  ImageView imageView8 = new ImageView(im2);
                                  imageView8.setX(413);
                          imageView8.setY(247);
                          imageView8.setFitWidth(30);
                          imageView8.setPreserveRatio(true);
                          imageView8.setViewOrder(0.0);
                          p.getChildren().add(imageView8);
                                  break;
                          case "ORD":
                                  ImageView imageView9 = new ImageView(im2);
                                  imageView9.setX(375);
                          imageView9.setY(150);
                          imageView9.setFitWidth(30);
                          imageView9.setPreserveRatio(true);
                          imageView9.setViewOrder(0.0);
                          p.getChildren().add(imageView9);
                                  break;
                          case "JFK":
                                  ImageView imageView10 = new ImageView(im2);
                                  imageView10.setX(500);
                          imageView10.setY(140);
                          imageView10.setFitWidth(30);
                          imageView10.setPreserveRatio(true);
                          imageView10.setViewOrder(0.0);
                          p.getChildren().add(imageView10);
                                  break;
                          default:
                                  System.out.println("ERROR: No airports with said abreviation");
                                  break;
                  }
          }
          System.out.println(anws.toString());
          lb.setText(airport1 + " to " + airport2 + ", Showing Path: " + anws.toString() + " Cost: " + cost);
  }
        /**
         * Everytime reset button is pressed goes or user inputs two new airports, goes to this method which resets the map and removes old path, cost, and planes.
         */
        public void resetCreateFlight(Pane p, BorderPane bp)
        {
                Image im = null;

        try
        {
                im = new Image(new FileInputStream("D:\\Eclipse\\eclipse-workspace\\ProjectThree\\csvFolder/us-busiest-airports.jpg"));
        }
        catch(Exception e)
        {
                System.out.println("Error with image");
        }
        ImageView imageView = new ImageView(im);
        imageView.setX(10);
        imageView.setY(10);
        imageView.setFitWidth(575);
        imageView.setPreserveRatio(true);
        imageView.setViewOrder(1.0);

        p.getChildren().clear();
        p.getChildren().addAll(imageView);

        bp.setCenter(p);


        }

        /**
         * SUDO main method that runs the front end interface when called 
         */
    public void run(FlightDataBackEnd engine) {
                gp = engine;
        Application.launch();
    }
}