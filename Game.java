/**
 * Game Class - Primary game logic for a Java-based Processing Game
 * @author Syed Rahman
 * @author Krishnendu Barman 
 * @version 6/12/25
 * No need to create PImage for bg
 */

//import processing.sound.*;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;


public class Game extends PApplet{

  //------------------ GAME VARIABLES --------------------//

  // VARIABLES: Processing variable to do Processing things
  PApplet p;

  // VARIABLES: Title Bar
  String titleText = "Carcing";
  String extraText = "CurrentLevel?";
  String name = "Track 1";

  // VARIABLES: Whole Game
  AnimatedSprite runningHorse;
  boolean doAnimation;

  // VARIABLES: splashScreen
  Screen splashScreen;
  String splashBgFile = "images/startingScreen.jpg";
  //SoundFile song;

  // VARIABLES: world1Grid Screen
  World world1;
  String world1BgFile = "images/carbg.png";
  PImage world1Bg;
  Sprite car; 
  String carObject = "images/carObject.png"; 
  Sprite barrier;
  String barrierObject = "images/barrierObject.png";
  int health = 3;
  Button b1;
  Sprite barrier1;
  Sprite barrier2;


  // VARIABLES: endScreen
  World endScreen;
  String endBgFile = "images/gameOver.png";


  // VARIABLES: Tracking the current Screen being displayed
  Screen currentScreen;
  CycleTimer slowCycleTimer;

  boolean start = true;


  //------------------ REQUIRED PROCESSING METHODS --------------------//

  // Processing method that runs once for screen resolution settings
  public void settings() {
    //SETUP: Match the screen size to the background image size
    size(600,700);  //these will automatically be saved as width & height

    // Allows p variable to be used by other classes to access PApplet methods
    p = this;
    
  }

  //Required Processing method that gets run once
  public void setup() {

    //SETUP: Set the title on the title bar
    surface.setTitle(titleText);
    p.imageMode(PConstants.CORNER);    //Set Images to read coordinates at corners

    //SETUP: World1
    splashScreen = new Screen(this, "splash", splashBgFile);
    world1 = new World(p, "track", world1BgFile, 3.5f, -65.0f, 0.0f); //moveable World constructor --> defines center & scale (x, scale, y)???
    // System.out.println( "World constructed: " + Util.toStringPImage(world1.getBgImage()));
    endScreen = new World(this, "end", endBgFile);
    currentScreen = splashScreen;

    //SETUP: Construct Game objects used in All Screens
    runningHorse = new AnimatedSprite(p, "sprites/horse_run.png", "sprites/horse_run.json", 50.0f, 75.0f, 1.0f);

    //SETUP: Setup more world1 objects
    car = new Sprite (p, "images/carObject.png", 1.0f, 175, 475);
    car.resize(100, 200);
    world1.addSprite(car);
    barrier = new Sprite(p, "Images/barrierObject.png" );
    world1.addSprite(barrier1);
    world1.addSpriteCopyTo(barrier1, 150,-50);
    barrier1 = new Sprite(p, "Images/barrierObject.png" );
    
  

    //SETUP: Sound
    // Load a soundfile from the sounds folder of the sketch and play it back
     //song = new SoundFile(this, "sounds/Lenny_Kravitz_Fly_Away.mp3");
     //song.play();
    
    System.out.println("Game started...");

  } //end setup()


  //Required Processing method that automatically loops
  //(Anything wwn on the screen should be called from here)
  public void draw() {

    // DRAW LOOP: Update Screen Visuals
    updateTitleBar();
    updateScreen();

    // DRAW LOOP: Set Timers
    int cycleTime = 1;  //milliseconds
    int slowCycleTime = 300;  //milliseconds
    if(slowCycleTimer == null){
      slowCycleTimer = new CycleTimer(p, slowCycleTime);
    }

    // DRAW LOOP: Populate & Move Sprites
    if(slowCycleTimer.isDone()){
      populateSprites();
      moveSprites();
    }

    // DRAW LOOP: Pause Game Cycle
    currentScreen.pause(cycleTime);   // slows down the game cycles

    // DRAW LOOP: Check for end of game
    if(isGameOver()){
      endGame();
    }


  } //end draw()

  //------------------ USER INPUT METHODS --------------------//


  //Known Processing method that automatically will run whenever a key is pressed
  public void keyPressed(){

    //check what key was pressed
    System.out.println("\nKey pressed: " + p.keyCode); //key gives you a character for the key pressed

    //What to do when a key is pressed?
    
    //KEYS FOR world1
    if(currentScreen == world1){

      //set [S] key to move the chick down & avoid Out-of-Bounds errors
      if(p.keyCode == 83){        


      }

      // if the 'n' key is pressed, ask for their name
      if(p.key == 'n'){
        name = Input.getString("What is your name?");
      }

      // if the 't' key is pressed, then toggle the animation on/off
      if(p.key == 't'){
        //Toggle the animation on & off
        doAnimation = !doAnimation;
        System.out.println("doAnimation: " + doAnimation);
      }


      if (p.keyCode == 39){
        car.move(200f, 0f);
      }
   
      if (p.keyCode == 37){
        car.move(-200f, 0f);
        }
   
    }



    //CHANGING SCREENS BASED ON KEYS
    //change to world1 if 1 key pressed, level2 if 2 key is pressed
    if(p.key == '1'){
      currentScreen = world1;
    } else if(p.key == 'e'){
      currentScreen = endScreen;
    }

  }

  // Known Processing method that automatically will run when a mouse click triggers it
  public void mouseClicked(){
    
    // Print coordinates of mouse click
    System.out.println("\nMouse was clicked at (" + p.mouseX + "," + p.mouseY + ")");

    // Display color of pixel clicked
    int color = p.get(p.mouseX, p.mouseY);
    PColor.printPColor(p, color);

    // what to do if clicked? (ex. assign a new location to player1)
    if(currentScreen == world1){

    }

   if (currentScreen == splashScreen){
    currentScreen = world1;
   }

  }



  //------------------ CUSTOM  GAME METHODS --------------------//

  // Updates the title bar of the Game
  public void updateTitleBar(){

    if(!isGameOver()) {

      extraText = currentScreen.getName();

      //set the title each loop
      surface.setTitle(titleText + "\t// CurrentScreen: " + extraText + " \t // Name: " + name + "\t // Health: " + health );

      //adjust the extra text as desired
    
    }
  }

  // Updates what is drawn on the screen each frame
  public void updateScreen(){

    // UPDATE: first lay down the Background
    currentScreen.showBg();

    // UPDATE: splashScreen
    if(currentScreen == splashScreen){

      // Print an s in console when splashscreen is up
      System.out.print("s");

      // Change the screen to level 1 between 3 and 5 seconds
      // if(splashScreen.getScreenTime() > 3000 && splashScreen.getScreenTime() < 5000){
      //   currentScreen = world1;
      // }
    }

    // UPDATE: world1 Screen
    if(currentScreen == world1){

      // Print a '1' in console when world1
      System.out.print("1");



      // world1.moveBgXY(-3.0f, 0f);
      // chick.show();

    }

    // UPDATE: End Screen
    if(currentScreen == endScreen){
      System.out.print("e");

    }

    // UPDATE: Any Screen
    if(doAnimation){
      runningHorse.animateHorizontal(5.0f, 10.0f, true);
    }

    // UPDATE: Other built-in to current World/Grid/HexGrid
    currentScreen.show();

  }

  // Populates enemies or other sprites on the Screen
  public void populateSprites(){

    // Decide which lanes to populate (ex. 0, 2)


    // copy barrier object to specific location (Use copySprite() )
    // barrier 1 = 
    // barrier 2 = 
      

  }

  // Moves around the enemies/sprites on the Screen
  public void moveSprites(){

    //tell barrier 1 and 2 to move
    barrier1.move(0f, 1f);


    // what happens if you touch the car?


    // what happens when it reaches below the bottom of the screen
    

  }

 

  // Indicates when the main game is over
  public boolean isGameOver(){

    // if health = 0 --> return true
    
    return false; //by default, the game is never over
  }

  // Describes what happens after the game is over
  public void endGame(){
      System.out.println("Game Over!");

      // Update the title bar

      // Show any end imagery
      currentScreen = endScreen;

  }


} // end of Game class
