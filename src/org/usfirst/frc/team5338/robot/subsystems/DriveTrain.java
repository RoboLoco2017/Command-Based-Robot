package org.usfirst.frc.team5338.robot.subsystems;

import org.usfirst.frc.team5338.robot.OI;
import org.usfirst.frc.team5338.robot.Robot;
import org.usfirst.frc.team5338.robot.commands.ArcadeDriveWithJoysticks;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The DriveTrain subsystem incorporates the sensors and actuators attached to
 * the robots chassis. These include four drive motors.
 */
public class DriveTrain extends Subsystem
{
	public final CANTalon DRIVEL1 = new CANTalon(4);
    public final CANTalon DRIVEL2 = new CANTalon(3);
    public final CANTalon DRIVER1 = new CANTalon(2);
    public final CANTalon DRIVER2 = new CANTalon(1);
	
	public final RobotDrive DRIVE = new RobotDrive(DRIVEL1, DRIVEL2, DRIVER1, DRIVER2);

	private double throttle = 1.0;

	public DriveTrain()
	{
		super();
	}
	/**
	 * When no other command is running let the operator drive around using the
	 * twin joysticks.
	 */
	@Override
	public void initDefaultCommand()
	{
		setDefaultCommand(new ArcadeDriveWithJoysticks());
	}
	/**
	 * Arcade style driving for the DriveTrain.
	 * 
	 * @param left
	 *            Speed in range [-1,1]
	 * @param right
	 *            Speed in range [-1,1]
	 */
	public void drive(double left, double right)
	{
		DRIVE.tankDrive(throttle * -left, -throttle * right, false);
	}
	public void tank(double left, double right)
	{
		DRIVE.drive(left, right);
	}
	/**
	 * @param joy
	 *            The XBOX style joystick to use to drive arcade style.
	 */
	public void drive(OI oi)
	{
		Joystick joyL = oi.getJoystick(0);
		Joystick joyR = oi.getJoystick(1);
		if(oi.get(JETSONRESET))
		{
			Robot.jetsonReset.set(Relay.Value.kOn);
		}
		else
		{
			Robot.jetsonReset.set(Relay.Value.kOff);
		}
		if(oi.get(SLOW))
		{
			throttle = 0.5;
		}
		else
		{
			throttle = 1.0;
		}
		if(oi.get(STRAIGHT))
		{
			drive(oi.getRight(), oi.getRight());
		}
		else
		{
	    DRIVE.tankDrive(throttle * -oi.getLeft(), throttle * -oi.getRight(), false);
		}
	}
	
	private double joystickDeadZone(double value)
	{
		if (value > 0.05 || value < -0.05)
		{
		 return (value - 0.05)/0.95;
		}
		else if (value < -0.05)
		{
		 return (value + 0.05)/0.95;
		}
		return 0.0;
	}
	
	public double getThrottle()
	{
		return throttle;
	}
}
