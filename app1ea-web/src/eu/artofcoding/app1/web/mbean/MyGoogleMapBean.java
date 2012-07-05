package eu.artofcoding.app1.web.mbean;

import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.*;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

/**
 *
 */
@ManagedBean
public class MyGoogleMapBean {

    private MapModel simpleModel;

    public MyGoogleMapBean() {
        simpleModel = new DefaultMapModel();
        // Shared coordinates
        LatLng coord1 = new LatLng(52.059158, 7.352737);
        LatLng coord2 = new LatLng(36.883707, 30.689216);
        /*
        LatLng coord3 = new LatLng(36.879703, 30.706707);
        LatLng coord4 = new LatLng(36.885233, 30.702323);
        */
        // Basic marker
        simpleModel.addOverlay(new Marker(coord1, "Ralf Bensmann"));
        simpleModel.addOverlay(new Marker(coord2, "Ataturk Parki"));
        /*
        simpleModel.addOverlay(new Marker(coord3, "Karaalioglu Parki"));
        simpleModel.addOverlay(new Marker(coord4, "Kaleici"));
        */
        //Polyline
        Polyline polyline = new Polyline();
        polyline.getPaths().add(coord1);
        polyline.getPaths().add(coord2);
        /*
        polyline.getPaths().add(coord3);
        polyline.getPaths().add(coord4);
        */
        polyline.setStrokeWeight(10);
        polyline.setStrokeColor("#FF9900");
        polyline.setStrokeOpacity(0.7);
        simpleModel.addOverlay(polyline);
    }

    public MapModel getSimpleModel() {
        return simpleModel;
    }

    public void onPolylineSelect(OverlaySelectEvent event) {
        addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO, "Möppi is ab nach'e Tükkai", null));
    }

    public void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

}
