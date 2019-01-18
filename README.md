# Moving Segments
The following library was built by [@matthewalangreen](https://github.com/matthewalangreen) as a way to support making animated, procedural drawings inspired by [this](https://twitter.com/beesandbombs/status/1019924265540431872) and [this](https://www.youtube.com/watch?v=bEyTZ5ZZxZs&t=1s). The current version supports drawing individual segments, presenting them on screen and generating randomized patterns that possess various lines of symmetry

<img src="https://raw.githubusercontent.com/riverpointacademy/apcsfallproject-2018/master/images/example.png"  width = "350" />  <img src="https://raw.githubusercontent.com/riverpointacademy/apcsfallproject-2018/master/images/symmetry.png"  width = "350" /> <img src="https://raw.githubusercontent.com/riverpointacademy/apcsfallproject-2018/master/images/example.gif" width = "1000" />


## Motivation
I’m in love with the work created by [Dave 🐝💣 — @beesandbombs](https://twitter.com/beesandbombs)). Rarely do I see one of his creations and feel capable of approximating it. I also tend to completely over-engineer E V E R Y T H I N G. He shared his [code](https://gist.github.com/beesandbombs/6e7a310b55fd7a1f1d9bb0788b96234f) with the note, “here’s the code for this. I don’t really understand how it works.” It proved too unruly for me too, so I thought, “Yep. I’m gonna build a library for this so I can make LOTS of these.”

## Usage & Screenshots
### Global Settings
```
// contains 4 values: {10, 20, 50, 100} these are the curated
// grid sizes that create the best output. This variable controls
// most other animation settings.
$gridWidthArray
```

```
// turns the grid on & off. True by default
$grid
```

```
// Turns debugging mode on. Some values will be written to
// console when set to true. Center point of each segment is
// also drawn.
$debug
```

```
// a way to toggle infinite looping
$animating
```

### Key Press Functions

**Press the '1' Key**
*Screen draws a new random pattern of segments on the smallest grid size. See example:*

<img src="https://raw.githubusercontent.com/riverpointacademy/apcsfallproject-2018/master/images/1Key.png"  width = "350" />

**Press the '2' Key**
*Screen draws a new random pattern of segments on the smallest grid size. See example:*

<img src="https://raw.githubusercontent.com/riverpointacademy/apcsfallproject-2018/master/images/2Key.png"  width = "350" />

**Press the '3' Key**
*Screen draws a new random pattern of segments on the smallest grid size. See example:*

<img src="https://raw.githubusercontent.com/riverpointacademy/apcsfallproject-2018/master/images/3Key.png"  width = "350" />

**Press the '4' Key**
*Screen draws a new random pattern of segments on the smallest grid size. See example:*

<img src="https://raw.githubusercontent.com/riverpointacademy/apcsfallproject-2018/master/images/4Key.png"  width = "350" />

**Press the 'n' Key**
*Toggles between the to pattern states assigned to the segments when they are created and mapped. You create more than two patterns by editing the `addAllPatterns() method in the Director class. If you create more than two patterns, pressing 'n' repeatedly will cycle through all patterns and upon reaching the last pattern, it will start over at the first pattern.*

<img src="https://raw.githubusercontent.com/riverpointacademy/apcsfallproject-2018/master/images/n1.png"  width = "250" />  <img src="https://raw.githubusercontent.com/riverpointacademy/apcsfallproject-2018/master/images/arrow.png"  width = "120" /><img src="https://raw.githubusercontent.com/riverpointacademy/apcsfallproject-2018/master/images/n2.png"  width = "250" />  

**Press the 'g' Key**
*Toggles $grid on & off.*

<img src="https://raw.githubusercontent.com/riverpointacademy/apcsfallproject-2018/master/images/GridOff.png"  width = "250" />  <img src="https://raw.githubusercontent.com/riverpointacademy/apcsfallproject-2018/master/images/arrow.png"  width = "120" /><img src="https://raw.githubusercontent.com/riverpointacademy/apcsfallproject-2018/master/images/GridOn.png"  width = "250" />  

**Press the 'd' Key**
*Toggles $debug on & off. Like with 'g' key you must press either 'n' or 1-4 again to see the change take effect*

<img src="https://raw.githubusercontent.com/riverpointacademy/apcsfallproject-2018/master/images/d1.png"  width = "250" />  <img src="https://raw.githubusercontent.com/riverpointacademy/apcsfallproject-2018/master/images/arrow.png"  width = "120" /><img src="https://raw.githubusercontent.com/riverpointacademy/apcsfallproject-2018/master/images/d2.png"  width = "250" />  

**Press the 's' Key**
*Saves the current frame to disk as "#####.png"*

**Press the 'a' Key**
*Toggles on and off infinite looping between patterns.*

## Built with Processing.java
Get version [3.4](https://processing.org/download/)

## Installation
1. Install Processing.java IDE for your platform. [Version 3.4](https://processing.org/download/) recommended
2. [Download](https://github.com/riverpointacademy/apcsfallproject-2018/archive/master.zip) or clone this repo: https://github.com/riverpointacademy/apcsfallproject-2018.git
3. Run sketch, “movingSegments”

## API Reference — Segment (Class)
**Description** *- Each segment can be drawn on screen and contains data to track its angle, center point (as a [PVector](https://processing.org/reference/PVector.html)) and its length.*

**Instance Data**
```
  int segmentLength;
  PVector center;
  float startAngle;
  float currentAngle;
  float endAngle;
  float newLength;  
```

**Constructor**
```
 Segment(int x, int y) {
    segmentLength = $gridWidth;
    float p = segmentLength/2;
    center = new PVector(x+p,y+p);
    startAngle = 0;
    currentAngle = startAngle;
    endAngle = startAngle;
    newLength = segmentLength;
  }
```
Create a new segment sized to fit current `$gridWidth` global value. Set it to lean to the left by default. I.e. each new segment will look like this before it is changed: `\` it can be changed to look like this `/`

**Getter Methods**

```
// Each of these are used to get values for a given segment.
// The director class uses them when mapping patterns. Can
// also be used in animation.

float getStartAngle()
float getEndAngle()
float getCurrentAngle()
float getLength()
```

**Setter Methods**
```
// Currently these are used  by an instance of the Director
// class to apply various Pattern objects to segments, but can
// also be used to facilitate animation of each segment.

void setCurrentAngle(float a)
void setStartAngle(boolean t)
void setCurrentAngle(boolean t)
void setRandomStartAngle()
```


**Display Methods**
```
// Currently we are only showing one of the segments
// “positions” The thinking here is that a segment needs a
// starting, current and ending position. If a segment starts
// here: \ and in the next pattern it is changed to / then
// it will need to be able to animate between these states.
// During the animation phase the showCurrent() method can be
// used to display the segment while it is in transition from
// its startAngle to its endAngle

void showStart()
void showCurrent()
void showEnd()
```


## API Reference — Pattern (Class)
**Description** - *This is a generic class that creates patterns that follow symmetry rules demonstrated above. Instances of this class are not aware of either the Segment Class or the Director class. Patterns are mapped using a series of methods to an IntList() that tracks values as either 0 or 1. These integer values are then read by an instance of the Director class which contains an ArrayList of Pattern objects and an ArrayList of Segment objects.

An example of a randomly generated pattern is shown below*

<img src = "https://raw.githubusercontent.com/riverpointacademy/apcsfallproject-2018/master/images/combined.png" width = "800" />

**Instance Data**
```
IntList patternPositions
```

**Methods (misc.)**
```
// used in pattern generation
int randomAngleInt()
```

```
// used by Director instance to map paterns
IntList getPatternPostions()
```

**makePattern() Method**
```
// This is the "workhorse" method for the application.
// It creates the pattern by starting in the top left corner.
// then this pattern is applied across the vertical line of
// symmetry to complete the top half. Then the entire top half
// is "flipped" across the horizontal line of symmetry to
// complete the bottom half.

IntList makePattern() {
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
```

## API Reference — Director (Class)
**Description** *- A single instance of the Director class is created to manage all of the Segment objects and Pattern objects.*

*The Director object is named thusly because it directs the action.  It is also intended to be the class that controls each segments animation and state.*

*Animation is not yet implemented at the class level.*

**Instance Data**
```
  ArrayList<Pattern> patterns; // holds Pattern objects
  ArrayList<Segment> segments; // Holds Segment objects
  String showing; // track which "state" of each segment is being displayed.
  int startPatternIndex; // track Pattern objects in patterns ArrayList
```

**Constructor**
```
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
```

**Methods (misc.)**
```
// Can be used to control animation
String getShowing()
void setShowing()
```

**Pattern Methods**
```
// Instantiate new Pattern object and add to patterns ArrayList
void addPattern()

// Create n Pattern objects and add them to patterns ArrayList
void addAllPatterns()

// Change the Pattern object that is being mapped onto the Segments that are being displayed.  
// The trick here is that there is only one "set" of segments within an instance of the Director class,
// these segments just get assinged new patterns.
void shiftPatterns()

// Method used to assign the current starting angle to the end angle and vice versa
for(int i = 0; i<$cols*$rows; i++) {
    float u = segments.get(i).getStartAngle();
    segments.get(i).setStartAngle(segments.get(i).getEndAngle());
    segments.get(i).setEndAngle(u);
    segments.get(i).setCurrentAngle(segments.get(i).getStartAngle());
}
```

**Display Methods**
```
// shows segment "showing"
void showSegments()

// show segment s
void showSegments(String s)

// call shiftPatterns() then show "start"
void showNext()
```

## API Reference - Animation (Inside Draw)
**Description** This is a short series of if statements that check what angle the segments should be at and animates accordingly.

**Instance Data**
```
//Holds value of the current angle that can be changed
float u = director.segments.get(i).getCurrentAngle();

//This controls the time between animations while infinitely looping
int wait = 75;

//This is the variable that acts as the alarm clock for infinite looping
int counter = wait;

//Toggles infinite loop
boolean loop = false;

//The speed the segments rotate at
float speed = 0.055;
```
**The Code (With comments!)**
```
//$animating will always be true
if ($animating) {
  //Draws grid
    if ($grid) { 
      drawGrid($gridWidth);
    };

    pushMatrix();
    translate($borderWidth, $borderWidth);
    for (int i = 0; i<$cols*$rows; i++) {
      float u = director.segments.get(i).getCurrentAngle();

      //For debugging
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

      //Sets the current segment to the new angle and draws it
      director.segments.get(i).setCurrentAngle(u);
      director.segments.get(i).showCurrent();
      director.showing = "current`";
    }

    popMatrix();

    //This controls the infinite loop
    if (counter == 0) {
      director.showNext(); 
      counter = wait;
    }
    if (loop) {
      counter--;
    }
  }
```      
## RA AP Computer Science Fall Project Expectations

**Your task:**

- Create animation methods that will enable an animated transition from one pattern state to another. Your solution should be applied to all segments in the pattern.

- Create additional key press options to use these methods or set up the sketch to run the animations automatically.

- Be sure to change the usage documentation (in the README) to explain how it works.

- This task will require making changes throughout the Segment and Director classes. _You will not need to change the Pattern class._

- Consider using "easing" to create a smooth animation. Learn more about it [here](https://processing.org/examples/easing.html)

**Steps:**

1. Login to [GitHub](https://github.com/) ([Sign Up](https://github.com/join) if you haven't already)
2. Fork this repo: [https://github.com/riverpointacademy/apcsfallproject-2018](https://github.com/riverpointacademy/apcsfallproject-2018)
3. Add "matthewalangreen" as a Collaborator to your repo
4. Checkout a copy of your fork and do your work. Be sure to commit changes with clear and descriptive commit messages. Get in the habit of committing code changes often.
5. __You are encouraged to collaborate with classmates on this project.__
6. Submit your project by sending me a WorkChat letting me know you are done.
7. This project is due Monday, January 22nd by 2pm.
8. You must complete this project to receive credit in the [course.](https://docs.google.com/document/d/1mzm1GeZODXGW1jzTI1MvCdr9f9ROGb2gd_Otwo69gbE/edit)


## Credits
Thank you [Dave 🐝💣 — @beesandbombs](https://twitter.com/beesandbombs)) for the amazing Processing art you create. I'm equal parts inspired and flabbergasted at the beauty of your work.

## License
MIT © [matthewalangreen](https://github.com/matthewalangreen)
