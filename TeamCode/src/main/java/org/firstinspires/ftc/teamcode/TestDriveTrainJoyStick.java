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
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "Into_the_Deep_Teleop")

public class TestDriveTrainJoyStick extends OpMode {

    public DcMotor frontLeftMotor;
    public DcMotor frontRightMotor;
    public DcMotor backLeftMotor;
    public DcMotor backRightMotor;
    public DcMotor armMotor;
    public DcMotor slideMotor; //Linear slides motor
    public CRServo wristServo;
    public CRServo intakeServo;

    @Override
    public void init() { //This runs when hitting the init button on the driver station

        telemetry.addData("Hardware: ","Initialized");

        //Hardware mapping gives each hardware object a name, which must be entered in the robot's configuration
        //Motor Mapping
        frontLeftMotor = hardwareMap.get(DcMotor.class, "frontLeftMotor");
        frontRightMotor = hardwareMap.get(DcMotor.class, "frontRightMotor");
        frontRightMotor.setDirection(DcMotor.Direction.REVERSE);
        backLeftMotor = hardwareMap.get(DcMotor.class, " backLeftMotor");
        backRightMotor = hardwareMap.get(DcMotor.class, " backRightMotor");
        backRightMotor.setDirection(DcMotor.Direction.REVERSE);
        armMotor = hardwareMap.get(DcMotor.class, "armMotor");
        slideMotor = hardwareMap.get(DcMotor.class,"slideMotor");

        //Servo Mapping
        intakeServo = hardwareMap.get(CRServo.class,"intakeServo");
        wristServo = hardwareMap.get(CRServo.class, "wristServo");

        //Setting motors to run using encoders
        /*
        DcMotor[] motors = {frontLeftMotor,frontRightMotor,backLeftMotor,backRightMotor};
        for (DcMotor motor: motors) {
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
        */

    }

    @Override
    public void loop() { //This repeats when you hit the start button
        mecanum_drivetrain();
        arm();
        intake();
        wrist();
        linear_slides();
    }

    public void mecanum_drivetrain() { //Checks joystick input and accordingly sets power level to motors in the mecanum drivetrain
        //Joystick variables and denominator
        frontLeftMotor.setPower(gamepad2.left_stick_y);
        frontRightMotor.setPower(gamepad2.right_stick_y);
        backLeftMotor.setPower(gamepad2.left_stick_x);
        backRightMotor.setPower(gamepad2.right_stick_x);
        //
        /* bad
        double y = gamepad2.left_stick_y;
        double x = -gamepad2.left_stick_x * 1.1;
        double r = -gamepad2.right_stick_x;
        */
/*
        double y = -gamepad2.right_stick_x;
        double x = gamepad2.right_stick_y * 1.1;
        double r = gamepad2.left_stick_y;


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

 */

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
            while (true){
                armMotor.setPower(-1);
                if (gamepad2.right_bumper){ //The right bumper can unlock the arm and unblock input
                    break;
                }
            }
        } else {
            armMotor.setPower(0);
        }

        telemetry.addData("Motor Ticks: ", armMotor.getCurrentPosition());
    }

    public void intake(){
        //intake_servo (how the arm retrieves the game-pieces)
        if (gamepad2.left_bumper){
            intakeServo.setPower(-1);
        }
        else if (gamepad2.left_trigger > 0.2){
            intakeServo.setPower(0.5);
        } else {
            intakeServo.setPower(0.0);
        }
    }

    public void wrist(){
        if (gamepad2.x){
            wristServo.setPower(0.5);
        }
        else if (gamepad2.b) {
            wristServo.setPower(-0.5);
        } else {
            wristServo.setPower(0.0);
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

}