

public class Article {
    private String Name, Header, Date, Author, Category, Source;

    public void setName(String Name){
        this.Name = Name;
    }

    public void setHeader(String Header){
        this.Header = Header;
    }

    public void setDate(String Date){
        this.Date = Date;
    }

    public void setAuthor(String Author){
        this.Author = Author;
    }

    public void setCategory(String Category){
        this.Category = Category;
    }

    public void setSource(String Source){
        this.Source = Source;
    }

    public String getName() {return Name;}
    public String getHeader() {return  Header;}
    public String getDate() {return Date;}
    public String getAuthor() {return Author;}
    public String getCategory() {return Category;}
    public String getSource() {return Source;}

    public String toString() {
        return "Article{" +
                "Name='" + Name + '\'' +
                ", Header='" + Header + '\'' +
                ", Date='" + Date + '\'' +
                ", Author='" + Author + '\'' +
                ", Category='" + Category + '\'' +
                ", Source='" + Source + '\'' +
                '}';
    }
}
