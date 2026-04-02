package az.edu.ada.wm2.lab6.model.dto;

import java.util.UUID;

public class CategoryResponseDto {

    private UUID id;
    private String name;

    public CategoryResponseDto() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
