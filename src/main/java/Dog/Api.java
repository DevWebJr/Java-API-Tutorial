package Dog;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
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
        final Router router = Router.router(vertx);


        // Définir des routes et sous-routes
        // Instancier un objet subRouter depuis DogResource
        final DogResource dogResource = new DogResource();
        final Router dogSubRouter = dogResource.getSubRouter(vertx);
        // Déterminer que chaque url commence par "/api/v1/dogs"
        router.mountSubRouter("/api/v1/dogs", dogSubRouter);

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
        // Envoyer la réponse
        routingContext.response()
                .setStatusCode(201)
                .putHeader("content-type", "application/json")
                .end(Json.encode(createdDog));
    }

    // Mettre à jour un objet
    public void updateOneDog(RoutingContext routingContext) {
        LOGGER.info("Dans la fonction updateOneDog");
        // Récupérer le paramètre id de la requête pour identifier l'objet
        final String id = routingContext.request().getParam("id");
        // Récupérer les paramètres de l'objet depuis le body
        final JsonObject body = routingContext.getBodyAsJson();
        final String name = body.getString("name");
        final String race = body.getString("race");
        final Integer age = body.getInteger("age");
        // Mettre à jour un objet
        final Dog dog = new Dog(id, name, race, age);
        final Dog updatedDog = dogService.update(dog);
        // Envoyer la réponse
        routingContext.response()
                .setStatusCode(200)
                .putHeader("content-type", "application/json")
                .end(Json.encode(updatedDog));
    }

    // Supprimer un objet
    public void deleteOneDog(RoutingContext routingContext) {
        LOGGER.info("Dans la fonction deleteOneDog...");
        // Récupérer le paramètre id de la requête pour identifier l'objet
        final String id = routingContext.request().getParam("id");

        // Supprimer l'objet selectionné
        dogService.remove(id);

        // Envoyer la réponse
        routingContext.response()
                .setStatusCode(200)
                .putHeader("content-type", "application/json")
                .end();
    }

}
