package shtykh.oddity.data;

import shtykh.oddity.data.dto.Tournament;

import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by shtykh on 19/09/16.
 */

public class TournamentFullResult extends TournamentResult {
	private Tournament tournament;
	private Map<Integer, FullResult> fullResultMap = new TreeMap<>();

	private List<Double> successRate = null;

	@Override
	public String toString() {
		return MessageFormat.format(
				"TournamentFullResult'{'\n\ttournament={0}, \n\tfullResultMap of {1}, \n\tresultMap of {2}\n'}'",
				tournament,
				fullResultMap.size(),
				resultMap.size());
	}

	private TournamentFullResult() {
	}

	public void setSuccessRate(List<Double> successRate) {
		this.successRate = successRate;
	}

	@Deprecated
	/**
	 * for serialization only. please use successRate()
	 */
	public List<Double> getSuccessRate() {
		return successRate;
	}

	public TournamentFullResult(Tournament tournament) {
		this();
		this.tournament = tournament;
	}

	public List<Double> successRate() {
		return successRate == null ? initSuccessRate() : successRate;
	}

	private List<Double> initSuccessRate() {
		List<Integer> numSuccesses = new ArrayList<>();
		Iterator<FullResult> iterator = fullResultMap.values().iterator();
		numSuccesses.addAll(iterator.next().getQuestionList());
		iterator.forEachRemaining(fullResult -> {
			List<Integer> list = fullResult.getQuestionList();
			for (int i = 0; i < list.size(); i++) {
				numSuccesses.set(i, numSuccesses.get(i) + list.get(i));
			}
		});
		int teamsNumber = fullResultMap.size();
		return numSuccesses.stream().map(summ -> ((double)summ) / teamsNumber).collect(Collectors.toList());
	}

	public FullResult getFullResult (int key) {
		return fullResultMap.get(key);
	}

	public FullResult putFullResult(Integer teamId, FullResult fullResult) {
		return fullResultMap.put(teamId, fullResult);
	}

	public Tournament getTournament() {
		return tournament;
	}

	public void setFullResultMap(Map<Integer, FullResult> fullResultMap) {
		this.fullResultMap = fullResultMap;
	}

	public void setTournament(Tournament tournament) {
		this.tournament = tournament;
	}

	public Map<Integer, FullResult> getFullResultMap() {
		return fullResultMap;
	}
}
