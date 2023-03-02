package game;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalTime;
import java.util.Map.Entry;

import game.Actor.Act;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

@SuppressWarnings("exports")
public class DungeonCrawlerController {

	@FXML
	private TextField nameTxt;

	@FXML
	private Canvas scene_canvas;
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	private File save_file = new File("DefaultSave.DCC");
	private File map_file = new File("DefaultMap.txt");
	
	public static StageState current_stage;
	public static GameState game_state;
	
	public static TileMap game_map;
	private static Player player_character;
	// initialize our listener
	
	public void gameInfo(String prnt_str) {
		System.out.println("[GAME LOG " + LocalTime.now() + "] " + prnt_str);
	}
	
	public void playerInfo(String prnt_str) {
		System.out.println("[Player LOG " + LocalTime.now() + "] " + prnt_str);
	}
	
	@FXML
	public void initialize() {
		// "this" corresponds to DungeonCrawlerController, but we don't use it because it's a static variable
		DungeonCrawlerController.game_map = new TileMap(15, 15);
		DungeonCrawlerController.current_stage = StageState.START;
		DungeonCrawlerController.game_state = GameState.PLAYERMOVE;
	}
	@FXML
	void loadLevel1(ActionEvent event) throws Throwable {
		String name = nameTxt.getText();
		loadScene(event, "level1.fxml");
		
		for (Object ob : root.getChildrenUnmodifiable()) {
			if (ob instanceof Canvas) {
				this.scene_canvas = (Canvas) ob;
			}
		}
		// this is the only way out of start, so we'll change scene here
		boolean proper_save = loadSaveFile();
		if (!proper_save) {
			player_character = new Player(name, new Coordinate(0, 0), 100, 10, 1, 4);
			DungeonCrawlerController.game_map.setActor(new Coordinate(2, 2), player_character);
		}
		current_stage = StageState.GAME;
		Render();
	}
	
	@FXML
	void loadSave() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		File t_file = fileChooser.showOpenDialog(stage);
		if (t_file != null) {
			save_file = t_file; 
			gameInfo("File " + save_file.getName() + " was selected");
		}
	}
	
	@FXML
	void loadMap() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		File t_file = fileChooser.showOpenDialog(stage);
		if (t_file != null) {
			map_file = t_file; 
			gameInfo("File " + map_file.getName() + " was selected");
		}
	}
	
	@FXML
	void loadScene(ActionEvent event, String sceneName) throws IOException {
		// move the scene loading code;
		// I do this because I plan to have a third, inventory scene. It's nice to be able to easily swap around,
		// and we conveniently already have the code lol. 
		root = FXMLLoader.load(getClass().getResource(sceneName));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		scene.addEventFilter(KeyEvent.KEY_TYPED,  
		        scene_event -> {
					try {
						Update(scene_event);
					} catch (Throwable e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
		);
	}
	
	// Add a method to load an inventory screen displaying all character objects? 

	
	
	/*
	 * 
	 * Because we decided to let player input act as our "tick" a gameloop
	 * 		is actually extremely easy. The gameloop decides when and how stuff
	 * 		happens. What we can do is only run a step of the gameloop when there
	 * 		is some sort of user interaction. We will call this function Update
	 * 
	 * It'd be nice to implement a simple state machine to host our game, so let's
	 * 		do that. A state machine is something that keeps track of an internal
	 * 		state, and uses that state to consider how it evaluates a symbol or 
	 * 		expression. 
	 * 
	 * We use an enum for our states. An enum is a type that consists of multiple
	 * 		constant values. Thus, we can use the internal names of the enum, but
	 * 		the values are numeric and don't change. This just helps with making 
	 * 		switch statements, if statements, and other value or flag controls not
	 * 		have "magic numbers" - numbers with no obvious meaning. 
	 */
	
	public static void Update(KeyEvent event_in) throws Throwable {
		switch (current_stage) {
			case GAME:
				current_stage = GameLoop(event_in);
				Render();
				break;
				// If we're on the game stage, send all input to the gameloop
			case INVENTORY:
				current_stage = InventoryLoop(event_in);
				// if we're on the inventory stage, send all input to the inventory loop
				break;
			case START:
				break;
				// if we're on the start stage, just ignore it lol
			default:
				break;
			}
	}
	
	public StageState GameLoop(KeyEvent event_in) {
		StageState NextStage = DungeonCrawlerController.current_stage; // the stage we're going to return
		/* 
		 * Let's define the controls we care about:
		 * 		W - movement forward/up
		 * 		A - movement left
		 * 		S - movement down/back
		 * 		D - movement right
		 * 		I - open the inventory
		 * 		?A - Possibly an attack option event
		 * 		J - wait/do nothing
		 */
		
		switch(DungeonCrawlerController.game_state) {
			case COMBAT:
				break;
			case DEATH:
				// if the player is dead we just want to consume their input unless it is the restart button
				break;
			case ENEMYMOVE:
				ActorTick();
				System.out.println("Enemies did stuff");
				NextStage = StageState.GAME;
				DungeonCrawlerController.game_state = GameState.PLAYERMOVE;
				break;
			case PLAYERMOVE:
				NextStage = PlayerAction(event_in);
				break;
			default:
				break;
		}
		
		return NextStage; 
		// we return a StageState, because that helps 
		// make clear what the next stage will be. 
	}
	
	public StageState PlayerAction(KeyEvent event_in) {
		StageState NextStage = DungeonCrawlerController.current_stage;
		System.out.println(DungeonCrawlerController.player_character);
		Decision move_decision = null;
		System.out.println(event_in.getCharacter().toUpperCase());
		switch (event_in.getCharacter().toUpperCase().charAt(0)) {
			case 'W':
				// move up
				move_decision = game_map.requestMove(player_character.Position.Forward(), player_character);
				if (move_decision instanceof Accept)
					DungeonCrawlerController.game_map.map_position = DungeonCrawlerController.game_map.map_position.Backward();
				NextStage = StageState.GAME;
				break;
			case 'A':
				// move right
				move_decision = game_map.requestMove(player_character.Position.Left(), player_character);
				if (move_decision instanceof Accept)
					DungeonCrawlerController.game_map.map_position = DungeonCrawlerController.game_map.map_position.Right();
				NextStage = StageState.GAME;
				break;
			case 'S':
				// move left
				move_decision = game_map.requestMove(player_character.Position.Backward(), player_character);
				if (move_decision instanceof Accept)
					DungeonCrawlerController.game_map.map_position = DungeonCrawlerController.game_map.map_position.Forward();
				NextStage = StageState.GAME;
				break;
			case 'D':
				// move down!
				move_decision = game_map.requestMove(player_character.Position.Right(), player_character);
				if (move_decision instanceof Accept)
					DungeonCrawlerController.game_map.map_position = DungeonCrawlerController.game_map.map_position.Left();
				NextStage = StageState.GAME;
				break;
			case 'I': 
				// open inventory
				// set to inventory stage
				NextStage = StageState.INVENTORY;
				break;
			case 'P':
				saveFile(); 
				NextStage = StageState.GAME;
			default:
				System.out.println("Nothing ran");
		}
		if (move_decision != null) {
			// this means it was a movement 
			if (move_decision.try_again) {
				DungeonCrawlerController.game_state = GameState.PLAYERMOVE;
			} else {
				DungeonCrawlerController.game_state = GameState.ENEMYMOVE;
			}
		}
		
		return NextStage;
	}
	
	public void ActorTick() {
		for (Entry<Coordinate, Actor> map_entry : DungeonCrawlerController.game_map.Tiles.entrySet()) {
			if (map_entry.getValue().frameActs != null) {
				for (Act performance : map_entry.getValue().frameActs) {
					performance.perform();
				}
			}
		}
	}
	
	public StageState InventoryLoop(KeyEvent event_in) {
		StageState NextStage = DungeonCrawlerController.current_stage;
		//TODO
		return NextStage;
	}
	
	
	public void Render() throws Throwable {
		int scale_factor = 50; // how much to multiply the units. 
		// This could easily be controlled by mouse or something to allow zooming
		byte board_moves = 0; // switch, 0 or 1
		
		GraphicsContext gc = scene_canvas.getGraphicsContext2D();
		int board_x = DungeonCrawlerController.game_map.map_position.getX() * scale_factor;
		int board_y = DungeonCrawlerController.game_map.map_position.getY() * scale_factor;
		gc.clearRect(0, 0, scene_canvas.getWidth(), scene_canvas.getHeight());
		// or modified to match the size of the board and everything else. 
		switch(board_moves) { // I decided to add a moving board after, lol. 
			case 0: 
				for (int i = 0; i< DungeonCrawlerController.game_map.x; i++) {
					for(int j = 0; j< DungeonCrawlerController.game_map.y; j++) {
						gc.drawImage(DungeonCrawlerController.game_map.getActor(new Coordinate(i, j)).sprite
								, i*scale_factor, j*scale_factor,
								scale_factor, scale_factor);
						
						
						// The first argument is the image, the next two are the coordinates to draw at, and the next two are the scale. 
					}
				}
				break;
			case 1:
				for (int i = 0; i< DungeonCrawlerController.game_map.x; i++) {
					for(int j = 0; j< DungeonCrawlerController.game_map.y; j++) {
						gc.drawImage(DungeonCrawlerController.game_map.getActor(new Coordinate(i, j)).sprite
								, board_x + i*scale_factor, board_y + j*scale_factor,
								scale_factor, scale_factor);
						
						
						// The first argument is the image, the next two are the coordinates to draw at, and the next two are the scale. 
					}
				}
				break;
			default: 
				throw new Throwable("Invalid board movement state");
		}
		
		
	}



	public boolean loadSaveFile() {
		
		FileInputStream fis = null;
		ObjectInputStream ois = null; 
		try {
			fis = new FileInputStream(save_file);
			ois = new ObjectInputStream(fis);
			SaveType outobj = (SaveType) ois.readObject();
		
			
			System.out.println("Save file " + save_file.getName() + " found, loading " 
					+ outobj.player.name + " with " + outobj.seconds_played + " of playtime");
			
			DungeonCrawlerController.player_character = outobj.player;
			DungeonCrawlerController.player_character.sprite = new Image(outobj.player.img_url);
			
			ois.close();
			fis.close();
			return true;
		} catch (IOException ex) {
			ex.printStackTrace();
			return false;
		} catch (ClassNotFoundException ex) {
			System.out.println("Invalid save file, defaulting.");
			try {
				ois.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
			
		}
	}
	
	public boolean saveFile() {
		SaveType Save = new SaveType(DungeonCrawlerController.player_character, LocalTime.now().getSecond());
		// insecure method, but we'll use it. 
		
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = new FileOutputStream(this.save_file);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(Save);
			oos.close();
			fos.close();
			System.out.println("Save success");
			return true;
		} catch(IOException ex) {
			ex.printStackTrace();
			return false;
		}
		
	}
}
















