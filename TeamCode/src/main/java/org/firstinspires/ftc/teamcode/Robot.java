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
    public void move() {

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
