/*
Documentation:
insert-useful info here
 */

package org.firstinspires.ftc.teamcode;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

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
    public double ticks; //Create ticks variable for each motor. Each motor has a number of ticks per rotation. This can be used to make half-turns
    public double newTarget;
    public double time; //Seconds
    public double velocity; //In wheel revolutions per second
    public double drivetrain_motor_ticks; //One revolution
    public final double wheel_radius = 4.4;

    public Robot(){
        //telemetry.addData("Hardware:", "initialized");

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
    public void move(double direction_degrees, double distance) throws InterruptedException { //Direction is an angle, distance is in revolutions (distance covered by one motor revolution)
        double direction_radians = direction_degrees/180.0; //Convert inputted degrees into radians
        this.velocity = 0.5*drivetrain_motor_ticks; //0.5 * motor_ticks/seconds (in this case half a revolution)
        time = this.velocity/distance; //Time is how long the robot will run at that velocity
        
        double y = this.velocity*sin(direction_radians); //In teleop: gamepad2.left_stick_y
        double x = -this.velocity*cos(direction_radians); //In teleop: -gamepad2.left_stick_x * 1.1
        double r = 0; //In teleop: -gamepad2.right_stick_x
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(r), this.wheel_radius);

        //Velocity variables calculated from joystick variables and denominator
        double frontLeftVelocity = (y + x + r) / denominator;
        double frontRightVelocity = (y - x - r) / denominator;
        double backLeftVelocity = (y - x + r) / denominator;
        double backRightVelocity = (y + x -r) / denominator;

        //Setting power ot motors using the power variables
        frontLeftMotor.setVelocity(frontLeftVelocity);
        frontRightMotor.setVelocity(frontRightVelocity);
        backLeftMotor.setVelocity(backLeftVelocity);
        backRightMotor.setVelocity(backRightVelocity);

        Thread.sleep(Math.round(time/1000));

        frontLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backLeftMotor.setPower(0);
        backRightMotor.setPower(0);
    }

    public void rotate(DcMotor motor, int degrees) { //Motor rotates by degrees/360 (360 being a full rotation)
        double turnage = 360.0 / degrees;
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        newTarget = drivetrain_motor_ticks/turnage;
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
