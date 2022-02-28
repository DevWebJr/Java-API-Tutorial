package Dog;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

public class DogResource {
    public Router getSubRouter(final Vertx vertx) {
        // Créer un sous-routeur
        final Router subRouter = Router.router(vertx);

        // Implémenter l'instance Body handler
        subRouter.route("/*").handler(BodyHandler.create());

        // Déterminer les différentes routes
        // La route pour récupérer tous les objets
        subRouter.get("/").handler(this::getAllDogs);
        // La route pour récupérer un objet selon l'id
        subRouter.get("/:id").handler(this::getOneDog);
        // La route pour ajouter un objet
        subRouter.post("/").handler(this::createOneDog);
        // La route pour mettre à jour un objet
        subRouter.put("/:id").handler(this::updateOneDog);
        // La route pour supprimer un objet
        subRouter.delete("/:id").handler(this::deleteOneDog);
        return subRouter;
    }

    private void getAllDogs(final RoutingContext routingContext) {
    }
    private void getOneDog(final RoutingContext routingContext) {
    }
    private void createOneDog(final RoutingContext routingContext) {
    }
    private void updateOneDog(final RoutingContext routingContext) {
    }
    private void deleteOneDog(final RoutingContext routingContext) {
    }


}
