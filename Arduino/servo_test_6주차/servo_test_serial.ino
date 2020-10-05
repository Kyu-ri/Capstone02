#include<Servo.h>
Servo myservo;
int pos = 0;

void setup() {
 myservo.attach(5);
 Serial.begin(9600);

}

void loop() {
  if(Serial.available()){
    char in_data;
    in_data = Serial.read();
    if(in_data == '0'){
            pos = 0;
            myservo.write(pos);
            delay(25);
        }
    else if(in_data == '1'){
            pos = 110;
            myservo.write(pos);
            delay(25);
        }
  }
}
