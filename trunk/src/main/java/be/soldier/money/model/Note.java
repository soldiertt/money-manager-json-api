package be.soldier.money.model;

/**
 * Created by soldiertt on 15-02-15.
 */
public class Note {

    private Integer id;

    private String content;

    public Note(Integer id, String content) {
        this.id = id;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
