// Inspired By: https://twitter.com/beesandbombs/status/1019924265540431872

// Globals
// *************************************************************************************************
int[] $gridWidthArray = {10, 20, 50, 100};
int $gridWidthArrayIndex = 1;
int $gridWidth = $gridWidthArray[$gridWidthArrayIndex]; //10, 20, 30, 50 for(600,600) canvas size
boolean $grid = true;
boolean $debug = false;
boolean $animating = false;
int $fRate = 60; // 40 seems to be smoothest for animation
int $strokeWeight = 3;
int $borderWidth = 100;
int $cols, $rows;

Director director; // create a Director object to handle interactions between elements

// Helper Methods
// *************************************************************************************************
void calculateProperties() {
  $cols = (width-2*$borderWidth)/$gridWidth;
  $rows = (height-2*$borderWidth)/$gridWidth;
}

void prepareAndCreateDirector(int newGridWidthArrayIndex) {
  background(25);
  $gridWidthArrayIndex = newGridWidthArrayIndex;
  $gridWidth = $gridWidthArray[$gridWidthArrayIndex];
  println("gridWidth: " + $gridWidth);
  println("gridWidthArrayIndex: " + $gridWidthArrayIndex);

  if ($grid) { 
    drawGrid($gridWidth);
  }; // show grid

  calculateProperties();
  director = new Director();
}

// Setup
// *************************************************************************************************
void setup() {
  size(800, 800, P2D);
  background(25);
  smooth(8);
  rectMode(CORNER);
  stroke(250);
  strokeWeight($strokeWeight);
  frameRate($fRate);
  smooth(8);

  calculateProperties();

  if ($grid) { 
    drawGrid($gridWidth);
  };

  director = new Director();
}

// Test segments to show how $animating works
Segment t1 = new Segment($gridWidth, $gridWidth);
Segment t2 = new Segment($gridWidth*2, $gridWidth);
Segment t3 = new Segment($gridWidth*3, $gridWidth);

// used in demo animation
float i = 0;
float delta = 0.1;
float speed = 0.055;
boolean loop = false;
boolean print = false;

int wait =75;
int counter = wait;

// Draw
// *************************************************************************************************
void draw() {
  background(25);
  // This is where you can start with building your animation code
  // This does not involve easing (read about it: https://processing.org/examples/easing.html)
  // and as such, its pretty clunky. You'll likely want to build a new
  // animation algorithm
  if ($animating) {
    if ($grid) { 
      drawGrid($gridWidth);
    };

    pushMatrix();
    translate($borderWidth, $borderWidth);
    for (int i = 0; i<$cols*$rows; i++) {
      float u = director.segments.get(i).getCurrentAngle();

      if (print) {
        println("Start angle: "+director.segments.get(0).getStartAngle());
        println("Current angle: "+director.segments.get(0).getCurrentAngle());
        println("End angle: "+director.segments.get(0).getEndAngle());
        println();
        print = false;
      }

      //Checks if the segments needs to rotate clockwise
      if (director.segments.get(i).getCurrentAngle() < director.segments.get(i).getEndAngle()) {
        u+=speed;
        //Locks the segment in place
        if (u >= HALF_PI) {
          u = HALF_PI;
        }
      }
      //Checks if the segments needs to rotate counterclockwise
      if (director.segments.get(i).getCurrentAngle() > director.segments.get(i).getEndAngle()) {
        u-=speed;
        //Locks the segment in place
        if (u <= 0) {
          u=0;
        }
      }


      director.segments.get(i).setCurrentAngle(u);
      director.segments.get(i).showCurrent();
      director.showing = "current`";
    }

    popMatrix();

    if (counter == 0) {
      director.showNext(); 
      counter = wait;
    }
    if (loop) {
      counter--;
    }
  }
}
