package de.holmarku.restapidescription.gui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.util.StringUtils;

import de.holmarku.restapidescription.model.ApiField;
import de.holmarku.restapidescription.repository.ApiFieldRepository;

@Route
public class MainView extends VerticalLayout {

	private final ApiFieldRepository apiFieldRepo;

	private final ApiFieldEditor apiFieldEditor;

	final Grid<ApiField> grid;

	final TextField filter;

	private final Button addNewBtn;

	public MainView(ApiFieldRepository apiFieldRepo, ApiFieldEditor apiFieldEditor) {
		this.apiFieldRepo = apiFieldRepo;
		this.apiFieldEditor = apiFieldEditor;
		this.grid = new Grid<>(ApiField.class);
		this.filter = new TextField();
		this.addNewBtn = new Button("New Field", VaadinIcon.PLUS.create());

		// build layout
		HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
		add(actions, grid, apiFieldEditor);

		grid.setHeight("300px");
		grid.setColumns("id", "name", "typeFormat");
		grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);

		filter.setPlaceholder("Filter by name");

		// Hook logic to components

		// Replace listing with filtered content when user changes filter
		filter.setValueChangeMode(ValueChangeMode.EAGER);
		filter.addValueChangeListener(e -> listApiFields(e.getValue()));

		// Connect selected Customer to editor or hide if none is selected
		grid.asSingleSelect().addValueChangeListener(e -> {
			apiFieldEditor.editApiField(e.getValue());
		});

		// Instantiate and edit new Customer the new button is clicked
		addNewBtn.addClickListener(e -> apiFieldEditor.editApiField(new ApiField()));

		// Listen changes made by the editor, refresh data from backend
		apiFieldEditor.setChangeHandler(() -> {
			apiFieldEditor.setVisible(false);
			listApiFields(filter.getValue());
		});

		// Initialize listing
		listApiFields(null);
	}

	// tag::listCustomers[]
	void listApiFields(String filterText) {
		if (StringUtils.isEmpty(filterText)) {
			grid.setItems(apiFieldRepo.findAll());
		}
		else {
			grid.setItems(apiFieldRepo.findByNameStartsWithIgnoreCase(filterText));
		}
	}
	// end::listCustomers[]
}
