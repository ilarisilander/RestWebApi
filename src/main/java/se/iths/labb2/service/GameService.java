package se.iths.labb2.service;

import java.util.List;

import se.iths.labb2.model.Game;

public interface GameService {

    Game findById(long id);

    Game findByName(String name);

    void saveGame(Game game);

    void updateGame(Game game);

    void deleteGameById(long id);

    List<Game> findAllGames();

    void deleteAllGames();

    boolean isGameExist(Game game);

}
