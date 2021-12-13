package dtos;

public class NewDeckWithCardDTO {
    String image;
    String value;
    String suit;
    String code;
    String deck_id;
    String remaining;

    public NewDeckWithCardDTO() {
    }

    public NewDeckWithCardDTO(boolean success, String image, String value, String suit, String code, String deck_id, String remaining) {
        this.image = image;
        this.value = value;
        this.suit = suit;
        this.code = code;
        this.deck_id = deck_id;
        this.remaining = remaining;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSuit() {
        return suit;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDeck_id() {
        return deck_id;
    }

    public void setDeck_id(String deck_id) {
        this.deck_id = deck_id;
    }

    public String getRemaining() {
        return remaining;
    }

    public void setRemaining(String remaining) {
        this.remaining = remaining;
    }
}
