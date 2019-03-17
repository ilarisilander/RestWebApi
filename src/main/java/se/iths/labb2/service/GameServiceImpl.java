package se.iths.labb2.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import se.iths.labb2.model.Game;



@Service("gameService")
public class GameServiceImpl implements GameService{

    private static final AtomicLong counter = new AtomicLong();

    private static List<Game> games;

    static{
        games= populateDummyGames();
    }

    public List<Game> findAllGames() {
        return games;
    }

    public Game findById(long id) {
        for(Game game : games){
            if(game.getId() == id){
                return game;
            }
        }
        return null;
    }

    public Game findByName(String name) {
        for(Game game : games){
            if(game.getName().equalsIgnoreCase(name)){
                return game;
            }
        }
        return null;
    }

    public void saveGame(Game game) {
        game.setId(counter.incrementAndGet());
        games.add(game);
    }

    public void updateGame(Game game) {
        int index = games.indexOf(game);
        games.set(index, game);
    }

    public void deleteGameById(long id) {

        for (Iterator<Game> iterator = games.iterator(); iterator.hasNext(); ) {
            Game game = iterator.next();
            if (game.getId() == id) {
                iterator.remove();
            }
        }
    }

    public boolean isGameExist(Game game) {
        return findByName(game.getName())!=null;
    }

    public void deleteAllGames(){
        games.clear();
    }

    private static List<Game> populateDummyGames(){
        List<Game> games = new ArrayList<Game>();
        games.add(new Game(counter.incrementAndGet(),"Super Mario Bros.",1985, "NES"));
        games.add(new Game(counter.incrementAndGet(),"Punch-Out",1984, "NES"));
        games.add(new Game(counter.incrementAndGet(),"The Legend of Zelda: A Link to the past",1991, "SNES"));
        games.add(new Game(counter.incrementAndGet(),"Golden Axe",1989, "Sega Genesis"));
        return games;
    }

}
