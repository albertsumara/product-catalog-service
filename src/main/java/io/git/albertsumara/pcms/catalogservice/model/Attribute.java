package io.git.albertsumara.pcms.catalogservice.model;

public class Attribute {

    private Long id;
    private String name;
    private String value;

    public Attribute(String _name, String _value) {
        this.name = _name;
        this.value = _value;
    }

    public String getName() {
        return name;
    }
    public String getValue() {
        return value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return this.name + ": " + this.value;
    }
}
