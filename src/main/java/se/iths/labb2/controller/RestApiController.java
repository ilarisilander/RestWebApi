package se.iths.labb2.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import se.iths.labb2.model.Game;
import se.iths.labb2.service.GameService;
import se.iths.labb2.util.CustomErrorType;

import java.util.List;

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

    @RequestMapping(value = "/game/{id}", method = RequestMethod.GET)
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

    //PUT (Update) A Game

    @RequestMapping(value = "/game/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateGame(@PathVariable("id") long id, @RequestBody Game game) {
        logger.info("Updating Game with id {}", id);

        Game currentGame = gameService.findById(id);

        if (currentGame == null) {
            logger.error("Unable to update. Game with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to update. Game with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }

        currentGame.setName(game.getName());
        currentGame.setConsole(game.getConsole());
        currentGame.setReleased(game.getReleased());

        gameService.updateGame(currentGame);
        return new ResponseEntity<Game>(currentGame, HttpStatus.OK);
    }

    //DELETE A Game

    @RequestMapping(value = "/game/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteGame(@PathVariable("id") long id) {
        logger.info("Fetching & Deleting Game with id {}", id);

        Game game = gameService.findById(id);
        if (game == null) {
            logger.error("Unable to delete. Game with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to delete. Game with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        gameService.deleteGameById(id);
        return new ResponseEntity<Game>(HttpStatus.NO_CONTENT);
    }

    //DELETE All Games

    @RequestMapping(value = "/game/", method = RequestMethod.DELETE)
    public ResponseEntity<Game> deleteAllGames() {
        logger.info("Deleting All Games");

        gameService.deleteAllGames();
        return new ResponseEntity<Game>(HttpStatus.NO_CONTENT);
    }

}
