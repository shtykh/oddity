package shtykh.oddity.source;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import shtykh.oddity.data.FullResult;
import shtykh.oddity.data.TournamentFullResult;
import shtykh.oddity.data.TournamentResult;
import shtykh.oddity.data.dto.*;
import shtykh.oddity.source.cache.Cache;
import shtykh.oddity.source.cache.CachedSource;
import shtykh.oddity.util.Serializer;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Created by shtykh on 14/09/16.
 */
@Component
public class ResultClient implements ResultSource, CachedSource {
	private static Logger log = Logger.getLogger(ResultClient.class);
	
	@Autowired
	private RatingRestClient rest;

	@Autowired
	private Cache cache;
	
	@Autowired
	private Serializer serializer;

	@Override
	public Cache getCache() {
		return cache;
	}

	@Override
	public void clearCache() {
		getCache().clear();
	}

	@Override
	public StringSource getSource() {
		return rest;
	}

	@Override
	public FullResult getFullResult(Integer tournament_id, Integer team_id) {
		return new FullResult(get(TourResult[].class, tournament_id, team_id).get());
	}

	@Override
	public Result[] getResults(Integer tournament_id) {
		return get(Result[].class, tournament_id).get();
	}

	@Override
	public Tournament getTournament(Integer tournament_id) {
		return get(Tournament[].class, tournament_id).get()[0];
	}

	@Override
	public TournamentFullResult getFullResults(final Integer tournament_id) {
		return get(TournamentFullResult.class, makeFullResult(tournament_id), tournament_id).get();
	}

	@Override
	public Team getTeam(Integer teamId) {
		return get(Team[].class, teamId).get()[0];
	}

	@Override
	public List<Tournament> getTournaments() {
		List<Tournament> tournaments = new ArrayList<>();
		for (int page = 1; true; page++) {
			TournamentsChunk tournamentsChunk = getTournamentsChunk(page);
			tournaments.addAll(tournamentsChunk.getItems());
			if (isChunkTheLast(tournamentsChunk)) {
				break;
			}
		}
		return tournaments;
	}

	@Override
	public List<Tournament> getTournamentsForTeam(int team) {
		return getTournaments()
				.parallelStream()
				.filter(tourn -> {
					Optional<TournamentResult> results = getTournamentResults(tourn.getIdtournament()) ;
					return results.isPresent() && results.get().getResultMap().containsKey(team);
				})
				.collect(Collectors.toList());
	}

	@Override
	public Map<Tournament, FullResult> getFullResultsForTeam(int team) {
		Map<Tournament, FullResult> results = new HashMap<>();
		getTournamentsForTeam(team).forEach(t->results.put(t, getFullResult(t.getIdtournament(), team)));
		return results;
	}

	private boolean isChunkTheLast(TournamentsChunk tournamentsChunk) {
		Integer[] borders = Arrays.stream(tournamentsChunk.getCurrent_items()
				.split("\\-"))
				.map(Integer::decode)
				.toArray(Integer[]::new);
		int total = tournamentsChunk.getTotal_items();
		return total >= borders[0] && total <= borders[1];
	}

	private Optional<TournamentResult> getTournamentResults(Integer tournament_id) {
		return get(TournamentResult.class, makeResult(tournament_id), tournament_id);
	}

	private TournamentsChunk getTournamentsChunk(Integer page) {
		return get(TournamentsChunk.class, page).get();
	}

	private Supplier<Optional<TournamentFullResult>> makeFullResult(Integer tournament_id) {
		return () -> {
			try {
				Result[] results = getResults(tournament_id);
				TournamentFullResult fullResults = new TournamentFullResult(getTournament(tournament_id));
				for (Result result : results) {
					Integer teamId = result.getIdteam();
					fullResults.putFullResult(teamId, getFullResult(tournament_id, teamId));
					fullResults.putResult(teamId, result);
				}
				return Optional.of(fullResults);
			} catch (Exception e) {
				return Optional.empty();
			}
		};
	}

	private Supplier<Optional<TournamentResult>> makeResult(Integer tournament_id) {
		return () -> {
			try {
				return Optional.of(new TournamentResult().initResults(get(Result[].class, tournament_id).get()));
			} catch (Exception e) {
				return Optional.empty();
			}
		};
	}

	@Override
	public Serializer getSerializer() {
		return serializer;
	}
}
