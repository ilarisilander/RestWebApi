package se.iths.labb2.model;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.List;

import se.iths.labb2.model.Game;
import org.springframework.web.client.RestTemplate;

public class RestGameClient {

    public static final String REST_SERVICE_URI = "http://localhost:8080/api/";

    //GET
    @SuppressWarnings("unchecked")
    private static void listAllGames(){
        System.out.println("Testing listAllGames API-----------");

        RestTemplate restTemplate = new RestTemplate();
        List<LinkedHashMap<String, Object>> gamesMap = restTemplate.getForObject(REST_SERVICE_URI+"/game/", List.class);

        if(gamesMap!=null){
            for(LinkedHashMap<String, Object> map : gamesMap){
                System.out.println("Game : id="+map.get("id")+", Name="+map.get("name")+", Released="+map.get("released")+", Console="+map.get("console"));;
            }
        }else{
            System.out.println("No game exist----------");
        }
    }

    /* GET */
    private static void getGame(){
        System.out.println("Testing getUser API----------");
        RestTemplate restTemplate = new RestTemplate();
        Game game = restTemplate.getForObject(REST_SERVICE_URI+"/game/1", Game.class);
        System.out.println(game);
    }

    /* POST */
    private static void createGame() {
        System.out.println("Testing create Game API----------");
        RestTemplate restTemplate = new RestTemplate();
        Game game = new Game(0,"Metroid",1986,"NES");
        URI uri = restTemplate.postForLocation(REST_SERVICE_URI+"/game/", game, Game.class);
        System.out.println("Location : "+uri.toASCIIString());
    }

    /* PUT */
    private static void updateGame() {
        System.out.println("Testing update Game API----------");
        RestTemplate restTemplate = new RestTemplate();
        Game game  = new Game(1,"CS:GO",2012, "PC");
        restTemplate.put(REST_SERVICE_URI+"/game/1", game);
        System.out.println(game);
    }

    /* DELETE */
    private static void deleteGame() {
        System.out.println("Testing delete Game API----------");
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(REST_SERVICE_URI+"/game/3");
    }


    /* DELETE */
    private static void deleteAllGames() {
        System.out.println("Testing all delete Games API----------");
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(REST_SERVICE_URI+"/game/");
    }

    public static void main(String args[]){
        listAllGames();
        getGame();
        createGame();
        listAllGames();
        updateGame();
        listAllGames();
        deleteGame();
        listAllGames();
        deleteAllGames();
        listAllGames();
    }
}
