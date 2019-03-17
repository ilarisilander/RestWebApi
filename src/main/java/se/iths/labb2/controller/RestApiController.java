package se.iths.labb2.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

}
