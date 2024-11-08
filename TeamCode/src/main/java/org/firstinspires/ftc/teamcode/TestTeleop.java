package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
@TeleOp(name = "Into_the_Deep_Teleop", group = "Iterative OpMode")

public class TestTeleop extends OpMode {
    Gamepad Gamepad;
    DcMotor frontLeftMotor;
    DcMotor frontRightMotor;
    DcMotor backLeftMotor;
    DcMotor backRightMotor;

    Double ticks; //Create ticks variable for each motor. Each motor has a number of ticks per rotation. This can be used to make half-turns
    Double newtarget;

    @Override
    public void init() { //This runs when hitting the init button on the driver station

        telemetry.addData("Hardware: ","Initialized");

        //Hardware mapping
        frontLeftMotor = hardwareMap.get(DcMotor.class, "frontLeftMotor");
        frontRightMotor = hardwareMap.get(DcMotor.class, "frontRightMotor");
        backLeftMotor = hardwareMap.get(DcMotor.class, " backLeftMotor");
        backRightMotor = hardwareMap.get(DcMotor.class, " backRightMotor");
    }

    @Override
    public void loop() { //This repeats when you hit the start button
        mecanum_set_power();
    }

    public void mecanum_set_power() {
        double y = Gamepad.left_stick_y;
        double x = -Gamepad.left_stick_x * 1.1;
        double r = -Gamepad.right_stick_x;
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
}