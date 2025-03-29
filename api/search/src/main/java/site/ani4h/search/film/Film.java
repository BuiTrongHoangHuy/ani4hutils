package site.ani4h.search.film;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import site.ani4h.shared.common.Uid;

@Document(indexName = "films")
@Getter
@Setter
public class Film {
    @Id
    private String id;

    @Field(type = FieldType.Text)
    private String title;

    @Field(type = FieldType.Search_As_You_Type)
    private String keyword;

    @Transient
    private final static int type = 7;

    public void setId(int id) { this.id = new Uid(id, 0, type).toString(); }
}
