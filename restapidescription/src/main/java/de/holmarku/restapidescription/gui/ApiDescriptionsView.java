package de.holmarku.restapidescription.gui;

import org.springframework.util.StringUtils;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

import de.holmarku.restapidescription.model.ApiDescription;
import de.holmarku.restapidescription.model.ApiField;
import de.holmarku.restapidescription.repository.ApiDescriptionRepository;

@Route("descriptions")
public class ApiDescriptionsView extends VerticalLayout {

	private final ApiDescriptionRepository apiDescriptionRepo;

	private final ApiDescriptionEditor apiDescriptionEditor;

	final Grid<ApiDescription> grid;

	final TextField filter;

	private final Button addNewBtn;

	public ApiDescriptionsView(ApiDescriptionRepository apiDescriptionRepo, ApiDescriptionEditor apiDescriptionEditor) {
		this.apiDescriptionRepo = apiDescriptionRepo;
		this.apiDescriptionEditor = apiDescriptionEditor;
		this.grid = new Grid<>(ApiDescription.class);
		this.filter = new TextField();
		this.addNewBtn = new Button("New Description", VaadinIcon.PLUS.create());

		// build layout
		HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
		add(actions, grid, apiDescriptionEditor);

		grid.setHeight("300px");
		grid.setColumns("id", "apiTitle", "productEnum", "apiUrl");
		grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);

		filter.setPlaceholder("Filter by API title");

		// Hook logic to components

		// Replace listing with filtered content when user changes filter
		filter.setValueChangeMode(ValueChangeMode.EAGER);
		filter.addValueChangeListener(e -> listApiDescriptions(e.getValue()));

		// Connect selected Customer to editor or hide if none is selected
		grid.asSingleSelect().addValueChangeListener(e -> {
			apiDescriptionEditor.editApiDescription(e.getValue());
		});

		// Instantiate and edit new Customer the new button is clicked
		addNewBtn.addClickListener(e -> apiDescriptionEditor.editApiDescription(new ApiDescription()));

		// Listen changes made by the editor, refresh data from backend
		apiDescriptionEditor.setChangeHandler(() -> {
			apiDescriptionEditor.setVisible(false);
			listApiDescriptions(filter.getValue());
		});

		// Initialize listing
		listApiDescriptions(null);
	}

	// tag::listDescriptions[]
	void listApiDescriptions(String filterText) {
		if (StringUtils.isEmpty(filterText)) {
			grid.setItems(apiDescriptionRepo.findAll());
		}
		else {
			grid.setItems(apiDescriptionRepo.findByApiTitleStartsWithIgnoreCase(filterText));
		}
	}
	// end::listDescriptions[]
}
