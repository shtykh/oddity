package shtykh.oddity.source;

import shtykh.oddity.data.FullResult;
import shtykh.oddity.data.TournamentFullResult;
import shtykh.oddity.data.dto.*;

import java.util.List;
import java.util.Map;

/**
 * Created by shtykh on 14/09/16.
 */
public interface ResultSource {
	void clearCache();

	FullResult getFullResult(Integer tournament_id, Integer team_id);

	Result[] getResults(Integer tournament_id);

	Tournament getTournament(Integer tournament_id);

	TournamentFullResult getFullResults(Integer tournament_id);

	Team getTeam(Integer teamId);

	List<Tournament> getTournaments();

	List<Tournament> getTournamentsForTeam(int team);

	Map<Tournament, FullResult> getFullResultsForTeam(int team);
}
