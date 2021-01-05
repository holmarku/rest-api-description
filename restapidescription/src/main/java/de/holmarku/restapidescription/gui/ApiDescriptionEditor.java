package de.holmarku.restapidescription.gui;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import de.holmarku.restapidescription.enums.ProductEnum;
import de.holmarku.restapidescription.model.ApiDescription;
import de.holmarku.restapidescription.repository.ApiDescriptionRepository;

@SpringComponent
@UIScope
public class ApiDescriptionEditor extends VerticalLayout implements KeyNotifier {

	private final ApiDescriptionRepository apiDescriptionRepo;

	/**
	 * The currently edited description
	 */
	private ApiDescription apiDescriptionObject;

	/* Fields to edit properties in ApiFiel entity */
	TextField apiTitle = new TextField("Title");
	TextField apiDescription = new TextField("Description");
	TextField apiUrl = new TextField("Url");
	ComboBox<ProductEnum> productEnum = new ComboBox<>();

	/* Action buttons */
	Button save = new Button("Save", VaadinIcon.CHECK.create());
	Button cancel = new Button("Cancel");
	Button delete = new Button("Delete", VaadinIcon.TRASH.create());
	HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

	Binder<ApiDescription> binder = new Binder<>(ApiDescription.class);
	private ChangeHandler changeHandler;
	
	@Autowired
	public ApiDescriptionEditor(ApiDescriptionRepository apiDescriptionRepo) {
		this.apiDescriptionRepo = apiDescriptionRepo;
		
		productEnum.setLabel("Product");
		productEnum.setItems(ProductEnum.values());
		
		add(apiTitle, apiDescription, productEnum, apiUrl, actions);

		// bind using naming convention
		binder.bindInstanceFields(this);	
		
		// Configure and style components
		setSpacing(true);

		save.getElement().getThemeList().add("primary");
		delete.getElement().getThemeList().add("error");

		addKeyPressListener(Key.ENTER, e -> save());

		// wire action buttons to save, delete and reset
		save.addClickListener(e -> save());
		delete.addClickListener(e -> delete());
		//cancel.addClickListener(e -> editApiField(apiField));
		cancel.addClickListener(e -> editApiDescription(null));
		setVisible(false);
	}

	void delete() {
		apiDescriptionRepo.delete(apiDescriptionObject);
		changeHandler.onChange();
	}

	void save() {
		apiDescriptionRepo.save(apiDescriptionObject);
		changeHandler.onChange();
	}

	public interface ChangeHandler {
		void onChange();
	}

	public final void editApiDescription(ApiDescription ad) {
		if (ad == null) {
			setVisible(false);
			return;
		}
		final boolean persisted = ad.getId() != null;
		//
		
		if (persisted) {
			// Find fresh entity for editing
			apiDescriptionObject = apiDescriptionRepo.findById(ad.getId()).get();
			productEnum.setValue(apiDescriptionObject.getProductEnum());
		}
		else {
			apiDescriptionObject = ad;
		}
		delete.setVisible(persisted);

		// Bind customer properties to similarly named fields
		// Could also use annotation or "manual binding" or programmatically
		// moving values from fields to entities before saving
		binder.setBean(apiDescriptionObject);

		setVisible(true);

		// Focus title initially
		apiTitle.focus();
	}

	public void setChangeHandler(ChangeHandler h) {
		// ChangeHandler is notified when either save or delete
		// is clicked
		changeHandler = h;
	}

}
