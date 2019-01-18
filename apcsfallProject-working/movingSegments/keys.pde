void keyPressed() {
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
       background(25);
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
void printD(String m, boolean t) {
  if (t) {
    println(m + ": on");
  } else {
    println(m + ": off");
  }
}
