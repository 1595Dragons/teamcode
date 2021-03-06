package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@TeleOp(name = "Basic TeleOp", group = "Official")
@Disabled
public class BasicTeleop extends LinearOpMode {

    private Config7935 robot = new Config7935(this);

    public void runOpMode() {

        // Initialize the robot
        robot.ConfigureRobtHardware();
        //Motor Power
        double speedTurn=1,speedMove=1,speedSide=1;
        double slowTurn = 0.5, slowMove=0.5,slowSide=0.7;
        boolean slowMode=false, powerMode=false;
        double powerUp=1.3;
        waitForStart();

        while (opModeIsActive()) {

            //Power up function
            if (gamepad2.left_trigger>0){
                slowMode=true;
            }else{
                slowMode=false;
            }
            if (gamepad2.right_trigger>0){
                powerMode=true;
                slowMode=false;
            }else{
                powerMode=false;
            }

            double driveForward = gamepad2.left_stick_y * speedMove, driveRightSide = gamepad2.left_stick_x * speedSide,
                    turnRight = -gamepad2.right_stick_x * speedTurn;
            double intakePower=gamepad1.left_trigger-gamepad1.right_trigger;
            //prevent small input from stick
            driveForward = (driveForward >= -0.1 && driveForward <= 0.1) ? 0 : driveForward;
            driveRightSide = (driveRightSide >= -0.1 && driveRightSide <= 0.1) ? 0 : driveRightSide;
            turnRight = (turnRight >= -0.1 && turnRight <= 0.1) ? 0 : turnRight;

            if (gamepad2.dpad_left){
                driveRightSide=-1*speedSide;
            }
            if (gamepad2.dpad_right){
                driveRightSide=1*speedSide;
            }
            if(gamepad2.dpad_up){
                driveForward=1*speedMove;
            }
            if(gamepad2.dpad_down){
                driveForward=-1*speedMove;
            }

            // Send calculated power to wheels
            if (slowMode){
                robot.left_front.setPower(Range.clip((-driveRightSide*slowSide + driveForward*slowMove + turnRight*slowTurn), -1.0, 1.0));
                robot.right_front.setPower(Range.clip((driveRightSide*slowSide + driveForward*slowMove - turnRight*slowTurn), -1.0, 1.0));
                robot.left_back.setPower(Range.clip((driveRightSide*slowSide + driveForward*slowMove + turnRight*slowTurn), -1.0, 1.0));
                robot.right_back.setPower(Range.clip((-driveRightSide*slowSide + driveForward*slowMove - turnRight*slowTurn), -1.0, 1.0));
            }else if(!powerMode){
                robot.left_front.setPower(Range.clip((-driveRightSide + driveForward + turnRight), -1.0, 1.0));
                robot.right_front.setPower(Range.clip((driveRightSide + driveForward - turnRight), -1.0, 1.0));
                robot.left_back.setPower(Range.clip((driveRightSide + driveForward + turnRight), -1.0, 1.0));
                robot.right_back.setPower(Range.clip((-driveRightSide + driveForward - turnRight), -1.0, 1.0));
            }else{
                robot.left_front.setPower(Range.clip((driveRightSide - driveForward + turnRight), -1.0, 1.0));
                robot.right_front.setPower(Range.clip((-driveRightSide - driveForward - turnRight), -1.0, 1.0));
                robot.left_back.setPower(Range.clip((-driveRightSide - driveForward + turnRight), -1.0, 1.0));
                robot.right_back.setPower(Range.clip((driveRightSide - driveForward - turnRight), -1.0, 1.0));
            }
            if (robot.intake_button.getState()==false){
                intakePower=-gamepad1.right_trigger;
            }
            robot.intake_left.setPower(intakePower);
            robot.intake_right.setPower(intakePower);


            /*
            if (gamepad1.a){
                robot.Servo_A.setPosition(0.5);
            }
            if (gamepad1.b){
                robot.Servo_A.setPosition(0);
            }
            */

            telemetry.addData("Lf power", robot.left_front.getPower())
                    .addData("Rf power", robot.right_front.getPower())
                    .addData("Lb power", robot.left_back.getPower())
                    .addData("Rb power", robot.right_back.getPower());
            telemetry.addData("button",robot.intake_button.getState());
            telemetry.update();

        }
    }
}
