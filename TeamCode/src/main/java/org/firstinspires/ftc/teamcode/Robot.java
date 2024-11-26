/*
Documentation:
insert-useful info here
 */

package org.firstinspires.ftc.teamcode;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class Robot {
    public DcMotorEx frontLeftMotor;
    public DcMotorEx frontRightMotor;
    public DcMotorEx backLeftMotor;
    public DcMotorEx backRightMotor;
    public DcMotorEx armMotor;
    public CRServo wristServo;
    public CRServo intakeServo;
    public DcMotorEx[] motors;
    public double ticks; //Create ticks variable for each motor. Each motor has a number of ticks per rotation. This can be used to make half-turns
    public double newTarget;

    public Robot(){

        telemetry.addData("Hardware:", "initialized");

        //Hardware mapping gives each hardware object a name, which must be entered in the robot's configuration
        //Motor Mapping
        this.frontLeftMotor = hardwareMap.get(DcMotorEx.class, "frontLeftMotor");
        this.frontRightMotor = hardwareMap.get(DcMotorEx.class, "frontRightMotor");
        this.backLeftMotor = hardwareMap.get(DcMotorEx.class, " backLeftMotor");
        this.backRightMotor = hardwareMap.get(DcMotorEx.class, " backRightMotor");
        this.armMotor = hardwareMap.get(DcMotorEx.class, "armMotor");

        //Servo Mapping
        this.intakeServo = hardwareMap.get(CRServo.class,"intakeServo");
        this.wristServo = hardwareMap.get(CRServo.class, "wristServo");

        //Setting motors to run using encoders
        DcMotorEx[] motors = {frontLeftMotor,frontRightMotor,backLeftMotor,backRightMotor,armMotor};
        for (DcMotorEx motor: motors) {
            motor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        }
    }
    public void move(double direction, double magnitude) { //Direction is an angle, magnitude is a fraction of max_power

        double y; //gamepad2.left_stick_y;
        double x; //-gamepad2.left_stick_x * 1.1;
        double r; //-gamepad2.right_stick_x;
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

    public void rotate(DcMotor motor, int degrees) { //Motor rotates by degrees/360 (360 being a full rotation)
        double turnage = 360.0 / degrees;
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        newTarget = ticks/turnage;
        motor.setTargetPosition((int)newTarget);
        motor.setPower(0.6);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void motor_goto(DcMotor motor, int target_position) { //For example, target_position=0 would reset the motor's position
        motor.setTargetPosition(target_position);
        motor.setPower(0.6);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
}
