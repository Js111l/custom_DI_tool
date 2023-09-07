package org.custom.appcontext.testobj.web.allcontrollers;

import java.util.Map;
import org.custom.appcontext.testobj.web.User;
import org.custom.web.annotations.Delete;
import org.custom.web.annotations.Get;
import org.custom.web.annotations.Patch;
import org.custom.web.annotations.PathVariable;
import org.custom.web.annotations.Post;
import org.custom.web.annotations.Put;
import org.custom.web.annotations.RequestBody;
import org.custom.web.annotations.RestController;

@RestController
public class UserController {

  @Get(url = "/users/{id}")
  public int getUserById(@PathVariable int id) {
    return id;
  }

  @Post(url = "/users")
  public String createUser(@RequestBody User newUser) {
    return "User created: " + newUser.name();
  }

  @Put(url = "/users/{id}")
  public String updateUser(@PathVariable int id, @RequestBody User updatedUser) {
    return "User updated: " + updatedUser.name();
  }

  @Delete(url = "/users/{id}")
  public String deleteUser(@PathVariable int id) {
    return "User deleted: " + id;
  }

  @Patch(url = "/users/{id}")
  public String partialUpdateUser(@PathVariable int id, @RequestBody Map<String, Object> updates) {
    return updates.values().toString();
  }
}
