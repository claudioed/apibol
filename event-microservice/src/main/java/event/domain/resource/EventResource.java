package event.domain.resource;

import event.domain.Event;
import event.domain.Game;
import event.domain.resource.model.BattleResultDTO;
import event.domain.resource.model.EventDTO;
import event.domain.resource.model.NewBattle;
import event.domain.resource.model.NewGame;
import event.domain.service.EventService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * Events Resources
 * @author Claudio E. de Oliveira on 28/02/16.
 */
@RestController
@RequestMapping("/")
@Api(value = "/event", description = "Operations about events")
public class EventResource {

    private final EventService eventService;

    @Autowired
    public EventResource(EventService eventService) {
        this.eventService = eventService;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "Create Event", nickname = "Create Event")
    public ResponseEntity<Event> create(@RequestBody EventDTO eventDTO) {
        Event savedEvent = this.eventService.create(eventDTO);
        return new ResponseEntity<>(savedEvent, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "List all events", nickname = "List Events")
    public ResponseEntity<List<Event>> all() {
        return new ResponseEntity<>(this.eventService.all(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "Find Event By ID", nickname = "Find Event by ID")
    public ResponseEntity<Event> findOne(@PathVariable("id") String id) {
        return new ResponseEntity<>(this.eventService.findOne(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/game", method = RequestMethod.POST)
    @ApiOperation(value = "Add game in Event", nickname = "Add Game")
    public ResponseEntity<Event> addBattleGame(@PathVariable("id") String id, @RequestBody NewBattle newBattle) {
        return new ResponseEntity<>(this.eventService.addNewGame(id, newBattle), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/game", method = RequestMethod.GET)
    @ApiOperation(value = "Get Event Games", nickname = "Get Games")
    public ResponseEntity<Set<Game>> games(@PathVariable("id") String id) {
        return new ResponseEntity<>(this.eventService.findOne(id).getGames(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/game/{gameId}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Remove game from Event", nickname = "Remove Game")
    public void removeBattleGame(@PathVariable("id") String id,@PathVariable("gameId") String gameId ) {
        this.eventService.removeGame(id,gameId);
    }

    @RequestMapping(value = "/{id}/game/{gameId}", method = RequestMethod.GET)
    @ApiOperation(value = "Get game in Event", nickname = "Get Game")
    public ResponseEntity<Game> findGameById(@PathVariable("id") String eventId,@PathVariable("gameId") String gameId) {
        return new ResponseEntity<>(this.eventService.findGameById(eventId,gameId), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/game/{gameId}", method = RequestMethod.PUT)
    @ApiOperation(value = "Update game result", nickname = "Update Game")
    public ResponseEntity<Game> updateGameResult(@PathVariable("id") String eventId,@PathVariable("gameId") String gameId,@RequestBody BattleResultDTO resultDTO) {
        return new ResponseEntity<>(this.eventService.addGameResult(eventId,gameId,resultDTO), HttpStatus.OK);
    }

}
