package ressources;

import metiers.UniteEnseignementBusiness;
import entities.UniteEnseignement;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("UE")
public class UERessources {

    public static UniteEnseignementBusiness UEBusiness = new UniteEnseignementBusiness();

    // ----------------------------
    // GET : Liste des UE
    // ----------------------------
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("list")
    public Response GetUEList() {
        return Response
                .status(Response.Status.OK)
                .entity(UEBusiness.getListeUE())
                .build();
    }

    // ----------------------------
    // GET : Rechercher par code
    // ----------------------------
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{code}")
    public Response getUEByCode(@PathParam("code") int code) {
        return Response
                .status(Response.Status.OK)
                .entity(UEBusiness.getUEByCode(code))
                .build();
    }

    // ----------------------------
    // GET : Rechercher par semestre
    // ----------------------------
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/GetBySemestre")
    public Response GetBySemestre(@QueryParam("semestre") int semestre) {
        return Response
                .status(Response.Status.OK)
                .entity(UEBusiness.getUEBySemestre(semestre))
                .build();
    }

    // ----------------------------
    // POST : Ajouter une UE (sans body)
    // ----------------------------
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("add")
    public Response addUE(UniteEnseignement UE) {

        if (UEBusiness.addUniteEnseignement(UE)) {
            return Response
                    .status(Response.Status.CREATED)  // 201
                    .build();
        }

        return Response
                .status(Response.Status.BAD_REQUEST)  // 400
                .build();
    }

    // ----------------------------
    // POST : Ajouter une UE (avec body)
    // ----------------------------
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("addWithBody")
    public Response addUEWithBody(UniteEnseignement UE) {

        if (UEBusiness.addUniteEnseignement(UE)) {
            return Response
                    .status(Response.Status.CREATED)
                    .entity(UEBusiness.getListeUE()) // renvoie la liste complète
                    .build();
        }

        return Response
                .status(Response.Status.NOT_ACCEPTABLE) // 406
                .build();
    }
    // delete UE
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("delete/{code}")
    public Response deleteUE(@PathParam("code") int code) {

        if (UEBusiness.deleteUniteEnseignement(code)) {
            return Response
                    .status(Response.Status.OK)
                    .build();
        }

        return Response
                .status(Response.Status.BAD_REQUEST)
                .build();
    }
    // Update UE
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("update/{code}")
    public Response updateUE(@PathParam("code") int code, UniteEnseignement UE) {

        if (UEBusiness.updateUniteEnseignement(code, UE)) {
            return Response
                    .status(Response.Status.OK)
                    .entity(UEBusiness.getUEByCode(code))
                    .build();
        }

        return Response
                .status(Response.Status.BAD_REQUEST)
                .build();
    }


}
