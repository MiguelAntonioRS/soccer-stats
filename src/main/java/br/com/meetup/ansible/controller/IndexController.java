package br.com.meetup.ansible.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.meetup.ansible.domain.Match;
import br.com.meetup.ansible.service.MatchService;

@Controller
public class IndexController {

    @Autowired
    private MatchService matchService;

    public IndexController() {

    }

    @GetMapping("/")
    public String index(Map<String, Object> model) {
        model.put("time", new Date());
        return "index";
    }

    @GetMapping
    public List<Match> getAllMatches(
            @RequestParam(required = false) String league,
            @RequestParam(required = false) String season) {

        if (league != null && season != null) {
            return matchService.findByLeagueAndSeason(league, season);
        }
        return matchService.findAll();
    }
}
