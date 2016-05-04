import org.apache.commons.beanutils.BeanUtils;
import spark.ModelAndView;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;


import static spark.Spark.halt;

/**
 * This is the Main class that configures our Spark application
 */
public class Main {

    public static void main(String[] args) {

        // this is a filter that is executed in front of all requests.
        Spark.before(
                "*",
                (request, response) -> {
                    // is the user NOT on the login form?
                    // and, is the user NOT logged in?
                    if(!request.uri().equals("/login") && !request.session().attributes().contains("user")){
                        // if so, then we need to redirect to the login form!
                        response.redirect("/login");
                        halt();
                    }
                }
        );

        // this is our homepage
        Spark.get(
                "/",
                (request, response) -> {
                    // create a map to hold our model
                    HashMap<String, Object> m = new HashMap<>();

                    // Get the user from their session
                    User user = request.session().attribute("user");
                    // put the user into our model
                    m.put("user", user);

                    // display the home template with our model
                    return new ModelAndView(m, "home.mustache");
                },
                new MustacheTemplateEngine()
        );

        // this shows the login form
        Spark.get(
                "/login",
                (request, response) -> {
                    // just show the login form
                    return new ModelAndView(null, "login.mustache");
                },
                new MustacheTemplateEngine()
        );

        // this handles when the login form is submitted
        Spark.post(
                "/login",
                (request, response) -> {

                    // create a user bean and populate it using the submitted data
                    User user = new User();
                    BeanUtils.populate(user, request.queryMap().toMap());

                    // validate our User
                    HashMap<String, String> errors = user.validate();

                    // were there validation errors?
                    if(errors.size() == 0) {
                        // add our user into the session
                        request.session().attribute("user", user);

                        // redirect to the homepage
                        response.redirect("/");
                        halt();
                    } else {
                        // the login failed
                        HashMap<String, Object> m = new HashMap<>();
                        m.put("errors", errors);
                        return new ModelAndView(m, "login.mustache");
                    }

                    // we must always return something
                    return null;
                },
                new MustacheTemplateEngine()
        );

        // this logs the user out
        Spark.get(
                "/logout",
                (request, response) -> {
                    // find the current user's session and invalidate it.
                    request.session().invalidate();
                    // redirect to the login page
                    response.redirect("/login");
                    halt();

                    // we must always return something
                    return null;
                }
        );

        // this shows the create game form
        Spark.get(
                "/create-game",
                (request, response) -> {
                    // show game form
                    return new ModelAndView(null, "gameForm.mustache");
                },
                new MustacheTemplateEngine()
        );

        // handle submission of the create game form
        Spark.post(
                "/create-game",
                (request, response) -> {

                    // create a game bean and populate it using the submitted data
                    Game game = new Game();
                    BeanUtils.populate(game, request.queryMap().toMap());

                    // validate our game
                    HashMap<String, String> errors = game.validate();

                    // were there validation errors?
                    if(errors.size() == 0) {
                        // nope, get our user from the session
                        User user = request.session().attribute("user");
                        // add the game to our user
                        user.games.add(game);
                        // redirect to the home page
                        response.redirect("/");
                        halt();
                    } else {
                        // there were errors. we'll add them to our model and show the create game form
                        HashMap<String, Object> m = new HashMap<>();
                        m.put("game", game);
                        m.put("errors", errors);
                        // finally, show the game form again
                        return new ModelAndView(m, "gameForm.mustache");
                    }

                    return null;
                },
                new MustacheTemplateEngine()
        );

    }

}
