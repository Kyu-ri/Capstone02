#include <Servo.h>

Servo myservo; 

int pos = 0;    

void setup() {
  myservo.attach(5);  
}

void loop() {
  for (pos = 0; pos <= 110; pos += 1) 
    myservo.write(pos);           
    delay(25);                       
  }
  for (pos = 110; pos >= 0; pos -= 1)
    myservo.write(pos);              
    delay(25);                      
  }
}
