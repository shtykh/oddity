package shtykh.oddity.data.dto;

import java.io.Serializable;

/**
 * Created by shtykh on 14/09/16.
 */
public class Team implements Serializable {
	private int idteam;
	private String name;
	private String town;
	private String comment;

	public int getIdteam() {
		return idteam;
	}

	public void setIdteam(int idteam) {
		this.idteam = idteam;
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public String toString() {
		return name + "(" + town + ")";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Team)) return false;

		Team team = (Team) o;

		if (idteam != team.idteam) return false;
		if (comment != null ? !comment.equals(team.comment) : team.comment != null) return false;
		if (name != null ? !name.equals(team.name) : team.name != null) return false;
		if (town != null ? !town.equals(team.town) : team.town != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = idteam;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (town != null ? town.hashCode() : 0);
		result = 31 * result + (comment != null ? comment.hashCode() : 0);
		return result;
	}
}
