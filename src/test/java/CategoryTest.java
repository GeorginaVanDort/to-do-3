import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.Arrays;

public class CategoryTest {

  @Before
  public void setUp() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/to_do_test", null, null);
  }

  @After
  public void tearDown() {
    try(Connection con = DB.sql2o.open()) {
      String deleteTasksQuery = "DELETE FROM tasks *;";//clears both classes//
      String deleteCategoriesQuery = "DELETE FROM categories *;";
      con.createQuery(deleteTasksQuery).executeUpdate();
      con.createQuery(deleteCategoriesQuery).executeUpdate();
    }
  }

  @Test
  public void category_instantiatesCorrectly_true() {
    Category testCategory = new Category("Home");
    assertEquals(true, testCategory instanceof Category);
  }

  @Test
  public void getName_categoryInstantiatesWithName_Home() {
    Category testCategory = new Category("Home");
    assertEquals("Home", testCategory.getName());
  }

 @Test
 public void all_returnsAllInstancesOfCategory_true() {
   Category firstCategory = new Category("Home");
   firstCategory.save();
   Category secondCategory = new Category("Work");
   secondCategory.save();
   assertEquals(true, Category.all().get(0).equals(firstCategory));
   assertEquals(true, Category.all().get(1).equals(secondCategory));
 }

 @Test
 public void getId_categoriesInstantiateWithAnId_1() {
   Category testCategory = new Category("Home");
   testCategory.save();
   assertTrue(testCategory.getId() > 0);
 }

 @Test
 public void find_returnsCategoryWithSameId_secondCategory() {
   Category firstCategory = new Category("Home");
   firstCategory.save();
   Category secondCategory = new Category("Work");
   secondCategory.save();
   assertEquals(Category.find(secondCategory.getId()), secondCategory);
 }

  @Test
  public void equals_returnsTrueIfNamesAretheSame() {
    Category firstCategory = new Category("Household chores");
    Category secondCategory = new Category("Household chores");
    assertTrue(firstCategory.equals(secondCategory));
  }

  @Test
  public void save_savesIntoDatabase_true() {
    Category myCategory = new Category("Household chores");
    myCategory.save();
    assertTrue(Category.all().get(0).equals(myCategory));
  }

  @Test
  public void save_assignsIdToObject() {
    Category myCategory = new Category("Household chores");
    myCategory.save();
    Category savedCategory = Category.all().get(0);
    assertEquals(myCategory.getId(), savedCategory.getId());
  }

  @Test
  public void getTasks_retrievesALlTasksFromDatabase_tasksList() {
    Category myCategory = new Category("Household chores");
    myCategory.save();
    Task firstTask = new Task("Mow the lawn", myCategory.getId());
    firstTask.save();
    Task secondTask = new Task("Do the dishes", myCategory.getId());
    secondTask.save();
    Task[] tasks = new Task[] { firstTask, secondTask };
    assertTrue(myCategory.getTasks().containsAll(Arrays.asList(tasks)));
  }
//containsAll() will use our equals() method from the Task class to compare items in two lists.
//first construct a task array containing those objects.
//use a new method Arrays.asList(tasks) to save those items into a list.
//!import java.util.Arrays!//
}
