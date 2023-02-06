/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

/**
 *
 * @author PHOENIX DINESH
 */
public class Subject {

    private String id;
    private String name;
    private int lectures;

    public Subject(String id, String name, int lectures) {
        this.id = id;
        this.name = name;
        this.lectures = lectures;
    }

   

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLectures() {
        return lectures;
    }

    public void setLectures(int lectures) {
        this.lectures = lectures;
    }
    
    
}
