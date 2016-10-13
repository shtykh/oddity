package shtykh.oddity.data.dto;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by shtykh on 19/09/16.
 */
public class TourResult implements Serializable {
	private int tour;
	private List<Integer> mask;

	public int getTour() {
		return tour;
	}

	public void setTour(int tour) {
		this.tour = tour;
	}

	public List<Integer> getMask() {
		return mask;
	}

	public void setMask(String[] mask) {
		this.mask = Arrays.stream(mask).sequential().map(s -> {
			try {
				return Integer.decode(s);
			} catch (Exception e) {
				return 0;
			}
		}).collect(Collectors.toList());
	}
}
