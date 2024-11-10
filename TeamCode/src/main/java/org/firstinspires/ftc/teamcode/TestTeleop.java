/*
Documentation:
insert-useful info here
*/


package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

@TeleOp(name = "Into_the_Deep_Teleop")

public class TestTeleop extends OpMode {
    Gamepad Gamepad1;
    DcMotor frontLeftMotor;
    DcMotor frontRightMotor;
    DcMotor backLeftMotor;
    DcMotor backRightMotor;
    DcMotor armMotor;
    CRServo wristServo;
    CRServo intakeServo;
    double ticks; //100.0 is a placeholder. Create ticks variable for each motor. Each motor has a number of ticks per rotation. This can be used to make half-turns
    double newTarget;

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
        mecanum_input();
        arm();
        intake();
        wrist();
    }

    public void mecanum_input() { //Checks joystick input and accordingly sets power level to motors in the mecanum drivetrain
        double y = Gamepad1.left_stick_y;
        double x = -Gamepad1.left_stick_x * 1.1;
        double r = -Gamepad1.right_stick_x;
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(r), 1);

        double frontLeftPower = (y + x + r) / denominator;
        double frontRightPower = (y - x - r) / denominator;
        double backLeftPower = (y - x + r) / denominator;
        double backRightPower = (y + x -r) / denominator;

        frontLeftMotor.setPower(frontLeftPower);
        frontRightMotor.setPower(frontRightPower);
        backLeftMotor.setPower(backLeftPower);
        backRightMotor.setPower(backRightPower);
    }

    public void arm(){
        //armMotor (claw-arm up/down)
        if (Gamepad1.dpad_up) {
            armMotor.setPower(0.5);
        }
        else if (Gamepad1.dpad_down){
            armMotor.setPower(-0.5);
        }
        else {
            armMotor.setPower(0);
        }
        telemetry.addData("Motor Ticks: ", armMotor.getCurrentPosition());
    }

    public void intake(){
        //intake_servo (how the arm retrieves the game-pieces)
        if (Gamepad1.a){
            intakeServo.setPower(1);
        }
        else if (Gamepad1.b){
            intakeServo.setPower(-1);
        }
        else {
            intakeServo.setPower(0);
        }
    }

    public void wrist(){
        if (Gamepad1.a){
            wristServo.setPower(1);
        }
        else if (Gamepad1.b){
            wristServo.setPower(-1);
        }
        else {
            wristServo.setPower(0);
        }
    }
    public void encoder(DcMotor motor, int turnage){ //Encoder turns 360°/turnage (Ex: 360°/2 = 180° → turns halfway)
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        newTarget = ticks/turnage;
        motor.setTargetPosition((int)newTarget);
        motor.setPower(0.5);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void motor_goto(DcMotor motor, int target_position){ //For example, target_position=0 would reset the motor's position
        motor.setTargetPosition(target_position);
        motor.setPower(0.8);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
}