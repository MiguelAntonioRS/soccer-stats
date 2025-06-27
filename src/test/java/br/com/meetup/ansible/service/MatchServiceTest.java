package br.com.meetup.ansible.service;

import static org.hamcrest.Matchers.emptyCollectionOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.meetup.ansible.SoccerStatsApplication;
import br.com.meetup.ansible.domain.Match;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE, classes = SoccerStatsApplication.class)
public class MatchServiceTest {

    @Autowired
    private MatchService matchService;

    @Test
    public void testListMatchesFromHomeTeam_emptyList() {
        assertThat(matchService.listMatchesFromHomeTeam(""), is(emptyCollectionOf(Match.class)));
    }

    @Test
    public void testListMatchesFromHomeTeam() {
        assertThat(matchService.listMatchesFromHomeTeam("juventus"), not(emptyCollectionOf(Match.class)));
    }

    @Test
    public void testListExactMatches_oneTeam() {
        assertThat(matchService.listExactMatches("juventus", ""), is(emptyCollectionOf(Match.class)));
    }
    
    @Test
    public void testListExactMatches() {
        assertThat(matchService.listExactMatches("juventus", "lazio"), not(emptyCollectionOf(Match.class)));
    }

    @Mock
    private MatchRepository matchRepository;

    @InjectMocks
    private MatchService matchService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnAllMatches() {
        Match match = new Match();
        Team juventus = new Team();
        juventus.setName("juventus");
        Team milan = new Team();
        milan.setName("milan");

        match.setHomeTeam(juventus);
        match.setAwayTeam(milan);
        match.setHomeGoals(2);
        match.setAwayGoals(1);

        List<Match> matches = Arrays.asList(match);

        when(matchRepository.findAll()).thenReturn(matches);

        List<MatchDTO> result = matchService.findAll();

        assertEquals(1, result.size());
    }

}
