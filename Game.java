/* Game Class Starter File
 * Authors: Syed Rahman, Krishnendu Barman 
 * Last Edit: 5/15/25
 * using new Screen show method
 */

//import processing.sound.*;
import processing.core.PApplet;
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
  PImage splashBg;
  String splashBgFile = "images/startingScreen.jpg";
  //SoundFile song;

  // VARIABLES: world1Grid Screen
  World world1;
  String world1BgFile = "images/carbg.png";
  PImage world1Bg;
  String player1File = "images/carObject.png"; 
  PImage player1;   // Use PImage to display the image in a GridLocation
  AnimatedSprite chick;
  String chickFile = "sprites/chick_walk.png";
  String chickJson = "sprites/chick_walk.json";
  Sprite car; 
  String carObject = "images/carObject.png"; 
  Sprite barrier;
  String barrierObject = "images/barrierObject.png";
  int player1Row = 3;
  int player1Col = 0;
  int player2Row = 5;
  int player2Col = 5;
  int health = 3;
  Button b1;



  // VARIABLES: endScreen
  World endScreen;
  String endBgFile = "images/gameOver.png";
  PImage endBg;


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

    p.imageMode(p.CORNER);    //Set Images to read coordinates at corners
    //fullScreen();   //only use if not using a specfic bg image
    
    //SETUP: Set the title on the title bar
    surface.setTitle(titleText);

    //SETUP: Load BG images used in all screens
    splashBg = p.loadImage(splashBgFile);
    world1Bg = p.loadImage(world1BgFile);
    endBg = p.loadImage(endBgFile);

    //SETUP: If non-moving, Resize all BG images to exactly match the screen size
    splashBg.resize(p.width, p.height);
    endBg.resize(p.width, p.height);   
    world1Bg.resize(p.width, p.height);

    //SETUP: World1
    splashScreen = new Screen(this, "splash", splashBg);
    world1 = new World(p, "track", world1BgFile, 3.5f, -65.0f, 0.0f); //moveable World constructor --> defines center & scale (x, scale, y)???
    // System.out.println( "World constructed: " + Util.toStringPImage(world1.getBgImage()));
    endScreen = new World(this, "end", endBg);
    currentScreen = splashScreen;

    //SETUP: Construct Game objects used in All Screens
    runningHorse = new AnimatedSprite(p, "sprites/horse_run.png", "sprites/horse_run.json", 50.0f, 75.0f, 1.0f);

    //SETUP: Setup more world1 objects
    chick = new AnimatedSprite(p, chickFile, chickJson, 0.0f, 0.0f, 0.5f);
    car = new Sprite (p, "images/carObject.png", 1.0f, 175, 475);
    car.resize(100, 200);
    world1.addSprite(car);
    barrier = new Sprite(p, "Images/barrierObject.png" );
    world1.addSprite(barrier);
  

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
    barrier.move(0f, 1f);

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

    //What is the index for the last column?
    

    //Loop through all the rows in the last column

      //Generate a random number


      //10% of the time, decide to add an enemy image to a Tile
      

  }

  // Moves around the enemies/sprites on the Screen
  public void moveSprites(){

    //Loop through all of the rows & cols in the grid

        //Store the current GridLocation

        //Store the next GridLocation

        //Check if the current tile has an image that is not piece1      


          //Get image/sprite from current location
            

          //CASE 1: Collision with piece1


          //CASE 2: Move enemy over to new location


          //Erase image/sprite from old location

          //System.out.println(loc + " " + grid.hasTileImage(loc));

            
        //CASE 3: Enemy leaves screen at first column

  }

  // Checks if there is a collision between Sprites on the Screen
  public boolean checkCollision(GridLocation loc, GridLocation nextLoc){

    //Check what image/sprite is stored in the CURRENT location
    // PImage image = grid.getTileImage(loc);
    // AnimatedSprite sprite = grid.getTileSprite(loc);

    //if empty --> no collision

    //Check what image/sprite is stored in the NEXT location

    //if empty --> no collision

    //check if enemy runs into player

      //clear out the enemy if it hits the player (using cleartTileImage() or clearTileSprite() from Grid class)

      //Update status variable

    //check if a player collides into enemy

    return false; //<--default return
  }

  // Indicates when the main game is over
  public boolean isGameOver(){
    
    return false; //by default, the game is never over
  }

  // Describes what happens after the game is over
  public void endGame(){
      System.out.println("Game Over!");

      // Update the title bar

      // Show any end imagery
      currentScreen = endScreen;

  }


} //close class
