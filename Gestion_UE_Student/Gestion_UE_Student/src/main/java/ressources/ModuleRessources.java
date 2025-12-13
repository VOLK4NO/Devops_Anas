package ressources;

import entities.Module;
import metiers.ModuleBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("module")
public class ModuleRessources {

    public static ModuleBusiness moduleBusiness = new ModuleBusiness();

    // ----------------------------
    // GET : Liste des modules
    // ----------------------------
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("list")
    public Response getAllModules() {
        return Response
                .status(Response.Status.OK)
                .entity(moduleBusiness.getAllModules())
                .build();
    }

    // ----------------------------
    // GET : Module par matricule
    // ----------------------------
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{matricule}")
    public Response getModuleByMatricule(@PathParam("matricule") String matricule) {
        Module module = moduleBusiness.getModuleByMatricule(matricule);
        if (module != null) {
            return Response.status(Response.Status.OK).entity(module).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // ----------------------------
    // GET : Modules par type
    // Exemple : /module/type/TRANSVERSAL
    // ----------------------------
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("type/{type}")
    public Response getModulesByType(@PathParam("type") Module.TypeModule type) {
        List<Module> result = moduleBusiness.getModulesByType(type);
        return Response.status(Response.Status.OK).entity(result).build();
    }

    // ----------------------------
    // POST : Ajouter module SANS BODY
    // ----------------------------
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("add")
    public Response addModule(Module module) {

        if (moduleBusiness.addModule(module)) {
            return Response.status(Response.Status.CREATED).build();
        }

        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    // ----------------------------
    // POST : Ajouter module AVEC BODY
    // ----------------------------
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("addWithBody")
    public Response addModuleWithBody(Module module) {

        if (moduleBusiness.addModule(module)) {
            return Response
                    .status(Response.Status.CREATED)
                    .entity(moduleBusiness.getAllModules())
                    .build();
        }

        return Response.status(Response.Status.NOT_ACCEPTABLE).build();
    }

    // ----------------------------
    // DELETE : supprimer module
    // ----------------------------
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("delete/{matricule}")
    public Response deleteModule(@PathParam("matricule") String matricule) {

        if (moduleBusiness.deleteModule(matricule)) {
            return Response.status(Response.Status.OK).build();
        }

        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    // ----------------------------
    // PUT : update module
    // ----------------------------
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("update/{matricule}")
    public Response updateModule(@PathParam("matricule") String matricule, Module updated) {

        if (moduleBusiness.updateModule(matricule, updated)) {
            return Response
                    .status(Response.Status.OK)
                    .entity(moduleBusiness.getModuleByMatricule(matricule))
                    .build();
        }

        return Response.status(Response.Status.BAD_REQUEST).build();
    }
}
