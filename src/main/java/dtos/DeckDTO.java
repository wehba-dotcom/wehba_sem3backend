package dtos;

public class DeckDTO {
    Boolean success;
    String remaining;

    public DeckDTO(Boolean success, String remaining) {
        this.success = success;
        this.remaining = remaining;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getRemaining() {
        return remaining;
    }

    public void setRemaining(String remaining) {
        this.remaining = remaining;
    }
}
