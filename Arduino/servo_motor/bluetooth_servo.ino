/* Sweep
 by BARRAGAN <http://barraganstudio.com>
 This example code is in the public domain.

 modified 8 Nov 2013
 by Scott Fitzgerald
 http://www.arduino.cc/en/Tutorial/Sweep
*/
#include <SoftwareSerial.h>
#include <Servo.h>

SoftwareSerial BTSerial(4, 5); 
Servo myservo;  // create servo object to control a servo
// twelve servo objects can be created on most boards

int pos = 0;    // variable to store the servo position

void setup() {
  Serial.begin(9600);
  BTSerial.begin(9600);

  myservo.attach(9);  // attaches the servo on pin 9 to the servo object
}

void loop() {

  if (BTSerial.available()){  
    char data = BTSerial.read(); 

    if (data == 'L') {  
      int temp = BTSerial.parseInt();
      pos = map(temp, 0, 180, 0, 90);
      
    } else if (data == 'U') { 
      int temp = BTSerial.parseInt();
      pos = map(temp, 180, 0, 90, 0);
    }    
  }
}
