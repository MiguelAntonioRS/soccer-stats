package io.answ.soccer.service;

import io.answ.soccer.dto.StandingDTO;
import io.answ.soccer.model.Match;
import io.answ.soccer.model.Team;
import io.answ.soccer.repository.MatchRepository;
import io.answ.soccer.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StandingService {

    private final MatchRepository matchRepository;
    private final TeamRepository teamRepository;

    public StandingService(MatchRepository matchRepository, TeamRepository teamRepository) {
        this.matchRepository = matchRepository;
        this.teamRepository = teamRepository;
    }

    public List<StandingDTO> generateStandings() {
        List<Match> matches = matchRepository.findAll();
        Map<Long, StandingDTO> standingsMap = new HashMap<>();

        // Inicializar standing por equipo
        for (Team team : teamRepository.findAll()) {
            standingsMap.put(team.getId(), new StandingDTO(team.getName()));
        }

        // Procesar partidos
        for (Match match : matches) {
            Long homeTeamId = match.getHomeTeam().getId();
            Long awayTeamId = match.getAwayTeam().getId();
            Integer homeGoals = match.getHomeGoals();
            Integer awayGoals = match.getAwayGoals();

            if (homeGoals != null && awayGoals != null) {
                standingsMap.get(homeTeamId).addMatchResult(true, homeGoals, awayGoals);
                standingsMap.get(awayTeamId).addMatchResult(false, awayGoals, homeGoals);
            }
        }

        // Convertir a lista y ordenar
        return standingsMap.values().stream()
                .sorted(Comparator.comparing(StandingDTO::getPoints).reversed()
                        .thenComparing(Comparator.comparing(StandingDTO::getGoalDifference).reversed())
                        .thenComparing(Comparator.comparing(StandingDTO::getGoalsFor).reversed()))
                .collect(Collectors.toList());
    }
}