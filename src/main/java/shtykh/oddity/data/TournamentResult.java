package shtykh.oddity.data;

import shtykh.oddity.data.dto.Result;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by shtykh on 21/09/16.
 */
public class TournamentResult {
	protected Map<Integer, Result> resultMap = new TreeMap<>();

	public TournamentResult initResults(Result[] results) {
		if (results != null && results.length > 0) {
			Arrays.stream(results).forEach(r -> this.resultMap.put(r.getIdteam(), r));
		}
		return this;
	}

	public Map<Integer, Result> getResultMap() {
		return resultMap;
	}

	public void setResultMap(Map<Integer, Result> resultMap) {
		this.resultMap = resultMap;
	}

	public void putResult(Integer teamId, Result result) {
		resultMap.put(teamId, result);
	}

	public Result getResult(int key) {
		return resultMap.get(key);
	}
}
