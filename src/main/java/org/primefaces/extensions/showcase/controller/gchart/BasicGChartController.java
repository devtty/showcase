package org.primefaces.extensions.showcase.controller.gchart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.primefaces.event.SelectEvent;
import org.primefaces.extensions.component.gchart.model.GChartModel;
import org.primefaces.extensions.component.gchart.model.GChartModelBuilder;
import org.primefaces.extensions.component.gchart.model.GChartModelRow;
import org.primefaces.extensions.component.gchart.model.GChartType;
import org.primefaces.json.JSONObject;

@ManagedBean
@RequestScoped
public class BasicGChartController implements Serializable {

    private static final long serialVersionUID = 253762400419864192L;

	private Random random = new Random();
	private int mushrooms = random.nextInt(10);
	private int onions = random.nextInt(10);
	private GChartType chartType = GChartType.PIE;
	private GChartModel chartModel = null;
	
	public GChartModel getChart(){
		return chartModel;
	}
	
	@PostConstruct
	public void generateModel() {
		chartModel = new GChartModelBuilder().setChartType(getChartType())
				.addColumns("Topping", "Slices").addRow("Mushrooms", mushrooms)
				.addRow("Onions", onions).build();
	}
	
	public void onSelect(SelectEvent event){
		JsonArray value = (JsonArray) event.getObject();
        if(value.size() > 0){
            JsonElement element = value.get(0);
            String label = new ArrayList<GChartModelRow>(this.getChart().getRows()).get(element.getAsJsonObject().get("row").getAsInt()).getLabel();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "You have selected: " + label, null));
        }
	}

	public int getMushrooms() {
		return mushrooms;
	}

	public void setMushrooms(int mushrooms) {
		this.mushrooms = mushrooms;
	}

	public int getOnions() {
		return onions;
	}

	public void setOnions(int onions) {
		this.onions = onions;
	}

	public GChartType getChartType() {
		return chartType;
	}

	public void setChartType(GChartType chartType) {
		this.chartType = chartType;
	}
	
	public List<GChartType> getTypes(){
		return Arrays.asList(new GChartType[]{GChartType.AREA,GChartType.BAR,GChartType.COLUMN,GChartType.PIE});
	}
}