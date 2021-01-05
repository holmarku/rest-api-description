package de.holmarku.restapidescription.gui;

import java.util.EnumSet;
import java.util.stream.Stream;

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

import de.holmarku.restapidescription.enums.TypeFormat;
import de.holmarku.restapidescription.model.ApiField;
import de.holmarku.restapidescription.repository.ApiFieldRepository;

@SpringComponent
@UIScope
public class ApiFieldEditor extends VerticalLayout implements KeyNotifier {

	private final ApiFieldRepository apiFieldRepo;

	/**
	 * The currently edited customer
	 */
	private ApiField apiField;

	/* Fields to edit properties in ApiFiel entity */
	TextField name = new TextField("Name");
	TextField descriptionDe = new TextField("Description DE");
	TextField descriptionEn = new TextField("Description EN");

	/* Action buttons */
	Button save = new Button("Save", VaadinIcon.CHECK.create());
	Button cancel = new Button("Cancel");
	Button delete = new Button("Delete", VaadinIcon.TRASH.create());
	HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

	Binder<ApiField> binder = new Binder<>(ApiField.class);
	private ChangeHandler changeHandler;

	@Autowired
	public ApiFieldEditor(ApiFieldRepository apiFieldRepo) {
		this.apiFieldRepo = apiFieldRepo;

		ComboBox<String> combobox = new ComboBox<>();
		combobox.setItems((Stream<String>) EnumSet.allOf(TypeFormat.class));
		 
		add(name, descriptionDe, descriptionEn, combobox, actions);

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
		cancel.addClickListener(e -> editApiField(apiField));
		setVisible(false);
	}

	void delete() {
		apiFieldRepo.delete(apiField);
		changeHandler.onChange();
	}

	void save() {
		apiFieldRepo.save(apiField);
		changeHandler.onChange();
	}

	public interface ChangeHandler {
		void onChange();
	}

	public final void editApiField(ApiField af) {
		if (af == null) {
			setVisible(false);
			return;
		}
		final boolean persisted = af.getId() != null;
		if (persisted) {
			// Find fresh entity for editing
			apiField = apiFieldRepo.findById(af.getId()).get();
		}
		else {
			apiField = af;
		}
		cancel.setVisible(persisted);

		// Bind customer properties to similarly named fields
		// Could also use annotation or "manual binding" or programmatically
		// moving values from fields to entities before saving
		binder.setBean(apiField);

		setVisible(true);

		// Focus first name initially
		name.focus();
	}

	public void setChangeHandler(ChangeHandler h) {
		// ChangeHandler is notified when either save or delete
		// is clicked
		changeHandler = h;
	}

}
