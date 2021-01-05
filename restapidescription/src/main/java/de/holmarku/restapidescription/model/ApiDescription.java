package de.holmarku.restapidescription.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import de.holmarku.restapidescription.enums.ProductEnum;

@Entity
public class ApiDescription {
	
	@Id  
	@GeneratedValue
    private Long id;
    
    //@ManyToOne(fetch=FetchType.LAZY)
    //private OpenApiVersion openApiVersion;
    
    @Column(length=50)  // The column length is used at the UI level and the DB level
    private String apiTitle;
    
    @Column(length=250)  // The column length is used at the UI level and the DB level
    private String apiDescription;

    /*@ManyToOne(fetch=FetchType.LAZY)
    @Required
    @DescriptionsList
    private ContactEmail contactEmail;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @Required
    @DescriptionsList
    private ApiVersion apiVersion;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @Required
    @DescriptionsList
    private LicenseName licenseName;
    */
    @Enumerated(EnumType.STRING)
    @Column(length=10)  // The column length is used at the UI level and the DB level
    private ProductEnum productEnum; //server
    
    @Column(length=50)  // The column length is used at the UI level and the DB level
    private String apiUrl; //server

    /*
    @ElementCollection
    @ListProperties("apiPath, apiMethods.methodId, apiMethods.methodEnum, apiMethods.methodOperation")
    
    hier: @OneToMany(cascade = CascadeType.All, mappedBy ="ToDo")
    in abhängigen Klasse 
    private Collection<ApiPath> apiPath; //oder List / ArrayList bzw Map / HashMap
     */

	public String getApiTitle() {
		return apiTitle;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setApiTitle(String apiTitle) {
		this.apiTitle = apiTitle;
	}

	public String getApiDescription() {
		return apiDescription;
	}

	public void setApiDescription(String apiDescription) {
		this.apiDescription = apiDescription;
	}

	public ProductEnum getProductEnum() {
		return productEnum;
	}

	public void setProductEnum(ProductEnum productEnum) {
		this.productEnum = productEnum;
	}

	public String getApiUrl() {
		return apiUrl;
	}

	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
	}
    

	
}

