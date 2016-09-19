import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

public class Task {
  private String description;
  private boolean completed;
  private LocalDateTime createdAt;
  private int id;
  private int categoryId;

//no arraylist(database)//
//no 'm' variables, names must match DB//

  public Task(String description) {
    this.description = description; //takes value from argument//
    completed = false;
    createdAt = LocalDateTime.now();
    this.categoryId = categoryId;
  }

  public String getDescription() {
    return description;
  }

  public boolean isCompleted() {
    return completed;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public int getCategoryId() {
    return categoryId;
  }

  public int getId() {
    return id;
  }


  public static List<Task> all() {
    String sql = "SELECT id, description FROM tasks";//requesting all id and description//
    try(Connection con = DB.sql2o.open()) {//establish a connection //
     return con.createQuery(sql).executeAndFetch(Task.class);//create a query //
   }//executes the SQL command and instructs Java to transform the information we receive
   //into Task objects. This will create a List<Task> object, which we return.//
  }

  @Override//to compare DB objects with code objects//
  public boolean equals(Object otherTask){
    if (!(otherTask instanceof Task)) {
      return false;
    } else {
      Task newTask = (Task) otherTask;
      return this.getDescription().equals(newTask.getDescription()) &&
             this.getId() == newTask.getId() &&
             this.getCategoryId() == newTask.getCategoryId();
    }
  }

  public void save() {
   try(Connection con = DB.sql2o.open()) {
     String sql = "INSERT INTO tasks(description) VALUES (:description)";
     this.id = (int) con.createQuery(sql, true)//true instructs Sql2o to add the id from the database//
     //saved as a key, to the Query object //
       .addParameter("description", this.description
//replace the :description placeholder with this.description
//using .addParameter("description", this.description).
       .addParameter("categoryId", this.categoryId)
       .executeUpdate()
       .getKey()//returns an Object with a numerical value. typecast into an int above here//
   }
 }

  // public static void clear() {
  // }
  //

  public int getId() {
    return id;
  }

  public static Task find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM tasks where id=:id";
      Task task = con.createQuery(sql)
        .addParameter("id", id)//pass in the id argument//
        .executeAndFetchFirst(Task.class);
//return the first item in the collection returned by our database, cast as a Task object//
      return task;
    }
  }

}
