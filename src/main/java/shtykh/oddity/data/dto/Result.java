package shtykh.oddity.data.dto;

import java.io.Serializable;

/**
 * Created by shtykh on 14/09/16.
 */
public class Result implements Serializable {
	private int idteam;
	private double position;
	private int questions_total;
	private Integer bonus_a;
	private Integer bonus_b;

	@Override
	public String toString() {
		return "{position=" + position +
				", questions=" + questions_total +
				", bonus_a=" + bonus_a +
				", bonus_b=" + bonus_b +
				'}';
	}

	public int getIdteam() {
		return idteam;
	}

	public void setIdteam(int idteam) {
		this.idteam = idteam;
	}

	public double getPosition() {
		return position;
	}

	public void setPosition(double position) {
		this.position = position;
	}

	public int getQuestions_total() {
		return questions_total;
	}

	public void setQuestions_total(int questions_total) {
		this.questions_total = questions_total;
	}

	public Integer getBonus_a() {
		return bonus_a;
	}

	public void setBonus_a(Integer bonus_a) {
		this.bonus_a = bonus_a;
	}

	public Integer getBonus_b() {
		return bonus_b;
	}

	public void setBonus_b(Integer bonus_b) {
		this.bonus_b = bonus_b;
	}
}
