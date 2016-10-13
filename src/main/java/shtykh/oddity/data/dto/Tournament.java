package shtykh.oddity.data.dto;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Date;

/**
 * Created by shtykh on 14/09/16.
 */
public class Tournament implements Serializable {
	private Integer idtournament;
	private String name;
	private String town;
	private String long_name;
	private Date date_start;
	private Date date_end;
	private Integer tour_count;
	private Integer tour_questions;
	private Integer tour_ques_per_tour;
	private Integer questions_total;
	private String type_name;
	private String comment;
	private String site_url;

	public Integer getIdtournament() {
		return idtournament;
	}

	public void setIdtournament(Integer idtournament) {
		this.idtournament = idtournament;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getLong_name() {
		return long_name;
	}

	public void setLong_name(String long_name) {
		this.long_name = long_name;
	}

	public Date getDate_start() {
		return date_start;
	}

	public void setDate_start(Date date_start) {
		this.date_start = date_start;
	}

	public Date getDate_end() {
		return date_end;
	}

	public void setDate_end(Date date_end) {
		this.date_end = date_end;
	}

	public Integer getTour_count() {
		return tour_count;
	}

	public void setTour_count(Integer tour_count) {
		this.tour_count = tour_count;
	}

	public Integer getTour_questions() {
		return tour_questions;
	}

	public void setTour_questions(Integer tour_questions) {
		this.tour_questions = tour_questions;
	}

	public Integer getTour_ques_per_tour() {
		return tour_ques_per_tour;
	}

	public void setTour_ques_per_tour(Integer tour_ques_per_tour) {
		this.tour_ques_per_tour = tour_ques_per_tour;
	}

	public Integer getQuestions_total() {
		return questions_total;
	}

	public void setQuestions_total(Integer questions_total) {
		this.questions_total = questions_total;
	}

	public String getType_name() {
		return type_name;
	}

	public void setType_name(String type_name) {
		this.type_name = type_name;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getSite_url() {
		return site_url;
	}

	public void setSite_url(String site_url) {
		this.site_url = site_url;
	}

	@Override
	public String toString() {
		return MessageFormat.format(
				"Tournament'{" +
						"'idtournament={0}, " +
						"name=''{1}'', " +
						"town=''{2}'', " +
						"long_name=''{3}'', " +
						"date_start={4}, " +
						"date_end={5}, " +
						"tour_count={6}, " +
						"tour_questions={7}, " +
						"tour_ques_per_tour={8}, " +
						"questions_total={9}, " +
						"type_name=''{10}'', " +
						"comment=''{11}'', " +
						"site_url=''{12}'''}'", 
				idtournament, name, town, long_name, date_start, date_end, tour_count, 
				tour_questions, tour_ques_per_tour, questions_total, type_name, comment, site_url);
	}
}
