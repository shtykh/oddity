package shtykh.oddity.engine;

import org.apache.commons.lang3.tuple.Pair;

import java.util.function.BiFunction;

/**
 * Created by shtykh on 29/09/16.
 */
public class OddityCombiner implements BiFunction<Integer, Double, Pair<Double, Double>> {
	private final double oddityCoef;

	public OddityCombiner(double oddityCoef) {
		this.oddityCoef = oddityCoef;
	}

	@Override
	/**
	 * produces pair of epic fail/win
	 */
	public Pair<Double, Double> apply(Integer success, Double rate) {
		if (rate < 1 - oddityCoef && rate > oddityCoef) {
			return Pair.of(0., 0.); // win or fail wasn't realy epic
		} else {
			if (success == 0) {
				return Pair.of(rate, 0.); // epic fail
			} else {
				return Pair.of(0., 1 - rate); // epic win
			}
		}
	}
}
