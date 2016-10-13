package shtykh.oddity.rest.control;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import shtykh.oddity.data.FullResult;
import shtykh.oddity.data.TournamentFullResult;
import shtykh.oddity.data.dto.*;
import shtykh.oddity.rest.view.ResultView;
import shtykh.oddity.source.ResultSource;

import java.util.List;
import java.util.Map;

/**
 * Created by shtykh on 20/09/16.
 */
@Controller
@Api(value = "Information gotten from rating api", description = "Information gotten from rating api")
public class ResultController {
	@Autowired
	private ResultSource model;

	@Autowired
	private ResultView resultView;

	@ResponseBody
	@RequestMapping(value = "results/{tourn}/full", method = RequestMethod.GET)
	public TournamentFullResult resultsFull(@PathVariable("tourn") int tourn) {
		return model.getFullResults(tourn);
	}

	@ResponseBody
	@RequestMapping(value = "results/{tourn}", method = RequestMethod.GET)
	public Result[] results(@PathVariable("tourn") int tourn) {
		return model.getResults(tourn);
	}

	@ResponseBody
	@RequestMapping(value = "results/{tourn}/team/{team}", method = RequestMethod.GET)
	public FullResult resultTeam(@PathVariable("tourn") int tourn, @PathVariable("team") int team) {
		return model.getFullResult(tourn, team);
	}

	@ResponseBody
	@RequestMapping(value = "tournaments/team/{team}", method = RequestMethod.GET)
	public List<Tournament> tournamentsForTeam(@PathVariable("team") int team) {
		return model.getTournamentsForTeam(team);
	}

	@ResponseBody
	@RequestMapping(value = "results/team/{team}", method = RequestMethod.GET)
	public Map<Tournament, FullResult> resultsForTeam(@PathVariable("team") int team) {
		return model.getFullResultsForTeam(team);
	}

	@ResponseBody
	@RequestMapping(value = "tournaments/{tourn}", method = RequestMethod.GET)
	public Tournament tournament(@PathVariable("tourn") int tourn) {
		return model.getTournament(tourn);
	}

	@ResponseBody
	@RequestMapping(value = "tournaments/all", method = RequestMethod.GET)
	public List<Tournament> tournament() {
		return model.getTournaments();
	}

	@ResponseBody
	@RequestMapping(value = "team/{team}", method = RequestMethod.GET)
	public Team team(@PathVariable("team") int team) {
		return model.getTeam(team);
	}

	@ResponseBody
	@RequestMapping(value = "clearCache", method = RequestMethod.DELETE)
	public void clearCache() {
		model.clearCache();
	}
	
}
