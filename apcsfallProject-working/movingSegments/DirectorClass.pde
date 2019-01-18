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
  
  String getShowing() {
    return showing;
  }
  
  void setShowing(String s) {
    showing = s;
  }
  
  // pattern methods
  // *************************************************************************************************
    // append new pattern
    void addPattern() {
      patterns.add(new Pattern());
    }
    
    void addAllPatterns() {
       for (int i = 0; i < 3; i++) { // change this to create more than two patterns
        patterns.add(new Pattern());
      }
    }
    
    void shiftPatterns() {
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
    void mapPattern(int patternNumber, String s) {
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
  void showNext() {
    shiftPatterns(); // go to the next pattern
    pushMatrix();
    translate($borderWidth,$borderWidth);
    for (int i = 0; i<$cols*$rows; i++) {
        $animating = true;
        segments.get(i).showCurrent();
        showing = "current";
    }
    popMatrix();
  }
    
}
