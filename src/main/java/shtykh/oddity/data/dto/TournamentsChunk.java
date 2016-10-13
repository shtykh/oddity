package shtykh.oddity.data.dto;

import java.util.List;

/**
 * Created by shtykh on 21/09/16.
 */
public class TournamentsChunk {
	private String current_items; 
	private List<Tournament> items;
	private int total_items;

	public String getCurrent_items() {
		return current_items;
	}

	public void setCurrent_items(String current_items) {
		this.current_items = current_items;
	}

	public List<Tournament> getItems() {
		return items;
	}

	public void setItems(List<Tournament> items) {
		this.items = items;
	}

	public int getTotal_items() {
		return total_items;
	}

	public void setTotal_items(int total_items) {
		this.total_items = total_items;
	}
}
