
package sront.crawler.schema.input;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "category",
    "articleSelector",
    "titleSelector",
    "bodySelector"
})
public class Category {

    @JsonProperty("category")
    private String category;
    @JsonProperty("articleSelector")
    private List<String> articleSelector = new ArrayList<String>();
    @JsonProperty("titleSelector")
    private String titleSelector;
    @JsonProperty("bodySelector")
    private String bodySelector;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("category")
    public String getCategory() {
        return category;
    }

    @JsonProperty("category")
    public void setCategory(String category) {
        this.category = category;
    }

    @JsonProperty("articleSelector")
    public List<String> getArticleSelector() {
        return articleSelector;
    }

    @JsonProperty("articleSelector")
    public void setArticleSelector(List<String> articleSelector) {
        this.articleSelector = articleSelector;
    }

    @JsonProperty("titleSelector")
    public String getTitleSelector() {
        return titleSelector;
    }

    @JsonProperty("titleSelector")
    public void setTitleSelector(String titleSelector) {
        this.titleSelector = titleSelector;
    }

    @JsonProperty("bodySelector")
    public String getBodySelector() {
        return bodySelector;
    }

    @JsonProperty("bodySelector")
    public void setBodySelector(String bodySelector) {
        this.bodySelector = bodySelector;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Category.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("category");
        sb.append('=');
        sb.append(((this.category == null)?"<null>":this.category));
        sb.append(',');
        sb.append("articleSelector");
        sb.append('=');
        sb.append(((this.articleSelector == null)?"<null>":this.articleSelector));
        sb.append(',');
        sb.append("titleSelector");
        sb.append('=');
        sb.append(((this.titleSelector == null)?"<null>":this.titleSelector));
        sb.append(',');
        sb.append("bodySelector");
        sb.append('=');
        sb.append(((this.bodySelector == null)?"<null>":this.bodySelector));
        sb.append(',');
        sb.append("additionalProperties");
        sb.append('=');
        sb.append(((this.additionalProperties == null)?"<null>":this.additionalProperties));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.articleSelector == null)? 0 :this.articleSelector.hashCode()));
        result = ((result* 31)+((this.bodySelector == null)? 0 :this.bodySelector.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.category == null)? 0 :this.category.hashCode()));
        result = ((result* 31)+((this.titleSelector == null)? 0 :this.titleSelector.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Category) == false) {
            return false;
        }
        Category rhs = ((Category) other);
        return ((((((this.articleSelector == rhs.articleSelector)||((this.articleSelector!= null)&&this.articleSelector.equals(rhs.articleSelector)))&&((this.bodySelector == rhs.bodySelector)||((this.bodySelector!= null)&&this.bodySelector.equals(rhs.bodySelector))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.category == rhs.category)||((this.category!= null)&&this.category.equals(rhs.category))))&&((this.titleSelector == rhs.titleSelector)||((this.titleSelector!= null)&&this.titleSelector.equals(rhs.titleSelector))));
    }

}
