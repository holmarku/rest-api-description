package de.holmarku.restapidescription.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import de.holmarku.restapidescription.enums.TypeFormat;

@Entity
public class ApiField {
	
	
	@Id  
	@GeneratedValue
    private Long id;
 
	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "apiFields")  //(cascade = CascadeType.ALL, mappedBy ="id")
	Set<ApiDescription> apiDescriptions;
	
    @Column(length=50)  
    private String name;
    
    @Column(length=1024) 
    private String descriptionDe;
    
    @Column(length=1024)  
    private String descriptionEn;
    
    @Enumerated(EnumType.STRING)
    @Column(length=20)  
    private TypeFormat typeFormat;
    
    @Column(length=2)  
    private String integralDigits;
    
    @Column(length=2)  
    private String fractionDigits;
    
    @Column(length=20)  
    private String parameterTableName;
    
    @Column(length=30) 
    private String parameterTableKey;
    
    @Column(length=30) 
    private String parameterTableValue;
    
    @Column(length=250)  
    private String enumValues;
    
    @Column(length=50) 
    private String defaultValue;
    
    @Column(length=50)  
    private String exampleValue;
    
    @Column(length=20)  
    private String minimumVal;
    
    @Column(length=20)  
    private String maximumVal;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public Set<ApiDescription> getApiDescriptions() {
		return apiDescriptions;
	}

	public void setApiDescriptions(Set<ApiDescription> apiDescriptions) {
		this.apiDescriptions = apiDescriptions;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescriptionDe() {
		return descriptionDe;
	}

	public void setDescriptionDe(String descriptionDe) {
		this.descriptionDe = descriptionDe;
	}

	public String getDescriptionEn() {
		return descriptionEn;
	}

	public void setDescriptionEn(String descriptionEN) {
		this.descriptionEn = descriptionEN;
	}

	public TypeFormat getTypeFormat() {
		return typeFormat;
	}

	public void setTypeFormat(TypeFormat typeFormat) {
		this.typeFormat = typeFormat;
	}

	public String getParameterTable() {
		return parameterTableName;
	}

	public void setParameterTable(String parameterTable) {
		this.parameterTableName = parameterTable;
	}

	public String getEnumValues() {
		return enumValues;
	}

	public String getParameterTableName() {
		return parameterTableName;
	}

	public void setParameterTableName(String parameterTableName) {
		this.parameterTableName = parameterTableName;
	}

	public String getParameterTableKey() {
		return parameterTableKey;
	}

	public void setParameterTableKey(String parameterTableKey) {
		this.parameterTableKey = parameterTableKey;
	}

	public String getParameterTableValue() {
		return parameterTableValue;
	}

	public void setParameterTableValue(String parameterTableValue) {
		this.parameterTableValue = parameterTableValue;
	}

	public void setEnumValues(String enumValues) {
		this.enumValues = enumValues;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getExampleValue() {
		return exampleValue;
	}

	public void setExampleValue(String exampleValue) {
		this.exampleValue = exampleValue;
	}

	public String getIntegralDigits() {
		return integralDigits;
	}

	public void setIntegralDigits(String integralDigits) {
		this.integralDigits = integralDigits;
	}

	public String getFractionDigits() {
		return fractionDigits;
	}

	public void setFractionDigits(String fractionDigits) {
		this.fractionDigits = fractionDigits;
	}

	public String getMinimumVal() {
		return minimumVal;
	}

	public void setMinimumVal(String minimumVal) {
		this.minimumVal = minimumVal;
	}

	public String getMaximumVal() {
		return maximumVal;
	}

	public void setMaximumVal(String maximumVal) {
		this.maximumVal = maximumVal;
	}
    
	
}
