package in.nitish.blog.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="category")
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Category {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer categoryId;
	
	@Column(name="title",length=100,nullable=false)
	@JsonProperty("title")
	private String categoryTitle;
	
	@Column(name="description",length=100,nullable=false)
	@JsonProperty("description")
	private String categoryDescription;
	
	@OneToMany(mappedBy="category",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private List<Post> posts=new ArrayList<>();
	

}
