/*
Documentation:
insert-useful info here
 */

package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "Into_the_Deep_Autonomous")

public class AutonomousAutomaton extends LinearOpMode {

    public Robot bot = new Robot();

    @Override
    public void runOpMode() throws InterruptedException {
        bot.move(180,5);
    }
}
