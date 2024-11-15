/*
Documentation:
insert-useful info here

- Plug in usb-a in computer and a usb-c out to the robot's control hub
*/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.Gamepad;

@TeleOp(name = "Into_the_Deep_Teleop")

public class TestTeleop extends OpMode {
    //public Gamepad currentGamepad1 = new Gamepad();
    //public Gamepad previousGamepad1 = new Gamepad();
    public DcMotor frontLeftMotor;
    public DcMotor frontRightMotor;
    public DcMotor backLeftMotor;
    public DcMotor backRightMotor;
    public DcMotor armMotor;
    public CRServo wristServo;
    public CRServo intakeServo;
    public double ticks; //Create ticks variable for each motor. Each motor has a number of ticks per rotation. This can be used to make half-turns
    public double newTarget;

    @Override
    public void init() { //This runs when hitting the init button on the driver station

        telemetry.addData("Hardware: ","Initialized");

        //Hardware mapping gives each hardware object a name, which must be entered in the robot's configuration
        //Motor Mapping
        frontLeftMotor = hardwareMap.get(DcMotor.class, "frontLeftMotor");
        frontRightMotor = hardwareMap.get(DcMotor.class, "frontRightMotor");
        backLeftMotor = hardwareMap.get(DcMotor.class, " backLeftMotor");
        backRightMotor = hardwareMap.get(DcMotor.class, " backRightMotor");
        armMotor = hardwareMap.get(DcMotor.class, "armMotor");

        //Servo Mapping
        intakeServo = hardwareMap.get(CRServo.class,"intakeServo");
        wristServo = hardwareMap.get(CRServo.class, "wristServo");

        //Setting motors to run using encoders
        DcMotor[] motors = {frontLeftMotor,frontRightMotor,backLeftMotor,backRightMotor};
        for (DcMotor motor: motors) {
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    @Override
    public void loop() { //This repeats when you hit the start button

        //previousGamepad1.copy(currentGamepad1);
        //currentGamepad1.copy(gamepad1);

        double y = gamepad2.left_stick_y;
        double x = -gamepad2.left_stick_x * 1.1;
        double r = -gamepad2.right_stick_x;
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(r), 1);

        telemetry.addData("y: ",y);

        double frontLeftPower = (y + x + r) / denominator;
        double frontRightPower = (y - x - r) / denominator;
        double backLeftPower = (y - x + r) / denominator;
        double backRightPower = (y + x -r) / denominator;

        frontLeftMotor.setPower(frontLeftPower/2);
        frontRightMotor.setPower(frontRightPower/2);
        backLeftMotor.setPower(backLeftPower/2);
        backRightMotor.setPower(backRightPower/2);

        arm();
        intake();
        wrist();
    }

    public void mecanum_input() { //Checks joystick input and accordingly sets power level to motors in the mecanum drivetrain
        //Drivetrain code
    }

    public void arm(){
        //armMotor (claw-arm up/down)
        if (gamepad2.dpad_up) {
            armMotor.setPower(0.5);
        }
        else if (gamepad2.dpad_down){
            armMotor.setPower(-0.5);
        }
        else {
            armMotor.setPower(0);
        }
        telemetry.addData("Motor Ticks: ", armMotor.getCurrentPosition());
    }

    public void intake(){
        //intake_servo (how the arm retrieves the game-pieces)
        if (gamepad2.a){
            intakeServo.setPower(1);
        }
        else if (gamepad2.b){
            intakeServo.setPower(-1);
        }
        else {
            intakeServo.setPower(0);
        }
    }

    public void wrist(){
        if (gamepad2.dpad_left){
            wristServo.setPower(1);
        }
        else if (gamepad2.dpad_right){
            wristServo.setPower(-1);
        }
        else {
            wristServo.setPower(0);
        }
    }
    public void encoder(DcMotor motor, int turnage) { //Encoder turns 360°/turnage (Ex: 360°/2 = 180° → turns halfway)
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        newTarget = ticks/turnage;
        motor.setTargetPosition((int)newTarget);
        motor.setPower(0.5);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void motor_goto(DcMotor motor, int target_position) { //For example, target_position=0 would reset the motor's position
        motor.setTargetPosition(target_position);
        motor.setPower(0.8);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
}