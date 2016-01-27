package org.usfirst.frc.team2785.misc;

import edu.wpi.first.wpilibj.PIDOutput;

public class DummyOutput implements PIDOutput {
	private double value = 0;
	public void pidWrite(double output) {
		value = output;
	}
	public double getValue() {
		return value;
	}

}
