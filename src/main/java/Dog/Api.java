package Dog;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

import java.util.List;

public class Api extends AbstractVerticle{
    private static final Logger LOGGER = LoggerFactory.getLogger(Api.class);

    private final DogService dogService = new DogService();

    // Démarrer l'API
    @Override
    public void start() throws Exception {
        LOGGER.info("Dans la fonction start...");
        // Initialiser le routeur
        Router router = Router.router(vertx);
        // Implémenter l'instance Body handler
        router.route("/api/v1/dogs*").handler(BodyHandler.create());
        // Déterminer les différentes routes
        // La route pour récupérer tous les objets
        router.get("/api/v1/dogs")
                .handler(this::getAllDogs);
        // La route pour récupérer un objet selon l'id
        router.get("/api/v1/dogs/:id")
                .handler(this::getOneDog);
        // La route pour ajouter un objet
        router.post("/api/v1/dogs")
                .handler(this::createOneDog);

        // Lancer le serveur
        vertx.createHttpServer()
                .requestHandler(router)
                .listen(8080);
    }

    // Stopper l'API
    @Override
    public void stop() throws Exception {
        LOGGER.info("Dans la fonction stop...");
    }

    // Récupérer l'ensemble des objets
    private void getAllDogs(RoutingContext routingContext) {
        LOGGER.info("Dans la fonction getAllDogs...");
        final List<Dog> dogs = dogService.findAll();

        final JsonObject jsonResponse = new JsonObject();
        jsonResponse.put("dogs", dogs);
        jsonResponse.put("my-name", "Tito");
        // Envoyer la réponse
        routingContext.response()
                .setStatusCode(200)
                .putHeader("content-type", "application/json")
                .end(Json.encodePrettily(jsonResponse));
    }

    // Récupérer un objet selon l'id
    private void getOneDog(RoutingContext routingContext) {
        LOGGER.info("Dans la fonction getOneDog");
        final String id = routingContext.request().getParam("id");
        // Rechercher l'objet correspondant à l'id
        final Dog dog = dogService.findById(id);
        // Vérifier que l'id de l'objet éxiste
        if (dog == null) {
            final JsonObject errorJsonResponse = new JsonObject();
            errorJsonResponse.put("error", "Aucun objet avec l'id " + id);
            errorJsonResponse.put("id", id);
            // Envoyer une réponse d'erreur 404
            routingContext.response()
                    .setStatusCode(404)
                    .putHeader("content-type", "application/json")
                    .end(Json.encode(errorJsonResponse));
            return;
        }
        else{
            // Envoyer la réponse
            routingContext.response()
                    .setStatusCode(200)
                    .putHeader("content-type", "application/json")
                    .end(Json.encode(dog));
        }
    }

    // Ajouter un objet
    private void createOneDog(RoutingContext routingContext) {
        LOGGER.info("Dans la fonction createOneDog");
        final JsonObject body = routingContext.getBodyAsJson();
        final String name = body.getString("name");
        final String race = body.getString("race");
        final Integer age = body.getInteger("age");
        // Créer un objet
        final Dog dog = new Dog(null, name, race, age);
        final Dog createdDog = dogService.add(dog);
        // Envyer la réponse
        routingContext.response()
                .setStatusCode(201)
                .putHeader("content-type", "application/json")
                .end(Json.encode(createdDog));
    }
}
