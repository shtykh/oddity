package shtykh.oddity.engine;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import shtykh.oddity.data.TournamentFullResult;
import shtykh.oddity.data.dto.Team;
import shtykh.oddity.data.dto.Tournament;
import shtykh.oddity.data.odd.Oddity;
import shtykh.oddity.data.odd.TeamOddityOnTournament;
import shtykh.oddity.data.odd.TournamentReport;
import shtykh.oddity.source.ResultSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by shtykh on 27/09/16.
 */
@Component
public class OddityEngine {
	private static Logger log = Logger.getLogger(OddityEngine.class);
	
	@Autowired
	ResultSource source;

	public TournamentReport oddityTornamentReport(int tournamentId, double oddityCoef) {
		return oddityTornamentReport(source.getTournament(tournamentId), oddityCoef);
	}

	private TournamentReport oddityTornamentReport(Tournament tournament, double oddityCoef) {
		Map<Team, Oddity> oddities = new HashMap<>();
		source.getFullResults(tournament.getIdtournament())
				.getFullResultMap()
				.keySet()
				.forEach(teamId -> {
					Team team = source.getTeam(teamId);
					oddities.put(team, oddity(tournament, team, oddityCoef));
				});
		return new TournamentReport()
				.setTournament(tournament)
				.setOddities(oddities);
	}

	public List<TeamOddityOnTournament> teamOddityReport(int teamId, double oddityCoef) {
		Team team = source.getTeam(teamId);
		return source.getTournamentsForTeam(teamId).stream()
				.map(tourn -> oddity(oddityTornamentReport(tourn, oddityCoef), team))
				.collect(Collectors.toList());
	}

	private Oddity oddity(Tournament tourn, Team team, double oddityCoef) {
		int teamId = team.getIdteam();
		TournamentFullResult fullResults = source.getFullResults(tourn.getIdtournament());
		List<Integer> result = fullResults.getFullResult(teamId).getQuestionList();
		List<Double> successRate = fullResults.successRate();
		OddityCombiner combiner = new OddityCombiner(
				oddityCoef);
		Pair<Double, Double> oddity = summCombined(result, successRate, combiner)
				.orElse(Pair.of(0.,0.));
		return new Oddity()
				.setValues(oddity)
				.setTeam(team)
				.setTournament(tourn)
				.setResult(fullResults.getResultMap().get(teamId));
	}

	private TeamOddityOnTournament oddity(TournamentReport report, Team team) {
		return new TeamOddityOnTournament()
				.setStatistics(report.getStatistics())
				.setOddity(report.getOddities().get(team));
	}

	private <T1, T2> Optional<Pair<Double, Double>> summCombined(
														List<T1> a,
														List<T2> b,
														BiFunction<T1, T2, Pair<Double, Double>> combiner) {
		int size = a.size();
		if (size != b.size()) {
//			log.error(MessageFormat.format("{0} != {1}", size, b.size()));
			return Optional.empty();
		} else {
			return IntStream
					.range(0, size)
					.mapToObj(i -> combiner.apply(a.get(i), b.get(i)))
					.reduce((a1, b1) -> Pair.of(a1.getLeft() + b1.getLeft(), a1.getRight() + b1.getRight()));
		}
	}
}
