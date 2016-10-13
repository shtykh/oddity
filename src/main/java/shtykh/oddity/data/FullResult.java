package shtykh.oddity.data;

import shtykh.oddity.data.dto.TourResult;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by shtykh on 19/09/16.
 */
public class FullResult {
	private List<TourResult> tours;

	private List<Integer> questionList;

	private FullResult() {}

	public FullResult(TourResult[] tourResults) {
		setTours(Arrays.asList(tourResults));
	}

	public void setQuestionList(List<Integer> questionList) {
		this.questionList = questionList;
	}

	public List<TourResult> getTours() {
		return tours;
	}

	public void setTours(List<TourResult> tours) {
		this.tours = tours;
	}

	public List<Integer> getQuestionList() {
		return questionList == null ? initQuestions() : questionList;
	}

	private List<Integer> initQuestions() {
		if (tours.size() > 3) {
			System.out.println("tours = " + tours);
		}
		return tours.stream().sequential().flatMap(this::tourToStream).collect(Collectors.toList());
	}

	private Stream<Integer> tourToStream(TourResult tour) {
		return tour.getMask().stream().sequential();
	}
}
