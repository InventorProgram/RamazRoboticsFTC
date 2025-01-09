package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "HardwareTest")
public class HardwareTest extends OpMode {
    public DcMotor frontLeftMotor;
    public DcMotor frontRightMotor;
    public DcMotor backLeftMotor;
    public DcMotor backRightMotor;
    public DcMotor armMotor;
    public DcMotor slideMotor; //Linear slides motor
    public Servo wristServo;
    public CRServo intakeServo;

    @Override
    public void init() {

        //Hardware mapping gives each hardware object a name, which must be entered in the robot's configuration
        //Motor Mapping
        frontLeftMotor = hardwareMap.get(DcMotor.class, "frontLeftMotor");
        frontRightMotor = hardwareMap.get(DcMotor.class, "frontRightMotor");
        backLeftMotor = hardwareMap.get(DcMotor.class, "backLeftMotor");
        backRightMotor = hardwareMap.get(DcMotor.class, "backRightMotor");
        armMotor = hardwareMap.get(DcMotor.class, "armMotor");
        slideMotor = hardwareMap.get(DcMotor.class, "slideMotor");

        //Servo Mapping
        intakeServo = hardwareMap.get(CRServo.class, "intakeServo");
        wristServo = hardwareMap.get(Servo.class, "wristServo");
    }
    @Override
    public void loop() {
        drivetrain_testing();
    }

    public void drivetrain_testing() {
        double left_stick_y = gamepad2.left_stick_y;
        double left_stick_x = gamepad2.left_stick_x;
        double right_stick_y = gamepad2.right_stick_y;
        double right_stick_x = gamepad2.right_stick_x;

        //Left stick y
        if (left_stick_y > 0) {
            frontLeftMotor.setPower(0.5);
            backLeftMotor.setPower(0.5);
        }

        else if (left_stick_y < 0) {
            frontLeftMotor.setPower(-0.5);
            backLeftMotor.setPower(-0.5);
        }

        //Left stick x
        if (left_stick_x > 0) {
            frontLeftMotor.setPower(0.5);
            frontRightMotor.setPower(0.5);
        }

        else if (left_stick_x < 0) {
            frontLeftMotor.setPower(-0.5);
            frontRightMotor.setPower(-0.5);
        }

        //Right stick y
        if (right_stick_y > 0) {
            frontRightMotor.setPower(0.5);
            backRightMotor.setPower(0.5);
        }

        else if (right_stick_y < 0) {
            frontRightMotor.setPower(-0.5);
            backRightMotor.setPower(-0.5);
        }

        //Right stick x
        if (right_stick_x > 0) {
            backLeftMotor.setPower(0.5);
            backRightMotor.setPower(0.5);
        }

        else if (right_stick_x < 0) {
            backLeftMotor.setPower(-0.5);
            backRightMotor.setPower(-0.5);
        }
    }
}
