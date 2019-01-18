import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class movingSegments extends PApplet {

// Inspired By: https://twitter.com/beesandbombs/status/1019924265540431872

// Globals
// *************************************************************************************************
int[] $gridWidthArray = {10,20,50,100};
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
public void calculateProperties() {
  $cols = (width-2*$borderWidth)/$gridWidth;
  $rows = (height-2*$borderWidth)/$gridWidth;
}

public void prepareAndCreateDirector(int newGridWidthArrayIndex) {
  background(255);
  $gridWidthArrayIndex = newGridWidthArrayIndex;
  $gridWidth = $gridWidthArray[$gridWidthArrayIndex];
  println("gridWidth: " + $gridWidth);
  println("gridWidthArrayIndex: " + $gridWidthArrayIndex);
  
  if ($grid) { drawGrid($gridWidth); }; // show grid
  
  calculateProperties();
  director = new Director(); 
}

// Setup
// *************************************************************************************************
public void setup() {
  
  background(255);
  
  rectMode(CORNER);
  stroke(0);
  strokeWeight($strokeWeight);
  frameRate($fRate);
  smooth(8);

  calculateProperties();

  if ($grid) { drawGrid($gridWidth); };

  director = new Director(); 
}

// Test segments to show how $animating works
Segment t1 = new Segment($gridWidth, $gridWidth);
Segment t2 = new Segment($gridWidth*2, $gridWidth);
Segment t3 = new Segment($gridWidth*3, $gridWidth);

// used in demo animation
float i = 0;
float delta = 0.1f;
float speed = 0.065f;
boolean sub = false;
boolean add = false;
boolean loop = false;
boolean print = false;

int wait =75;
int counter = wait;

// Draw
// *************************************************************************************************
public void draw() {
  
  // This is where you can start with building your animation code
  // This does not involve easing (read about it: https://processing.org/examples/easing.html)
  // and as such, its pretty clunky. You'll likely want to build a new
  // animation algorithm
  if($animating) {
    background(255);
    if ($grid) { drawGrid($gridWidth); };

    pushMatrix();
    translate($borderWidth,$borderWidth);
    for (int i = 0; i<$cols*$rows; i++) {
      float u = director.segments.get(i).getCurrentAngle();

      if(print) {
       println("Start angle: "+director.segments.get(0).getStartAngle());
       println("Current angle: "+director.segments.get(0).getCurrentAngle());
       println("End angle: "+director.segments.get(0).getEndAngle());
       println();
       print = false;
      }

      while(director.segments.get(i).getCurrentAngle() != director.segments.get(i).getEndAngle()) {

      if(director.segments.get(i).getCurrentAngle() < director.segments.get(i).getEndAngle()) {
        u+=speed;
        if(u >= HALF_PI) {
          u = HALF_PI;
        }
      }

      if(director.segments.get(i).getCurrentAngle() > director.segments.get(i).getEndAngle()) {
        u-=speed;
        if(u <= 0) {
          u=0;
        }
      }
      

      director.segments.get(i).setCurrentAngle(u);
      director.segments.get(i).showCurrent();
      director.showing = "current`";
      }
    }
    
    popMatrix();

    if(counter == 0) {
      director.showNext(); 
      counter = wait;
    }
    if(loop) {
      counter--;
    }
  }
}
// a way to hold onto and manipulate all of the patterns available to present on screen
// a PatternContainer object has Pattern objects

class Director
{
  // instance data  
  // *************************************************************************************************
  ArrayList<Pattern> patterns;
  ArrayList<Segment> segments; 
  String showing;
  int startPatternIndex;
  
  // Deprecated
  //float startTime;
  //float currentTime;
  
  
  // constructor 
  // *************************************************************************************************
   Director() {
     startPatternIndex = 0;
     
     // only one of these needed. We will change the behavior/attributes of these segments
     segments = new ArrayList<Segment>();

     // contains lots of patterns, can be changed.
     patterns = new ArrayList<Pattern>();
     
     // showing
     showing = "start";
     
     // fill up segments with the right number of segments and default values
     for (int row = 0; row<$rows; row++) {
       for (int col = 0; col<$cols; col++) {
         segments.add(new Segment(0+$gridWidth*col, 0+$gridWidth*row));
       }
     }

     addAllPatterns();
     
     
     // map firstPattern onto "startAngle" for each Segment in segments
     mapPattern(0,"start");
     // map secondPattern onto "endAngle" for each Segment in segments
     mapPattern(1,"end");
     
  }
  
  // methods
  // *************************************************************************************************
  
  public String getShowing() {
    return showing;
  }
  
  public void setShowing(String s) {
    showing = s;
  }
  
  // pattern methods
  // *************************************************************************************************
    // append new pattern
    public void addPattern() {
      patterns.add(new Pattern());
    }
    
    public void addAllPatterns() {
       for (int i = 0; i < 3; i++) { // change this to create more than two patterns
        patterns.add(new Pattern());
      }
    }
    
    public void shiftPatterns() {
      startPatternIndex++;
      
      if ($debug) {
        println("start pattern #" + startPatternIndex + " end pattern #" + (startPatternIndex+1));
        println(patterns.size());
        println();
      }
      
      if(startPatternIndex > 1) {
        startPatternIndex = 0;
      }

      for(int i = 0; i<$cols*$rows; i++) {
        float u = segments.get(i).getStartAngle();
        segments.get(i).setStartAngle(segments.get(i).getEndAngle());
        segments.get(i).setEndAngle(u);
        segments.get(i).setCurrentAngle(segments.get(i).getStartAngle());
      }

      //  mapPattern(startPatternIndex, "start");
      //  mapPattern(startPatternIndex+1, "end");
    }
    
    // remove pattern
    // apply pattern to segments
    public void mapPattern(int patternNumber, String s) {
      //IntList pat = makePattern(); // generate new pattern and hold it.
      IntList pat = patterns.get(patternNumber).getPatternPositions();

      for(int row = 0; row<$rows; row++) {
        for(int col = 0; col<$cols; col++) {
          int index = col + (row*$cols);
          if(s == "start") {
            if(pat.get(index) == 1) {
              segments.get(index).setStartAngle(HALF_PI);
              segments.get(index).setCurrentAngle(HALF_PI);
            }else {
              segments.get(index).setStartAngle(0);
              segments.get(index).setCurrentAngle(0);
            }
          }
          else if(s == "end") {
            if(pat.get(index) == 1) {
              segments.get(index).setEndAngle(HALF_PI);
            }else {
              segments.get(index).setEndAngle(0);
            }
          }
        }
      }
    }
    
  // show methods
  // *************************************************************************************************
  
  // show next pattern in list
  public void showNext() {
    shiftPatterns(); // go to the next pattern
    pushMatrix();
    translate($borderWidth,$borderWidth);
    for (int i = 0; i<$cols*$rows; i++) {
        $animating = true;
        segments.get(i).showCurrent();
    }
    popMatrix();
  }
    
}
// a way to hold onto and manipulate all of the patterns available to present on screen
// a PatternContainer object has Pattern objects

class Pattern
{
  // instance data  
  // *************************************************************************************************
  IntList patternPositions;
  
  // constructor 
  // *************************************************************************************************
   Pattern() {
     patternPositions = new IntList();
     
     // make a new random pattern
     patternPositions = makePattern();
  }
  
  
  // Helper methods
  // *************************************************************************************************
  public int randomAngleInt() {
    int p = PApplet.parseInt(random(0,2)); // random value 0 or 1
    if(p == 1) {
     return 1;
    } else {
     return 0; 
    }
  }
  
  // Getter methods
  // *************************************************************************************************
  public IntList getPatternPositions() {
    return this.patternPositions;
  }
  
  // Pattern generation
  // *************************************************************************************************
  // make new random pattern, first generated as an IntList... more generalized... might be useful later
  public IntList makePattern() {
    // create empty pattern IntList
    IntList p = new IntList();
    
    // fill it with 2's as default value
    for (int i = 0; i<$rows*$cols; i++) {
        p.append(2);
    }
    
    // create design in top left that will be copied over to top right (to fill top half) and top half
    // will be copied to bottom half in a mirror fashion
      for (int row = 0; row<($rows/2); row++) {
        for (int col = 0; col<($cols/2); col++) {
          int index = col+(row*$cols);                 // index of current element
          int mirrorIndex = row+(col*$cols);           // index of its "mirror" across diagonal
          if (row == col )                              // on a diagonal
          {                            
            // choose random angle and overwrite -1 with this new one
            p.set(index,randomAngleInt());
          } 
          if (p.get(index) == 2)       // see if its been set to anything beyond default yet
          {   
            // set value and set it's "mirror" value to match
            int angle = randomAngleInt();
            p.set(index, angle);
            p.set(mirrorIndex, angle);
          }
        }
      }
    
    // top half
    for (int row = 0; row<($rows/2); row++) {
      int distBetween = 1;  // set the "offset" between values across L.O.S.
      for (int col = ($cols/2); col<$cols; col++) {
        int index = col+(row*$cols);
        int mirrorIndex = index - distBetween;
        if(p.get(mirrorIndex) == 1) { // check to see if leaning right
          p.set(index, 0);
        } else {
          p.set(index,1);
        }
        distBetween += 2;
      }
    }
  
    
    // bottom half
      int distBetween = 1; // set the "offset" between values between rows in L.O.S.
      for (int row = ($rows/2); row<$rows; row++) {                       
        for (int col = 0; col<$cols; col++) {
          int index = col+(row*$cols);
          int mirrorIndex = col+((row-distBetween)*$cols);
         // println("index: " + index + " mirrorIndex: "+mirrorIndex);
          if(p.get(mirrorIndex) == 1) { // check to see if leaning right. floats require a value that's between two extremes. I picked PI/3. Anything between PI/2 and PI/4 would have worked.
            p.set(index,0);
          } else {
            p.set(index,1);
          }
        }
        distBetween += 2;
      }
      //pattern = p; // overwrite global "pattern" IntList
      return p; // return IntList for use in mapping
  }

}
class Segment
{
  // private data
  // *************************************************************************************************
  static final boolean showBox = true;
  static final float easing = 0.1f;
  static final float error = 0.005f;
  
  // instance data  
  // *************************************************************************************************
  int segmentLength;
  PVector center;
  float startAngle;
  float currentAngle; 
  float endAngle; 
  float newLength;  
  
  // constructor 
  // *************************************************************************************************
  Segment(int x, int y) {
    segmentLength = $gridWidth;
    float p = segmentLength/2;
    center = new PVector(x+p,y+p);
    startAngle = 0; 
    currentAngle = startAngle;
    endAngle = startAngle;
    newLength = segmentLength; 
  }
  
  // Getters
  // *************************************************************************************************
  public float getStartAngle() {  
    return this.startAngle;  
  }
  
   public float getEndAngle() {
    return this.endAngle;  
  }
  
   public float getCurrentAngle() {
    return this.currentAngle;  
  }
  
  // Deprecated
  //int getStartAngleBool() {
  //  if ( ((PI/2)-error) < this.startAngle && this.startAngle < ((PI/2)+error) ) {
  //    return 1; // learning right
  //  } else {
  //    return 0; // not leaning right
  //  }
  //}
  
  //int getEndAngleBool() {
  //   if ( ((PI/2)-error) < this.endAngle && this.endAngle < ((PI/2)+error) ) {
  //  // if (this.endAngle > (PI/3)) {
  //    return 1; // learning right
  //  } else {
  //    return 0; // not leaning right
  //  }
  //}
  
  public float getLength(float angle) {
    //https://www.desmos.com/calculator/mu1snong2u
    return 0.6714f*angle*angle-1.0548f*angle+sqrt(2);
  }
  
  // Setters
  // *************************************************************************************************
    public void setCurrentAngle(float a) {
      currentAngle = a;
    }

    public void setStartAngle(float a) {
      startAngle = a;
    }

    public void setEndAngle(float a) {
      endAngle = a;
    }
    
    public void setRandomStartAngle() {
      float p = random(0,1);
      if (p < 0.5f) {
       startAngle = 0;  // left 
      } else {
       startAngle = HALF_PI; // right 
      }
    }
    

    // Display methods
    // *************************************************************************************************
   
    public void showStart() {
      float l = getLength(startAngle);
      float k = (segmentLength/sqrt(2));
      float p = round((l*k)/2);
      pushMatrix();
      translate(center.x,center.y); // move to center + 1/2 line length
      rotate(startAngle);
      line(-p,-p,p,p);
      popMatrix();
      if($debug) {
        fill(255,0,0);
        ellipse(center.x,center.y,5,5);
      }
    }
    
     public void showCurrent() {
      float l = getLength(currentAngle);
      float k = (segmentLength/sqrt(2));
      float p = round((l*k)/2);
      pushMatrix();
      translate(center.x,center.y); // move to center + 1/2 line length
      rotate(currentAngle);
      line(-p,-p,p,p);
      popMatrix();
      if($debug) {
        fill(255,0,0);
        ellipse(center.x,center.y,5,5);
      }
    }
    
    public void showEnd() {
      float l = getLength(endAngle);
      float k = (segmentLength/sqrt(2));
      float p = round((l*k)/2);
      pushMatrix();
      translate(center.x,center.y); // move to center + 1/2 line length
      rotate(endAngle);
      line(-p,-p,p,p);
      popMatrix();
      if($debug) {
        fill(255,0,0);
        ellipse(center.x,center.y,5,5);
      }
    }
}
public void drawGrid(int size) {
 
  pushStyle();
  strokeWeight(1);
  int rows, cols;
  cols = width/size;
  rows = height/size;
  
  for (int i = 0; i < cols+1; i++) {
    // Begin loop for rows
    for (int j = 0; j < rows+1; j++) {

      // Scaling up to draw a rectangle at (x,y)
      int x = i*size;
      int y = j*size;
      fill(255);
      stroke(220);
      // For every column and row, a rectangle is drawn at an (x,y) location scaled and sized by videoScale.
      rect(x, y, size, size);
    }
  }
  popStyle();
}
public void keyPressed() {
  if(key == 's') { // save frame to disk
    saveFrame("#####.png");
  }
  
  // toggle $animating 
  if(key == 'a') {
    loop = !loop;
    println("$activeMode: "+$animating);
  }
  
  if(key == '1') {  // set grid size 10
    prepareAndCreateDirector(0);
    director.showNext();
  }
  
  if(key == '2') {  // set grid size 20
    prepareAndCreateDirector(1);
    director.showNext();
  }
  
  if(key == '3') {  // set grid size to 50
    prepareAndCreateDirector(2);
    director.showNext();
  }
  
  if(key == '4') {  // set grid size to 100
    prepareAndCreateDirector(3);
    director.showNext();
  }
  
  //if(key == '5') {
  //  background(255);
  //  if ($grid) { drawGrid($gridWidth); };
  //}
 
 if(key == 'g') {
  $grid = !$grid; 
  if($grid) {
    printD("grid",true);
  } else {
    printD("grid",false); 
  }
 }
 
 // toggle between "start" and "end" patterns
 // you'll want to $animating to be false for these to really work.
     if(key == 'n') {
       background(255);
       if ($grid) { drawGrid($gridWidth); };
       print = true;
       director.showNext();
     }
 

 // turn on some console print statements and visual elements
 if(key == 'd') {
  $debug = !$debug;
  if($debug) {
    printD("debug",true);
  } else {
    printD("debug",false); 
  }
 }
 
}

// Helper method for string formatting
public void printD(String m, boolean t) {
  if (t) {
    println(m + ": on");
  } else {
    println(m + ": off");
  }
}
  public void settings() {  size(800, 800);  smooth(8); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "movingSegments" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
