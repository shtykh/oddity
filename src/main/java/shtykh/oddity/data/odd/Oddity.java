package shtykh.oddity.data.odd;

import org.apache.commons.lang3.tuple.Pair;
import shtykh.oddity.data.dto.Result;
import shtykh.oddity.data.dto.Team;
import shtykh.oddity.data.dto.Tournament;

import java.util.Comparator;

/**
 * Created by shtykh on 26/09/16.
 */
public class Oddity implements Comparable <Oddity> {
	public static Comparator<? super Oddity> comparator = new Comparator<Oddity>() {
		@Override
		public int compare(Oddity o1, Oddity o2) {
			return o1.compareTo(o2);
		}
	};
	
	private double value;
	private Team team;
	private Tournament tournament;
	private Result result;
	private Double fails;
	private Double wins;

	public Team getTeam() {
		return team;
	}

	public Double getFails() {
		return fails;
	}

	public Double getWins() {
		return wins;
	}

	public double getValue() {
		return value;
	}

	public Oddity setValues(Pair<Double, Double> values) {
		this.fails = values.getLeft();
		this.wins = values.getRight();
		this.value = wins + fails;
		return this;
	}

	public Oddity setTeam(Team team) {
		this.team = team;
		return this;
	}

	@Override
	public int compareTo(Oddity o) {
		return Double.compare(value, o.value);
	}

	public Oddity setResult(Result result) {
		this.result = result;
		return this;
	}

	public Result getResult() {
		return result;
	}

	public Tournament getTournament() {
		return tournament;
	}

	public Oddity setTournament(Tournament tournament) {
		this.tournament = tournament;
		return this;
	}
}
