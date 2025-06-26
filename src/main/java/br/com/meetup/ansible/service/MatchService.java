package br.com.meetup.ansible.service;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import br.com.meetup.ansible.domain.Match;

public interface MatchService {

	List<Match> listMatchesFromHomeTeam(@NotEmpty String homeTeam);
	
	List<Match> listExactMatches(@NotEmpty String homeTeam, @NotEmpty String awayTeam);

	public List<MatchDTO> findByLeagueAndSeason(String league, String season) {
    return matchRepository.findByLeagueAndSeason(league, season)
            .stream()
            .map(MatchDTO::new)
            .collect(Collectors.toList());
	}
}
