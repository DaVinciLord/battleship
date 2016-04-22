package serie09;

import java.util.Observable;

import util.Contract;

public class StdSpeedometerModel 
	extends Observable implements SpeedometerModel {

	private double step;
	private double max;
	private boolean state;
	private SpeedUnit unit;
	private double speed;

	StdSpeedometerModel(double step, double max) {
		Contract.checkCondition(step >= 1 && step <= max, "invalide step");
		this.step = step;
		this.max = max;
		state = false;
		unit = SpeedUnit.KMH;
		speed = 0;
	}
	
	
	@Override
	public double getMaxSpeed() {
		return max;
	}

	@Override
	public double getSpeed() {
		return speed;
	}

	@Override
	public double getStep() {
		return step;
	}

	@Override
	public SpeedUnit getUnit() {
		return unit;
	}

	@Override
	public boolean isOn() {
		return state;
	}

	@Override
	public void setUnit(SpeedUnit unit) {
	Contract.checkCondition(unit != null, "null unit");
	speed = speed / getUnit().getUnitPerKm() * unit.getUnitPerKm();
	step = step / getUnit().getUnitPerKm() * unit.getUnitPerKm();
	max = max / getUnit().getUnitPerKm() * unit.getUnitPerKm();
	this.unit = unit;
	}

	@Override
	public void slowDown() {
		Contract.checkCondition(isOn(), "Appuyer sur la pedale de frein "
				+ "ne sert pas a grand chose ...");
		speed = Math.max(speed - step, 0);
	}

	@Override
	public void speedUp() {
		Contract.checkCondition(isOn(), "Missing key");
		speed = Math.min(getMaxSpeed(), speed + step);
	}

	@Override
	public void turnOff() {
		Contract.checkCondition(isOn(),
				"Vous essayer d'éteindre une voiture a l arret");
		state = false;
		speed = 0;
	}

	@Override
	public void turnOn() {
		Contract.checkCondition(!isOn(), "Vous l'entendez pas le moteur là ??");
		state = true;
	}

}
