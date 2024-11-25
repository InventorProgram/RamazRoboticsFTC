/*
Documentation:
insert-useful info here
 */

package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Robot;

@Autonomous(name = "Into_the_Deep_Autonomous")

public class AutonomousAutomaton extends LinearOpMode {

    public Robot robot;
    public DcMotor frontLeftMotor;
    public DcMotor frontRightMotor;
    public DcMotor backLeftMotor;
    public DcMotor backRightMotor;
    public DcMotor armMotor;
    public CRServo wristServo;
    public CRServo intakeServo;

    @Override
    public void runOpMode() throws InterruptedException {
    }
}
