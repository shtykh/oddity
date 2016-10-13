package shtykh.oddity.data.odd;

import java.util.DoubleSummaryStatistics;

/**
 * Created by shtykh on 02/10/16.
 */
public class TeamOddityOnTournament {

	private DoubleSummaryStatistics statistics;
	private Oddity oddity;

	@Override
	public String toString() {
		return "TeamOddityOnTournament{" +
				"statistics=" + statistics +
				", oddity=" + oddity +
				'}';
	}

	public TeamOddityOnTournament setStatistics(DoubleSummaryStatistics statistics) {
		this.statistics = statistics;
		return this;
	}

	public DoubleSummaryStatistics getStatistics() {
		return statistics;
	}

	public TeamOddityOnTournament setOddity(Oddity oddity) {
		this.oddity = oddity;
		return this;
	}

	public Oddity getOddity() {
		return oddity;
	}
}
