package se.iths.labb2.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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



}
