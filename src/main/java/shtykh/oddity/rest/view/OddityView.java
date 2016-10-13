package shtykh.oddity.rest.view;

/**
 * Created by shtykh on 26/09/16.
 */

import org.springframework.stereotype.Component;
import shtykh.oddity.data.dto.Team;
import shtykh.oddity.data.dto.Tournament;
import shtykh.oddity.data.odd.Oddity;
import shtykh.oddity.data.odd.TeamOddityOnTournament;
import shtykh.oddity.data.odd.TournamentReport;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static java.text.MessageFormat.format;

@Component
public class OddityView {
	private DateFormat dateFormat = new SimpleDateFormat("dd LLL YY");
	private Comparator<? super Map.Entry<Team, Oddity>> odditiEntryComparator = new Comparator<Map.Entry<Team, Oddity>>() {
		@Override
		public int compare(Map.Entry<Team, Oddity> o1, Map.Entry<Team, Oddity> o2) {
			return o2.getValue().compareTo(o1.getValue());
		}
	};

	public String toString(TournamentReport report) {
		StringBuilder sb = new StringBuilder();
		Tournament tournament = report.getTournament();
		sb.append(toString(tournament) + "\n");
		sb.append(toString(report.getStatistics()) + "\n\n");
		printOddities(report.getOddities(), sb);
		return sb.toString();
	}

	private String toString(Tournament tournament) {
		return format("{0}-{1}({2})",
				tournament.getName(),
				dateFormat.format(tournament.getDate_start()),
				tournament.getType_name());
	}

	private void printOddities(Map<Team, Oddity> oddities, StringBuilder sb) {
		ArrayList<Map.Entry<Team, Oddity>> list = new ArrayList<>(oddities.entrySet());
		list.sort(odditiEntryComparator);
		AtomicInteger i = new AtomicInteger();
		list.stream()
				.map(ent -> format("{0}. {1}: {2}\n",
						i.addAndGet(1),
						ent.getKey(),
						toString(ent.getValue())))
				.forEach(sb::append);
	}

	private String toString(Oddity odd) {
		return format("{0}({2} wins, {3} fails)\n {1}",
				odd.getValue(),
				odd.getResult(),
				odd.getWins(),
				odd.getFails());
	}

	public String toString(List<TeamOddityOnTournament> teamOddityOnTournaments) {
		StringBuilder sb = new StringBuilder();
		teamOddityOnTournaments.forEach(t -> {
			sb.append(MessageFormat.format("{0}:\n {1}\n {2}\n {3}\n\n",
					toString(t.getOddity().getTournament()),
					score(t),
					toString(t.getOddity()), 
					toString(t.getStatistics())
					));
		});
		return sb.toString();
	}

	private String score(TeamOddityOnTournament t) {
		double value = t.getOddity().getValue();
		if (value > t.getStatistics().getAverage()) {
			if (Double.valueOf(value).equals(t.getStatistics().getMax())) {
				return "THE ODDEST";
			} else {
				return "RATHER ODD";
			}
		} else {
			if (Double.valueOf(value).equals(t.getStatistics().getMin())) {
				return "the least odd";
			} else {
				return "not so odd";
			}
		}
	}

	private String toString(DoubleSummaryStatistics stat) {
		return MessageFormat.format("[min, ave, max]=[{0}, {1}, {2}]",
				stat.getMin(),
				stat.getAverage(),
				stat.getMax()
		);
	}
}
