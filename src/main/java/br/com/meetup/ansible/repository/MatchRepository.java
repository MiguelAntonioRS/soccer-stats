package br.com.meetup.ansible.repository;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.meetup.ansible.domain.Match;

@Repository
public interface MatchRepository extends CrudRepository<Match, Integer> {
	
	List<Match> findByHomeTeam(String homeTeam);
	
	List<Match> findByAwayTeam(String awayTeam);
	
	List<Match> findByHomeTeamAndAwayTeam(String homeTeam, String awayTeam);

	private List<Match> loadMatchesFromCsv(String filename, String league, String season) throws Exception {
    List<Match> matches = new ArrayList<>();
    Path path = Paths.get(ClassLoader.getSystemResource(filename).toURI());
    
    try (BufferedReader reader = Files.newBufferedReader(path)) {
        String line;
        reader.readLine(); // Skip header
        while ((line = reader.readLine()) != null) {
            String[] values = line.split(",");
            Match match = new Match();
            match.setHomeTeam(findAll(values[1]));
            match.setAwayTeam(findAll(values[2]));
            match.setAwayTeamGoals(Integer.parseInt(values[3]));
            match.setAwayTeamGoals(Integer.parseInt(values[4]));
            match.setLeague(league);
            match.setSeason(season);
            matches.add(match);
    	    }
 	   }
    return matches;
	}
	List<Match> findByLeagueAndSeason(String league, String season);
}

