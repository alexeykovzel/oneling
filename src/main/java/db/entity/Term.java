package db.entity;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Term {
    private String value;
    private String sourceLink;
    private Set<Definition> definitions;
    private Set<String> translations;
    private Set<String> examples;
}