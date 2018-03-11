import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LangtonsAnt extends Application{
	public char[][] map;
	public int size = 100;
	public double h = 1000;
	public double w = 1500;
	public ArrayList<Ant> list = new ArrayList<>();
	public ObservableList<Node> children;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane main = new BorderPane();
		
		main.setCenter(CreateMap());
		
		HBox bottom = new HBox(5);
		bottom.setAlignment(Pos.CENTER);
		Timeline animation = new Timeline(new KeyFrame(Duration.millis(1),(e)->{playAction();}));
		
		Button play = new Button("Play");
		play.setOnAction((e) ->{
			animation.stop();
			playAction();
		});
		
		
		Button playplus = new Button("Play++");
		
		playplus.setOnAction((e) ->{
				animation.setCycleCount(Timeline.INDEFINITE);
				animation.play();
		});
		
		Button stop = new Button("Stop");
		
		stop.setOnAction((e) -> {
			animation.stop();
		});
		
		Button clean = new Button("Clean");
		
		clean.setOnAction((e)-> {
			animation.stop();
			main.setCenter(null);
			main.setCenter(CreateMap());
			list = new ArrayList<>();
		});
		
		bottom.getChildren().addAll(play, playplus, stop, clean);
		
		main.setBottom(bottom);
		Scene scene = new Scene(main);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Langston's Ant");
		primaryStage.show();
	}


	private void playAction() {
		ArrayList<Ant> remove = new ArrayList<>();
		for (Ant ant : list) {
			int x = ant.x, y = ant.y;
			if(map[x][y] == 'b') {
				map[x][y] = 'r';
				((Shape) children.get(x * size + y)).setFill(Color.WHITE);
				ant.direction = turnToLeft(ant.direction);
			}
			else {
				map[x][y] = 'b';
				((Shape) children.get(x * size + y)).setFill(Color.BLACK);
				ant.direction = turnToRigth(ant.direction);
			}
			if(move(ant))
				((Shape) children.get(ant.x * size + ant.y)).setFill(Color.RED);
			else
				remove.add(ant);
		}
		
		list.removeAll(remove);
	}


	private boolean move(Ant ant) {
		int x = ant.x, y = ant.y;
		dir direction = ant.direction;
		if(direction == dir.NORTH) {
			
			if(x >= 1)
				ant.x = x - 1;
			else
				ant.x = size - 1;
		} else if(direction == dir.WEST) {
			if(y >= 1)
				ant.y = y - 1;
			else
				ant.y = size - 1;
		} else if(direction == dir.SOUTH) {
			if(x < size - 1)
				ant.x = x + 1;
			else
				ant.x = 0;
		}
		else if(direction == dir.EAST) {
			if(y < size - 1)	
				ant.y = y + 1;
			else
				ant.y = 0;
		}
		return true;
	}


	private dir turnToRigth(dir direction) {
		switch (direction) {
		case NORTH:
			return dir.EAST;
		case EAST:
			return dir.SOUTH;
		case SOUTH:
			return dir.WEST;
		case WEST:
			return dir.NORTH;
		}
		return null;
	}


	private dir turnToLeft(dir direction) {
		switch (direction) {
		case NORTH:
			return dir.WEST;
		case WEST:
			return dir.SOUTH;
		case SOUTH:
			return dir.EAST;
		case EAST:
			return dir.NORTH;
		}
		return null;
	}


	private Node CreateMap() {
		GridPane pane = new GridPane();
//		pane.setVgap(2);
//		pane.setHgap(2);
		map = new char[size][size];
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				map[i][j] = 'b';
				Rectangle rec = new Rectangle(h / size, h / size, Color.BLACK);
				pane.add(rec, j, i);
				
				rec.setOnMouseClicked((e)->{
					int x = GridPane.getRowIndex((Node)e.getSource());
					int y = GridPane.getColumnIndex((Node)e.getSource());
					
					System.out.println(x + "   " + y);
					if(!rec.getFill().equals(Color.RED)) {
						createAnt(x,y, rec);
						rec.setFill(Color.RED);
					}
				});
			}
		}
		
		children = pane.getChildren();		
		return pane;
	}

	
	private void createAnt(int x, int y, Rectangle rec) {
		list.add(new Ant(x,y));
	}


	public static void main(String[] args) {
		Application.launch(args);
	}
}
class Ant{
	public dir direction;
	public int x, y;
	public Ant(int x, int y) {
		direction = dir.NORTH;
		this.x = x;
		this.y = y;
	}
	
}

enum dir{NORTH, SOUTH, EAST, WEST};