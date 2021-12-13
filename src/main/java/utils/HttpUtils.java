package utils;

import com.google.gson.*;
import dtos.CardDTO;
import dtos.DeckDTO;
import dtos.NewDeckWithCardDTO;
import facades.DeckFacade;
import facades.FacadeExample;
//import dtos.ChuckDTO;
//import dtos.CombinedDTO;
//import dtos.DadDTO;

import javax.json.Json;
import javax.persistence.EntityManagerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.*;

public class HttpUtils {
    private static Gson gson = new Gson();
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final DeckFacade FACADE = DeckFacade.getDeckFacade(EMF);

//    public static CombinedDTO fetchDataSequential() throws IOException {
//        String chuck = HttpUtils.fetchData("https://api.chucknorris.io/jokes/random");
//        String dad = HttpUtils.fetchData("https://icanhazdadjoke.com");
//
//        ChuckDTO chuckDTO = gson.fromJson(chuck, ChuckDTO.class);
//        DadDTO dadDTO = gson.fromJson(dad, DadDTO.class);
//        return new CombinedDTO(chuckDTO,dadDTO);
//    }
//
//    public static CombinedDTO fetchDataParrallel() throws ExecutionException, InterruptedException, TimeoutException {
//        ExecutorService es = Executors.newCachedThreadPool();
//        Future<ChuckDTO> chuckDTOFuture = es.submit(
//                () -> gson.fromJson(HttpUtils.fetchData("https://api.chucknorris.io/jokes/random"),ChuckDTO.class)
//        );
//        Future<DadDTO> dadDTOFuture = es.submit(
//                () -> gson.fromJson(HttpUtils.fetchData("https://icanhazdadjoke.com"),DadDTO.class)
//        );
//
//        ChuckDTO chuckDTO = chuckDTOFuture.get();
//        DadDTO dadDTO = dadDTOFuture.get();
//
//        return new CombinedDTO(chuckDTO, dadDTO);
//    }


    public static String fetchData(String _url) throws MalformedURLException, IOException {
        URL url = new URL(_url);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        //con.setRequestProperty("Accept", "application/json;charset=UTF-8");
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("User-Agent", "server");

        Scanner scan = new Scanner(con.getInputStream());
        String jsonStr = null;
        if (scan.hasNext()) {
            jsonStr = scan.nextLine();
        }
        scan.close();
        return jsonStr;
    }

    public static JsonObject fetchJson(String _url) throws IOException {
        URL url = new URL(_url);
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.setRequestMethod("GET");
        request.setRequestProperty("Accept", "application/json");
        request.setRequestProperty("User-Agent", "server");
        request.connect();
        JsonParser jp = new JsonParser();
        JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
        return root.getAsJsonObject();
    }

    public static NewDeckWithCardDTO fetchNewDeck() throws IOException {
        JsonObject newDeck = fetchJson("http://deckofcardsapi.com/api/deck/new/draw/?count=1");
        HashMap[] cards = gson.fromJson(newDeck.get("cards"), HashMap[].class);
        NewDeckWithCardDTO dto = new NewDeckWithCardDTO();
        dto.setDeck_id(newDeck.get("deck_id").getAsString());
        dto.setCode((String) cards[0].get("code"));
        dto.setSuit((String) cards[0].get("suit"));
        dto.setValue((String) cards[0].get("value"));
        dto.setImage((String) cards[0].get("image"));
        dto.setRemaining(newDeck.get("remaining").getAsString());
        FACADE.persistDeck(dto.getDeck_id());
        return dto;
    }

    public static DeckDTO shuffleCurrentDeck(String id) throws IOException {
        JsonObject deck = fetchJson("http://deckofcardsapi.com/api/deck/" + id + "/shuffle/");
        return new DeckDTO(deck.get("success").getAsBoolean(), deck.get("remaining").getAsString());
    }

    public static CardDTO drawCard(String id) throws IOException {
        JsonObject newCard = fetchJson("http://deckofcardsapi.com/api/deck/" + id + "/draw/?count=1");
        HashMap[] cards = gson.fromJson(newCard.get("cards"), HashMap[].class);
        CardDTO dto = new CardDTO();
        dto.setDeck_id(newCard.get("deck_id").getAsString());
        dto.setCode((String) cards[0].get("code"));
        dto.setSuit((String) cards[0].get("suit"));
        dto.setValue((String) cards[0].get("value"));
        dto.setImage((String) cards[0].get("image"));
        dto.setRemaining(newCard.get("remaining").getAsString());
        return dto;
    }
}
