package com.fazil.grievance_ai.api;

import com.fazil.grievance_ai.utilities.Constants;

public class FetchDataModal {

    // Variables declarations.
    private int itemID;
    private String  itemTitle, itemDescription, itemDate;
    private String itemCode, itemHTML, itemCSS, itemJavaScript;
    private boolean itemLocked;
    private Constants.fetchDataType itemType;

    // Getter and setter functions.
    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) { this.itemDescription = itemDescription; }

    public String getItemDate() {
        return itemDate;
    }

    public void setItemDate(String itemDate) {
        this.itemDate = itemDate;
    }

    public Constants.fetchDataType getItemType() {
        return itemType;
    }

    public void setItemType(Constants.fetchDataType itemType) {
        this.itemType = itemType;
    }

    public String getItemHTML() {
        return itemHTML;
    }

    public void setItemHTML(String itemHTML) {
        this.itemHTML = itemHTML;
    }

    public String getItemCSS() {
        return itemCSS;
    }

    public void setItemCSS(String itemCSS) {
        this.itemCSS = itemCSS;
    }

    public String getItemJavaScript() {
        return itemJavaScript;
    }

    public void setItemJavaScript(String itemJavaScript) {
        this.itemJavaScript = itemJavaScript;
    }

    public String getItemCode() { return itemCode; }

    public void setItemCode(String itemCode) { this.itemCode = itemCode; }

    public boolean getItemLocked() { return itemLocked; }

    public void setItemLocked(boolean itemLocked) { this.itemLocked = itemLocked; }

    // Parameter Constructor
    public FetchDataModal(
            int projectId,
            String projectTitle,
            String projectDescription,
            String projectCode,
            String projectLanguage,
            boolean isProjectLocked,
            Constants.fetchDataType itemType
    ) {
        this.itemID = projectId;
        this.itemTitle = projectTitle;
        this.itemDescription = projectDescription;
        this.itemCode = projectCode;
        this.itemDate = projectLanguage;
        this.itemLocked = isProjectLocked;
        this.itemType = itemType;
    }

    // Empty Constructor
    public FetchDataModal(){
        this.itemID = 0;
        this.itemTitle = "";
        this.itemDescription = "";
        this.itemCode = "";
        this.itemDate = "";
        this.itemLocked = false;
    }

}
