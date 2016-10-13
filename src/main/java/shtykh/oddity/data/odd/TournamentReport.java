package shtykh.oddity.data.odd;

import shtykh.oddity.data.dto.Team;
import shtykh.oddity.data.dto.Tournament;

import java.util.DoubleSummaryStatistics;
import java.util.Map;

/**
 * Created by shtykh on 26/09/16.
 */
public class TournamentReport {
	private Tournament tournament;
	private Map<Team, Oddity> oddities;

	public DoubleSummaryStatistics getStatistics() {
		return statistics == null ? initStatistics() : statistics;
	}

	public void setStatistics(DoubleSummaryStatistics statistics) {
		this.statistics = statistics;
	}

	private DoubleSummaryStatistics statistics;

	public TournamentReport setTournament(Tournament tournament) {
		this.tournament = tournament;
		return this;
	}

	public Tournament getTournament() {
		return tournament;
	}

	public TournamentReport setOddities(Map<Team, Oddity> oddities) {
		this.oddities = oddities;
		return this;
	}

	private DoubleSummaryStatistics initStatistics() {
		statistics = oddities
				.values()
				.parallelStream()
				.mapToDouble(Oddity::getValue)
				.summaryStatistics();
		return statistics;
	}

	public Map<Team, Oddity> getOddities() {
		return oddities;
	}
}
