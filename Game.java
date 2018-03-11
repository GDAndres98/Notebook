
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Game extends Application{
	public static int[] x = {1,-1,0,0,1,1,-1,-1};
	public static int[] y = {0,0,-1,1,1,-1,1,-1};
	public static int size = 100;
	public int h = 1000;
	
	public boolean[][] matrix;
	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane pane = new BorderPane();
		
		
		
		matrix = new boolean[size][size];
		
		createMap(matrix, pane);
		
		VBox box = new VBox(60);
		box.setPadding(new Insets(10));
		box.setAlignment(Pos.CENTER);
		
		Button play = new Button("Play");
		play.setPadding(new Insets(10));
		
		play.setOnAction(createEvent(pane));
		
		Timeline animation = new Timeline(new KeyFrame(Duration.millis(50), createEvent(pane)));
		
		
		Button playplus = new Button("Play++");
		playplus.setPadding(new Insets(10));
		
		playplus.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				
				animation.setCycleCount(Timeline.INDEFINITE);
				animation.play();
			}
		});
		
		Button stop = new Button("Stop");
		stop.setPadding(new Insets(10));
		
		stop.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				animation.stop();
			}
		});
		
		
		Button clean = new Button("Clean");
		clean.setPadding(new Insets(10));
		clean.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				animation.stop();
				createMap(new boolean[size][size], pane);
			}
		});
		
		box.getChildren().addAll(play, playplus, stop, clean);
		
		pane.setRight(box);
		BorderPane.setAlignment(pane.getRight(), Pos.CENTER);
		
		Scene scene = new Scene(pane);
		
		scene.setOnMouseDragged(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				Pane p = (Pane) pane.getCenter();
				int i = (int) Math.floor(event.getX() * matrix.length / h);
				int j = (int) Math.floor(event.getY() * matrix[0].length / h);
				matrix[i][j] = true;
				
				Rectangle rec = (Rectangle) p.getChildren().get(i * matrix.length + j);
				rec.setFill(Color.WHITE);
				System.out.println(event.getX() + "   " + event.getY());
			}
		});
		primaryStage.setScene(scene);
		primaryStage.setTitle("Game of life");
		primaryStage.show();
	}

	private void createMap(boolean[][] a, BorderPane p) {
		matrix = a;
		Pane pane = new Pane();
		pane.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
		double w = h;
		for(int i = 0; i < a.length; i++) {
			for(int j = 0; j < a[0].length; j++) {
				Rectangle rec = new Rectangle();
				rec.setX(w / a.length * i);
				rec.setY(h / a[0].length * j);
				rec.setHeight(h/a.length);
				rec.setWidth(h/a.length);
				rec.setFill(!a[i][j]? Color.BLACK: Color.WHITE);
//				rec.setStroke(Color.WHITE);
				

				rec.setOnMouseClicked(new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent event) {
						rec.setFill(Color.WHITE);
						System.out.println(event.getX() + "   " + event.getY());
						int i = (int) Math.floor(event.getX() * a.length / h);
						int j = (int) Math.floor(event.getY() * a[0].length / w);
						a[i][j] = true;
						
//						for(int l = 0; l < a.length; l++)
//							System.out.println(Arrays.toString(a[l]));
					}
				});
	
				pane.setOnMouseDragged(new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent event) {
						
						int i = (int) Math.floor(event.getX() * size / h);
						int j = (int) Math.floor(event.getY() * size / w);
						a[i][j] = true;
						
						Rectangle rec = (Rectangle) pane.getChildren().get(i * size + j);
						rec.setFill(Color.WHITE);
						System.out.println(event.getX() + "   " + event.getY());
					}
				});
				pane.getChildren().add(rec);
			}
		}

		p.setCenter(pane);
	}
	
	public EventHandler<ActionEvent> createEvent(BorderPane pane){
		EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				boolean[][] a = new boolean[size][size];
				for(int i = 0; i < a.length; i++) {
					for(int j = 0; j < a[0].length; j++) {
						int d = 0;
						for(int k = 0; k < x.length; k++) {
							if(matrix[f(i + x[k])][f(j + y[k])])
								d++;
						}
						
						if(!matrix[i][j] && d == 3) a[i][j] = true;
						if(matrix[i][j]) {
							if(d == 2 || d == 3) a[i][j] = true;
							else a[i][j] = false;
						}
					}
				}
				createMap(a, pane);
			}

			private int f(int i) {
				if(i == -1) return size -1;
				if(i == size) return 0;
				return i;
			}
		};
		return event;
	}

	public static void main(String[] args) {
		Application.launch(args);
	}
}
