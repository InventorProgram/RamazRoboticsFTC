/*
Documentation:
-insert-useful info here
- Plug in usb-a in computer and a usb-c out to the robot's control hub
*/
package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Teleop")

public class Teleop extends OpMode {

    public DcMotor frontLeftMotor;
    public DcMotor frontRightMotor;
    public DcMotor backLeftMotor;
    public DcMotor backRightMotor;
    public DcMotor armMotor;
    public DcMotor slideMotor; //Linear slides motor
    public Servo wristServo;
    public CRServo intakeServo;

    @Override
    public void init() { //This runs when hitting the init button on the driver station

        telemetry.addData("Hardware: ","Initialized");

        //Hardware mapping gives each hardware object a name, which must be entered in the robot's configuration
        //Motor Mapping
        frontLeftMotor = hardwareMap.get(DcMotor.class, "frontLeftMotor");
        frontRightMotor = hardwareMap.get(DcMotor.class, "frontRightMotor");
        frontRightMotor.setDirection(DcMotor.Direction.REVERSE);
        backLeftMotor = hardwareMap.get(DcMotor.class, "backLeftMotor");
        backRightMotor = hardwareMap.get(DcMotor.class, "backRightMotor");
        backRightMotor.setDirection(DcMotor.Direction.REVERSE);
        armMotor = hardwareMap.get(DcMotor.class, "armMotor");
        slideMotor = hardwareMap.get(DcMotor.class,"slideMotor");

        //Servo Mapping
        intakeServo = hardwareMap.get(CRServo.class,"intakeServo");
        wristServo = hardwareMap.get(Servo.class, "wristServo");

        //Setting motors to run using encoders
        DcMotor[] motors = {frontLeftMotor,frontRightMotor,backLeftMotor,backRightMotor};
        for (DcMotor motor: motors) {
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    @Override
    public void loop() { //This repeats when you hit the start button
        mecanum_drivetrain();
        //motor_test();
        arm();
        intake();
        wrist();
        linear_slides();
        controls_telemetry();
    }

    public void motor_test() {
        frontLeftMotor.setPower(gamepad2.left_stick_y);
        frontRightMotor.setPower(gamepad2.right_stick_y);
        backLeftMotor.setPower(gamepad2.left_stick_x);
        backRightMotor.setPower(gamepad2.right_stick_x);

    }

    public void mecanum_drivetrain() { //Checks joystick input and accordingly sets power level to motors in the mecanum drivetrain

        /*Joystick variables and denominator (this is the band-aid solution joystick mapping)
        double y = -gamepad2.right_stick_x;
        double x = gamepad2.right_stick_y * 1.1;
        double r = gamepad2.left_stick_y;
        */

        //(This is the original mecanum joystick mapping)
        double y = gamepad2.left_stick_y;
        double x = -gamepad2.left_stick_x * 1.1;
        double r = -gamepad2.right_stick_x;

        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(r), 1);

        //Power variables calculated from joystick variables and denominator
        double frontLeftPower = (y + x + r) / denominator;
        double frontRightPower = (y - x - r) / denominator;
        double backLeftPower = (y - x + r) / denominator;
        double backRightPower = (y + x -r) / denominator;

        //Setting power ot motors using the power variables
        frontLeftMotor.setPower(frontLeftPower);
        frontRightMotor.setPower(frontRightPower);
        backLeftMotor.setPower(backLeftPower);
        backRightMotor.setPower(backRightPower);
    }

    public void arm(){
        //armMotor (claw-arm up/down)
        if (gamepad2.y) {
            armMotor.setPower(0.5);
        }
        else if (gamepad2.a){
            armMotor.setPower(-0.5);
        }
        else if (gamepad2.right_trigger > 0.2){  //If the right trigger is pressed, the arm will close to hang the robot and block input
            //The right bumper can unlock the arm and unblock input
            do {
                armMotor.setPower(-1);
            } while (!gamepad2.right_bumper);

        } else {
            armMotor.setPower(0);
        }

        telemetry.addData("Motor Ticks: ", armMotor.getCurrentPosition());
    }

    public void intake(){
        //intake_servo (how the arm retrieves the game-pieces)
        if (gamepad2.dpad_right){
            intakeServo.setPower(-1);
        }
        else if (gamepad2.dpad_left){
            intakeServo.setPower(0.5);
        } else {
            intakeServo.setPower(0.0);
        }
    }

    public void wrist(){
        if (gamepad2.x){
            wristServo.setPosition(0);
        }
        else if (gamepad2.b) {
            wristServo.setPosition(1);
        } else {
            wristServo.setPosition(0.5);
        }
    }

    public void linear_slides(){
        if (gamepad2.dpad_up){
            slideMotor.setPower(0.4);
        }
        else if (gamepad2.dpad_down) {
            slideMotor.setPower(-0.4);
        } else {
            slideMotor.setPower(0);
        }
    }

    public void controls_telemetry(){
        telemetry.addData("triangle: ",gamepad2.triangle);
        telemetry.addData("cross: ",gamepad2.cross);
        telemetry.addData("square: ", gamepad2.square);
        telemetry.addData("circle: ",gamepad2.circle);
        telemetry.addData("left_bumper: ",gamepad2.left_bumper);
    }
}