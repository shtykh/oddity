package shtykh.oddity.rest.control;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import shtykh.oddity.engine.OddityEngine;
import shtykh.oddity.rest.view.OddityView;

/**
 * Created by shtykh on 26/09/16.
 */
@Controller
@Api(value = "Information gotten from rating api", description = "How odd some results are")
public class OddityController {
	@Autowired
	private OddityEngine model;

	@Autowired
	private OddityView view;


	@ResponseBody
	@RequestMapping(value = "oddity/tournament/{tournament}", method = RequestMethod.GET)
	public String oddityTournament(
			@PathVariable("tournament") int tournament, 
			@RequestParam("odd") @ApiParam(defaultValue = "0.1") double oddityCoef) {
		return view.toString(model.oddityTornamentReport(tournament, oddityCoef));
	}

	@ResponseBody
	@RequestMapping(value = "oddity/team/{team}", method = RequestMethod.GET)
	public String oddityTeam(
			@PathVariable("team") int team,
			@RequestParam("odd") @ApiParam(defaultValue = "0.1") double oddityCoef) {
		return view.toString(model.teamOddityReport(team, oddityCoef));
	}
}
