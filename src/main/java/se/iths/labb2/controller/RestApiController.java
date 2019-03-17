package se.iths.labb2.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api")
public class RestApiController {

    public static final Logger logger = LoggerFactory.getLogger(RestApiController.class);

    @Autowired
    GameService gameService;

    //GET All Games

    @RequestMapping(value = "/game/", method = RequestMethod.GET)
    public ResponseEntity<List<Game>> listAllGames() {
        List<Game> games = gameService.findAllGames();
        if(games.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Game>>(games, HttpStatus.OK);
    }

    //GET A Single Game

    @RequestMapping(value = "/game/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> getGame(@PathVariable("id") long id) {
        logger.info("Get Game with ID {}", id);
        Game game = gameService.findById(id);
        if(game == null) {
            logger.error("Game with ID {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Game with ID " + id
                + " not found"), HttpStatus.OK);
        }
        return new ResponseEntity<Game>(game, HttpStatus.OK);
    }

    //POST A Game

    @RequestMapping(value = "/game/", method = RequestMethod.POST)
    public ResponseEntity<?> createGame(@RequestBody Game game, UriComponentsBuilder ucBuilder) {
        logger.info("Creating Game : {}", game);

        if(gameService.isGameExist(game)) {
            logger.error("Unable to create. A Game with name {} already exists", game.getName());
            return new ResponseEntity(new CustomErrorType("Unable to create. A Game with name " +
                    game.getName() + " already exist."), HttpStatus.CONFLICT);
        }
        gameService.saveGame(game);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/game/{id}").buildAndExpand(game.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

}
